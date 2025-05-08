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
    public Cliente agregarCliente(Cliente cliente) throws PersistenciaSOFException;
    public List<Cliente> obtenerTodosClientes() throws PersistenciaSOFException;
    public List<Cliente> obtenerClientesSimilares(String nombre) throws PersistenciaSOFException;
    public Cliente obtenerCliente(String correo) throws PersistenciaSOFException;
    public Cliente eliminarCliente(String correo)throws PersistenciaSOFException;
    public Cliente editarCliente(String correo, Cliente cliente) throws PersistenciaSOFException;
    
    }
