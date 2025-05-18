package org.itson.sof.sof_dtos;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa un paquete de fotos. Un
 * paquete contiene un nombre, un precio, una lista de tipos de fotos incluidas
 * y una referencia al contrato asociado.
 *
 * @author SOF LEVEL
 */
public class PaqueteDTO {

    private Float precio;
    private String nombre;
    private Set<PaqueteTipoFotoDTO> tiposDeFoto = new HashSet<>();
    private ContratoDTO contrato;
    private Long id;

    /**
     * Constructor vacío de PaqueteDTO. Se utiliza para crear un objeto sin
     * inicializar sus atributos.
     */
    public PaqueteDTO() {
    }

    /**
     * Constructor que inicializa un PaqueteDTO con valores específicos.
     *
     * @param precio Precio del paquete.
     * @param nombre Nombre del paquete.
     * @param contrato Contrato asociado al paquete.
     */
    public PaqueteDTO(Float precio, String nombre, ContratoDTO contrato) {
        this.precio = precio;
        this.nombre = nombre;
        this.contrato = contrato;
    }

    /**
     * Obtiene el precio del paquete.
     *
     * @return El precio del paquete.
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del paquete.
     *
     * @param precio El nuevo precio a asignar.
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el nombre del paquete.
     *
     * @return El nombre del paquete.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del paquete.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el conjunto de tipos de foto incluidos en el paquete.
     *
     * @return Un conjunto de PaqueteTipoFotoDTO representando los tipos de
     * foto.
     */
    public Set<PaqueteTipoFotoDTO> getTiposDeFoto() {
        return tiposDeFoto;
    }

    /**
     * Establece el conjunto de tipos de foto incluidos en el paquete.
     *
     * @param tiposDeFoto El nuevo conjunto de tipos de foto a asignar.
     */
    public void setTiposDeFoto(Set<PaqueteTipoFotoDTO> tiposDeFoto) {
        this.tiposDeFoto = tiposDeFoto;
    }

    /**
     * Obtiene el contrato asociado al paquete.
     *
     * @return El contrato asociado al paquete.
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * Establece el contrato asociado al paquete.
     *
     * @param contrato El nuevo contrato a asignar.
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    

    /**
     * Devuelve una representación en cadena del objeto PaqueteDTO.
     *
     * @return Cadena con los valores de los atributos del paquete.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaqueteDTO{");
        sb.append("precio=").append(precio);
        sb.append(", nombre=").append(nombre);
        sb.append(", tiposDeFoto=").append(tiposDeFoto);
        sb.append(", contrato=").append(contrato);
        sb.append('}');
        return sb.toString();
    }

}
