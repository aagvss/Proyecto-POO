package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class PerfilWindow extends JFrame {

    private final Usuario usuario;
    private final UsuarioService usuarioService;

    public PerfilWindow(Usuario usuario, UsuarioService usuarioService) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;

        setTitle("Perfil de Usuario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Email:"));
        add(new JLabel(usuario.getEmail()));

        add(new JLabel("Rol:"));
        add(new JLabel(usuario.getRol().name()));

        add(new JLabel("Estado:"));
        add(new JLabel(usuario.getEstado().name()));

        add(new JLabel("Fecha creación:"));
        add(new JLabel(usuario.getFechaCreacion().toString()));

        add(new JLabel("Intentos fallidos:"));
        add(new JLabel(String.valueOf(usuario.getIntentosLoginFallidos())));

        add(new JLabel("Nueva contraseña:"));
        JPasswordField txtNueva = new JPasswordField();
        add(txtNueva);

        JButton btnGuardar = new JButton("Actualizar contraseña");
        JButton btnCerrar = new JButton("Cerrar");
        add(btnGuardar);
        add(btnCerrar);

        btnGuardar.addActionListener(e -> {
            try {
                String nueva = new String(txtNueva.getPassword()).trim();
                usuarioService.actualizarPassword(usuario.getId(), nueva);
                JOptionPane.showMessageDialog(this, "Contraseña actualizada correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnCerrar.addActionListener(e -> dispose());
    }
}
