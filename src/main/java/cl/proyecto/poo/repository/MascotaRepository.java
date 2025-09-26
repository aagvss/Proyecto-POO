package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Mascota;

import java.util.*;

public class MascotaRepository {
    private final Map<String, Mascota> store = new HashMap<>();

    public void save(Mascota m) { store.put(m.getId(), m); }
    public Optional<Mascota> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public List<Mascota> findAll() { return new ArrayList<>(store.values()); }
    public List<Mascota> findByEspecie(String especie) {
        List<Mascota> out = new ArrayList<>();
        for (Mascota m : store.values()) {
            if (m.getEspecie().name().equalsIgnoreCase(especie)) out.add(m);
        }
        return out;
    }
}
