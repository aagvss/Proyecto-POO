package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.repository.MascotaRepository;

import java.util.List;
import java.util.Optional;

public class MascotaService {
    private final MascotaRepository repo;

    public MascotaService(MascotaRepository repo) { this.repo = repo; }

    public void registrarMascota(Mascota m) { repo.save(m); }

    public Optional<Mascota> buscarPorId(String id) { return repo.findById(id); }

    public List<Mascota> obtenerMascotasDisponibles() {
        return repo.findDispoibles();
    }

    public List<Mascota> obtenerMascotasAdoptadas() {
        return repo.findAdoptadas();
    }

    public void marcarMascotaComoAdoptada(String mascotaId, String adoptanteId) {
        repo.marcarComoAdoptada(mascotaId, adoptanteId);
    }
}
