package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

public class IngresosMinimosRule implements RulesEngine.BiRule {

    private final boolean considerarTamanoMascota;

    public IngresosMinimosRule() {
        this.considerarTamanoMascota = true;
    }

    public IngresosMinimosRule(boolean considerarTamanoMascota) {
        this.considerarTamanoMascota = considerarTamanoMascota;
    }

    @Override
    public RuleResult apply(Adoptante adoptante, Mascota mascota) {
        double ingresosBase = 400000;
        double ingresosRequeridos = calcularIngresosRequeridos(mascota, ingresosBase);
        double ingresosActuales = adoptante.getIngresosMensuales();

        boolean pass = ingresosActuales >= ingresosRequeridos;
        String message = generarMensajeResultado(pass, ingresosActuales, ingresosRequeridos, mascota);

        return new RuleResult(pass, "rule_ingresos_minimos", message);
    }

    private double calcularIngresosRequeridos(Mascota mascota, double ingresosBase) {
        if (!considerarTamanoMascota) {
            return ingresosBase;
        }

        double multiplicador = 1.0;

        switch (mascota.getTamano()) {
            case PEQUENO -> multiplicador = 1.0;
            case MEDIANO -> multiplicador = 1.3;
            case GRANDE -> multiplicador = 1.7;
            case EXTRA_GRANDE -> multiplicador = 2.2;
        }


        if (mascota.getEspecie() == cl.proyecto.poo.model.Especie.PERRO) {
            multiplicador *= 1.2;
        }

        return ingresosBase * multiplicador;
    }

    private String generarMensajeResultado(boolean pass, double ingresosActuales,
                                           double ingresosRequeridos, Mascota mascota) {
        if (pass) {
            return String.format("Ingresos suficientes: $%,.0f >= $%,.0f requeridos para %s %s",
                    ingresosActuales, ingresosRequeridos,
                    mascota.getEspecie().toString().toLowerCase(),
                    mascota.getTamano().toString().toLowerCase());
        } else {
            return String.format("Ingresos insuficientes: $%,.0f < $%,.0f requeridos para %s %s",
                    ingresosActuales, ingresosRequeridos,
                    mascota.getEspecie().toString().toLowerCase(),
                    mascota.getTamano().toString().toLowerCase());
        }
    }
}