package org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;

/**
 *
 * @author haesp
 */
public class ConverterUtil {

    /**
     * Convierte una citaDTO a una cita entidad
     *
     * @param citaDTO cita que se desea convertir
     * @return cita convertida
     */
    public static Cita citaDTOAEntidad(CitaDTO citaDTO) {
        Cita cita = new Cita();

        if (citaDTO.getContrato() != null) {
            cita.setContrato(contratoDTOAEntidad(citaDTO.getContrato()));
        }
        if (citaDTO.getExtras() != null) {
            cita.setExtras(citaDTO.getExtras());
        }
        if (citaDTO.getFechaHoraFin() != null) {
            cita.setFechaHoraFin(citaDTO.getFechaHoraFin());
        }
        if (citaDTO.getFechaHoraInicio() != null) {
            cita.setFechaHoraInicio(citaDTO.getFechaHoraInicio());
        }
        if (citaDTO.getFotografo() != null) {
            cita.setFotografo(fotografoDTOAEntidad(citaDTO.getFotografo()));
        }
        if (citaDTO.getLugar() != null) {
            cita.setLugar(citaDTO.getLugar());
        }
        if (citaDTO.getCodigo() != null) {
            cita.setCodigo(citaDTO.getCodigo());
        }
        if (citaDTO.getId() != null) {
            cita.setId(citaDTO.getId());
        }
        if (citaDTO.getCitaMateriales() != null) {
            cita.setCitaMateriales(citaMaterialesDTOAEntidad(citaDTO.getCitaMateriales(), cita));
        }
        return cita;
    }

    /**
     * Convierte un materialDTO a una entidad Material.
     *
     * @param materialDTO material a convertir.
     * @return material convertido.
     */
    public static Material materialDTOAEntidad(MaterialDTO materialDTO) {
        Material material = new Material();
        material.setId(materialDTO.getId());
        material.setNombre(materialDTO.getNombre());
        material.setCantidad(materialDTO.getCantidad());
        return material;
    }

    /**
     * Convierte un material entidad a un materialDTO.
     *
     * @param material material a convertir.
     * @return materialDTO convertido.
     */
    public static MaterialDTO materialEntidadADTO(Material material) {
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setNombre(material.getNombre());
        materialDTO.setCantidad(material.getCantidad());
        return materialDTO;
    }

    /**
     * Convierte una lista de CitaMaterial entidad a una lista de
     * CitaMaterialDTO.
     *
     * @param citaMateriales Lista de CitaMaterial entidad.
     * @return Lista de CitaMaterialDTO.
     */
    public static List<CitaMaterialDTO> citaMaterialesEntidadADTO(List<CitaMaterial> citaMateriales) {
        List<CitaMaterialDTO> citaMaterialDTOs = new ArrayList<>();

        for (CitaMaterial citaMaterial : citaMateriales) {
            CitaMaterialDTO citaMaterialDTO = new CitaMaterialDTO();
            citaMaterialDTO.setId(citaMaterial.getId());
            citaMaterialDTO.setCantidad(citaMaterial.getCantidad());
            citaMaterialDTO.setMaterial(materialEntidadADTO(citaMaterial.getMaterial()));
            citaMaterialDTOs.add(citaMaterialDTO);
        }

        return citaMaterialDTOs;
    }

    /**
     * Convierte una lista de CitaMaterialDTO a una lista de CitaMaterial
     * entidad.
     *
     * @param citaMaterialDTOs Lista de CitaMaterialDTO.
     * @param cita Cita asociada.
     * @return Lista de CitaMaterial entidad.
     */
    public static List<CitaMaterial> citaMaterialesDTOAEntidad(List<CitaMaterialDTO> citaMaterialDTOs, Cita cita) {
        List<CitaMaterial> citaMateriales = new ArrayList<>();

        for (CitaMaterialDTO citaMaterialDTO : citaMaterialDTOs) {
            CitaMaterial citaMaterial = new CitaMaterial();
            citaMaterial.setCita(cita);
            citaMaterial.setCantidad(citaMaterialDTO.getCantidad());
            citaMaterial.setMaterial(materialDTOAEntidad(citaMaterialDTO.getMaterial()));
            citaMateriales.add(citaMaterial);
        }

        return citaMateriales;
    }

