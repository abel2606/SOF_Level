package org.itson.sof.sof_level_presentacion.interfaces;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.FotografoDTO;

/**
 *
 * @author JazmE
 */
public class DialogCita extends javax.swing.JDialog {

    CitaDTO cita;
    FotografoBO fotografoBO;
    CitaBO citasBO;
    boolean editando=false;
    /**
     * Creates new form DialogCita
     * @param parent
     * @param modal
     * @param cita
     */
    public DialogCita(java.awt.Frame parent, boolean modal,CitaDTO cita) {
        super(parent, modal);
        initComponents();
        this.cita=cita;
        fotografoBO=new FotografoBO();
        citasBO=new CitaBO();
        
        AsignarFotografos();
        AsignarCita();
        
        if(cita==null){
            this.lblDelete.setEnabled(false);
            this.lblEdit.setEnabled(false);
            editando=true;
            HabilitarEditar();
        }
    }
    
    private void AsignarCita() {
        if (cita != null) {
            if (cita.getLugar() != null) {
                this.txtaLugar.setText(cita.getLugar());
            }
            if (cita.getFotografo() != null) {
                String nombreFotografo = cita.getFotografo().getNombrePersona();

                // Recorrer todos los elementos del JComboBox y seleccionar el correcto
                for (int i = 0; i < cbFotografo.getItemCount(); i++) {
                    String nombreItem = cbFotografo.getItemAt(i).toString(); // Obtener el nombre del fotógrafo en el JComboBox
                    if (nombreItem.equals(nombreFotografo)) {
                        cbFotografo.setSelectedIndex(i); // Seleccionar el índice correspondiente
                        break; // Salir del bucle una vez encontrado
                    }
                }
            }
            if (cita.getExtras() != null) {
                this.txtaExtras.setText(cita.getExtras());
            }
            if (cita.getFechaHoraInicio() != null) {
                
            }
            if (cita.getFechaHoraFin() != null) {
                
            }
        }

    }

