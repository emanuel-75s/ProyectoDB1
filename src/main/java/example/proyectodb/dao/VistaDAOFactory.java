/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.dao.VistaMySQLDAO.VistaMySQLDAO;
import example.proyectodb.dao.VistaOracleDAO.VistaOracleDAO;
/**
 *
 * @author emanu
 */
public class VistaDAOFactory {
      public static IVistaDAO crearVistaDAO() {
        String tipoBD = ConexionFactory.getTipoBDActual();
        
        if (tipoBD.equals("MYSQL")) {
            return new VistaMySQLDAO();
        } else if (tipoBD.equals("ORACLE")) {
            return new VistaOracleDAO();
        } else {
            throw new IllegalArgumentException("Base de datos no soportada: " + tipoBD);
        }
    }
       
}
