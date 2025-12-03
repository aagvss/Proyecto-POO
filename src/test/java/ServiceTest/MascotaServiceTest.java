package ServiceTest;

import RepositoryTest.MascotaRepositoryStub;
import cl.proyecto.poo.model.Especie;
import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.service.MascotaService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MascotaServiceTest {

    @Test
    void testRegistrarMascota_Exito_ConStub() {
        // 1. PREPARACIÓN: Crear el Stub y el Servicio usando el Stub
        MascotaRepositoryStub repoStub = new MascotaRepositoryStub();
        MascotaService mascotaService = new MascotaService(repoStub);

        Mascota nuevaMascota = new Mascota(
                "M-TEST", "Boli", Especie.GATO, "Mestizo", Tamano.PEQUENO,
                LocalDate.of(2023, 1, 1), true, true, "Saludable"
        );

        // 2. EJECUCIÓN
        mascotaService.registrarMascota(nuevaMascota);

        // 3. VERIFICACIÓN: Usar los métodos del Stub
        assertTrue(repoStub.fueLlamadoSave(),
                "El método save() del repositorio debe ser llamado");
        assertEquals(nuevaMascota, repoStub.obtenerMascotaGuardada(),
                "La mascota guardada debe ser la misma que la registrada");
    }
}