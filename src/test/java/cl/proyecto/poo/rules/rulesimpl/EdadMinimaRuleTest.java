package cl.proyecto.poo.rules.rulesimpl;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.rules.RuleResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EdadMinimaRuleTest {

    @Test
    @DisplayName("Debe rechazar si el adoptante es menor de 18 años")
    void testMenorDeEdad() {
        Adoptante menor = new Adoptante("1", "Juan", "123",
                LocalDate.now().minusYears(17),
                null, false, 0, "", "", "");

        Mascota mascota = new Mascota("M1", "Dog", null, null, null, null, false, false, "");
        EdadMinimaRule regla = new EdadMinimaRule(18);

        RuleResult resultado = regla.apply(menor, mascota);

        assertFalse(resultado.isPass(), "El test debería fallar por ser menor de edad");
        assertEquals("rule_edad_minima", resultado.getRuleId());
    }

    @Test
    @DisplayName("Debe aprobar si el adoptante tiene 18 años o más")
    void testMayorDeEdad() {
        Adoptante mayor = new Adoptante("1", "Pedro", "123",
                LocalDate.now().minusYears(18),
                null, false, 0, "", "", "");

        Mascota mascota = new Mascota("M1", "Dog", null, null, null, null, false, false, "");
        EdadMinimaRule regla = new EdadMinimaRule(18);

        assertTrue(regla.apply(mayor, mascota).isPass());
    }
}