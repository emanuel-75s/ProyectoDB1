/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.conexion.ConexionMySQL;
import example.proyectodb.conexion.ConexionOracle;
import example.proyectodb.conexion.IConexion;
import example.proyectodb.modelo.Usuario;
import java.sql.*;

/**
 *
 * @author emanu
 */
public class UsuarioDAO {
     
    /**
     * Obtiene conexión según BD configurada
     */
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
    
    public Usuario validarLogin(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username=? AND password=? AND activo=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            if (getTipoBD().contains("ORACLE")) {
                pstmt.setInt(3, 1);
            } else {
                pstmt.setBoolean(3, true);
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCargo(rs.getString("cargo"));
                
                if (getTipoBD().contains("ORACLE")) {
                    usuario.setActivo(rs.getInt("activo") == 1);
                } else {
                    usuario.setActivo(rs.getBoolean("activo"));
                }
                
                System.out.println(" Login exitoso");
                return usuario;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error en login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, nombre, cargo, activo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getCargo());
            
            if (getTipoBD().contains("ORACLE")) {
                pstmt.setInt(5, usuario.isActivo() ? 1 : 0);
            } else {
                pstmt.setBoolean(5, usuario.isActivo());
            }
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Usuario insertado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET username=?, password=?, nombre=?, cargo=?, activo=? WHERE id_usuario=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getCargo());
            
            if (getTipoBD().contains("ORACLE")) {
                pstmt.setInt(5, usuario.isActivo() ? 1 : 0);
            } else {
                pstmt.setBoolean(5, usuario.isActivo());
            }
            
            pstmt.setInt(6, usuario.getIdUsuario());
            
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Usuario actualizado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println(" Usuario eliminado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println(" Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public Usuario obtenerPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCargo(rs.getString("cargo"));
                
                if (getTipoBD().contains("ORACLE")) {
                    usuario.setActivo(rs.getInt("activo") == 1);
                } else {
                    usuario.setActivo(rs.getBoolean("activo"));
                }
                
                return usuario;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al obtener usuario: " + e.getMessage());
        }
        
        return null;
    }
}
