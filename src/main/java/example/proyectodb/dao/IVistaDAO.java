/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.modelo.ResumenInventario;
import example.proyectodb.modelo.Venta;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface IVistaDAO {
    
     /**
     * Obtiene ventas del día desde la vista
     */
    List<Venta> obtenerVentasHoy();
    
    /**
     * Obtiene resumen de inventario por categoría desde la vista
     */
    List<ResumenInventario> obtenerResumenInventario();
    
    /**
     * Obtiene total de ventas del día
     */
    double obtenerTotalVentasHoy();
}
