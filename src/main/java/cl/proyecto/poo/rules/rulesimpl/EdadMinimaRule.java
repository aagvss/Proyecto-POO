package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.rules.RuleResult;
import cl.proyecto.poo.rules.RulesEngine;

import java.time.LocalDate;

public class EdadMinimaRule implements RulesEngine.BiRule {
    private final int edadMinima;

    public EdadMinimaRule(int edadMinima) { this.edadMinima = edadMinima; }

    @Override
    public RuleResult apply(Adoptante a, Mascota m) {
        int edad = a.getEdad();
        boolean pass = edad >= edadMinima;
        String msg = pass ? "Edad suficiente" : "Edad insuficiente: " + edad + " < " + edadMinima;
        return new RuleResult(pass, "rule_edad_minima", msg);
    }
}
