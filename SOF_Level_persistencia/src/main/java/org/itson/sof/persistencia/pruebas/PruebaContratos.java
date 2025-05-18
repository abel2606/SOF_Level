/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.persistencia.pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ClientesDAO;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.IClientesDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.daos.IPaquetesDAO;
import org.itson.sof.persistencia.daos.PaquetesDAO;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author haesp
 */
public class PruebaContratos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexion conexion = new Conexion();

        IContratosDAO contratosDAO;
        contratosDAO = new ContratosDAO(conexion);

        IClientesDAO clientesDAO;
        clientesDAO = new ClientesDAO(conexion);

        IPaquetesDAO paquetesDAO;
        paquetesDAO = new PaquetesDAO(conexion);

        try {
            Cliente cliente = clientesDAO.obtenerCliente("chuy@gmail.com");
            Paquete paquetee = new Paquete();
            paquetee.setNombre("quince");
            Paquete paquete = paquetesDAO.obtenerPaquete(paquetee);
            Contrato contrato = new Contrato();

            
            contrato.setEstado("ACTIVO");
            contrato.setFolio("789012");
            contrato.setTematica("calmate chuy");
            contrato.setPaquete(paquete);
            contrato.setCliente(cliente);

            contratosDAO.terminarContrato(contrato);
        } catch (PersistenciaSOFException ex) {
            System.err.println("valiste verga");
        }





    }

}
