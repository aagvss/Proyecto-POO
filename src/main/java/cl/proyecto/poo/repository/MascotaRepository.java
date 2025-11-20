package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.core.JsonDataManager;
import java.util.*;
import java.util.stream.Collectors;

public class MascotaRepository {
    private final Map<String, Mascota> store = new HashMap<>();
    private static final String ARCHIVO_MASCOTAS = "mascotas.json";

    public MascotaRepository() {
        cargarDesdeJson();
    }

    public void save(Mascota m) {
        store.put(m.getId(), m);
        guardarEnJson();
    }

    public Optional<Mascota> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Mascota> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Mascota> findDispoibles() {
        return store.values().stream().filter(m -> !m.isAdoptada()).collect(Collectors.toList());
    }

    public List<Mascota> findAdoptadas() {
        return store.values().stream().filter(Mascota::isAdoptada).collect(Collectors.toList());
    }

    public List<Mascota> findByEspecie(String especie) {
        List<Mascota> out = new ArrayList<>();
        for (Mascota m : store.values()) {
            if (m.getEspecie().name().equalsIgnoreCase(especie)) out.add(m);
        }
        return out;
    }

    public void marcarComoAdoptada(String mascotaId, String adoptanteId) {
        Mascota mascota = store.get(mascotaId);
        if (mascota != null) {
            mascota.setAdoptada(true);
            mascota.setAdoptanteId(adoptanteId);
            guardarEnJson();
        }
    }

    private void cargarDesdeJson() {
        List<Mascota> mascotas = JsonDataManager.cargarDatos(ARCHIVO_MASCOTAS, Mascota.class);
        for (Mascota mascota : mascotas) {
            store.put(mascota.getId(), mascota);
        }
    }

    private void guardarEnJson() {
        JsonDataManager.guardarDatos(ARCHIVO_MASCOTAS, new ArrayList<>(store.values()));
    }
}
