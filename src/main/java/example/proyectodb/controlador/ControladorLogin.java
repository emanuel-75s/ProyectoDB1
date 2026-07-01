/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package example.proyectodb.controlador;
import example.proyectodb.dao.UsuarioDAO;
import example.proyectodb.modelo.Usuario;
/**
 *
 * @author emanu
 */
public class ControladorLogin {
     private UsuarioDAO usuarioDAO;
    
    public ControladorLogin() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Valida las credenciales de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario si las credenciales son correctas, null si no
     */
    public Usuario validarCredenciales(String username, String password) {
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            System.err.println("El username no puede estar vacío");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.err.println("La contraseña no puede estar vacía");
            return null;
        }
        
        // Validar con la base de datos
        Usuario usuario = usuarioDAO.validarLogin(username.trim(), password);
        
        if (usuario != null) {
            System.out.println("Login exitoso: " + usuario.getNombre());
        } else {
            System.err.println("Credenciales incorrectas");
        }
        
        return usuario;
    }
    
    /**
     * Verifica si el usuario tiene permisos de administrador
     * @param usuario usuario a verificar
     * @return true si es administrador
     */
    public boolean esAdministrador(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        return "administrador".equalsIgnoreCase(usuario.getCargo()) ||
               "Gerente".equalsIgnoreCase(usuario.getCargo());
    }
}
