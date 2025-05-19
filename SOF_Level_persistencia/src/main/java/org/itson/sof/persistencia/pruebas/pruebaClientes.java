/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.persistencia.pruebas;

import java.util.List;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ClientesDAO;
import org.itson.sof.persistencia.daos.IClientesDAO;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class pruebaClientes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PersistenciaSOFException {
        IConexion conexion = new Conexion();
        IClientesDAO clienteDAO = new ClientesDAO(conexion);

//        List<Cliente> clientes = cliente.obtenerTodosClientes();
//        
//        for (Cliente cliente1 : clientes) {
//            System.out.println(cliente1);
//            for (Contrato contrato : cliente1.getContratos()) {
//                System.out.println(contrato.toString());
//            }
//        }
//        Cliente cliente = clienteDAO.obtenerCliente("juan@example.com");
//        System.out.println(cliente.toString());
//        
//        for (Cliente cliente : clientes) {
//            System.out.println(cliente.toString());
//            
//        }
//
//          Cliente cliente = clienteDAO.eliminarCliente("hola@gmail.com");
//          System.out.println(cliente.toString());
//            Cliente clienteActializar = new Cliente();
//            clienteActializar.setNombre("Abel Eduaro Sanchez Guerrero");
//            clienteActializar.setCorreo("abel.san@gmail.com");
//            clienteActializar.setTelefono("6441297653");
//            
//            Cliente clienteActualizado = clienteDAO.editarCliente("abel@gmail.com", clienteActializar);
//            
        Cliente cliente = new Cliente();
        cliente.setCorreo("ricardo2606@example.com");
        cliente.setNombre("Ricardo Alan Gutierrez Garces");
        cliente.setTelefono("644129338");
        //Cliente nuevoCliente = clienteDAO.agregarCliente(cliente);
        
       Cliente clienteActualizado = clienteDAO.editarCliente("ricardo5@gmail.com", cliente);
    }

}
