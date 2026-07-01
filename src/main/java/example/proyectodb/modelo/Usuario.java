/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.modelo;

/**
 *
 * @author emanu
 */
    
public class Usuario {
   
    private int idUsuario;
    private String username;
    private String password;
    private String nombre;
    private String cargo;
    private boolean activo;
   
    public Usuario() {
    }
   
    public Usuario(int idUsuario, String username, String password, String nombre, String cargo, boolean activo) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.cargo = cargo;
        this.activo = activo;
    }

    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}

