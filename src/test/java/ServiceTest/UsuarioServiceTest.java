package ServiceTest;// ... (imports)

import RepositoryTest.UsuarioRepositoryStub;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Test
    void testRegistrarUsuario_Fallo_EmailExistente_ConStub() {
        // 1. PREPARACIÓN: Crear los Stubs
        UsuarioRepositoryStub repoStub = new UsuarioRepositoryStub();
        EncriptacionServiceStub encriptacionStub = new EncriptacionServiceStub();

        // Configurar el Stub del Repositorio para simular que el email ya existe
        repoStub.setExisteEmailSimulado(true);

        UsuarioService usuarioService = new UsuarioService(repoStub, encriptacionStub);

        // 2. EJECUCIÓN y VERIFICACIÓN DE EXCEPCIÓN
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario("existente@mail.cl", "password", Rol.EMPLEADO, null);
        });

        // 3. VERIFICACIÓN:
        assertTrue(exception.getMessage().contains("El email ya está registrado"));
        assertFalse(repoStub.fueLlamadoSave(),
                "El método save() NO debe ser llamado si el email ya existe.");
    }

    @Test
    void testRegistrarUsuario_Exito_Adoptante_ConStub() {
        // 1. PREPARACIÓN
        UsuarioRepositoryStub repoStub = new UsuarioRepositoryStub();
        EncriptacionServiceStub encriptacionStub = new EncriptacionServiceStub();
        UsuarioService usuarioService = new UsuarioService(repoStub, encriptacionStub);

        String adoptanteId = "ADOP-999";
        String password = "securepassword";

        // 2. EJECUCIÓN
        usuarioService.registrarUsuario("adoptante@mail.cl", password, Rol.ADOPTANTE, adoptanteId);

        // 3. VERIFICACIÓN
        assertTrue(repoStub.fueLlamadoSave(), "El método save() debe ser llamado.");
        assertEquals("HASH_DE_" + password, repoStub.obtenerUsuarioGuardado().getPassword(),
                "La contraseña debe estar encriptada.");
        assertEquals(adoptanteId, repoStub.obtenerUsuarioGuardado().getAdoptanteId());
    }
}