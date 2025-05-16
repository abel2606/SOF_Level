package org.itson.sof.sof_level_presentacion.interfaces;

import Deprecated.PantallaClientes;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.itson.sof.objetosnegocios.gestorclientes.GestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.IGestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellEditor;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellRender;
import org.itson.sof.sof_level_presentacion.componentes.TableActionEvent;

/**
 *
 * @author JazmE
 */
public class PanelClientes extends javax.swing.JPanel {

    IGestorClientes gestor;
    private final PantallaPrincipal principal;
    private boolean inicializado = false;

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            filtroBusquedaGeneral(txtBuscador);

            inicializado = true;
        }
        cargarClientesEnTabla(obtenerClientes());
    }

    
    /**
     * Creates new form PanelContratos
     *
     * @param principal
     */
    public PanelClientes(PantallaPrincipal principal) {
        this.principal = principal;
        gestor = GestorClientes.getInstance();
    }

    /**
     * Metodo que se llama cuando se hace click a un cliente
     *
     * @param cliente Cliente al que se le hizo click
     */
    private void manejarClicEnCliente(ClienteDTO cliente) {
        //Notificar el cambio de panel
        //principal.setCliente(cliente);
        //principal.PanelCliente();
    }

    private List<ClienteDTO> obtenerClientes() {
        List<ClienteDTO> clientes = new LinkedList<>();

        try {
            clientes = gestor.obtenerTodosClientes();
        } catch (GestorClientesException ex) {
            Logger.getLogger(PantallaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    private void cargarClientesEnTabla(List<ClienteDTO> listaClientes) {
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);
        for (ClienteDTO cliente : listaClientes) {
            model.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCorreo(),});
            TableActionEvent event = new TableActionEvent() {
                @Override
                public void onEditar(int row) {
                    String correo = (String) tblClientes.getValueAt(row, 2);
                    try {
                        ClienteDTO cliente = GestorClientes.getInstance().obtenerCliente(correo);
                        DialogCliente dc = new DialogCliente(null, true, cliente);
                        dc.setVisible(true);

                        if (dc.isEdicionRealizada()) {
                            List<ClienteDTO> clientesActualizados = GestorClientes.getInstance().obtenerTodosClientes();
                            cargarClientesEnTabla(clientesActualizados);
                            JOptionPane.showMessageDialog(null, "Cliente editado con éxito.");
                        }
                    } catch (GestorClientesException ex) {
                        JOptionPane.showMessageDialog(null, "Error al obtener cliente: " + ex.getMessage());
                    }

                }

                @Override
                public void onEliminar(int row) {
                    System.out.println("Eliminar columna: " + row + cliente.getCorreo());
                    int opcion = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estás seguro de que deseas eliminar este cliente?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (tblClientes.isEditing()) {
                            tblClientes.getCellEditor().stopCellEditing();
                        }

                        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
                        String correo = (String) model.getValueAt(row, 2);

                        try {
                            boolean eliminado = GestorClientes.getInstance().eliminarCliente(correo);
                            if (eliminado) {
                                model.removeRow(row);
                                JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito.");
                            }
                        } catch (GestorClientesException ex) {
                            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                    }

                }
            };
            tblClientes.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
            tblClientes.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
        }

    }

    public void filtroBusquedaGeneral(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            // Solo letras, números, espacio, @ . _ -
            Pattern regEx = Pattern.compile("[a-zA-Z0-9@._\\- ]*");

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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscador = new javax.swing.JTextField();
        btnAgregarCliente = new org.itson.sof.sof_level_presentacion.componentes.BotonIcono();

        setBackground(new java.awt.Color(220, 240, 255));

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("(Por nombre, e-mail, teléfono)");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Buscar");

        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyReleased(evt);
            }
        });

        btnAgregarCliente.setBackground(new java.awt.Color(220, 240, 255));
        btnAgregarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addClienteIcon.png"))); // NOI18N
        btnAgregarCliente.setColor(new java.awt.Color(220, 240, 255));
        btnAgregarCliente.setColorClick(new java.awt.Color(220, 240, 255));
        btnAgregarCliente.setColorOver(new java.awt.Color(220, 240, 255));
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 333, Short.MAX_VALUE)
                        .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscador))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        DialogCliente dc = new DialogCliente(null, true);
        dc.setVisible(true);

        if (dc.isEdicionRealizada()) {
            List<ClienteDTO> clienteAgregado = new LinkedList<>();
            try {
                clienteAgregado = GestorClientes.getInstance().obtenerTodosClientes();
            } catch (GestorClientesException ex) {
                Logger.getLogger(PanelClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            cargarClientesEnTabla(clienteAgregado);
            JOptionPane.showMessageDialog(null, "Cliente agregado con éxito.");
        }
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void txtBuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyReleased
        //Aquí hare el buscador
    }//GEN-LAST:event_txtBuscadorKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.itson.sof.sof_level_presentacion.componentes.BotonIcono btnAgregarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
