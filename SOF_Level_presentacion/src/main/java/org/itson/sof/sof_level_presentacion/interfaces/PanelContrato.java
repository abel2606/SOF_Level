package org.itson.sof.sof_level_presentacion.interfaces;

import com.toedter.calendar.JDayChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_level_presentacion.componentes.EvaluadorCitasFecha;
import org.itson.sof.sof_level_presentacion.componentes.ItemCita;

/**
 *
 * @author JazmE
 */
public class PanelContrato extends javax.swing.JPanel {

    GestorCitas gestor;
    ContratoDTO contrato;
    private final PantallaPrincipal principal;
    DialogCita dlgCita;
    boolean unicaCita;
    private boolean inicializado = false;
    ItemScrollCitas scrollCitas;
    private Calendar fechaAnterior;
    boolean primeraVez = true;
    private EvaluadorCitasFecha evaluadorActual;
    private boolean puedeAbrir=true;

    public JPanel panelContenedor;

    /**
     * Creates new form PanelContrato
     *
     * @param principal
     */
    public PanelContrato(PantallaPrincipal principal) {
        this.principal = principal;

        gestor = GestorCitas.getInstance();
    }

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        this.fechaAnterior = Calendar.getInstance();
        fechaAnterior.setTime(jCalendarCitas.getDate());
        contrato = principal.getContrato();
        agregarCitas();
        llenarCamposContrato();

