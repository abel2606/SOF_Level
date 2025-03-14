package org.itson.sof.sof_dtos;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa a un fotógrafo dentro del
 * sistema. Un fotógrafo tiene información de contacto y puede estar asociado a
 * múltiples citas.
 *
 * @author SOF LEVEL
 */
public class FotografoDTO {

    private String correo;
    private String nombrePersona;
    private String telefono;
    private Set<CitaDTO> citas = new HashSet<>();

    /**
     * Constructor vacío de FotografoDTO. Se utiliza para crear un objeto sin
     * inicializar sus atributos.
     */
    public FotografoDTO() {
    }

    /**
     * Constructor que inicializa un FotografoDTO con valores específicos.
     *
     * @param correo Correo electrónico del fotógrafo.
     * @param nombrePersona Nombre completo del fotógrafo.
     * @param telefono Número de teléfono del fotógrafo.
     */
    public FotografoDTO(String correo, String nombrePersona, String telefono) {
        this.correo = correo;
        this.nombrePersona = nombrePersona;
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del fotógrafo.
     *
     * @return Correo del fotógrafo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del fotógrafo.
     *
     * @param correo Nuevo correo del fotógrafo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el nombre completo del fotógrafo.
     *
     * @return Nombre del fotógrafo.
     */
    public String getNombrePersona() {
        return nombrePersona;
    }

    /**
     * Establece el nombre completo del fotógrafo.
     *
     * @param nombrePersona Nuevo nombre del fotógrafo.
     */
    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    /**
     * Obtiene el número de teléfono del fotógrafo.
     *
     * @return Teléfono del fotógrafo.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del fotógrafo.
     *
     * @param telefono Nuevo número de teléfono del fotógrafo.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el conjunto de citas en las que participa el fotógrafo.
     *
     * @return Un conjunto de citas asociadas al fotógrafo.
     */
    public Set<CitaDTO> getCitas() {
        return citas;
    }

    /**
     * Establece el conjunto de citas en las que participa el fotógrafo.
     *
     * @param citas Nuevo conjunto de citas a asignar.
     */
    public void setCitas(Set<CitaDTO> citas) {
        this.citas = citas;
    }

    /**
     * Devuelve una representación en cadena del objeto FotografoDTO.
     *
     * @return Cadena con los valores de los atributos del fotógrafo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FotografoDTO{");
        sb.append("correo=").append(correo);
        sb.append(", nombrePersona=").append(nombrePersona);
        sb.append(", telefono=").append(telefono);
        sb.append(", citas=").append(citas);
        sb.append('}');
        return sb.toString();
    }

}
