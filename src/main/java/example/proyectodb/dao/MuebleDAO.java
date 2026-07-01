/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.conexion.ConexionMySQL;
import example.proyectodb.conexion.ConexionOracle;
import example.proyectodb.conexion.IConexion;
import example.proyectodb.modelo.Mueble;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emanu
 */
public class MuebleDAO {
     private Connection getConnection() {
        String tipoBD = ConexionFactory.getTipoBDActual();
        if (tipoBD.equals("MYSQL")) {
            return ConexionMySQL.getConexion();
        } else {
            return ConexionOracle.getConexion();
        }
    }
    
    public boolean insertar(Mueble mueble) {
        String sql = "INSERT INTO muebles (codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mueble.getCodigoMueble());
            pstmt.setString(2, mueble.getNombreMueble());
            pstmt.setString(3, mueble.getCategoria());
            pstmt.setString(4, mueble.getMaterial());
            pstmt.setString(5, mueble.getColor());
            pstmt.setDouble(6, mueble.getPrecio());
            pstmt.setInt(7, mueble.getCantidadStock());
            pstmt.setString(8, mueble.getDimensiones());
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Mueble insertado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al insertar mueble: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizar(Mueble mueble) {
        String sql = "UPDATE muebles SET codigo_mueble=?, nombre_mueble=?, categoria=?, material=?, " +
                     "color=?, precio=?, cantidad_stock=?, dimensiones=? WHERE id_mueble=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mueble.getCodigoMueble());
            pstmt.setString(2, mueble.getNombreMueble());
            pstmt.setString(3, mueble.getCategoria());
            pstmt.setString(4, mueble.getMaterial());
            pstmt.setString(5, mueble.getColor());
            pstmt.setDouble(6, mueble.getPrecio());
            pstmt.setInt(7, mueble.getCantidadStock());
            pstmt.setString(8, mueble.getDimensiones());
            pstmt.setInt(9, mueble.getIdMueble());
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Mueble actualizado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al actualizar mueble: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int idMueble) {
        String sql = "DELETE FROM muebles WHERE id_mueble=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idMueble);
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Mueble eliminado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al eliminar mueble: " + e.getMessage());
            return false;
        }
    }
    
    public Mueble obtenerPorId(int idMueble) {
        String sql = "SELECT id_mueble, codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones FROM muebles WHERE id_mueble=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idMueble);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearMueble(rs);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener mueble: " + e.getMessage());
        }
        
        return null;
    }
    
    public Mueble obtenerPorCodigo(String codigo) {
        String sql = "SELECT id_mueble, codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones FROM muebles WHERE codigo_mueble=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearMueble(rs);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar por código: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Mueble> listarTodos() {
        List<Mueble> lista = new ArrayList<>();
        String sql = "SELECT id_mueble, codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones FROM muebles ORDER BY nombre_mueble";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(mapearMueble(rs));
            }
            
            System.out.println(" Listados " + lista.size() + " muebles");
            
        } catch (SQLException e) {
            System.err.println(" Error al listar muebles: " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Mueble> buscarPorNombre(String nombre) {
        List<Mueble> lista = new ArrayList<>();
        String sql = "SELECT id_mueble, codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones FROM muebles WHERE nombre_mueble LIKE ? ORDER BY nombre_mueble";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearMueble(rs));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar por nombre: " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Mueble> buscarPorCategoria(String categoria) {
        List<Mueble> lista = new ArrayList<>();
        String sql = "SELECT id_mueble, codigo_mueble, nombre_mueble, categoria, material, " +
                     "color, precio, cantidad_stock, dimensiones FROM muebles WHERE categoria=? ORDER BY nombre_mueble";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearMueble(rs));
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar por categoría: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * ✅ MÉTODO CON INDEPENDENCIA LÓGICA
     * Obtiene muebles con stock bajo usando VISTA
     * La lógica de "stock < 5" está en la BD, no en Java
     */
    public List<Mueble> obtenerStockBajo() {
        List<Mueble> lista = new ArrayList<>();
        // ✅ INDEPENDENCIA LÓGICA: La vista ya filtra cantidad_stock < 5
        String sql = "SELECT * FROM vista_stock_bajo";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Mueble mueble = new Mueble();
                mueble.setIdMueble(rs.getInt("id_mueble"));
                mueble.setCodigoMueble(rs.getString("codigo_mueble"));
                mueble.setNombreMueble(rs.getString("nombre_mueble"));
                mueble.setCategoria(rs.getString("categoria"));
                mueble.setCantidadStock(rs.getInt("cantidad_stock"));
                mueble.setPrecio(rs.getDouble("precio"));
                
                lista.add(mueble);
            }
            
            System.out.println(" " + lista.size() + " muebles con stock bajo desde VISTA");
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener stock bajo: " + e.getMessage());
        }
        
        return lista;
    }
    
    public boolean actualizarStock(int idMueble, int nuevaCantidad) {
        String sql = "UPDATE muebles SET cantidad_stock=? WHERE id_mueble=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, idMueble);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
    
    private Mueble mapearMueble(ResultSet rs) throws SQLException {
        Mueble mueble = new Mueble();
        mueble.setIdMueble(rs.getInt("id_mueble"));
        mueble.setCodigoMueble(rs.getString("codigo_mueble"));
        mueble.setNombreMueble(rs.getString("nombre_mueble"));
        mueble.setCategoria(rs.getString("categoria"));
        mueble.setMaterial(rs.getString("material"));
        mueble.setColor(rs.getString("color"));
        mueble.setPrecio(rs.getDouble("precio"));
        mueble.setCantidadStock(rs.getInt("cantidad_stock"));
        mueble.setDimensiones(rs.getString("dimensiones"));
        return mueble;
    }
}
