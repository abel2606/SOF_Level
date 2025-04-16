/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public interface IMaterialBO {

    public List<MaterialDTO> obtenerMateriales() throws ObjetosNegocioException;

    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws ObjetosNegocioException;

    public MaterialDTO obtenerMaterialPorNombre(String nombre) throws ObjetosNegocioException;

    public void actualizarMaterial(MaterialDTO materialDTO) throws ObjetosNegocioException;
}
