package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.SolicitudAdopcion;
import cl.proyecto.poo.core.JsonDataManager;
import java.util.*;

public class SolicitudRepository {
    private final Map<String, SolicitudAdopcion> store = new HashMap<>();
    private static final String ARCHIVO_SOLICITUDES = "solicitudes.json";

    public SolicitudRepository() {
        cargarDesdeJson();
    }

    public void save(SolicitudAdopcion s) {
        store.put(s.getId(), s);
        guardarEnJson();
    }

    public Optional<SolicitudAdopcion> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<SolicitudAdopcion> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<SolicitudAdopcion> findByAdoptanteId(String adoptanteId) {
        List<SolicitudAdopcion> out = new ArrayList<>();
        for (SolicitudAdopcion s : store.values()) {
            if (s.getAdoptanteId().equals(adoptanteId)) out.add(s);
        }
        return out;
    }

    private void cargarDesdeJson() {
        List<SolicitudAdopcion> solicitudes = JsonDataManager.cargarDatos(ARCHIVO_SOLICITUDES, SolicitudAdopcion.class);
        for (SolicitudAdopcion solicitud : solicitudes) {
            store.put(solicitud.getId(), solicitud);
        }
    }

    private void guardarEnJson() {
        JsonDataManager.guardarDatos(ARCHIVO_SOLICITUDES, new ArrayList<>(store.values()));
    }
}
