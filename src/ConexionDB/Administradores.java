/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Administradores {
    
    private Connection connection;

    public Administradores() {
        Conexion conexion = new Conexion();
        this.connection = conexion.DB;
    }

    private boolean idExiste(String id) {
        String query = "SELECT COUNT(*) FROM administradores WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String generarIdUnico() {
        Random rand = new Random();
        String id;
        do {
            id = String.valueOf(rand.nextInt(900000) + 100000);  // Generar un número entre 100000 y 999999
        } while (idExiste(id));  // Verificar que el ID no existe en la base de datos
        return id;
    }
    
    public void crearAdministrador(String nombre) {
        String id = generarIdUnico();
        String contrasena = nombre + id;  // Generar contraseña concatenando nombre e ID
        String query = "INSERT INTO administradores (id, nombre, contrasena) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, nombre);
            statement.setString(3, contrasena);
            statement.executeUpdate();
            System.out.println("Administrador creado correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarAdministrador(String id) {
        String query = "DELETE FROM administradores WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Administrador eliminado correctamente.");
            } else {
                System.out.println("No se encontró el administrador con ID: " + id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarAdministradores() {
        String query = "SELECT * FROM administradores";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nombre = resultSet.getString("nombre");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean iniciarSesion(String id, String contrasena) {
        String query = "SELECT * FROM administradores WHERE id = ? AND contrasena = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            Logger.getLogger(Administradores.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
