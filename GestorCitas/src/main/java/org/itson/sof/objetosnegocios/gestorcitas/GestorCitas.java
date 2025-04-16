package org.itson.sof.objetosnegocios.gestorcitas;

import java.util.List;
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
    public CitaDTO crearCita(CitaDTO cita) throws GestorException {

        try {
            ContratoDTO contrato = contratoBO.obtenerContratoFolio(cita.getContrato().getFolio());
            FotografoDTO fotografo = fotografoBO.obtenerFotografoNombreUsuario(cita.getFotografo().getNombreUsuario());

            cita.setContrato(contrato);
            cita.setFotografo(fotografo);

            return citaBO.crearCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
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
            throw new GestorException(ex.getMessage());
        }

    }

    @Override
    public CitaDTO eliminarCita(CitaDTO cita) throws GestorException {

        try {
            return citaBO.eliminarCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }

    }

    @Override
    public List<FotografoDTO> obtenerFotografos() throws GestorException {
        try {
            return fotografoBO.obtenerTodosFotografos();
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
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

    @Override
    public List<CitaMaterialDTO> obtenerMaterialesCita(CitaDTO cita) throws GestorException {
        try {
            return materialBO.obtenerMaterialesCita(cita);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }

    @Override
    public ContratoDTO obtenerContrato(String folio) throws GestorException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> obtenerHorariosDisponibles(String fechaInicio) throws GestorException {
        try {
            return citaBO.obtenerHorariosDisponibles(fechaInicio);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(List<String> horariosInicio, String horaInicioSeleccionada) throws GestorException {
        try {
            System.out.println("Horario inicio: " + horariosInicio);
            System.out.println("Hora inicio seleccionada: " + horaInicioSeleccionada);
            return citaBO.obtenerHorariosDisponiblesFin(horariosInicio, horaInicioSeleccionada);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }

    @Override
    public List<String> obtenerHorariosDisponiblesFin(String fechaInicio, String horaInicioSeleccionada) throws GestorException {

        try {
            System.out.println("Fecha inicio: " + fechaInicio);
            System.out.println("Hora inicio seleccionada: " + horaInicioSeleccionada);
            return citaBO.obtenerHorariosDisponiblesFin(fechaInicio, horaInicioSeleccionada);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException(ex.getMessage());
        }
    }

    @Override
    public void actualizarStockMaterial(String nombreMaterial, float nuevoStock) throws GestorException {
        try {
            MaterialDTO material = materialBO.obtenerMaterialPorNombre(nombreMaterial);
            if (material == null) {
                throw new GestorException("Material no encontrado: " + nombreMaterial);
            }
            material.setCantidad(nuevoStock);
            materialBO.actualizarMaterial(material);
        } catch (ObjetosNegocioException ex) {
            throw new GestorException("Error al actualizar stock: " + ex.getMessage());
        }
    }

}
