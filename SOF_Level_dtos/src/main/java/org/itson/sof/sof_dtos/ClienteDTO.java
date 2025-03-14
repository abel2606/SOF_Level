package org.itson.sof.sof_dtos;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) para representar un cliente. Contiene
 * información sobre el nombre, teléfono y correo del cliente. También almacena
 * un conjunto de contratos asociados a este cliente.
 *
 * @author SOF LEVEL
 */
public class ClienteDTO {

    private String nombre;
    private String telefono;
    private String correo;
    private Set<ContratoDTO> contratos = new HashSet<>();

    /**
     * Constructor vacío de ClienteDTO. Se utiliza cuando se necesita crear un
     * objeto sin inicializar sus atributos.
     */
    public ClienteDTO() {
    }

    /**
     * Constructor que inicializa un ClienteDTO con valores específicos.
     *
     * @param nombre Nombre del cliente.
     * @param telefono Teléfono del cliente.
     * @param correo Correo electrónico del cliente.
     */
    public ClienteDTO(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del cliente.
     *
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     *
     * @param telefono El nuevo teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return El correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo El nuevo correo electrónico a asignar.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el conjunto de contratos asociados al cliente.
     *
     * @return Un conjunto de contratos del cliente.
     */
    public Set<ContratoDTO> getContratos() {
        return contratos;
    }

    /**
     * Establece el conjunto de contratos asociados al cliente.
     *
     * @param contratos El nuevo conjunto de contratos a asignar.
     */
    public void setContratos(Set<ContratoDTO> contratos) {
        this.contratos = contratos;
    }

    /**
     * Devuelve una representación en cadena del objeto ClienteDTO.
     *
     * @return Cadena con los valores de los atributos del cliente.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClienteDTO{");
        sb.append("nombre=").append(nombre);
        sb.append(", telefono=").append(telefono);
        sb.append(", correo=").append(correo);
        sb.append(", contratos=").append(contratos);
        sb.append('}');
        return sb.toString();
    }

}
