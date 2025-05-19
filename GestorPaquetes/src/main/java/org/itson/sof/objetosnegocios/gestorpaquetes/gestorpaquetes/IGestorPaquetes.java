/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorpaquetes.gestorpaquetes;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorpaquetesexception.GestorPaqueteException;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public interface IGestorPaquetes {
    
    
    public List <PaqueteDTO> obtenerPaquetes() throws GestorPaqueteException;
    
    public PaqueteDTO obtenerPaquete (PaqueteDTO paquete) throws GestorPaqueteException;
    
}
