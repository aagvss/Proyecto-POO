package cl.proyecto.poo.core;

import java.util.regex.Pattern;

public class ValidadorUtils {

    private static final Pattern PATRON_TELEFONO = Pattern.compile("^9\\d{8}$");

    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null) return false;
        return PATRON_TELEFONO.matcher(telefono).matches();
    }

    public static String formatearRut(String rutRaw) {
        if (rutRaw == null || rutRaw.trim().isEmpty()) return "";

        String rutLimpio = rutRaw.replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();

        if (rutLimpio.length() < 2) return rutRaw;

        if (rutLimpio.length() == 8) {
            rutLimpio = "0" + rutLimpio;
        }

        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        String dv = rutLimpio.substring(rutLimpio.length() - 1);

        return cuerpo + "-" + dv;
    }

    public static boolean esRutConFormatoValido(String rut) {
        return rut.matches("^[0-9]+-[0-9K]$");
    }
}
