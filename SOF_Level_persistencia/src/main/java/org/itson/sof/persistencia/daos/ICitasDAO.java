/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public interface ICitasDAO {
    
    /**
     * Obtiene la cita mediante el codigo unico que tiene
     * @param codigo codigo por el que se desea buscar
     * @return regresa la cita de la cita obtenida
     * @throws PersistenciaSOFException lanza un error en caso de no existir
     */
    public Cita obtenerCitaCodigo(String codigo) throws PersistenciaSOFException;
    
    public List<Cita> obtenerCitasContratos(long id) throws PersistenciaSOFException;
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
    
    /**
     * Elimina una cita existente
     * @param cita cita a eliminar
     * @return cita que se elimino
     */
    public Cita eliminarcita(Cita cita);
    
    
    /**
     * Obtiente una lista de citas por medio de la fecha hora de inicio y fecha hora de fin
     * @param cita cita que contiene las fechas 
     * @return lista de citas que coinciden
     * @throws PersistenciaSOFException
     */
    public List<Cita> obtenerCitasFecha (Cita cita) throws PersistenciaSOFException;
    
    /**
     * 
     * @param fecha
     * @return
     * @throws PersistenciaSOFException 
     */
    public List<Cita> obtenerCitasPorFecha(String fecha) throws PersistenciaSOFException;
    
}
