package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.EstadoUsuario;
import cl.proyecto.poo.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio para autenticación y gestión de sesiones
 */
public class AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private final EncriptacionService encriptacionService;
    private Usuario usuarioLogueado;

    public AutenticacionService(UsuarioRepository usuarioRepository, EncriptacionService encriptacionService) {
        this.usuarioRepository = usuarioRepository;
        this.encriptacionService = encriptacionService;
        this.usuarioLogueado = null;
    }

    /**
     * Intenta autenticar un usuario con email y password
     */
    public boolean autenticar(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return false; // Usuario no existe
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar estado de la cuenta
        if (!usuario.puedeAutenticar()) {
            return false; // Cuenta inactiva o bloqueada
        }

        // Verificar contraseña
        boolean credencialesCorrectas = encriptacionService.verificar(password, usuario.getPassword());

        if (credencialesCorrectas) {
            // Login exitoso
            usuario.setUltimoLogin(LocalDateTime.now().withNano(0));
            usuario.resetearIntentosFallidos();
            usuarioRepository.save(usuario);
            this.usuarioLogueado = usuario;
            return true;
        } else {
            // Login fallido
            usuario.incrementarIntentosFallidos();
            usuarioRepository.save(usuario);
            return false;
        }
    }

    /**
     * Cierra la sesión del usuario actual
     */
    public void logout() {
        this.usuarioLogueado = null;
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean estaAutenticado() {
        return usuarioLogueado != null;
    }

    /**
     * Obtiene el usuario actualmente autenticado
     */
    public Optional<Usuario> getUsuarioLogueado() {
        return Optional.ofNullable(usuarioLogueado);
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     */
    public boolean tieneRol(cl.proyecto.poo.model.Rol rol) {
        return estaAutenticado() && usuarioLogueado.getRol() == rol;
    }

    /**
     * Verifica si el usuario actual es administrador
     */
    public boolean esAdministrador() {
        return tieneRol(cl.proyecto.poo.model.Rol.ADMINISTRADOR);
    }

    /**
     * Verifica si el usuario actual es empleado
     */
    public boolean esEmpleado() {
        return tieneRol(cl.proyecto.poo.model.Rol.EMPLEADO);
    }

    /**
     * Verifica si el usuario actual es adoptante
     */
    public boolean esAdoptante() {
        return tieneRol(cl.proyecto.poo.model.Rol.ADOPTANTE);
    }

    /**
     * Obtiene el ID del adoptante asociado al usuario actual (si es adoptante)
     */
    public Optional<String> getAdoptanteId() {
        if (estaAutenticado() && usuarioLogueado.getRol() == cl.proyecto.poo.model.Rol.ADOPTANTE) {
            return Optional.ofNullable(usuarioLogueado.getAdoptanteId());
        }
        return Optional.empty();
    }
}