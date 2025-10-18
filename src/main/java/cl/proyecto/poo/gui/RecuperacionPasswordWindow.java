package cl.proyecto.poo.gui;

import cl.proyecto.poo.service.EncriptacionService;
import cl.proyecto.poo.service.UsuarioService;
import cl.proyecto.poo.repository.UsuarioRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RecuperacionPasswordWindow extends JFrame {
    private LoginWindow loginWindow;
    private JTextField txtEmail;
    private JButton btnRecuperar;
    private JButton btnVolver;

    private UsuarioService usuarioService;

    public RecuperacionPasswordWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
        inicializarServicios();
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarServicios() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        EncriptacionService encriptacionService = new EncriptacionService();
        this.usuarioService = new UsuarioService(usuarioRepo, encriptacionService);
    }

    private void configurarVentana() {
        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel lblTitulo = new JLabel("RECUPERAR CONTRASEÑA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelPrincipal.add(lblTitulo, gbc);


        JLabel lblInstrucciones = new JLabel(
                "<html><div style='text-align: center;'>Ingrese su email y le enviaremos<br>una contraseña temporal</div></html>",
                SwingConstants.CENTER
        );
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelPrincipal.add(lblInstrucciones, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;


        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtEmail = new JTextField(20);
        panelPrincipal.add(txtEmail, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        btnRecuperar = new JButton("Recuperar Contraseña");
        btnRecuperar.setBackground(new Color(0, 100, 0));
        btnRecuperar.setForeground(Color.WHITE);
        btnRecuperar.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(btnRecuperar, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        btnVolver = new JButton("Volver al Login");
        panelPrincipal.add(btnVolver, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void configurarEventos() {

        btnRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarPassword();
            }
        });


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverALogin();
            }
        });


        txtEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarPassword();
            }
        });
    }

    private void recuperarPassword() {
        String email = txtEmail.getText().trim();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese su email",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Verificar si el email existe
            var usuarioOpt = usuarioService.buscarPorEmail(email);

            if (usuarioOpt.isPresent()) {
                // Generar y asignar contraseña temporal
                String passwordTemporal = usuarioService.generarYAsignarPasswordTemporal(usuarioOpt.get().getId());

                // Simular envío de email
                JOptionPane.showMessageDialog(this,
                        "<html><div style='text-align: center;'>" +
                                "¡Contraseña temporal generada!<br><br>" +
                                "<b>Email:</b> " + email + "<br>" +
                                "<b>Contraseña temporal:</b> " + passwordTemporal + "<br><br>" +
                                "Por seguridad, cambie su contraseña después de ingresar." +
                                "</div></html>",
                        "Contraseña Recuperada",
                        JOptionPane.INFORMATION_MESSAGE);

                volverALogin();

            } else {
                JOptionPane.showMessageDialog(this,
                        "El email ingresado no está registrado en el sistema",
                        "Email no encontrado",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error durante la recuperación: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverALogin() {
        this.dispose();
        loginWindow.mostrarVentana();
    }
}