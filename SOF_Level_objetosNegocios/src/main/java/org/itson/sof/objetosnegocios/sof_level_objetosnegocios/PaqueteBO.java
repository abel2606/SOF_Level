/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.IPaquetesDAO;
import org.itson.sof.persistencia.daos.PaquetesDAO;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class PaqueteBO implements IPaqueteBO {

    IPaquetesDAO paquetesDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public PaqueteBO() {
        IConexion conexion = new Conexion();
        this.paquetesDAO = new PaquetesDAO(conexion);
    }

    @Override
    public PaqueteDTO obtenerPaquete(PaqueteDTO paquete) throws ObjetosNegocioException {

        try {
            return ConverterUtil.paqueteEntidadADTO(paquetesDAO.obtenerPaquete(ConverterUtil.paqueteDTOAEntidad(paquete)));
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    @Override
    public List<PaqueteDTO> obtenerPaquete() throws ObjetosNegocioException { 
        List<PaqueteDTO> paquetesDTO = new LinkedList<>(); 

        try {
            List<Paquete> paquetes = paquetesDAO.obtenerPaquetes(); 

            for (Paquete paqueteEntidad : paquetes) {
                paquetesDTO.add(ConverterUtil.paqueteEntidadADTO(paqueteEntidad));
            }

            return paquetesDTO; 

        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(PaqueteBO.class.getName()).log(Level.SEVERE, "Error de persistencia al obtener paquetes", ex);
            throw new ObjetosNegocioException("No se pudieron obtener los paquetes debido a un error de datos");
        } catch (Exception ex) {
            Logger.getLogger(PaqueteBO.class.getName()).log(Level.SEVERE, "Error inesperado al obtener paquetes", ex);
            throw new ObjetosNegocioException("Ocurrió un error inesperado al procesar la solicitud de paquetes.");
        }
    }

}
