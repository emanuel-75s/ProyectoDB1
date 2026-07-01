/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.controlador;
import example.proyectodb.dao.VentaDAO;
import example.proyectodb.modelo.Venta;
import java.util.Date;
import java.util.List;
/**
 *
 * @author emanu
 */
public class ControladorVenta {
    private VentaDAO ventaDAO;
    private ControladorMueble controladorMueble;
    
    public ControladorVenta() {
        this.ventaDAO = new VentaDAO();
        this.controladorMueble = new ControladorMueble();
    }
    
    /**
     * Registra una nueva venta
     * Incluye validaciones y actualización automática de stock
     * @param venta objeto Venta
     * @return true si se registró correctamente
     */
    public boolean registrarVenta(Venta venta) {
        // Validaciones
        if (venta.getIdUsuario() <= 0) {
            System.err.println("Usuario inválido");
            return false;
        }
        
        if (venta.getIdMueble() <= 0) {
            System.err.println("Mueble inválido");
            return false;
        }
        
        if (venta.getClienteNombre() == null || venta.getClienteNombre().trim().isEmpty()) {
            System.err.println("El nombre del cliente es obligatorio");
            return false;
        }
        
        if (venta.getCantidad() <= 0) {
            System.err.println("La cantidad debe ser mayor a 0");
            return false;
        }
        
        if (venta.getPrecioTotal() <= 0) {
            System.err.println("El precio total debe ser mayor a 0");
            return false;
        }
        
        if (venta.getTipoPago() == null || venta.getTipoPago().trim().isEmpty()) {
            System.err.println("Debe seleccionar un tipo de pago");
            return false;
        }
        
        // Verificar y reducir stock
        boolean stockActualizado = controladorMueble.reducirStock(venta.getIdMueble(), venta.getCantidad());
        
        if (!stockActualizado) {
            System.err.println("No se pudo actualizar el stock");
            return false;
        }
        
        // Registrar la venta
        boolean ventaRegistrada = ventaDAO.insertar(venta);
        
        if (ventaRegistrada) {
            System.out.println("Venta registrada exitosamente");
            return true;
        } else {
            // Si falla la venta, revertir el stock (compensación)
            System.err.println("Error al registrar venta, revirtiendo stock...");
            // Aquí podrías implementar lógica para revertir el stock
            return false;
        }
    }
    
    /**
     * Obtiene una venta por ID
     * @param idVenta ID de la venta
     * @return objeto Venta o null
     */
    public Venta obtenerPorId(int idVenta) {
        if (idVenta <= 0) {
            return null;
        }
        
        return ventaDAO.obtenerPorId(idVenta);
    }
    
    /**
     * Lista todas las ventas
     * @return lista de ventas
     */
    public List<Venta> listarTodas() {
        return ventaDAO.listarTodas();
    }
    
    /**
     * Busca ventas por nombre de cliente
     * @param nombreCliente nombre a buscar
     * @return lista de ventas
     */
    public List<Venta> buscarPorCliente(String nombreCliente) {
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            return listarTodas();
        }
        
        return ventaDAO.buscarPorCliente(nombreCliente.trim());
    }
    
    /**
     * Obtiene ventas de una fecha específica
     * @param fecha fecha a buscar
     * @return lista de ventas
     */
    public List<Venta> obtenerPorFecha(Date fecha) {
        if (fecha == null) {
            return listarTodas();
        }
        
        return ventaDAO.obtenerPorFecha(fecha);
    }
    
    /**
     * Obtiene ventas en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de ventas
     */
    public List<Venta> obtenerPorRangoFechas(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return listarTodas();
        }
        
        return ventaDAO.obtenerPorRangoFechas(fechaInicio, fechaFin);
    }
    
    /**
     * Obtiene el total de ventas del día actual
     * @return monto total
     */
    public double obtenerTotalVentasDelDia() {
        return ventaDAO.obtenerTotalVentasDelDia();
    }
    
    /**
     * Cancela/elimina una venta
     * @param idVenta ID de la venta
     * @return true si se eliminó
     */
    public boolean cancelarVenta(int idVenta) {
        if (idVenta <= 0) {
            System.err.println("ID de venta inválido");
            return false;
        }
        
        // Aquí podrías agregar lógica para devolver el stock
        
        return ventaDAO.eliminar(idVenta);
    }
    
    
    
}
