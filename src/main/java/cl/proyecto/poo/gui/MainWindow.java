package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.AdoptanteService;
import cl.proyecto.poo.service.UsuarioService;
import cl.proyecto.poo.repository.MascotaRepository;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final Usuario usuarioActual;
    private final UsuarioService usuarioService;
    private final MascotaRepository mascotaRepository;

    public MainWindow(Usuario usuarioActual, UsuarioService usuarioService, MascotaRepository mascotaRepository, AdoptanteService adoptanteService) {
        this.usuarioActual = usuarioActual;
        this.usuarioService = usuarioService;
        this.mascotaRepository = mascotaRepository;

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

        if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
            JButton btnAdmin = new JButton("Administrar Usuarios");
            panelBotones.add(btnAdmin);
            btnAdmin.addActionListener(e -> {
                new AdminPanelUsuarios(usuarioService).setVisible(true);
            });
        }

        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.CENTER);

        btnPerfil.addActionListener(e -> new PerfilWindow(usuarioActual, usuarioService).setVisible(true));
        btnMascotas.addActionListener(e -> {
            new ListaMascotasWindow(mascotaRepository).setVisible(true);
        });        btnSalir.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
            new LoginWindow().setVisible(true);
        });
    }
}
