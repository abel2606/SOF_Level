package org.itson.sof.sof_dtos;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa una compra dentro del
 * sistema. Una compra está vinculada a un contrato y puede incluir materiales
 * adquiridos.
 *
 * @author SOF LEVEL
 */
public class CompraDTO {

    private Long folio;
    private Float cantidad;
    private Calendar fechaHora;
    private Double costo;
    private String proveedor;
    private ContratoDTO contrato;
    private Set<MaterialDTO> materiales = new HashSet<>();

    /**
     * Constructor vacío de CompraDTO. Se usa para inicializar un objeto sin
     * establecer sus valores.
     */
    public CompraDTO() {
    }

    /**
     * Constructor que inicializa una compra con valores específicos.
     *
     * @param folio Número de folio de la compra.
     * @param cantidad Cantidad de unidades adquiridas.
     * @param fechaHora Fecha y hora en que se realizó la compra.
     * @param costo Costo total de la compra.
     * @param proveedor Nombre del proveedor.
     * @param contrato Contrato asociado a la compra.
     */
    public CompraDTO(Long folio, Float cantidad, Calendar fechaHora, Double costo, String proveedor, ContratoDTO contrato) {
        this.folio = folio;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.costo = costo;
        this.proveedor = proveedor;
        this.contrato = contrato;
    }

    /**
     * Obtiene el número de folio de la compra.
     *
     * @return Número de folio.
     */
    public Long getFolio() {
        return folio;
    }

    /**
     * Establece el número de folio de la compra.
     *
     * @param folio Nuevo número de folio.
     */
    public void setFolio(Long folio) {
        this.folio = folio;
    }

    /**
     * Obtiene la cantidad de unidades adquiridas.
     *
     * @return Cantidad de unidades compradas.
     */
    public Float getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades adquiridas.
     *
     * @param cantidad Nueva cantidad de unidades compradas.
     */
    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la fecha y hora en que se realizó la compra.
     *
     * @return Fecha y hora de la compra.
     */
    public Calendar getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la compra.
     *
     * @param fechaHora Nueva fecha y hora de la compra.
     */
    public void setFechaHora(Calendar fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el costo total de la compra.
     *
     * @return Costo total.
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * Establece el costo total de la compra.
     *
     * @param costo Nuevo costo total.
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }

    /**
     * Obtiene el nombre del proveedor de la compra.
     *
     * @return Nombre del proveedor.
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * Establece el proveedor de la compra.
     *
     * @param proveedor Nuevo proveedor de la compra.
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Obtiene el contrato asociado a la compra.
     *
     * @return Contrato vinculado a la compra.
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * Establece el contrato asociado a la compra.
     *
     * @param contrato Nuevo contrato vinculado.
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    /**
     * Obtiene el conjunto de materiales adquiridos en la compra.
     *
     * @return Conjunto de materiales comprados.
     */
    public Set<MaterialDTO> getMateriales() {
        return materiales;
    }

    /**
     * Establece el conjunto de materiales adquiridos en la compra.
     *
     * @param materiales Nuevo conjunto de materiales comprados.
     */
    public void setMateriales(Set<MaterialDTO> materiales) {
        this.materiales = materiales;
    }

    /**
     * Devuelve una representación en cadena del objeto CompraDTO.
     *
     * @return Cadena con los valores de los atributos de la compra.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompraDTO{");
        sb.append("folio=").append(folio);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", fechaHora=").append(fechaHora);
        sb.append(", costo=").append(costo);
        sb.append(", proveedor=").append(proveedor);
        sb.append(", contrato=").append(contrato);
        sb.append(", materiales=").append(materiales);
        sb.append('}');
        return sb.toString();
    }

}
