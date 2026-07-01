/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao.VistaOracleDAO;
import example.proyectodb.conexion.ConexionOracle;
import example.proyectodb.dao.IVistaDAO;
import example.proyectodb.modelo.ResumenInventario;
import example.proyectodb.modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author emanu
 */
public class VistaOracleDAO implements IVistaDAO{
   // @Override
    public List<Venta> obtenerVentasHoy() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_hoy ORDER BY fecha_venta DESC";
        
        try (Connection conn = ConexionOracle.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setClienteNombre(rs.getString("cliente_nombre"));
                venta.setCantidad(rs.getInt("cantidad"));
                venta.setPrecioTotal(rs.getDouble("precio_total"));
                venta.setFechaVenta(rs.getTimestamp("fecha_venta"));
                venta.setNombreMueble(rs.getString("nombre_mueble"));
                venta.setNombreUsuario(rs.getString("vendedor"));
                
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas de hoy desde vista: " + e.getMessage());
        }
        
        return ventas;
    }
    
   // @Override
    public List<ResumenInventario> obtenerResumenInventario() {
        List<ResumenInventario> resumenes = new ArrayList<>();
        String sql = "SELECT * FROM vista_resumen_inventario ORDER BY categoria";
        
        try (Connection conn = ConexionOracle.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                ResumenInventario resumen = new ResumenInventario();
                resumen.setCategoria(rs.getString("categoria"));
                resumen.setTotalMuebles(rs.getInt("total_muebles"));
                resumen.setStockTotal(rs.getInt("stock_total"));
                resumen.setPrecioPromedio(rs.getDouble("precio_promedio"));
                resumen.setPrecioMinimo(rs.getDouble("precio_minimo"));
                resumen.setPrecioMaximo(rs.getDouble("precio_maximo"));
                resumen.setValorInventario(rs.getDouble("valor_inventario"));
                
                resumenes.add(resumen);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener resumen de inventario: " + e.getMessage());
        }
        
        return resumenes;
    }
    
   // @Override
    public double obtenerTotalVentasHoy() {
        double total = 0.0;
        String sql = "SELECT NVL(SUM(precio_total), 0) AS total FROM vista_ventas_hoy";
        
        try (Connection conn = ConexionOracle.getConexion();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total: " + e.getMessage());
        }
        
        return total;
    } 
}
