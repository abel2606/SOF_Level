/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author haesp
 */
public interface IFotografoBO {
    
    /**
     * Obtiene un fotografo con su nombre de usuario
     * @param nombreUsuario nombre de usuario del fotografo
     * @return fotografo obtenido
     * @throws ObjetosNegocioException 
     */
    public FotografoDTO obtenerFotografoNombreUsuario (String nombreUsuario) throws ObjetosNegocioException;
    
}
