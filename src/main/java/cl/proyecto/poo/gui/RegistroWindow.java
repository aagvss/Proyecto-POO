package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.model.TipoVivienda;
import cl.proyecto.poo.service.AdoptanteService;
import cl.proyecto.poo.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;

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
    private AdoptanteService adoptanteService;


    private JTextField txtNombreAdoptante;
    private JTextField txtDocumentoAdoptante;
    private JTextField txtTelefonoAdoptante;
    private JTextField txtDireccionAdoptante;
    private JTextField txtOcupacionAdoptante;
    private JTextField txtDiaNacimiento;
    private JTextField txtMesNacimiento;
    private JTextField txtAnioNacimiento;
    private JTextField txtIngresosMensuales;
    private JComboBox<TipoVivienda> cmbTipoVivienda;
    private JRadioButton rdSiPropietario;
    private JRadioButton rdNoPropietario;
    private ButtonGroup buttonGroupPropietario;

    public RegistroWindow(LoginWindow loginWindow, UsuarioService usuarioService, AdoptanteService adoptanteService) {
        this.loginWindow = loginWindow;
        this.usuarioService = usuarioService;
        this.adoptanteService = adoptanteService;
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 700);
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
        panelBasico.add(new JLabel("Email:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtEmail = new JTextField(20);
        panelBasico.add(txtEmail, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelBasico.add(new JLabel("Contraseña:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtPassword = new JPasswordField(20);
        panelBasico.add(txtPassword, gbc);

        // Confirmar Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelBasico.add(new JLabel("Confirmar Contraseña:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        txtConfirmarPassword = new JPasswordField(20);
        panelBasico.add(txtConfirmarPassword, gbc);

        panelFormulario.add(panelBasico);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel contenedor para formularios específicos
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBorder(BorderFactory.createTitledBorder("Información Específica"));
        panelContenedor.setPreferredSize(new Dimension(400, 300));

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
        btnRegistrar.setBackground(new Color(0, 150, 0));
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 80, 0), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
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

        int row = 0;

        // Nombre completo
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Nombre completo:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtNombreAdoptante = new JTextField(20);
        panelAdoptante.add(txtNombreAdoptante, gbc);
        row++;

        // Documento
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Documento:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtDocumentoAdoptante = new JTextField(20);
        panelAdoptante.add(txtDocumentoAdoptante, gbc);
        row++;

        // Fecha de nacimiento
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Fecha nacimiento:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtDiaNacimiento = new JTextField(2);
        txtDiaNacimiento.setToolTipText("Día (1-31)");
        txtMesNacimiento = new JTextField(2);
        txtMesNacimiento.setToolTipText("Mes (1-12)");
        txtAnioNacimiento = new JTextField(4);
        txtAnioNacimiento.setToolTipText("Año (ej: 1990)");

        panelFecha.add(txtDiaNacimiento);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(txtMesNacimiento);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(txtAnioNacimiento);
        panelFecha.add(new JLabel(" (dd/mm/aaaa)"));
        panelAdoptante.add(panelFecha, gbc);
        row++;

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Teléfono:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtTelefonoAdoptante = new JTextField(20);
        panelAdoptante.add(txtTelefonoAdoptante, gbc);
        row++;

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Dirección:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtDireccionAdoptante = new JTextField(20);
        panelAdoptante.add(txtDireccionAdoptante, gbc);
        row++;

        // Ocupación
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Ocupación:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtOcupacionAdoptante = new JTextField(20);
        panelAdoptante.add(txtOcupacionAdoptante, gbc);
        row++;

        // Tipo de vivienda
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Tipo de vivienda:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        cmbTipoVivienda = new JComboBox<>(TipoVivienda.values());
        cmbTipoVivienda.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoVivienda) {
                    setText(((TipoVivienda) value).getDescripcion());
                }
                return this;
            }
        });
        panelAdoptante.add(cmbTipoVivienda, gbc);
        row++;

        // ¿Es propietario?
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("¿Es propietario?*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JPanel panelPropietario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonGroupPropietario = new ButtonGroup();
        rdSiPropietario = new JRadioButton("Sí");
        rdNoPropietario = new JRadioButton("No");
        rdNoPropietario.setSelected(true);
        buttonGroupPropietario.add(rdSiPropietario);
        buttonGroupPropietario.add(rdNoPropietario);
        panelPropietario.add(rdSiPropietario);
        panelPropietario.add(rdNoPropietario);
        panelAdoptante.add(panelPropietario, gbc);
        row++;

        // Ingresos mensuales
        gbc.gridx = 0;
        gbc.gridy = row;
        panelAdoptante.add(new JLabel("Ingresos mensuales:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JPanel panelIngresos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtIngresosMensuales = new JTextField(10);
        txtIngresosMensuales.setToolTipText("Ingrese solo números");
        panelIngresos.add(txtIngresosMensuales);
        panelIngresos.add(new JLabel("$"));
        panelAdoptante.add(panelIngresos, gbc);
        row++;

        // Información adicional
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("<html><small><i>* Campos obligatorios. Los datos serán usados para evaluar su idoneidad como adoptante.</i></small></html>");
        lblInfo.setForeground(Color.GRAY);
        panelAdoptante.add(lblInfo, gbc);
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

                if (!validarCamposAdoptante()) {
                    return;
                }

                adoptanteId = "ADOP-" + System.currentTimeMillis();

                // Crear fecha de nacimiento
                LocalDate fechaNacimiento = crearFechaNacimiento();
                if (fechaNacimiento == null) {
                    JOptionPane.showMessageDialog(this,
                            "Fecha de nacimiento inválida",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Adoptante nuevoAdoptante = new Adoptante(
                        adoptanteId,
                        txtNombreAdoptante.getText().trim(),
                        txtDocumentoAdoptante.getText().trim(),
                        fechaNacimiento,
                        (TipoVivienda) cmbTipoVivienda.getSelectedItem(),
                        rdSiPropietario.isSelected(),
                        Double.parseDouble(txtIngresosMensuales.getText().trim()),
                        txtTelefonoAdoptante.getText().trim(),
                        txtDireccionAdoptante.getText().trim(),
                        txtOcupacionAdoptante.getText().trim()
                );

                adoptanteService.registrarAdoptante(nuevoAdoptante);
            }

            // Registrar usuario
            usuarioService.registrarUsuario(email, password, rol, adoptanteId);

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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCamposAdoptante() {
        // Validar campos obligatorios
        if (txtNombreAdoptante.getText().trim().isEmpty() ||
                txtDocumentoAdoptante.getText().trim().isEmpty() ||
                txtTelefonoAdoptante.getText().trim().isEmpty() ||
                txtDireccionAdoptante.getText().trim().isEmpty() ||
                txtOcupacionAdoptante.getText().trim().isEmpty() ||
                txtIngresosMensuales.getText().trim().isEmpty() ||
                txtDiaNacimiento.getText().trim().isEmpty() ||
                txtMesNacimiento.getText().trim().isEmpty() ||
                txtAnioNacimiento.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios marcados con *",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validar ingresos (solo números)
        try {
            double ingresos = Double.parseDouble(txtIngresosMensuales.getText().trim());
            if (ingresos <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Los ingresos mensuales deben ser mayores a 0",
                        "Error de validación",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un valor numérico válido para los ingresos mensuales",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private LocalDate crearFechaNacimiento() {
        try {
            int dia = Integer.parseInt(txtDiaNacimiento.getText().trim());
            int mes = Integer.parseInt(txtMesNacimiento.getText().trim());
            int anio = Integer.parseInt(txtAnioNacimiento.getText().trim());

            // Validaciones básicas de fecha
            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 1900 || anio > LocalDate.now().getYear()) {
                return null;
            }

            return LocalDate.of(anio, mes, dia);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void volverALogin() {
        this.dispose();
        loginWindow.mostrarVentana();
    }
}