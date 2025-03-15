package org.itson.sof.sof_dtos;

import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa una cita dentro del sistema.
 * Una cita está asociada a un contrato y cuenta con un fotógrafo, materiales y
 * detalles sobre su programación.
 *
 * @author SOF LEVEL
 */
public class CitaDTO {

    private GregorianCalendar fechaHoraInicio;
    private GregorianCalendar fechaHoraFin;
    private String lugar;
    private String extras;
    private ContratoDTO contrato;
    private FotografoDTO fotografo;
    private Set<MaterialDTO> materiales;

    /**
     * Constructor vacío de CitaDTO. Se usa para inicializar un objeto sin
     * establecer sus valores.
     */
    public CitaDTO() {
    }

    /**
     * Constructor que inicializa una CitaDTO con valores específicos.
     *
     * @param fechaHoraInicio Fecha y hora de inicio de la cita.
     * @param fechaHoraFin Fecha y hora de finalización de la cita.
     * @param lugar Lugar donde se llevará a cabo la cita.
     * @param extras Información adicional sobre la cita.
     * @param contrato Contrato asociado a la cita.
     * @param fotografo Fotógrafo asignado a la cita.
     * @param materiales Materiales utilizados en la cita.
     */
    public CitaDTO(GregorianCalendar fechaHoraInicio, GregorianCalendar fechaHoraFin, String lugar, String extras, ContratoDTO contrato, FotografoDTO fotografo, Set<MaterialDTO> materiales) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.lugar = lugar;
        this.extras = extras;
        this.contrato = contrato;
        this.fotografo = fotografo;
        this.materiales = materiales;
    }

    /**
     * Obtiene la fecha y hora de inicio de la cita.
     *
     * @return Fecha y hora de inicio de la cita.
     */
    public GregorianCalendar getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * Establece la fecha y hora de inicio de la cita.
     *
     * @param fechaHoraInicio Nueva fecha y hora de inicio de la cita.
     */
    public void setFechaHoraInicio(GregorianCalendar fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * Obtiene la fecha y hora de finalización de la cita.
     *
     * @return Fecha y hora de finalización de la cita.
     */
    public GregorianCalendar getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * Establece la fecha y hora de finalización de la cita.
     *
     * @param fechaHoraFin Nueva fecha y hora de finalización de la cita.
     */
    public void setFechaHoraFin(GregorianCalendar fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    /**
     * Obtiene el lugar donde se llevará a cabo la cita.
     *
     * @return Lugar de la cita.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Establece el lugar donde se llevará a cabo la cita.
     *
     * @param lugar Nuevo lugar de la cita.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Obtiene la información adicional de la cita.
     *
     * @return Información adicional de la cita.
     */
    public String getExtras() {
        return extras;
    }

    /**
     * Establece la información adicional de la cita.
     *
     * @param extras Nueva información adicional de la cita.
     */
    public void setExtras(String extras) {
        this.extras = extras;
    }

    /**
     * Obtiene el contrato asociado a la cita.
     *
     * @return Contrato de la cita.
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * Establece el contrato asociado a la cita.
     *
     * @param contrato Nuevo contrato asociado a la cita.
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    /**
     * Obtiene el fotógrafo asignado a la cita.
     *
     * @return Fotógrafo de la cita.
     */
    public FotografoDTO getFotografo() {
        return fotografo;
    }

    /**
     * Establece el fotógrafo asignado a la cita.
     *
     * @param fotografo Nuevo fotógrafo de la cita.
     */
    public void setFotografo(FotografoDTO fotografo) {
        this.fotografo = fotografo;
    }

    /**
     * Obtiene el conjunto de materiales utilizados en la cita.
     *
     * @return Conjunto de materiales de la cita.
     */
    public Set<MaterialDTO> getMateriales() {
        return materiales;
    }

    /**
     * Establece el conjunto de materiales utilizados en la cita.
     *
     * @param materiales Nuevo conjunto de materiales de la cita.
     */
    public void setMateriales(Set<MaterialDTO> materiales) {
        this.materiales = materiales;
    }

    /**
     * Devuelve una representación en cadena del objeto CitaDTO.
     *
     * @return Cadena con los valores de los atributos de la cita.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CitaDTO{");
        sb.append("fechaHoraInicio=").append(fechaHoraInicio);
        sb.append(", fechaHoraFin=").append(fechaHoraFin);
        sb.append(", lugar=").append(lugar);
        sb.append(", extras=").append(extras);
        sb.append(", contrato=").append(contrato);
        sb.append(", fotografo=").append(fotografo);
        sb.append(", materiales=").append(materiales);
        sb.append('}');
        return sb.toString();
    }

}
