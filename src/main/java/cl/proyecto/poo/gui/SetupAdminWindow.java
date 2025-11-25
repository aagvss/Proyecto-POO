package cl.proyecto.poo.gui;

import cl.proyecto.poo.core.Application;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class SetupAdminWindow extends JFrame {
    private final UsuarioService usuarioService;

    public SetupAdminWindow(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Configuración Inicial del Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInfo = new JLabel("<html><center>Bienvenido.<br>Al ser el primer inicio, debe configurar<br>la cuenta de ADMINISTRADOR.</center></html>");
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblInfo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(new JLabel("Email Admin:"), gbc);
        JTextField txtEmail = new JTextField(20);
        gbc.gridx = 1; panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Contraseña:"), gbc);
        JPasswordField txtPass = new JPasswordField(20);
        gbc.gridx = 1; panel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Confirmar:"), gbc);
        JPasswordField txtConfirm = new JPasswordField(20);
        gbc.gridx = 1; panel.add(txtConfirm, gbc);

        JButton btnCrear = new JButton("Crear Administrador e Iniciar");
        btnCrear.setBackground(new Color(0, 100, 0));
        btnCrear.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(btnCrear, gbc);

        add(panel, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String p1 = new String(txtPass.getPassword());
            String p2 = new String(txtConfirm.getPassword());

            if (email.isEmpty() || p1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos");
                return;
            }
            if (!p1.equals(p2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
                return;
            }

            try {
                usuarioService.registrarUsuario(email, p1, Rol.ADMINISTRADOR, null);

                JOptionPane.showMessageDialog(this, "¡Sistema inicializado correctamente!");
                dispose();
                new LoginWindow().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}