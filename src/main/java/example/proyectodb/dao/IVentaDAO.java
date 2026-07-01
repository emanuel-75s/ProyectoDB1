/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.modelo.Venta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author emanu
 */
public interface IVentaDAO {
    
    
    /**
     * Registra una nueva venta
     * @param venta objeto Venta con los datos
     * @return true si se registró correctamente
     */
    boolean insertar(Venta venta);
    
    /**
     * Obtiene una venta por su ID
     * @param idVenta ID de la venta
     * @return objeto Venta o null
     */
    Venta obtenerPorId(int idVenta);
    
    /**
     * Lista todas las ventas
     * @return lista de ventas
     */
    List<Venta> listarTodas();
    
    /**
     * Busca ventas por nombre de cliente
     * @param nombreCliente nombre a buscar
     * @return lista de ventas
     */
    List<Venta> buscarPorCliente(String nombreCliente);
    
    /**
     * Obtiene ventas por fecha
     * @param fecha fecha de las ventas
     * @return lista de ventas de esa fecha
     */
    List<Venta> obtenerPorFecha(Date fecha);
    
    /**
     * Obtiene ventas por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de ventas en ese rango
     */
    List<Venta> obtenerPorRangoFechas(Date fechaInicio, Date fechaFin);
    
    /**
     * Obtiene el total de ventas del día
     * @return monto total
     */
    double obtenerTotalVentasDelDia();
    
    /**
     * Cancela/elimina una venta
     * @param idVenta ID de la venta
     * @return true si se eliminó
     */
    boolean eliminar(int idVenta);
    
}
