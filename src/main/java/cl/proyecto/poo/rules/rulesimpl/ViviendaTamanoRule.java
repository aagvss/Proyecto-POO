package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

public class ViviendaTamanoRule implements RulesEngine.BiRule {
    @Override
    public RuleResult apply(Adoptante a, Mascota m) {
        // ejemplo simple: si mascota es GRANDE o EXTRA_GRANDE y vivienda != "casa_con_patio" -> fail
        if ((m.getTamano() == Tamano.GRANDE || m.getTamano() == Tamano.EXTRA_GRANDE)
                && !"casa_con_patio".equalsIgnoreCase(a.getTipoVivienda())) {
            return new RuleResult(false, "rule_vivienda_tamano",
                    "Mascota de tamaño " + m.getTamano() + " requiere casa_con_patio");
        }
        return new RuleResult(true, "rule_vivienda_tamano", "Vivienda compatible con tamaño");
    }
}
