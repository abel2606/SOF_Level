/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author haesp
 */
public interface ICitaMaterialDAO {
    
    /**
     * Agrega materiales a una cita
     * @param cita cita a la que se le asginara material
     * @param material material que se le asignará a la cita
     * @throws PersistenciaSOFException 
     */
    public void agregarCitaMaterial (Cita cita, Material material) throws PersistenciaSOFException;
    
    /**
     * Elimina la cita material
     * @param citaMaterial citamaterial
     * @throws PersistenciaSOFException 
     */
    public void eliminarCitaMaterial (CitaMaterial citaMaterial) throws PersistenciaSOFException;
    
    /**
     * Consulta los materiales de una cita
     * @param cita cita 
     * @param material material
     * @throws PersistenciaSOFException 
     */
    public void consultaCitaMaterial (Cita cita, Material material) throws PersistenciaSOFException;
    
    /**
     * Actualiza cantidad de un material en una cita
     * @param citaMaterial
     * @throws PersistenciaSOFException 
     */
    public void actualizarCitaMaterial (CitaMaterial citaMaterial) throws PersistenciaSOFException;
    
}
