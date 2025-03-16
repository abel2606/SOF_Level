package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.GregorianCalendar;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.sof_dtos.CitaDTO;

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
        
        Contrato contrato = new Contrato();
        
        
        
    }
    
}
