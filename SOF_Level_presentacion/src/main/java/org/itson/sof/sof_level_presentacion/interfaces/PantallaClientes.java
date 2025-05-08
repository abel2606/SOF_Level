package org.itson.sof.sof_level_presentacion.interfaces;

import Deprecated.*;
import javax.swing.table.DefaultTableModel;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.UsuarioDTO;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellEditor;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellRender;
import org.itson.sof.sof_level_presentacion.componentes.TableActionEvent;

/**
 *
 * @author renec
 */
public class PantallaClientes extends javax.swing.JFrame {

   
    public PantallaClientes() {
        
        initComponents();
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEditar(int row) {
                System.out.println("Editar columna: " + row);
                
            }

            @Override
            public void onEliminar(int row) {
                System.out.println("Eliminar columna: " + row);
                if(tblClientes.isEditing()){
                    tblClientes.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
                model.removeRow(row);
            }
        };
        tblClientes.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        tblClientes.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDetalleContrato = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        pnlOpciones = new javax.swing.JPanel();
        lblContratos = new javax.swing.JLabel();
        lblImgContrato = new javax.swing.JLabel();
        lblImgPaquetes = new javax.swing.JLabel();
        lblPaquetes = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        lblImgClientes = new javax.swing.JLabel();
        lblImgMateriales = new javax.swing.JLabel();
        lblMateriales = new javax.swing.JLabel();
        lblImgCostos = new javax.swing.JLabel();
        lblCostos = new javax.swing.JLabel();
        lblImgCerrarSesion = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        lblSesion = new javax.swing.JLabel();
        pnlEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnMenuDesplegable = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDetalleContrato.setBackground(new java.awt.Color(220, 240, 255));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Abel Eduardo Sánchez Guerrer", "abel.sanchez245473@potros.itson.edu.mx", "6441297653", null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "E-Mail", "Teléfono", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setRowHeight(50);
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout pnlDetalleContratoLayout = new javax.swing.GroupLayout(pnlDetalleContrato);
        pnlDetalleContrato.setLayout(pnlDetalleContratoLayout);
        pnlDetalleContratoLayout.setHorizontalGroup(
            pnlDetalleContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalleContratoLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        pnlDetalleContratoLayout.setVerticalGroup(
            pnlDetalleContratoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetalleContratoLayout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(pnlDetalleContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 850, 550));

        pnlOpciones.setBackground(new java.awt.Color(255, 255, 255));

        lblContratos.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblContratos.setForeground(new java.awt.Color(0, 146, 236));
        lblContratos.setText("Contratos");

        lblImgContrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contratoAzulIcon.png"))); // NOI18N

        lblImgPaquetes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paqueteICon.png"))); // NOI18N

        lblPaquetes.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPaquetes.setText("Paquetes");

        lblClientes.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblClientes.setText("Clientes");

        lblImgClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clienteIcon.png"))); // NOI18N

        lblImgMateriales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/materialesIcon.png"))); // NOI18N

        lblMateriales.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblMateriales.setText("Materiales");

        lblImgCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/costosIcon.png"))); // NOI18N

        lblCostos.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCostos.setText("Costos");

        lblImgCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarSesionIcon.png"))); // NOI18N

        lblCerrar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCerrar.setText("Cerrar");

        lblSesion.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblSesion.setText("sesión");

        javax.swing.GroupLayout pnlOpcionesLayout = new javax.swing.GroupLayout(pnlOpciones);
        pnlOpciones.setLayout(pnlOpcionesLayout);
        pnlOpcionesLayout.setHorizontalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpcionesLayout.createSequentialGroup()
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOpcionesLayout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImgClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgCostos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpcionesLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImgMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(lblMateriales)))
                .addGap(10, 10, 10))
        );
        pnlOpcionesLayout.setVerticalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpcionesLayout.createSequentialGroup()
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImgCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlOpcionesLayout.createSequentialGroup()
                        .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 130, Short.MAX_VALUE))
        );

        getContentPane().add(pnlOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 190, 550));

        pnlEncabezado.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Contratos");

        lblNombreUsuario.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblNombreUsuario.setText("NombreUsuario");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/locoIcon.png"))); // NOI18N

        btnMenuDesplegable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/3lineasBoton.png"))); // NOI18N
        btnMenuDesplegable.setBorder(null);

        javax.swing.GroupLayout pnlEncabezadoLayout = new javax.swing.GroupLayout(pnlEncabezado);
        pnlEncabezado.setLayout(pnlEncabezadoLayout);
        pnlEncabezadoLayout.setHorizontalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnMenuDesplegable, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                        .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        pnlEncabezadoLayout.setVerticalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnMenuDesplegable, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        getContentPane().add(pnlEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 120));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMenuDesplegable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblContratos;
    private javax.swing.JLabel lblCostos;
    private javax.swing.JLabel lblImgCerrarSesion;
    private javax.swing.JLabel lblImgClientes;
    private javax.swing.JLabel lblImgContrato;
    private javax.swing.JLabel lblImgCostos;
    private javax.swing.JLabel lblImgMateriales;
    private javax.swing.JLabel lblImgPaquetes;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMateriales;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblPaquetes;
    private javax.swing.JLabel lblSesion;
    private javax.swing.JPanel pnlDetalleContrato;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlOpciones;
    private javax.swing.JTable tblClientes;
    // End of variables declaration//GEN-END:variables
}
