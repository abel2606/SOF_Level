package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_level_presentacion.componentes.ItemCita;
import org.itson.sof.sof_level_presentacion.interfaces.DialogCita;

/**
 *
 * @author JazmE
 */
public class PanelContrato extends javax.swing.JPanel {

    GestorCitas gestor;
    ContratoDTO contrato;
    private final PantallaPrincipal principal;
    DialogCita dlgCita;
    boolean unicaCita;
    private boolean inicializado = false;

    public JPanel panelContenedor;

    /**
     * Creates new form PanelContrato
     *
     * @param principal
     */
    public PanelContrato(PantallaPrincipal principal) {
        this.principal = principal;

        gestor = GestorCitas.getInstance();
    }

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        contrato = principal.getContrato();
        agregarCitasTabla();
        llenarCamposContrato();
    }

    private void agregarCitasTabla() {
        List<CitaDTO> citas = ConsultarCitas();

        // Crear un panel de contenedor para los contratos
        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setBackground(new Color(220, 240, 255));

        if (citas.isEmpty()) {
            //Poner un mensaje si no hay contratos
            JLabel mensaje = new JLabel("Aun no hay citas");
            mensaje.setSize(100, 100);
            mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            panelContenedor.add(mensaje);
        } else {
            for (CitaDTO cita : citas) {
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

                panelContenedor.setBackground(new Color(220, 240, 255));
                panelContenedor.add(panel);
                panelContenedor.add(Box.createVerticalStrut(10));
            }
        }
        scrollPaneCitas.setViewportView(panelContenedor);
        scrollPaneCitas.getVerticalScrollBar().setUnitIncrement(20);
    }

    /**
     * Devuelve la lista de citas del contrato seleccionado de la base de datos
     *
     * @return Lista de CitaDTO
     */
    private List<CitaDTO> ConsultarCitas() {
        try {
            List<CitaDTO> citas = gestor.obtenerCitasContrato(contrato);
            if (citas != null) {
                if (citas.size() == 1) {
                    unicaCita = true;
                }
            }
            return citas;
        } catch (GestorException ex) {
            JOptionPane.showMessageDialog(principal, ex);
        }
        return null;
    }

    /**
     * Metodo que se llama cuando se hace click a una cita
     *
     * @param cita cita al que se le hizo click
     */
    private void manejarClicEnCita(CitaDTO cita) {
        dlgCita = new DialogCita(principal, true, cita, unicaCita);
        dlgCita.setVisible(true);
        dlgCita.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("El diálogo de cita se ha cerrado.");
                agregarCitasTabla();
            }
        });
    }

    private void añadirCita() {
        dlgCita = new DialogCita(principal, true, null,true);
        dlgCita.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("El diálogo de cita se ha cerrado.");
                if (dlgCita.citaAgregada != null) {
                    agregarCitasTabla();
                    dlgCita.citaAgregada = null;
                } else {
                    JOptionPane.showMessageDialog(principal, "El diálogo fue cerrado sin cambios.");
                }
            }
        });
        dlgCita.setVisible(true);
    }
    
    
    private void llenarCamposContrato (){
        txtCliente.setText(contrato.getCliente().getNombre());
        txtTematica.setText(contrato.getTematica());
        txtPrecio.setText("$ "+contrato.getPaquete().getPrecio());
        
        txtCliente.setEnabled(false);
        txtTematica.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        lblPaquete = new javax.swing.JLabel();
        cmbPaquete = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        lblTematica = new javax.swing.JLabel();
        txtTematica = new javax.swing.JTextArea();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblCitas = new javax.swing.JLabel();
        pnlFecha = new javax.swing.JPanel();
        btnAgregarCita = new javax.swing.JButton();
        lblAgregarCita = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblFlechaDerecha = new javax.swing.JLabel();
        lblFlechaIzquierda = new javax.swing.JLabel();
        pnlCitas = new javax.swing.JPanel();
        scrollPaneCitas = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(220, 240, 255));
        setMaximumSize(new java.awt.Dimension(550, 750));
        setMinimumSize(new java.awt.Dimension(550, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCliente.setText("Cliente:");
        add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 50, 20));

        txtCliente.setEditable(false);
        add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 300, -1));

        lblPaquete.setText("Paquete:");
        add(lblPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        cmbPaquete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo del paquete" }));
        add(cmbPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 170, -1));

        btnEditar.setText("Editar");
        add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 110, -1));

        lblTematica.setText("Tematica:");
        add(lblTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        txtTematica.setColumns(20);
        txtTematica.setRows(5);
        add(txtTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 410, -1));

        lblPrecio.setText("Precio:");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));
        add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 330, -1));

        lblCitas.setText("Citas (X)");
        add(lblCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, -1));

        pnlFecha.setBackground(new java.awt.Color(220, 240, 255));
        pnlFecha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(pnlFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 260, 270));

        btnAgregarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarCitaMouseClicked(evt);
            }
        });
        btnAgregarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCitaActionPerformed(evt);
            }
        });
        add(btnAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 50, 50));

        lblAgregarCita.setText("Agregar cita");
        add(lblAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, -1, -1));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblFecha.setText("MES      AÑO");
        add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, -1, -1));

        lblFlechaDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/arrowRightIcon.png"))); // NOI18N
        add(lblFlechaDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 40, 40));

        lblFlechaIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/arrowLeftIcon.png"))); // NOI18N
        add(lblFlechaIzquierda, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 40, 40));

        pnlCitas.setBackground(new java.awt.Color(220, 240, 255));
        pnlCitas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        scrollPaneCitas.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneCitas.setBorder(null);
        scrollPaneCitas.setToolTipText("");

        javax.swing.GroupLayout pnlCitasLayout = new javax.swing.GroupLayout(pnlCitas);
        pnlCitas.setLayout(pnlCitasLayout);
        pnlCitasLayout.setHorizontalGroup(
            pnlCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        pnlCitasLayout.setVerticalGroup(
            pnlCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCitasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        add(pnlCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 350, 160));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCitaMouseClicked
        añadirCita();
    }//GEN-LAST:event_btnAgregarCitaMouseClicked

    private void btnAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarCitaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnEditar;
    private javax.swing.JComboBox<String> cmbPaquete;
    private javax.swing.JLabel lblAgregarCita;
    private javax.swing.JLabel lblCitas;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFlechaDerecha;
    private javax.swing.JLabel lblFlechaIzquierda;
    private javax.swing.JLabel lblPaquete;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTematica;
    private javax.swing.JPanel pnlCitas;
    private javax.swing.JPanel pnlFecha;
    private javax.swing.JScrollPane scrollPaneCitas;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextArea txtTematica;
    // End of variables declaration//GEN-END:variables
}
