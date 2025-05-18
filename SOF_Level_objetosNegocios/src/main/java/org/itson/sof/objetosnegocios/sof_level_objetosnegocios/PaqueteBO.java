/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.IPaquetesDAO;
import org.itson.sof.persistencia.daos.PaquetesDAO;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class PaqueteBO implements IPaqueteBO{
    
    IPaquetesDAO paquetesDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public PaqueteBO() {
        IConexion conexion = new Conexion();
        this.paquetesDAO = new PaquetesDAO(conexion);
    }
    
    

    @Override
    public PaqueteDTO obtenerPaquete(PaqueteDTO paquete) throws ObjetosNegocioException {
        
        try {
            return ConverterUtil.paqueteEntidadADTO (paquetesDAO.obtenerPaquete(ConverterUtil.paqueteDTOAEntidad(paquete)));
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException (ex.getMessage());
        }
        
    }
    
}
