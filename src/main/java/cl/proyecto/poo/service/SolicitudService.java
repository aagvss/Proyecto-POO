package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.SolicitudAdopcion;
import cl.proyecto.poo.model.EstadoSolicitud;
import cl.proyecto.poo.repository.SolicitudRepository;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

import java.util.List;

public class SolicitudService {
    private final SolicitudRepository repo;
    private final AdoptanteService adoptanteService;
    private final MascotaService mascotaService;
    private final RulesEngine rulesEngine;

    public SolicitudService(SolicitudRepository repo, AdoptanteService adoptanteService,
                            MascotaService mascotaService, RulesEngine rulesEngine) {
        this.repo = repo;
        this.adoptanteService = adoptanteService;
        this.mascotaService = mascotaService;
        this.rulesEngine = rulesEngine;
    }

    public SolicitudAdopcion solicitar(String adoptanteId, String mascotaId) {
        Adoptante a = adoptanteService.buscarPorId(adoptanteId)
                .orElseThrow(() -> new IllegalArgumentException("Adoptante no encontrado"));
        Mascota m = mascotaService.buscarPorId(mascotaId)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        // Primero evaluar reglas
        List<RuleResult> results = rulesEngine.evaluate(a, m);
        boolean allPass = results.stream().allMatch(RuleResult::isPass);

        // Luego crear y guardar solicitud con estado final
        SolicitudAdopcion s = new SolicitudAdopcion(adoptanteId, mascotaId);

        if (allPass) {
            s.setEstado(EstadoSolicitud.APROBADA);
        } else {
            s.setEstado(EstadoSolicitud.REQUIERE_REVISION);
            StringBuilder motivos = new StringBuilder();
            results.stream().filter(r -> !r.isPass())
                    .forEach(r -> motivos.append(r.getMessage()).append("; "));
            s.setMotivoRechazo(motivos.toString());
        }

        repo.save(s); // Una sola operación de guardado
        return s;
    }
}
