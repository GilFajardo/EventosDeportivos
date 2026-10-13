package asientos;

import ConexionDB.Asientos;
import ConexionDB.Eventos;
import ConexionDB.Eventos.Evento;
import ConexionDB.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AsientosReservas extends JFrame {

    private Asientos asientosDB = new Asientos();
    private Evento evento;
    private int idEvento;
    private String idUsuario;
    private List<JPanel> botonesAsientos;
    private JButton btnConfirmar;
    private JButton btnSalir;

    public AsientosReservas(int idEvento) {
        this.idEvento = idEvento;
        if (Session.isLoggedIn()) {
            this.idUsuario = Session.getUserId();
            initComponents();
            inicializarEventoYAsientos();
            inicializarBotones();
        } else {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión para reservar un asiento.");
            dispose();
        }
    }

    private void inicializarEventoYAsientos() {
        Eventos eventosDB = new Eventos();
        this.evento = eventosDB.obtenerEventoPorId(idEvento);
        if (evento != null) {
            setTitle("Reservación de Asientos para " + evento.nombre);
            asientosDB.inicializarAsientosParaEvento(idEvento, evento.tipoEvento, evento.precio);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el evento.");
            dispose();
        }
    }

    private void inicializarBotones() {
        botonesAsientos = List.of(
            btnAsiento1, btnAsiento2, btnAsiento3, btnAsiento4, btnAsiento5,
            btnAsiento6, btnAsiento7, btnAsiento8, btnAsiento9, btnAsiento10,
            btnAsiento11, btnAsiento12, btnAsiento13, btnAsiento14, btnAsiento15,
            btnAsiento16, btnAsiento17, btnAsiento18, btnAsiento19, btnAsiento20,
            btnAsiento21, btnAsiento22, btnAsiento23, btnAsiento24, btnAsiento25,
            btnAsiento26, btnAsiento27, btnAsiento28, btnAsiento29, btnAsiento30,
            btnAsiento31, btnAsiento32, btnAsiento33, btnAsiento34, btnAsiento35
        );

        for (int i = 0; i < botonesAsientos.size(); i++) {
            int numeroAsiento = i + 1;
            JPanel boton = botonesAsientos.get(i);
            String estado = asientosDB.getEstadoAsiento(idEvento, numeroAsiento);
            JLabel label = new JLabel(String.valueOf(numeroAsiento));
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            boton.add(label);

            if ("disponible".equalsIgnoreCase(estado)) {
                boton.setBackground(Color.BLUE);  // Azul oscuro para disponible
            } else if ("reservado".equalsIgnoreCase(estado)) {
                boton.setBackground(Color.GRAY);  // Gris para reservado
            } else {
                boton.setBackground(Color.RED);   // Rojo para estado desconocido o error
            }

            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reservarAsiento(boton, numeroAsiento);
                }
            });
        }
    }

    private void reservarAsiento(JPanel boton, int numeroAsiento) {
        String estado = asientosDB.getEstadoAsiento(idEvento, numeroAsiento);
        if ("disponible".equalsIgnoreCase(estado)) {
            if (asientosDB.reservarAsiento(idEvento, numeroAsiento, Integer.parseInt(idUsuario))) {
                boton.setBackground(Color.GRAY);
                boton.removeMouseListener(boton.getMouseListeners()[0]);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo reservar el asiento.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El asiento ya está reservado.");
        }
    }

    private void confirmarReservas() {
        // Implementa la lógica para confirmar las reservas, por ejemplo, guardar en la base de datos
        JOptionPane.showMessageDialog(this, "Reservas confirmadas.");
    }

    private void cancelarReservas() {
        dispose();  // Cierra solo esta ventana
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnAsiento1 = new JPanel();
        btnAsiento2 = new JPanel();
        btnAsiento3 = new JPanel();
        btnAsiento4 = new JPanel();
        btnAsiento5 = new JPanel();
        btnAsiento6 = new JPanel();
        btnAsiento7 = new JPanel();
        btnAsiento8 = new JPanel();
        btnAsiento9 = new JPanel();
        btnAsiento10 = new JPanel();
        btnAsiento11 = new JPanel();
        btnAsiento12 = new JPanel();
        btnAsiento13 = new JPanel();
        btnAsiento14 = new JPanel();
        btnAsiento15 = new JPanel();
        btnAsiento16 = new JPanel();
        btnAsiento17 = new JPanel();
        btnAsiento18 = new JPanel();
        btnAsiento19 = new JPanel();
        btnAsiento20 = new JPanel();
        btnAsiento21 = new JPanel();
        btnAsiento22 = new JPanel();
        btnAsiento23 = new JPanel();
        btnAsiento24 = new JPanel();
        btnAsiento25 = new JPanel();
        btnAsiento26 = new JPanel();
        btnAsiento27 = new JPanel();
        btnAsiento28 = new JPanel();
        btnAsiento29 = new JPanel();
        btnAsiento30 = new JPanel();
        btnAsiento31 = new JPanel();
        btnAsiento32 = new JPanel();
        btnAsiento33 = new JPanel();
        btnAsiento34 = new JPanel();
        btnAsiento35 = new JPanel();

        btnConfirmar = new JButton("Confirmar");
        btnSalir = new JButton("Cancelar");

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarReservas();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarReservas();
            }
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  // Cerrar solo esta ventana
        setLayout(new BorderLayout());  // Usar BorderLayout para añadir los botones confirmar y cancelar

        JPanel panelAsientos = new JPanel(new GridLayout(7, 5, 10, 10));
        List<JPanel> botones = List.of(
            btnAsiento1, btnAsiento2, btnAsiento3, btnAsiento4, btnAsiento5, 
            btnAsiento6, btnAsiento7, btnAsiento8, btnAsiento9, btnAsiento10, 
            btnAsiento11, btnAsiento12, btnAsiento13, btnAsiento14, btnAsiento15, 
            btnAsiento16, btnAsiento17, btnAsiento18, btnAsiento19, btnAsiento20, 
            btnAsiento21, btnAsiento22, btnAsiento23, btnAsiento24, btnAsiento25, 
            btnAsiento26, btnAsiento27, btnAsiento28, btnAsiento29, btnAsiento30, 
            btnAsiento31, btnAsiento32, btnAsiento33, btnAsiento34, btnAsiento35
        );

        for (JPanel boton : botones) {
            boton.setPreferredSize(new Dimension(50, 50));
            boton.setBorder(BorderFactory.createSoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
            panelAsientos.add(boton);
        }

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnSalir);

        add(panelAsientos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }



    private JPanel btnAsiento1;
    private JPanel btnAsiento2;
    private JPanel btnAsiento3;
    private JPanel btnAsiento4;
    private JPanel btnAsiento5;
    private JPanel btnAsiento6;
    private JPanel btnAsiento7;
    private JPanel btnAsiento8;
    private JPanel btnAsiento9;
    private JPanel btnAsiento10;
    private JPanel btnAsiento11;
    private JPanel btnAsiento12;
    private JPanel btnAsiento13;
    private JPanel btnAsiento14;
    private JPanel btnAsiento15;
    private JPanel btnAsiento16;
    private JPanel btnAsiento17;
    private JPanel btnAsiento18;
    private JPanel btnAsiento19;
    private JPanel btnAsiento20;
    private JPanel btnAsiento21;
    private JPanel btnAsiento22;
    private JPanel btnAsiento23;
    private JPanel btnAsiento24;
    private JPanel btnAsiento25;
    private JPanel btnAsiento26;
    private JPanel btnAsiento27;
    private JPanel btnAsiento28;
    private JPanel btnAsiento29;
    private JPanel btnAsiento30;
    private JPanel btnAsiento31;
    private JPanel btnAsiento32;
    private JPanel btnAsiento33;
    private JPanel btnAsiento34;
    private JPanel btnAsiento35;
}
