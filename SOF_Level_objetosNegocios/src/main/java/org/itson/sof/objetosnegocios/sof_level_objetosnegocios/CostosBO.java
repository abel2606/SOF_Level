/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.ContratoDTO;
import reportes.ReporteVenta;

/**
 *
 * @author JazmE
 */
public class CostosBO implements ICostosBO {

    @Override
    public List<ReporteVenta> obtenerReportesVenta(String ruta) throws ObjetosNegocioException {
        List<ReporteVenta> reportes = new ArrayList<>();

        File carpeta = new File(ruta);
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            throw new ObjetosNegocioException("Ruta inválida: " + ruta);
        }
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".pdf")) {
                    reportes.add(new ReporteVenta(archivo.getName(), archivo.getAbsolutePath()));
                }
            }
        }
        return reportes;
    }

    @Override
    public void abrirReporte(String ruta) throws ObjetosNegocioException {
        try {
            File archivo = new File(ruta);
            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            } else {
                throw new ObjetosNegocioException("Ruta inválida: " + ruta);
            }
        } catch (IOException e) {
            throw new ObjetosNegocioException("No se pudo abrir el archivo");
        }
    }

    @Override
    public void generarReporte(GregorianCalendar fechaInicio, GregorianCalendar fechaFinal, ReporteVenta reporte, List<ContratoDTO> contratos) throws ObjetosNegocioException {
        Document documento = new Document();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(reporte.getRuta()));
            documento.open();

            // Encabezado
            Paragraph titulo = new Paragraph("REPORTE DE VENTAS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Periodo: " + formatoFecha.format(fechaInicio.getTime()) + " - " + formatoFecha.format(fechaFinal.getTime())));
            documento.add(new Paragraph(" "));

            // Tabla
            PdfPTable tabla = new PdfPTable(6); // 6 columnas
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] columnas = {"Cliente", "Folio", "Temática", "Estado", "Paquete", "Costo"};
            for (String col : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(col, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                tabla.addCell(celda);
            }

            double total = 0.0;
            for (ContratoDTO contrato : contratos) {
                tabla.addCell(contrato.getCliente().getNombre());                    // Cliente
                tabla.addCell(contrato.getFolio());                      // Folio
                tabla.addCell(contrato.getTematica());                   // Temática
                tabla.addCell(contrato.getEstado());                     // Estado
                tabla.addCell(contrato.getPaquete().getNombre());        // Nombre del paquete
                tabla.addCell("$" + contrato.getPaquete().getPrecio());   // Costo

                total += contrato.getPaquete().getPrecio();
            }

            documento.add(tabla);

            // Total
            Paragraph totalTexto = new Paragraph("Total: $" + total, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            totalTexto.setAlignment(Element.ALIGN_RIGHT);
            documento.add(totalTexto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ObjetosNegocioException("Error al generar el reporte: " + e.getMessage());
        } finally {
            documento.close();
        }
    }

}
