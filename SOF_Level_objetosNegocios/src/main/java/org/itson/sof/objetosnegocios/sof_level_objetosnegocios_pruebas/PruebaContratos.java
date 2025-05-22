/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IClienteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IPaqueteBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.PaqueteBO;
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
        IClienteBO clientesBO = new ClienteBO();
        IPaqueteBO paquetesBO = new PaqueteBO();

        try {
            //        ClienteDTO cliente1 = new ClienteDTO();
//        ClienteDTO cliente2 = new ClienteDTO();
//        ClienteDTO cliente3 = new ClienteDTO();
//        ClienteDTO cliente4 = new ClienteDTO();
//
//        cliente1.setCorreo("abel35@gmail.com");
//        cliente2.setCorreo("jorge@gmail.com");
//        cliente3.setCorreo("chuy@gmail.com");
//        cliente4.setCorreo("hector@gmail.com");
//
//        try {
//            cliente1 = clientesBO.obtenerCliente("abel35@gmail.com");
//            cliente2 = clientesBO.obtenerCliente("jorge@gmail.com");
//            cliente3 = clientesBO.obtenerCliente("chuy@gmail.com");
//            cliente4 = clientesBO.obtenerCliente("hector@gmail.com");
//        } catch (ObjetosNegocioException ex) {
//            Logger.getLogger(PruebaContratos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        PaqueteDTO paquete1 = new PaqueteDTO();
//        PaqueteDTO paquete2 = new PaqueteDTO();
//        PaqueteDTO paquete3 = new PaqueteDTO();
//        PaqueteDTO paquete4 = new PaqueteDTO();
//
//        paquete1.setNombre("boda");
//        paquete2.setNombre("quince");
//        paquete3.setNombre("bautizo");
//        paquete4.setNombre("graduacion");
//
//        try {
//            paquete1 = paquetesBO.obtenerPaquete(paquete1);
//            paquete2 = paquetesBO.obtenerPaquete(paquete2);
//            paquete3 = paquetesBO.obtenerPaquete(paquete3);
//            paquete4 = paquetesBO.obtenerPaquete(paquete4);
//        } catch (ObjetosNegocioException ex) {
//            Logger.getLogger(PruebaContratos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ContratoDTO contrato1 = new ContratoDTO();
//        ContratoDTO contrato2 = new ContratoDTO();
//        ContratoDTO contrato3 = new ContratoDTO();
//        ContratoDTO contrato4 = new ContratoDTO();
//
//        contrato1.setTematica("Vintage");
//        contrato2.setTematica("Vaqueros");
//        contrato3.setTematica("Disney");
//        contrato4.setTematica("Traje de gala");
//
//        try {
//            contratosBO.crearContrato(contrato1, cliente1, paquete1);
//            contratosBO.crearContrato(contrato2, cliente2, paquete2);
//            contratosBO.crearContrato(contrato3, cliente3, paquete3);
//            contratosBO.crearContrato(contrato4, cliente4, paquete4);
//        } catch (ObjetosNegocioException ex) {
//            Logger.getLogger(PruebaContratos.class.getName()).log(Level.SEVERE, null, ex);
//        }

            contratosBO.cancelarContratoCliente("abel35@gmail.com");
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(PruebaContratos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
