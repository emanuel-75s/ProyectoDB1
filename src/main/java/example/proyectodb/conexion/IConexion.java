/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.conexion;

/**
 *
 * @author emanu
 */
import java.sql.Connection;
import java.sql.SQLException;

public interface IConexion {
   /**
     * Método para conectar a la base de datos
     * @return Connection objeto de conexión
     */
    Connection conectar();
    
    // NO debe tener getConexion() aquí
}
