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
        //Notificar el cambio de panel
        principal.setContrato(contrato);
        principal.PanelContrato();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneContratos = new javax.swing.JScrollPane();

        setPreferredSize(new java.awt.Dimension(850, 550));

        scrollPaneContratos.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneContratos.setBorder(null);
        scrollPaneContratos.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneContratos, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneContratos, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPaneContratos;
    // End of variables declaration//GEN-END:variables
}
