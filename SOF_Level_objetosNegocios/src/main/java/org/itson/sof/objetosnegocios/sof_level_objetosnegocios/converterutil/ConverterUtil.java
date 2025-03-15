package org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil;

import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.sof_dtos.CitaDTO;

/**
 *
 * @author haesp
 */
public class ConverterUtil {

    /**
     * Convierte una citaDTO a una cita entidad
     * @param citaDTO cita que se desea convertir
     * @return cita convertida
     */
    public static Cita citaDTOAEntidad(CitaDTO citaDTO){
        
        Cita cita = new Cita();
        //cita.setContrato(citaDTO.getContrato());
        cita.setExtras(citaDTO.getExtras());
        cita.setFechaHoraFin(citaDTO.getFechaHoraFin());
        cita.setFechaHoraInicio(citaDTO.getFechaHoraInicio());
        //cita.setFotografo(citaDTO.getFotografo());
        cita.setLugar(citaDTO.getLugar());
        //cita.setMateriales(citaDTO.getMateriales());
        return cita;
    }
    
    /**
     * Convierte una citaD entidad a una citaDTO
     * @param cita cita que se desea convertir
     * @return cita convertida
     */
    public static CitaDTO citaEntidadADTO (Cita cita){
        
        CitaDTO citaDTO = new CitaDTO();
        //citaDTO.setContrato(cita.getContrato());
        citaDTO.setExtras(cita.getExtras());
        citaDTO.setFechaHoraFin(cita.getFechaHoraFin());
        citaDTO.setFechaHoraInicio(cita.getFechaHoraInicio());
        //citaDTO.setFotografo(cita.getFotografo());
        citaDTO.setLugar(cita.getLugar());
        //citaDTO.setMateriales(cita.getMateriales();
        
        return citaDTO;
                
    }
    
}
