package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Mascota;

import javax.swing.*;
import java.awt.*;

public class DetalleMascotaWindow extends JFrame {

    public DetalleMascotaWindow(Mascota mascota) {
        setTitle("Detalle de Mascota - " + mascota.getNombre());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Nombre:"));
        add(new JLabel(mascota.getNombre()));

        add(new JLabel("Especie:"));
        add(new JLabel(mascota.getEspecie().toString()));

        add(new JLabel("Raza:"));
        add(new JLabel(mascota.getRaza()));

        add(new JLabel("Tamaño:"));
        add(new JLabel(mascota.getTamano().toString()));

        add(new JLabel("Edad (años):"));
        add(new JLabel(String.valueOf(mascota.getEdadAnios())));

        add(new JLabel("Vacunado:"));
        add(new JLabel(mascota.isVacunado() ? "Sí" : "No"));

        add(new JLabel("Esterilizado:"));
        add(new JLabel(mascota.isEsterilizado() ? "Sí" : "No"));

        add(new JLabel("Estado de salud:"));
        add(new JLabel(mascota.getEstadoSalud()));

        JButton btnCerrar = new JButton("Cerrar");
        add(new JLabel(""));
        add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());
    }
}
