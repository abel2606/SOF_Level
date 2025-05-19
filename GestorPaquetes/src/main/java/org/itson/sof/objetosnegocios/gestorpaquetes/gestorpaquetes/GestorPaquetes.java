/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorpaquetes.gestorpaquetes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorpaquetesexception.GestorPaqueteException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IPaqueteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.PaqueteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class GestorPaquetes implements IGestorPaquetes{
    
    private IPaqueteBO paqueteBO;

    public GestorPaquetes() {
        paqueteBO = new PaqueteBO();
    }
    
    

    @Override
    public List<PaqueteDTO> obtenerPaquetes() throws GestorPaqueteException {
        
        try {
            return paqueteBO.obtenerPaquete();
        } catch (ObjetosNegocioException ex) {
            throw new GestorPaqueteException (ex.getMessage());
        }
        
    }

    @Override
    public PaqueteDTO obtenerPaquete(PaqueteDTO paquete) throws GestorPaqueteException {
        try {
            return paqueteBO.obtenerPaquete(paquete);
        } catch (ObjetosNegocioException ex) {
            throw new GestorPaqueteException (ex.getMessage());
        }
    }
    
}
