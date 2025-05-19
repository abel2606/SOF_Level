/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public interface IPaqueteBO {
    
    
    public PaqueteDTO obtenerPaquete (PaqueteDTO paquete) throws ObjetosNegocioException;
    
    public List<PaqueteDTO> obtenerPaquete () throws ObjetosNegocioException;
    
}