    /**
     * Convierte una cita entidad a una citaDTO
     *
     * @param cita cita que se desea convertir
     * @return cita convertida
     */
    public static CitaDTO citaEntidadADTO(Cita cita) {
        CitaDTO citaDTO = new CitaDTO();

        if (cita.getContrato() != null) {
            citaDTO.setContrato(contratoEntidadADTO(cita.getContrato()));
        }
        if (cita.getExtras() != null) {
            citaDTO.setExtras(cita.getExtras());
        }
        if (cita.getFechaHoraFin() != null) {
            citaDTO.setFechaHoraFin(cita.getFechaHoraFin());
        }
        if (cita.getFechaHoraInicio() != null) {
            citaDTO.setFechaHoraInicio(cita.getFechaHoraInicio());
        }
        if (cita.getFotografo() != null) {
            citaDTO.setFotografo(fotografoEntidadADTO(cita.getFotografo()));
        }
        if (cita.getLugar() != null) {
            citaDTO.setLugar(cita.getLugar());
        }
        if (cita.getCodigo() != null) {
            citaDTO.setCodigo(cita.getCodigo());
        }
        // if (cita.getMateriales() != null) {
        //     citaDTO.setMateriales(materialesEntidadADTO(cita.getMateriales()));
        // }

        return citaDTO;
    }

    /**
     * Convierte un contratoDTO a un contrato entidad
     *
     * @param contratoDTO contrato que se desea convertir
     * @return contrato convertido
     */
    public static Contrato contratoDTOAEntidad(ContratoDTO contratoDTO) {
        Contrato contrato = new Contrato();

        if (contratoDTO.getEstado() != null) {
            contrato.setEstado(contratoDTO.getEstado());
        }
        if (contratoDTO.getTematica() != null) {
            contrato.setTematica(contratoDTO.getTematica());
        }
        if (contratoDTO.getFolio() != null) {
            contrato.setFolio(contratoDTO.getFolio());
        }
        if (contratoDTO.getId() != null) {
            contrato.setId(contratoDTO.getId());
        }
        if (contratoDTO.getFechaInicio()!=null){
            contrato.setFechaInicio(contratoDTO.getFechaInicio());
        }
        if (contratoDTO.getFechaTermino()!=null){
            contrato.setFechaInicio(contratoDTO.getFechaTermino());
        }

        return contrato;
    }

    /**
     * Convierte un contrato entidad a un contratoDTO
     *
     * @param contrato contrato que se desea convertir
     * @return contrato convertido
     */
    public static ContratoDTO contratoEntidadADTO(Contrato contrato) {
        ContratoDTO contratoDTO = new ContratoDTO();

        if (contrato.getTematica() != null) {
            contratoDTO.setTematica(contrato.getTematica());
        }
        if (contrato.getFolio() != null) {
            contratoDTO.setFolio(contrato.getFolio());
        }
        if (contrato.getId() != null) {
            contratoDTO.setId(contrato.getId());
        }

        if (contrato.getEstado() != null) {
            contratoDTO.setEstado(contrato.getEstado());
        }
        if (contrato.getFechaInicio()!=null){
            contratoDTO.setFechaInicio(contrato.getFechaInicio());
        }
        if (contrato.getFechaTermino()!=null){
            contratoDTO.setFechaTermino(contrato.getFechaTermino());
        }

        if (contrato.getCliente() != null) {
            Cliente cliente = contrato.getCliente();
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNombre(cliente.getNombre());
            clienteDTO.setCorreo(cliente.getCorreo());
            clienteDTO.setTelefono(cliente.getTelefono());
            clienteDTO.setEstado(cliente.getEstado());
            contratoDTO.setCliente(clienteDTO);
        }

        if (contrato.getPaquete() != null) {
            Paquete paquete = contrato.getPaquete();
            PaqueteDTO paqueteDTO = new PaqueteDTO();
            paqueteDTO.setNombre(paquete.getNombre());
            paqueteDTO.setPrecio(paquete.getPrecio());
            contratoDTO.setPaquete(paqueteDTO);
        }

        return contratoDTO;
    }

    /**
     * Convierte una lista de citas a una lista de citasDTO
     *
     * @param citas lista de citas
     * @return lista de citas DTO
     */
    public static List<CitaDTO> citasEntidadADTO(List<Cita> citas) {

        List<CitaDTO> citasDTO = new ArrayList<>();
        citas.forEach(cita -> citasDTO.add(citaEntidadADTO(cita)));

        return citasDTO;
    }

    /**
     * Convierte una lista de citasDTO a una lista de citas entidad
     *
     * @param citasDTO lista de citas
     * @return lista de citas entidad
     */
    public static List<Cita> citasDTOAEntidad(List<CitaDTO> citasDTO) {

        List<Cita> citas = new ArrayList<>();
        citasDTO.forEach(cita -> citas.add(citaDTOAEntidad(cita)));

        return citas;
    }

