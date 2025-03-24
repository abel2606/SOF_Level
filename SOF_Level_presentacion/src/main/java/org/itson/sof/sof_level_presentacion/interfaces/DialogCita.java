package org.itson.sof.sof_level_presentacion.interfaces;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.CitaBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.FotografoBO;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil.DiferenciadorUtils;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.FotografoDTO;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author JazmE
 */
public class DialogCita extends javax.swing.JDialog {

    private List<MaterialDTO> materiales = new ArrayList<>();
    private List<MaterialDTO> materialesSeleccionados = new ArrayList<>();
    public static CitaDTO citaAgregada;
    GestorCitas gestor;
    CitaDTO cita;
    boolean editando = false;
    List<FotografoDTO> fotografos;
    Frame parent;

    private DefaultListModel<String> listModel;
    private JList<String> suggestionList;

    public DialogCita(java.awt.Frame parent, boolean modal, CitaDTO cita) {
        super(parent, modal);
        initComponents();

        gestor = GestorCitas.getInstance();
        this.parent = parent;
        this.cita = cita;

        configurarAutocompletado();

        inicializar();
    }

    private void configurarAutocompletado() {
    listModel = new DefaultListModel<>();
    suggestionList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(suggestionList);
    scrollPane.setPreferredSize(new Dimension(180, 100));

    jpopmMateriales.add(scrollPane);

        try {
            materiales = gestor.obtenerMateriales();
            if (cita != null) {
                materialesSeleccionados = gestor.obtenerMaterialesCita(cita);
                System.out.println(materialesSeleccionados.size());
            }

            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Nombre", "Cantidad"}, 0);
            tblMaterial.setModel(tableModel);

            cargarMaterialesEnTabla(tableModel);

            txtNombreMat.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
                    String input = txtNombreMat.getText().trim().toLowerCase();
                    listModel.clear();

                    if (!input.isEmpty()) {
                        for (MaterialDTO item : materiales) {
                            if (item.getNombre().toLowerCase().contains(input)) {
                                listModel.addElement(item.getNombre());
                            }
                        }

                        if (!listModel.isEmpty()) {
                            SwingUtilities.invokeLater(() -> {
                                if (!jpopmMateriales.isVisible()) {
                                    Point location = txtNombreMat.getLocationOnScreen();
                                    jpopmMateriales.show(txtNombreMat, 0, txtNombreMat.getHeight());
                                }
                            });
                        } else {
                            jpopmMateriales.setVisible(false);
                        }
                    } else {
                        jpopmMateriales.setVisible(false);
                    }
                }
            });

            // Selección de material desde la lista de autocompletar
            suggestionList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1 && suggestionList.getSelectedValue() != null) {
                        txtNombreMat.setText(suggestionList.getSelectedValue());
                        jpopmMateriales.setVisible(false);
                    }
                }
            });

            // Evento de tecla ENTER para selección
            suggestionList.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && suggestionList.getSelectedValue() != null) {
                        txtNombreMat.setText(suggestionList.getSelectedValue());
                        jpopmMateriales.setVisible(false);
                    }
                }
            });

            // Botón para agregar material a la tabla y a la lista de la cita
            this.btnAgregar.addActionListener(e -> {
                String nombre = txtNombreMat.getText().trim();
                String cantidadTexto = txtCantidad.getText().trim();

                if (nombre.isEmpty() || cantidadTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar nombre y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float cantidad;
                try {
                    cantidad = Float.parseFloat(cantidadTexto);
                    if (cantidad <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                MaterialDTO seleccionado = materiales.stream()
                        .filter(mat -> mat.getNombre().equalsIgnoreCase(nombre))
                        .findFirst()
                        .orElse(null);

                if (seleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Material no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                materialesSeleccionados.add(seleccionado);

                tableModel.addRow(new Object[]{nombre, cantidad});

                txtNombreMat.setText("");
                txtCantidad.setText("");
            });

            // Eliminar material de la lista y la tabla con clic
            tblMaterial.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int filaSeleccionada = tblMaterial.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombre = (String) tableModel.getValueAt(filaSeleccionada, 0);
                        materialesSeleccionados.removeIf(mat -> mat.getNombre().equalsIgnoreCase(nombre));
                        tableModel.removeRow(filaSeleccionada);
                    }
                }
            });

        } catch (GestorException e) {
            e.printStackTrace();
        }
    }
    
    private void cargarMaterialesEnTabla(DefaultTableModel tableModel) {
        for (MaterialDTO material : materialesSeleccionados) {
            tableModel.addRow(new Object[]{material.getNombre(), material.getCantidad()});
        }
    }

    private void inicializar() {
        // Crear un modelo de SpinnerDateModel independiente para cada JSpinner
        SpinnerDateModel modeloSpinnerInicio = new SpinnerDateModel();
        SpinnerDateModel modeloSpinnerFin = new SpinnerDateModel();

        // Asignar el modelo a cada JSpinner
        this.sFechaInicio.setModel(modeloSpinnerInicio);
        this.sFechaFin.setModel(modeloSpinnerFin);

        // Configurar los editores para mostrar solo la hora
        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(sFechaInicio, "HH:mm");
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(sFechaFin, "HH:mm");

        // Establecer los editores para cada spinner
        sFechaInicio.setEditor(editorInicio);
        sFechaFin.setEditor(editorFin);

        // Establecer la fecha/hora actual como valor predeterminado
        Date horaActual = new Date();
        sFechaInicio.setValue(horaActual);
        sFechaFin.setValue(horaActual);

        AsignarFotografos();
        AsignarCita();

        if (cita == null) {
            this.lblDelete.setEnabled(false);
            this.lblEdit.setEnabled(false);
            editando = true;
            Calendar today = Calendar.getInstance(); 
            jcalendar.setCalendar(today);
        }
        HabilitarEditar();
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
            if (cita.getFechaHoraInicio() != null && cita.getFechaHoraFin() != null) {
                jcalendar.setCalendar(cita.getFechaHoraInicio());

                // Obtener la fecha de la cita
                GregorianCalendar fechaHoraInicio = cita.getFechaHoraInicio();
                GregorianCalendar fechaHoraFin = cita.getFechaHoraFin();

                // Crear una nueva fecha sin alterar el día, mes y año
                Date fechaInicioSinHora = fechaHoraInicio.getTime();
                Date fechaFinSinHora = fechaHoraFin.getTime();

                // Crear un nuevo objeto Calendar para la fecha de inicio
                Calendar calendarInicio = Calendar.getInstance();
                calendarInicio.setTime(fechaInicioSinHora); // Establecer solo la fecha (sin hora)

                // Crear un nuevo objeto Calendar para la fecha de fin
                Calendar calendarFin = Calendar.getInstance();
                calendarFin.setTime(fechaFinSinHora); // Establecer solo la fecha (sin hora)

                // Establecer la hora, minutos y segundos de la fecha de inicio
                calendarInicio.set(Calendar.HOUR_OF_DAY, fechaHoraInicio.get(Calendar.HOUR_OF_DAY));
                calendarInicio.set(Calendar.MINUTE, fechaHoraInicio.get(Calendar.MINUTE));
                calendarInicio.set(Calendar.SECOND, fechaHoraInicio.get(Calendar.SECOND));

                // Establecer la hora, minutos y segundos de la fecha de fin
                calendarFin.set(Calendar.HOUR_OF_DAY, fechaHoraFin.get(Calendar.HOUR_OF_DAY));
                calendarFin.set(Calendar.MINUTE, fechaHoraFin.get(Calendar.MINUTE));
                calendarFin.set(Calendar.SECOND, fechaHoraFin.get(Calendar.SECOND));

                // Establecer la nueva fecha con hora en los JSpinners
                this.sFechaInicio.setValue(calendarInicio.getTime());
                this.sFechaFin.setValue(calendarFin.getTime());
            }
        }
    }

    public void AsignarFotografos() {
        try {
            // Obtener la lista de fotógrafos
            fotografos = gestor.obtenerFotografos();

            // Limpiar el JComboBox antes de agregar nuevos elementos
            cbFotografo.removeAllItems();

            // Agregar los nombres de los fotógrafos al JComboBox
            for (FotografoDTO fotografo : fotografos) {
                cbFotografo.addItem(fotografo.getNombrePersona());
            }
        } catch (GestorException ex) {
            JOptionPane.showMessageDialog(parent, ex);
        }
    }

    public void Cancelar() {
        this.dispose();
    }

    public void Aceptar() {
        if (cita == null) {
            int respuesta = JOptionPane.showConfirmDialog(parent, "¿Desea agregar la cita?");

            if (respuesta == JOptionPane.OK_OPTION) {
                AgregarCita();
            }

        } else {
            if (editando) {
                int respuesta = JOptionPane.showConfirmDialog(parent, "¿Desea actualizar la cita?");

                if (respuesta == JOptionPane.OK_OPTION) {
                    EditarCita();
                }

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
            this.jcalendar.setEnabled(true);
            this.sFechaFin.setEnabled(true);
            this.sFechaInicio.setEnabled(true);
        } else {
            //Volver a colocar los valores originales de la cita
            AsignarCita();
            //Bloquear los campos
            this.txtaExtras.setEnabled(false);
            this.txtaLugar.setEnabled(false);
            this.cbFotografo.setEnabled(false);
            this.jcalendar.setEnabled(false);
            this.sFechaFin.setEnabled(false);
            this.sFechaInicio.setEnabled(false);
        }
    }

    private void BorrarCita() {
        int respuesta = JOptionPane.showConfirmDialog(parent, "¿Desea borrar la cita?");

        if (respuesta == JOptionPane.OK_OPTION) {
            try {
                gestor.eliminarCita(cita);
                //Mostrar mensaje de confirmación
                JOptionPane.showMessageDialog(parent, "Cita eliminada");
                this.dispose();
            } catch (GestorException ex) {
                JOptionPane.showMessageDialog(parent, ex);
            }

        }
    }

    private void EditarCita() {
    cita.setExtras(this.txtaExtras.getText());
    cita.setLugar(this.txtaLugar.getText());
    
    cita.setMateriales(materialesSeleccionados);

    Date fechaSeleccionada = this.jcalendar.getDate();

    GregorianCalendar fechaHoraInicio = new GregorianCalendar();
    fechaHoraInicio.setTime(fechaSeleccionada);

    Date horaInicio = (Date) sFechaInicio.getValue(); 
    Date horaFin = (Date) sFechaFin.getValue(); 

    fechaHoraInicio.set(Calendar.HOUR_OF_DAY, horaInicio.getHours()); 
    fechaHoraInicio.set(Calendar.MINUTE, horaInicio.getMinutes()); 

    GregorianCalendar fechaHoraFin = (GregorianCalendar) fechaHoraInicio.clone(); 
    fechaHoraFin.set(Calendar.HOUR_OF_DAY, horaFin.getHours()); 
    fechaHoraFin.set(Calendar.MINUTE, horaFin.getMinutes()); 

    if (fechaHoraFin.before(fechaHoraInicio)) {
        JOptionPane.showMessageDialog(parent, "La hora de fin no puede ser antes de la hora de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }

    cita.setFechaHoraInicio(fechaHoraInicio); 
    cita.setFechaHoraFin(fechaHoraFin); 

    for (FotografoDTO fotografo : fotografos) {
        if (fotografo.getNombrePersona().equals(this.cbFotografo.getSelectedItem().toString())) {
            cita.setFotografo(fotografo);
        }
    }

    try {
        gestor.actualizarCita(cita); 
        JOptionPane.showMessageDialog(parent, "Cita actualizada");
        this.dispose();
    } catch (GestorException ex) {
        JOptionPane.showMessageDialog(parent, ex);
    }
}

    private void AgregarCita() {
        cita = new CitaDTO();
        cita.setCodigo(DiferenciadorUtils.generarCodigo());
        
        cita.setMateriales(materialesSeleccionados);

        cita.setExtras(this.txtaExtras.getText());
        cita.setLugar(this.txtaLugar.getText());

        if (parent instanceof PantallaPrincipal) {
            PantallaPrincipal pantallaPrincipal = (PantallaPrincipal) parent;
            cita.setContrato(pantallaPrincipal.getContrato());
        }

        // Obtenemos la fecha seleccionada del JCalendar
        Date fechaSeleccionada = this.jcalendar.getDate();

        // Convertimos la fecha seleccionada a GregorianCalendar
        GregorianCalendar fechaHoraInicio = new GregorianCalendar();
        fechaHoraInicio.setTime(fechaSeleccionada);

        // Obtener la hora desde el JSpinner (hora de inicio y fin)
        Date horaInicio = (Date) sFechaInicio.getValue(); // Obtener la hora del JSpinner de hora de inicio
        Date horaFin = (Date) sFechaFin.getValue(); // Obtener la hora del JSpinner de hora de fin

        // Establecer las horas de inicio y fin en los objetos GregorianCalendar
        fechaHoraInicio.set(Calendar.HOUR_OF_DAY, horaInicio.getHours()); // Establecer la hora de inicio
        fechaHoraInicio.set(Calendar.MINUTE, horaInicio.getMinutes()); // Establecer los minutos de inicio

        GregorianCalendar fechaHoraFin = (GregorianCalendar) fechaHoraInicio.clone(); // Hacemos una copia para la hora de fin
        fechaHoraFin.set(Calendar.HOUR_OF_DAY, horaFin.getHours()); // Establecer la hora de fin
        fechaHoraFin.set(Calendar.MINUTE, horaFin.getMinutes()); // Establecer los minutos de fin

        // Ahora, asignamos estos valores a los atributos del objeto Cita
        cita.setFechaHoraInicio(fechaHoraInicio); // Asignar la fecha y hora de inicio
        cita.setFechaHoraFin(fechaHoraFin); // Asignar la fecha y hora de fin

        for (FotografoDTO fotografo : fotografos) {
            if (fotografo.getNombrePersona().equals(this.cbFotografo.getSelectedItem().toString())) {
                cita.setFotografo(fotografo);
            }
        }

        try {
            CitaDTO citaAgregada = gestor.crearCita(cita);
            DialogCita.citaAgregada = citaAgregada;
            //Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(parent, "Cita agregada");
            this.dispose();
        } catch (GestorException ex) {
            JOptionPane.showMessageDialog(parent, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpopmMateriales = new javax.swing.JPopupMenu();
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
        jcalendar = new com.toedter.calendar.JCalendar();
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
        txtCantidad = new javax.swing.JTextField();
        txtNombreMat = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        lblExtras1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMaterial = new javax.swing.JTable();

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
        pnlPrincipal.add(lblSeleccionarFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblSelecionarHorario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSelecionarHorario.setText("Seleccionar horario inicio y fin");
        pnlPrincipal.add(lblSelecionarHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        lblLugar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLugar.setText("Lugar:");
        pnlPrincipal.add(lblLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, -1));

        lblFotografo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFotografo.setText("Fotografo:");
        pnlPrincipal.add(lblFotografo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, -1));

        lblExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblExtras.setText("Materiales:");
        pnlPrincipal.add(lblExtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, -1, -1));

        pnlFecha.setBackground(new java.awt.Color(220, 240, 255));
        pnlFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlFecha.setMaximumSize(new java.awt.Dimension(320, 170));
        pnlFecha.setMinimumSize(new java.awt.Dimension(320, 170));

        jcalendar.setBackground(new java.awt.Color(220, 240, 255));

        javax.swing.GroupLayout pnlFechaLayout = new javax.swing.GroupLayout(pnlFecha);
        pnlFecha.setLayout(pnlFechaLayout);
        pnlFechaLayout.setHorizontalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
        );
        pnlFechaLayout.setVerticalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcalendar, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        );

        pnlPrincipal.add(pnlFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 500, 240));

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

        pnlPrincipal.add(pnlHorarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 350, 50));

        cbFotografo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ricardo Gutierrez", " ", " ", " ", " " }));
        cbFotografo.setBorder(null);
        pnlPrincipal.add(cbFotografo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 230, 40));

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
        pnlPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 590, 120, 30));

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
        pnlPrincipal.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 590, 120, 30));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaExtras.setColumns(20);
        txtaExtras.setRows(5);
        txtaExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(txtaExtras);

        pnlPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 310, 110));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtaLugar.setColumns(20);
        txtaLugar.setRows(5);
        txtaLugar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtaLugar);

        pnlPrincipal.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 350, 90));

        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pencilIcon.png"))); // NOI18N
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });
        pnlPrincipal.add(lblEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, -1, -1));

        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/deleteIconBig.png"))); // NOI18N
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDeleteMouseClicked(evt);
            }
        });
        pnlPrincipal.add(lblDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, -1, -1));

        txtCantidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCantidad.setText("1");
        txtCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        pnlPrincipal.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 60, 50));

        txtNombreMat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombreMat.setText("Mazapan");
        txtNombreMat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombreMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreMatActionPerformed(evt);
            }
        });
        pnlPrincipal.add(txtNombreMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 190, 50));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
        });
        pnlPrincipal.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 290, -1, -1));

        lblExtras1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblExtras1.setText("Extras:");
        pnlPrincipal.add(lblExtras1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, -1, -1));

        tblMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cantidad", "Material", ""
            }
        ));
        jScrollPane3.setViewportView(tblMaterial);

        pnlPrincipal.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 360, -1, 210));

        getContentPane().add(pnlPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 640));

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
        editando = !editando;
        HabilitarEditar();
    }//GEN-LAST:event_lblEditMouseClicked

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtNombreMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMatActionPerformed

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        
    }//GEN-LAST:event_btnAgregarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel btnCerrar;
    private javax.swing.JLabel btnEditar;
    private javax.swing.JLabel btnEliminar;
    private javax.swing.JComboBox<String> cbFotografo;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JCalendar jcalendar;
    private javax.swing.JPopupMenu jpopmMateriales;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblExtras;
    private javax.swing.JLabel lblExtras1;
    private javax.swing.JLabel lblFotografo;
    private javax.swing.JLabel lblLugar;
    private javax.swing.JLabel lblSeleccionarFecha;
    private javax.swing.JLabel lblSelecionarHorario;
    private javax.swing.JPanel pnlFecha;
    private javax.swing.JPanel pnlHorarios;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSpinner sFechaFin;
    private javax.swing.JSpinner sFechaInicio;
    private javax.swing.JTable tblMaterial;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNombreMat;
    private javax.swing.JLabel txtTitulo;
    private javax.swing.JTextArea txtaExtras;
    private javax.swing.JTextArea txtaLugar;
    // End of variables declaration//GEN-END:variables
}
