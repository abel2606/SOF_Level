/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package org.itson.sof.sof_level_presentacion.interfaces;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.itson.sof.objetosnegocios.gestorclientes.IGestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.objetosnegocios.gestorclientes.GestorClientes;
import org.itson.sof.sof_dtos.ClienteDTO;

/**
 *
 * @author Abe
 */
public class DialogCliente extends javax.swing.JDialog {

    private ClienteDTO clienteDTO;
    private IGestorClientes gestionarClientes;
    private boolean editando;
    private boolean edicionRealizada = false;

    /**
     * Creates new form DialogCliente
     */
    public DialogCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        gestionarClientes = GestorClientes.getInstance();
        editando = false;
        lblEdit.setVisible(false);
        txtNombre.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtCelular.setEnabled(true);
        soloNumeros(txtCelular);
        soloTexto(txtNombre);
        soloCorreo(txtCorreo);
        
        this.setTitle("Agregar cliente");
    }

    public DialogCliente(java.awt.Frame parent, boolean modal, ClienteDTO cliente) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
        
        if(cliente==null){
            this.setTitle("Agregar cliente");
        }else{
            this.setTitle("Consultar cliente");
        }
        
        editando = true;
        gestionarClientes = GestorClientes.getInstance();

        clienteDTO = cliente;

        txtNombre.setText(clienteDTO.getNombre());
        txtCorreo.setText(clienteDTO.getCorreo());
        txtCelular.setText(clienteDTO.getTelefono());

        txtNombre.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtCelular.setEnabled(false);
        lblEdit.setVisible(true);
        soloNumeros(txtCelular);
        soloTexto(txtNombre);
        soloCorreo(txtCorreo);
    }

    public void soloTexto(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("[\\p{L} ]*");

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (regEx.matcher(text).matches()) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (regEx.matcher(text).matches()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public void soloCorreo(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            // Permite letras, números, @, punto, guion, guion bajo y más comunes para correos
            Pattern regEx = Pattern.compile("[a-zA-Z0-9@._-]*");

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (regEx.matcher(text).matches()) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (regEx.matcher(text).matches()) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public void soloNumeros(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if (!matcher.matches()) {
                    return;
                }

                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength - length + text.length();

                if (newLength <= 10) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if (!matcher.matches()) {
                    return;
                }

                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength + text.length();

                if (newLength <= 10) {
                    super.insertString(fb, offset, text, attr);
                }
            }
        });
    }

    public boolean isEdicionRealizada() {
        return edicionRealizada;
    }

    public void setEdicionRealizada(boolean edicionRealizada) {
        this.edicionRealizada = edicionRealizada;
    }
    
    private void guardar(){
        String nombre = "";
        String correo = "";
        String celular = "";

        if (txtNombre.getText() != null || txtNombre.getText().isEmpty()) {
            nombre = txtNombre.getText();
        }
        if (txtCorreo.getText() != null || txtCorreo.getText().isEmpty()) {
            correo = txtCorreo.getText();
        }
        if (txtCelular.getText() != null || txtCelular.getText().isEmpty()) {
            celular = txtCelular.getText();
        }

        if (!validarCampos(nombre, correo, celular)) {
            return;
        }
        ClienteDTO cliente = new ClienteDTO(nombre, celular, correo);
        try {
            if (editando) {
                cliente = gestionarClientes.editarCliente(clienteDTO.getCorreo(), cliente);
            } else {
                cliente = gestionarClientes.agregarCliente(cliente);
            }

            setEdicionRealizada(true);
            dispose();

        } catch (GestorClientesException ex) {
            // Errores de lógica de negocio (correo/telefono duplicado, etc.)
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de cliente", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Errores no controlados
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DialogCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        pnlColor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblEdit = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnCancelar1 = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(220, 240, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Celular:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Correo:");

        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });

        pnlColor.setBackground(new java.awt.Color(220, 240, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cliente");

        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pencilIcon.png"))); // NOI18N
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });

        jLabel5.setText("Ej: 6622790444");

        jLabel6.setText("Ej: level@gmail.com");

        jLabel7.setText("Ej: Juan Antonio");

        btnCancelar1.setBackground(new java.awt.Color(160, 36, 38));
        btnCancelar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar1.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar1.setText("Cancelar");
        btnCancelar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCancelar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelar1MouseClicked(evt);
            }
        });

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
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlColorLayout = new javax.swing.GroupLayout(pnlColor);
        pnlColor.setLayout(pnlColorLayout);
        pnlColorLayout.setHorizontalGroup(
            pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlColorLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblEdit)
                .addGap(36, 36, 36))
            .addGroup(pnlColorLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addGroup(pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlColorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        pnlColorLayout.setVerticalGroup(
            pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColorLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(58, 58, 58)
                .addComponent(jLabel6)
                .addGap(65, 65, 65)
                .addComponent(jLabel5)
                .addGap(70, 70, 70)
                .addGroup(pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addComponent(pnlColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 158, Short.MAX_VALUE))
            .addComponent(pnlColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean validarCampos(String nombre, String correo, String celular) {
        StringBuilder errores = new StringBuilder();

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            errores.append("- El nombre solo debe contener letras y espacios.\n");
        }

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errores.append("- El correo no tiene un formato válido.\n");
        }

        if (!celular.matches("\\d{10}")) {
            errores.append("- El celular debe tener exactamente 10 dígitos numéricos.\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this, errores.toString(), "Errores de validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }
    
    private void editar(){
        txtCelular.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtNombre.setEnabled(true);
    }

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        editar();
    }//GEN-LAST:event_lblEditMouseClicked

    private void btnCancelar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelar1MouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCancelar1MouseClicked

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        guardar();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JPanel pnlColor;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
