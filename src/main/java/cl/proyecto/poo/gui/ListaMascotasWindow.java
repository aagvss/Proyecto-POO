package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.repository.MascotaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaMascotasWindow extends JFrame {

    private final MascotaRepository mascotaRepository;

    public ListaMascotasWindow(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;

        setTitle("Mascotas Disponibles");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Nombre", "Especie", "Raza", "Tamaño", "Edad (años)", "Vacunado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Cargar mascotas
        List<Mascota> mascotas = mascotaRepository.findAll();
        for (Mascota m : mascotas) {
            model.addRow(new Object[]{
                    m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(),
                    m.getTamano(), m.getEdadAnios(), m.isVacunado() ? "Sí" : "No"
            });
        }

        JPanel panelBotones = new JPanel();
        JButton btnVer = new JButton("Ver Detalle");
        JButton btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        btnVer.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una mascota.");
                return;
            }
            String idMascota = (String) model.getValueAt(fila, 0);
            mascotaRepository.findById(idMascota).ifPresent(m ->
                    new DetalleMascotaWindow(m).setVisible(true)
            );
        });

        btnCerrar.addActionListener(e -> dispose());
    }
}
