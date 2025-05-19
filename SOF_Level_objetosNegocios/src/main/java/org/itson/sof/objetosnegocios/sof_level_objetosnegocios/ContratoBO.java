package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.DiferenciadorUtils;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class ContratoBO implements IContratoBO {

    IContratosDAO contratosDAO;
    private static final Logger LOG = Logger.getLogger(ContratoBO.class.getName());

    public ContratoBO() {
        IConexion conexion = new Conexion();
        this.contratosDAO = new ContratosDAO(conexion);
    }

    @Override
    public ContratoDTO crearContrato(ContratoDTO contrato, ClienteDTO cliente, PaqueteDTO paquete) throws ObjetosNegocioException {

        String folioGenerado;
        do {

            folioGenerado = DiferenciadorUtils.generarCodigo();

        } while (existeFolio(folioGenerado));

        try {
            contrato.setFolio(folioGenerado);
            
            Contrato contratoCreado = contratosDAO.crearContrato(ConverterUtil.contratoDTOAEntidad(contrato),
                    ConverterUtil.clienteDTOAEntidad(cliente),
                    ConverterUtil.paqueteDTOAEntidad(paquete));

            return ConverterUtil.contratoEntidadADTO(contratoCreado);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }
    
    
    private boolean existeFolio(String folio) throws ObjetosNegocioException {
        try {
            contratosDAO.obtenerContratoFolio(folio);
            return true;
        } catch (PersistenciaSOFException ex) {
            if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("no se encontro el folio")) {
                return false; 
            }
            return false;
        }
    }

    @Override
    public ContratoDTO actualizarContrato(ContratoDTO contrato) throws ObjetosNegocioException {

        try {

            Contrato contratoCreado = contratosDAO.actualizarContrato(ConverterUtil.contratoDTOAEntidad(contrato));
            return ConverterUtil.contratoEntidadADTO(contratoCreado);

        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    @Override
    public ContratoDTO cancelarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        try {

            Contrato contratoCancelado = contratosDAO.cancelarContrato(ConverterUtil.contratoDTOAEntidad(contrato));
            return ConverterUtil.contratoEntidadADTO(contratoCancelado);

        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public ContratoDTO terminarContrato(ContratoDTO contrato) throws ObjetosNegocioException {
        try {

            Contrato contratoCancelado = contratosDAO.terminarContrato(ConverterUtil.contratoDTOAEntidad(contrato));
            return ConverterUtil.contratoEntidadADTO(contratoCancelado);

        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public ContratoDTO obtenerContratoFolio(String folio) throws ObjetosNegocioException {

        try {
            return ConverterUtil.contratoEntidadADTO(contratosDAO.obtenerContratoFolio(folio));
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<ContratoDTO> obtenerTotalContratos() throws ObjetosNegocioException {
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
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<ContratoDTO> obtenerContratosPorCliente(ClienteDTO clienteDTO) throws ObjetosNegocioException {
        try {
            Cliente cliente = ConverterUtil.clienteDTOAEntidad(clienteDTO);
            // Obtienes las entidades de contratos desde el DAO
            List<Contrato> contratos = this.contratosDAO.obtenerContratosPorCliente(cliente);

            // Si la lista no está vacía, conviértela
            if (contratos != null && !contratos.isEmpty()) {
                List<ContratoDTO> contratosDTO = contratos.stream()
                        .map(entidad -> ConverterUtil.contratoEntidadADTO(entidad)) // Conversión de entidad a DTO
                        .collect(Collectors.toList());
                return contratosDTO;
            } else {
                System.out.println("No se encontraron contratos del cliente.");
                return new ArrayList<>();
            }

        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(ContratoBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }
}
