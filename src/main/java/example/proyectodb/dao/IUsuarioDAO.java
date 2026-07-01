/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package example.proyectodb.dao;
//import example.proyectodb.modelo.Usuario;
import example.proyectodb.modelo.Usuario;
/**
 *
 * @author emanu
 */
public interface IUsuarioDAO {
    
    /**
     * Valida el login de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario si es válido, null si no
     */
    Usuario validarLogin(String username, String password);
    
    /**
     * Inserta un nuevo usuario
     * @param usuario objeto Usuario con los datos
     * @return true si se insertó, false si no
     */
    boolean insertar(Usuario usuario);
    
    /**
     * Actualiza un usuario existente
     * @param usuario objeto Usuario con los nuevos datos
     * @return true si se actualizó, false si no
     */
    boolean actualizar(Usuario usuario);
    
    /**
     * Elimina un usuario por ID
     * @param idUsuario ID del usuario a eliminar
     * @return true si se eliminó, false si no
     */
    boolean eliminar(int idUsuario);
    
    /**
     * Obtiene un usuario por su ID
     * @param idUsuario ID del usuario
     * @return Usuario encontrado o null
     */
    Usuario obtenerPorId(int idUsuario);
}
