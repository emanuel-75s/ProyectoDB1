/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.conexion.ConexionMySQL;
import example.proyectodb.conexion.ConexionOracle;
import example.proyectodb.conexion.IConexion;
import example.proyectodb.modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author emanu
 */
public class VentaDAO {
   
   private Connection getConnection() {
        String tipoBD = ConexionFactory.getTipoBDActual();
        if (tipoBD.equals("MYSQL")) {
            return ConexionMySQL.getConexion();
        } else {
            return ConexionOracle.getConexion();
        }
    }
    
    private String getTipoBD() {
        return ConexionFactory.getTipoBDActual();
    }
    
    public boolean insertar(Venta venta) {
        String sql = "INSERT INTO ventas_muebles (id_usuario, id_mueble, cliente_nombre, " +
                     "cliente_telefono, cantidad, precio_total, tipo_pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, venta.getIdUsuario());
            pstmt.setInt(2, venta.getIdMueble());
            pstmt.setString(3, venta.getClienteNombre());
            pstmt.setString(4, venta.getClienteTelefono());
            pstmt.setInt(5, venta.getCantidad());
            pstmt.setDouble(6, venta.getPrecioTotal());
            pstmt.setString(7, venta.getTipoPago());
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Venta registrada");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al registrar venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Venta obtenerPorId(int idVenta) {
        // ✅ INDEPENDENCIA LÓGICA: Usa vista en lugar de JOIN manual
        String sql = "SELECT * FROM vista_ventas_completas WHERE id_venta = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idVenta);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearVentaDesdeVista(rs);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener venta: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Lista todas las ventas usando VISTA
     * ✅ INDEPENDENCIA LÓGICA - Antes tenía JOINs, ahora usa vista
     */
    public List<Venta> listarTodas() {
        List<Venta> lista = new ArrayList<>();
        // ✅ ANTES: SELECT con JOINs de 3 tablas (8 líneas)
        // ✅ AHORA: SELECT simple desde vista (1 línea)
        String sql = "SELECT * FROM vista_ventas_completas";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(mapearVentaDesdeVista(rs));
            }
            
            System.out.println(" Listadas " + lista.size() + " ventas desde VISTA");
            
        } catch (SQLException e) {
            System.err.println(" Error al listar ventas: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Busca ventas por cliente usando VISTA
     * ✅ INDEPENDENCIA LÓGICA
     */
    public List<Venta> buscarPorCliente(String nombreCliente) {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_completas WHERE cliente_nombre LIKE ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombreCliente + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearVentaDesdeVista(rs));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar ventas por cliente: " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Venta> obtenerPorFecha(Date fecha) {
        List<Venta> lista = new ArrayList<>();
        
        String sql;
        if (getTipoBD().contains("ORACLE")) {
            sql = "SELECT * FROM vista_ventas_completas WHERE TRUNC(fecha_venta) = TRUNC(?)";
        } else {
            sql = "SELECT * FROM vista_ventas_completas WHERE DATE(fecha_venta) = DATE(?)";
        }
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, new java.sql.Date(fecha.getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearVentaDesdeVista(rs));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ventas por fecha: " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Venta> obtenerPorRangoFechas(Date fechaInicio, Date fechaFin) {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_completas WHERE fecha_venta BETWEEN ? AND ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(2, new java.sql.Date(fechaFin.getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearVentaDesdeVista(rs));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener ventas por rango: " + e.getMessage());
        }
        
        return lista;
    }
    
    public double obtenerTotalVentasDelDia() {
        double total = 0.0;
        
        String sql;
        if (getTipoBD().contains("ORACLE")) {
            sql = "SELECT NVL(SUM(precio_total), 0) AS total " +
                  "FROM ventas_muebles WHERE TRUNC(fecha_venta) = TRUNC(SYSDATE)";
        } else {
            sql = "SELECT IFNULL(SUM(precio_total), 0) AS total " +
                  "FROM ventas_muebles WHERE DATE(fecha_venta) = CURDATE()";
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total del día: " + e.getMessage());
        }
        
        return total;
    }
    
    public boolean eliminar(int idVenta) {
        String sql = "DELETE FROM ventas_muebles WHERE id_venta=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idVenta);
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Venta eliminada");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al eliminar venta: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mapea datos desde la VISTA (con JOINs ya resueltos)
     * ✅ INDEPENDENCIA LÓGICA - Los datos ya vienen completos
     */
    private Venta mapearVentaDesdeVista(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setIdVenta(rs.getInt("id_venta"));
        venta.setIdUsuario(rs.getInt("id_usuario"));
        venta.setIdMueble(rs.getInt("id_mueble"));
        venta.setClienteNombre(rs.getString("cliente_nombre"));
        venta.setClienteTelefono(rs.getString("cliente_telefono"));
        venta.setCantidad(rs.getInt("cantidad"));
        venta.setPrecioTotal(rs.getDouble("precio_total"));
        venta.setTipoPago(rs.getString("tipo_pago"));
        
        if (getTipoBD().contains("ORACLE")) {
            Timestamp timestamp = rs.getTimestamp("fecha_venta");
            venta.setFechaVenta(new Date(timestamp.getTime()));
        } else {
            venta.setFechaVenta(rs.getTimestamp("fecha_venta"));
        }
        
        // ✅ ESTOS CAMPOS VIENEN DE LA VISTA (no hay JOIN en Java)
        venta.setNombreMueble(rs.getString("nombre_mueble"));
        venta.setNombreUsuario(rs.getString("vendedor"));
        
        return venta;
    }

}
