package org.itson.sof.sof_level_presentacion.interfaces;

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
import org.itson.sof.objetosnegocios.gestorcontratos.GestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.IGestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorcontratosexception.GestorContratoException;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellEditor;
import org.itson.sof.sof_level_presentacion.componentes.TableActionCellRender;
import org.itson.sof.sof_level_presentacion.componentes.TableActionEvent;

/**
 *
 * @author JazmE
 */
public class PanelClientes extends javax.swing.JPanel {

    private static final int COLUMNA_NOMBRE = 0;
    private static final int COLUMNA_CORREO = 1;
    private static final int COLUMNA_TELEFONO = 2;
    private static final int COLUMNA_ACCIONES = 3;

    private IGestorClientes gestorClientes;
     private IGestorContratos gestorContratos;
    private final PantallaPrincipal principal;
    private boolean inicializado = false;
    private List<ClienteDTO> clientesTotales = new LinkedList<>();

    public PanelClientes(PantallaPrincipal principal) {
        this.principal = principal;
        gestorClientes = GestorClientes.getInstance();
        gestorContratos = GestorContratos.getInstance();
    }

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            filtroBusquedaGeneral(txtBuscador);
            inicializado = true;
        }
        clientesTotales = obtenerClientes();
        cargarClientesEnTabla(clientesTotales);
    }

    private List<ClienteDTO> obtenerClientes() {
        try {
            return gestorClientes.obtenerTodosClientes();
        } catch (GestorClientesException ex) {
            Logger.getLogger(PanelClientes.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<>();
        }
    }

    private void cargarClientesEnTabla(List<ClienteDTO> listaClientes) {
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);

        for (ClienteDTO cliente : listaClientes) {
            model.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                null
            });
        }

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEditar(int row) {
                String correo = (String) tblClientes.getValueAt(row, COLUMNA_CORREO);
                try {
                    ClienteDTO cliente = gestorClientes.obtenerCliente(correo);
                    DialogCliente dc = new DialogCliente(null, true, cliente);
                    dc.setVisible(true);

                    if (dc.isEdicionRealizada()) {
                        clientesTotales = obtenerClientes();
                        cargarClientesEnTabla(clientesTotales);
                        JOptionPane.showMessageDialog(null, "Cliente editado con éxito.");
                    }
                } catch (GestorClientesException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener cliente: " + ex.getMessage());
                }
            }

            @Override
            public void onEliminar(int row) {
                String correo = (String) tblClientes.getValueAt(row, COLUMNA_CORREO);
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Estás seguro de que deseas cancelar este cliente?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (opcion == JOptionPane.YES_OPTION) {
                    try {
                        gestorClientes.cancelarCliente(correo);
                        clientesTotales.removeIf(c -> c.getCorreo().equals(correo));
                        cargarClientesEnTabla(clientesTotales);
                        gestorContratos.cancelarContratosCliente(correo);
                        JOptionPane.showMessageDialog(null, "Cliente cancelado con éxito.");
                    } catch (GestorClientesException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cancelar: " + ex.getMessage());
                    } catch (GestorContratoException ex) {
                        JOptionPane.showMessageDialog(null, "No se pudieron cancelar los contratos: " + ex.getMessage());
                    }
                }
            }
        };

        tblClientes.getColumnModel().getColumn(COLUMNA_ACCIONES).setCellRenderer(new TableActionCellRender());
        tblClientes.getColumnModel().getColumn(COLUMNA_ACCIONES).setCellEditor(new TableActionCellEditor(event));
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
    
    private void agregarCliente(){
        DialogCliente dc = new DialogCliente(null, true);
        dc.setVisible(true);

        if (dc.isEdicionRealizada()) {
            try {
                clientesTotales = GestorClientes.getInstance().obtenerTodosClientes();
                cargarClientesEnTabla(clientesTotales);
                JOptionPane.showMessageDialog(null, "Cliente agregado con éxito.");
            } catch (GestorClientesException ex) {
                Logger.getLogger(PanelClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void buscadorKey(){
        String texto = txtBuscador.getText().toLowerCase();
        List<ClienteDTO> filtrados = new LinkedList<>();

        for (ClienteDTO cliente : clientesTotales) {
            if (cliente.getNombre().toLowerCase().contains(texto)
                    || cliente.getCorreo().toLowerCase().contains(texto)
                    || cliente.getTelefono().toLowerCase().contains(texto)) {
                filtrados.add(cliente);
            }
        }

        cargarClientesEnTabla(filtrados);
    }

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {
        // tu lógica aquí
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscador = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();
        lblGenerar = new javax.swing.JLabel();

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

        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyReleased(evt);
            }
        });

        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        lblGenerar.setText("Agregar Cliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGenerar)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnGenerar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(240, 240, 240))))
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
                        .addComponent(btnGenerar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblGenerar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyReleased
        buscadorKey();
    }//GEN-LAST:event_txtBuscadorKeyReleased

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        agregarCliente();
    }//GEN-LAST:event_btnGenerarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGenerar;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
