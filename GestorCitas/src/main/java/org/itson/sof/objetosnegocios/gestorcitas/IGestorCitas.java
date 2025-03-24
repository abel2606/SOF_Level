/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public interface IGestorCitas {
 
    /**
     * Método para crear una cita a un contrato y asignar su fotografo
     * @param cita cita a crear
     * @param folioContrato folio del contrato
     * @param nombreUsuarioFotografo nombre de usuario del fotografo
     * @return cita creada
     * @throws GestorException 
     */
    public CitaDTO crearCita (CitaDTO cita) throws GestorException;
    
    /**
     * Método para consultar ua cita
     * @param cita cita a consultar
     * @return cita consultada
     */
    public CitaDTO consultarCita (CitaDTO cita);
    
    /**
     * Metodo para actualizar una cita
     * @param cita cita a actualizar
     * @return cita actualizada
     * @throws GestorException 
     */
    public CitaDTO actualizarCita (CitaDTO cita) throws GestorException;
    
    /**
     * Método para eliminar una cita
     * @param cita cita a eliminar
     * @return cita eliminada
     * @throws GestorException 
     */
    public CitaDTO eliminarCita (CitaDTO cita) throws GestorException;
    
    /**
     * Método para obtener los fotografos registrados
     * @return lista de fotografos
     * @throws GestorException
     * 
     */
    public List<FotografoDTO> obtenerFotografos()throws GestorException;
    
    /**
     * Obtiene todas las citas de un contrato
     * @param contrato contrato del qyue se dsean obtener las citas
     * @return lsita de citas
     * @throws GestorException 
     */
    public List<CitaDTO> obtenerCitasContrato (ContratoDTO contrato) throws GestorException;
    
    /**
     * Obtiene todos los contratos tegistrados
     * @return lista de los contratos
     * @throws GestorException 
     */
    public List<ContratoDTO> obtenerContratos () throws GestorException;

    /**
     * Obtiene todos los contratos tegistrados
     * @return lista de los contratos
     * @throws GestorException 
     */
    public List<MaterialDTO> obtenerMateriales () throws GestorException;

    /**
     * Valida si el código de una cita o contrato ya existe
     * @param tabla tabla que se consultará
     * @param codigo codigo a consultar
     * @return true si ya existe
     * @throws GestorException 
     */
    public boolean validarCodigo(String tabla, String codigo) throws GestorException;
}
