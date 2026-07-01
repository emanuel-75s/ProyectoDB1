/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.util;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import example.proyectodb.modelo.Mueble;
import example.proyectodb.modelo.Venta;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author emanu
 */
public class GeneradorPDF {
    
     private static final BaseColor COLOR_HEADER = new BaseColor(41, 128, 185); // Azul
    private static final BaseColor COLOR_ALTERNADO = new BaseColor(236, 240, 241); // Gris claro
    
    /**
     * Genera un PDF con el reporte de ventas
     * @param ventas Lista de ventas a incluir en el reporte
     * @param totalDelDia Total de ventas del día actual
     * @return true si se generó exitosamente, false en caso contrario
     */
    public static boolean generarReporteVentas(List<Venta> ventas, double totalDelDia) {
        try {
            // Abrir diálogo para guardar archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte de Ventas");
            fileChooser.setSelectedFile(new java.io.File("Reporte_Ventas_" + 
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".pdf"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                rutaArchivo += ".pdf";
            }
            
            // Crear documento
            Document documento = new Document(PageSize.LETTER);
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();
            
            // Título
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, COLOR_HEADER);
            Paragraph titulo = new Paragraph("REPORTE DE VENTAS", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            documento.add(titulo);
            
            // Subtítulo - Sistema
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.ITALIC);
            Paragraph subtitulo = new Paragraph("Sistema de Mueblería", fuenteSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(5);
            documento.add(subtitulo);
            
            // Fecha del reporte
            Font fuenteFecha = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);
            Paragraph fecha = new Paragraph("Generado: " + 
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()), fuenteFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            fecha.setSpacingAfter(20);
            documento.add(fecha);
            
            // Línea separadora
            documento.add(new Paragraph(" "));
            
            // Resumen
            Font fuenteResumen = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph resumen = new Paragraph();
            resumen.add(new Chunk("Total de ventas: ", fuenteResumen));
            resumen.add(new Chunk(String.valueOf(ventas.size())));
            resumen.add(new Chunk("\nVentas del día: ", fuenteResumen));
            resumen.add(new Chunk(String.format("Q %.2f", totalDelDia)));
            resumen.setSpacingAfter(15);
            documento.add(resumen);
            
            // Crear tabla
            PdfPTable tabla = new PdfPTable(6); // 6 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10);
            tabla.setSpacingAfter(10);
            
            // Anchos de columnas
            float[] anchos = {0.8f, 2.5f, 2.5f, 1f, 1.5f, 2f};
            tabla.setWidths(anchos);
            
            // Headers
            String[] headers = {"ID", "Cliente", "Mueble", "Cant.", "Total", "Fecha"};
            Font fuenteHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Font.BOLD, BaseColor.WHITE);
            
            for (String header : headers) {
                PdfPCell celda = new PdfPCell(new Phrase(header, fuenteHeader));
                celda.setBackgroundColor(COLOR_HEADER);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(8);
                tabla.addCell(celda);
            }
            
            // Datos
            Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 9);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            boolean alternar = false;
            
