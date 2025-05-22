/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcontratos;

import org.itson.sof.objetosnegocios.gestorcontratos.gestorcontratosexception.GestorContratoException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public interface IGestorContratos {
    
    
    public ContratoDTO crearContrato(ContratoDTO contrato, ClienteDTO ciente, PaqueteDTO paquete)throws GestorContratoException;
    
    public ContratoDTO actualizarContrato (ContratoDTO contrato) throws GestorContratoException;
    
    public ContratoDTO cancelarContrato (ContratoDTO contrato) throws GestorContratoException;
    
    public ContratoDTO terminarContrato (ContratoDTO contrato) throws GestorContratoException;
    
    public boolean cancelarContratosCliente (String correo) throws GestorContratoException;
    
    
}
