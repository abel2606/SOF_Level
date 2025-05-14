package org.itson.sof.objetosnegocios.gestorcostos;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcostos.gestorexception.GestorCostosException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CostosBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ICostosBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ContratoDTO;
import reportes.ReporteVenta;

/**
 *
 * @author haesp
 */
public class GestorCostos implements IGestorCostos {

    private static GestorCostos gestor;
    private final ICostosBO costosBO;

    private GestorCostos() {
        this.costosBO = new CostosBO();
    }

    public static GestorCostos getInstance() {
        if (gestor == null) {
            synchronized (GestorCostos.class) {
                if (gestor == null) {
                    gestor = new GestorCostos();
                }
            }
        }
        return gestor;
    }

    @Override
    public List<ReporteVenta> obtenerReportesVenta(String ruta) throws GestorCostosException {
        try {
            costosBO.obtenerReportesVenta(ruta);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCostosException(ex.getMessage());
        }
        return null;
    }
    
    @Override
    public void abrirReporte(String ruta) throws GestorCostosException {
        try {
            costosBO.abrirReporte(ruta);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCostosException(ex.getMessage());
        }
    }

    @Override
    public void generarReporte(GregorianCalendar fechaInicio, GregorianCalendar fechaFinal, ReporteVenta reporte, List<ContratoDTO> contratos) throws GestorCostosException {
        try {
            costosBO.generarReporte(fechaInicio, fechaFinal, reporte, contratos);
        } catch (ObjetosNegocioException ex) {
            throw new GestorCostosException(ex.getMessage());
        }
    }
    
}
