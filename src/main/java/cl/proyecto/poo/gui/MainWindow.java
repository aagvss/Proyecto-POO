package cl.proyecto.poo.gui;

import cl.proyecto.poo.core.Application;
import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.AdoptanteService;
import cl.proyecto.poo.service.MascotaService;
import cl.proyecto.poo.service.UsuarioService;
import cl.proyecto.poo.repository.MascotaRepository;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final Usuario usuarioActual;
    private final UsuarioService usuarioService;
    private final MascotaRepository mascotaRepository;
    private final MascotaService mascotaService;

    public MainWindow(Usuario usuarioActual, UsuarioService usuarioService,
                      MascotaRepository mascotaRepository, AdoptanteService adoptanteService, MascotaService mascotaService) {
        this.usuarioActual = usuarioActual;
        this.usuarioService = usuarioService;
        this.mascotaRepository = mascotaRepository;
        this.mascotaService = mascotaService;

        setTitle("Sistema Hogar Responsable - Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblBienvenido = new JLabel("Bienvenido/a: " + usuarioActual.getEmail(), SwingConstants.CENTER);
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblBienvenido, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton btnPerfil = new JButton("Ver Perfil");
        JButton btnMascotas = new JButton("Ver Mascotas Disponibles");
        JButton btnSalir = new JButton("Cerrar Sesión");

        panelBotones.add(btnPerfil);
        panelBotones.add(btnMascotas);

        if (usuarioActual.getRol() == Rol.ADMINISTRADOR || usuarioActual.getRol() == Rol.EMPLEADO) {
            JButton btnAdmin = new JButton("Administrar Usuarios");
            panelBotones.add(btnAdmin);
            btnAdmin.addActionListener(e -> {
                new AdminPanelUsuarios(usuarioService).setVisible(true);
            });

            JButton btnMascotasAdoptadas = new JButton("Ver Mascotas Adoptadas");
            panelBotones.add(btnMascotasAdoptadas);
            btnMascotasAdoptadas.addActionListener(e -> {
                new MascotasAdoptadasWindow(
                        Application.getMascotaService(), Application.getAdoptanteService()
                ).setVisible(true);
            });

            JButton btnGestionSolicitudes = new JButton("Gestionar Solicitudes");
            panelBotones.add(btnGestionSolicitudes);

            btnGestionSolicitudes.addActionListener(e -> {
                new GestionSolicitudesWindow(
                        Application.getSolicitudService(),
                        Application.getAdoptanteService(),
                        Application.getMascotaService()
                ).setVisible(true);
            });
        }

        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.CENTER);

        btnPerfil.addActionListener(e -> new PerfilWindow(usuarioActual, usuarioService).setVisible(true));

        btnSalir.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
            new LoginWindow().setVisible(true);
        });



        btnMascotas.addActionListener(e -> {
            if (usuarioActual.getRol() == Rol.ADOPTANTE) {
                // Para adoptantes: con botón adoptar
                new ListaMascotasWindow(
                        Application.getMascotaRepository(),
                        Application.getSolicitudService(),
                        usuarioActual
                ).setVisible(true);
            } else {
                // Para empleados/admin: solo ver
                new ListaMascotasWindow(Application.getMascotaRepository()).setVisible(true);
            }
        });

        if (usuarioActual.getRol() == Rol.EMPLEADO || usuarioActual.getRol() == Rol.ADMINISTRADOR) {
            JButton btnAgregarMascota = new JButton("Agregar Mascota");
            panelBotones.add(btnAgregarMascota);

            btnAgregarMascota.addActionListener(e -> {
                new AgregarMascotaWindow(mascotaService).setVisible(true);
            });
        }


    }
}
