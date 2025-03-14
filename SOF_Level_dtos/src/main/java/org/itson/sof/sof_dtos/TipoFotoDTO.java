package org.itson.sof.sof_dtos;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) para representar un tipo de foto. Contiene
 * información sobre el precio, dimensiones y el marco de la foto. También
 * almacena un conjunto de paquetes a los que pertenece este tipo de foto.
 *
 * @author SOF LEVEL
 */
public class TipoFotoDTO {

    private Float precio;
    private Float altura;
    private Float anchura;
    private String marco;
    private Set<PaqueteTipoFotoDTO> paquetes = new HashSet<>();

    /**
     * Constructor vacío de TipoFotoDTO. Se utiliza cuando se necesita crear un
     * objeto sin inicializar sus atributos.
     */
    public TipoFotoDTO() {
    }

    /**
     * Constructor que inicializa un TipoFotoDTO con valores específicos.
     *
     * @param precio Precio del tipo de foto.
     * @param altura Altura de la foto en unidades de medida.
     * @param anchura Anchura de la foto en unidades de medida.
     * @param marco Tipo de marco de la foto.
     */
    public TipoFotoDTO(Float precio, Float altura, Float anchura, String marco) {
        this.precio = precio;
        this.altura = altura;
        this.anchura = anchura;
        this.marco = marco;
    }

    /**
     * Obtiene el precio del tipo de foto.
     *
     * @return El precio de la foto.
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del tipo de foto.
     *
     * @param precio El nuevo precio a asignar.
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la altura de la foto.
     *
     * @return La altura de la foto.
     */
    public Float getAltura() {
        return altura;
    }

    /**
     * Establece la altura de la foto.
     *
     * @param altura La nueva altura a asignar.
     */
    public void setAltura(Float altura) {
        this.altura = altura;
    }

    /**
     * Obtiene la anchura de la foto.
     *
     * @return La anchura de la foto.
     */
    public Float getAnchura() {
        return anchura;
    }

    /**
     * Establece la anchura de la foto.
     *
     * @param anchura La nueva anchura a asignar.
     */
    public void setAnchura(Float anchura) {
        this.anchura = anchura;
    }

    /**
     * Obtiene el tipo de marco de la foto.
     *
     * @return El tipo de marco de la foto.
     */
    public String getMarco() {
        return marco;
    }

    /**
     * Establece el tipo de marco de la foto.
     *
     * @param marco El nuevo tipo de marco a asignar.
     */
    public void setMarco(String marco) {
        this.marco = marco;
    }

    /**
     * Obtiene el conjunto de paquetes a los que pertenece este tipo de foto.
     *
     * @return Un conjunto de paquetes asociados a la foto.
     */
    public Set<PaqueteTipoFotoDTO> getPaquetes() {
        return paquetes;
    }

    /**
     * Establece el conjunto de paquetes a los que pertenece este tipo de foto.
     *
     * @param paquetes El nuevo conjunto de paquetes a asignar.
     */
    public void setPaquetes(Set<PaqueteTipoFotoDTO> paquetes) {
        this.paquetes = paquetes;
    }

    /**
     * Devuelve una representación en cadena del objeto TipoFotoDTO.
     *
     * @return Cadena con los valores de los atributos del tipo de foto.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TipoFotoDTO{");
        sb.append("precio=").append(precio);
        sb.append(", altura=").append(altura);
        sb.append(", anchura=").append(anchura);
        sb.append(", marco=").append(marco);
        sb.append(", paquetes=").append(paquetes);
        sb.append('}');
        return sb.toString();
    }

}
