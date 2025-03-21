/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class ContratoBO implements IContratoBO{
    
    IContratosDAO contratosDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public ContratoBO() {
        IConexion conexion = new Conexion();
        this.contratosDAO = new ContratosDAO(conexion);
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
    public ContratoDTO obtenerContratoFolio(String folio) throws ObjetosNegocioException {
        
        try {
            return ConverterUtil.contratoEntidadADTO(contratosDAO.obtenerContratoFolio(folio));
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException (ex.getMessage());
        }
        
        
    }

    @Override
    public List<ContratoDTO> obtenerTotalContratos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
