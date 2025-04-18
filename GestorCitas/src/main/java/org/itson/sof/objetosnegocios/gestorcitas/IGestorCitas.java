/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
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
     *
     * @param cita cita a crear
     * @param folioContrato folio del contrato
     * @param nombreUsuarioFotografo nombre de usuario del fotografo
     * @return cita creada
     * @throws GestorException
     */
    public CitaDTO crearCita(CitaDTO cita) throws GestorException;

    /**
     * Método para consultar ua cita
     *
     * @param cita cita a consultar
     * @return cita consultada
     */
    public CitaDTO consultarCita(CitaDTO cita);

    /**
     * Metodo para actualizar una cita
     *
     * @param cita cita a actualizar
     * @return cita actualizada
     * @throws GestorException
     */
    public CitaDTO actualizarCita(CitaDTO cita) throws GestorException;

    /**
     * Método para eliminar una cita
     *
     * @param cita cita a eliminar
     * @return cita eliminada
     * @throws GestorException
     */
    public CitaDTO eliminarCita(CitaDTO cita) throws GestorException;

    /**
     * Método para obtener los fotografos registrados
     *
     * @return lista de fotografos
     * @throws GestorException
     *
     */
    public List<FotografoDTO> obtenerFotografos() throws GestorException;

    /**
     * Obtiene todas las citas de un contrato
     *
     * @param contrato contrato del qyue se dsean obtener las citas
     * @return lsita de citas
     * @throws GestorException
     */
    public List<CitaDTO> obtenerCitasContrato(ContratoDTO contrato) throws GestorException;

    /**
     * Obtiene todos los contratos tegistrados
     *
     * @return lista de los contratos
     * @throws GestorException
     */
    public List<ContratoDTO> obtenerContratos() throws GestorException;

    /**
     * Obtiene todos los contratos tegistrados
     *
     * @return lista de los contratos
     * @throws GestorException
     */
    public List<MaterialDTO> obtenerMateriales() throws GestorException;

    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws GestorException;

    /**
     * Valida si el código de una cita o contrato ya existe
     *
     * @param tabla tabla que se consultará
     * @param codigo codigo a consultar
     * @return true si ya existe
     * @throws GestorException
     */
    public boolean validarCodigo(String tabla, String codigo) throws GestorException;

    /**
     * Método que obitene un contrato a través de su folio
     *
     * @param folio folio del contrato
     * @return contrato obtenido
     * @throws GestorException
     */
    public ContratoDTO obtenerContrato(String folio) throws GestorException;

    /**
     * Obtiene los horarios disponibles del día seleccioando
     *
     * @param fechaInicio fecha de inicio selecciondada para la cita
     * @return regresa la lista de los horarios disponibles
     * @throws GestorException en caso de suceder un error
     */
    public List<String> obtenerHorariosDisponibles(String fechaInicio) throws GestorException;

    /**
     * Obtiene una lista de los horaios disponibles para la fecha fin de la cita
     *
     * @param fechaInicio fecha en la que comienza la cita en
     * formato(YYYY-MM-DD)
     * @param horaInicioSeleccionada
     * @return
     * @throws GestorException
     */
    public List<String> obtenerHorariosDisponiblesFin(List<String> horariosInicio, String horaInicioSeleccionada) throws GestorException;

    public List<String> obtenerHorariosDisponiblesFin(String fechaInicio, String horaInicioSeleccionada) throws GestorException;
    
    public void actualizarStockMaterial(String nombreMaterial, float nuevoStock) throws GestorException;
}
