package cl.proyecto.poo.rules;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;

import java.util.ArrayList;
import java.util.List;

public class RulesEngine {
    private final List<BiRule> rules = new ArrayList<>();

    public void registerRule(BiRule rule) {
        rules.add(rule);
        System.out.println("Regla registrada: " + rule.getClass().getSimpleName());
    }

    public int getRuleCount() {
        return rules.size();
    }

    public List<RuleResult> evaluate(Adoptante adoptante, Mascota mascota) {
        List<RuleResult> results = new ArrayList<>();
        System.out.println("Evaluando " + rules.size() + " reglas para adopción...");

        for (BiRule rule : rules) {
            RuleResult result = rule.apply(adoptante, mascota);
            results.add(result);
            System.out.println(rule.getClass().getSimpleName() + ": " +
                    (result.isPass() ? "APROBADO" : "RECHAZADO") + " - " + result.getMessage());
        }

        return results;
    }

    public interface BiRule {
        RuleResult apply(Adoptante a, Mascota m);
    }
}
