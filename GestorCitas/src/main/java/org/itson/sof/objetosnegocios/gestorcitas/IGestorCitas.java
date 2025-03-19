/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas;

import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.CitaDTO;

/**
 *
 * @author haesp
 */
public interface IGestorCitas {
 
    /**
     * Método para crear una cita a un contrato y asignar su fotografo
     * @param cita cita a crear
     * @param folioContrato folio del contrato
     * @param nombreUsuarioFotografo nombre de usuario del fotografo
     * @return cita creada
     * @throws GestorException 
     */
    public CitaDTO crearCita (CitaDTO cita, String folioContrato, String nombreUsuarioFotografo) throws GestorException;
    
    /**
     * Método para consultar ua cita
     * @param cita cita a consultar
     * @return cita consultada
     */
    public CitaDTO consultarCita (CitaDTO cita);
    
    /**
     * Metodo para actualizar una cita
     * @param cita cita a actualizar
     * @return cita actualizada
     * @throws GestorException 
     */
    public CitaDTO actualizarCita (CitaDTO cita) throws GestorException;
    
    /**
     * Método para eliminar una cita
     * @param cita cita a eliminar
     * @return cita eliminada
     * @throws GestorException 
     */
    public CitaDTO eliminarCita (CitaDTO cita) throws GestorException;
    
    
    
}
