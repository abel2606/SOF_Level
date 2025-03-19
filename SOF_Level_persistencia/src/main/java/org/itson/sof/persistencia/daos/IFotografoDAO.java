/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public interface IFotografoDAO {
    /**
     * Obtiene todos los fotografos almacenados en la base de datos
     * @return regresa una lista de todos los fotografos
     * @throws PersistenciaSOFException lanza una exepcion de tipo persistencia
     */
    public List<Fotografo> obtenerTodosFotografos() throws PersistenciaSOFException;
    
    /**
     * Obtiene un fotografo según el nombre de usuario
     * @param nombreUsuario nombre del usuario
     * @return fotografo obtenido
     * @throws PersistenciaSOFException 
     */
    public Fotografo obtenerFotografoNombreUsuario (String nombreUsuario) throws PersistenciaSOFException;

}
