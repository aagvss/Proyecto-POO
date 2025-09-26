package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.repository.MascotaRepository;

import java.util.Optional;

public class MascotaService {
    private final MascotaRepository repo;

    public MascotaService(MascotaRepository repo) { this.repo = repo; }

    public void registrarMascota(Mascota m) { repo.save(m); }

    public Optional<Mascota> buscarPorId(String id) { return repo.findById(id); }
}
