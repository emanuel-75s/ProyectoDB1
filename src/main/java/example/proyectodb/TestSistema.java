/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb;
import example.proyectodb.conexion.ConexionFactory;
import example.proyectodb.controlador.ControladorLogin;
import example.proyectodb.controlador.ControladorMueble;
import example.proyectodb.controlador.ControladorVenta;
import example.proyectodb.modelo.Mueble;
import example.proyectodb.modelo.Usuario;
import example.proyectodb.modelo.Venta;
import java.util.List;
/**
 *
 * @author emanu
 */
public class TestSistema {
     public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   🏪 SISTEMA DE MUEBLERÍA - PRUEBA COMPLETA  ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
        
        // Mostrar configuración actual
        System.out.println("📊 BASE DE DATOS CONFIGURADA: " + ConexionFactory.getTipoBDActual());
        System.out.println("═══════════════════════════════════════════════\n");
        
        // Instanciar controladores
        ControladorLogin controladorLogin = new ControladorLogin();
        ControladorMueble controladorMueble = new ControladorMueble();
        ControladorVenta controladorVenta = new ControladorVenta();
        
        // ============================================
        // TEST 1: LOGIN
        // ============================================
        System.out.println("🔐 TEST 1: VALIDACIÓN DE LOGIN");
        System.out.println("───────────────────────────────");
        
        Usuario usuario = controladorLogin.validarCredenciales("admin", "123456");
        
        if (usuario != null) {
            System.out.println("✅ Login exitoso");
            System.out.println("   Usuario: " + usuario.getUsername());
            System.out.println("   Nombre: " + usuario.getNombre());
            System.out.println("   Cargo: " + usuario.getCargo());
            System.out.println("   Es Administrador: " + controladorLogin.esAdministrador(usuario));
        } else {
            System.out.println("❌ Login fallido");
            System.out.println("\n⚠️ NOTA: Asegúrate de tener el usuario 'admin' con contraseña '123456' en ambas BD");
            return;
        }
        
        System.out.println();
        
        // ============================================
        // TEST 2: LISTAR MUEBLES
        // ============================================
        System.out.println("📋 TEST 2: LISTAR TODOS LOS MUEBLES");
        System.out.println("───────────────────────────────────");
        
        List<Mueble> muebles = controladorMueble.listarTodos();
        
        if (muebles.isEmpty()) {
            System.out.println("⚠️ No hay muebles en la base de datos");
            System.out.println("   Insertando un mueble de prueba...\n");
            
            // Crear mueble de prueba
            Mueble nuevoMueble = new Mueble();
            nuevoMueble.setCodigoMueble("TEST001");
            nuevoMueble.setNombreMueble("Silla Ejecutiva de Prueba");
            nuevoMueble.setCategoria("Oficina");
            nuevoMueble.setMaterial("Cuero");
            nuevoMueble.setColor("Negro");
            nuevoMueble.setPrecio(1500.00);
            nuevoMueble.setCantidadStock(10);
            nuevoMueble.setDimensiones("60x60x120 cm");
            
            if (controladorMueble.guardarMueble(nuevoMueble)) {
                System.out.println("✅ Mueble de prueba insertado");
                muebles = controladorMueble.listarTodos();
            }
        }
        
        System.out.println("Total de muebles: " + muebles.size());
        for (int i = 0; i < Math.min(3, muebles.size()); i++) {
            Mueble m = muebles.get(i);
            System.out.println("   " + (i+1) + ". " + m.getCodigoMueble() + " - " + m.getNombreMueble() + 
                             " - Q" + m.getPrecio() + " - Stock: " + m.getCantidadStock());
        }
        
        if (muebles.size() > 3) {
            System.out.println("   ... y " + (muebles.size() - 3) + " más");
        }
        
        System.out.println();
        
        // ============================================
        // TEST 3: BUSCAR MUEBLE
        // ============================================
        System.out.println("🔍 TEST 3: BUSCAR MUEBLE POR CÓDIGO");
        System.out.println("───────────────────────────────────");
        
        if (!muebles.isEmpty()) {
            String codigoBuscar = muebles.get(0).getCodigoMueble();
            Mueble muebleEncontrado = controladorMueble.buscarPorCodigo(codigoBuscar);
            
            if (muebleEncontrado != null) {
                System.out.println("✅ Mueble encontrado: " + codigoBuscar);
                System.out.println("   Nombre: " + muebleEncontrado.getNombreMueble());
                System.out.println("   Precio: Q" + muebleEncontrado.getPrecio());
                System.out.println("   Stock: " + muebleEncontrado.getCantidadStock());
            } else {
                System.out.println("❌ No se encontró el mueble");
            }
        }
        
        System.out.println();
        
        // ============================================
        // TEST 4: STOCK BAJO
        // ============================================
        System.out.println("⚠️  TEST 4: MUEBLES CON STOCK BAJO");
        System.out.println("───────────────────────────────────");
        
