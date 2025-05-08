/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.ClienteDTO;



/**
 *
 * @author haesp
 */
public interface IGestorClientes {
    ClienteDTO agregarCliente(ClienteDTO clienteDTO) throws GestorException;

    List<ClienteDTO> obtenerTodosClientes() throws GestorException;

    List<ClienteDTO> obtenerClientesSimilares(String nombre) throws GestorException;

    ClienteDTO editarCliente(String correo, ClienteDTO clienteDTO) throws GestorException;

    boolean eliminarCliente(String correo) throws GestorException;

    ClienteDTO obtenerCliente(String correo) throws GestorException;
    
}
