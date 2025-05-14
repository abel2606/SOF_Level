/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.ClienteDTO;
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
     * @throws GestorCitasException
     */
    public CitaDTO crearCita(CitaDTO cita) throws GestorCitasException;

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
     * @throws GestorCitasException
     */
    public CitaDTO actualizarCita(CitaDTO cita) throws GestorCitasException;

    /**
     * Método para eliminar una cita
     *
     * @param cita cita a eliminar
     * @return cita eliminada
     * @throws GestorCitasException
     */
    public CitaDTO eliminarCita(CitaDTO cita) throws GestorCitasException;

    /**
     * Método para obtener los fotografos registrados
     *
     * @return lista de fotografos
     * @throws GestorCitasException
     *
     */
    public List<FotografoDTO> obtenerFotografos() throws GestorCitasException;

    /**
     * Obtiene todas las citas de un contrato
     *
     * @param contrato contrato del qyue se dsean obtener las citas
     * @return lsita de citas
     * @throws GestorCitasException
     */
    public List<CitaDTO> obtenerCitasContrato(ContratoDTO contrato) throws GestorCitasException;

    /**
     * Obtiene todos los contratos tegistrados
     *
     * @return lista de los contratos
     * @throws GestorCitasException
     */
    public List<ContratoDTO> obtenerContratos() throws GestorCitasException;
    
    public List<ContratoDTO> obtenerContratosPorCliente(ClienteDTO clienteDTO) throws GestorCitasException;

    /**
     * Obtiene todos los contratos tegistrados
     *
     * @return lista de los contratos
     * @throws GestorCitasException
     */
    public List<MaterialDTO> obtenerMateriales() throws GestorCitasException;

    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws GestorCitasException;

    /**
     * Valida si el código de una cita o contrato ya existe
     *
     * @param tabla tabla que se consultará
     * @param codigo codigo a consultar
     * @return true si ya existe
     * @throws GestorCitasException
     */
    public boolean validarCodigo(String tabla, String codigo) throws GestorCitasException;

    /**
     * Método que obitene un contrato a través de su folio
     *
     * @param folio folio del contrato
     * @return contrato obtenido
     * @throws GestorCitasException
     */
    public ContratoDTO obtenerContrato(String folio) throws GestorCitasException;

    /**
     * Obtiene los horarios disponibles del día seleccioando
     *
     * @param fechaInicio fecha de inicio selecciondada para la cita
     * @return regresa la lista de los horarios disponibles
     * @throws GestorCitasException en caso de suceder un error
     */
    public List<String> obtenerHorariosDisponibles(String fechaInicio) throws GestorCitasException;

    /**
     * Obtiene una lista de los horaios disponibles para la fecha fin de la cita
     *
     * @param fechaInicio fecha en la que comienza la cita en
     * formato(YYYY-MM-DD)
     * @param horaInicioSeleccionada
     * @return
     * @throws GestorCitasException
     */
    public List<String> obtenerHorariosDisponiblesFin(List<String> horariosInicio, String horaInicioSeleccionada) throws GestorCitasException;

    public List<String> obtenerHorariosDisponiblesFin(String fechaInicio, String horaInicioSeleccionada, CitaDTO cita) throws GestorCitasException;
    
    public void actualizarStockMaterial(String nombreMaterial, float nuevoStock) throws GestorCitasException;
}
