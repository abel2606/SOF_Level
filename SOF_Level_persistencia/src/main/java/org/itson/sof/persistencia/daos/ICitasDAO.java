/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import org.itson.sof.persistencia.entidades.Cita;

/**
 *
 * @author Abe
 */
public interface ICitasDAO {
    /**
     * Metodo para obtener una cita
     * @param cita cita que se desea obtener
     * @return regresa la cita obtenida
     */
    public Cita obtenerCita(Cita cita);
    
    /**
     * Agrega una nueva cita
     * @return regresa una cita
     */
    public Cita agregarCita(Cita cita);
    
    
    /**
     * Actualiza una cita existente
     * @param cita cita a actualizar
     * @return regresa la cita actualizada
     */
    public Cita actualizarCita(Cita cita);
    
    public Cita eliminarcita(Cita cita);
    
    
}
