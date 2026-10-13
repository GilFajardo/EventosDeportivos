/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion{
    
    Connection DB;
    String bd = "dbpfp3"; 
    String url="jdbc:mysql://localhost:3306/";
    String user="root"; 
    String password=""; 
    String driver="com.mysql.cj.jdbc.Driver"; 
    
public Conexion(){
  try {
      Class.forName(driver);
      DB = DriverManager.getConnection(url+bd,user,password);
        System.out.println("Conexion a la base de datos exitosa!");
  }catch (ClassNotFoundException | SQLException ex){
      System.out.println("ERROR DE CONEXION CON LA BASE DE DATOS!"); 
  }  
}

public void desconectar()
{
        try {
            DB.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
}

}