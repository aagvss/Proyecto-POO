package cl.proyecto.poo.core;

import cl.proyecto.poo.gui.LoginWindow;
import cl.proyecto.poo.repository.*;
import cl.proyecto.poo.service.*;
import cl.proyecto.poo.model.*;
import javax.swing.*;
import java.time.LocalDate;

public class Application {
    private static UsuarioService usuarioService;
    private static AutenticacionService autenticacionService;
    private static MascotaRepository mascotaRepository;
    private static AdoptanteService adoptanteService;

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        initializeServices();
        loadInitialData();
        showLogin();
    }

    private static void initializeServices() {
        EncriptacionService encriptacionService = new EncriptacionService();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        mascotaRepository = new MascotaRepository();
        AdoptanteRepository adoptanteRepository = new AdoptanteRepository();

        usuarioService = new UsuarioService(usuarioRepository, encriptacionService);
        autenticacionService = new AutenticacionService(usuarioRepository, encriptacionService);
        adoptanteService = new AdoptanteService(adoptanteRepository);
    }

    private static void loadInitialData() {
        usuarioService.crearUsuarioAdminPorDefecto();

        mascotaRepository.save(new Mascota("M-001", "Luna", Especie.PERRO, "Mestizo", Tamano.MEDIANO,
                LocalDate.of(2021, 3, 15), true, true, "Saludable"));
        mascotaRepository.save(new Mascota("M-002", "Michi", Especie.GATO, "Siames", Tamano.PEQUENO,
                LocalDate.of(2022, 6, 2), true, false, "En tratamiento"));
        mascotaRepository.save(new Mascota("M-003", "Rocky", Especie.PERRO, "Pitbull", Tamano.GRANDE,
                LocalDate.of(2020, 11, 9), true, true, "Excelente"));
    }

    private static void showLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        });
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
        return adoptanteService;
    }
}