package org.itson.sof.sof_dtos;

/**
 * Clase DTO (Data Transfer Object) para representar la información de un
 * usuario. Se utiliza para transferir datos de usuario entre capas sin exponer
 * la entidad original. Contiene la contraseña y el nombre de usuario.
 *
 * @author SOF LEVEL
 */
public class UsuarioDTO {

    private String contrasena;
    private String nombreUsuario;

    /**
     * Constructor vacío de UsuarioDTO. Se utiliza cuando se necesita crear un
     * objeto sin inicializar sus atributos.
     */
    public UsuarioDTO() {
    }

    /**
     * Constructor que inicializa un UsuarioDTO con valores específicos.
     *
     * @param contrasena Contraseña del usuario.
     * @param nombreUsuario Nombre de usuario.
     */
    public UsuarioDTO(String contrasena, String nombreUsuario) {
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La nueva contraseña a asignar.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param nombreUsuario El nuevo nombre de usuario a asignar.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Devuelve una representación en cadena del objeto UsuarioDTO.
     *
     * @return Cadena con los valores de los atributos del usuario.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsuarioDTO{");
        sb.append("contrasena=").append(contrasena);
        sb.append(", nombreUsuario=").append(nombreUsuario);
        sb.append('}');
        return sb.toString();
    }

}
