/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.IContratos;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class ContratoBO implements IContratoBO{
    
    IContratos contratosDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public ContratoBO(IContratos contratosDAO) {
        IConexion conexion = new Conexion();
        //this.contratosDAO = new Contratos(conexion);
    }
    
    

    @Override
    public ContratoDTO crearContrato(ContratoDTO contrato, ClienteDTO cliente) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ContratoDTO actualizarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ContratoDTO obtenerContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
