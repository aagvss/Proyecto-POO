package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.AutenticacionService;
import cl.proyecto.poo.service.EncriptacionService;
import cl.proyecto.poo.service.UsuarioService;
import cl.proyecto.poo.repository.UsuarioRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginWindow extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<Rol> cmbRol;
    private JButton btnIngresar;
    private JButton btnRegistrarse;
    private JButton btnRecuperarPassword;

    // Servicios (temporal)
    private UsuarioService usuarioService;
    private AutenticacionService autenticacionService;

    public LoginWindow() {
        inicializarServicios();
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarServicios() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        EncriptacionService encriptacionService = new EncriptacionService();
        this.usuarioService = new UsuarioService(usuarioRepo, encriptacionService);
        this.autenticacionService = new AutenticacionService(usuarioRepo, encriptacionService);

        // Crear usuario admin por defecto para pruebas
        usuarioService.crearUsuarioAdminPorDefecto();
    }

    private void configurarVentana() {
        setTitle("Sistema de Adopciones - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelPrincipal.add(lblTitulo, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtEmail = new JTextField(20);
        panelPrincipal.add(txtEmail, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtPassword = new JPasswordField(20);
        panelPrincipal.add(txtPassword, gbc);

        // Rol
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        cmbRol = new JComboBox<>(Rol.values());
        panelPrincipal.add(cmbRol, gbc);

        // Botón Ingresar
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 100, 0));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(btnIngresar, gbc);

        // Panel de botones secundarios
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        JPanel panelBotonesSecundarios = new JPanel(new FlowLayout());
        panelBotonesSecundarios.setBackground(Color.WHITE);

        btnRegistrarse = new JButton("Registrarse");
        btnRecuperarPassword = new JButton("¿Olvidó contraseña?");

        panelBotonesSecundarios.add(btnRegistrarse);
        panelBotonesSecundarios.add(btnRecuperarPassword);

        panelPrincipal.add(panelBotonesSecundarios, gbc);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Botón Ingresar
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Botón Registrarse
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });

        // Botón Recuperar Contraseña
        btnRecuperarPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRecuperacionPassword();
            }
        });

        // Enter en campos de texto ejecuta login
        txtPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        Rol rolSeleccionado = (Rol) cmbRol.getSelectedItem();

        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Intentar autenticar
            boolean autenticado = autenticacionService.autenticar(email, password);

            if (autenticado) {
                // Verificar que el rol coincida
                if (autenticacionService.getUsuarioLogueado().get().getRol() == rolSeleccionado) {
                    JOptionPane.showMessageDialog(this,
                            "¡Login exitoso! Bienvenido al sistema.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(this,
                            "El rol seleccionado no coincide con su cuenta",
                            "Error de autenticación",
                            JOptionPane.ERROR_MESSAGE);
                    autenticacionService.logout();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas o cuenta bloqueada",
                        "Error de autenticación",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error durante la autenticación: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        RegistroWindow registroWindow = new RegistroWindow(this);
        registroWindow.setVisible(true);
        this.setVisible(false); // Ocultar login temporalmente
    }

    private void abrirRecuperacionPassword() {
        RecuperacionPasswordWindow recuperacionWindow = new RecuperacionPasswordWindow(this);
        recuperacionWindow.setVisible(true);
        this.setVisible(false); // Ocultar login temporalmente
    }


    public void mostrarVentana() {
        this.setVisible(true);
        // Limpiar campos
        txtPassword.setText("");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }
}
