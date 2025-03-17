package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.GregorianCalendar;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author haesp
 */
public class PruebasCitas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        ICitaBO citaBO = new CitaBO();
        
        CitaDTO citaDTO = new CitaDTO();
        
        citaDTO.setCodigo("100000");
        citaDTO.setExtras("Llevar un caguamon");
        citaDTO.setFechaHoraFin(new GregorianCalendar());
        citaDTO.setFechaHoraInicio(new GregorianCalendar());
        
        ContratoDTO contrato = new ContratoDTO();
        contrato.setFolio("FOLIO123");
        
        FotografoDTO fotografo = new FotografoDTO();
        
        fotografo.setNombreUsuario("carlosL");
        
        citaDTO.setContrato(contrato);
        citaDTO.setFotografo(fotografo);
        
        try{
            System.out.println(citaBO.obtenerCita(citaDTO));
        }catch (ObjetosNegocioException ex){
            System.out.println("IH NO SE PUDO");
        }
        
        
        
    }
    
}
