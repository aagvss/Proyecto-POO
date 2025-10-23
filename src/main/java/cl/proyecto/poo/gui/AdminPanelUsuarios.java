package cl.proyecto.poo.gui;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.model.EstadoUsuario;
import cl.proyecto.poo.service.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanelUsuarios extends JFrame {

    private final UsuarioService usuarioService;

    public AdminPanelUsuarios(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setTitle("Administración de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Email", "Rol", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarUsuarios(model);

        JPanel panelBotones = new JPanel();
        JButton btnAprobar = new JButton("Activar");
        JButton btnBloquear = new JButton("Bloquear");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnAprobar);
        panelBotones.add(btnBloquear);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAprobar.addActionListener(e -> cambiarEstadoSeleccionado(tabla, model, EstadoUsuario.ACTIVO));
        btnBloquear.addActionListener(e -> cambiarEstadoSeleccionado(tabla, model, EstadoUsuario.BLOQUEADO));
        btnEliminar.addActionListener(e -> eliminarSeleccionado(tabla, model));
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarUsuarios(DefaultTableModel model) {
        model.setRowCount(0);
        List<Usuario> usuarios = usuarioService.listarTodos();
        for (Usuario u : usuarios) {
            model.addRow(new Object[]{
                    u.getId(), u.getEmail(), u.getRol(), u.getEstado()
            });
        }
    }

    private void cambiarEstadoSeleccionado(JTable tabla, DefaultTableModel model, EstadoUsuario nuevoEstado) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return;
        }
        String id = (String) model.getValueAt(fila, 0);
        usuarioService.cambiarEstado(id, nuevoEstado);
        JOptionPane.showMessageDialog(this, "Estado actualizado a " + nuevoEstado);
        cargarUsuarios(model);
    }

    private void eliminarSeleccionado(JTable tabla, DefaultTableModel model) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return;
        }
        String id = (String) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            usuarioService.buscarPorId(id).ifPresent(u -> {
                usuarioService.listarTodos().remove(u); // si tu repo permite remove directo
            });
            JOptionPane.showMessageDialog(this, "Usuario eliminado.");
            cargarUsuarios(model);
        }
    }
}
