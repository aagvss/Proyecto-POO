package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.SolicitudAdopcion;
import cl.proyecto.poo.model.EstadoSolicitud;
import cl.proyecto.poo.service.AdoptanteService;
import cl.proyecto.poo.service.MascotaService;
import cl.proyecto.poo.service.SolicitudService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionSolicitudesWindow extends JFrame {

    private final SolicitudService solicitudService;
    private final AdoptanteService adoptanteService;
    private final MascotaService mascotaService;
    private JTable tabla;
    private DefaultTableModel model;

    public GestionSolicitudesWindow(SolicitudService solicitudService,
                                    AdoptanteService adoptanteService,
                                    MascotaService mascotaService) {
        this.solicitudService = solicitudService;
        this.adoptanteService = adoptanteService;
        this.mascotaService = mascotaService;

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Solicitudes de Adopción");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        String[] columnas = {"ID", "Fecha", "Adoptante", "Mascota", "Estado", "Motivo Sistema"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(model);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(300);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAprobar = new JButton("Aprobar Manualmente");
        btnAprobar.setBackground(new Color(0, 120, 0));
        btnAprobar.setForeground(Color.WHITE);
        btnAprobar.setOpaque(true);
        btnAprobar.setBorderPainted(false);
        btnAprobar.setFocusPainted(false);

        JButton btnRechazar = new JButton("Rechazar Definitivamente");
        btnRechazar.setBackground(new Color(180, 0, 0));
        btnRechazar.setForeground(Color.WHITE);
        btnRechazar.setOpaque(true);
        btnRechazar.setBorderPainted(false);
        btnRechazar.setFocusPainted(false);

        JButton btnActualizar = new JButton("Actualizar Lista");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnAprobar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        btnAprobar.addActionListener(e -> procesarAprobacion());
        btnRechazar.addActionListener(e -> procesarRechazo());
        btnActualizar.addActionListener(e -> cargarDatos());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarDatos() {
        model.setRowCount(0);
        List<SolicitudAdopcion> solicitudes = solicitudService.listarTodas();

        solicitudes.sort((s1, s2) -> s1.getEstado().compareTo(s2.getEstado()));

        for (SolicitudAdopcion s : solicitudes) {
            String nombreAdoptante = adoptanteService.buscarPorId(s.getAdoptanteId())
                    .map(a -> a.getNombre()).orElse("Desconocido (" + s.getAdoptanteId() + ")");

            String nombreMascota = mascotaService.buscarPorId(s.getMascotaId())
                    .map(m -> m.getNombre()).orElse("Desconocido (" + s.getMascotaId() + ")");

            model.addRow(new Object[]{
                    s.getId(),
                    s.getFechaSolicitud(),
                    nombreAdoptante,
                    nombreMascota,
                    s.getEstado(),
                    s.getMotivoRechazo() != null ? s.getMotivoRechazo() : "-"
            });
        }
    }

    private void procesarAprobacion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud");
            return;
        }

        EstadoSolicitud estadoActual = (EstadoSolicitud) model.getValueAt(fila, 4);
        if (estadoActual == EstadoSolicitud.APROBADA) {
            JOptionPane.showMessageDialog(this, "Esta solicitud ya está aprobada.");
            return;
        }

        String idSolicitud = (String) model.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de aprobar esta solicitud manualmente?\nEsto marcará a la mascota como adoptada.",
                "Confirmar Aprobación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                solicitudService.aprobarSolicitudManual(idSolicitud);
                JOptionPane.showMessageDialog(this, "Solicitud aprobada con éxito.");
                cargarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void procesarRechazo() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud");
            return;
        }

        EstadoSolicitud estadoActual = (EstadoSolicitud) model.getValueAt(fila, 4);
        if (estadoActual == EstadoSolicitud.RECHAZADA) {
            JOptionPane.showMessageDialog(this, "Esta solicitud ya está rechazada.");
            return;
        }

        String idSolicitud = (String) model.getValueAt(fila, 0);
        String motivo = JOptionPane.showInputDialog(this, "Ingrese el motivo del rechazo final:");

        if (motivo != null && !motivo.trim().isEmpty()) {
            try {
                solicitudService.rechazarSolicitudManual(idSolicitud, motivo);
                JOptionPane.showMessageDialog(this, "Solicitud rechazada.");
                cargarDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
