/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.persistencia.pruebas;

import java.util.List;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.MaterialesDAO;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class PruebaMateriales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexion conexion = new Conexion();
        MaterialesDAO materialesDAO = new MaterialesDAO(conexion);
        String codigoCita = "CITA123"; 

        try {
            List<CitaMaterial> citaMateriales = materialesDAO.obtenerMaterialesCita(codigoCita);

            if (citaMateriales.isEmpty()) {
                System.out.println("No se encontraron materiales para la cita con código: " + codigoCita);
            } else {
                // Imprimir los materiales en formato tabla
                System.out.println("Materiales para la Cita con código: " + codigoCita);
                System.out.println("---------------------------------------------------");
                System.out.printf("%-20s%-10s\n", "Nombre del Material", "Cantidad"); 

                for (CitaMaterial citaMaterial : citaMateriales) {
                    // Imprimir el nombre del material y la cantidad
                    String nombreMaterial = citaMaterial.getMaterial().getNombre();
                    float cantidadMaterial = citaMaterial.getCantidad();
                    System.out.printf("%-20s%-10.2f\n", nombreMaterial, cantidadMaterial);
                }
            }
        } catch (PersistenciaSOFException e) {
            System.out.println("Error al obtener los materiales de la cita: " + e.getMessage());
        }
    }

}
