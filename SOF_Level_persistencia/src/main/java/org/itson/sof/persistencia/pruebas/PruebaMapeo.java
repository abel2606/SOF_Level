package org.itson.sof.persistencia.pruebas;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

public class PruebaMapeo {

    static final Logger logger = Logger.getLogger(PruebaMapeo.class.getName());

    public static void main(String[] args) {
        boolean salir = false;
        IConexion conexion;
        conexion = new Conexion();

        IContratosDAO contratosDAO = new ContratosDAO(conexion);

        
        while (salir == true) {
            System.out.println("*****************");
            System.out.println("Total de contratos:");
            List<Contrato> contratos = null;
            try {
                System.out.println("Hola");
                contratos = contratosDAO.obtenerTotalContratos();
                for (Contrato contrato : contratos) {
                    System.out.println(contrato.toString());
                }

            } catch (PersistenciaSOFException ex) {
                System.out.println("No hola");
                Logger.getLogger(PruebaMapeo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            

        }

//        // Crear un Cliente
//        Cliente cliente = new Cliente();
//        cliente.setNombre("Juan Pérez");
//        cliente.setTelefono("555-1234");
//        cliente.setCorreo("juan.perez@correo.com");
//
//        //Crear algunos contratos
//        Contrato contrato1 = new Contrato();
//        contrato1.setTematica("Contrato de Fotografía Básico");
//        contrato1.setCliente(cliente);
//
//        Contrato contrato2 = new Contrato();
//        contrato2.setTematica("Contrato de Fotografía Avanzado");
//        contrato2.setCliente(cliente);
//
//        //Añadir los contratos al cliente
//        List<Contrato> contratos = new ArrayList<>();
//        contratos.add(contrato1);
//        contratos.add(contrato2);
//        //cliente.setContratos((Set<Contrato>) contratos);
//
//        // Persistir el Cliente (esto también persistirá los Contratos debido a la relación CascadeType.ALL)
//        em.persist(cliente);
//
//        // Confirmar la transacción
//        em.getTransaction().commit();
//
//        // Verificar si el cliente fue guardado correctamente
//        Cliente clientePersistido = em.find(Cliente.class, cliente.getId());
//        System.out.println("Cliente guardado: " + clientePersistido.getNombre());
//
//        // Verificar si los contratos se guardaron
//        /*
//        for (Contrato contrato : clientePersistido.getContratos()) {
//            System.out.println("Contrato de cliente: " + contrato.getTematica());
//        }*/
//
//        // Cerrar el EntityManager
//        em.close();
    }
}
