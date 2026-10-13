package ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EstadisticasEventos {

    private Connection connection;

    public EstadisticasEventos() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;
    }

    // Método para obtener los eventos deportivos con más compras
    public DefaultTableModel obtenerEventosMasComprados() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Evento");
        modelo.addColumn("Tipo de Evento");
        modelo.addColumn("Compras");

        String query = "SELECT e.nombre, e.tipo_evento, COUNT(r.id_evento) AS compras " +
                       "FROM eventos e " +
                       "JOIN reservas r ON e.id = r.id_evento " +
                       "GROUP BY e.nombre, e.tipo_evento " +
                       "ORDER BY compras DESC";

        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String tipoEvento = resultSet.getString("tipo_evento");
                int compras = resultSet.getInt("compras");
                modelo.addRow(new Object[]{nombre, tipoEvento, compras});
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstadisticasEventos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar los eventos más comprados: " + ex.getMessage());
        }
        return modelo;
    }

    // Método para obtener los asientos más seleccionados
    public DefaultTableModel obtenerAsientosMasSeleccionados() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Número de Asiento");
        modelo.addColumn("Evento");
        modelo.addColumn("Seleccionado");

        String query = "SELECT a.numero_asiento, e.nombre AS evento, COUNT(r.id_asiento) AS seleccionado " +
                       "FROM asientos a " +
                       "JOIN reservas r ON a.id_asiento = r.id_asiento " +
                       "JOIN eventos e ON a.id_evento = e.id " +
                       "GROUP BY a.numero_asiento, e.nombre " +
                       "ORDER BY seleccionado DESC";

        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int numeroAsiento = resultSet.getInt("numero_asiento");
                String evento = resultSet.getString("evento");
                int seleccionado = resultSet.getInt("seleccionado");
                modelo.addRow(new Object[]{numeroAsiento, evento, seleccionado});
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstadisticasEventos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar los asientos más seleccionados: " + ex.getMessage());
        }
        return modelo;
    }

    // Método para obtener los clientes que más compran
    public DefaultTableModel obtenerClientesMasCompras() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Cliente");
        modelo.addColumn("Compras");

        String query = "SELECT c.nombre, COUNT(r.id_cliente) AS compras " +
                       "FROM clientes c " +
                       "JOIN reservas r ON c.id = r.id_cliente " +
                       "GROUP BY c.nombre " +
                       "ORDER BY compras DESC";

        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String cliente = resultSet.getString("nombre");
                int compras = resultSet.getInt("compras");
                modelo.addRow(new Object[]{cliente, compras});
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstadisticasEventos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar los clientes que más compran: " + ex.getMessage());
        }
        return modelo;
    }
}
