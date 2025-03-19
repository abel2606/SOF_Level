package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IFotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author haesp
 */
public class GestorCitas implements IGestorCitas{
    
    private ICitaBO citaBO;
    private IContratoBO contratoBO;
    private IFotografoBO fotografoBO;

    public GestorCitas() {
        this.citaBO = new CitaBO();
        this.contratoBO = new ContratoBO();
        this.fotografoBO = new FotografoBO();
    }
    
    
    @Override
    public CitaDTO crearCita (CitaDTO cita, String folioContrato, String nombreUsuarioFotografo) throws GestorException {
        
        try {
            ContratoDTO contrato = contratoBO.obtenerContratoFolio(folioContrato);
            FotografoDTO fotografo = fotografoBO.obtenerFotografoNombreUsuario(nombreUsuarioFotografo);
            
            cita.setContrato(contrato);
            cita.setFotografo(fotografo);
            
            return citaBO.crearCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException (ex.getMessage());
        } 
    }

    @Override
    public CitaDTO consultarCita(CitaDTO cita) {
        
        try {
            return citaBO.obtenerCita(cita);
        } catch (ObjetosNegocioException ex) {
            return null;
        }
        
    }

    @Override
    public CitaDTO actualizarCita(CitaDTO cita) throws GestorException {
        
        try {
            return citaBO.actualizarCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException (ex.getMessage());
        }
        
    }

    @Override
    public CitaDTO eliminarCita(CitaDTO cita) throws GestorException {
        
        try {
            return citaBO.eliminarCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException (ex.getMessage());
        }
        
    }
    
    
}
