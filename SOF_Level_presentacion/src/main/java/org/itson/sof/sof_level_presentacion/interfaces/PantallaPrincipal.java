package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author JazmE
 */
public class PantallaPrincipal extends javax.swing.JFrame {
    private final CardLayout cardLayout;
    private final PanelContratos pnlContratos;
    private final PanelContrato pnlContrato;
    private final PanelCostos pnlCostos;
    private final PanelClientes pnlClientes;
    private boolean menuVisible = true;
    private ContratoDTO contrato;
    
    private final Color colorMarcado=new Color(0,146,236);
    private final ImageIcon imgContratoNegro;
    private final ImageIcon imgContratoAzul;
    private final ImageIcon imgClientesNegro;
    private final ImageIcon imgClientesAzul;
    private final ImageIcon imgCostosNegro;
    private final ImageIcon imgCostosAzul;
    
    
    private static PantallaPrincipal instancia;
    

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
        cardLayout = new CardLayout();
        pnlCentral.setLayout(cardLayout);

        // Agregar paneles al pnlCentral
        pnlContratos = new PanelContratos(this);
        pnlContrato = new PanelContrato(this);
        pnlCostos = new PanelCostos(this);
        pnlClientes = new PanelClientes(this);

        pnlCentral.add(pnlContratos, "PanelContratos");
        pnlCentral.add(pnlCostos, "PanelCostos");
        pnlCentral.add(pnlClientes, "PanelClientes");
        pnlCentral.add(pnlContrato, "PanelContrato");

        ImageIcon imgPaqueteGris = loadIcon("/imagenes/Photo albumGray.png", 45, 45);
        ImageIcon imgMaterialGris = loadIcon("/imagenes/GroupGray.png", 45, 45);

        this.lblImgPaquetes.setIcon(imgPaqueteGris);
        this.lblPaquetes.setForeground(Color.gray);
        this.lblImgMateriales.setIcon(imgMaterialGris);
        this.lblMateriales.setForeground(Color.gray);

        imgContratoNegro = loadIcon("/imagenes/Contract.png", 45, 45);
        imgContratoAzul = loadIcon("/imagenes/contratoAzulIcon.png", 45, 45);
        this.lblImgContratos.setIcon(imgContratoAzul);

        imgClientesNegro = loadIcon("/imagenes/clienteIcon.png", 45, 45);
        imgClientesAzul = loadIcon("/imagenes/PersonAzul.png", 45, 45);
        this.lblImgClientes.setIcon(imgClientesNegro);

        imgCostosNegro = loadIcon("/imagenes/costosIcon.png", 25, 45);
        imgCostosAzul = loadIcon("/imagenes/costosIconAzul.png", 25, 45);
        this.lblImgCostos.setIcon(imgCostosNegro);

