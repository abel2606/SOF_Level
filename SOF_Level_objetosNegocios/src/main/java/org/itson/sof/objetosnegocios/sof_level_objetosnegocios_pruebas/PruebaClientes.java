/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;

/**
 *
 * @author Abe
 */
public class PruebaClientes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ObjetosNegocioException {
        ClienteBO clienteBO = new ClienteBO();

        List<ClienteDTO> clientes =clienteBO.obtenerTodosClientes();
        for (ClienteDTO cliente : clientes) {
            System.out.println(cliente.toString());
        }
        List<ClienteDTO> cliente = clienteBO.obtenerClientesSimilares("uan");
        for (ClienteDTO clienteDTO : cliente) {
            System.out.println(clienteDTO.toString());
        }
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Abel");
        clienteDTO.setTelefono("44444");
        clienteDTO.setCorreo("abel35@gmail.com");
        clienteBO.agregarCliente(clienteDTO);

        
    }
}
