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
    
    /**
     * Método para actualizar información de un cliente
     * @param clienteDTO objeto ClienteDTO que se desea actualizar
     * @return cliente en caso de actualizar
     * @throws ObjetosNegocioException en caso de un error al momento de actualizar
     */
    public ClienteDTO editarCliente (ClienteDTO clienteDTO) throws ObjetosNegocioException;
    
    /**
     * Método para eliminar un cliente del registro
     * @param telefonoCliente telefono único del cliente que se desea eliminar
     * @return true en caso de eliminar al cliente, false en caso de poder eliminar
     * @throws ObjetosNegocioException en caso de existir un error si se borra el cliente
     */
    public boolean eliminarCliente (String telefonoCliente) throws ObjetosNegocioException;
    
    /**
     * Método que consulta los clientes por medio de un nombre o parte del nombre
     * @param nombre nombre o parte del nombre del cliente
     * @return lista de clientes que se encontraron con ese nombre
     */
    public List<ClienteDTO> consultarClienteNombre (String nombre);
    
    /**
     * Método que consulta los clientes por medio de un telefono o parte del telefono
     * @param telefono telefono o parte del telefono del cliente
     * @return lista de clientes que se encontraron con esa parte de telefono
     */
    public List<ClienteDTO> consultarClienteTelefono (String telefono);
    
    /**
     * Método que consulta los clientes por medio de un email o parte del email
     * @param email email o parte del email del cliente
     * @return lista de clientes que se encontraron con esa parte del email
     */
    public List<ClienteDTO> consultarClienteEmail (String email);
    
    
    
    
}
