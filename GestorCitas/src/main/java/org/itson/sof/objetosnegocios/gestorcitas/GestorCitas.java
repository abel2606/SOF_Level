package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaMaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICitaMaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IContratoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IFotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.IMaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.MaterialBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.CitaMaterialDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class GestorCitas implements IGestorCitas {

    private static GestorCitas gestor;
    private final ICitaBO citaBO;
    private final IContratoBO contratoBO;
    private final IMaterialBO materialBO;
    private final IFotografoBO fotografoBO;
    private final ICitaMaterialBO citaMaterialBO;

    private GestorCitas() {
        this.citaBO = new CitaBO();
        this.contratoBO = new ContratoBO();
        this.materialBO = new MaterialBO();
        this.fotografoBO = new FotografoBO();
        this.citaMaterialBO = new CitaMaterialBO();
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
    public CitaDTO crearCita(CitaDTO cita) throws GestorCitasException {

        try {
            ContratoDTO contrato = contratoBO.obtenerContratoFolio(cita.getContrato().getFolio());
            FotografoDTO fotografo = fotografoBO.obtenerFotografoNombreUsuario(cita.getFotografo().getNombreUsuario());

            cita.setContrato(contrato);
            cita.setFotografo(fotografo);

            return citaBO.crearCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
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
    public CitaDTO actualizarCita(CitaDTO cita) throws GestorCitasException {

        try {
            return citaBO.actualizarCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }

    }

    @Override
    public CitaDTO eliminarCita(CitaDTO cita) throws GestorCitasException {

        try {
            return citaBO.eliminarCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }

    }

    @Override
    public List<FotografoDTO> obtenerFotografos() throws GestorCitasException {
        try {
            return fotografoBO.obtenerTodosFotografos();
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public List<CitaDTO> obtenerCitasContrato(ContratoDTO contrato) throws GestorCitasException {
        try {
            return citaBO.obtenerCitasPorContrato(contrato);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public List<ContratoDTO> obtenerContratos() throws GestorCitasException {
        try {
            return contratoBO.obtenerTotalContratos();
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public boolean validarCodigo(String tabla, String codigo) throws GestorCitasException {
        return false;
    }

    @Override
    public List<MaterialDTO> obtenerMateriales() throws GestorCitasException {
        try {
            return materialBO.obtenerMateriales();
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws GestorCitasException {
        try {
            return materialBO.obtenerMaterialesCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public ContratoDTO obtenerContrato(String folio) throws GestorCitasException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> obtenerHorariosDisponibles(String fechaInicio) throws GestorCitasException {
        try {
            return citaBO.obtenerHorariosDisponibles(fechaInicio);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(List<String> horariosInicio, String horaInicioSeleccionada) throws GestorCitasException {
        try {
            return citaBO.obtenerHorariosDisponiblesFin(horariosInicio, horaInicioSeleccionada);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(String fechaInicio, String horaInicioSeleccionada, CitaDTO cita) throws GestorCitasException {

        try {
            return citaBO.obtenerHorariosDisponiblesFin(fechaInicio, horaInicioSeleccionada, cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException(ex.getMessage());
        }
    }

    @Override
    public void actualizarStockMaterial(String nombreMaterial, float nuevoStock) throws GestorCitasException {
        try {
            MaterialDTO material = materialBO.obtenerMaterialPorNombre(nombreMaterial);
            if (material == null) {
                throw new GestorCitasException("Material no encontrado: " + nombreMaterial);
            }
            material.setCantidad(nuevoStock);
            materialBO.actualizarMaterial(material);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCitasException("Error al actualizar stock: " + ex.getMessage());
        }
    }

}
