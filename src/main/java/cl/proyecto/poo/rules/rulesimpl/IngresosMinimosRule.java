package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

/**
 * Regla que evalúa si el adoptante cumple con los ingresos mínimos requeridos
 * para mantener a la mascota adecuadamente.
 */
public class IngresosMinimosRule implements RulesEngine.BiRule {

    private final double ingresosMinimos;
    private final boolean considerarTamanoMascota;

    /**
     * Constructor básico - ingresos mínimos fijos
     */
    public IngresosMinimosRule(double ingresosMinimos) {
        this.ingresosMinimos = ingresosMinimos;
        this.considerarTamanoMascota = false;
    }

    /**
     * Constructor avanzado - permite activar ajuste por tamaño de mascota
     */
    public IngresosMinimosRule(double ingresosMinimos, boolean considerarTamanoMascota) {
        this.ingresosMinimos = ingresosMinimos;
        this.considerarTamanoMascota = considerarTamanoMascota;
    }

    @Override
    public RuleResult apply(Adoptante adoptante, Mascota mascota) {
        double ingresosRequeridos = calcularIngresosRequeridos(mascota);
        double ingresosActuales = adoptante.getIngresosMensuales();

        boolean pass = ingresosActuales >= ingresosRequeridos;
        String message = generarMensajeResultado(pass, ingresosActuales, ingresosRequeridos, mascota);

        return new RuleResult(pass, "rule_ingresos_minimos", message);
    }

    /**
     * Calcula los ingresos requeridos según la mascota
     */
    private double calcularIngresosRequeridos(Mascota mascota) {
        if (!considerarTamanoMascota) {
            return ingresosMinimos; // Retorna el mínimo base
        }

        // Ajustar ingresos requeridos según tamaño de la mascota
        return switch (mascota.getTamano()) {
            case PEQUENO -> ingresosMinimos * 1.0;      // 100% del mínimo
            case MEDIANO -> ingresosMinimos * 1.2;      // 20% extra
            case GRANDE -> ingresosMinimos * 1.5;       // 50% extra
            case EXTRA_GRANDE -> ingresosMinimos * 2.0; // 100% extra
        };
    }

    /**
     * Genera mensaje descriptivo del resultado
     */
    private String generarMensajeResultado(boolean pass, double ingresosActuales,
                                           double ingresosRequeridos, Mascota mascota) {
        if (pass) {
            if (considerarTamanoMascota) {
                return String.format("Ingresos suficientes para mascota %s: $%,.0f >= $%,.0f",
                        mascota.getTamano().name().toLowerCase(), ingresosActuales, ingresosRequeridos);
            }
            return String.format("Ingresos suficientes: $%,.0f >= $%,.0f",
                    ingresosActuales, ingresosRequeridos);
        } else {
            if (considerarTamanoMascota) {
                return String.format("Ingresos insuficientes para mascota %s: $%,.0f < $%,.0f",
                        mascota.getTamano().name().toLowerCase(), ingresosActuales, ingresosRequeridos);
            }
            return String.format("Ingresos insuficientes: $%,.0f < $%,.0f",
                    ingresosActuales, ingresosRequeridos);
        }
    }

    // Getters para información de configuración
    public double getIngresosMinimos() {
        return ingresosMinimos;
    }

    public boolean isConsiderarTamanoMascota() {
        return considerarTamanoMascota;
    }
}
