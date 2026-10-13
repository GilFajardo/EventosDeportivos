package Clientesproyecto;

import ConexionDB.Clientes;
import ConexionDB.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientePerfilFrame extends JInternalFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEdad;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JLabel lblMensaje;

    private String idCliente;

    public ClientePerfilFrame() {
        this.idCliente = Session.getUserId();
        initComponents();
        cargarDatosCliente();
    }

    private void initComponents() {
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Perfil de Usuario");
        setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField();

        JLabel lblEdad = new JLabel("Edad:");
        txtEdad = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField();

        JLabel lblEmail = new JLabel("Correo Electrónico:");
        txtEmail = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JTextField();

        lblMensaje = new JLabel("");

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPerfil();
            }
        });

        btnEliminar = new JButton("Eliminar Cuenta");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCuenta();
            }
        });

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblApellido);
        panel.add(txtApellido);
        panel.add(lblEdad);
        panel.add(txtEdad);
        panel.add(lblTelefono);
        panel.add(txtTelefono);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(lblMensaje);

        add(panel, BorderLayout.CENTER);
    }

    private void cargarDatosCliente() {
        Clientes clientes = new Clientes();
        DefaultTableModel modelo = clientes.obtenerModeloClientes();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(idCliente)) {
                txtNombre.setText(modelo.getValueAt(i, 1).toString());
                txtApellido.setText(modelo.getValueAt(i, 2).toString());
                txtEdad.setText(modelo.getValueAt(i, 3).toString());
                txtTelefono.setText(modelo.getValueAt(i, 4).toString());
                txtEmail.setText(modelo.getValueAt(i, 5).toString());
                break;
            }
        }
    }

    private void modificarPerfil() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        Clientes clientes = new Clientes();
        boolean exito = clientes.modificarPerfil(idCliente, nombre, apellido, edad, telefono, email);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Perfil modificado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el perfil.");
        }
    }

    private void eliminarCuenta() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar tu cuenta?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            Clientes clientes = new Clientes();
            boolean exito = clientes.eliminarCuentaCliente(idCliente);
            if (exito) {
                Session.cerrarSesion();
                JOptionPane.showMessageDialog(this, "Cuenta eliminada correctamente. La sesión se cerrará.");
                // Cerrar solo este frame y no la aplicación
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la cuenta.");
            }
        }
    }
}
