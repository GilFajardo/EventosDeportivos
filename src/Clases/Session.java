/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author richi
 */
package ConexionDB;

public class Session {
    private static String userId;
    private static String userName;

    public static void iniciarSesion(String id, String nombre) {
        userId = id;
        userName = nombre;
    }

    public static void cerrarSesion() {
        userId = null;
        userName = null;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isLoggedIn() {
        return userId != null;
    }
}
