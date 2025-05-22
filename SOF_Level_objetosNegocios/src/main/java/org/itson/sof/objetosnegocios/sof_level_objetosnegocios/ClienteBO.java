/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import java.util.stream.Collectors;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ClientesDAO;
import org.itson.sof.persistencia.daos.IClientesDAO;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.ClienteDTO;

/**
 *
 * @author haesp
 */
public class ClienteBO implements IClienteBO {

    private IClientesDAO clientesDAO;

    public ClienteBO() {
        IConexion conexion = new Conexion();
        this.clientesDAO = new ClientesDAO(conexion);
    }

    @Override
    public ClienteDTO agregarCliente(ClienteDTO clienteDTO) throws ObjetosNegocioException {
        Cliente cliente = ConverterUtil.clienteDTOAEntidad(clienteDTO);
        cliente.setEstado("ACTIVO");
        try {
            if (clientesDAO.agregarCliente(cliente) == null) {
                throw new ObjetosNegocioException("No se pudo agregar el cliente");
            }
            return clienteDTO;
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodosClientes() throws ObjetosNegocioException {
        try {
            List<Cliente> clientes = clientesDAO.obtenerTodosClientes();
            return clientes.stream()
                    .map(ConverterUtil::clienteEntidadADTO)
                    .collect(Collectors.toList());
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<ClienteDTO> obtenerClientesSimilares(String nombre) throws ObjetosNegocioException {
        try {
            List<Cliente> clientes = clientesDAO.obtenerClientesSimilares(nombre);
            return clientes.stream()
                    .map(ConverterUtil::clienteEntidadADTO)
                    .collect(Collectors.toList());
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public ClienteDTO editarCliente(String correo, ClienteDTO clienteDTO) throws ObjetosNegocioException {
        try {
            Cliente cliente = ConverterUtil.clienteDTOAEntidad(clienteDTO);
            Cliente actualizado = clientesDAO.editarCliente(correo, cliente);
            return ConverterUtil.clienteEntidadADTO(actualizado);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public ClienteDTO cancelarCliente(String correo) throws ObjetosNegocioException {
        try {
            
            return ConverterUtil.clienteEntidadADTO(clientesDAO.cancelarCliente(correo));
            
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public ClienteDTO obtenerCliente(String correo) throws ObjetosNegocioException {
        try {
            Cliente cliente = clientesDAO.obtenerCliente(correo);
            return ConverterUtil.clienteEntidadADTO(cliente);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

}
