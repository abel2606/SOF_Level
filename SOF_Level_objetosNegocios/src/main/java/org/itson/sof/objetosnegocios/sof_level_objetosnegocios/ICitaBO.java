package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public interface ICitaBO {
    
    /**
     * Método crear y asignar una cita a un contrato
     * @param citaDTO cita que se desea crear
     * @return cita en caso que se cree
     * @throws ObjetosNegocioException en caso de un error al crear la cita 
     */
    public CitaDTO crearCita (CitaDTO citaDTO) throws ObjetosNegocioException;
    
    /**
     * Método para actulizar una cita ya creada
     * @param citaDTO cita que se desea actualizar
     * @return cita en caso de actualizarla
     * @throws ObjetosNegocioException en caso de un error al actuaizar la cita
     */
    public CitaDTO actualizarCita (CitaDTO citaDTO) throws ObjetosNegocioException;
    
    /**
     * Método para eliminar la una cita.
     * @param citaDTO cita que se desea eliminar
     * @return la cita en caso de eliminarse
     * @throws ObjetosNegocioException 
     */
    public CitaDTO eliminarCita (CitaDTO citaDTO) throws ObjetosNegocioException;
    
    /**
     * Metodo para consultar una cita
     * @param citaDTO cita que se desea consultar
     * @return cita consultada
     * @throws ObjetosNegocioException
     */
    public CitaDTO obtenerCita (CitaDTO citaDTO) throws ObjetosNegocioException;
    
    /**
     * Obtiene todas las citas de un contrato
     * @param contratoDTO Contrato al que pertenecen las citas
     * @return Lista de citaDTO
     * @throws ObjetosNegocioException 
     */
    public List<CitaDTO> obtenerCitasPorContrato(ContratoDTO contratoDTO) throws ObjetosNegocioException;
}
