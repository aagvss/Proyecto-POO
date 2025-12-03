package ServiceTest;

import cl.proyecto.poo.service.EncriptacionService;

public class EncriptacionServiceStub extends EncriptacionService {
    @Override
    public String encriptar(String password) {
        // Retorna un valor predecible en lugar de un hash real
        return "HASH_DE_" + password;
    }

    @Override
    public boolean verificar(String password, String hashAlmacenado) {
        // Implementar la lógica de verificación si es necesario para el login,
        // pero para el registro solo necesitamos el encriptar.
        return hashAlmacenado.equals("HASH_DE_" + password);
    }
}