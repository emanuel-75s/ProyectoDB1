/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package example.proyectodb.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import example.proyectodb.conexion.IConexion;

/**
 *
 * @author emanu
 */
public class ConexionMySQL implements IConexion {
    
     
   private static final String URL = "jdbc:mysql://localhost:3306/muebleria_db";
    private static final String USER = "root";
    private static final String PASSWORD = "realmadrid2";
    
    /**
     * Método estático para obtener conexión
     * NO implementa la interfaz
     * Se usa directamente en los DAOs
     */
    public static Connection getConexion() {
        Connection conexion = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado a MySQL en puerto 3306");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Error de conexión a MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    /**
     * Método de instancia que implementa la interfaz
     * Llama internamente al método estático
     */
    @Override
    public Connection conectar() {
        return getConexion();
    }
}

