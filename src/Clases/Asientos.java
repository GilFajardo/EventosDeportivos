package ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Asientos {

    private Connection connection;

    public Asientos() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;
    }
    public DefaultTableModel obtenerModeloAsientosPorUsuario(String idUsuario) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Asiento");
        modelo.addColumn("ID Evento");
        modelo.addColumn("Número Asiento");
        modelo.addColumn("Estado");
        modelo.addColumn("ID Usuario");
        modelo.addColumn("Tipo Evento");
        modelo.addColumn("Precio");

        String query = "SELECT id_asiento, id_evento, numero_asiento, estado, id_usuario, tipo_evento, precio FROM asientos WHERE id_usuario = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAsiento = resultSet.getInt("id_asiento");
                int idEvento = resultSet.getInt("id_evento");
                int numeroAsiento = resultSet.getInt("numero_asiento");
                String estado = resultSet.getString("estado");
                String idUsuarioObtenido = resultSet.getString("id_usuario");
                String tipoEvento = resultSet.getString("tipo_evento");
                double precio = resultSet.getDouble("precio");
                modelo.addRow(new Object[]{idAsiento, idEvento, numeroAsiento, estado, idUsuarioObtenido, tipoEvento, precio});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Asientos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar los asientos: " + ex.getMessage());
        }
        return modelo;
    }

    public String getEstadoAsiento(int idEvento, int numeroAsiento) {
        String query = "SELECT estado FROM asientos WHERE id_evento = ? AND numero_asiento = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEvento);
            statement.setInt(2, numeroAsiento);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("estado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Asientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "desconocido";
    }

    public boolean reservarAsiento(int idEvento, int numeroAsiento, int idUsuario) {
        String query = "UPDATE asientos SET estado = 'reservado', id_usuario = ? WHERE id_evento = ? AND numero_asiento = ? AND estado = 'disponible'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idUsuario);
            statement.setInt(2, idEvento);
            statement.setInt(3, numeroAsiento);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Asientos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void inicializarAsientosParaEvento(int idEvento, String tipoEvento, double precio) {
        String query = "UPDATE asientos SET id_evento = ?, tipo_evento = ?, precio = ?, estado = 'disponible' WHERE id_evento IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEvento);
            statement.setString(2, tipoEvento);
            statement.setDouble(3, precio);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Asientos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
