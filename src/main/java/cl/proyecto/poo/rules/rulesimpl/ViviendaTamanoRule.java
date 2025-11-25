package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.model.TipoVivienda;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

public class ViviendaTamanoRule implements RulesEngine.BiRule {

    @Override
    public RuleResult apply(Adoptante adoptante, Mascota mascota) {
        TipoVivienda tipoVivienda = adoptante.getTipoVivienda();
        Tamano tamanoMascota = mascota.getTamano();


        if (tamanoMascota == Tamano.EXTRA_GRANDE) {
            if (tipoVivienda != TipoVivienda.CASA_CON_PATIO_GRANDE &&
                    tipoVivienda != TipoVivienda.SITIO_RURAL) {
                return new RuleResult(false, "rule_vivienda_tamano",
                        "Mascotas extra grandes requieren casa con patio grande o sitio rural");
            }
        }

        if (tamanoMascota == Tamano.GRANDE) {
            if (tipoVivienda == TipoVivienda.DEPARTAMENTO) {
                return new RuleResult(false, "rule_vivienda_tamano",
                        "Mascotas grandes no son recomendables para departamentos");
            }
        }

        if (tamanoMascota == Tamano.MEDIANO) {
            if (tipoVivienda == TipoVivienda.DEPARTAMENTO) {

                return new RuleResult(true, "rule_vivienda_tamano",
                        "Mascota mediana en departamento - requiere paseos frecuentes");
            }
        }

        return new RuleResult(true, "rule_vivienda_tamano",
                "Vivienda " + tipoVivienda.getDescripcion() + " compatible con mascota " + tamanoMascota);
    }
}
