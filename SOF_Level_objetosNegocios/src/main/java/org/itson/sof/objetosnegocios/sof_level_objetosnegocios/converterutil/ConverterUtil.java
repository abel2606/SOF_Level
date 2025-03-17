package org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil;

import java.util.ArrayList;
import java.util.List;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

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
        cita.setContrato(contratoDTOAEntidad(citaDTO.getContrato()));
        cita.setExtras(citaDTO.getExtras());
        cita.setFechaHoraFin(citaDTO.getFechaHoraFin());
        cita.setFechaHoraInicio(citaDTO.getFechaHoraInicio());
        cita.setFotografo(fotografoDTOAEntidad(citaDTO.getFotografo()));
        cita.setLugar(citaDTO.getLugar());
        //cita.setMateriales(materialesDTOAEntidad(citaDTO.getMateriales()));
        return cita;
    }

    /**
     * Convierte una citaD entidad a una citaDTO
     *
     * @param cita cita que se desea convertir
     * @return cita convertida
     */
    public static CitaDTO citaEntidadADTO(Cita cita) {

        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setContrato(contratoEntidadADTO(cita.getContrato()));
        citaDTO.setExtras(cita.getExtras());
        citaDTO.setFechaHoraFin(cita.getFechaHoraFin());
        citaDTO.setFechaHoraInicio(cita.getFechaHoraInicio());
        citaDTO.setFotografo(fotografoEntidadADTO(cita.getFotografo()));
        citaDTO.setLugar(cita.getLugar());
        //citaDTO.setMateriales(materialesEntidadADTO(cita.getMateriales()));

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
        //contrato.setCitas(citasDTOAEntidad(contratoDTO.getCitas()));
        //contrato.setCliente(contratoDTO.getCliente());
        //contrato.setCompras(contratoDTO.getCompras());
//        contrato.setEstado(contratoDTO.getEstado());
//        //contrato.setPaquete(contratoDTO.getPaquete());
//        contrato.setTematica(contratoDTO.getTematica());
        contrato.setFolio(contratoDTO.getFolio());

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
        //contratoDTO.setCitas(citasEntidadADTO(contrato.getCitas()));
        //contratoDTO.setCliente(contrato.getCliente());
        //contratoDTO.setCompras(contrato.getCompras());
//        contratoDTO.setEstado(contrato.getEstado());
//        //contratoDTO.setPaquete(contrato.getPaquete());
//        contratoDTO.setTematica(contrato.getTematica());
        contratoDTO.setFolio(contrato.getFolio());

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

//        fotografo.setNombrePersona(fotografoDTO.getNombrePersona());
        fotografo.setNombreUsuario(fotografoDTO.getNombreUsuario());
        //FALTA DISCUTIR COSAS CON EL EQUIPO ACERCA PARA PODER TERMINAR EL METODO
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
        //FALTA DISCUTIR COSAS CON EL EQUIPO ACERCA PARA PODER TERMINAR EL METODO
        return fotografoDTO;
    }

    /**
     * Metodo que convierte un material DTO a un material entidad
     *
     * @param materialDTO material a convertir
     * @return material convertido
     */
    public static Material materialDTOAEntidad(MaterialDTO materialDTO) {

        Material material = new Material();

        material.setCantidad(materialDTO.getCantidad());
        material.setNombre(materialDTO.getNombre());

        return material;

    }

    /**
     * Metodo que convierte un material entidad a un material DTO
     *
     * @param material material a convertir
     * @return material convertido
     */
    public static MaterialDTO materialEntidadADTO(Material material) {

        MaterialDTO materialDTO = new MaterialDTO();

        materialDTO.setCantidad(material.getCantidad());
        materialDTO.setNombre(material.getNombre());

        return materialDTO;

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
    
    

}
