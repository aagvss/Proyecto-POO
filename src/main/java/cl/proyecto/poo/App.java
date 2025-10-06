package cl.proyecto.poo;

import cl.proyecto.poo.model.*;
import cl.proyecto.poo.repository.*;
import cl.proyecto.poo.rules.RulesEngine;
import cl.proyecto.poo.rules.rulesimpl.EdadMinimaRule;
import cl.proyecto.poo.rules.rulesimpl.IngresosMinimosRule;
import cl.proyecto.poo.rules.rulesimpl.ViviendaTamanoRule;
import cl.proyecto.poo.service.*;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Inicializar repositorios (in-memory)
        AdoptanteRepository adoptanteRepo = new AdoptanteRepository();
        MascotaRepository mascotaRepo = new MascotaRepository();
        SolicitudRepository solicitudRepo = new SolicitudRepository();

        // Servicios
        AdoptanteService adoptanteService = new AdoptanteService(adoptanteRepo);
        MascotaService mascotaService = new MascotaService(mascotaRepo);

        // RulesEngine y registro de reglas
        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerRule(new EdadMinimaRule(18));
        rulesEngine.registerRule(new ViviendaTamanoRule());
        rulesEngine.registerRule(new IngresosMinimosRule(500000));

        SolicitudService solicitudService = new SolicitudService(solicitudRepo, adoptanteService, mascotaService, rulesEngine);

        // Crear datos de ejemplo
        Adoptante maria = new Adoptante("U-001", "María Pérez", "12.345.678-9",
                LocalDate.of(1995,3,10), "departamento", false, 650000);
        adoptanteService.registrarAdoptante(maria);

        Mascota rex = new Mascota("M-001", "Rex", Especie.PERRO, "mestizo", Tamano.GRANDE,
                LocalDate.of(2019, 6, 1), true, true, "saludable");
        mascotaService.registrarMascota(rex);

        // Intento de solicitud (debería requerir revisión o rechazo por vivienda)
        System.out.println("Solicitando adopción de Rex por María...");
        SolicitudAdopcion sol = solicitudService.solicitar(maria.getId(), rex.getId());

        System.out.println("Solicitud creada: " + sol.getId());
        System.out.println("Estado: " + sol.getEstado());
        if (sol.getMotivoRechazo() != null) {
            System.out.println("Motivos / notas: " + sol.getMotivoRechazo());
        }

        // Mostrar ejemplo de mascota pequeña y aprobación
        Mascota luna = new Mascota("M-002", "Luna", Especie.GATO, "siamés", Tamano.PEQUENO,
                LocalDate.of(2021,1,5), true, true, "saludable");
        mascotaService.registrarMascota(luna);

        System.out.println("\nSolicitando adopción de Luna por María...");
        SolicitudAdopcion sol2 = solicitudService.solicitar(maria.getId(), luna.getId());
        System.out.println("Solicitud creada: " + sol2.getId());
        System.out.println("Estado: " + sol2.getEstado());
        if (sol2.getMotivoRechazo() != null) System.out.println("Notas: " + sol2.getMotivoRechazo());

        System.out.println("\n=== Datos del adoptante ===");
        System.out.println(maria);

        System.out.println("\n=== Datos de la mascota Rex ===");
        System.out.println(rex);

        System.out.println("\n=== Solicitud de adopción de Rex ===");
        System.out.println(sol);

        System.out.println("\n=== Datos de la mascota Luna ===");
        System.out.println(luna);

        System.out.println("\n=== Solicitud de adopción de Luna ===");
        System.out.println(sol2);

    }


}