        cmbPaquete.removeAllItems(); // Limpiar cualquier dato previo
        if (contrato != null && contrato.getPaquete() != null) {
            cmbPaquete.addItem(contrato.getPaquete().getNombre());
        }
        cmbPaquete.setEnabled(false);
    }

    private void decorarCalendario(List<CitaDTO> citas) {
        // Contar cuántas citas hay por día
        Map<String, Integer> citasPorDia = new HashMap<>();

        for (CitaDTO cita : citas) {
            GregorianCalendar cal = cita.getFechaHoraInicio();
            String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);

            citasPorDia.put(key, citasPorDia.getOrDefault(key, 0) + 1);
        }

        // Obtener el DayChooser del JCalendar
        JDayChooser dayChooser = jCalendarCitas.getDayChooser();

        // Remover el evaluador antiguo si existe
        if (evaluadorActual != null) {
            dayChooser.removeDateEvaluator(evaluadorActual);
        }

        // Crear y agregar un nuevo evaluador actualizado
        evaluadorActual = new EvaluadorCitasFecha(citasPorDia);
        dayChooser.addDateEvaluator(evaluadorActual);

        // Guardar fecha del dia 1
        Calendar temp2 = Calendar.getInstance();
        temp2.setTime(jCalendarCitas.getDate());
        temp2.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaActual = temp2.getTime();

        // Ir a otro mes y volver para forzar repintado completo
        Calendar temp = Calendar.getInstance();
        temp.setTime(fechaActual);
        temp.add(Calendar.MONTH, 1); // Mover un mes adelante
        jCalendarCitas.setDate(temp.getTime());

        temp.add(Calendar.MONTH, -1); // Volver al mes original
        jCalendarCitas.setDate(fechaActual);

        if (primeraVez) {
            fechaAnterior.set(Calendar.DAY_OF_MONTH, 1); // Establecer el día a 1
            jCalendarCitas.setCalendar(fechaAnterior);
            primeraVez = false;
            añadirListernerCalendario();
            
        }

    }

    private void añadirListernerCalendario() {
        jCalendarCitas.addPropertyChangeListener("calendar", (PropertyChangeEvent evt) -> {
                
                // Obtener la fecha seleccionada
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.setTime(jCalendarCitas.getDate()); // Convertir Date a Calendar

                //Comprobar si solo el día ha cambiado
                if ((fechaAnterior.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)
                        && fechaAnterior.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH)
                        && fechaAnterior.get(Calendar.DAY_OF_MONTH) != selectedDate.get(Calendar.DAY_OF_MONTH))) {
                    // Si el día cambió, ejecutamos la lógica
                    String key = selectedDate.get(Calendar.YEAR) + "-"
                            + selectedDate.get(Calendar.MONTH) + "-"
                            + selectedDate.get(Calendar.DAY_OF_MONTH);

                    // Mostrar las citas para el día seleccionado
                    mostrarCitasDelDia(key);
                }
                fechaAnterior.setTime(jCalendarCitas.getDate()); // Actualizamos la fecha anterior
            });
    }

    private void mostrarCitasDelDia(String key) {
        if(!puedeAbrir){
            return;
        }
        List<CitaDTO> citasDelDia = obtenerCitasDelDia(key);

        if (citasDelDia.isEmpty()) {
        } else {
            if (scrollCitas != null) {
                if (scrollCitas.isVisible()) {
                    return;
                }
            }

            // Convertir la clave en el formato YYYY-MM-DD a un objeto Calendar
            String[] dateParts = key.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);

            // Crear un objeto Calendar para la fecha seleccionada
            Calendar selectedCalendar = new GregorianCalendar(year, month, day);
            
            // Obtener el día, mes y año
            String dia = String.format("%02d", selectedCalendar.get(Calendar.DAY_OF_MONTH));  // Día con dos dígitos
            String mes = String.format("%02d", selectedCalendar.get(Calendar.MONTH) + 1);     // Mes con dos dígitos (sumamos 1 porque el mes comienza desde 0)
            String anio = String.valueOf(selectedCalendar.get(Calendar.YEAR));                // Año

            // Crear un nuevo ItemScrollCitas con las citas y mostrarlo
            scrollCitas = new ItemScrollCitas(principal, true, citasDelDia);
            scrollCitas.setTitle("Citas del día " + dia + "/" + mes + "/" + anio + ":");
            scrollCitas.setVisible(true);
            scrollCitas.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    puedeAbrir=false;
                    fechaAnterior.set(Calendar.DAY_OF_MONTH, 1);
                    jCalendarCitas.setCalendar(fechaAnterior);
                    agregarCitas();
                    puedeAbrir = true;
                    
                    // Guardar fecha actual
                    Date fechaActual = jCalendarCitas.getDate();

                    // Ir a otro mes y volver para forzar repintado completo
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(fechaActual);
                    temp.add(Calendar.MONTH, 1); // Mover un mes adelante
                    jCalendarCitas.setDate(temp.getTime());

                    temp.add(Calendar.MONTH, -1); // Volver al mes original
                    jCalendarCitas.setDate(fechaActual);
                }
            });
        }
    }

    private List<CitaDTO> obtenerCitasDelDia(String key) {
        // Filtrar y obtener las citas que coinciden con el día seleccionado
        return ConsultarCitas().stream()
                .filter(cita -> {
                    GregorianCalendar fechaCita = cita.getFechaHoraInicio();
                    String citaKey = fechaCita.get(Calendar.YEAR) + "-" + fechaCita.get(Calendar.MONTH) + "-" + fechaCita.get(Calendar.DAY_OF_MONTH);
                    return citaKey.equals(key);
                })
                .toList();
    }
    
    private void agregarCitas() {
        List<CitaDTO> citas = ConsultarCitas();

        // Crear un panel de contenedor para los contratos
        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setBackground(new Color(220, 240, 255));

        if (citas==null || citas.isEmpty()) {
            //Poner un mensaje si no hay contratos
            JLabel mensaje = new JLabel("Aun no hay citas");
            mensaje.setSize(100, 100);
            mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            panelContenedor.add(mensaje);
        } else {
            decorarCalendario(citas);
            for (CitaDTO cita : citas) {
                ItemCita panel = new ItemCita(
                        cita.getFechaHoraInicio());
                panel.setPreferredSize(new Dimension(300, 50));
                panel.setMaximumSize(new Dimension(300, 50));
                panel.setMinimumSize(new Dimension(300, 50));

                panel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        manejarClicEnCita(cita);
                    }
                });

                panelContenedor.setBackground(new Color(220, 240, 255));
                panelContenedor.add(panel);
                panelContenedor.add(Box.createVerticalStrut(10));
            }
        }
        scrollPaneCitas.setViewportView(panelContenedor);
        scrollPaneCitas.getVerticalScrollBar().setUnitIncrement(20);
    }

    /**
     * Devuelve la lista de citas del contrato seleccionado de la base de datos
     *
     * @return Lista de CitaDTO
     */
    private List<CitaDTO> ConsultarCitas() {
        try {
            List<CitaDTO> citas = gestor.obtenerCitasContrato(contrato);
            if (citas != null) {
                if (citas.size() == 1) {
                    unicaCita = true;
                }else{
                    unicaCita=false;
                }
            }
            return citas;
        } catch (GestorException ex) {
            JOptionPane.showMessageDialog(principal, ex.getMessage());
        }
        return null;
    }

    /**
     * Metodo que se llama cuando se hace click a una cita
     *
     * @param cita cita al que se le hizo click
     */
    private void manejarClicEnCita(CitaDTO cita) {
        dlgCita = new DialogCita(principal, true, cita, unicaCita);
        dlgCita.setVisible(true);
        dlgCita.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                agregarCitas();
                inicializar();
            }
        });
    }

    private void añadirCita() {
        dlgCita = new DialogCita(principal, true, null, true);
        dlgCita.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (DialogCita.citaAgregada != null) {
                    DialogCita.citaAgregada = null;
                    inicializar();
                } 
            }
        });
        dlgCita.setVisible(true);
    }

    private void llenarCamposContrato() {
        txtCliente.setText(contrato.getCliente().getNombre());
        txtTematica.setText(contrato.getTematica());
        txtPrecio.setText("$ " + contrato.getPaquete().getPrecio());

        txtCliente.setEnabled(false);
        txtTematica.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        lblPaquete = new javax.swing.JLabel();
        cmbPaquete = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        lblTematica = new javax.swing.JLabel();
        txtTematica = new javax.swing.JTextArea();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblCitas = new javax.swing.JLabel();
        btnAgregarCita = new javax.swing.JButton();
        lblAgregarCita = new javax.swing.JLabel();
        pnlCitas = new javax.swing.JPanel();
        scrollPaneCitas = new javax.swing.JScrollPane();
        jCalendarCitas = new com.toedter.calendar.JCalendar();

        setBackground(new java.awt.Color(220, 240, 255));
        setMaximumSize(new java.awt.Dimension(550, 750));
        setMinimumSize(new java.awt.Dimension(550, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCliente.setText("Cliente:");
        add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 50, 20));

        txtCliente.setEditable(false);
        add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 300, -1));

        lblPaquete.setText("Paquete:");
        add(lblPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        cmbPaquete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo del paquete", "mlm", " " }));
        add(cmbPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, -1));

        btnEditar.setText("Editar");
        add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 110, -1));

        lblTematica.setText("Tematica:");
        add(lblTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        txtTematica.setColumns(20);
        txtTematica.setRows(5);
        add(txtTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 140, 20));

        lblPrecio.setText("Precio:");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));
        add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 140, -1));

        lblCitas.setText("Citas (X)");
        add(lblCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, -1));

        btnAgregarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadirIcon.png"))); // NOI18N
        btnAgregarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarCitaMouseClicked(evt);
            }
        });
        btnAgregarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCitaActionPerformed(evt);
            }
        });
        add(btnAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 50, 50));

        lblAgregarCita.setText("Agregar cita");
        add(lblAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, -1, -1));

        pnlCitas.setBackground(new java.awt.Color(220, 240, 255));
        pnlCitas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        scrollPaneCitas.setBackground(new java.awt.Color(220, 240, 240));
        scrollPaneCitas.setBorder(null);
        scrollPaneCitas.setToolTipText("");

        javax.swing.GroupLayout pnlCitasLayout = new javax.swing.GroupLayout(pnlCitas);
        pnlCitas.setLayout(pnlCitasLayout);
        pnlCitasLayout.setHorizontalGroup(
            pnlCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        pnlCitasLayout.setVerticalGroup(
            pnlCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCitasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        add(pnlCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 350, 160));
        add(jCalendarCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 460, 220));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCitaMouseClicked
        añadirCita();
    }//GEN-LAST:event_btnAgregarCitaMouseClicked

    private void btnAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarCitaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnEditar;
    private javax.swing.JComboBox<String> cmbPaquete;
    private com.toedter.calendar.JCalendar jCalendarCitas;
    private javax.swing.JLabel lblAgregarCita;
    private javax.swing.JLabel lblCitas;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblPaquete;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTematica;
    private javax.swing.JPanel pnlCitas;
    private javax.swing.JScrollPane scrollPaneCitas;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextArea txtTematica;
    // End of variables declaration//GEN-END:variables
}
