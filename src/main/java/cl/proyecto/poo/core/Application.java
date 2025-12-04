package cl.proyecto.poo.core;

import cl.proyecto.poo.gui.LoginWindow;
import cl.proyecto.poo.gui.SetupAdminWindow;
import cl.proyecto.poo.repository.*;
import cl.proyecto.poo.service.*;
import cl.proyecto.poo.model.*;
import cl.proyecto.poo.rules.*;
import cl.proyecto.poo.rules.rulesimpl.*;

import javax.swing.*;
import java.time.LocalDate;

public class Application {
    private static UsuarioService usuarioService;
    private static AutenticacionService autenticacionService;
    private static MascotaRepository mascotaRepository;
    private static AdoptanteService adoptanteService;
    private static SolicitudService solicitudService;
    private static SolicitudRepository solicitudRepository;
    private static MascotaService mascotaService;

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        initializeServices();
        if (usuarioService.listarTodos().isEmpty()) {

            SwingUtilities.invokeLater(() -> {
                new SetupAdminWindow(usuarioService).setVisible(true);
            });
        } else {
            loadInitialData();
            showLogin();
        }
    }

    private static void initializeServices() {
        EncriptacionService encriptacionService = new EncriptacionService();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        mascotaRepository = new MascotaRepository();
        AdoptanteRepository adoptanteRepository = new AdoptanteRepository();
        solicitudRepository = new SolicitudRepository();

        usuarioService = new UsuarioService(usuarioRepository, encriptacionService);
        autenticacionService = new AutenticacionService(usuarioRepository, encriptacionService);
        adoptanteService = new AdoptanteService(adoptanteRepository);


        mascotaService = new MascotaService(mascotaRepository);
        solicitudService = new SolicitudService(solicitudRepository, adoptanteService, mascotaService);
    }


    private static RulesEngine crearRulesEngine() {
        RulesEngine engine = new RulesEngine();
        engine.registerRule(new EdadMinimaRule(18));
        engine.registerRule(new ViviendaTamanoRule());
        engine.registerRule(new IngresosMinimosRule(true));
        return engine;
    }

    private static void loadInitialData() {


        if (mascotaRepository.findAll().isEmpty()) {
            mascotaRepository.save(new Mascota("M-001", "Luna", Especie.PERRO, "Mestizo", Tamano.MEDIANO,
                    LocalDate.of(2021, 3, 15), true, true, "Saludable"));
            mascotaRepository.save(new Mascota("M-002", "Michi", Especie.GATO, "Siames", Tamano.PEQUENO,
                    LocalDate.of(2022, 6, 2), true, false, "En tratamiento"));

            Mascota mascotaAdoptada = new Mascota("M-003", "Firulais", Especie.PERRO, "Labrador", Tamano.GRANDE,
                    LocalDate.of(2020, 1, 10), true, true, "Saludable");
            mascotaAdoptada.setAdoptada(true);
            mascotaAdoptada.setAdoptanteId("ADOP-EJEMPLO");
            mascotaRepository.save(mascotaAdoptada);
        }


    }

    private static void showLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        });
    }

    public static MascotaService getMascotaService() {
        return mascotaService;
    }

    public static UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public static AutenticacionService getAutenticacionService() {
        return autenticacionService;
    }

    public static MascotaRepository getMascotaRepository() {
        return mascotaRepository;
    }

    public static AdoptanteService getAdoptanteService() {
        return adoptanteService; // GETTER AGREGADO
    }

    public static SolicitudService getSolicitudService() {
        return solicitudService;
    }

    public static SolicitudRepository getSolicitudRepository() {
        return solicitudRepository;
    }
}