package org.itson.sof.objetosnegocios.gestorcostos;

import java.util.GregorianCalendar;
import java.util.List;
import org.itson.sof.objetosnegocios.gestorcostos.gestorexception.GestorCostosException;
import org.itson.sof.sof_dtos.ContratoDTO;
import reportes.ReporteVenta;



/**
 *
 * @author haesp
 */
public interface IGestorCostos {
    List<ReporteVenta> obtenerReportesVenta(String ruta) throws GestorCostosException;
    
    public void abrirReporte(String ruta) throws GestorCostosException;
    
    public void generarReporte (GregorianCalendar fechaInicio,GregorianCalendar fechaFinal, ReporteVenta reporte, List<ContratoDTO> contratos) throws GestorCostosException;

}
