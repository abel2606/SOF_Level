package org.itson.sof.sof_dtos;

/**
 * Clase DTO (Data Transfer Object) que representa la relación entre un paquete
 * y un tipo de foto. Es utilizada para definir cuántas fotos de un tipo
 * específico están incluidas en un paquete determinado.
 *
 * @author SOF LEVEL
 */
public class PaqueteTipoFotoDTO {

    private PaqueteDTO paquete;
    private TipoFotoDTO tipoFoto;
    private int cantidad;

    /**
     * Constructor vacío de PaqueteTipoFotoDTO. Se utiliza para crear un objeto
     * sin inicializar sus atributos.
     */
    public PaqueteTipoFotoDTO() {
    }

    /**
     * Constructor que inicializa un PaqueteTipoFotoDTO con valores específicos.
     *
     * @param paquete Paquete asociado.
     * @param tipoFoto Tipo de foto incluida en el paquete.
     * @param cantidad Cantidad de fotos de este tipo en el paquete.
     */
    public PaqueteTipoFotoDTO(PaqueteDTO paquete, TipoFotoDTO tipoFoto, int cantidad) {
        this.paquete = paquete;
        this.tipoFoto = tipoFoto;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el paquete asociado a este objeto.
     *
     * @return El paquete asociado.
     */
    public PaqueteDTO getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete asociado a este objeto.
     *
     * @param paquete El nuevo paquete a asignar.
     */
    public void setPaquete(PaqueteDTO paquete) {
        this.paquete = paquete;
    }

    /**
     * Obtiene el tipo de foto asociado a este objeto.
     *
     * @return El tipo de foto asociado.
     */
    public TipoFotoDTO getTipoFoto() {
        return tipoFoto;
    }

    /**
     * Establece el tipo de foto asociado a este objeto.
     *
     * @param tipoFoto El nuevo tipo de foto a asignar.
     */
    public void setTipoFoto(TipoFotoDTO tipoFoto) {
        this.tipoFoto = tipoFoto;
    }

    /**
     * Obtiene la cantidad de fotos de este tipo incluidas en el paquete.
     *
     * @return La cantidad de fotos de este tipo en el paquete.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de fotos de este tipo en el paquete.
     *
     * @param cantidad La nueva cantidad a asignar.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve una representación en cadena del objeto PaqueteTipoFotoDTO.
     *
     * @return Cadena con los valores de los atributos de la relación
     * paquete-tipo de foto.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaqueteTipoFotoDTO{");
        sb.append("paquete=").append(paquete);
        sb.append(", tipoFoto=").append(tipoFoto);
        sb.append(", cantidad=").append(cantidad);
        sb.append('}');
        return sb.toString();
    }

}
