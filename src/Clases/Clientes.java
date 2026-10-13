package ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Clientes {
    
    private Connection connection;
    
    public Clientes() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;
    }

    public Connection getConnection() {
        return connection;
    }
        
    private boolean idExiste(String id) {
        String query = "SELECT COUNT(*) FROM clientes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;  // Si el conteo es mayor que 0, el ID ya existe
            }
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;  // Suponer que no existe si hay un error
    }

    public String generarIdUnico() {
        Random rand = new Random();
        String id;
        do {
            id = String.valueOf(rand.nextInt(900000) + 100000);
        } while (idExiste(id));
        return id;
    }
    
    public void crearCuentaCliente(String nombre, String apellido, int edad, String telefono, String email) {
        String id = generarIdUnico();  // Utilizar el método para generar un ID único
        String contrasena = id + nombre + edad;  // Concatenar ID, nombre y edad para crear una contraseña
        String query = "INSERT INTO clientes (id, nombre, apellido, edad, telefono, email, contrasena, puntos, movimientos) VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setInt(4, edad);
            statement.setString(5, telefono);
            statement.setString(6, email);
            statement.setString(7, contrasena);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cuenta de cliente creada correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al crear cuenta de cliente: " + ex.getMessage());
        }
    }

    public void eliminarCuentaCliente(String id, String apellido) {
        String query = "DELETE FROM clientes WHERE id = ? AND apellido = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, apellido);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Cuenta de cliente eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la cuenta de cliente con ID: " + id + " y apellido: " + apellido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al eliminar cuenta de cliente: " + ex.getMessage());
        }
    }

    public boolean iniciarSesion(String id, String contrasena) {
        String query = "SELECT * FROM clientes WHERE id = ? AND contrasena = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                Session.iniciarSesion(id, nombre);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public DefaultTableModel obtenerModeloClientes() {
        String[] columnNames = {"ID", "Nombre", "Apellido", "Edad", "Telefono", "Correo", "Movimientos", "Puntos"};
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);

        String query = "SELECT id, nombre, apellido, edad, telefono, email, movimientos, puntos FROM clientes";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int edad = resultSet.getInt("edad");
                String telefono = resultSet.getString("telefono");
                String email = resultSet.getString("email");
                int movimientos = resultSet.getInt("movimientos");
                int puntos = resultSet.getInt("puntos");

                Object[] data = {id, nombre, apellido, edad, telefono, email, movimientos, puntos};
                modelo.addRow(data);
            }
        } catch (SQLException ex) {
            System.err.println("Error al recuperar los datos de los clientes: " + ex.getMessage());
        }

        return modelo;
    }
    
    public DefaultTableModel EliminarClientes() {
        String[] columnNames = {"ID", "Nombre", "Apellido"};
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);

        String query = "SELECT id, nombre, apellido FROM clientes";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");

                Object[] data = {id, nombre, apellido};
                modelo.addRow(data);
            }
        } catch (SQLException ex) {
            System.err.println("Error al recuperar los datos de los clientes: " + ex.getMessage());
        }

        return modelo;
    }
    
    public boolean modificarPerfil(String id, String nombre, String apellido, int edad, String telefono, String email) {
        String query = "UPDATE clientes SET nombre = ?, apellido = ?, edad = ?, telefono = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setInt(3, edad);
            statement.setString(4, telefono);
            statement.setString(5, email);
            statement.setString(6, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean eliminarCuentaCliente(String id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
