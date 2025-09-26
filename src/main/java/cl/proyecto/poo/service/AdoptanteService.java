package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.repository.AdoptanteRepository;

import java.util.Optional;

public class AdoptanteService {
    private final AdoptanteRepository repo;

    public AdoptanteService(AdoptanteRepository repo) { this.repo = repo; }

    public void registrarAdoptante(Adoptante a) {
        if (repo.existsByDocumento(a.getDocumento())) {
            throw new IllegalArgumentException("Documento ya registrado");
        }
        repo.save(a);
    }

    public Optional<Adoptante> buscarPorId(String id) { return repo.findById(id); }
}
