/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public interface ICitaMaterialBO {
    
    /**
     * Agrega materiales a una cita
     * @param cita cita a la que se le asginara material
     * @param material material que se le asignará a la cita
     * @throws ObjetosNegocioException
     */
    public void agregarCitaMaterial (CitaDTO cita, MaterialDTO material) throws ObjetosNegocioException;
    
    /**
     * Elimina la cita material
     * @param citaMaterial citamaterial
     * @throws ObjetosNegocioException
     */
    public void eliminarCitaMaterial (CitaMaterialDTO citaMaterial) throws ObjetosNegocioException;
    
    /**
     * Consulta los materiales de una cita
     * @param cita cita 
     * @param material material
     * @throws ObjetosNegocioException
     */
    public void consultaCitaMaterial (CitaDTO cita, MaterialDTO material) throws ObjetosNegocioException;
    
    /**
     * Actualiza cantidad de un material en una cita
     * @param citaMaterial
     * @throws ObjetosNegocioException 
     */
    public void actualizarCitaMaterial (CitaMaterialDTO citaMaterial) throws ObjetosNegocioException;
    
}
