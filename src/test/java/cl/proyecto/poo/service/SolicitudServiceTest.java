package cl.proyecto.poo.service;

import cl.proyecto.poo.model.*;
import cl.proyecto.poo.repository.AdoptanteRepository;
import cl.proyecto.poo.repository.MascotaRepository;
import cl.proyecto.poo.repository.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudServiceTest {
    private SolicitudService solicitudService;
    private MascotaService mascotaService;
    private AdoptanteService adoptanteService;

    @BeforeEach
    void setUp() {
        MascotaRepository mascotaRepo = new MascotaRepository();
        AdoptanteRepository adoptanteRepo = new AdoptanteRepository();
        SolicitudRepository solicitudRepo = new SolicitudRepository();

        mascotaService = new MascotaService(mascotaRepo);
        adoptanteService = new AdoptanteService(adoptanteRepo);
        solicitudService = new SolicitudService(solicitudRepo, adoptanteService, mascotaService);
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta solicitar una mascota ya adoptada")
    void testSolicitarMascotaYaAdoptada() {
        Mascota mascota = new Mascota("M1", "Firulais", Especie.PERRO, "Mestizo",
                Tamano.MEDIANO, LocalDate.now(), true, true, "Sano");
        mascota.setAdoptada(true);
        mascotaService.registrarMascota(mascota);

        Adoptante adoptante = new Adoptante("A1", "Pedro", "111", LocalDate.now().minusYears(30),
                TipoVivienda.CASA_CON_PATIO_GRANDE, true, 800000, "999999999", "Calle 1", "Ingeniero");
        adoptanteService.registrarAdoptante(adoptante);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            solicitudService.solicitar("A1", "M1");
        });

        assertEquals("La mascota ya ha sido adoptada", exception.getMessage());
    }

    @Test
    @DisplayName("Si el adoptante no cumple las reglas, la solicitud queda en REQUIERE_REVISION")
    void testSolicitudConReglasFallidas() {
        Mascota mascota = new Mascota("M2", "Thor", Especie.PERRO, "Pastor",
                Tamano.GRANDE, LocalDate.now(), true, true, "Sano");
        mascotaService.registrarMascota(mascota);

        Adoptante adoptante = new Adoptante("A2", "Ana", "222", LocalDate.now().minusYears(30),
                TipoVivienda.DEPARTAMENTO, true, 900000, "988888888", "Av Siempre Viva", "Doctora");
        adoptanteService.registrarAdoptante(adoptante);

        SolicitudAdopcion solicitud = solicitudService.solicitar("A2", "M2");

        assertEquals(EstadoSolicitud.REQUIERE_REVISION, solicitud.getEstado());
        assertNotNull(solicitud.getMotivoRechazo());

        Mascota mascotaVerificada = mascotaService.buscarPorId("M2").get();
        assertFalse(mascotaVerificada.isAdoptada());

        System.out.println("Motivo del rechazo (Test): " + solicitud.getMotivoRechazo());
    }

}