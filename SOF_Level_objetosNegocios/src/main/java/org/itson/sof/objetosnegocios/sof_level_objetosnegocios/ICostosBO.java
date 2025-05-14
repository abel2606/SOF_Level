
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.GregorianCalendar;
import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ContratoDTO;
import reportes.ReporteVenta;

/**
 *
 * @author JazmE
 */
public interface ICostosBO {
    
    public List<ReporteVenta> obtenerReportesVenta(String ruta) throws ObjetosNegocioException;
    
    public void abrirReporte (String ruta) throws ObjetosNegocioException;
    
    public void generarReporte (GregorianCalendar fechaInicio,GregorianCalendar fechaFinal, ReporteVenta reporte, List<ContratoDTO> contratos) throws ObjetosNegocioException;
    
}
