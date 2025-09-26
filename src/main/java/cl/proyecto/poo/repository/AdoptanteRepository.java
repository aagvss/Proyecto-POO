package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Adoptante;

import java.util.*;

public class AdoptanteRepository {
    private final Map<String, Adoptante> store = new HashMap<>();

    public void save(Adoptante a) { store.put(a.getId(), a); }
    public Optional<Adoptante> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public List<Adoptante> findAll() { return new ArrayList<>(store.values()); }
    public boolean existsByDocumento(String documento) {
        return store.values().stream().anyMatch(a -> a.getDocumento().equals(documento));
    }
}
