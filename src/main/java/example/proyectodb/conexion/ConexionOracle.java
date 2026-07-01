/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.conexion;
import example.proyectodb.conexion.IConexion;

/**
 *
 * @author emanu
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle implements IConexion {
    
   
     private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "Tienda";
    private static final String PASSWORD = "password123";
    
    /**
     * Método estático para obtener conexión
     * Usado directamente por los DAOs
     */
    public static Connection getConexion() {
        Connection conexion = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conectado a Oracle en puerto 1521");
        } catch (ClassNotFoundException e) {
            System.err.println(" Error: Driver Oracle no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Error de conexión a Oracle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    /**
     * Implementación del método de la interfaz
     * Llama internamente al método estático
     */
    @Override
    public Connection conectar() {
        return getConexion();
    }
}

