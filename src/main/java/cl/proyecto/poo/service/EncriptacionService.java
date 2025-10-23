package cl.proyecto.poo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Servicio para encriptación y verificación de contraseñas
 */
public class EncriptacionService {
    private static final String ALGORITMO = "SHA-256";

    /**
     * Genera un hash seguro para una contraseña
     */
    public String encriptar(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITMO);
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con su hash
     */
    public boolean verificar(String password, String hashAlmacenado) {
        String hashIngresado = encriptar(password);
        return hashIngresado.equals(hashAlmacenado);
    }

    /**
     * Convierte bytes a hexadecimal
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    /**
     * Genera una contraseña temporal aleatoria
     */
    public String generarPasswordTemporal() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return sb.toString();
    }
}