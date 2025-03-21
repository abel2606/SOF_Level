package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class ContratoBO implements IContratoBO{
    
    IContratosDAO contratosDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public ContratoBO() {
        IConexion conexion = new Conexion();
        this.contratosDAO = new ContratosDAO(conexion);
    }
    
   
    @Override
    public ContratoDTO crearContrato(ContratoDTO contrato, ClienteDTO cliente) throws ObjetosNegocioException {
        System.out.print("No implementado aun");
        return null;
    }

    @Override
    public ContratoDTO actualizarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        System.out.print("No implementado aun");
        return null;
    }

    @Override
    public boolean eliminarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        System.out.print("No implementado aun");
        return false;
    }

    @Override
    public ContratoDTO obtenerContratoFolio(String folio) throws ObjetosNegocioException {
        
        try {
            return ConverterUtil.contratoEntidadADTO(contratosDAO.obtenerContratoFolio(folio));
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException (ex.getMessage());
        }     
    }

    @Override
    public List<ContratoDTO> obtenerTotalContratos() {
        try {
            // Obtienes las entidades de contratos desde el DAO
            List<Contrato> contratos = this.contratosDAO.obtenerTotalContratos();

            // Si la lista no está vacía, conviértela
            if (contratos != null && !contratos.isEmpty()) {
                List<ContratoDTO> contratosDTO = contratos.stream()
                        .map(entidad -> ConverterUtil.contratoEntidadADTO(entidad)) // Conversión de entidad a DTO
                        .collect(Collectors.toList());
                return contratosDTO;
            } else {
                System.out.println("No se encontraron contratos.");
                return new ArrayList<>();
            }

        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(ContratoBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
