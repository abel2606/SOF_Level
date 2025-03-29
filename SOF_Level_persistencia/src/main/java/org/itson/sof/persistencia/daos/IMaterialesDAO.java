/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author JazmE
 */
public interface IMaterialesDAO {

    public List<Material> obtenerTodosMateriales() throws PersistenciaSOFException;

    public List<CitaMaterial> obtenerMaterialesCita(String codigo) throws PersistenciaSOFException;
}
