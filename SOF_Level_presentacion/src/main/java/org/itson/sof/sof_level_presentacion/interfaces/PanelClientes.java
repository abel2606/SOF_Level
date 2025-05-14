
package org.itson.sof.sof_level_presentacion.interfaces;

import Deprecated.PantallaClientes;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
        gestor=GestorClientes.getInstance();
    }

    /**
     * Metodo que se llama cuando se hace click a un cliente
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    // End of variables declaration//GEN-END:variables
}
