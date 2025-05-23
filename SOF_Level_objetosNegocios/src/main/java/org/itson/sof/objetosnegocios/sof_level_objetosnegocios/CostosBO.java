/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import java.awt.Color;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.ByteArrayOutputStream;
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
            throw new ObjetosNegocioException("No se pudo abrir el archivo: " + e.getMessage());
        }
    }

    @Override
    public void generarReporte(GregorianCalendar fechaInicio, GregorianCalendar fechaFinal, ReporteVenta reporte, List<ContratoDTO> contratos) throws ObjetosNegocioException {
        Document documento = new Document();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

        String nombreArchivo = reporte.getNombre() + ".pdf";
        File archivoPDF = new File(reporte.getRuta(), nombreArchivo);
        
        if (archivoPDF.exists()) {
            throw new ObjetosNegocioException("Ya existe un archivo con ese nombre en la ruta: " + archivoPDF.getAbsolutePath());
        }
        
        try {

            PdfWriter.getInstance(documento, new FileOutputStream(archivoPDF));
            documento.open();

            Font fuenteTituloPrincipal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.UNDERLINE);
            Font fuenteTituloSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fuenteCabeceraTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteTextoNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font fuenteTextoResaltado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            BaseColor colorCabeceraTabla = new BaseColor(220, 220, 220);

            Paragraph tituloPrincipal = new Paragraph("REPORTE DETALLADO DE VENTAS Y ANÁLISIS", fuenteTituloPrincipal);
            tituloPrincipal.setAlignment(Element.ALIGN_CENTER);
            tituloPrincipal.setSpacingAfter(20f);
            documento.add(tituloPrincipal);

            Paragraph parrafoPeriodo = new Paragraph("Periodo del reporte: " + formatoFecha.format(fechaInicio.getTime()) + " al " + formatoFecha.format(fechaFinal.getTime()), fuenteTextoNormal);
            parrafoPeriodo.setAlignment(Element.ALIGN_CENTER);
            parrafoPeriodo.setSpacingAfter(20f);
            documento.add(parrafoPeriodo);

            double totalVentasGlobal = 0.0;
            Map<String, Integer> conteoContratosPorTematica = new HashMap<>();
            Map<String, Double> sumaVentasPorTematica = new HashMap<>();
            Map<String, Integer> conteoContratosPorPaquete = new HashMap<>();
            Map<String, Integer> conteoContratosPorCliente = new HashMap<>();
            ContratoDTO contratoMasCostoso = null;
            ContratoDTO contratoMenosCostoso = null;
            
            if (contratos == null || contratos.isEmpty()) {
                documento.add(new Paragraph("No se encontraron contratos para el periodo seleccionado.", fuenteTextoNormal));
            } else {
                Paragraph tituloTabla = new Paragraph("Detalle de Contratos", fuenteTituloSeccion);
                tituloTabla.setSpacingBefore(10f);
                tituloTabla.setSpacingAfter(10f);
                documento.add(tituloTabla);

                PdfPTable tablaContratos = new PdfPTable(6);
                tablaContratos.setWidthPercentage(100);
                tablaContratos.setSpacingBefore(10f);
                tablaContratos.setSpacingAfter(10f);
                float[] anchosColumnas = {2.5f, 1f, 1.5f, 1.5f, 2f, 1.5f};
                tablaContratos.setWidths(anchosColumnas);

                String[] cabeceras = {"Cliente", "Folio", "Temática", "Estado", "Paquete", "Costo"};
                for (String cabecera : cabeceras) {
                    PdfPCell celdaCabecera = new PdfPCell(new Phrase(cabecera, fuenteCabeceraTabla));
                    celdaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaCabecera.setBackgroundColor(colorCabeceraTabla);
                    tablaContratos.addCell(celdaCabecera);
                }

                for (ContratoDTO contrato : contratos) {
                    tablaContratos.addCell(new Phrase(contrato.getCliente().getNombre(), fuenteTextoNormal));
                    tablaContratos.addCell(new Phrase(contrato.getFolio(), fuenteTextoNormal));
                    tablaContratos.addCell(new Phrase(contrato.getTematica(), fuenteTextoNormal));
                    tablaContratos.addCell(new Phrase(contrato.getEstado(), fuenteTextoNormal));
                    tablaContratos.addCell(new Phrase(contrato.getPaquete().getNombre(), fuenteTextoNormal));
                    
                    PdfPCell celdaCosto = new PdfPCell(new Phrase(formatoMoneda.format(contrato.getPaquete().getPrecio()), fuenteTextoNormal));
                    celdaCosto.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tablaContratos.addCell(celdaCosto);

                    totalVentasGlobal += contrato.getPaquete().getPrecio();

                    String tematica = contrato.getTematica();
                    conteoContratosPorTematica.put(tematica, conteoContratosPorTematica.getOrDefault(tematica, 0) + 1);
                    sumaVentasPorTematica.put(tematica, sumaVentasPorTematica.getOrDefault(tematica, 0.0) + contrato.getPaquete().getPrecio());

                    String nombrePaquete = contrato.getPaquete().getNombre();
                    conteoContratosPorPaquete.put(nombrePaquete, conteoContratosPorPaquete.getOrDefault(nombrePaquete, 0) + 1);
                    
                    String nombreCliente = contrato.getCliente().getNombre();
                    conteoContratosPorCliente.put(nombreCliente, conteoContratosPorCliente.getOrDefault(nombreCliente, 0) + 1);

                    if (contratoMasCostoso == null || contrato.getPaquete().getPrecio() > contratoMasCostoso.getPaquete().getPrecio()) {
                        contratoMasCostoso = contrato;
                    }
                    if (contratoMenosCostoso == null || contrato.getPaquete().getPrecio() < contratoMenosCostoso.getPaquete().getPrecio()) {
                        contratoMenosCostoso = contrato;
                    }
                }
                documento.add(tablaContratos);

                Paragraph textoTotalVentas = new Paragraph("Total General de Ventas: " + formatoMoneda.format(totalVentasGlobal), fuenteSubtitulo);
                textoTotalVentas.setAlignment(Element.ALIGN_RIGHT);
                textoTotalVentas.setSpacingBefore(10f);
                documento.add(textoTotalVentas);
                documento.add(new Paragraph(" "));

                Paragraph tituloEstadisticas = new Paragraph("Resumen Estadístico", fuenteTituloSeccion);
                tituloEstadisticas.setSpacingBefore(20f);
                tituloEstadisticas.setSpacingAfter(10f);
                documento.add(tituloEstadisticas);
                documento.add(new Paragraph("Número total de contratos: " + contratos.size(), fuenteTextoNormal));
                if (!contratos.isEmpty()) {
                    documento.add(new Paragraph("Costo promedio por contrato: " + formatoMoneda.format(totalVentasGlobal / contratos.size()), fuenteTextoNormal));
                }

                //GRAFICO 1: VENTAS POR TEMÁTICA
                if (!sumaVentasPorTematica.isEmpty()) {
                    documento.newPage();
                    PdfPTable tablaGraficoTematica = new PdfPTable(1);
                    tablaGraficoTematica.setWidthPercentage(100);
                    tablaGraficoTematica.setSpacingBefore(20f);

                    Paragraph tituloGrafico = crearTituloGraficoInterno("Gráfico: Monto de Ventas por Temática", fuenteTituloSeccion);
                    PdfPCell celdaTitulo = new PdfPCell(tituloGrafico);
                    celdaTitulo.setBorder(Rectangle.NO_BORDER);
                    celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaGraficoTematica.addCell(celdaTitulo);

                    try (ByteArrayOutputStream chartStream = new ByteArrayOutputStream()) {
                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        sumaVentasPorTematica.entrySet().stream()
                            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                            .forEach(entry -> dataset.addValue(entry.getValue(), "Ventas ($)", entry.getKey()));
                        
                        JFreeChart barChart = ChartFactory.createBarChart("", "Temática", "Monto ($)", dataset, PlotOrientation.VERTICAL, false, true, false);
                        personalizarGraficoBarras(barChart, Color.decode("#4572A7"));
                        ChartUtils.writeChartAsPNG(chartStream, barChart, 550, 380); 
                        Image chartImage = Image.getInstance(chartStream.toByteArray());
                        PdfPCell celdaImagen = new PdfPCell(chartImage, true);
                        celdaImagen.setBorder(Rectangle.NO_BORDER);
                        celdaImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celdaImagen.setPaddingTop(5f);
                        tablaGraficoTematica.addCell(celdaImagen);
                        documento.add(tablaGraficoTematica);
                    } catch (Exception e) { manejarErrorGrafico(documento, "ventas por temática", e, fuenteTextoNormal); }
                } else {
                    documento.add(new Paragraph("No hay datos suficientes para generar el gráfico de ventas por temática.", fuenteTextoNormal));
                }

                //GRÁFICO 2: CONTRATOS POR CLIENTE
                if (!conteoContratosPorCliente.isEmpty()) {
                    documento.newPage();
                    PdfPTable tablaGraficoCliente = new PdfPTable(1);
                    tablaGraficoCliente.setWidthPercentage(100);
                    tablaGraficoCliente.setSpacingBefore(20f);

                    Paragraph tituloGrafico = crearTituloGraficoInterno("Gráfico: Número de Contratos por Cliente", fuenteTituloSeccion);
                    PdfPCell celdaTitulo = new PdfPCell(tituloGrafico);
                    celdaTitulo.setBorder(Rectangle.NO_BORDER);
                    celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaGraficoCliente.addCell(celdaTitulo);
                    
                     try (ByteArrayOutputStream chartStream = new ByteArrayOutputStream()) {
                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        conteoContratosPorCliente.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .limit(15)
                            .forEach(entry -> dataset.addValue(entry.getValue(), "Nº Contratos", entry.getKey()));

                        JFreeChart barChart = ChartFactory.createBarChart("", "Cliente", "Nº Contratos", dataset, PlotOrientation.VERTICAL, false, true, false);
                        personalizarGraficoBarras(barChart, Color.decode("#89A54E"));
                        ChartUtils.writeChartAsPNG(chartStream, barChart, 550, 380);
                        Image chartImage = Image.getInstance(chartStream.toByteArray());
                        PdfPCell celdaImagen = new PdfPCell(chartImage, true);
                        celdaImagen.setBorder(Rectangle.NO_BORDER);
                        celdaImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celdaImagen.setPaddingTop(5f);
                        tablaGraficoCliente.addCell(celdaImagen);
                        documento.add(tablaGraficoCliente);
                    } catch (Exception e) { manejarErrorGrafico(documento, "contratos por cliente", e, fuenteTextoNormal); }
                } else {
                     documento.add(new Paragraph("No hay datos suficientes para generar el gráfico de contratos por cliente.", fuenteTextoNormal));
                }

                //GRAFICO 3: POPULARIDAD DE PAQUETES
                if (!conteoContratosPorPaquete.isEmpty()) {
                    documento.newPage();
                    PdfPTable tablaGraficoPaquete = new PdfPTable(1);
                    tablaGraficoPaquete.setWidthPercentage(100);
                    tablaGraficoPaquete.setSpacingBefore(20f);
                    
                    Paragraph tituloGrafico = crearTituloGraficoInterno("Gráfico: Popularidad de Paquetes (Cantidad Vendida)", fuenteTituloSeccion);
                    PdfPCell celdaTitulo = new PdfPCell(tituloGrafico);
                    celdaTitulo.setBorder(Rectangle.NO_BORDER);
                    celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaGraficoPaquete.addCell(celdaTitulo);

                    try (ByteArrayOutputStream chartStream = new ByteArrayOutputStream()) {
                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                         conteoContratosPorPaquete.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .forEach(entry -> dataset.addValue(entry.getValue(), "Cantidad Vendida", entry.getKey()));

                        JFreeChart barChart = ChartFactory.createBarChart("", "Paquete", "Cantidad Vendida", dataset, PlotOrientation.VERTICAL, false, true, false);
                        personalizarGraficoBarras(barChart, Color.decode("#AA4643"));
                        ChartUtils.writeChartAsPNG(chartStream, barChart, 550, 380);
                        Image chartImage = Image.getInstance(chartStream.toByteArray());
                        PdfPCell celdaImagen = new PdfPCell(chartImage, true);
                        celdaImagen.setBorder(Rectangle.NO_BORDER);
                        celdaImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celdaImagen.setPaddingTop(5f);
                        tablaGraficoPaquete.addCell(celdaImagen);
                        documento.add(tablaGraficoPaquete);
                    } catch (Exception e) { manejarErrorGrafico(documento, "popularidad de paquetes", e, fuenteTextoNormal); }
                } else {
                    documento.add(new Paragraph("No hay datos suficientes para generar el gráfico de popularidad de paquetes.", fuenteTextoNormal));
                }

            }

        } catch (DocumentException | FileNotFoundException e) {
            throw new ObjetosNegocioException("Error al generar el documento PDF: " + e.getMessage());
        } finally {
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
        }
    }
    
    private Paragraph crearTituloGraficoInterno(String titulo, Font fuente) {
        Paragraph parrafoTitulo = new Paragraph(titulo, fuente);
        parrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        return parrafoTitulo;
    }
    
    private void personalizarGraficoBarras(JFreeChart chart, Color colorBarras) {
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE); 
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY); 
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);  

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarras); 
        renderer.setDrawBarOutline(false); 
    }
    
    private void manejarErrorGrafico(Document doc, String nombreGrafico, Exception ex, Font fuente) {
        System.err.println("Error al generar o insertar el gráfico de " + nombreGrafico + ": " + ex.getMessage());
        ex.printStackTrace();
        try {
            doc.add(new Paragraph("No se pudo generar el gráfico de " + nombreGrafico + ".", fuente));
        } catch (DocumentException de) {
            System.err.println("Error al añadir mensaje de error de gráfico al PDF: " + de.getMessage());
        }
    }

}