package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;

/**
 *
 * @author JazmE
 */
public class PanelCostos extends javax.swing.JPanel {

    GestorCitas gestor;
    private final PantallaPrincipal principal;
    private boolean inicializado = false;

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        //List<ReporteVenta> reportes = ConsultarReportesVenta();

            // Crear un panel de contenedor para los contratos
            JPanel panelContenedor = new JPanel();
            panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
            panelContenedor.setBackground(new Color(220, 240, 255));

            /*if (reportes.isEmpty()) {
                //Poner un mensaje si no hay contratos
                JLabel mensaje = new JLabel("Aun no hay reportes");
                mensaje.setSize(100, 100);
                mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 40));
                panelContenedor.add(mensaje);
            } else {
                /*
                for (ContratoDTO contrato : contratos) {
                    ItemContrato panel = new ItemContrato(
                            contrato.getCliente().getNombre(),
                            contrato.getPaquete().getNombre(),
                            contrato.getEstado(),
                            contrato.getTematica(), 
                            contrato.getFolio());

                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            manejarClicEnContrato(contrato);
                        }
                    });

                    panelContenedor.setBackground(new Color(220, 240, 255));
                    panelContenedor.add(panel);
                    panelContenedor.add(Box.createVerticalStrut(10));
                }
            }*/
            scrollPaneReportesVenta.setViewportView(panelContenedor);
            scrollPaneReportesVenta.getVerticalScrollBar().setUnitIncrement(20);
    }
    /**
     * Creates new form PanelContratos
     *
     * @param principal
     */
    public PanelCostos(PantallaPrincipal principal) {
        this.principal = principal;
        gestor=GestorCitas.getInstance();
    }

    /**
     * Devuelve la lista de reportes de la ruta
     *
     * @return Lista de ReportesVenta
     */
    /*
    private List<ReporteVenta> ConsultarReportesVenta() {
        try {
            List<ReporteVenta> contratos = gestor.obtenerReportesVenta();
            return contratos != null ? contratos : new ArrayList<>();
        } catch (GestorException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            
        }
        return new ArrayList<>();
    }*/

    /**
     * Metodo que se llama cuando se hace click a un contrato
     * @param contrato Contrato al que se le hizo click
     */
    /*
    private void manejarClicEnReporte(ReporteVenta reporteVenta) {
        //Notificar el cambio de panel
        principal.setContrato(contrato);
        principal.PanelContrato();
    }*/

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        scrollPaneReportesVenta = new javax.swing.JScrollPane();

        jPanel2.setPreferredSize(new java.awt.Dimension(850, 550));

        scrollPaneReportesVenta.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneReportesVenta.setBorder(null);
        scrollPaneReportesVenta.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneReportesVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneReportesVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scrollPaneReportesVenta;
    // End of variables declaration//GEN-END:variables
}
