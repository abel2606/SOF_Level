package org.itson.sof.objetosnegocios.gestorcostos.gestorexception;

import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;

/**
 *
 * @author haesp
 */
public class GestorCostosException extends Exception{

    public GestorCostosException(String par, ObjetosNegocioException ex) {
    }

    public GestorCostosException(String message) {
        super(message);
    } 
}
