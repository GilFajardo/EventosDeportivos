package ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Reservas {

    private Connection connection;

    public Reservas() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;

        if (this.connection == null) {
            JOptionPane.showMessageDialog(null, "Error al establecer la conexión con la base de datos.");
        }
    }

    public DefaultTableModel obtenerModeloReservasPorCliente(String idCliente) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Reserva");
        modelo.addColumn("ID Asiento");
        modelo.addColumn("ID Usuario");
        modelo.addColumn("Estado");

        String query = "SELECT id_reserva, id_asiento, id_usuario, estado FROM reservas WHERE id_usuario = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idCliente);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idReserva = resultSet.getInt("id_reserva");
                int idAsiento = resultSet.getInt("id_asiento");
                int idUsuario = resultSet.getInt("id_usuario");
                String estado = resultSet.getString("estado");
                modelo.addRow(new Object[]{idReserva, idAsiento, idUsuario, estado});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Reservas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar las reservas: " + ex.getMessage());
        }
        return modelo;
    }
}
