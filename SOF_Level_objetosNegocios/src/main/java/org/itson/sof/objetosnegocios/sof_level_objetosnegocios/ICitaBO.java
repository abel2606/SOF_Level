package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

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
     * @param contrato contrato al que se le asignará la cita
     * @param cita cita que se desea crear
     * @return cita en caso que se cree
     * @throws ObjetosNegocioException en caso de un error al crear la cita 
     */
    public CitaDTO crearCita (ContratoDTO contrato, CitaDTO cita) throws ObjetosNegocioException;
    
    /**
     * Método para actulizar una cita ya creada
     * @param cita cita que se desea actualizar
     * @return cita en caso de actualizarla
     * @throws ObjetosNegocioException en caso de un error al actuaizar la cita
     */
    public CitaDTO actualizarCita (CitaDTO cita) throws ObjetosNegocioException;
    
    /**
     * Método para eliminar la una cita.
     * @param cita cita que se desea eliminar
     * @return true en caso de eliminar la cita, false en caso contrario
     * @throws ObjetosNegocioException 
     */
    public boolean eliminarCita (CitaDTO cita) throws ObjetosNegocioException;
    
}
