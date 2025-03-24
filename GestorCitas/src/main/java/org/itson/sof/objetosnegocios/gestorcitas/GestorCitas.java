package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IFotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IMaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.MaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class GestorCitas implements IGestorCitas{
    
    private static GestorCitas gestor;
    private ICitaBO citaBO;
    private IContratoBO contratoBO;
    private IMaterialBO materialBO;
    private IFotografoBO fotografoBO;

    private GestorCitas() {
        this.citaBO = new CitaBO();
        this.contratoBO = new ContratoBO();
        this.materialBO = new MaterialBO();
        this.fotografoBO = new FotografoBO();
    }
    
    
    public static GestorCitas getInstance() {
        if (gestor == null) {
            synchronized (GestorCitas.class) {
                if (gestor == null) {
                    gestor = new GestorCitas();
                }
            }
        }
        return gestor;
    }
    
    
    
    @Override
    public CitaDTO crearCita (CitaDTO cita) throws GestorException {
        
        try {
            ContratoDTO contrato = contratoBO.obtenerContratoFolio(cita.getContrato().getFolio());
            FotografoDTO fotografo = fotografoBO.obtenerFotografoNombreUsuario(cita.getFotografo().getNombreUsuario());
            
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

    @Override
    public List<FotografoDTO> obtenerFotografos() throws GestorException {
        try {
            return fotografoBO.obtenerTodosFotografos();
        } catch (ObjetosNegocioException ex) {
            throw new GestorException (ex.getMessage());
        }
    }

    @Override
    public List<CitaDTO> obtenerCitasContrato(ContratoDTO contrato) throws GestorException {
        try {
            return citaBO.obtenerCitasPorContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }

    @Override
    public List<ContratoDTO> obtenerContratos() throws GestorException {
        return contratoBO.obtenerTotalContratos();
    }

    @Override
    public boolean validarCodigo(String tabla, String codigo) throws GestorException {
        return false;
    }

    @Override
    public List<MaterialDTO> obtenerMateriales() throws GestorException {
        try {
            return materialBO.obtenerMateriales();
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }
}
