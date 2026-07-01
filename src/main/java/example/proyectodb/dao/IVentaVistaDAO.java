/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.modelo.Venta;
import java.util.List;
/**
 *
 * @author emanu
 */
public interface IVentaVistaDAO {
 
    /**
     * Obtiene ventas completas desde la vista
     * @return Lista de ventas con información completa
     */
    List<Venta> obtenerVentasCompletas();
    
    /**
     * Obtiene ventas del día desde la vista
     * @return Lista de ventas del día actual
     */
    List<Venta> obtenerVentasHoy();
    
    /**
     * Obtiene el total de ventas del día desde la vista
     * @return Total vendido hoy
     */
    double obtenerTotalVentasHoy();
}
