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
        gestionarClientes = GestorClientes.getInstance();
        editando = false;
        btnEditar.setVisible(false);
        txtNombre.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtCelular.setEnabled(true);
        soloNumeros(txtCelular);
        soloTexto(txtNombre);
        soloCorreo(txtCorreo);
    }

    public DialogCliente(java.awt.Frame parent, boolean modal, ClienteDTO cliente) {
        super(parent, modal);
        initComponents();
        editando = true;
        gestionarClientes = GestorClientes.getInstance();

        clienteDTO = cliente;

        txtNombre.setText(clienteDTO.getNombre());
        txtCorreo.setText(clienteDTO.getCorreo());
        txtCelular.setText(clienteDTO.getTelefono());

        txtNombre.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtCelular.setEnabled(false);
        btnEditar.setVisible(true);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Celular:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cliente");

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

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

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
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEditar)))
                .addGap(40, 40, 40)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean validarCampos(String nombre, String correo, String celular) {
        // Validar nombre: solo letras y espacios
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            javax.swing.JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y espacios.");
            return false;
        }

        // Validar correo con expresión regular simple
        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            javax.swing.JOptionPane.showMessageDialog(this, "El correo no es válido.");
            return false;
        }

        // Validar celular: exactamente 10 dígitos
        if (!celular.matches("\\d{10}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "El número de celular debe contener exactamente 10 dígitos.");
            return false;
        }

        return true;
    }

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

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
        if (editando == true) {
            try {
                cliente = gestionarClientes.editarCliente(clienteDTO.getCorreo(), cliente);
                setEdicionRealizada(true);

                dispose();
            } catch (GestorClientesException ex) {
                JOptionPane.showMessageDialog(null, "Número de teléfonon o correo existente");
            }
        } else {
            try {
                cliente = gestionarClientes.agregarCliente(cliente);
                setEdicionRealizada(true);
                dispose();
            } catch (GestorClientesException ex) {
                JOptionPane.showMessageDialog(null, "Número de teléfonon o correo existente");
            }

        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        txtCelular.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtNombre.setEnabled(true);
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
