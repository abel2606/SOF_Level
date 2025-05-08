/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas.gestorexception;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;

/**
 *
 * @author haesp
 */
public class GestorException extends Exception{

    public GestorException(String par, ObjetosNegocioException ex) {
    }

    public GestorException(String message) {
        super(message);
    }
    
    
    
    
    
}
