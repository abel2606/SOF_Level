package org.itson.sof.sof_dtos;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa un material utilizado en el
 * sistema. Un material puede estar asociado a una compra y a múltiples citas
 * donde se utilice.
 *
 * @author SOF LEVEL
 */
public class MaterialDTO {

    private String nombre;
    private Float cantidad;
    private CompraDTO compra;
    private Set<CitaDTO> citas = new HashSet<>();

    /**
     * Constructor vacío de MaterialDTO. Se utiliza para crear un objeto sin
     * inicializar sus atributos.
     */
    public MaterialDTO() {
    }

    /**
     * Constructor que inicializa un MaterialDTO con valores específicos.
     *
     * @param nombre Nombre del material.
     * @param cantidad Cantidad disponible del material.
     * @param compra Compra asociada al material.
     */
    public MaterialDTO(String nombre, Float cantidad, CompraDTO compra) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.compra = compra;
    }

    /**
     * Obtiene el nombre del material.
     *
     * @return El nombre del material.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del material.
     *
     * @param nombre El nuevo nombre del material.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la cantidad disponible del material.
     *
     * @return La cantidad del material.
     */
    public Float getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible del material.
     *
     * @param cantidad La nueva cantidad del material.
     */
    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la compra asociada a este material.
     *
     * @return La compra asociada.
     */
    public CompraDTO getCompra() {
        return compra;
    }

    /**
     * Establece la compra asociada a este material.
     *
     * @param compra La nueva compra a asignar.
     */
    public void setCompra(CompraDTO compra) {
        this.compra = compra;
    }

    /**
     * Obtiene el conjunto de citas en las que se utiliza este material.
     *
     * @return Un conjunto de citas asociadas al material.
     */
    public Set<CitaDTO> getCitas() {
        return citas;
    }

    /**
     * Establece el conjunto de citas en las que se utiliza este material.
     *
     * @param citas El nuevo conjunto de citas a asignar.
     */
    public void setCitas(Set<CitaDTO> citas) {
        this.citas = citas;
    }

    /**
     * Devuelve una representación en cadena del objeto MaterialDTO.
     *
     * @return Cadena con los valores de los atributos del material.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaterialDTO{");
        sb.append(", nombre=").append(nombre);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", compra=").append(compra);
        sb.append(", citas=").append(citas);
        sb.append('}');
        return sb.toString();
    }

}
