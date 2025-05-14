package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.gestorcostos.GestorCostos;
import org.itson.sof.objetosnegocios.gestorcostos.gestorexception.GestorCostosException;
import org.itson.sof.sof_level_presentacion.componentes.ItemReporte;
import reportes.ReporteVenta;

/**
 *
 * @author JazmE
 */
public class PanelCostos extends javax.swing.JPanel {

    GestorCostos gestor;
    private final PantallaPrincipal principal;
    private boolean inicializado = false;
    private String ruta;
    DialogReporte dlgReporte;
    
    /**
     * Creates new form PanelContratos
     *
     * @param principal
     */
    public PanelCostos(PantallaPrincipal principal) {
        this.principal = principal;
        gestor=GestorCostos.getInstance();
    }

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        ruta = leerRuta();
        txtaRuta.setText(ruta);
        
        DecorarTabla();
    }
    
    private void DecorarTabla(){
        List<ReporteVenta> reportes = consultarReportesVenta();

        // Crear un panel de contenedor para los contratos
            JPanel panelContenedor = new JPanel();
            panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
            panelContenedor.setBackground(new Color(220, 240, 255));

            if (reportes.isEmpty()) {
                //Poner un mensaje si no hay contratos
                JLabel mensaje = new JLabel("No hay reportes en la ruta indicada");
                mensaje.setSize(100, 100);
                mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 40));
                panelContenedor.add(mensaje);
            } else {
                for (ReporteVenta reporte : reportes) {
                    ItemReporte panel = new ItemReporte(
                            reporte.getNombre());

                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            manejarClicEnReporte(reporte);
                        }
                    });

                    panelContenedor.setBackground(new Color(220, 240, 255));
                    panelContenedor.add(panel);
                    panelContenedor.add(Box.createVerticalStrut(10));
                }
            }
        scrollPaneReportesVenta.setViewportView(panelContenedor);
        scrollPaneReportesVenta.getVerticalScrollBar().setUnitIncrement(20);
    }

    private String leerRuta() {
        try {
            File archivo = new File(System.getProperty("user.dir") + File.separator + "ruta.txt");

            // Si no existe, crea el archivo y escribe la ruta relativa al proyecto padre
            if (!archivo.exists()) {
                // Obtener carpeta padre (subimos un nivel desde el proyecto actual)
                File carpetaActual = new File(System.getProperty("user.dir"));
                File carpetaPadre = carpetaActual.getParentFile();
                File carpetaReportes = new File(carpetaPadre, "Reportes");

                // Crear carpeta Reportes si no existe
                if (!carpetaReportes.exists()) {
                    carpetaReportes.mkdirs();
                }

                // Escribir ruta completa en ruta.txt
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                    bw.write(carpetaReportes.getAbsolutePath());
                }
            }

            // Leer la ruta del archivo
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                return br.readLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer o crear ruta.txt: " + e.getMessage());
            return null;
        }
    }

    private String escribirRuta(String nuevaRuta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ruta.txt"))) {
            bw.write(nuevaRuta);
            return "Ruta escrita correctamente";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al escribir la ruta";
        }
    }

    /**
     * Devuelve la lista de reportes de la ruta
     *
     * @return Lista de ReportesVenta
     */
    private List<ReporteVenta> consultarReportesVenta() {
        try {
            List<ReporteVenta> reportes = gestor.obtenerReportesVenta(ruta);
            return reportes != null ? reportes : new ArrayList<>();
        } catch (GestorCostosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());            
        }
        return new ArrayList<>();
    }

    /**
     * Metodo que se llama cuando se hace click a un reporte
     * @param reporte Reporte al que se le hizo click
     */
    private void manejarClicEnReporte(ReporteVenta reporteVenta) {
        try {
            gestor.abrirReporte(reporteVenta.getRuta());
        } catch (GestorCostosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());            
        }
    }
    
    private void SeleccionarRuta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opcion = chooser.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File carpeta = chooser.getSelectedFile();
            txtaRuta.setText(carpeta.getAbsolutePath());
            escribirRuta(carpeta.getAbsolutePath());
            DecorarTabla();
        }
    }
    
    private void GenerarReporte(){
        dlgReporte = new DialogReporte(principal, true,ruta);
        dlgReporte.setVisible(true);
        dlgReporte.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                inicializar();
            }
        });
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSuperior = new javax.swing.JPanel();
        scrollPaneReportesVenta = new javax.swing.JScrollPane();
        lblRuta = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaRuta = new javax.swing.JTextArea();
        lblGenerar = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();

        pnlSuperior.setBackground(new java.awt.Color(220, 240, 255));
        pnlSuperior.setPreferredSize(new java.awt.Dimension(850, 550));

        scrollPaneReportesVenta.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneReportesVenta.setBorder(null);
        scrollPaneReportesVenta.setToolTipText("");

        lblRuta.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblRuta.setText("Ruta:");

        txtaRuta.setColumns(20);
        txtaRuta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtaRuta.setRows(5);
        txtaRuta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtaRutaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtaRuta);

        lblGenerar.setText("Generar Reporte");

        btnGenerar.setText("Generar reporte");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSuperiorLayout = new javax.swing.GroupLayout(pnlSuperior);
        pnlSuperior.setLayout(pnlSuperiorLayout);
        pnlSuperiorLayout.setHorizontalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneReportesVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
            .addGroup(pnlSuperiorLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGenerar)
                    .addGroup(pnlSuperiorLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblGenerar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRuta)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSuperiorLayout.setVerticalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSuperiorLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblRuta, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(btnGenerar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblGenerar)
                        .addGap(18, 18, 18)))
                .addComponent(scrollPaneReportesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtaRutaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtaRutaMouseClicked
        SeleccionarRuta();
    }//GEN-LAST:event_txtaRutaMouseClicked

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        GenerarReporte();
    }//GEN-LAST:event_btnGenerarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGenerar;
    private javax.swing.JLabel lblRuta;
    private javax.swing.JPanel pnlSuperior;
    private javax.swing.JScrollPane scrollPaneReportesVenta;
    private javax.swing.JTextArea txtaRuta;
    // End of variables declaration//GEN-END:variables
}
