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
import org.itson.sof.persistencia.daos.FotografoDAO;
import org.itson.sof.persistencia.daos.IFotografoDAO;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author haesp
 */
public class FotografoBO implements IFotografoBO{

    
    IFotografoDAO fotografosDAO;
    private static final Logger LOG = Logger.getLogger(FotografoBO.class.getName());

    public FotografoBO() {
        IConexion conexion = new Conexion();
        this.fotografosDAO = new FotografoDAO(conexion);
    }
    
    @Override
    public FotografoDTO obtenerFotografoNombreUsuario(String nombreUsuario) throws ObjetosNegocioException {
        
        try {
            return ConverterUtil.fotografoEntidadADTO(fotografosDAO.obtenerFotografoNombreUsuario(nombreUsuario));
        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(FotografoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjetosNegocioException (ex.getMessage()); 
        }
        
    }
    
}
