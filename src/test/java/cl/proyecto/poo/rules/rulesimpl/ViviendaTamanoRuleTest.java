package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.*;
import cl.proyecto.poo.rules.RuleResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ViviendaTamanoRuleTest {
    @Test
    void testPerroGrandeEnDepartamentoFalla() {
        // Adoptante vive en Depto
        Adoptante adoptante = new Adoptante("1", "Ana", "123", LocalDate.now().minusYears(25),
                TipoVivienda.DEPARTAMENTO, false, 1000000, "", "", "");

        // Mascota es Grande
        Mascota mascota = new Mascota("M1", "Rex", Especie.PERRO, "Pastor",
                Tamano.GRANDE, LocalDate.now(), true, true, "Sano");

        ViviendaTamanoRule rule = new ViviendaTamanoRule();
        RuleResult result = rule.apply(adoptante, mascota);

        assertFalse(result.isPass());
        assertTrue(result.getMessage().contains("no son recomendables para departamentos"));
    }

}