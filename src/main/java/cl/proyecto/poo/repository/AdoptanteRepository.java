package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.core.JsonDataManager;
import java.util.*;

public class AdoptanteRepository {
    private final Map<String, Adoptante> store = new HashMap<>();
    private static final String ARCHIVO_ADOPTANTES = "adoptantes.json";

    public AdoptanteRepository() {
        cargarDesdeJson();
    }

    public void save(Adoptante a) {
        store.put(a.getId(), a);
        guardarEnJson();
    }

    public Optional<Adoptante> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Adoptante> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean existsByDocumento(String documento) {
        return store.values().stream().anyMatch(a -> a.getDocumento().equals(documento));
    }

    private void cargarDesdeJson() {
        List<Adoptante> adoptantes = JsonDataManager.cargarDatos(ARCHIVO_ADOPTANTES, Adoptante.class);
        for (Adoptante adoptante : adoptantes) {
            store.put(adoptante.getId(), adoptante);
        }
    }

    private void guardarEnJson() {
        JsonDataManager.guardarDatos(ARCHIVO_ADOPTANTES, new ArrayList<>(store.values()));
    }
}
