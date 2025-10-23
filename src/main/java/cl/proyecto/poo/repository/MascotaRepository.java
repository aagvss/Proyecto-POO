package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.core.JsonDataManager;
import java.util.*;

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

    public List<Mascota> findByEspecie(String especie) {
        List<Mascota> out = new ArrayList<>();
        for (Mascota m : store.values()) {
            if (m.getEspecie().name().equalsIgnoreCase(especie)) out.add(m);
        }
        return out;
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
