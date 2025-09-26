package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.SolicitudAdopcion;

import java.util.*;

public class SolicitudRepository {
    private final Map<String, SolicitudAdopcion> store = new HashMap<>();

    public void save(SolicitudAdopcion s) { store.put(s.getId(), s); }
    public Optional<SolicitudAdopcion> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public List<SolicitudAdopcion> findAll() { return new ArrayList<>(store.values()); }
    public List<SolicitudAdopcion> findByAdoptanteId(String adoptanteId) {
        List<SolicitudAdopcion> out = new ArrayList<>();
        for (SolicitudAdopcion s : store.values()) {
            if (s.getAdoptanteId().equals(adoptanteId)) out.add(s);
        }
        return out;
    }
}
