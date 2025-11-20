package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Especie;
import cl.proyecto.poo.model.Tamano;
import cl.proyecto.poo.service.MascotaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AgregarMascotaWindow extends JFrame {
    private final MascotaService mascotaService;

    private JTextField txtNombre;
    private JComboBox<Especie> cmbEspecie;
    private JTextField txtRaza;
    private JComboBox<Tamano> cmbTamano;
    private JTextField txtFechaNacimiento;
    private JCheckBox chkVacunado;
    private JCheckBox chkEsterilizado;
    private JTextArea txtEstadoSalud;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public AgregarMascotaWindow(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Agregar Nueva Mascota");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenido.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitulo = new JLabel("AGREGAR NUEVA MASCOTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelContenido.add(lblTitulo, gbc);

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;

        int row = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Nombre:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtNombre = new JTextField(20);
        panelContenido.add(txtNombre, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Especie:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        cmbEspecie = new JComboBox<>(Especie.values());
        cmbEspecie.setPreferredSize(new Dimension(200, 25));
        panelContenido.add(cmbEspecie, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Raza:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtRaza = new JTextField(20);
        panelContenido.add(txtRaza, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Tamaño:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        cmbTamano = new JComboBox<>(Tamano.values());
        cmbTamano.setPreferredSize(new Dimension(200, 25));
        panelContenido.add(cmbTamano, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Fecha Nacimiento:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        txtFechaNacimiento = new JTextField(10);
        txtFechaNacimiento.setToolTipText("Formato: AAAA-MM-DD (ej: 2022-05-15)");
        panelFecha.add(txtFechaNacimiento);
        panelFecha.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFecha.add(new JLabel("(AAAA-MM-DD)"));
        panelContenido.add(panelFecha, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Vacunado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        chkVacunado = new JCheckBox("Sí");
        panelContenido.add(chkVacunado, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelContenido.add(new JLabel("Esterilizado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        chkEsterilizado = new JCheckBox("Sí");
        panelContenido.add(chkEsterilizado, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelContenido.add(new JLabel("Estado de Salud:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtEstadoSalud = new JTextArea(3, 20);
        txtEstadoSalud.setLineWrap(true);
        txtEstadoSalud.setWrapStyleWord(true);
        JScrollPane scrollEstadoSalud = new JScrollPane(txtEstadoSalud);
        scrollEstadoSalud.setPreferredSize(new Dimension(200, 60));
        panelContenido.add(scrollEstadoSalud, gbc);
        row++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("<html><small><i>* Campos obligatorios</i></small></html>");
        lblInfo.setForeground(Color.GRAY);
        panelContenido.add(lblInfo, gbc);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        btnGuardar = new JButton("Guardar Mascota");
        btnGuardar.setBackground(new Color(0, 120, 0));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setPreferredSize(new Dimension(140, 35));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMascota();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        txtNombre.addActionListener(e -> guardarMascota());
        txtRaza.addActionListener(e -> guardarMascota());
        txtFechaNacimiento.addActionListener(e -> guardarMascota());
    }

    private void guardarMascota() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtRaza.getText().trim().isEmpty() ||
                txtFechaNacimiento.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios (*)",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate fechaNacimiento = LocalDate.parse(txtFechaNacimiento.getText().trim());

            if (fechaNacimiento.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                        "La fecha de nacimiento no puede ser futura",
                        "Error de validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String mascotaId = "M-" + System.currentTimeMillis();

            Mascota nuevaMascota = new Mascota(
                    mascotaId,
                    txtNombre.getText().trim(),
                    (Especie) cmbEspecie.getSelectedItem(),
                    txtRaza.getText().trim(),
                    (Tamano) cmbTamano.getSelectedItem(),
                    fechaNacimiento,
                    chkVacunado.isSelected(),
                    chkEsterilizado.isSelected(),
                    txtEstadoSalud.getText().trim().isEmpty() ? "Saludable" : txtEstadoSalud.getText().trim()
            );

            mascotaService.registrarMascota(nuevaMascota);

            JOptionPane.showMessageDialog(this,
                    "¡Mascota registrada exitosamente!\n\n" +
                            "ID: " + mascotaId + "\n" +
                            "Nombre: " + nuevaMascota.getNombre() + "\n" +
                            "Especie: " + nuevaMascota.getEspecie(),
                    "Mascota Guardada",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use AAAA-MM-DD (ej: 2022-05-15)",
                    "Error de fecha",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar mascota: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtRaza.setText("");
        txtFechaNacimiento.setText("");
        chkVacunado.setSelected(false);
        chkEsterilizado.setSelected(false);
        txtEstadoSalud.setText("");
        cmbEspecie.setSelectedIndex(0);
        cmbTamano.setSelectedIndex(0);
        txtNombre.requestFocus();
    }
}