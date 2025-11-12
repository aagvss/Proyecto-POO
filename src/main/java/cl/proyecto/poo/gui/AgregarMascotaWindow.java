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
        setSize(500, 500);
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
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitulo = new JLabel("AGREGAR NUEVA MASCOTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelPrincipal.add(lblTitulo, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;

        int row = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Nombre:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtNombre = new JTextField(20);
        panelPrincipal.add(txtNombre, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Especie:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        cmbEspecie = new JComboBox<>(Especie.values());
        panelPrincipal.add(cmbEspecie, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Raza:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtRaza = new JTextField(20);
        panelPrincipal.add(txtRaza, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Tamaño:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        cmbTamano = new JComboBox<>(Tamano.values());
        panelPrincipal.add(cmbTamano, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Fecha Nacimiento:*"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtFechaNacimiento = new JTextField(10);
        txtFechaNacimiento.setToolTipText("Formato: AAAA-MM-DD (ej: 2022-05-15)");
        panelFecha.add(txtFechaNacimiento);
        panelFecha.add(new JLabel(" (AAAA-MM-DD)"));
        panelPrincipal.add(panelFecha, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Vacunado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        chkVacunado = new JCheckBox("Sí");
        panelPrincipal.add(chkVacunado, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Esterilizado:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        chkEsterilizado = new JCheckBox("Sí");
        panelPrincipal.add(chkEsterilizado, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        panelPrincipal.add(new JLabel("Estado de Salud:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        txtEstadoSalud = new JTextArea(3, 20);
        txtEstadoSalud.setLineWrap(true);
        txtEstadoSalud.setWrapStyleWord(true);
        JScrollPane scrollEstadoSalud = new JScrollPane(txtEstadoSalud);
        panelPrincipal.add(scrollEstadoSalud, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("<html><small><i>* Campos obligatorios</i></small></html>");
        lblInfo.setForeground(Color.GRAY);
        panelPrincipal.add(lblInfo, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGuardar = new JButton("Guardar Mascota");
        btnGuardar.setBackground(new Color(0, 120, 0));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setPreferredSize(new Dimension(140, 35));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, gbc);

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