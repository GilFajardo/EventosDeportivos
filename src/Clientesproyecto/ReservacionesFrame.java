package Clientesproyecto;

import ConexionDB.Asientos;
import ConexionDB.Session;
import Clases.GenerarPDF;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservacionesFrame extends JFrame {
    private String idCliente;
    private JTable jTableReservas;
    private JButton jbtnImprimir;

    public ReservacionesFrame() {
        this.idCliente = Session.getUserId(); // Obtener el ID del cliente desde la sesión
        initComponents();
        setTitle("Consultar Reservas");
        cargarDatosDeAsientos();
    }

    public void cargarDatosDeAsientos() {
        Asientos asientos = new Asientos();
        DefaultTableModel modelo = asientos.obtenerModeloAsientosPorUsuario(idCliente);
        jTableReservas.setModel(modelo);
    }

    private void initComponents() {
        JScrollPane jScrollPane3 = new JScrollPane();
        jTableReservas = new JTable();
        jbtnImprimir = new JButton();

        jTableReservas.setModel(new DefaultTableModel(
            new Object[][]{
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String[]{
                "ID Asiento", "ID Evento", "Número Asiento", "Estado", "ID Usuario", "Tipo Evento", "Precio"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableReservas);

        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jbtnImprimir)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                    .addComponent(jbtnImprimir)
                    .addContainerGap())
        );

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiar la operación de cierre
        setSize(600, 400);
    }

    private void jbtnImprimirActionPerformed(ActionEvent evt) {
        // Generar PDF con los asientos del usuario
        GenerarPDF generarPDF = new GenerarPDF();
        String rutaArchivo = "asientos_cliente.pdf";
        generarPDF.generarPDFAsientosPorUsuario((DefaultTableModel) jTableReservas.getModel(), rutaArchivo);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new ReservacionesFrame().setVisible(true);
        });
    }
}