    /**
     * Metodo para convertir un fotografo DTO a un fotografo entidad
     *
     * @param fotografoDTO fotografo a convertir
     * @return fotografo convertido
     */
    public static Fotografo fotografoDTOAEntidad(FotografoDTO fotografoDTO) {
        Fotografo fotografo = new Fotografo();

        fotografo.setNombrePersona(fotografoDTO.getNombrePersona());
        fotografo.setNombreUsuario(fotografoDTO.getNombreUsuario());
        fotografo.setId(fotografoDTO.getId());
        return fotografo;
    }

    /**
     * Metodo para convertir un fotografo entidad a un fotografo DTO
     *
     * @param fotografo fotografo a convertir
     * @return fotografo convertido
     */
    public static FotografoDTO fotografoEntidadADTO(Fotografo fotografo) {
        FotografoDTO fotografoDTO = new FotografoDTO();

        fotografoDTO.setNombrePersona(fotografo.getNombrePersona());
        fotografoDTO.setNombreUsuario(fotografo.getNombreUsuario());
        fotografoDTO.setId(fotografo.getId());
        return fotografoDTO;
    }

    /**
     * Metodo que convierte los materiales DTO a entidad
     *
     * @param materialesDTO Lista de materiales DTO
     * @return Lista de materiales entidad
     */
    public static List<Material> materialesDTOAEntidad(List<MaterialDTO> materialesDTO) {
        List<Material> materiales = new ArrayList<>();

        materialesDTO.forEach(materialDTO -> materiales.add(materialDTOAEntidad(materialDTO)));

        return materiales;
    }

    /**
     * Metodo que convierte los materiales entidad a DTO
     *
     * @param materiales Lista de materiales entidad
     * @return Lista de materiales DTO
     */
    public static List<MaterialDTO> materialesEntidadADTO(List<Material> materiales) {
        List<MaterialDTO> materialesDTO = new ArrayList<>();

        materiales.forEach(material -> materialesDTO.add(materialEntidadADTO(material)));

        return materialesDTO;
    }

    /**
     * Convierte una entidad Cliente a una DTO
     *
     * @param cliente Valor del cliente
     * @return regresa el clienteDTO
     */
    public static ClienteDTO clienteEntidadADTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        

        ClienteDTO clienteDTO = new ClienteDTO();
        
        if (cliente.getId() != null) {
            clienteDTO.setId(cliente.getId());
        }
        
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setEstado(cliente.getEstado());

        Set<ContratoDTO> contratosDTO = new HashSet<>();
        if (cliente.getContratos() != null) {
            for (Contrato contrato : cliente.getContratos()) {
                contratosDTO.add(contratoEntidadADTO(contrato));
            }
        }
        clienteDTO.setContratos(contratosDTO);

        return clienteDTO;
    }

    public static Cliente clienteDTOAEntidad(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        
        if (clienteDTO.getId() != null) {
            cliente.setId(clienteDTO.getId());
        }
        
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEstado(clienteDTO.getEstado());

        Set<Contrato> contratos = new HashSet<>();
        if (clienteDTO.getContratos() != null) {
            for (ContratoDTO contratoDTO : clienteDTO.getContratos()) {
                contratos.add(contratoDTOAEntidad(contratoDTO));
            }
        }
        cliente.setContratos(contratos);

        return cliente;
    }

    public static Paquete paqueteDTOAEntidad(PaqueteDTO paquete) {
        Paquete paqueteEntidad = new Paquete();

        if (paquete.getNombre() != null) {
            paqueteEntidad.setNombre(paquete.getNombre());

        }
        if (paquete.getPrecio() != null) {
            paqueteEntidad.setPrecio(paquete.getPrecio());

        }
        if (paquete.getId() != null) {
            paqueteEntidad.setId(paquete.getId());
        }

        return paqueteEntidad;

    }

    public static PaqueteDTO paqueteEntidadADTO(Paquete paquete) {
        PaqueteDTO paqueteDTO = new PaqueteDTO();

        if (paquete.getNombre() != null) {
            paqueteDTO.setNombre(paquete.getNombre());
        }

        if (paquete.getPrecio() != null) {
            paqueteDTO.setPrecio(paquete.getPrecio());
        }

        if (paquete.getId() != null) {
            paqueteDTO.setId(paquete.getId());
        }

        return paqueteDTO;
    }

}
