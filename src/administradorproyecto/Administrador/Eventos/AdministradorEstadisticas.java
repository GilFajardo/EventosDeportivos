package administradorproyecto.Administrador.Eventos;

import ConexionDB.EstadisticasEventos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;

public class AdministradorEstadisticas extends JInternalFrame {
    private JTable jTable1;
    private JPanel jPanel1;
    private JButton jButtonEventos;
    private JButton jButtonAsientos;
    private JButton jButtonClientes;

    public AdministradorEstadisticas() {
        initComponents();
        setTitle("Estadísticas de Eventos");
    }

    private void initComponents() {
        JScrollPane jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jButtonEventos = new JButton();
        jButtonAsientos = new JButton();
        jButtonClientes = new JButton();
        jPanel1 = new JPanel();

        setClosable(true);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(120, 70));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jTable1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{}
        ));
        jScrollPane1.setViewportView(jTable1);

        jButtonEventos.setText("Eventos Más Comprados");
        jButtonEventos.addActionListener(evt -> cargarEventosMasComprados());

        jButtonAsientos.setText("Asientos Más Seleccionados");
        jButtonAsientos.addActionListener(evt -> cargarAsientosMasSeleccionados());

        jButtonClientes.setText("Clientes con Más Compras");
        jButtonClientes.addActionListener(evt -> cargarClientesMasCompras());

        jPanel1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 254, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButtonEventos)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonAsientos)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonClientes)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonEventos)
                        .addComponent(jButtonAsientos)
                        .addComponent(jButtonClientes))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );

        pack();
    }

    private void cargarEventosMasComprados() {
        EstadisticasEventos estadisticas = new EstadisticasEventos();
        DefaultTableModel modelo = estadisticas.obtenerEventosMasComprados();
        jTable1.setModel(modelo);
        graficar(modelo, "Eventos Más Comprados", "Eventos", "Compras");
    }

    private void cargarAsientosMasSeleccionados() {
        EstadisticasEventos estadisticas = new EstadisticasEventos();
        DefaultTableModel modelo = estadisticas.obtenerAsientosMasSeleccionados();
        jTable1.setModel(modelo);
        graficar(modelo, "Asientos Más Seleccionados", "Asientos", "Seleccionados");
    }

    private void cargarClientesMasCompras() {
        EstadisticasEventos estadisticas = new EstadisticasEventos();
        DefaultTableModel modelo = estadisticas.obtenerClientesMasCompras();
        jTable1.setModel(modelo);
        graficar(modelo, "Clientes con Más Compras", "Clientes", "Compras");
    }

    private void graficar(DefaultTableModel modelo, String titulo, String categoria, String valor) {
        DefaultCategoryDataset datosGrafica = new DefaultCategoryDataset();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String categoriaDato = modelo.getValueAt(i, 0).toString();
            int valorDato = Integer.parseInt(modelo.getValueAt(i, 2).toString());
            datosGrafica.addValue(valorDato, categoriaDato, categoriaDato);
        }

        JFreeChart graficoBarras = ChartFactory.createBarChart(
            titulo,
            categoria,
            valor,
            datosGrafica,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        ChartPanel panel = new ChartPanel(graficoBarras);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 200));

        jPanel1.removeAll();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(panel, BorderLayout.NORTH);
        jPanel1.revalidate();
        jPanel1.repaint();
    }
}
