/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.util;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
/**
 *
 * @author emanu
 */
public class Validaciones {
    
    /**
     * Valida que un campo de texto no esté vacío
     * @param campo JTextField a validar
     * @return true si está vacío
     */
    public static boolean campoVacio(JTextField campo) {
        return campo.getText() == null || campo.getText().trim().isEmpty();
    }
    
    /**
     * Valida que un texto sea un número entero válido
     * @param texto texto a validar
     * @return true si es un número entero válido
     */
    public static boolean esNumeroEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida que un texto sea un número decimal válido
     * @param texto texto a validar
     * @return true si es un número decimal válido
     */
    public static boolean esNumeroDecimal(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Convierte texto a entero, devuelve 0 si falla
     * @param texto texto a convertir
     * @return número entero
     */
    public static int textoAEntero(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Convierte texto a decimal, devuelve 0.0 si falla
     * @param texto texto a convertir
     * @return número decimal
     */
    public static double textoADecimal(String texto) {
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    /**
     * Limpia todos los campos de texto de un array
     * @param campos array de JTextField a limpiar
     */
    public static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo != null) {
                campo.setText("");
            }
        }
    }
    
    /**
     * Muestra un mensaje de error
     * @param mensaje mensaje a mostrar
     */
    public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de éxito
     * @param mensaje mensaje a mostrar
     */
    public static void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de advertencia
     * @param mensaje mensaje a mostrar
     */
    public static void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Muestra un diálogo de confirmación
     * @param mensaje mensaje a mostrar
     * @return true si el usuario confirma
     */
    public static boolean confirmar(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(
            null, 
            mensaje, 
            "Confirmación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    /**
     * Valida que un ComboBox tenga una opción seleccionada
     * @param combo JComboBox a validar
     * @return true si no tiene selección válida
     */
    public static boolean comboVacio(JComboBox combo) {
        return combo.getSelectedIndex() <= 0;
    }
    
    
}
