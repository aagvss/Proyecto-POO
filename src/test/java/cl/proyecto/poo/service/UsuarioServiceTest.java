package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        // Usamos repositorios reales pero vacíos para cada test
        usuarioRepository = new UsuarioRepository();
        // Limpiamos el repositorio
        EncriptacionService encriptacionService = new EncriptacionService();
        usuarioService = new UsuarioService(usuarioRepository, encriptacionService);
    }

    @Test
    void registrarUsuarioExitoso() {
        Usuario u = usuarioService.registrarUsuario("test@mail.com", "123456", Rol.EMPLEADO, null);

        assertNotNull(u.getId());
        assertEquals("test@mail.com", u.getEmail());
        assertNotEquals("123456", u.getPassword());
    }

    @Test
    void registrarEmailDuplicadoLanzaExcepcion() {
        usuarioService.registrarUsuario("duplicado@mail.com", "123456", Rol.EMPLEADO, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario("duplicado@mail.com", "otraPass", Rol.EMPLEADO, null);
        });

        assertTrue(exception.getMessage().contains("ya está registrado"));
    }

}