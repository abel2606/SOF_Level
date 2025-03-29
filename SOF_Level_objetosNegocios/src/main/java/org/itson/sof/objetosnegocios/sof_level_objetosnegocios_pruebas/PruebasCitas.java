package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IMaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.MaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;

/**
 *
 * @author haesp
 */
public class PruebasCitas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ObjetosNegocioException {
        IMaterialBO materialBO = new MaterialBO();
        ICitaBO citasBO = new CitaBO();
        CitaDTO cita = new CitaDTO();
        cita.setCodigo("CITA123");
        try {
            // Obtener la cita desde el negocio
            CitaDTO citaObtenida = citasBO.obtenerCita(cita);

            if (citaObtenida != null) {
                System.out.println("Cita obtenida con código: " + citaObtenida.getCodigo());

                // Obtener los materiales asociados a la cita
                List<CitaMaterialDTO> materialesCita = materialBO.obtenerMaterialesCita(citaObtenida);

                // Imprimir los materiales obtenidos
                if (materialesCita.isEmpty()) {
                    System.out.println("No se encontraron materiales para la cita con código: " + citaObtenida.getCodigo());
                } else {
                    System.out.println("Materiales para la Cita con código: " + citaObtenida.getCodigo());
                    System.out.println("---------------------------------------------------");
                    System.out.printf("%-20s%-10s\n", "Nombre del Material", "Cantidad");

                    // Iterar y mostrar los materiales y sus cantidades
                    for (CitaMaterialDTO citaMaterialDTO : materialesCita) {
                        String nombreMaterial = citaMaterialDTO.getMaterial().getNombre();
                        float cantidadMaterial = citaMaterialDTO.getCantidad();
                        System.out.printf("%-20s%-10.2f\n", nombreMaterial, cantidadMaterial);
                    }
                }
            } else {
                System.out.println("Cita no encontrada con el código: " + cita.getCodigo());
            }
        } catch (ObjetosNegocioException e) {
            // Manejo de excepciones en caso de fallos en la capa de negocio
            System.out.println("Error al obtener la cita o los materiales: " + e.getMessage());
        }

//        ICitaBO citaBO = new CitaBO();
//        IContratoBO contratoBO = new ContratoBO();
//        IFotografoBO fotografoBO = new FotografoBO();
//        
//        CitaDTO citaDTO = new CitaDTO();
//        
//        citaDTO.setCodigo("12345");
//        citaDTO.setExtras("Llevar globos");
//        citaDTO.setFechaHoraFin(new GregorianCalendar(2025, 3, 18, 11, 0));
//        citaDTO.setFechaHoraInicio(new GregorianCalendar(2025, 3, 18, 10, 0));
//        citaDTO.setLugar("La laguna");
//        
//        ContratoDTO contrato = new ContratoDTO();
//        try {
//            contrato = contratoBO.obtenerContratoFolio("FOLIO123");
//        } catch (ObjetosNegocioException ex) {
//            Logger.getLogger(PruebasCitas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        FotografoDTO fotografo = new FotografoDTO();
//            
//        try {
//            fotografo = fotografoBO.obtenerFotografoNombreUsuario("carlosL");
//        } catch (ObjetosNegocioException ex) {
//            Logger.getLogger(PruebasCitas.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        citaDTO.setContrato(contrato);
//        citaDTO.setFotografo(fotografo);
//        
//        try{
//            citaBO.actualizarCita(citaDTO);
//        }catch (ObjetosNegocioException ex){
//            System.err.println(ex.getMessage());
//        }
//        
//        
    }

}
