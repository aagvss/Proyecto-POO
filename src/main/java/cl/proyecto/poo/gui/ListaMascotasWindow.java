package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.Rol;
import cl.proyecto.poo.repository.MascotaRepository;
import cl.proyecto.poo.service.SolicitudService;
import cl.proyecto.poo.core.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaMascotasWindow extends JFrame {

    private final MascotaRepository mascotaRepository;
    private final SolicitudService solicitudService;
    private final Usuario usuarioActual;
    private final boolean modoAdopcion;


    public ListaMascotasWindow(MascotaRepository mascotaRepository, SolicitudService solicitudService, Usuario usuarioActual) {
        this.mascotaRepository = mascotaRepository;
        this.solicitudService = solicitudService;
        this.usuarioActual = usuarioActual;
        this.modoAdopcion = (usuarioActual.getRol() == Rol.ADOPTANTE);
        inicializarVentana();
    }


    public ListaMascotasWindow(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
        this.solicitudService = null;
        this.usuarioActual = null;
        this.modoAdopcion = false;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle(modoAdopcion ? "Mascotas Disponibles para Adopción" : "Mascotas Disponibles");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Tamaño", "Edad (años)", "Vacunado", "Esterilizado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(model);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarMascotasEnTabla(model);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnVer = new JButton("Ver Detalle");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnVer);

        if (modoAdopcion) {
            JButton btnAdoptar = new JButton("Solicitar Adopción");
            btnAdoptar.setBackground(new Color(0, 150, 0));
            btnAdoptar.setForeground(Color.BLACK);
            panelBotones.add(btnAdoptar);
            btnAdoptar.addActionListener(e -> solicitarAdopcion(tabla, model));
        }

        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        btnVer.addActionListener(e -> verDetalleMascota(tabla, model));
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarMascotasEnTabla(DefaultTableModel model) {
        model.setRowCount(0);
        List<Mascota> mascotas = mascotaRepository.findDispoibles();

        for (Mascota m : mascotas) {
            model.addRow(new Object[]{
                    m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(),
                    m.getTamano(), m.getEdadAnios(),
                    m.isVacunado() ? "Sí" : "No",
                    m.isEsterilizado() ? "Sí" : "No"
            });
        }
    }

    private void verDetalleMascota(JTable tabla, DefaultTableModel model) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota.");
            return;
        }
        String idMascota = (String) model.getValueAt(fila, 0);
        mascotaRepository.findById(idMascota).ifPresent(m ->
                new DetalleMascotaWindow(m).setVisible(true)
        );
    }

    private void solicitarAdopcion(JTable tabla, DefaultTableModel model) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una mascota para adoptar.");
            return;
        }

        String idMascota = (String) model.getValueAt(fila, 0);
        String nombreMascota = (String) model.getValueAt(fila, 1);


        if (usuarioActual.getRol() != Rol.ADOPTANTE) {
            JOptionPane.showMessageDialog(this,
                    "Solo los adoptantes pueden solicitar adopciones.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String adoptanteId = usuarioActual.getAdoptanteId();
        if (adoptanteId == null) {
            JOptionPane.showMessageDialog(this,
                    "Su cuenta no tiene un adoptante asociado.",
                    "Error de Configuración",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Solicitar adopción de " + nombreMascota + "?",
                "Confirmar Adopción",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                var solicitud = solicitudService.solicitar(adoptanteId, idMascota);

                String mensaje = "Solicitud creada: " + solicitud.getId() +
                        "\nEstado: " + solicitud.getEstado();

                if (solicitud.getMotivoRechazo() != null) {
                    mensaje += "\nObservaciones: " + solicitud.getMotivoRechazo();
                }

                JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
