/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.ConverterUtil;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.CitasDAO;
import org.itson.sof.persistencia.daos.ICitasDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class CitaBO implements ICitaBO {

    private ICitasDAO citasDAO;

    public CitaBO() {
        IConexion conexion = new Conexion();
        this.citasDAO = new CitasDAO(conexion);
    }

    /**
     * Método crear y asignar una cita a un contrato
     *
     * @param citaDTO cita que se desea crear
     * @return cita en caso que se cree
     * @throws ObjetosNegocioException en caso de un error al crear la cita
     */
    @Override
    public CitaDTO crearCita(CitaDTO citaDTO) throws ObjetosNegocioException {

        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            if (!citasDAO.obtenerCitasFecha(cita).isEmpty()) {
                throw new ObjetosNegocioException("Se han encontrado citas que interfieren con esta, cambie la fecha");
            }

            cita = citasDAO.agregarCita(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    /**
     * Método para actulizar una cita ya creada
     *
     * @param citaDTO cita que se desea actualizar
     * @return cita en caso de actualizarla
     * @throws ObjetosNegocioException en caso de un error al actuaizar la cita
     */
    @Override
    public CitaDTO actualizarCita(CitaDTO citaDTO) throws ObjetosNegocioException {
        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            if (!citasDAO.obtenerCitasFecha(cita).isEmpty()) {
                throw new ObjetosNegocioException("Se han encontrado citas que interfieren con esta, cambie la fecha");
            }

            cita = citasDAO.actualizarCita(cita);
            citaDTO = ConverterUtil.citaEntidadADTO(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    /**
     * Método para eliminar la una cita.
     *
     * @param citaDTO cita que se desea eliminar
     * @return la cita en caso de eliminarse
     * @throws ObjetosNegocioException
     */
    @Override
    public CitaDTO eliminarCita(CitaDTO citaDTO) throws ObjetosNegocioException {

        Cita cita = ConverterUtil.citaDTOAEntidad(citaDTO);

        try {
            cita = citasDAO.eliminarcita(cita);
            citaDTO = ConverterUtil.citaEntidadADTO(cita);
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }

    }

    /**
     * Metodo para consultar una cita
     *
     * @param citaDTO cita que se desea consultar
     * @return cita consultada
     */
    @Override
    public CitaDTO obtenerCita(CitaDTO citaDTO) throws ObjetosNegocioException {
        Cita cita = new Cita();
        cita.setCodigo(citaDTO.getCodigo());

        try {
            citaDTO = ConverterUtil.citaEntidadADTO(citasDAO.obtenerCita(cita));
            return citaDTO;
        } catch (Exception ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<CitaDTO> obtenerCitasPorContrato(ContratoDTO contratoDTO) throws ObjetosNegocioException {
        try {
            // Obtener la lista de Citas desde el DAO
            List<Cita> citas = citasDAO.obtenerCitasContratos(contratoDTO.getId());

            // Convertir cada Cita a CitaDTO
            List<CitaDTO> citasDTO = new ArrayList<>();
            for (Cita cita : citas) {
                citasDTO.add(ConverterUtil.citaEntidadADTO(cita));
            }

            return citasDTO;
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException(ex.getMessage());
        }
    }

    @Override
    public List<String> obtenerHorariosDisponibles(String fechaInicio) throws ObjetosNegocioException {
        List<Cita> citasOcupadas;
        try {
            // Se obtiene la lista de citas ocupadas en la fecha proporcionada
            citasOcupadas = citasDAO.obtenerCitasPorFecha(fechaInicio);
        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(CitaBO.class.getName()).log(Level.SEVERE, "Error al obtener citas", ex);
            throw new ObjetosNegocioException("No se pudieron obtener las citas");
        }

        List<String> horariosDisponibles = new ArrayList<>();
        LocalTime inicio = LocalTime.of(2, 0); // Hora de inicio del horario disponible
        LocalTime fin = LocalTime.of(23, 0); // Hora de fin del horario disponible
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<LocalTime> todosLosHorarios = new ArrayList<>();
        // Se generan los horarios cada 30 minutos dentro del rango establecido
        while (inicio.isBefore(fin)) {
            todosLosHorarios.add(inicio);
            inicio = inicio.plusMinutes(30);
        }

        // Obtener la última hora de fin de todas las citas
        LocalTime ultimaHoraFin = citasOcupadas.stream()
                .map(c -> c.getFechaHoraFin().toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
                .max(LocalTime::compareTo)
                .orElse(LocalTime.MIN);
        LocalTime inicioHorariosDespues = ultimaHoraFin.plusMinutes(30);

        // Evaluamos cada horario generado
        for (LocalTime horaActual : todosLosHorarios) {
            boolean ocupado = false;
            boolean permitido = true;

            for (Cita cita : citasOcupadas) {
                LocalTime inicioCita = cita.getFechaHoraInicio().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();
                LocalTime finCita = cita.getFechaHoraFin().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();

                // 1. No permitir horarios dentro de una cita ni exactamente en su fin
                if ((!horaActual.isBefore(inicioCita) && horaActual.isBefore(finCita)) || horaActual.equals(finCita)) {
                    ocupado = true;
                    break;
                }

                // 2. Permitir horarios al menos 1 hora antes del inicio de la cita
                if (horaActual.plusHours(1).isAfter(inicioCita) && horaActual.isBefore(inicioCita)) {
                    permitido = false;
                }
            }

            // 3. Permitir solo los horarios estrictamente después de 30 minutos del fin de la última cita
            if (horaActual.isAfter(inicioHorariosDespues)) {
                permitido = true;
            }

            // Agregar solo los horarios que cumplan las reglas
            if (!ocupado && permitido) {
                horariosDisponibles.add(horaActual.format(formatter));
            }
        }

        return horariosDisponibles;
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(String fechaInicio, String horaInicioSeleccionada, CitaDTO citaDTO) throws ObjetosNegocioException {
        List<String> horariosDisponiblesFin = new ArrayList<>();
        LocalTime horaInicio = LocalTime.parse(horaInicioSeleccionada);
        LocalTime fin = LocalTime.of(23, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime horaFinMinima = horaInicio.plusMinutes(30);

        List<LocalTime> todosLosHorarios = new ArrayList<>();
        while (horaFinMinima.isBefore(fin)) {
            todosLosHorarios.add(horaFinMinima);
            horaFinMinima = horaFinMinima.plusMinutes(30);
        }

        List<Cita> citasOcupadas;
        try {
            citasOcupadas = citasDAO.obtenerCitasPorFecha(fechaInicio);
        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(CitaBO.class.getName()).log(Level.SEVERE, "Error al obtener citas", ex);
            throw new ObjetosNegocioException("No se pudieron obtener las citas");
        }

        if (citaDTO != null) {
            Cita citaARemover = ConverterUtil.citaDTOAEntidad(citaDTO);
            citasOcupadas.remove(citaARemover);
        }

        for (LocalTime horaActual : todosLosHorarios) {
            for (Cita cita : citasOcupadas) {
                LocalTime inicioCita = cita.getFechaHoraInicio().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();
                LocalTime finCita = cita.getFechaHoraFin().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();

                if (!horaActual.isBefore(inicioCita) && horaActual.isBefore(finCita)) {
                    return horariosDisponiblesFin; // Detener búsqueda al encontrar un horario ocupado
                }
            }
            horariosDisponiblesFin.add(horaActual.format(formatter));
        }

        return horariosDisponiblesFin;
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(List<String> horariosInicio, String fechaInicio) throws ObjetosNegocioException {
        List<String> horariosDisponiblesFin = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<LocalTime> todosLosHorariosFin = new ArrayList<>();
        // Convertir las horas de inicio seleccionadas a LocalTime y calcular los horarios de fin válidos
        for (String horaInicioStr : horariosInicio) {
            LocalTime horaInicio = LocalTime.parse(horaInicioStr);

            LocalTime horaFinMinima = horaInicio.plusMinutes(30);
            LocalTime fin = LocalTime.of(23, 0);

            while (horaFinMinima.isBefore(fin)) {
                todosLosHorariosFin.add(horaFinMinima);
                horaFinMinima = horaFinMinima.plusMinutes(30);
            }
        }

        List<Cita> citasOcupadas;
        try {
            citasOcupadas = citasDAO.obtenerCitasPorFecha(fechaInicio);
        } catch (PersistenciaSOFException ex) {
            Logger.getLogger(CitaBO.class.getName()).log(Level.SEVERE, "Error al obtener citas", ex);
            throw new ObjetosNegocioException("No se pudieron obtener las citas");
        }

        for (LocalTime horaActual : todosLosHorariosFin) {
            boolean ocupado = false;

            for (Cita cita : citasOcupadas) {
                LocalTime inicioCita = cita.getFechaHoraInicio().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();
                LocalTime finCita = cita.getFechaHoraFin().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalTime();

                if (!horaActual.isBefore(inicioCita) && horaActual.isBefore(finCita)) {
                    ocupado = true;
                }
            }

            if (!ocupado) {
                horariosDisponiblesFin.add(horaActual.format(formatter));
                break;
            }
        }

        return horariosDisponiblesFin;
    }

    
    @Override
    public boolean eliminarCitasContrato(String folio) throws ObjetosNegocioException {
        try {
            return citasDAO.eliminarCitasContrato(folio);
        } catch (PersistenciaSOFException ex) {
            throw new ObjetosNegocioException (ex.getMessage());
        }
    }

}
