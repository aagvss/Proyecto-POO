package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.model.EstadoUsuario;
import cl.proyecto.poo.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de usuarios y lógica de negocio
 */
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EncriptacionService encriptacionService;

    public UsuarioService(UsuarioRepository usuarioRepository, EncriptacionService encriptacionService) {
        this.usuarioRepository = usuarioRepository;
        this.encriptacionService = encriptacionService;
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    public Usuario registrarUsuario(String email, String password, Rol rol, String adoptanteId) {
        // Validaciones
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado: " + email);
        }

        if (password == null || password.trim().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        // Validar que si el rol es ADOPTANTE, tenga adoptanteId
        if (rol == Rol.ADOPTANTE && (adoptanteId == null || adoptanteId.trim().isEmpty())) {
            throw new IllegalArgumentException("Los adoptantes deben tener una referencia a adoptante");
        }

        // Crear usuario
        String id = "USR-" + java.util.UUID.randomUUID().toString().substring(0, 8);
        String passwordEncriptada = encriptacionService.encriptar(password);

        Usuario usuario = new Usuario(id, email, passwordEncriptada, rol, adoptanteId);
        usuarioRepository.save(usuario);

        return usuario;
    }


    public Usuario crearUsuarioAdminPorDefecto() {
        String email = "d.lincopi02@ufromail.cl";
        if (!usuarioRepository.existsByEmail(email)) {
            return registrarUsuario(email, "daniel123", Rol.ADMINISTRADOR, null);
        }
        return usuarioRepository.findByEmail(email).get();
    }

    /**
     * Actualiza la contraseña de un usuario
     */
    public void actualizarPassword(String usuarioId, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (nuevaPassword == null || nuevaPassword.trim().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        String passwordEncriptada = encriptacionService.encriptar(nuevaPassword);
        usuario.setPassword(passwordEncriptada);
        usuario.resetearIntentosFallidos();
        usuarioRepository.save(usuario);
    }

    /**
     * Cambia el estado de un usuario
     */
    public void cambiarEstado(String usuarioId, EstadoUsuario nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoUsuario.ACTIVO) {
            usuario.resetearIntentosFallidos();
        }
        usuarioRepository.save(usuario);
    }

    /**
     * Desbloquea un usuario y resetea intentos fallidos
     */
    public void desbloquearUsuario(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.resetearIntentosFallidos();
        usuarioRepository.save(usuario);
    }

    /**
     * Genera y asigna una contraseña temporal
     */
    public String generarYAsignarPasswordTemporal(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String passwordTemporal = encriptacionService.generarPasswordTemporal();
        actualizarPassword(usuarioId, passwordTemporal);

        return passwordTemporal;
    }

    // Métodos de consulta
    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    public List<Usuario> listarPorEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado);
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
