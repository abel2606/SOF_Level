package org.itson.sof.objetosnegocios.gestorclientes;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;

/**
 *
 * @author haesp
 */
public class GestorClientes implements IGestorClientes {

    private static GestorClientes gestor;
    private final IClienteBO clienteBO;

    private GestorClientes() {
        this.clienteBO = new ClienteBO();
    }

    public static GestorClientes getInstance() {
        if (gestor == null) {
            synchronized (GestorClientes.class) {
                if (gestor == null) {
                    gestor = new GestorClientes();
                }
            }
        }
        return gestor;
    }

    @Override
    public ClienteDTO agregarCliente(ClienteDTO clienteDTO) throws GestorClientesException {
        try {
            return clienteBO.agregarCliente(clienteDTO);
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al agregar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodosClientes() throws GestorClientesException {
        try {
            return clienteBO.obtenerTodosClientes();
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al obtener los clientes: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<ClienteDTO> obtenerClientesSimilares(String nombre) throws GestorClientesException {
        try {
            return clienteBO.obtenerClientesSimilares(nombre);
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al buscar clientes similares: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ClienteDTO editarCliente(String correo, ClienteDTO clienteDTO) throws GestorClientesException {
        try {
            return clienteBO.editarCliente(correo, clienteDTO);
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al editar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean eliminarCliente(String correo) throws GestorClientesException {
        try {
            return clienteBO.eliminarCliente(correo);
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al eliminar cliente: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ClienteDTO obtenerCliente(String correo) throws GestorClientesException {
        try {
            return clienteBO.obtenerCliente(correo);
        } catch (ObjetosNegocioException ex) {
            throw new GestorClientesException("Error al obtener cliente: " + ex.getMessage(), ex);
        }
    }
}
