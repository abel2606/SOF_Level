/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public interface IContratoBO {
    
    /**
     * Método para crear un contrato y asignarle un cliente 
     * @param contrato contrato que se desea guardar
     * @param cliente cliente que se le asignara al contrato
     * @return contrato en caso de que cree el contrato
     * @throws ObjetosNegocioException error en caso que de un error al crear el contrato
     */
    public ContratoDTO crearContrato (ContratoDTO contrato, ClienteDTO cliente) throws ObjetosNegocioException;
    
    /**
     * Método para actualizar un contrato que se tiene registrado
     * @param contrato contrato que se desea actualizar
     * @return contrato en caso que se cree el contrato
     * @throws ObjetosNegocioException en caso de un error al actualizar el contrato
     */
    public ContratoDTO actualizarContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
    /**
     * Método para eliminar un contrato del registro
     * @param contrato contrato a eliminar
     * @return true en caso que se elimine el contrato
     * @throws ObjetosNegocioException en caso que no se elimine retorna error
     */
    public boolean eliminarContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
    /**
     * Método para obtener un contrato
     * @param contrato contrato que se desea obtener
     * @return contrato que se haya encontrado
     * @throws ObjetosNegocioException en caso que ocurra un error en la consulta del contrato
     */
    public ContratoDTO obtenerContrato (ContratoDTO contrato) throws ObjetosNegocioException;
    
}
