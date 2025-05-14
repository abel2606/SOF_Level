package org.itson.sof.sof_level_presentacion.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_level_presentacion.componentes.ItemCita;
import org.itson.sof.sof_level_presentacion.interfaces.DialogCita;
import org.itson.sof.sof_level_presentacion.interfaces.PantallaPrincipal;

/**
 *
 * @author JazmE
 */
public class ItemScrollCitas extends javax.swing.JDialog {
    DialogCita dlgCita;
    List<CitaDTO> citasDelDia;
    PantallaPrincipal pantallaPrincipal;
    /**
     * Creates new form ItemScrollCitas
     * @param parent
     * @param modal
     * @param citasDelDia
     */
    public ItemScrollCitas(java.awt.Frame parent, boolean modal, List<CitaDTO> citasDelDia) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        
        if (parent instanceof PantallaPrincipal pantallaPrincipal1) {
             pantallaPrincipal= pantallaPrincipal1;
        }
        
        // Crear un panel de contenedor para los contratos
        this.jPanel1 = new JPanel();
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
        jPanel1.setBackground(new Color(220, 240, 255));

        if (citasDelDia.isEmpty()) {
            //Poner un mensaje si no hay contratos
            JLabel mensaje = new JLabel("Aun no hay citas");
            mensaje.setSize(100, 100);
            mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            jPanel1.add(mensaje);
        } else {
            for (CitaDTO cita : citasDelDia) {
                ItemCita panel = new ItemCita(
                        cita.getFechaHoraInicio());
                panel.setPreferredSize(new Dimension(300, 50));
                panel.setMaximumSize(new Dimension(300, 50));
                panel.setMinimumSize(new Dimension(300, 50));

                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        manejarClicEnCita(cita);
                    }
                });

                jPanel1.setBackground(new Color(220, 240, 255));
                jPanel1.add(panel);
                jPanel1.add(Box.createVerticalStrut(10));
            }
        }
        this.jScrollPane1.setViewportView(jPanel1);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
    }
    
    /**
     * Metodo que se llama cuando se hace click a una cita
     *
     * @param cita cita al que se le hizo click
     */
    private void manejarClicEnCita(CitaDTO cita) {
        
        boolean unicaCita=false;
        if (citasDelDia != null) {
                if (citasDelDia.size() == 1) {
                    unicaCita = true;
                }
            }
        
        dlgCita = new DialogCita(pantallaPrincipal, true, cita, unicaCita);
        dlgCita.setVisible(true);
        dlgCita.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("El diálogo de cita se ha cerrado.");
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
