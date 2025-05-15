package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.IGestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.objetosnegocios.gestorclientes.GestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.IGestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.objetosnegocios.gestorcostos.GestorCostos;
import org.itson.sof.objetosnegocios.gestorcostos.IGestorCostos;
import org.itson.sof.objetosnegocios.gestorcostos.gestorexception.GestorCostosException;
import reportes.ReporteVenta;

/**
 *
 * @author JazmE
 */
public class DialogReporte extends javax.swing.JDialog {
    Frame parent;
    IGestorCostos gestorCostos;
    IGestorCitas gestorCitas;
    IGestorClientes gestorClientes;
    List<ClienteDTO> clientesSeleccionados= new ArrayList<>();
    private DefaultListModel<String> listModel;
    private JList<String> suggestionList;
    private List<ClienteDTO> clientes = new ArrayList<>();

    public DialogReporte(java.awt.Frame parent, boolean modal, String ruta) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        gestorCostos = GestorCostos.getInstance();
        gestorCitas = GestorCitas.getInstance();
        gestorClientes = GestorClientes.getInstance();
        this.txtaUbicacion.setText(ruta);
        this.txtaNombre.setText("Reporte de ventas");
        Date hoy = new Date();
        this.cmbFechaFin.setDate(hoy);
        Calendar inicio = Calendar.getInstance();
        inicio.set(Calendar.MONTH, Calendar.JANUARY);
        inicio.set(Calendar.DAY_OF_MONTH, 1);
        this.cmbFechaInicio.setDate(inicio.getTime());
        
