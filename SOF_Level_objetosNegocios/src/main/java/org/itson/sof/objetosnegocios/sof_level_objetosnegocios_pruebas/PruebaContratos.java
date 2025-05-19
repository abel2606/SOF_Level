/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class PruebaContratos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        IContratoBO contratosBO = new ContratoBO();
        
        
        ClienteDTO cliente = new ClienteDTO();
        
        cliente.setId(2L);
        
        PaqueteDTO paquete = new PaqueteDTO();
        
        paquete.setId(2L);
        
        ContratoDTO contrato = new ContratoDTO();
        
        contrato.setEstado("ACTIVO");
        contrato.setFolio("dadasdasd");
        contrato.setTematica("tematica");
        
        try {
            contratosBO.terminarContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            System.err.println("Error");
        }
        
        
        
    }
    
}
