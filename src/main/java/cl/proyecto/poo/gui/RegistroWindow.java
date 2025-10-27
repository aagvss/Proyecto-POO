package cl.proyecto.poo.gui;

import cl.proyecto.poo.core.Application;
import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.service.AdoptanteService;
import cl.proyecto.poo.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class RegistroWindow extends JFrame {
    private LoginWindow loginWindow;

    private JComboBox<Rol> cmbTipoUsuario;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;


    private JPanel panelAdoptante;
    private JPanel panelEmpleado;
    private JPanel panelContenedor;

    private JButton btnRegistrar;
    private JButton btnVolver;

    // Servicios
    private UsuarioService usuarioService;

    public RegistroWindow(LoginWindow loginWindow, UsuarioService usuarioService, AdoptanteService adoptanteService) {
        this.loginWindow = loginWindow;
        this.usuarioService = usuarioService;
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
        setVisible(true);
    }



    private void configurarVentana() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());


        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 100, 0));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel principal de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Panel de datos básicos
        JPanel panelBasico = new JPanel(new GridBagLayout());
        panelBasico.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tipo de usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBasico.add(new JLabel("Tipo de Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        cmbTipoUsuario = new JComboBox<>(new Rol[]{Rol.ADOPTANTE, Rol.EMPLEADO});
        cmbTipoUsuario.setPreferredSize(new Dimension(200, 25));
        panelBasico.add(cmbTipoUsuario, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelBasico.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtEmail = new JTextField(20);
        panelBasico.add(txtEmail, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelBasico.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtPassword = new JPasswordField(20);
        panelBasico.add(txtPassword, gbc);

        // Confirmar Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelBasico.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        txtConfirmarPassword = new JPasswordField(20);
        panelBasico.add(txtConfirmarPassword, gbc);

        panelFormulario.add(panelBasico);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel contenedor para formularios específicos
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createTitledBorder("Información Específica"));
        panelContenedor.setPreferredSize(new Dimension(400, 200));

        // Inicializar paneles específicos
        inicializarPanelAdoptante();
        inicializarPanelEmpleado();

        // Mostrar panel por defecto
        mostrarPanelSegunRol((Rol) cmbTipoUsuario.getSelectedItem());

        panelFormulario.add(panelContenedor);
        panelFormulario.add(Box.createVerticalGlue());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(0, 100, 0));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));

        btnVolver = new JButton("Volver al Login");
        btnVolver.setPreferredSize(new Dimension(120, 35));

        panelBotones.add(btnRegistrar);
        panelBotones.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBotones.add(btnVolver);


        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void inicializarPanelAdoptante() {
        panelAdoptante = new JPanel(new GridBagLayout());
        panelAdoptante.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelAdoptante.add(new JLabel("Nombre completo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField txtNombreAdoptante = new JTextField(20);
        panelAdoptante.add(txtNombreAdoptante, gbc);

        // Documento
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelAdoptante.add(new JLabel("Documento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField txtDocumento = new JTextField(20);
        panelAdoptante.add(txtDocumento, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelAdoptante.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField txtTelefono = new JTextField(20);
        panelAdoptante.add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelAdoptante.add(new JLabel("Dirección:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextField txtDireccion = new JTextField(20);
        panelAdoptante.add(txtDireccion, gbc);
    }

    private void inicializarPanelEmpleado() {
        panelEmpleado = new JPanel(new GridBagLayout());
        panelEmpleado.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEmpleado.add(new JLabel("Nombre completo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField txtNombreEmpleado = new JTextField(20);
        panelEmpleado.add(txtNombreEmpleado, gbc);

        // Cargo
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelEmpleado.add(new JLabel("Cargo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField txtCargo = new JTextField(20);
        panelEmpleado.add(txtCargo, gbc);

        // Departamento
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelEmpleado.add(new JLabel("Departamento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField txtDepartamento = new JTextField(20);
        panelEmpleado.add(txtDepartamento, gbc);

        // Fecha contratación
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelEmpleado.add(new JLabel("Fecha contratación:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextField txtFechaContratacion = new JTextField(20);
        panelEmpleado.add(txtFechaContratacion, gbc);
    }

    private void mostrarPanelSegunRol(Rol rol) {
        panelContenedor.removeAll();

        JPanel panelSeleccionado = null;
        switch (rol) {
            case ADOPTANTE:
                panelSeleccionado = panelAdoptante;
                break;
            case EMPLEADO:
                panelSeleccionado = panelEmpleado;
                break;
        }

        if (panelSeleccionado != null) {
            panelContenedor.add(panelSeleccionado, BorderLayout.CENTER);
        }

        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    private void configurarEventos() {
        // Cambio de tipo de usuario
        cmbTipoUsuario.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    mostrarPanelSegunRol((Rol) e.getItem());
                }
            }
        });


        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRegistro();
            }
        });

        // Botón Volver
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverALogin();
            }
        });
    }

    private void realizarRegistro() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmarPassword = new String(txtConfirmarPassword.getPassword());
        Rol rol = (Rol) cmbTipoUsuario.getSelectedItem();

        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmarPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Las contraseñas no coinciden",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 6 caracteres",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String adoptanteId = null;

            if (rol == Rol.ADOPTANTE) {
                // OBTENER DATOS DEL FORMULARIO ADOPTANTE
                JTextField txtNombreAdoptante = (JTextField) panelAdoptante.getComponent(1);
                JTextField txtDocumento = (JTextField) panelAdoptante.getComponent(3);
                JTextField txtTelefono = (JTextField) panelAdoptante.getComponent(5);
                JTextField txtDireccion = (JTextField) panelAdoptante.getComponent(7);

                String nombre = txtNombreAdoptante.getText().trim();
                String documento = txtDocumento.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();

                // Validar campos obligatorios para adoptante
                if (nombre.isEmpty() || documento.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Los campos nombre y documento son obligatorios para adoptantes",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // CREAR ADOPTANTE
                adoptanteId = "ADOP-" + System.currentTimeMillis();
                Adoptante adoptante = new Adoptante(
                        adoptanteId, nombre, documento,
                        java.time.LocalDate.now().minusYears(25), // Fecha nacimiento ejemplo
                        direccion, false, 800000 // Valores por defecto
                );

                // GUARDAR ADOPTANTE
                Application.getAdoptanteService().registrarAdoptante(adoptante);
            }

            // Registrar usuario
            usuarioService.registrarUsuario(email, password, rol, adoptanteId);
            //  Esto crea el Usuario, pero si es ADOPTANTE, falta crear el Adoptante

            JOptionPane.showMessageDialog(this,
                    "¡Usuario registrado exitosamente!\nAhora puede iniciar sesión.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            volverALogin();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error en el registro: " + ex.getMessage(),
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverALogin() {
        this.dispose();
        loginWindow.mostrarVentana();
    }
}