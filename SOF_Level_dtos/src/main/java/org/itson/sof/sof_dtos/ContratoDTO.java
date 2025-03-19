package org.itson.sof.sof_dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase DTO (Data Transfer Object) que representa un contrato dentro del
 * sistema. Un contrato está asociado a un cliente y a un paquete, y puede
 * incluir compras y citas.
 *
 * @author SOF LEVEL
 */
public class ContratoDTO {

    private String tematica;
    private String estado;
    private PaqueteDTO paquete;
    private ClienteDTO cliente;
    private Set<CompraDTO> compras = new HashSet<>();
    private List<CitaDTO> citas = new ArrayList<>();
    private String folio;
    private Long id;

    /**
     * Constructor vacío de ContratoDTO. Se usa para crear un objeto sin
     * inicializar sus atributos.
     */
    public ContratoDTO() {
    }

    /**
     * Constructor que inicializa un ContratoDTO con valores específicos.
     *
     * @param tematica La temática del contrato.
     * @param estado Estado actual del contrato (ejemplo: activo, cancelado,
     * finalizado).
     * @param paquete Paquete de servicios asociado al contrato.
     * @param cliente Cliente que firma el contrato.
     */
    public ContratoDTO(String tematica, String estado, PaqueteDTO paquete, ClienteDTO cliente, String folio, Long id) {
        this.tematica = tematica;
        this.estado = estado;
        this.paquete = paquete;
        this.cliente = cliente;
        this.folio = folio;
        this.id = id;
    }

    

    /**
     * Obtiene la temática del contrato.
     *
     * @return Temática del contrato.
     */
    public String getTematica() {
        return tematica;
    }

    /**
     * Establece la temática del contrato.
     *
     * @param tematica Nueva temática del contrato.
     */
    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    /**
     * Obtiene el estado actual del contrato.
     *
     * @return Estado del contrato.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del contrato.
     *
     * @param estado Nuevo estado del contrato.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el paquete asociado al contrato.
     *
     * @return Paquete de servicios del contrato.
     */
    public PaqueteDTO getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete de servicios del contrato.
     *
     * @param paquete Nuevo paquete asociado al contrato.
     */
    public void setPaquete(PaqueteDTO paquete) {
        this.paquete = paquete;
    }

    /**
     * Obtiene el cliente asociado al contrato.
     *
     * @return Cliente que firmó el contrato.
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente del contrato.
     *
     * @param cliente Nuevo cliente asociado al contrato.
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el conjunto de compras asociadas al contrato.
     *
     * @return Conjunto de compras relacionadas con el contrato.
     */
    public Set<CompraDTO> getCompras() {
        return compras;
    }

    /**
     * Establece el conjunto de compras asociadas al contrato.
     *
     * @param compras Nuevo conjunto de compras a asignar.
     */
    public void setCompras(Set<CompraDTO> compras) {
        this.compras = compras;
    }

    /**
     * Obtiene el conjunto de citas asociadas al contrato.
     *
     * @return Conjunto de citas del contrato.
     */
    public List<CitaDTO> getCitas() {
        return citas;
    }

    /**
     * Establece el conjunto de citas asociadas al contrato.
     *
     * @param citas Nuevo conjunto de citas a asignar.
     */
    public void setCitas(List<CitaDTO> citas) {
        this.citas = citas;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    

    /**
     * Devuelve una representación en cadena del objeto ContratoDTO.
     *
     * @return Cadena con los valores de los atributos del contrato.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContratoDTO{");
        sb.append("tematica=").append(tematica);
        sb.append(", estado=").append(estado);
        sb.append(", paquete=").append(paquete);
        sb.append(", cliente=").append(cliente);
        sb.append(", compras=").append(compras);
        sb.append(", citas=").append(citas);
        sb.append('}');
        return sb.toString();
    }
    
    

}
