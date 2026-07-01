/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.modelo;

/**
 *
 * @author emanu
 */
public class ResumenInventario {
    
     private String categoria;
    private int totalMuebles;
    private int stockTotal;
    private double precioPromedio;
    private double precioMinimo;
    private double precioMaximo;
    private double valorInventario;
    
    // Constructor vacío
    public ResumenInventario() {
    }
    
    // Getters y Setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTotalMuebles() {
        return totalMuebles;
    }

    public void setTotalMuebles(int totalMuebles) {
        this.totalMuebles = totalMuebles;
    }

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public double getPrecioPromedio() {
        return precioPromedio;
    }

    public void setPrecioPromedio(double precioPromedio) {
        this.precioPromedio = precioPromedio;
    }

    public double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public double getValorInventario() {
        return valorInventario;
    }

    public void setValorInventario(double valorInventario) {
        this.valorInventario = valorInventario;
    }

    @Override
    public String toString() {
        return "ResumenInventario{" +
                "categoria='" + categoria + '\'' +
                ", totalMuebles=" + totalMuebles +
                ", stockTotal=" + stockTotal +
                ", valorInventario=" + valorInventario +
                '}';
    }
}
