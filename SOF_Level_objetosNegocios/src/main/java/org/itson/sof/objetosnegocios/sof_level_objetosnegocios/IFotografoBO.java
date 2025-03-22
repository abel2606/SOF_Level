/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author haesp
 */
public interface IFotografoBO {
    
    /**
     * Obtiene todos los fotografos almacenados en la base de datos
     * @return regresa una lista de todos los fotografos
     * @throws ObjetosNegocioException lanza una exepcion de tipo persistencia
     */
    public List<FotografoDTO> obtenerTodosFotografos() throws ObjetosNegocioException;
    /**
     * Obtiene un fotografo con su nombre de usuario
     * @param nombreUsuario nombre de usuario del fotografo
     * @return fotografo obtenido
     * @throws ObjetosNegocioException 
     */
    public FotografoDTO obtenerFotografoNombreUsuario (String nombreUsuario) throws ObjetosNegocioException;
    
}
