package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_level_presentacion.componentes.ItemContrato;

/**
 *
 * @author JazmE
 */
public class PanelContratos extends javax.swing.JPanel {
    GestorCitas gestor;
    private final PantallaPrincipal principal;
    private boolean inicializado = false;

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        List<ContratoDTO> contratos = ConsultarContratos();

            // Crear un panel de contenedor para los contratos
            JPanel panelContenedor = new JPanel();
            panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
            panelContenedor.setBackground(new Color(220, 240, 255));

            if (contratos.isEmpty()) {
                //Poner un mensaje si no hay contratos
                JLabel mensaje = new JLabel("Aun no hay contratos");
                mensaje.setSize(100, 100);
                mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 40));
                panelContenedor.add(mensaje);
            } else {
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
            }
            scrollPaneContratos.setViewportView(panelContenedor);
            scrollPaneContratos.getVerticalScrollBar().setUnitIncrement(20);
    }
    /**
     * Creates new form PanelContratos
     *
     * @param principal
     */
    public PanelContratos(PantallaPrincipal principal) {
        this.principal = principal;
        gestor=GestorCitas.getInstance();
    }

    /**
     * Devuelve la lista de contratos de la base de datos
     *
     * @return Lista de ContratoDTO
     */
    private List<ContratoDTO> ConsultarContratos() {
        try {
            List<ContratoDTO> contratos = gestor.obtenerContratos();
            return contratos != null ? contratos : new ArrayList<>();
        } catch (GestorCitasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            
        }
        return new ArrayList<>();
    }

    /**
     * Metodo que se llama cuando se hace click a un contrato
     * @param contrato Contrato al que se le hizo click
     */
    private void manejarClicEnContrato(ContratoDTO contrato) {
        principal.setContrato(contrato);
        principal.PanelContrato();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneContratos = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        lblAgregar = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();

        setForeground(new java.awt.Color(220, 240, 255));
        setPreferredSize(new java.awt.Dimension(850, 550));

        scrollPaneContratos.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneContratos.setBorder(null);
        scrollPaneContratos.setForeground(new java.awt.Color(220, 240, 255));
        scrollPaneContratos.setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(220, 240, 255));
        jPanel1.setForeground(new java.awt.Color(220, 240, 255));

        lblAgregar.setText("Agregar Contrato");

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(lblAgregar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnAgregar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar)
                .addGap(5, 5, 5)
                .addComponent(lblAgregar)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneContratos, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(scrollPaneContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        principal.crearContrato();
    }//GEN-LAST:event_btnAgregarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgregar;
    private javax.swing.JScrollPane scrollPaneContratos;
    // End of variables declaration//GEN-END:variables
}