        // Mostrar la pantalla por defecto
        PanelContratos();
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        URL url = getClass().getResource(path);
        if (url != null) {
            ImageIcon originalIcon = new ImageIcon(url);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.out.println("Imagen no encontrada: " + path);
            return null;
        }
    }


    public static PantallaPrincipal getInstance() {
        if (instancia == null) {
            instancia = new PantallaPrincipal();
        }
        return instancia;
    }
    
    private void colorearNegroIconos(){
        this.lblContratos.setForeground(Color.black);
        this.lblClientes.setForeground(Color.black);
        this.lblCostos.setForeground(Color.black);
        
        this.lblImgCostos.setIcon(imgCostosNegro);
        this.lblImgContratos.setIcon(imgContratoNegro);
        this.lblImgClientes.setIcon(imgClientesNegro);
    }

    /**
     * Cambia el contenido del frame interior al menu de contratos
     */
    public void PanelContratos(){
        colorearNegroIconos();
        this.lblImgContratos.setIcon(imgContratoAzul);
        this.lblContratos.setForeground(colorMarcado);
        this.lblTitulo.setText("Contratos");
        pnlContratos.inicializar();
        cardLayout.show(pnlCentral, "PanelContratos");
    }
    
    protected void crearContrato(){
        this.contrato=null;
        PanelContrato();
    }
    
    /**
     * Cambia el contenido del frame interior al menu de contratos
     */
    public void PanelContrato(){
        this.lblTitulo.setText("Contrato");
        pnlContrato.contrato = this.contrato;
        pnlContrato.inicializar();
        cardLayout.show(pnlCentral, "PanelContrato");
    }
    
    /**
     * Cambia el contenido del frame interior al menu de clientes
     */
    public void PanelClientes(){
        colorearNegroIconos();
        this.lblClientes.setForeground(colorMarcado);
        this.lblImgClientes.setIcon(imgClientesAzul);
        this.lblTitulo.setText("Clientes");
        pnlClientes.inicializar();
        cardLayout.show(pnlCentral, "PanelClientes");
    }
    
    /**
     * Cambia el contenido del frame interior al menu de costos
     */
    public void PanelCostos(){
        colorearNegroIconos();
        this.lblCostos.setForeground(colorMarcado);
        this.lblImgCostos.setIcon(imgCostosAzul);
        this.lblTitulo.setText("Reportes de venta");
        pnlCostos.inicializar();
        cardLayout.show(pnlCentral, "PanelCostos");
    }
    
    /**
     * Cierra sesion
     */
    private void CerrarSesion(boolean mostrarDialogo, String mensaje) {
        if (mostrarDialogo) {
            JOptionPane.showMessageDialog(this, mensaje);
        }
        System.exit(0);  // Finaliza el programa
    }

    public ContratoDTO getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    } 
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCentral = new javax.swing.JPanel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCentral.setBackground(new java.awt.Color(220, 240, 255));

        javax.swing.GroupLayout pnlCentralLayout = new javax.swing.GroupLayout(pnlCentral);
        pnlCentral.setLayout(pnlCentralLayout);
        pnlCentralLayout.setHorizontalGroup(
            pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        pnlCentralLayout.setVerticalGroup(
            pnlCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        getContentPane().add(pnlCentral, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 850, 550));

        pnlMenuOpciones.setBackground(new java.awt.Color(255, 255, 255));

        lblContratos.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblContratos.setForeground(new java.awt.Color(0, 146, 236));
        lblContratos.setText("Contratos");
        lblContratos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblContratosMouseClicked(evt);
            }
        });

        lblImgContratos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contratoAzulIcon.png"))); // NOI18N
        lblImgContratos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgContratosMouseClicked(evt);
            }
        });

        lblImgPaquetes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paqueteICon.png"))); // NOI18N

        lblPaquetes.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPaquetes.setText("Paquetes");

        lblClientes.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblClientes.setText("Clientes");
        lblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClientesMouseClicked(evt);
            }
        });

        lblImgClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clienteIcon.png"))); // NOI18N
        lblImgClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgClientesMouseClicked(evt);
            }
        });

        lblImgMateriales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/materialesIcon.png"))); // NOI18N

        lblMateriales.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblMateriales.setText("Materiales");

        lblImgCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/costosIcon.png"))); // NOI18N
        lblImgCostos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgCostosMouseClicked(evt);
            }
        });

        lblCostos.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCostos.setText("Costos");
        lblCostos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCostosMouseClicked(evt);
            }
        });

        lblImgCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarSesionIcon.png"))); // NOI18N
        lblImgCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgCerrarSesionMouseClicked(evt);
            }
        });

        lblCerrar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCerrar.setText("Cerrar");
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });

        lblSesion.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblSesion.setText("sesión");
        lblSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSesionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuOpcionesLayout = new javax.swing.GroupLayout(pnlMenuOpciones);
        pnlMenuOpciones.setLayout(pnlMenuOpcionesLayout);
        pnlMenuOpcionesLayout.setHorizontalGroup(
            pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuOpcionesLayout.createSequentialGroup()
                .addGroup(pnlMenuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuOpcionesLayout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
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
                        .addGap(0, 9, Short.MAX_VALUE))
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
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo.setText("Contratos");

        lblNombreUsuario.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblNombreUsuario.setText("NombreUsuario");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/locoIcon.png"))); // NOI18N

        javax.swing.GroupLayout pnlEncabezadoLayout = new javax.swing.GroupLayout(pnlEncabezado);
        pnlEncabezado.setLayout(pnlEncabezadoLayout);
        pnlEncabezadoLayout.setHorizontalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap(891, Short.MAX_VALUE)
                .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        pnlEncabezadoLayout.setVerticalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        getContentPane().add(pnlEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 120));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblContratosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContratosMouseClicked
        PanelContratos();
    }//GEN-LAST:event_lblContratosMouseClicked

    private void lblImgContratosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgContratosMouseClicked
        PanelContratos();
    }//GEN-LAST:event_lblImgContratosMouseClicked

    private void lblImgCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgCerrarSesionMouseClicked
        CerrarSesion(false,"");
    }//GEN-LAST:event_lblImgCerrarSesionMouseClicked

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        CerrarSesion(false,"");
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void lblSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSesionMouseClicked
        CerrarSesion(false,"");
    }//GEN-LAST:event_lblSesionMouseClicked

    private void lblCostosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCostosMouseClicked
        PanelCostos();
    }//GEN-LAST:event_lblCostosMouseClicked

    private void lblImgCostosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgCostosMouseClicked
        PanelCostos();
    }//GEN-LAST:event_lblImgCostosMouseClicked

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        PanelClientes();
    }//GEN-LAST:event_lblClientesMouseClicked

    private void lblImgClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgClientesMouseClicked
        PanelClientes();
    }//GEN-LAST:event_lblImgClientesMouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JPanel pnlCentral;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlMenuOpciones;
    // End of variables declaration//GEN-END:variables
}
