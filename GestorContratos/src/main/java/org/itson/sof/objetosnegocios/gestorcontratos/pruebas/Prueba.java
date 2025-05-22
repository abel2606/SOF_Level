/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcontratos.pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcontratos.GestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.IGestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorcontratosexception.GestorContratoException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        IGestorContratos gestor = new GestorContratos ();
        
        
        
        try {
            gestor.cancelarContratosCliente("abel35@gmail.com");
        } catch (GestorContratoException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
