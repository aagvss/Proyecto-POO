package cl.proyecto.poo.rules;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor simple: mantiene una lista de reglas (implementadas como lambdas/objetos)
 * y las ejecuta secuencialmente. Devuelve lista de RuleResult.
 */
public class RulesEngine {
    private final List<BiRule> rules = new ArrayList<>();

    public void registerRule(BiRule rule) {
        rules.add(rule);
    }

    public List<RuleResult> evaluate(Adoptante adoptante, Mascota mascota) {
        List<RuleResult> results = new ArrayList<>();
        for (BiRule r : rules) {
            results.add(r.apply(adoptante, mascota));
        }
        return results;
    }

    // Interface funcional para reglas que usan adoptante + mascota
    public interface BiRule {
        RuleResult apply(Adoptante a, Mascota m);
    }
}
