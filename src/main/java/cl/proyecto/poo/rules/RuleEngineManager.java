package cl.proyecto.poo.rules;

import cl.proyecto.poo.rules.rulesimpl.*;

public class RuleEngineManager {
    private static RulesEngine instance;

    public static RulesEngine getInstance() {
        if (instance == null) {
            instance = createRulesEngine();
        }
        return instance;
    }

    private static RulesEngine createRulesEngine() {
        RulesEngine engine = new RulesEngine();


        engine.registerRule(new EdadMinimaRule(18));
        engine.registerRule(new ViviendaTamanoRule());
        engine.registerRule(new IngresosMinimosRule(true));


        System.out.println("Motor de reglas inicializado con " + engine.getRuleCount() + " reglas");
        return engine;
    }

    public static void reloadRules() {
        instance = createRulesEngine();
    }
}