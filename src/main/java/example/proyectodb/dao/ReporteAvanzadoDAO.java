/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.dao;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.conexion.ConexionMySQL;
import example.proyectodb.conexion.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author emanu
 */
public class ReporteAvanzadoDAO {
 
     private Connection getConnection() {
        String tipoBD = ConexionFactory.getTipoBDActual();
        if (tipoBD.equals("MYSQL")) {
            return ConexionMySQL.getConexion();
        } else {
            return ConexionOracle.getConexion();
        }
    }
    
    /**
     * Obtiene ranking de muebles más vendidos
     * ✅ INDEPENDENCIA LÓGICA: Usa vista_muebles_mas_vendidos
     * La vista calcula: COUNT, SUM, ordenamiento
     */
    public List<Map<String, Object>> obtenerMueblesMasVendidos(int limite) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_muebles_mas_vendidos";
        
        String tipoBD = ConexionFactory.getTipoBDActual();
        if (tipoBD.equals("MYSQL")) {
            sql += " LIMIT " + limite;
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int contador = 0;
            while (rs.next() && (tipoBD.equals("MYSQL") || contador < limite)) {
                Map<String, Object> item = new HashMap<>();
                item.put("codigo_mueble", rs.getString("codigo_mueble"));
                item.put("nombre_mueble", rs.getString("nombre_mueble"));
                item.put("categoria", rs.getString("categoria"));
                item.put("precio", rs.getDouble("precio"));
                item.put("veces_vendido", rs.getInt("veces_vendido"));
                item.put("unidades_vendidas", rs.getInt("unidades_vendidas"));
                item.put("ingresos_generados", rs.getDouble("ingresos_generados"));
                
                lista.add(item);
                contador++;
            }
            
            System.out.println(" Top " + lista.size() + " muebles desde VISTA");
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Obtiene desempeño de vendedores
     * ✅ INDEPENDENCIA LÓGICA: Usa vista_desempeno_vendedores
     * La vista calcula métricas automáticamente
     */
    public List<Map<String, Object>> obtenerDesempenoVendedores() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_desempeno_vendedores";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id_usuario", rs.getInt("id_usuario"));
                item.put("vendedor", rs.getString("vendedor"));
                item.put("cargo", rs.getString("cargo"));
                item.put("total_ventas", rs.getInt("total_ventas"));
                item.put("unidades_vendidas", rs.getInt("unidades_vendidas"));
                item.put("ingresos_generados", rs.getDouble("ingresos_generados"));
                item.put("ticket_promedio", rs.getDouble("ticket_promedio"));
                
                Timestamp ultimaVenta = rs.getTimestamp("ultima_venta");
                item.put("ultima_venta", ultimaVenta != null ? ultimaVenta.toString() : "Sin ventas");
                
                lista.add(item);
            }
            
            System.out.println(" Desempeño de " + lista.size() + " vendedores desde VISTA");
            
        } catch (SQLException e) {
            System.err.println(" Error: " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Map<String, Object>> obtenerAnalisisClientes(int limite) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_por_cliente";
        
        String tipoBD = ConexionFactory.getTipoBDActual();
        if (tipoBD.equals("MYSQL")) {
            sql += " LIMIT " + limite;
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int contador = 0;
            while (rs.next() && (tipoBD.equals("MYSQL") || contador < limite)) {
                Map<String, Object> item = new HashMap<>();
                item.put("cliente_nombre", rs.getString("cliente_nombre"));
                item.put("cliente_telefono", rs.getString("cliente_telefono"));
                item.put("total_compras", rs.getInt("total_compras"));
                item.put("total_unidades", rs.getInt("total_unidades"));
                item.put("total_gastado", rs.getDouble("total_gastado"));
                item.put("ultima_compra", rs.getTimestamp("ultima_compra"));
                item.put("categorias_compradas", rs.getString("categorias_compradas"));
                
                lista.add(item);
                contador++;
            }
            
            System.out.println(" Análisis de " + lista.size() + " clientes desde VISTA");
            
        } catch (SQLException e) {
            System.err.println(" Error: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Obtiene resumen de inventario por categoría
     * ✅ INDEPENDENCIA LÓGICA: Usa vista_resumen_inventario
     */
    public List<Map<String, Object>> obtenerResumenInventario() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT * FROM vista_resumen_inventario";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("categoria", rs.getString("categoria"));
                item.put("total_muebles", rs.getInt("total_muebles"));
                item.put("stock_total", rs.getInt("stock_total"));
                item.put("precio_promedio", rs.getDouble("precio_promedio"));
                item.put("precio_minimo", rs.getDouble("precio_minimo"));
                item.put("precio_maximo", rs.getDouble("precio_maximo"));
                item.put("valor_inventario", rs.getDouble("valor_inventario"));
                
                lista.add(item);
            }
            
            System.out.println(" Resumen de " + lista.size() + " categorías desde VISTA");
            
        } catch (SQLException e) {
            System.err.println(" Error: " + e.getMessage());
        }
        
        return lista;
    }
    
    
}
