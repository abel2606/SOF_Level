/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.CitaMaterialDAO;
import org.itson.sof.persistencia.daos.ICitaMaterialDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class CitaMaterialBO implements ICitaMaterialBO {
    
     private final ICitaMaterialDAO citaMaterialDAO;

    public CitaMaterialBO() {
        IConexion conexion = new Conexion();
        this.citaMaterialDAO = new CitaMaterialDAO(conexion);
    }
     
     

    @Override
    public void agregarCitaMaterial(CitaDTO citaDTO, MaterialDTO materialDTO) throws ObjetosNegocioException {
        try {
            // Convertimos los DTOs a entidades
            Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);
            Material material = ConverterUtil.materialDTOAEntidad(materialDTO);

            // Llamamos al DAO para persistir la relación
            citaMaterialDAO.agregarCitaMaterial(cita, material);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    @Override
    public void eliminarCitaMaterial(CitaMaterialDTO citaMaterial) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void consultaCitaMaterial(CitaDTO cita, MaterialDTO material) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarCitaMaterial(CitaMaterialDTO citaMaterial) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
