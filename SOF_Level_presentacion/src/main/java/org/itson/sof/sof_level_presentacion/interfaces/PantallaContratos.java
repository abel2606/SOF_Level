package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.ContratoBO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_level_presentacion.componentes.PanelContrato;

/**
 *
 * @author renec
 */
public class PantallaContratos extends javax.swing.JFrame {
    ContratoBO contratoBO;
    
    /**
     * Creates new form PantallaContratos
     */
    public PantallaContratos() {
        initComponents();
        this.setResizable(false);
        
        contratoBO=new ContratoBO();

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
                PanelContrato panel = new PanelContrato(
                        contrato.getCliente().getNombre(),
                        contrato.getPaquete().getNombre(),
                        contrato.getEstado(),
                        contrato.getTematica());

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
     * Devuelve la lista de contratos de la base de datos
     *
     * @return Lista de ContratoDTO
     */
    private List<ContratoDTO> ConsultarContratos() {
        return contratoBO.obtenerTotalContratos();
    }

    /**
     * Metodo que se llama cuando se hace click a un contrato
     * @param contrato Contrato al que se le hizo click
     */
    private void manejarClicEnContrato(ContratoDTO contrato) {
        System.out.println("Contrato seleccionado: " + contrato.getCliente().getNombre());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContratos = new javax.swing.JPanel();
        scrollPaneContratos = new javax.swing.JScrollPane();
        pnlMenuOpciones = new javax.swing.JPanel();
        lblContratos = new javax.swing.JLabel();
        lblImgContratos = new javax.swing.JLabel();
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
        lblTitulo = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnMenuDesplegable = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContratos.setBackground(new java.awt.Color(220, 240, 255));

        scrollPaneContratos.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneContratos.setBorder(null);
        scrollPaneContratos.setToolTipText("");

        javax.swing.GroupLayout pnlContratosLayout = new javax.swing.GroupLayout(pnlContratos);
        pnlContratos.setLayout(pnlContratosLayout);
        pnlContratosLayout.setHorizontalGroup(
            pnlContratosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContratosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(scrollPaneContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        pnlContratosLayout.setVerticalGroup(
            pnlContratosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContratosLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(scrollPaneContratos, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnlContratos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 850, 550));

        pnlMenuOpciones.setBackground(new java.awt.Color(255, 255, 255));

        lblContratos.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblContratos.setForeground(new java.awt.Color(0, 146, 236));
        lblContratos.setText("Contratos");

        lblImgContratos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contratoAzulIcon.png"))); // NOI18N

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

        javax.swing.GroupLayout pnlMenuOpcionesLayout = new javax.swing.GroupLayout(pnlMenuOpciones);
        pnlMenuOpciones.setLayout(pnlMenuOpcionesLayout);
        pnlMenuOpcionesLayout.setHorizontalGroup(
            pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuOpcionesLayout.createSequentialGroup()
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuOpcionesLayout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImgClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImgCostos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuOpcionesLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImgMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(lblMateriales)))
                .addGap(10, 10, 10))
        );
        pnlMenuOpcionesLayout.setVerticalGroup(
            pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuOpcionesLayout.createSequentialGroup()
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgPaquetes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgCostos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImgCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMenuOpcionesLayout.createSequentialGroup()
                        .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 130, Short.MAX_VALUE))
        );

        getContentPane().add(pnlMenuOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 190, 550));

        pnlEncabezado.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Contratos");

        lblNombreUsuario.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblNombreUsuario.setText("NombreUsuario");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/locoIcon.png"))); // NOI18N

        btnMenuDesplegable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/3lineasBoton.png"))); // NOI18N
        btnMenuDesplegable.setBorder(null);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.setMaximumSize(new java.awt.Dimension(45, 45));
        btnAgregar.setMinimumSize(new java.awt.Dimension(45, 45));

        javax.swing.GroupLayout pnlEncabezadoLayout = new javax.swing.GroupLayout(pnlEncabezado);
        pnlEncabezado.setLayout(pnlEncabezadoLayout);
        pnlEncabezadoLayout.setHorizontalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnMenuDesplegable, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, Short.MAX_VALUE)
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
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaContratos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnMenuDesplegable;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblContratos;
    private javax.swing.JLabel lblCostos;
    private javax.swing.JLabel lblImgCerrarSesion;
    private javax.swing.JLabel lblImgClientes;
    private javax.swing.JLabel lblImgContratos;
    private javax.swing.JLabel lblImgCostos;
    private javax.swing.JLabel lblImgMateriales;
    private javax.swing.JLabel lblImgPaquetes;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMateriales;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblPaquetes;
    private javax.swing.JLabel lblSesion;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlContratos;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlMenuOpciones;
    private javax.swing.JScrollPane scrollPaneContratos;
    // End of variables declaration//GEN-END:variables
}