            for (Venta v : ventas) {
                BaseColor colorFondo = alternar ? COLOR_ALTERNADO : BaseColor.WHITE;
                
                agregarCeldaTabla(tabla, String.valueOf(v.getIdVenta()), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                agregarCeldaTabla(tabla, v.getClienteNombre(), fuenteDatos, colorFondo, Element.ALIGN_LEFT);
                agregarCeldaTabla(tabla, v.getNombreMueble() != null ? v.getNombreMueble() : "N/A", fuenteDatos, colorFondo, Element.ALIGN_LEFT);
                agregarCeldaTabla(tabla, String.valueOf(v.getCantidad()), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                agregarCeldaTabla(tabla, String.format("Q %.2f", v.getPrecioTotal()), fuenteDatos, colorFondo, Element.ALIGN_RIGHT);
                agregarCeldaTabla(tabla, sdf.format(v.getFechaVenta()), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                
                alternar = !alternar;
            }
            
            documento.add(tabla);
            
            // Total general
            double totalGeneral = ventas.stream().mapToDouble(Venta::getPrecioTotal).sum();
            Font fuenteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, COLOR_HEADER);
            Paragraph totalParrafo = new Paragraph();
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            totalParrafo.add(new Chunk("TOTAL GENERAL: Q " + String.format("%.2f", totalGeneral), fuenteTotal));
            totalParrafo.setSpacingBefore(10);
            documento.add(totalParrafo);
            
            // Pie de página
            Font fuentePie = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            Paragraph pie = new Paragraph("Sistema de Mueblería - Reporte Generado Automáticamente", fuentePie);
            pie.setAlignment(Element.ALIGN_CENTER);
            pie.setSpacingBefore(30);
            documento.add(pie);
            
            documento.close();
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al generar PDF: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Genera un PDF con el reporte de muebles
     * @param muebles Lista de muebles a incluir en el reporte
     * @param tipoReporte Tipo de reporte (Todos, StockBajo, etc.)
     * @return true si se generó exitosamente, false en caso contrario
     */
    public static boolean generarReporteMuebles(List<Mueble> muebles, String tipoReporte) {
        try {
            // Abrir diálogo para guardar archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte de Muebles");
            fileChooser.setSelectedFile(new java.io.File("Reporte_Muebles_" + 
                tipoReporte + "_" + 
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".pdf"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                rutaArchivo += ".pdf";
            }
            
            // Crear documento
            Document documento = new Document(PageSize.LETTER, 36, 36, 54, 36);
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();
            
            // Título
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, COLOR_HEADER);
            Paragraph titulo = new Paragraph("REPORTE DE MUEBLES - " + tipoReporte.toUpperCase(), fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            documento.add(titulo);
            
            // Subtítulo
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.ITALIC);
            Paragraph subtitulo = new Paragraph("Sistema de Mueblería", fuenteSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(5);
            documento.add(subtitulo);
            
            // Fecha del reporte
            Font fuenteFecha = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);
            Paragraph fecha = new Paragraph("Generado: " + 
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()), fuenteFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            fecha.setSpacingAfter(20);
            documento.add(fecha);
            
            // Resumen
            Font fuenteResumen = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            double valorTotal = muebles.stream().mapToDouble(m -> m.getPrecio() * m.getCantidadStock()).sum();
            int stockTotal = muebles.stream().mapToInt(Mueble::getCantidadStock).sum();
            
            Paragraph resumen = new Paragraph();
            resumen.add(new Chunk("Total de muebles: ", fuenteResumen));
            resumen.add(new Chunk(String.valueOf(muebles.size())));
            resumen.add(new Chunk("\nStock total: ", fuenteResumen));
            resumen.add(new Chunk(String.valueOf(stockTotal) + " unidades"));
            resumen.add(new Chunk("\nValor del inventario: ", fuenteResumen));
            resumen.add(new Chunk(String.format("Q %.2f", valorTotal)));
            resumen.setSpacingAfter(15);
            documento.add(resumen);
            
            // Crear tabla
            PdfPTable tabla = new PdfPTable(7); // 7 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10);
            tabla.setSpacingAfter(10);
            
            // Anchos de columnas
            float[] anchos = {1f, 2.5f, 1.5f, 1.5f, 1f, 1f, 1.5f};
            tabla.setWidths(anchos);
            
            // Headers
            String[] headers = {"Código", "Nombre", "Categoría", "Material", "Precio", "Stock", "Valor Total"};
            Font fuenteHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Font.BOLD, BaseColor.WHITE);
            
            for (String header : headers) {
                PdfPCell celda = new PdfPCell(new Phrase(header, fuenteHeader));
                celda.setBackgroundColor(COLOR_HEADER);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(6);
                tabla.addCell(celda);
            }
            
            // Datos
            Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 8);
            boolean alternar = false;
            
            for (Mueble m : muebles) {
                BaseColor colorFondo = alternar ? COLOR_ALTERNADO : BaseColor.WHITE;
                double valorMueble = m.getPrecio() * m.getCantidadStock();
                
                agregarCeldaTabla(tabla, m.getCodigoMueble(), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                agregarCeldaTabla(tabla, m.getNombreMueble(), fuenteDatos, colorFondo, Element.ALIGN_LEFT);
                agregarCeldaTabla(tabla, m.getCategoria(), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                agregarCeldaTabla(tabla, m.getMaterial(), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                agregarCeldaTabla(tabla, String.format("Q%.2f", m.getPrecio()), fuenteDatos, colorFondo, Element.ALIGN_RIGHT);
                
                // Resaltar stock bajo en rojo
                if (m.getCantidadStock() <= 5) {
                    Font fuenteRoja = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.RED);
                    agregarCeldaTabla(tabla, String.valueOf(m.getCantidadStock()), fuenteRoja, colorFondo, Element.ALIGN_CENTER);
                } else {
                    agregarCeldaTabla(tabla, String.valueOf(m.getCantidadStock()), fuenteDatos, colorFondo, Element.ALIGN_CENTER);
                }
                
                agregarCeldaTabla(tabla, String.format("Q%.2f", valorMueble), fuenteDatos, colorFondo, Element.ALIGN_RIGHT);
                
                alternar = !alternar;
            }
            
            documento.add(tabla);
            
            // Total general
            Font fuenteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, COLOR_HEADER);
            Paragraph totalParrafo = new Paragraph();
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            totalParrafo.add(new Chunk("VALOR TOTAL DEL INVENTARIO: Q " + String.format("%.2f", valorTotal), fuenteTotal));
            totalParrafo.setSpacingBefore(10);
            documento.add(totalParrafo);
            
            // Pie de página
            Font fuentePie = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            Paragraph pie = new Paragraph("Sistema de Mueblería - Reporte Generado Automáticamente", fuentePie);
            pie.setAlignment(Element.ALIGN_CENTER);
            pie.setSpacingBefore(30);
            documento.add(pie);
            
            documento.close();
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al generar PDF: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Genera un PDF con el resumen general del sistema
     * @param ventas Lista de todas las ventas
     * @param muebles Lista de todos los muebles
     * @param stockBajo Lista de muebles con stock bajo
     * @param totalVentasHoy Total de ventas del día actual
     * @return true si se generó exitosamente, false en caso contrario
     */
    public static boolean generarResumenGeneral(
            List<Venta> ventas, 
            List<Mueble> muebles, 
            List<Mueble> stockBajo,
            double totalVentasHoy) {
        try {
            // Abrir diálogo para guardar archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Resumen General");
            fileChooser.setSelectedFile(new java.io.File("Resumen_General_" + 
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".pdf"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
            fileChooser.setFileFilter(filter);
            
            int seleccion = fileChooser.showSaveDialog(null);
            
            if (seleccion != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                rutaArchivo += ".pdf";
            }
            
            // Crear documento
            Document documento = new Document(PageSize.LETTER);
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();
            
            // Título principal
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, COLOR_HEADER);
            Paragraph titulo = new Paragraph("RESUMEN GENERAL DEL SISTEMA", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15);
            documento.add(titulo);
            
            // Subtítulo
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.ITALIC);
            Paragraph subtitulo = new Paragraph("Sistema de Mueblería", fuenteSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(5);
            documento.add(subtitulo);
            
            // Fecha del reporte
            Font fuenteFecha = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.ITALIC);
            Paragraph fecha = new Paragraph("Generado: " + 
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()), fuenteFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            fecha.setSpacingAfter(25);
            documento.add(fecha);
            
            // === SECCIÓN DE VENTAS ===
            Font fuenteSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, COLOR_HEADER);
            Paragraph seccionVentas = new Paragraph("📊 VENTAS", fuenteSeccion);
            seccionVentas.setSpacingAfter(10);
            documento.add(seccionVentas);
            
            double totalGeneral = ventas.stream().mapToDouble(Venta::getPrecioTotal).sum();
            
            Font fuenteDatos = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fuenteNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            
            Paragraph datosVentas = new Paragraph();
            datosVentas.add(new Chunk("• Total de ventas realizadas: ", fuenteDatos));
            datosVentas.add(new Chunk(String.valueOf(ventas.size()) + "\n", fuenteNegrita));
            datosVentas.add(new Chunk("• Monto total acumulado: ", fuenteDatos));
            datosVentas.add(new Chunk(String.format("Q %.2f\n", totalGeneral), fuenteNegrita));
            datosVentas.add(new Chunk("• Ventas del día de hoy: ", fuenteDatos));
            datosVentas.add(new Chunk(String.format("Q %.2f\n", totalVentasHoy), fuenteNegrita));
            datosVentas.add(new Chunk("• Promedio por venta: ", fuenteDatos));
            datosVentas.add(new Chunk(String.format("Q %.2f\n", ventas.isEmpty() ? 0 : totalGeneral / ventas.size()), fuenteNegrita));
            datosVentas.setSpacingAfter(20);
            documento.add(datosVentas);
            
            // === SECCIÓN DE INVENTARIO ===
            Paragraph seccionInventario = new Paragraph("📦 INVENTARIO", fuenteSeccion);
            seccionInventario.setSpacingAfter(10);
            documento.add(seccionInventario);
            
            double valorInventario = muebles.stream().mapToDouble(m -> m.getPrecio() * m.getCantidadStock()).sum();
            int stockTotal = muebles.stream().mapToInt(Mueble::getCantidadStock).sum();
            
            Paragraph datosInventario = new Paragraph();
            datosInventario.add(new Chunk("• Total de muebles: ", fuenteDatos));
            datosInventario.add(new Chunk(String.valueOf(muebles.size()) + "\n", fuenteNegrita));
            datosInventario.add(new Chunk("• Stock total disponible: ", fuenteDatos));
            datosInventario.add(new Chunk(stockTotal + " unidades\n", fuenteNegrita));
            datosInventario.add(new Chunk("• Muebles con stock bajo: ", fuenteDatos));
            
            Font fuenteRoja = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.RED);
            datosInventario.add(new Chunk(String.valueOf(stockBajo.size()) + "\n", fuenteRoja));
            datosInventario.add(new Chunk("• Valor total del inventario: ", fuenteDatos));
            datosInventario.add(new Chunk(String.format("Q %.2f\n", valorInventario), fuenteNegrita));
            datosInventario.setSpacingAfter(20);
            documento.add(datosInventario);
            
            // === SECCIÓN DE ESTADÍSTICAS ===
            Paragraph seccionEstadisticas = new Paragraph("📈 ESTADÍSTICAS", fuenteSeccion);
            seccionEstadisticas.setSpacingAfter(10);
            documento.add(seccionEstadisticas);
            
            Paragraph datosEstadisticas = new Paragraph();
            datosEstadisticas.add(new Chunk("• Mueble más caro: ", fuenteDatos));
            datosEstadisticas.add(new Chunk(String.format("Q %.2f\n", 
                muebles.stream().mapToDouble(Mueble::getPrecio).max().orElse(0)), fuenteNegrita));
            datosEstadisticas.add(new Chunk("• Mueble más barato: ", fuenteDatos));
            datosEstadisticas.add(new Chunk(String.format("Q %.2f\n", 
                muebles.stream().mapToDouble(Mueble::getPrecio).min().orElse(0)), fuenteNegrita));
            datosEstadisticas.add(new Chunk("• Precio promedio: ", fuenteDatos));
            datosEstadisticas.add(new Chunk(String.format("Q %.2f\n", 
                muebles.stream().mapToDouble(Mueble::getPrecio).average().orElse(0)), fuenteNegrita));
            datosEstadisticas.setSpacingAfter(30);
            documento.add(datosEstadisticas);
            
            // Cuadro de resumen destacado
            PdfPTable tablaResumen = new PdfPTable(2);
            tablaResumen.setWidthPercentage(80);
            tablaResumen.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            Font fuenteResumen = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE);
            
            PdfPCell celdaTitulo = new PdfPCell(new Phrase("RESUMEN FINANCIERO", fuenteResumen));
            celdaTitulo.setBackgroundColor(COLOR_HEADER);
            celdaTitulo.setColspan(2);
            celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaTitulo.setPadding(10);
            tablaResumen.addCell(celdaTitulo);
            
            agregarCeldaTabla(tablaResumen, "Ingresos Totales:", fuenteNegrita, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
            agregarCeldaTabla(tablaResumen, String.format("Q %.2f", totalGeneral), fuenteNegrita, BaseColor.WHITE, Element.ALIGN_RIGHT);
            
            agregarCeldaTabla(tablaResumen, "Valor del Inventario:", fuenteNegrita, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
            agregarCeldaTabla(tablaResumen, String.format("Q %.2f", valorInventario), fuenteNegrita, BaseColor.WHITE, Element.ALIGN_RIGHT);
            
            agregarCeldaTabla(tablaResumen, "VALOR TOTAL:", fuenteResumen, COLOR_HEADER, Element.ALIGN_LEFT);
            Font fuenteTotalBlanco = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE);
            agregarCeldaTabla(tablaResumen, String.format("Q %.2f", totalGeneral + valorInventario), fuenteTotalBlanco, COLOR_HEADER, Element.ALIGN_RIGHT);
            
            documento.add(tablaResumen);
            
            // Pie de página
            Font fuentePie = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            Paragraph pie = new Paragraph("Sistema de Mueblería - Resumen General del Sistema", fuentePie);
            pie.setAlignment(Element.ALIGN_CENTER);
            pie.setSpacingBefore(40);
            documento.add(pie);
            
            documento.close();
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al generar PDF: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Método auxiliar para agregar celdas a las tablas
     * @param tabla Tabla a la que se agregará la celda
     * @param texto Texto de la celda
     * @param fuente Fuente del texto
     * @param colorFondo Color de fondo de la celda
     * @param alineacion Alineación del texto (Element.ALIGN_LEFT, CENTER, RIGHT)
     */
    private static void agregarCeldaTabla(PdfPTable tabla, String texto, Font fuente, 
                                         BaseColor colorFondo, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(colorFondo);
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(5);
        tabla.addCell(celda);
    }
}
