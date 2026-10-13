/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import administradorproyecto.Administrador.Eventos.AdministradorModificarEvento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Random;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;
import java.util.logging.Level;



public class Eventos {

    private Connection connection;

       public Eventos() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;
    }

    public DefaultTableModel obtenerModeloEventos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Tipo de Evento");
        modelo.addColumn("Fecha");
        modelo.addColumn("Capacidad");
        modelo.addColumn("Precio");

        String query = "SELECT id, nombre, tipo_evento, fecha, capacidad, precio FROM eventos";
        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nombre = resultSet.getString("nombre");
                String tipoEvento = resultSet.getString("tipo_evento");
                String fecha = resultSet.getString("fecha");
                int capacidad = resultSet.getInt("capacidad");
                double precio = resultSet.getDouble("precio");
                modelo.addRow(new Object[]{id, nombre, tipoEvento, fecha, capacidad, precio});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los eventos: " + ex.getMessage());
        }
        return modelo;
    }
    
    private boolean idExiste(String id) {
        String query = "SELECT COUNT(*) FROM eventos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String generarIdUnico() {
        Random rand = new Random();
        String id;
        do {
            // Genera un ID numérico de 6 dígitos como ejemplo
            id = String.valueOf(rand.nextInt(900000) + 100000);
        } while (idExiste(id));
        return id;
    }


public void agregarEvento(String nombre, String tipoEvento, String fecha, int capacidad, double precio) {
    String eventId = generarIdUnico();  // Usar el ID único generado
    String queryEvento = "INSERT INTO eventos (id, nombre, tipo_evento, fecha, capacidad, precio) VALUES (?, ?, ?, ?, ?, ?)";
    String queryAsientos = "UPDATE asientos SET id_evento = ?, tipo_evento = ?, precio = ? WHERE id_evento IS NULL";
    
    try (PreparedStatement statementEvento = connection.prepareStatement(queryEvento);
         PreparedStatement statementAsientos = connection.prepareStatement(queryAsientos)) {
        
        // Insertar el evento
        statementEvento.setString(1, eventId);
        statementEvento.setString(2, nombre);
        statementEvento.setString(3, tipoEvento);
        statementEvento.setString(4, fecha);
        statementEvento.setInt(5, capacidad);
        statementEvento.setDouble(6, precio);
        statementEvento.executeUpdate();
        
        // Asociar los asientos con el nuevo evento
        statementAsientos.setString(1, eventId);
        statementAsientos.setString(2, tipoEvento);
        statementAsientos.setDouble(3, precio);
        statementAsientos.executeUpdate();

        JOptionPane.showMessageDialog(null, "Evento y asientos agregados correctamente.");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al agregar el evento: " + ex.getMessage());
        Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
    }
}




    public void eliminarEvento(int id, String adminId, String adminPassword) {
        if (autenticarAdmin(adminId, adminPassword)) {
            String query = "DELETE FROM eventos WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Evento eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el evento para eliminar.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el evento: " + ex.getMessage());
                Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Autenticación fallida: ID o contraseña incorrecta.");
        }
    }

    private boolean autenticarAdmin(String adminId, String adminPassword) {
        Administradores admin = new Administradores();
        return admin.iniciarSesion(adminId, adminPassword);
    }
    



    
    private static final Logger LOGGER = Logger.getLogger(AdministradorModificarEvento.class.getName());

public void actualizarEvento(String id, String campo, Object nuevoValor) {
    
    String query = "UPDATE eventos SET " + campo + " = ? WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setObject(1, nuevoValor);
        statement.setString(2, id);
        int result = statement.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "Modificación exitosa.", "Actualización", JOptionPane.INFORMATION_MESSAGE);
            LOGGER.info("Evento actualizado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el evento.", "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.warning("No se encontraron filas para actualizar.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al actualizar el evento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        LOGGER.log(Level.SEVERE, "Error al actualizar el evento", ex);
    }
}

    public Evento obtenerEventoPorId(int idEvento) {
        String query = "SELECT id, nombre, tipo_evento, fecha, capacidad, precio FROM eventos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEvento);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Evento(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("tipo_evento"),
                    resultSet.getString("fecha"),
                    resultSet.getInt("capacidad"),
                    resultSet.getDouble("precio")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static class Evento {
        public int id;
        public String nombre;
        public String tipoEvento;
        public String fecha;
        public int capacidad;
        public double precio;

        public Evento(int id, String nombre, String tipoEvento, String fecha, int capacidad, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.tipoEvento = tipoEvento;
            this.fecha = fecha;
            this.capacidad = capacidad;
            this.precio = precio;
        }
    }
    
    public DefaultTableModel obtenerModeloReservas() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID Reserva");
    modelo.addColumn("ID Cliente");
    modelo.addColumn("ID Evento");
    modelo.addColumn("ID Asiento");
    modelo.addColumn("Fecha Reserva");

    String query = "SELECT id_reserva, id_cliente, id_evento, id_asiento, fecha_reserva FROM reservas";
    try (PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            int idReserva = resultSet.getInt("id_reserva");
            int idCliente = resultSet.getInt("id_cliente");
            int idEvento = resultSet.getInt("id_evento");
            int idAsiento = resultSet.getInt("id_asiento");
            String fechaReserva = resultSet.getString("fecha_reserva");
            modelo.addRow(new Object[]{idReserva, idCliente, idEvento, idAsiento, fechaReserva});
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return modelo;
}

}


