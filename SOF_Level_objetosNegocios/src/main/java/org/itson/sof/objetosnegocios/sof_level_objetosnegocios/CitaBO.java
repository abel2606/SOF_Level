/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.CitasDAO;
import org.itson.sof.persistencia.daos.ICitasDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class CitaBO implements ICitaBO {

    private ICitasDAO citasDAO;

    public CitaBO() {
        IConexion conexion = new Conexion();
        this.citasDAO = new CitasDAO(conexion);
    }

    /**
     * Método crear y asignar una cita a un contrato
     *
     * @param citaDTO cita que se desea crear
     * @return cita en caso que se cree
     * @throws ObjetosNegocioException en caso de un error al crear la cita
     */
    @Override
    public CitaDTO crearCita(CitaDTO citaDTO) throws ObjetosNegocioException {

        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            cita = citasDAO.agregarCita(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    /**
     * Método para actulizar una cita ya creada
     *
     * @param citaDTO cita que se desea actualizar
     * @return cita en caso de actualizarla
     * @throws ObjetosNegocioException en caso de un error al actuaizar la cita
     */
    @Override
    public CitaDTO actualizarCita(CitaDTO citaDTO) throws ObjetosNegocioException {
        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            cita = citasDAO.actualizarCita(cita);
            citaDTO = ConverterUtil.citaEntidadADTO(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    /**
     * Método para eliminar la una cita.
     *
     * @param citaDTO cita que se desea eliminar
     * @return la cita en caso de eliminarse
     * @throws ObjetosNegocioException
     */
    @Override
    public CitaDTO eliminarCita(CitaDTO citaDTO) throws ObjetosNegocioException {

        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            cita = citasDAO.eliminarcita(cita);
            citaDTO = ConverterUtil.citaEntidadADTO(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    /**
     * Metodo para consultar una cita
     *
     * @param citaDTO cita que se desea consultar
     * @return cita consultada
     */
    @Override
    public CitaDTO obtenerCita(CitaDTO citaDTO) throws ObjetosNegocioException{
        Cita cita = new Cita();
        cita.setCodigo(citaDTO.getCodigo());
        
        try {
            citaDTO = ConverterUtil.citaEntidadADTO(citasDAO.obtenerCita(cita));
            return citaDTO;
        }catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    public List<CitaDTO> obtenerCitasPorContrato(ContratoDTO contratoDTO) throws ObjetosNegocioException {
        try {
            // Obtener la lista de Citas desde el DAO
            List<Cita> citas = citasDAO.obtenerCitasContratos(contratoDTO.getId());

            // Convertir cada Cita a CitaDTO
            List<CitaDTO> citasDTO = new ArrayList<>();
            for (Cita cita : citas) {
                citasDTO.add(ConverterUtil.citaEntidadADTO(cita));
            }

            return citasDTO;
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

}
