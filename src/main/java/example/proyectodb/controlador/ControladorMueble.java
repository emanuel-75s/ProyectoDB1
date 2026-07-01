/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.controlador;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.conexion.IConexion;
import example.proyectodb.dao.MuebleDAO;
import example.proyectodb.modelo.Mueble;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emanu
 */
public class ControladorMueble {
  private MuebleDAO muebleDAO;
    
    public ControladorMueble() {
        this.muebleDAO = new MuebleDAO();
    }
    
    /**
     * Guarda un nuevo mueble con validaciones
     * @param mueble objeto Mueble
     * @return true si se guardó correctamente
     */
    public boolean guardarMueble(Mueble mueble) {
        // Validaciones de negocio
        if (mueble.getCodigoMueble() == null || mueble.getCodigoMueble().trim().isEmpty()) {
            System.err.println("El código no puede estar vacío");
            return false;
        }
        
        if (mueble.getNombreMueble() == null || mueble.getNombreMueble().trim().isEmpty()) {
            System.err.println("El nombre no puede estar vacío");
            return false;
        }
        
        if (mueble.getCategoria() == null || mueble.getCategoria().trim().isEmpty()) {
            System.err.println("Debe seleccionar una categoría");
            return false;
        }
        
        if (mueble.getPrecio() <= 0) {
            System.err.println("El precio debe ser mayor a 0");
            return false;
        }
        
        if (mueble.getCantidadStock() < 0) {
            System.err.println("El stock no puede ser negativo");
            return false;
        }
        
        // Verificar si el código ya existe
        Mueble existente = muebleDAO.obtenerPorCodigo(mueble.getCodigoMueble());
        if (existente != null) {
            System.err.println("El código de mueble ya existe");
            return false;
        }
        
        return muebleDAO.insertar(mueble);
    }
    
    /**
     * Actualiza un mueble existente
     * @param mueble objeto Mueble con los nuevos datos
     * @return true si se actualizó correctamente
     */
    public boolean actualizarMueble(Mueble mueble) {
        if (mueble.getIdMueble() <= 0) {
            System.err.println("ID de mueble inválido");
            return false;
        }
        
        // Validar que el mueble exista
        Mueble existente = muebleDAO.obtenerPorId(mueble.getIdMueble());
        if (existente == null) {
            System.err.println("El mueble no existe");
            return false;
        }
        
        // Validaciones básicas
        if (mueble.getPrecio() <= 0) {
            System.err.println("El precio debe ser mayor a 0");
            return false;
        }
        
        if (mueble.getCantidadStock() < 0) {
            System.err.println("El stock no puede ser negativo");
            return false;
        }
        
        return muebleDAO.actualizar(mueble);
    }
    
    /**
     * Elimina un mueble por ID
     * @param idMueble ID del mueble
     * @return true si se eliminó correctamente
     */
    public boolean eliminarMueble(int idMueble) {
        if (idMueble <= 0) {
            System.err.println("ID de mueble inválido");
            return false;
        }
        
        // Verificar que el mueble exista
        Mueble existente = muebleDAO.obtenerPorId(idMueble);
        if (existente == null) {
            System.err.println("El mueble no existe");
            return false;
        }
        
        return muebleDAO.eliminar(idMueble);
    }
    
    /**
     * Busca un mueble por su código
     * @param codigo código del mueble
     * @return Mueble encontrado o null
     */
    public Mueble buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.err.println("El código no puede estar vacío");
            return null;
        }
        
        return muebleDAO.obtenerPorCodigo(codigo.trim());
    }
    
    /**
     * Busca un mueble por su ID
     * @param idMueble ID del mueble
     * @return Mueble encontrado o null
     */
    public Mueble buscarPorId(int idMueble) {
        if (idMueble <= 0) {
            return null;
        }
        
        return muebleDAO.obtenerPorId(idMueble);
    }
    
    /**
     * Lista todos los muebles
     * @return lista de muebles
     */
    public List<Mueble> listarTodos() {
        return muebleDAO.listarTodos();
    }
    
    /**
     * Busca muebles por nombre
     * @param nombre nombre a buscar
     * @return lista de muebles encontrados
     */
    public List<Mueble> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarTodos();
        }
        
        return muebleDAO.buscarPorNombre(nombre.trim());
    }
    
    /**
     * Busca muebles por categoría
     * @param categoria categoría a buscar
     * @return lista de muebles de esa categoría
     */
    public List<Mueble> buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            return listarTodos();
        }
        
        return muebleDAO.buscarPorCategoria(categoria);
    }
    
    /**
     * Obtiene muebles con stock bajo (menos de 5)
     * @return lista de muebles con poco stock
     */
    public List<Mueble> obtenerStockBajo() {
        return muebleDAO.obtenerStockBajo();
    }
    
    /**
     * Actualiza el stock de un mueble
     * @param idMueble ID del mueble
     * @param nuevaCantidad nueva cantidad de stock
     * @return true si se actualizó
     */
    public boolean actualizarStock(int idMueble, int nuevaCantidad) {
        if (idMueble <= 0) {
            System.err.println("ID de mueble inválido");
            return false;
        }
        
        if (nuevaCantidad < 0) {
            System.err.println("La cantidad no puede ser negativa");
            return false;
        }
        
        return muebleDAO.actualizarStock(idMueble, nuevaCantidad);
    }
    
    /**
     * Reduce el stock después de una venta
     * @param idMueble ID del mueble
     * @param cantidadVendida cantidad vendida
     * @return true si se actualizó correctamente
     */
    public boolean reducirStock(int idMueble, int cantidadVendida) {
        Mueble mueble = muebleDAO.obtenerPorId(idMueble);
        
        if (mueble == null) {
            System.err.println("El mueble no existe");
            return false;
        }
        
        if (mueble.getCantidadStock() < cantidadVendida) {
            System.err.println("Stock insuficiente. Disponible: " + mueble.getCantidadStock());
            return false;
        }
        
        int nuevoStock = mueble.getCantidadStock() - cantidadVendida;
        return muebleDAO.actualizarStock(idMueble, nuevoStock);
    } 
    
    //////////////////////////////////////////////////////////////////////////////
    
    

    
 
    
    
}
