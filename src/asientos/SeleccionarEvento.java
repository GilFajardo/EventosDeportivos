package asientos;

import ConexionDB.Eventos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionarEvento extends JFrame {

    private JTable tablaEventos;
    private JButton botonSeleccionar;
    private DefaultTableModel modelo;

    public SeleccionarEvento() {
        setTitle("Seleccionar Evento");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        cargarEventos();
    }

    private void initComponents() {
        modelo = new DefaultTableModel();
        tablaEventos = new JTable(modelo);
        botonSeleccionar = new JButton("Seleccionar Evento");

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo de Evento");
        modelo.addColumn("Fecha");
        modelo.addColumn("Capacidad");
        modelo.addColumn("Precio");

        JScrollPane scrollPane = new JScrollPane(tablaEventos);

        botonSeleccionar.addActionListener((ActionEvent e) -> {
            seleccionarEvento();
        });

        add(scrollPane, BorderLayout.CENTER);
        add(botonSeleccionar, BorderLayout.SOUTH);
    }

    private void cargarEventos() {
        Eventos eventosDB = new Eventos();
        DefaultTableModel modeloEventos = eventosDB.obtenerModeloEventos();
        tablaEventos.setModel(modeloEventos);
    }

    private void seleccionarEvento() {
        int filaSeleccionada = tablaEventos.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idEvento = Integer.parseInt(tablaEventos.getValueAt(filaSeleccionada, 0).toString());
            AsientosReservas asientosReserva = new AsientosReservas(idEvento);
            asientosReserva.setVisible(true);
            dispose(); // Close the current frame after opening the AsientosReserva frame
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un evento.");
        }
    }
    /*
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new SeleccionarEvento().setVisible(true);
        });
    }
    */  
}
