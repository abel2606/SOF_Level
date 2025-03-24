/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.IMaterialesDAO;
import org.itson.sof.persistencia.daos.MaterialesDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class MaterialBO implements IMaterialBO{

    
    private IMaterialesDAO materialesDAO;

    public MaterialBO() {
        IConexion conexion = new Conexion();
        this.materialesDAO = new MaterialesDAO(conexion);
    }
    
    @Override
    public List<MaterialDTO> obtenerMateriales() throws ObjetosNegocioException {
        
        try {
            // Obtienes las entidades de contratos desde el DAO
            List<Material> materiales = this.materialesDAO.obtenerTodosMateriales();

            // Si la lista no está vacía, conviértela
            if (materiales != null && !materiales.isEmpty()) {
                List<MaterialDTO> contratosDTO = materiales.stream()
                        .map(entidad -> ConverterUtil.materialEntidadADTO(entidad)) // Conversión de entidad a DTO
                        .collect(Collectors.toList());
                return contratosDTO;
            } else {
                System.out.println("No se encontraron contratos.");
                return new ArrayList<>();
            }

        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(ContratoBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public List<MaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws ObjetosNegocioException {
        try {
            // Obtener la lista de Materiales desde el DAO
            List<Material> materiales = materialesDAO.obtenerMaterialesCita(cita.getId());
            System.out.println(materiales);
            // Convertir cada Material a MaterialDTO
            List<MaterialDTO> materialDTO = new ArrayList<>();
            for (Material material : materiales) {
                materialDTO.add(ConverterUtil.materialEntidadADTO(material));
            }

            return materialDTO;
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }
    
}
