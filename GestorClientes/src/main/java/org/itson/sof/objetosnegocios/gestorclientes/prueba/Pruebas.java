/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorclientes.prueba;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorclientes.GestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.IGestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;

/**
 *
 * @author haesp
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        IGestorClientes gestorCliente = new GestorClientes();
        
        try {
            gestorCliente.cancelarCliente("abel35@gmail.com");
        } catch (GestorClientesException ex) {
            Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
