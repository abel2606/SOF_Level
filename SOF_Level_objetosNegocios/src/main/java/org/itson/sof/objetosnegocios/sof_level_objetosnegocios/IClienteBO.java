/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;

/**
 *
 * @author haesp
 */
public interface IClienteBO {
    
    
    /**
     * Método que agrega un cliente al registro
     * @param clienteDTO objeto ClienteDTO que se desea agregar
     * @return cliente en caso de agregarse
     * @throws ObjetosNegocioException en caso de un error para agregar al cliente
     */
    public ClienteDTO agregarCliente(ClienteDTO clienteDTO) throws ObjetosNegocioException;

    public List<ClienteDTO> obtenerTodosClientes() throws ObjetosNegocioException;
    
    public List<ClienteDTO> obtenerClientesSimilares(String nombre) throws ObjetosNegocioException;
    /**
     * Método para actualizar información de un cliente
     * @param clienteDTO objeto ClienteDTO que se desea actualizar
     * @return cliente en caso de actualizar
     * @throws ObjetosNegocioException en caso de un error al momento de actualizar
     */
    public ClienteDTO editarCliente(String correo, ClienteDTO clienteDTO) throws ObjetosNegocioException;
    /**
     * Método para eliminar un cliente del registro
     * @param correo correo único del cliente que se desea eliminar
     * @return true en caso de eliminar al cliente, false en caso de no poder eliminar
     * @throws ObjetosNegocioException en caso de existir un error si se borra el cliente
     */
    public boolean eliminarCliente(String correo) throws ObjetosNegocioException;

    public ClienteDTO obtenerCliente(String nombre) throws ObjetosNegocioException;
}