        configurarAutocompletado();
    }
    
    private void SeleccionarRuta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opcion = chooser.showOpenDialog(parent);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File carpeta = chooser.getSelectedFile();
            txtaUbicacion.setText(carpeta.getAbsolutePath());
            escribirRuta(carpeta.getAbsolutePath());
        }
    }

    private void Aceptar() {
        String nombre = txtaNombre.getText().trim();
        String ubicacion = txtaUbicacion.getText().trim();
        
        // Validar campos vacíos
        if (nombre.isEmpty() || ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Favor de llenar todos los campos.");
            return;
        }

        // Verificar caracteres inválidos para nombres de archivos en Windows
        if (!nombre.matches("^[^\\\\/:*?\"<>|]+$")) {
            JOptionPane.showMessageDialog(parent, "El nombre no debe contener caracteres inválidos: \\ / : * ? \" < > |");
            return;
        }

        // Validar ubicación válida
        File archivo = new File(ubicacion);
        if (!archivo.exists() || !archivo.isDirectory()) {
            JOptionPane.showMessageDialog(parent, "La ubicación no es válida.");
            return;
        }

        // Validar que el nombre no exista ya en esa ubicación
        File posibleArchivo = new File(archivo, nombre);
        if (posibleArchivo.exists()) {
            JOptionPane.showMessageDialog(parent, "Ya existe un elemento con ese nombre en la ubicación indicada.");
            return;
        }

        //Validar fechas validas (Primero la de inicio luego la de despues, formato correcto, ambas fechas no vacias,
        // ambas fechas ya hayan ocurrido)
        Date fechaInicio = cmbFechaInicio.getDate();
        Date fechaFin = cmbFechaFin.getDate();
        Date hoy = new Date();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Ambas fechas deben estar seleccionadas.");
            return;
        }

        if (fechaInicio.after(hoy) || fechaFin.after(hoy)) {
            JOptionPane.showMessageDialog(null, "Las fechas no pueden ser futuras.");
            return;
        }

        if (!fechaInicio.before(fechaFin)) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio debe ser anterior a la fecha final.");
            return;
        }

        //Al menos un cliente ingresado o hecho click en seleccionar todos los clientes
        if (tblCliente.getRowCount() > 0 || this.boxCilientes.isSelected()) {
        } else {
            JOptionPane.showMessageDialog(parent, "Debes ingresar al menos un cliente");
            return;
        }
        GenerarReporte();
    }

    private void Cancelar() {
        this.dispose();
    }

    private void GenerarReporte() {
        String nombre = txtaNombre.getText().trim();
        String ubicacion = txtaUbicacion.getText().trim();
        ReporteVenta reporteVenta = new ReporteVenta(nombre, ubicacion);

        Date fechaInicioDate = cmbFechaInicio.getDate();
        Date fechaFinDate = cmbFechaFin.getDate();

        GregorianCalendar fechaInicio = null;
        GregorianCalendar fechaFin = null;

        if (fechaInicioDate != null) {
            fechaInicio = new GregorianCalendar();
            fechaInicio.setTime(fechaInicioDate);
        }

        if (fechaFinDate != null) {
            fechaFin = new GregorianCalendar();
            fechaFin.setTime(fechaFinDate);
        }

        boolean todosClientes = this.boxCilientes.isSelected();
        List<ContratoDTO> contratos = new ArrayList<>();
        
        if (todosClientes) {
            try {
                contratos = gestorCitas.obtenerContratos();
            } catch (GestorCitasException ex) {
                JOptionPane.showMessageDialog(parent, ex);
            return;
            }
        } else {
            for (ClienteDTO cliente : clientesSeleccionados) {//Obtener todos los contratos de cada cliente
                try {
                    List<ContratoDTO> contratosCliente = gestorCitas.obtenerContratosPorCliente(cliente);
                    contratos.addAll(contratosCliente);
                } catch (GestorCitasException ex) {
                    Logger.getLogger(DialogReporte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        boolean terminados = this.boxTerminados.isSelected();

        if (terminados) {
            contratos = contratos.stream()
                    .filter(c -> c.getEstado() != null && c.getEstado().equalsIgnoreCase("Terminado"))
                    .collect(Collectors.toList());
        }

        try {
            gestorCostos.generarReporte(fechaInicio, fechaFin, reporteVenta, contratos);
            gestorCostos.abrirReporte(ubicacion);
            this.dispose();
        } catch (GestorCostosException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage());
        }
    }
    
    private void configurarAutocompletado() {
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setPreferredSize(new Dimension(180, 100));
        this.popMenu.add(scrollPane);

        try {
            clientes = gestorClientes.obtenerTodosClientes();
        } catch (GestorClientesException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage());
            return;
        }
        
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        this.tblCliente.setModel(tableModel);
        cargarClientesEnTabla(tableModel);
        
        txtNombreCliente.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNombreCliente.getText().equals("Ingrese el Cliente")) {
                    txtNombreCliente.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombreCliente.getText().isEmpty()) {
                    txtNombreCliente.setText("Ingrese el Cliente");
                }
            }
        });
        this.txtNombreCliente.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizarLista();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizarLista();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                actualizarLista();
            }
            
            private void actualizarLista() {
                String input = txtNombreCliente.getText().trim().toLowerCase();
                listModel.clear();
                if (!input.isEmpty()) {
                    for (ClienteDTO item : clientes) {
                        if (item.getNombre().toLowerCase().contains(input)) {
                            listModel.addElement(item.getNombre());
                        }
                    }
                    
                    if (!listModel.isEmpty()) {
                        SwingUtilities.invokeLater(() -> {
                            if (!popMenu.isVisible()) {
                                popMenu.show(txtNombreCliente, 0, txtNombreCliente.getHeight());
                            }
                        });
                    } else {
                        popMenu.setVisible(false);
                    }
                } else {
                    popMenu.setVisible(false);
                }
            }
        });
        // Selección de clientes desde la lista de autocompletar
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && suggestionList.getSelectedValue() != null) {
                    txtNombreCliente.setText(suggestionList.getSelectedValue());
                    popMenu.setVisible(false);
                }
            }
        });
        // Evento de tecla ENTER para selección
        suggestionList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && suggestionList.getSelectedValue() != null) {
                    txtNombreCliente.setText(suggestionList.getSelectedValue());
                    popMenu.setVisible(false);
                }
            }
        });
        // Botón para agregar Cliente a la tabla y a la lista de la cita
        this.btnAgregar.addActionListener(e -> {
            String nombre = txtNombreCliente.getText().trim();
            
            if (nombre.isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ClienteDTO seleccionado = clientes.stream()
                    .filter(mat -> mat.getNombre().equalsIgnoreCase(nombre))
                    .findFirst()
                    .orElse(null);
            
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean yaExiste = false;
            for (int i = 0; i < clientesSeleccionados.size(); i++) {
                ClienteDTO existente = clientesSeleccionados.get(i);
                if (existente.getNombre().equalsIgnoreCase(nombre)) {
                    yaExiste = true;
                    break;
                }
            }
            
            if (!yaExiste) {
                ClienteDTO copia = new ClienteDTO();
                copia.setNombre(seleccionado.getNombre());
                
                clientesSeleccionados.add(copia);
                ((DefaultTableModel) this.tblCliente.getModel()).addRow(new Object[]{nombre});
            }

            this.txtNombreCliente.setText("");
        });
        tblCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tblCliente.getSelectedRow();

                if (filaSeleccionada >= 0) {
                    String nombre = (String) tblCliente.getValueAt(filaSeleccionada, 0);

                    // Eliminar de la lista clientesSeleccionados
                    clientesSeleccionados.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));

                    // Eliminar fila de la tabla
                    ((DefaultTableModel) tblCliente.getModel()).removeRow(filaSeleccionada);
                }
            }
        });
    }
    
    private void cargarClientesEnTabla(DefaultTableModel tableModel) {
        for (ClienteDTO cliente : clientesSeleccionados) {
            tableModel.addRow(new Object[]{cliente.getNombre()});
        }
    }
    
    private String escribirRuta(String nuevaRuta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ruta.txt"))) {
            bw.write(nuevaRuta);
            return "Ruta escrita correctamente";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al escribir la ruta";
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu = new javax.swing.JPopupMenu();
        pnlPrincipal = new javax.swing.JPanel();
        txtTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaUbicacion = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaNombre = new javax.swing.JTextArea();
        lblUbicacion = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        lblTerminados = new javax.swing.JLabel();
        boxTerminados = new javax.swing.JCheckBox();
        btnGenerar = new javax.swing.JButton();
        lblTodosClientes = new javax.swing.JLabel();
        boxCilientes = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        lblClientes = new javax.swing.JLabel();
        cmbFechaInicio = new com.toedter.calendar.JDateChooser();
        cmbFechaFin = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlPrincipal.setBackground(new java.awt.Color(220, 240, 255));

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtTitulo.setText("Generar Reporte");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");

        lblPeriodo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPeriodo.setText("Periodo:");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaUbicacion.setColumns(20);
        txtaUbicacion.setRows(5);
        txtaUbicacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtaUbicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtaUbicacionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtaUbicacion);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaNombre.setColumns(20);
        txtaNombre.setRows(5);
        txtaNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtaNombre);

        lblUbicacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUbicacion.setText("Ruta:");

        txtNombreCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombreCliente.setText("Ingrese Cliente");
        txtNombreCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
        });
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cliente", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblCliente);

        lblTerminados.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTerminados.setText("Solo contratos terminados*");

        boxTerminados.setMaximumSize(new java.awt.Dimension(40, 40));
        boxTerminados.setMinimumSize(new java.awt.Dimension(40, 40));
        boxTerminados.setPreferredSize(new java.awt.Dimension(40, 40));

        btnGenerar.setBackground(new java.awt.Color(36, 160, 108));
        btnGenerar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGenerar.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerar.setText("Generar");
        btnGenerar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGenerar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarMouseClicked(evt);
            }
        });
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        lblTodosClientes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTodosClientes.setText("Agregar todos los clientes*");

        boxCilientes.setMaximumSize(new java.awt.Dimension(40, 40));
        boxCilientes.setMinimumSize(new java.awt.Dimension(40, 40));
        boxCilientes.setPreferredSize(new java.awt.Dimension(40, 40));

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
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblClientes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClientes.setText("Clientes:");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTitulo)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(lblPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblClientes)
                                .addGap(32, 32, 32)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregar))
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(lblTodosClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boxCilientes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(lblTerminados)
                                .addGap(18, 18, 18)
                                .addComponent(boxTerminados, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82))
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(txtTitulo)
                        .addGap(38, 38, 38)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblUbicacion))
                .addGap(40, 40, 40)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(lblPeriodo)
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                        .addComponent(cmbFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50)
                                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(boxTerminados, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblTerminados)))
                                    .addComponent(cmbFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblClientes))
                                    .addComponent(btnAgregar))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTodosClientes)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(boxCilientes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked

    }//GEN-LAST:event_btnAgregarMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGenerarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarMouseClicked
        
    }//GEN-LAST:event_btnGenerarMouseClicked

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        Aceptar();
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtaUbicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtaUbicacionMouseClicked
       SeleccionarRuta();
    }//GEN-LAST:event_txtaUbicacionMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxCilientes;
    private javax.swing.JCheckBox boxTerminados;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGenerar;
    private com.toedter.calendar.JDateChooser cmbFechaFin;
    private com.toedter.calendar.JDateChooser cmbFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblTerminados;
    private javax.swing.JLabel lblTodosClientes;
    private javax.swing.JLabel lblUbicacion;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JLabel txtTitulo;
    private javax.swing.JTextArea txtaNombre;
    private javax.swing.JTextArea txtaUbicacion;
    // End of variables declaration//GEN-END:variables
}
