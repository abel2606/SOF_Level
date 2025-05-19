/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcontratos.pruebas;

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
        
        
        
        
        ClienteDTO cliente = new ClienteDTO();
        
        cliente.setCorreo("hector@gmail.com");
        
        PaqueteDTO paquete = new PaqueteDTO();
        
        paquete.setNombre("boda");
        
        ContratoDTO contrato = new ContratoDTO();
        
        contrato.setEstado("ACTIVO");
        contrato.setFolio("PRUEBA");
        contrato.setTematica("MODIFICACIÓN NUEVA");
        
        try {
            gestor.cancelarContrato(contrato);
        } catch (GestorContratoException ex) {
            System.err.println(ex.getMessage());
        }
        
        
    }
    
}
