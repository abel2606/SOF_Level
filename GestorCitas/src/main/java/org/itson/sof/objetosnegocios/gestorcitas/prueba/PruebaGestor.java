/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas.prueba;

import java.util.ArrayList;
import java.util.List;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author Abe
 */
public class PruebaGestor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Crear una instancia del GestorCitas (utilizando el patrón singleton)
        GestorCitas gestorCitas = GestorCitas.getInstance();

        // Crear una cita con el código de la que quieres consultar los materiales
        CitaDTO cita = new CitaDTO();
        cita.setCodigo("CITA123"); // Código de la cita a consultar

        try {
            // Consultar la cita
            CitaDTO citaObtenida = gestorCitas.consultarCita(cita);

            if (citaObtenida != null) {
                System.out.println("Cita obtenida con código: " + citaObtenida.getCodigo());

                // Obtener los materiales asociados a esta cita
                List<CitaMaterialDTO> materialesCita = gestorCitas.obtenerMaterialesCita(citaObtenida);

                // Verificar si hay materiales
                if (materialesCita.isEmpty()) {
                    System.out.println("No se encontraron materiales para la cita con código: " + citaObtenida.getCodigo());
                } else {
                    System.out.println("Materiales para la Cita con código: " + citaObtenida.getCodigo());
                    System.out.println("---------------------------------------------------");
                    System.out.printf("%-20s%-10s\n", "Nombre del Material", "Cantidad");

                    // Imprimir los materiales y sus cantidades
                    for (CitaMaterialDTO citaMaterialDTO : materialesCita) {
                        String nombreMaterial = citaMaterialDTO.getMaterial().getNombre();
                        float cantidadMaterial = citaMaterialDTO.getCantidad();
                        System.out.printf("%-20s%-10.2f\n", nombreMaterial, cantidadMaterial);
                    }
                }
            } else {
                System.out.println("Cita no encontrada con el código: " + cita.getCodigo());
            }
        } catch (GestorCitasException e) {
            // Manejo de excepciones en caso de error en el gestor
            System.out.println("Error al obtener la cita o los materiales: " + e.getMessage());
        }

// Obtener la cita previamente
        CitaDTO citaActualizar = gestorCitas.consultarCita(cita);  // Cita que obtuviste previamente

// Crear un nuevo objeto de MaterialDTO para el globo
        MaterialDTO globoMaterial = new MaterialDTO();
        globoMaterial.setNombre("globo verde");
        globoMaterial.setCantidad(2.0f); // Se agrega 1 más de globo

// Crear CitaMaterialDTO para asociar el material con la cita
        CitaMaterialDTO citaMaterialDTO = new CitaMaterialDTO();
        citaMaterialDTO.setMaterial(globoMaterial);
        citaMaterialDTO.setCantidad(2.0f); // Aumentamos la cantidad del material

// Obtener la lista de materiales de la cita (no la sobrescribas)
        List<CitaMaterialDTO> listaMateriales = citaActualizar.getCitaMateriales();  // Obtener la lista de materiales

// Verificar si la lista de materiales es null
        if (listaMateriales == null) {
            // Si es null, inicializamos la lista
            listaMateriales = new ArrayList<>();
            citaActualizar.setCitaMateriales(listaMateriales);  // Asignamos la lista inicializada a la cita
        }

// Agregar el nuevo material a la lista existente (sin borrar los materiales previos)
        listaMateriales.add(citaMaterialDTO);  // Agregar el nuevo material

// Actualizamos la cita con el nuevo material agregado
        try {
            CitaDTO citaActualizada = gestorCitas.actualizarCita(citaActualizar);
            if (citaActualizada != null) {
                System.out.println("Cita actualizada con éxito. Material agregado: Globo Verde");
            } else {
                System.out.println("No se pudo actualizar la cita.");
            }
        } catch (GestorCitasException e) {
            System.out.println("Error al actualizar la cita: " + e.getMessage());
        }

    }
}