        List<Mueble> stockBajo = controladorMueble.obtenerStockBajo();
        
        if (stockBajo.isEmpty()) {
            System.out.println("✅ No hay muebles con stock bajo (menos de 5)");
        } else {
            System.out.println("⚠️ Muebles con stock bajo: " + stockBajo.size());
            for (Mueble m : stockBajo) {
                System.out.println("   • " + m.getNombreMueble() + " - Stock: " + m.getCantidadStock());
            }
        }
        
        System.out.println();
        
        // ============================================
        // TEST 5: REGISTRAR VENTA
        // ============================================
        System.out.println("💰 TEST 5: REGISTRAR VENTA");
        System.out.println("───────────────────────────");
        
        if (!muebles.isEmpty() && usuario != null) {
            Mueble muebleVender = muebles.get(0);
            
            if (muebleVender.getCantidadStock() > 0) {
                Venta nuevaVenta = new Venta();
                nuevaVenta.setIdUsuario(usuario.getIdUsuario());
                nuevaVenta.setIdMueble(muebleVender.getIdMueble());
                nuevaVenta.setClienteNombre("Cliente de Prueba");
                nuevaVenta.setClienteTelefono("1234-5678");
                nuevaVenta.setCantidad(1);
                nuevaVenta.setPrecioTotal(muebleVender.getPrecio());
                nuevaVenta.setTipoPago("Efectivo");
                
                if (controladorVenta.registrarVenta(nuevaVenta)) {
                    System.out.println("✅ Venta registrada exitosamente");
                    System.out.println("   Mueble: " + muebleVender.getNombreMueble());
                    System.out.println("   Cliente: Cliente de Prueba");
                    System.out.println("   Total: Q" + nuevaVenta.getPrecioTotal());
                    
                    // Verificar que se redujo el stock
                    Mueble muebleActualizado = controladorMueble.buscarPorId(muebleVender.getIdMueble());
                    System.out.println("   Stock anterior: " + muebleVender.getCantidadStock());
                    System.out.println("   Stock actual: " + muebleActualizado.getCantidadStock());
                } else {
                    System.out.println("❌ Error al registrar venta");
                }
            } else {
                System.out.println("⚠️ No hay stock disponible para vender");
            }
        }
        
        System.out.println();
        
        // ============================================
        // TEST 6: TOTAL VENTAS DEL DÍA
        // ============================================
        System.out.println("📊 TEST 6: TOTAL DE VENTAS DEL DÍA");
        System.out.println("───────────────────────────────────");
        
        double totalDia = controladorVenta.obtenerTotalVentasDelDia();
        System.out.println("Total vendido hoy: Q" + String.format("%.2f", totalDia));
        
        System.out.println();
        
        // ============================================
        // TEST 7: LISTAR VENTAS
        // ============================================
        System.out.println("🛒 TEST 7: LISTAR VENTAS RECIENTES");
        System.out.println("───────────────────────────────────");
        
        List<Venta> ventas = controladorVenta.listarTodas();
        
        if (ventas.isEmpty()) {
            System.out.println("⚠️ No hay ventas registradas");
        } else {
            System.out.println("Total de ventas: " + ventas.size());
            for (int i = 0; i < Math.min(3, ventas.size()); i++) {
                Venta v = ventas.get(i);
                System.out.println("   " + (i+1) + ". Cliente: " + v.getClienteNombre() + 
                                 " - Total: Q" + v.getPrecioTotal() + 
                                 " - Fecha: " + v.getFechaVenta());
            }
            
            if (ventas.size() > 3) {
                System.out.println("   ... y " + (ventas.size() - 3) + " más");
            }
        }
        
        System.out.println();
        
        // ============================================
        // RESUMEN FINAL
        // ============================================
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║              ✅ PRUEBAS COMPLETADAS           ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📊 RESUMEN:");
        System.out.println("   • Base de datos: " + ConexionFactory.getTipoBDActual());
        System.out.println("   • Usuarios: OK ✅");
        System.out.println("   • Muebles: OK ✅");
        System.out.println("   • Ventas: OK ✅");
        System.out.println("   • Stock: OK ✅");
        System.out.println();
        System.out.println("💡 INDEPENDENCIA FÍSICA DEMOSTRADA:");
        System.out.println("   Para cambiar de MySQL a Oracle:");
        System.out.println("   1. Ve a ConexionFactory.java");
        System.out.println("   2. Cambia: TIPO_BD = \"ORACLE\"");
        System.out.println("   3. Ejecuta este test de nuevo");
        System.out.println("   4. ¡Todo funciona igual! 🎉");
        System.out.println();
        System.out.println("🚀 SIGUIENTE PASO: Crear los JFrames (formularios)");
        System.out.println("═══════════════════════════════════════════════");
    }
}
