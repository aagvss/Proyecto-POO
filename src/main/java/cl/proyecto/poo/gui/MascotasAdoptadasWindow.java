package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.service.MascotaService;
import cl.proyecto.poo.service.AdoptanteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MascotasAdoptadasWindow extends JFrame {

    private final MascotaService mascotaService;
    private final AdoptanteService adoptanteService;

    public MascotasAdoptadasWindow(MascotaService mascotaService, AdoptanteService adoptanteService) {
        this.mascotaService = mascotaService;
        this.adoptanteService = adoptanteService;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Mascotas Adoptadas - Panel Administrador");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID Mascota", "Nombre", "Especie", "Raza", "Tamaño", "ID Adoptante", "Nombre Adoptante", "Documento"};
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

        cargarMascotasAdoptadas(model);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarMascotasAdoptadas(model));
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarMascotasAdoptadas(DefaultTableModel model) {
        model.setRowCount(0);
        List<Mascota> mascotasAdoptadas = mascotaService.obtenerMascotasAdoptadas();

        for (Mascota mascota : mascotasAdoptadas) {
            String adoptanteId = mascota.getAdoptanteId();
            String nombreAdoptante = "No encontrado";
            String documentoAdoptante = "No encontrado";

            if (adoptanteId != null) {
                adoptanteService.buscarPorId(adoptanteId).ifPresent(adoptante -> {
                    model.addRow(new Object[]{
                            mascota.getId(),
                            mascota.getNombre(),
                            mascota.getEspecie(),
                            mascota.getRaza(),
                            mascota.getTamano(),
                            adoptanteId,
                            adoptante.getNombre(),
                            adoptante.getDocumento()
                    });
                });
            } else {
                model.addRow(new Object[]{
                        mascota.getId(),
                        mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getTamano(),
                        "N/A",
                        "N/A",
                        "N/A"
                });
            }
        }

        if (mascotasAdoptadas.isEmpty()) {
            model.addRow(new Object[]{"", "No hay mascotas adoptadas", "", "", "", "", "", ""});
        }
    }
}
