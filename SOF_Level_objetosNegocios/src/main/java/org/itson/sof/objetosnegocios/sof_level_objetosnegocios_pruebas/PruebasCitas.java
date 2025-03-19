package org.itson.sof.objetosnegocios.sof_level_objetosnegocios_pruebas;

import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IFotografoBO;
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
        IContratoBO contratoBO = new ContratoBO();
        IFotografoBO fotografoBO = new FotografoBO();
        
        CitaDTO citaDTO = new CitaDTO();
        
        citaDTO.setCodigo("12345");
        citaDTO.setExtras("Llevar globos");
        citaDTO.setFechaHoraFin(new GregorianCalendar());
        citaDTO.setFechaHoraInicio(new GregorianCalendar());
        
        ContratoDTO contrato = new ContratoDTO();
        try {
            contrato = contratoBO.obtenerContratoFolio("FOLIO123");
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(PruebasCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FotografoDTO fotografo = new FotografoDTO();
            
        try {
            fotografo = fotografoBO.obtenerFotografoNombreUsuario("carlosL");
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(PruebasCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        citaDTO.setContrato(contrato);
        citaDTO.setFotografo(fotografo);
        
        try{
            citaBO.crearCita(citaDTO);
        }catch (ObjetosNegocioException ex){
            System.out.println("IH NO SE PUDO");
        }
        
        
        
    }
    
}