    public void AsignarFotografos() {
        try {
            // Obtener la lista de fotógrafos
            List<FotografoDTO> fotografos = fotografoBO.obtenerTodosFotografos();

            // Limpiar el JComboBox antes de agregar nuevos elementos
            cbFotografo.removeAllItems();

            // Agregar los nombres de los fotógrafos al JComboBox
            for (FotografoDTO fotografo : fotografos) {
                cbFotografo.addItem(fotografo.getNombrePersona());
            }
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(DialogCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Cancelar(){
        this.dispose();
    }
    
    public void Aceptar() {
        if (cita == null) {
            AgregarCita();
        } else {
            if (editando) {
                EditarCita();
            } else {
              this.dispose(); 
            }
        }
        this.dispose();
    }

    private void HabilitarEditar() {
        if (editando) {
            //Reactivar los campos
            this.txtaExtras.setEnabled(true);
            this.txtaLugar.setEnabled(true);
            this.cbFotografo.setEnabled(true);
        }else{
            //Volver a colocar los valores originales de la cita
            AsignarCita();
            //Bloquear los campos
            this.txtaExtras.setEnabled(false);
            this.txtaLugar.setEnabled(false);
            this.cbFotografo.setEnabled(false);
        }
    }
    
    private void BorrarCita(){
        try {
            citasBO.eliminarCita(cita);
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(DialogCita.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostrar mensaje de confirmación
    }

    private void EditarCita() {
        cita.setExtras(this.txtaExtras.getText());
        cita.setLugar(this.txtaLugar.getText());
        
        cita.getFechaHoraInicio(); //TODO
        cita.getFechaHoraFin(); //TODO
        
        FotografoDTO fotografo=new FotografoDTO();
        fotografo.setNombrePersona(this.cbFotografo.getSelectedItem().toString());
        cita.setFotografo(fotografo);
        
        try {
            citasBO.actualizarCita(cita);

        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(DialogCita.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostrar mensaje de confirmación
    }

    private void AgregarCita() {
        cita.setExtras(this.txtaExtras.getText());
        cita.setLugar(this.txtaLugar.getText());
        
        cita.getFechaHoraInicio(); //TODO
        cita.getFechaHoraFin(); //TODO
        
        FotografoDTO fotografo=new FotografoDTO();
        fotografo.setNombrePersona(this.cbFotografo.getSelectedItem().toString());
        cita.setFotografo(fotografo);
        try {
            citasBO.crearCita(cita);
        } catch (ObjetosNegocioException ex) {
            Logger.getLogger(DialogCita.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostrar mensaje de confirmación
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JLabel();
        btnEditar = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JLabel();
        lblSeleccionarFecha = new javax.swing.JLabel();
        lblSelecionarHorario = new javax.swing.JLabel();
        lblLugar = new javax.swing.JLabel();
        lblFotografo = new javax.swing.JLabel();
        lblExtras = new javax.swing.JLabel();
        pnlFecha = new javax.swing.JPanel();
        pnlHorarios = new javax.swing.JPanel();
        sFechaFin = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        sFechaInicio = new javax.swing.JSpinner();
        cbFotografo = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaExtras = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaLugar = new javax.swing.JTextArea();
        lblEdit = new javax.swing.JLabel();
        lblDelete = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlPrincipal.setBackground(new java.awt.Color(220, 240, 255));
        pnlPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlPrincipal.setMaximumSize(new java.awt.Dimension(740, 420));
        pnlPrincipal.setMinimumSize(new java.awt.Dimension(740, 420));
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(740, 420));
        pnlPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCerrarMouseClicked(evt);
            }
        });
        pnlPrincipal.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, -1, -1));

        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlPrincipal.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlPrincipal.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtTitulo.setText("Detalle cita");
        pnlPrincipal.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lblSeleccionarFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSeleccionarFecha.setText("Seleccionar fecha");
        pnlPrincipal.add(lblSeleccionarFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblSelecionarHorario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSelecionarHorario.setText("Seleccionar horario inicio y fin");
        pnlPrincipal.add(lblSelecionarHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        lblLugar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLugar.setText("Lugar:");
        pnlPrincipal.add(lblLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, -1));

        lblFotografo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFotografo.setText("Fotografo:");
        pnlPrincipal.add(lblFotografo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, -1, -1));

        lblExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblExtras.setText("Extras:");
        pnlPrincipal.add(lblExtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, -1, -1));

        pnlFecha.setMaximumSize(new java.awt.Dimension(320, 170));
        pnlFecha.setMinimumSize(new java.awt.Dimension(320, 170));

        javax.swing.GroupLayout pnlFechaLayout = new javax.swing.GroupLayout(pnlFecha);
        pnlFecha.setLayout(pnlFechaLayout);
        pnlFechaLayout.setHorizontalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        pnlFechaLayout.setVerticalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        pnlPrincipal.add(pnlFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 360, 170));

        pnlHorarios.setBackground(new java.awt.Color(255, 255, 255));
        pnlHorarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlHorarios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sFechaFin.setBorder(null);
        pnlHorarios.add(sFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 130, 30));
        sFechaFin.setModel(new javax.swing.SpinnerDateModel());
        sFechaFin.setEditor(new javax.swing.JSpinner.DateEditor(sFechaFin, "hh:mm a"));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("-");
        pnlHorarios.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 20, 20));

        sFechaInicio.setBorder(null);
        pnlHorarios.add(sFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 30));
        sFechaInicio.setModel(new javax.swing.SpinnerDateModel());
        sFechaInicio.setEditor(new javax.swing.JSpinner.DateEditor(sFechaInicio, "hh:mm a"));

        pnlPrincipal.add(pnlHorarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 350, 50));

        cbFotografo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ricardo Gutierrez", " ", " ", " ", " " }));
        cbFotografo.setBorder(null);
        pnlPrincipal.add(cbFotografo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 230, 40));

        btnCancelar.setBackground(new java.awt.Color(160, 36, 38));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        pnlPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 120, 30));

        btnAceptar.setBackground(new java.awt.Color(36, 160, 108));
        btnAceptar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Confirmar");
        btnAceptar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
        });
        pnlPrincipal.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 370, 120, 30));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaExtras.setColumns(20);
        txtaExtras.setRows(5);
        txtaExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtaExtras);

        pnlPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 310, 70));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaLugar.setColumns(20);
        txtaLugar.setRows(5);
        txtaLugar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtaLugar);

        pnlPrincipal.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 310, 70));

        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pencilIcon.png"))); // NOI18N
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });
        pnlPrincipal.add(lblEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, -1));

        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteIconBig.png"))); // NOI18N
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDeleteMouseClicked(evt);
            }
        });
        pnlPrincipal.add(lblDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        getContentPane().add(pnlPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 420));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCerrarMouseClicked
        dispose();
    }//GEN-LAST:event_btnCerrarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        Cancelar();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        Aceptar();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void lblDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseClicked
        BorrarCita();
    }//GEN-LAST:event_lblDeleteMouseClicked

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        editando=!editando;
        HabilitarEditar();
    }//GEN-LAST:event_lblEditMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel btnCerrar;
    private javax.swing.JLabel btnEditar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JComboBox<String> cbFotografo;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblExtras;
    private javax.swing.JLabel lblFotografo;
    private javax.swing.JLabel lblLugar;
    private javax.swing.JLabel lblSeleccionarFecha;
    private javax.swing.JLabel lblSelecionarHorario;
    private javax.swing.JPanel pnlFecha;
    private javax.swing.JPanel pnlHorarios;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSpinner sFechaFin;
    private javax.swing.JSpinner sFechaInicio;
    private javax.swing.JLabel txtTitulo;
    private javax.swing.JTextArea txtaExtras;
    private javax.swing.JTextArea txtaLugar;
    // End of variables declaration//GEN-END:variables
}
