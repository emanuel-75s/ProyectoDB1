/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.modelo;
import java.util.Date;
/**
 *
 * @author emanu
 */
public class Venta {
      private int idVenta;
    private int idUsuario;
    private int idMueble;
    private String clienteNombre;
    private String clienteTelefono;
    private int cantidad;
    private double precioTotal;
    private Date fechaVenta;
    private String tipoPago;
    
    // Campos adicionales para mostrar información (no están en la BD)
    private String nombreUsuario;
    private String nombreMueble;
    
    // Constructor vacío
    public Venta() {
    }
    
    // Constructor completo
    public Venta(int idVenta, int idUsuario, int idMueble, String clienteNombre, 
                 String clienteTelefono, int cantidad, double precioTotal, 
                 Date fechaVenta, String tipoPago) {
        this.idVenta = idVenta;
        this.idUsuario = idUsuario;
        this.idMueble = idMueble;
        this.clienteNombre = clienteNombre;
        this.clienteTelefono = clienteTelefono;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.fechaVenta = fechaVenta;
        this.tipoPago = tipoPago;
    }
    
    // GETTERS Y SETTERS
    
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMueble() {
        return idMueble;
    }

    public void setIdMueble(int idMueble) {
        this.idMueble = idMueble;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + idVenta +
                ", cliente='" + clienteNombre + '\'' +
                ", cantidad=" + cantidad +
                ", total=" + precioTotal +
                ", fecha=" + fechaVenta +
                '}';
    }
}
    

