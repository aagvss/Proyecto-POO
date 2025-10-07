package cl.proyecto.poo.repository;

import cl.proyecto.poo.model.Usuario;

import java.util.*;

/**
 * Repositorio para gestión de usuarios en memoria
 */
public class UsuarioRepository {
    private final Map<String, Usuario> store = new HashMap<>();
    private final Map<String, Usuario> storeByEmail = new HashMap<>(); // Índice por email

    public void save(Usuario usuario) {
        store.put(usuario.getId(), usuario);
        storeByEmail.put(usuario.getEmail().toLowerCase(), usuario);
    }

    public Optional<Usuario> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Usuario> findByEmail(String email) {
        return Optional.ofNullable(storeByEmail.get(email.toLowerCase()));
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Usuario> findByRol(cl.proyecto.poo.model.Rol rol) {
        List<Usuario> resultados = new ArrayList<>();
        for (Usuario usuario : store.values()) {
            if (usuario.getRol() == rol) {
                resultados.add(usuario);
            }
        }
        return resultados;
    }

    public List<Usuario> findByEstado(cl.proyecto.poo.model.EstadoUsuario estado) {
        List<Usuario> resultados = new ArrayList<>();
        for (Usuario usuario : store.values()) {
            if (usuario.getEstado() == estado) {
                resultados.add(usuario);
            }
        }
        return resultados;
    }

    public boolean existsByEmail(String email) {
        return storeByEmail.containsKey(email.toLowerCase());
    }

    public void delete(String id) {
        Usuario usuario = store.get(id);
        if (usuario != null) {
            store.remove(id);
            storeByEmail.remove(usuario.getEmail().toLowerCase());
        }
    }
}