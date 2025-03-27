package org.itson.sof.sof_level_presentacion.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;

/**
 *
 * @author Abe
 */
public class ItemContrato extends javax.swing.JPanel {

    /**
     * Creates new form PanelContrato
     * @param nombreCliente
     * @param paquete
     * @param fechaInicio
     * @param fechaFin
     * @param folio
     */
    public ItemContrato(String nombreCliente, String paquete, String fechaInicio, String fechaFin, String folio) {
        initComponents();
        lblNombreCliente.setText("Cliente: " + nombreCliente);
        lblPaquete.setText("Paquete: " + paquete);
        lblFechaInicio.setText(fechaInicio);
        lblFechaFin.setText(fechaFin);
        lblFolio.setText(folio);

        int radius = 50;
        setBorder(BorderFactory.createCompoundBorder(
                new BordeRedondo(radius, Color.BLACK, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        setOpaque(false); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255)); 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

        g2.dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFechaFin = new javax.swing.JLabel();
        lblPaquete = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        lblFechaInicio1 = new javax.swing.JLabel();
        lblFolio = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setMaximumSize(new java.awt.Dimension(32767, 150));
        setPreferredSize(new java.awt.Dimension(700, 150));

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblFechaFin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaFin.setText("19/05/2025");

        lblPaquete.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPaquete.setText("Paquete");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaInicio.setText("19/05/2025");

        lblNombreCliente.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblNombreCliente.setText("Jesus rene");

        lblFechaInicio1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblFechaInicio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaInicio1.setText("-");

        lblFolio.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblFolio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFolio.setText("19/05/2025");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPaquete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaFin))
                    .addComponent(lblFolio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaInicio1;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblPaquete;
    // End of variables declaration//GEN-END:variables
}
