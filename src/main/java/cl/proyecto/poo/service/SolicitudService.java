package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.SolicitudAdopcion;
import cl.proyecto.poo.model.EstadoSolicitud;
import cl.proyecto.poo.repository.SolicitudRepository;
import cl.proyecto.poo.rules.RuleEngineManager;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

import java.util.List;

public class SolicitudService {
    private final SolicitudRepository repo;
    private final AdoptanteService adoptanteService;
    private final MascotaService mascotaService;
    private final RulesEngine rulesEngine;

    public SolicitudService(SolicitudRepository repo, AdoptanteService adoptanteService,
                            MascotaService mascotaService) {
        this.repo = repo;
        this.adoptanteService = adoptanteService;
        this.mascotaService = mascotaService;
        this.rulesEngine = RuleEngineManager.getInstance();
    }

    public SolicitudAdopcion solicitar(String adoptanteId, String mascotaId) {
        Adoptante a = adoptanteService.buscarPorId(adoptanteId)
                .orElseThrow(() -> new IllegalArgumentException("Adoptante no encontrado"));
        Mascota m = mascotaService.buscarPorId(mascotaId)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        if (m.isAdoptada()) {
            throw new IllegalArgumentException("La mascota ya ha sido adoptada");
        }

        List<RuleResult> results = rulesEngine.evaluate(a, m);
        boolean allPass = results.stream().allMatch(RuleResult::isPass);

        SolicitudAdopcion s = new SolicitudAdopcion(adoptanteId, mascotaId);

        if (allPass) {
            s.setEstado(EstadoSolicitud.APROBADA);
            mascotaService.marcarMascotaComoAdoptada(mascotaId, adoptanteId);
        } else {
            s.setEstado(EstadoSolicitud.REQUIERE_REVISION);
            StringBuilder motivos = new StringBuilder();
            results.stream().filter(r -> !r.isPass())
                    .forEach(r -> motivos.append(r.getMessage()).append("; "));
            s.setMotivoRechazo(motivos.toString());
        }

        repo.save(s);
        return s;
    }

    public void aprobarSolicitudManual(String solicitudId) {
        SolicitudAdopcion solicitud = repo.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Mascota mascota = mascotaService.buscarPorId(solicitud.getMascotaId())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        if (mascota.isAdoptada()) {
            throw new IllegalArgumentException("La mascota ya ha sido adoptada");
        }

        solicitud.setEstado(EstadoSolicitud.APROBADA);
        mascotaService.marcarMascotaComoAdoptada(solicitud.getMascotaId(), solicitud.getAdoptanteId());
        repo.save(solicitud);
    }

    public List<SolicitudAdopcion> listarTodas() {
        return repo.findAll();
    }

    public void rechazarSolicitudManual(String solicitudId, String motivoFinal) {
        SolicitudAdopcion solicitud = repo.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (solicitud.getEstado() == EstadoSolicitud.APROBADA) {
            throw new IllegalStateException("No se puede rechazar una solicitud ya aprobada");
        }

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);

        String motivoAnterior = solicitud.getMotivoRechazo() != null ? solicitud.getMotivoRechazo() : "";
        solicitud.setMotivoRechazo(motivoAnterior + " | Rechazo Admin: " + motivoFinal);

        repo.save(solicitud);
    }
}
