/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public interface IClientesDAO {

    /**
     * Agrega un nuevo cliente al sistema. Si ya existe un cliente con el mismo
     * correo, lanza una excepción.
     *
     * @param cliente Objeto Cliente que se desea agregar.
     * @return El cliente que fue agregado exitosamente.
     * @throws PersistenciaSOFException Si ya existe un cliente con el mismo
     * correo o ocurre un error al persistir.
     */
    public Cliente agregarCliente(Cliente cliente) throws PersistenciaSOFException;

    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return Una lista con todos los clientes almacenados.
     * @throws PersistenciaSOFException Si ocurre un error al recuperar los
     * clientes.
     */
    public List<Cliente> obtenerTodosClientes() throws PersistenciaSOFException;

    /**
     * Busca clientes cuyos nombres contengan una coincidencia parcial con el
     * nombre dado.
     *
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de clientes con nombres similares.
     * @throws PersistenciaSOFException Si ocurre un error al realizar la
     * búsqueda.
     */
    public List<Cliente> obtenerClientesSimilares(String nombre) throws PersistenciaSOFException;

    /**
     * Obtiene un cliente a partir de su correo electrónico.
     *
     * @param correo El correo electrónico del cliente a buscar.
     * @return El cliente que coincide con el correo.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o hay un
     * error al acceder a los datos.
     */
    public Cliente obtenerCliente(String correo) throws PersistenciaSOFException;

    /**
     * Elimina un cliente identificado por su correo electrónico.
     *
     * @param correo El correo del cliente a eliminar.
     * @return El cliente eliminado.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o ocurre
     * un error al eliminarlo.
     */
    public Cliente eliminarCliente(String correo) throws PersistenciaSOFException;

    /**
     * Edita los datos de un cliente identificado por su correo electrónico.
     *
     * @param correo El correo del cliente a editar.
     * @param cliente El nuevo objeto Cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o ocurre
     * un error al actualizarlo.
     */
    public Cliente editarCliente(String correo, Cliente cliente) throws PersistenciaSOFException;

}
