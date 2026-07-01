/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.modelo.Mueble;
import java.util.List;
/**
 *
 * @author emanu
 */
public interface IMuebleDAO {
    boolean insertar(Mueble mueble);
    
    boolean actualizar(Mueble mueble);
    
    boolean eliminar(int idMueble);
    
    Mueble obtenerPorId(int idMueble);
    
    Mueble obtenerPorCodigo(String codigo);
    
    /**
     * Lista todos los muebles
     * @return lista de muebles
     */
    List<Mueble> listarTodos();
    
    /**
     * Busca muebles por nombre
     * @param nombre nombre a buscar (puede ser parcial)
     * @return lista de muebles que coinciden
     */
    List<Mueble> buscarPorNombre(String nombre);
    
    /**
     * Busca muebles por categoría
     * @param categoria categoría exacta
     * @return lista de muebles de esa categoría
     */
    List<Mueble> buscarPorCategoria(String categoria);
    
    /**
     * Obtiene muebles con stock bajo (menos de 5)
     * @return lista de muebles con poco stock
     */
    List<Mueble> obtenerStockBajo();
    
    /**
     * Actualiza solo el stock de un mueble
     * @param idMueble ID del mueble
     * @param nuevaCantidad nueva cantidad en stock
     * @return true si se actualizó
     */
    boolean actualizarStock(int idMueble, int nuevaCantidad); 
}
