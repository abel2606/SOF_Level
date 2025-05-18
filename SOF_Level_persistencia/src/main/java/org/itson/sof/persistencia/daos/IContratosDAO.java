/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public interface IContratosDAO {
    
    public List<Contrato> obtenerTotalContratos() throws PersistenciaSOFException;
    
    public List<Contrato> obtenerContratosPorCliente(Cliente cliente) throws PersistenciaSOFException;
    
    public Contrato obtenerContratoFolio (String folio)throws PersistenciaSOFException;
    
    public Contrato crearContrato (Contrato contrato, Cliente cliente, Paquete paquete) throws PersistenciaSOFException;
    
    public Contrato actualizarContrato(Contrato contrato) throws PersistenciaSOFException;
    
    public Contrato cancelarContrato (Contrato contrato) throws PersistenciaSOFException;
    
    public Contrato terminarContrato (Contrato contrato) throws PersistenciaSOFException;
    
    public Contrato actualizarEstadoContrato (Contrato contrato, String nuevoEstado) throws PersistenciaSOFException;
}
