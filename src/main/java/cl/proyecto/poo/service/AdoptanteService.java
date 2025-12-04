package cl.proyecto.poo.service;

import cl.proyecto.poo.core.ValidadorUtils;
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
        if (!ValidadorUtils.esTelefonoValido(a.getTelefono())) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos y comenzar con 9 (Ej: 912345678)");
        }

        repo.save(a);
    }

    public Optional<Adoptante> buscarPorId(String id) { return repo.findById(id); }
}
