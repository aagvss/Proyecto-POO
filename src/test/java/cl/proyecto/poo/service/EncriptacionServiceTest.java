package cl.proyecto.poo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncriptacionServiceTest {
    @Test
    void testEncriptacionYVerificacion() {
        EncriptacionService service = new EncriptacionService();
        String passwordReal = "miPasswordSecreta";

        // Encriptar
        String hash = service.encriptar(passwordReal);

        // Verificar que el hash no sea igual a la password
        assertNotEquals(passwordReal, hash);

        // Verificar que al comprobar funcione
        assertTrue(service.verificar(passwordReal, hash));

        // Verificar que una password incorrecta falle
        assertFalse(service.verificar("passwordIncorrecta", hash));
    }

}