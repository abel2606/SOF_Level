/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;

/**
 *
 * @author Abe
 */
public class PruebaHorario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ObjetosNegocioException {
        // TODO code application logic here
        ICitaBO citasBO = new CitaBO();
        CitaDTO cita = new CitaDTO();
        List<String> horarios = citasBO.obtenerHorariosDisponibles("2024-03-18");
        System.out.println(horarios);
    }

}
