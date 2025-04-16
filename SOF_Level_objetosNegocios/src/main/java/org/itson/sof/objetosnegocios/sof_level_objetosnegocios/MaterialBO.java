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
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class MaterialBO implements IMaterialBO {

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
    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO citaDTO) throws ObjetosNegocioException {
        try {
            // Obtener la lista de CitaMaterial desde el DAO usando el código de la cita
            List<CitaMaterial> citaMateriales = materialesDAO.obtenerMaterialesCita(citaDTO.getCodigo());

            // Convertir los CitaMaterial a CitaMaterialDTO usando ConverterUtil
            List<CitaMaterialDTO> citaMaterialDTOs = ConverterUtil.citaMaterialesEntidadADTO(citaMateriales); // Llamamos a la versión que acepta lista

            return citaMaterialDTOs;
        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(MaterialBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjetosNegocioException("Error al obtener los materiales de la cita: " + ex.getMessage());
        }
    }

    @Override
    public MaterialDTO obtenerMaterialPorNombre(String nombre) throws ObjetosNegocioException {
        try {
            Material entidad = materialesDAO.obtenerMaterialPorNombre(nombre);
            return ConverterUtil.materialEntidadADTO(entidad);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException("No se pudo obtener el material: " + ex.getMessage());
        }
    }

    @Override
    public void actualizarMaterial(MaterialDTO materialDTO) throws ObjetosNegocioException {
        try {
            Material entidad = ConverterUtil.materialDTOAEntidad(materialDTO);
            materialesDAO.actualizarMaterial(entidad);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException("No se pudo actualizar el material: " + ex.getMessage());
        }
    }

}
