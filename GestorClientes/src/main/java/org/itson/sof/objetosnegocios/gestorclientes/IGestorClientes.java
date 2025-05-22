/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorclientes;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.sof_dtos.ClienteDTO;



/**
 *
 * @author haesp
 */
public interface IGestorClientes {
    ClienteDTO agregarCliente(ClienteDTO clienteDTO) throws GestorClientesException;

    List<ClienteDTO> obtenerTodosClientes() throws GestorClientesException;

    List<ClienteDTO> obtenerClientesSimilares(String nombre) throws GestorClientesException;

    ClienteDTO editarCliente(String correo, ClienteDTO clienteDTO) throws GestorClientesException;

    ClienteDTO cancelarCliente(String correo) throws GestorClientesException;

    ClienteDTO obtenerCliente(String correo) throws GestorClientesException;
    
}
