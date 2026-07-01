/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.conexion;

//import example.proyectodb.conexion.ConexionOracle;
//import example.proyectodb.conexion.ConexionMySQL;
/**
 *
 * @author emanu
 */
public class ConexionFactory {
    private static final String TIPO_BD = "MYSQL"; 
    
    public static IConexion getConexion() {
        switch (TIPO_BD.toUpperCase()) {
            case "MYSQL":
                return new ConexionMySQL();
            case "ORACLE":
                return new ConexionOracle();
            default:
                throw new IllegalArgumentException("Tipo de BD no soportado: " + TIPO_BD);
        }
    }
    
    public static String getTipoBDActual() {
        return TIPO_BD;
    }
}
