/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.modelo;

/**
 *
 * @author emanu
 */
public class Mueble {
    private int idMueble;
    private String codigoMueble;
    private String nombreMueble;
    private String categoria;
    private String material;
    private String color;
    private double precio;
    private int cantidadStock;
    private String dimensiones;
    
    // Constructor vacío
    public Mueble() {
    }
    
    // Constructor completo
    public Mueble(int idMueble, String codigoMueble, String nombreMueble, String categoria, 
                  String material, String color, double precio, int cantidadStock, String dimensiones) {
        this.idMueble = idMueble;
        this.codigoMueble = codigoMueble;
        this.nombreMueble = nombreMueble;
        this.categoria = categoria;
        this.material = material;
        this.color = color;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.dimensiones = dimensiones;
    }
    
    // GETTERS Y SETTERS
    
    public int getIdMueble() {
        return idMueble;
    }

    public void setIdMueble(int idMueble) {
        this.idMueble = idMueble;
    }

    public String getCodigoMueble() {
        return codigoMueble;
    }

    public void setCodigoMueble(String codigoMueble) {
        this.codigoMueble = codigoMueble;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    @Override
    public String toString() {
        return "Mueble{" +
                "id=" + idMueble +
                ", codigo='" + codigoMueble + '\'' +
                ", nombre='" + nombreMueble + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + cantidadStock +
                '}';
    }
}
