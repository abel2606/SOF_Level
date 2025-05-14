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
public class ItemReporte extends javax.swing.JPanel {

    /**
     * Creates new form PanelReporte
     * @param nombreReporte
     */
    public ItemReporte(String nombreReporte) {
        initComponents();
        lblNombreReporte.setText("Cliente: " + nombreReporte);

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

        lblNombreReporte = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setMaximumSize(new java.awt.Dimension(32767, 150));
        setPreferredSize(new java.awt.Dimension(700, 150));

        lblNombreReporte.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblNombreReporte.setText("Jesus rene");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblNombreReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addGap(297, 297, 297))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblNombreReporte;
    // End of variables declaration//GEN-END:variables
}
