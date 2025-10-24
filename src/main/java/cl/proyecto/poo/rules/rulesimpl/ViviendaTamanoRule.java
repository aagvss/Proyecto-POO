package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.model.TipoVivienda;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

public class ViviendaTamanoRule implements RulesEngine.BiRule {

    @Override
    public RuleResult apply(Adoptante a, Mascota m) {

        if (m.getTamano() == Tamano.EXTRA_GRANDE) {
            if (a.getTipoVivienda() != TipoVivienda.CASA_CON_PATIO_GRANDE &&
                    a.getTipoVivienda() != TipoVivienda.SITIO_RURAL) {
                return new RuleResult(false, "rule_vivienda_tamano",
                        "Mascotas extra grandes requieren casa con patio grande o sitio rural");
            }
        }

        if (m.getTamano() == Tamano.GRANDE) {
            if (a.getTipoVivienda() == TipoVivienda.DEPARTAMENTO) {
                return new RuleResult(false, "rule_vivienda_tamano",
                        "Mascotas grandes no son recomendables para departamentos");
            }
        }

        return new RuleResult(true, "rule_vivienda_tamano",
                "Vivienda " + a.getTipoVivienda().getDescripcion() + " compatible con mascota " + m.getTamano());
    }
}
