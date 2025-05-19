/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcontratos;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorcontratosexception.GestorContratoException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IPaqueteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.PaqueteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class GestorContratos implements IGestorContratos{
    
    private IClienteBO clienteBO;
    private IContratoBO contratoBO;
    private IPaqueteBO paqueteBO;

    public GestorContratos() {
        clienteBO = new ClienteBO();
        contratoBO = new ContratoBO();
        paqueteBO = new PaqueteBO();
    }

    @Override
    public ContratoDTO crearContrato(ContratoDTO contrato, ClienteDTO cliente, PaqueteDTO paquete) throws GestorContratoException {
        
        try {
            ClienteDTO clienteEncontrado = clienteBO.obtenerCliente(cliente.getCorreo());
            PaqueteDTO paqueteEncontrado = paqueteBO.obtenerPaquete(paquete);
            
            contrato.setEstado("ACTIVO");
            
            return contratoBO.crearContrato(contrato, clienteEncontrado, paqueteEncontrado);
            
        } catch (ObjetosNegocioException ex) {
            throw new GestorContratoException (ex.getMessage(), ex.getCause());
        }
        
    }

    @Override
    public ContratoDTO actualizarContrato(ContratoDTO contrato) throws GestorContratoException {
        
        String estado = contrato.getEstado();
        
        if ((estado.equalsIgnoreCase("cancelado") || estado.equalsIgnoreCase("terminado"))){
            throw new GestorContratoException ("No es posible modificar un contrato cancelado o terminado");
        }
        
        try {
            return contratoBO.actualizarContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            throw new GestorContratoException (ex.getMessage(), ex.getCause());
        }
        
    }

    @Override
    public ContratoDTO cancelarContrato(ContratoDTO contrato) throws GestorContratoException {
        
        try {
            ContratoDTO contratoExistente = contratoBO.obtenerContratoFolio(contrato.getFolio());
            if (contratoExistente == null) {
                throw new GestorContratoException("No se encontró un contrato con el folio: " + contrato.getFolio());
            }

            String estadoActual = contratoExistente.getEstado();

            if (estadoActual.equalsIgnoreCase("CANCELADO")) {
                throw new GestorContratoException("El contrato ya se encuentra cancelado. Folio: " + contrato.getFolio());
            }
            if (estadoActual.equalsIgnoreCase("TERMINADO")) {
                throw new GestorContratoException("No se puede cancelar un contrato que ya está terminado. Folio: " + contrato.getFolio());
            }

            return contratoBO.cancelarContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            throw new GestorContratoException("Error al cancelar el contrato: " + ex.getMessage(), ex);
        }
        
    }

    @Override
    public ContratoDTO terminarContrato(ContratoDTO contrato) throws GestorContratoException {
        
        try {
            ContratoDTO contratoExistente = contratoBO.obtenerContratoFolio(contrato.getFolio());
            if (contratoExistente == null) {
                throw new GestorContratoException("No se encontró un contrato con el folio: " + contrato.getFolio());
            }

            String estadoActual = contratoExistente.getEstado();

            if (estadoActual.equalsIgnoreCase("TERMINADO")) {
                throw new GestorContratoException("El contrato ya se encuentra terminado. Folio: " + contrato.getFolio());
            }
            if (estadoActual.equalsIgnoreCase("CANCELADO")) {
                throw new GestorContratoException("No se puede terminar un contrato que ya está cancelado. Folio: " + contrato.getFolio());
            }

            return contratoBO.terminarContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            throw new GestorContratoException("Error al terminar el contrato: " + ex.getMessage(), ex);
        }
        
    }
    
    
    
    
}
