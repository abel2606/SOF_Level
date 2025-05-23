/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public interface IContratoBO {
    /**
     * Obtener el total de contratos
     * @return regresar una lista del total de contratos
     * @throws org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException
     */
    public List<ContratoDTO> obtenerTotalContratos() throws ObjetosNegocioException;
    
    public List<ContratoDTO> obtenerContratosPorCliente(ClienteDTO cliente) throws ObjetosNegocioException;
    
    /**
     * Método para crear un contrato y asignarle un cliente 
     * @param contrato contrato que se desea guardar
     * @param cliente cliente que se le asignara al contrato
     * @param paquete paqiete del contrato
     * @return contrato en caso de que cree el contrato
     * @throws ObjetosNegocioException error en caso que de un error al crear el contrato
     */
    public ContratoDTO crearContrato (ContratoDTO contrato, ClienteDTO cliente, PaqueteDTO paquete) throws ObjetosNegocioException;
    
    /**
     * Método para actualizar un contrato que se tiene registrado
     * @param contrato contrato que se desea actualizar
     * @return contrato en caso que se cree el contrato
     * @throws ObjetosNegocioException en caso de un error al actualizar el contrato
     */
    public ContratoDTO actualizarContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
    /**
     * Método para cancelar un contrato del registro
     * @param contrato contrato a eliminar
     * @return true en caso que se cancele el contrato
     * @throws ObjetosNegocioException en caso que no se elimine retorna error
     */
    public ContratoDTO cancelarContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
    
    /**
     * Método para terminar un contrato del registro
     * @param contrato contrato a eliminar
     * @return true en caso que se termine el contrato
     * @throws ObjetosNegocioException en caso que no se elimine retorna error
     */
    public ContratoDTO terminarContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
    
    /**
     * Método para obtener un contrato por folio
     * @param folio folio del contrato que se desea obtener
     * @return contrato que se haya encontrado
     * @throws ObjetosNegocioException en caso que ocurra un error en la consulta del contrato
     */
    public ContratoDTO obtenerContratoFolio (String folio) throws ObjetosNegocioException;    
    
    /**
     * Método para cancelar todos los contratos de un cliente
     * @param correo correo del cliente
     * @return true en caso de haber cancelado contratos
     * @throws ObjetosNegocioException en caso de algún error al momento de cancelar contratos
     */
    public List<ContratoDTO> cancelarContratoCliente(String correo) throws ObjetosNegocioException;
}
