package org.itson.sof.sof_level_presentacion.interfaces;

import org.itson.sof.sof_level_presentacion.componentes.ItemScrollCitas;
import com.toedter.calendar.JDayChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.IGestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorCitasException;
import org.itson.sof.objetosnegocios.gestorclientes.GestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.IGestorClientes;
import org.itson.sof.objetosnegocios.gestorclientes.gestorexception.GestorClientesException;
import org.itson.sof.objetosnegocios.gestorcontratos.GestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.IGestorContratos;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorcontratosexception.GestorContratoException;
import org.itson.sof.objetosnegocios.gestorcontratos.gestorpaquetesexception.GestorPaqueteException;
import org.itson.sof.objetosnegocios.gestorpaquetes.gestorpaquetes.GestorPaquetes;
import org.itson.sof.objetosnegocios.gestorpaquetes.gestorpaquetes.IGestorPaquetes;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ClienteDTO;
import org.itson.sof.sof_dtos.ContratoDTO;
import org.itson.sof.sof_dtos.PaqueteDTO;
import org.itson.sof.sof_level_presentacion.componentes.EvaluadorCitasFecha;
import org.itson.sof.sof_level_presentacion.componentes.ItemCita;

/**
 *
 * @author JazmE
 */
public class PanelContrato extends javax.swing.JPanel {

    IGestorCitas gestor;
    IGestorContratos gestorContrato;
    IGestorPaquetes gestorPaquete;
    IGestorClientes gestorCliente;
    ContratoDTO contrato;
    private final PantallaPrincipal principal;
    DialogCita dlgCita;
    boolean unicaCita;
    private boolean inicializado = false;
    ItemScrollCitas scrollCitas;
    private Calendar fechaAnterior;
    boolean primeraVez = true;
    private EvaluadorCitasFecha evaluadorActual;
    private boolean puedeAbrir = true;
    private List<ClienteDTO> clientesTotales = new LinkedList<>();
    public JPanel panelContenedor;
    private javax.swing.JPopupMenu popupMenuClientes;
    private javax.swing.JList<String> listaCorreosClientes;
    private DefaultListModel<String> listaCorreosModel;

    /**
     * Creates new form PanelContrato
     *
     * @param principal
     */
    public PanelContrato(PantallaPrincipal principal) {
        this.principal = principal;

        gestor = GestorCitas.getInstance();
        gestorPaquete = new GestorPaquetes();
        gestorContrato = new GestorContratos();
        gestorCliente = GestorClientes.getInstance();
    }

    public void inicializar() {
        if (!inicializado) {
            initComponents();
            inicializado = true;
        }
        deshabilitar();
        
        fechaAnterior = Calendar.getInstance();
        fechaAnterior.setTime(jCalendarCitas.getDate());
        contrato = principal.getContrato();

        if (contrato == null) {
            lblEdit.setVisible(false);
            inicializarCreacionContrato();
        } else {
            if (contrato.getEstado().equalsIgnoreCase("CANCELADO")
                    || contrato.getEstado().equalsIgnoreCase("TERMINADO")) {

                lblEdit.setVisible(false);
                btnAgregarCita.setVisible(false);
                lblAgregarCita.setVisible(false);

            } else {
                lblEdit.setVisible(true);
                btnAgregarCita.setVisible(true);
                lblAgregarCita.setVisible(true);
            }

            llenarCamposContrato();

            cmbPaquete.removeAllItems(); // Limpiar cualquier dato previo
            if (contrato != null && contrato.getPaquete() != null) {
                cmbPaquete.addItem(contrato.getPaquete().getNombre());
            }
            cmbPaquete.setEnabled(false);
        }
        agregarCitas();
    }

    private void deshabilitar() {
        this.btnConfirmarEdicion.setVisible(false);
        this.lblTerminarContrato.setVisible(false);
        this.lblCancelarContrato.setVisible(false);
        this.lblDescCancelarContrato.setVisible(false);
        this.lbldescTerminarContrato.setVisible(false);
        txtPrecio.setEnabled(false);
        
        if (contrato == null) {
            txtTematica.setEnabled(true);
            txtTematica.setText("");
            this.lblAgregarCita.setVisible(false);
            btnAgregarCita.setVisible(false);
            lblEdit.setVisible(true);
            btnCrearContrato.setVisible(true);
        } else {
            txtTematica.setEnabled(false);
            txtTematica.setText(contrato.getTematica());
            lblEdit.setVisible(false);
            btnCrearContrato.setVisible(false);
            
            this.lblAgregarCita.setVisible(true);
            btnAgregarCita.setVisible(true);
        }
    }

    private List<ClienteDTO> obtenerClientes() {
        try {
            return gestorCliente.obtenerTodosClientes();
        } catch (GestorClientesException ex) {
            Logger.getLogger(PanelClientes.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<>();
        }
    }

    private void inicializarCreacionContrato() {
        txtCliente.setText("");
        cmbPaquete.removeAllItems();
        txtPrecio.setText("$ 0.0");
        txtCliente.setEnabled(true);
        txtCliente.setEditable(true);
        cmbPaquete.setEnabled(true);
        lblCitas.setText("Citas (Nuevo Contrato)");
        pnlCitas.setVisible(true);

        if (panelContenedor != null) {
            panelContenedor.removeAll();
            JLabel mensajeModoCreacion = new JLabel("No hay citas para este nuevo contrato aún.");
            mensajeModoCreacion.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            panelContenedor.add(mensajeModoCreacion);
            panelContenedor.revalidate();
            panelContenedor.repaint();
        } else {
            panelContenedor = new JPanel();
            panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
            panelContenedor.setBackground(new Color(220, 240, 255));
            JLabel mensajeModoCreacion = new JLabel("No hay citas para este nuevo contrato aún.");
            mensajeModoCreacion.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            panelContenedor.add(mensajeModoCreacion);
        }
        scrollPaneCitas.setViewportView(panelContenedor);

        llenarCmbPaquetes();
        cmbPaquete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String nombrePaqueteSeleccionado = (String) e.getItem();
                    PaqueteDTO paqueteSeleccionado = new PaqueteDTO();
                    paqueteSeleccionado.setNombre(nombrePaqueteSeleccionado);
                    try {
                        paqueteSeleccionado = gestorPaquete.obtenerPaquete(paqueteSeleccionado);
                        Float precio = paqueteSeleccionado.getPrecio();
                        String precioString = new String("$ " + precio.toString() + ".00");
                        txtPrecio.setText(precioString);
                    } catch (GestorPaqueteException ex) {
                        JOptionPane.showMessageDialog(principal, ex.getMessage());
                    }
                }
            }
        });
        cmbPaquete.setSelectedIndex(-1);
        txtPrecio.setText("$ 0.0");

        this.clientesTotales = obtenerClientes();

        listaCorreosModel = new DefaultListModel<>();
        listaCorreosClientes = new JList<>(listaCorreosModel);
        JScrollPane scrollPane = new JScrollPane(listaCorreosClientes);
        scrollPane.setPreferredSize(new Dimension(250, 100)); // Ajusta las dimensiones según necesites

        popupMenuClientes = new JPopupMenu();
        popupMenuClientes.add(scrollPane);

        // Selección de correo desde la lista de autocompletar
        listaCorreosClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && listaCorreosClientes.getSelectedValue() != null) {
                    txtCliente.setText(listaCorreosClientes.getSelectedValue());
                    popupMenuClientes.setVisible(false);
                    txtCliente.requestFocusInWindow(); // Devolver el foco al JTextField
                } else if (e.getClickCount() == 2 && listaCorreosClientes.getSelectedValue() != null) {
                    txtCliente.setText(listaCorreosClientes.getSelectedValue());
                    popupMenuClientes.setVisible(false);
                    txtCliente.requestFocusInWindow(); // Devolver el foco al JTextField
                }
            }
        });

        // Evento de tecla ENTER para selección
        listaCorreosClientes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && listaCorreosClientes.getSelectedValue() != null) {
                    txtCliente.setText(listaCorreosClientes.getSelectedValue());
                    popupMenuClientes.setVisible(false);
                    txtCliente.requestFocusInWindow(); // Devolver el foco al JTextField
                }
            }
        });

        // Document listener para el JTextField txtCliente
        txtCliente.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarListaClientes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarListaClientes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarListaClientes();
            }
        });
    }

    private void actualizarListaClientes() {
        String input = txtCliente.getText().trim().toLowerCase();
        listaCorreosModel.clear();

        if (!input.isEmpty()) {
            for (ClienteDTO cliente : clientesTotales) {
                if (cliente.getNombre().toLowerCase().contains(input)
                        || cliente.getCorreo().toLowerCase().contains(input)
                        || cliente.getTelefono().toLowerCase().contains(input)) {
                    listaCorreosModel.addElement(cliente.getCorreo());
                }
            }

            if (!listaCorreosModel.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    if (!popupMenuClientes.isVisible()) {
                        //popupMenuClientes.show(txtCliente, 0, txtCliente.getHeight());
                    }
                    txtCliente.requestFocusInWindow(); // Intentar enfocar después de mostrar
                });
            } else {
                popupMenuClientes.setVisible(false);
            }
        } else {
            popupMenuClientes.setVisible(false);
        }
    }

    private String obtenerNombreClientePorCorreo(String correo) {
        for (ClienteDTO cliente : clientesTotales) {
            if (cliente.getCorreo().equalsIgnoreCase(correo)) {
                return cliente.getNombre();
            }
        }
        return "";
    }

    private void llenarCmbPaquetes() {

        try {
            List<PaqueteDTO> paquetes = gestorPaquete.obtenerPaquetes();
            cmbPaquete.removeAllItems();
            for (PaqueteDTO paquete : paquetes) {
                cmbPaquete.addItem(paquete.getNombre());
            }

        } catch (GestorPaqueteException ex) {
            JOptionPane.showMessageDialog(principal, "No hay paquetes registrados");
        }

    }

    private void decorarCalendario(List<CitaDTO> citas) {
        // Contar cuántas citas hay por día
        Map<String, Integer> citasPorDia = new HashMap<>();

        if(citas != null) {
            for (CitaDTO cita : citas) {
                GregorianCalendar cal = cita.getFechaHoraInicio();
                String key = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);

                citasPorDia.put(key, citasPorDia.getOrDefault(key, 0) + 1);
            }
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
        if (!puedeAbrir) {
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
                    puedeAbrir = false;
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

        if (citas == null || citas.isEmpty()) {
            //Poner un mensaje si no hay contratos
            JLabel mensaje = new JLabel("Aun no hay citas");
            mensaje.setSize(100, 100);
            mensaje.setFont(new Font("Sego Ui", Font.PLAIN, 15));
            panelContenedor.add(mensaje);
            decorarCalendario(null);
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
        if(contrato==null){
            return new ArrayList<>();
        }
        try {
            List<CitaDTO> citas = gestor.obtenerCitasContrato(contrato);
            if (citas != null) {
                if (citas.size() == 1) {
                    unicaCita = true;
                } else {
                    unicaCita = false;
                }
            }
            return citas;
        } catch (GestorCitasException ex) {
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

    private void crearContrato() {
        if (txtCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(principal, "Por favor ingrese el correo de un cliente", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbPaquete.getSelectedItem() == null || cmbPaquete.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(principal, "Por favor seleccione un paquete", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtTematica.getText().isEmpty()){
            JOptionPane.showMessageDialog(principal, "Ingrese la tematica", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(principal, "El precio no puede ser de $0.0", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ContratoDTO contratoDto = new ContratoDTO();
        contratoDto.setTematica(txtTematica.getText());

        ClienteDTO cliente = new ClienteDTO();

        String textoIngresado = txtCliente.getText();
        boolean encontrado = false;

        for (int i = 0; i < listaCorreosModel.getSize(); i++) {
            if (textoIngresado.equals(listaCorreosModel.getElementAt(i))) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            cliente.setCorreo(textoIngresado);

            PaqueteDTO paquete = new PaqueteDTO();
            paquete.setNombre(cmbPaquete.getSelectedItem().toString());

            try {
                this.contrato = gestorContrato.crearContrato(contratoDto, cliente, paquete);
                principal.setContrato(this.contrato);
                this.btnAgregarCita.setVisible(true);
                this.btnAgregarCita.setEnabled(true);
                lblAgregarCita.setVisible(true);
                this.lblEdit.setVisible(true);
                btnCrearContrato.setVisible(false);
                JOptionPane.showMessageDialog(principal, "Contrato creado");
            } catch (GestorContratoException ex) {
                JOptionPane.showMessageDialog(principal, ex.getMessage(), "Error en creación del paquete", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(principal, "Este cliente no existe", "Error al crear contrato", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void llenarCamposContrato() {
        txtCliente.setText(contrato.getCliente().getNombre());
        
        txtPrecio.setText("$ " + contrato.getPaquete().getPrecio());

        txtCliente.setEnabled(false);
        //txtTematica.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    private void actualizarContrato() {

        ContratoDTO contratoDTO = contrato;

        if (txtTematica.getText().isEmpty()){
            JOptionPane.showMessageDialog(principal, "Ingrese la tematica", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        contratoDTO.setTematica(txtTematica.getText());

        try {
            gestorContrato.actualizarContrato(contratoDTO);
            txtTematica.setEnabled(false);
            txtTematica.setEditable(false);
            btnConfirmarEdicion.setVisible(false);
            JOptionPane.showMessageDialog(principal, "Contrato Actualizado");
        } catch (GestorContratoException ex) {
            JOptionPane.showMessageDialog(principal, ex.getMessage(), "Error al actualizar", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void terminarContrato() {
        int opcion = JOptionPane.showConfirmDialog(
                principal,
                "¿Está seguro que desea terminar el contrato?",
                "Confirmar terminación",
                JOptionPane.YES_NO_OPTION
        );

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                gestorContrato.terminarContrato(contrato);
                lblCancelarContrato.setVisible(false);
                lblTerminarContrato.setVisible(false);
                lblDescCancelarContrato.setVisible(false);
                this.lbldescTerminarContrato.setVisible(false);
                lblEdit.setVisible(false);
                btnConfirmarEdicion.setVisible(false);
                btnAgregarCita.setVisible(false);
                lblAgregarCita.setVisible(false);
                JOptionPane.showMessageDialog(
                        principal,
                        "El contrato ha sido terminado.",
                        "Contrato terminado",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (GestorContratoException ex) {
                JOptionPane.showMessageDialog(
                        principal,
                        "Error al terminar el contrato: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {

            JOptionPane.showMessageDialog(
                    principal,
                    "La terminación del contrato ha sido cancelada.",
                    "Terminación cancelada",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void cancelarContrato() {
        int opcion = JOptionPane.showConfirmDialog(
                principal,
                "¿Está seguro que desea cancelar el contrato? esto eliminará todas sus citas",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                gestorContrato.cancelarContrato(contrato);
                try {
                    gestor.eliminarCitasCotrato(contrato.getFolio());
                } catch (GestorCitasException ex) {
                    JOptionPane.showMessageDialog(principal, ex.getMessage(), "Error con citas del contrato", JOptionPane.ERROR_MESSAGE);
                }
                lblCancelarContrato.setVisible(false);
                lblTerminarContrato.setVisible(false);
                lblDescCancelarContrato.setVisible(false);
                this.lbldescTerminarContrato.setVisible(false);
                lblEdit.setVisible(false);
                btnConfirmarEdicion.setVisible(false);
                btnAgregarCita.setVisible(false);
                lblAgregarCita.setVisible(false);
                JOptionPane.showMessageDialog(
                        principal,
                        "El contrato ha sido cancelado.",
                        "Contrato cancelado",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (GestorContratoException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al cancelar el contrato: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {

            JOptionPane.showMessageDialog(
                    principal,
                    "La cancelación del contrato ha sido anulada.",
                    "Cancelación anulada",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void editarContrato(){
        this.btnConfirmarEdicion.setVisible(true);
        this.txtTematica.setEditable(true);
        this.txtTematica.setEnabled(true);
        
        lblCancelarContrato.setVisible(true);
        lblDescCancelarContrato.setVisible(true);
        lblTerminarContrato.setVisible(true);
        lbldescTerminarContrato.setVisible(true);
        lblEdit.setVisible(false);
    }
    
    private void guardarEdicion(){
        txtTematica.setEditable(true);
        txtTematica.setEnabled(true);
        this.btnConfirmarEdicion.setVisible(false);
        lblCancelarContrato.setVisible(false);
        lblDescCancelarContrato.setVisible(false);
        lblTerminarContrato.setVisible(false);
        lbldescTerminarContrato.setVisible(false);
        lblEdit.setVisible(true);
        actualizarContrato();  
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        lblPaquete = new javax.swing.JLabel();
        cmbPaquete = new javax.swing.JComboBox<>();
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
        lblEdit = new javax.swing.JLabel();
        btnConfirmarEdicion = new javax.swing.JButton();
        lblTerminarContrato = new javax.swing.JLabel();
        lblCancelarContrato = new javax.swing.JLabel();
        lblDescCancelarContrato = new javax.swing.JLabel();
        lbldescTerminarContrato = new javax.swing.JLabel();
        btnCrearContrato = new javax.swing.JButton();

        setBackground(new java.awt.Color(220, 240, 255));
        setMaximumSize(new java.awt.Dimension(550, 750));
        setMinimumSize(new java.awt.Dimension(550, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCliente.setText("Cliente:");
        add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, 20));

        txtCliente.setEditable(false);
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClienteKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClienteKeyTyped(evt);
            }
        });
        add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 260, -1));

        lblPaquete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPaquete.setText("Paquete:");
        add(lblPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        cmbPaquete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo del paquete", "mlm", " " }));
        add(cmbPaquete, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 170, -1));

        lblTematica.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTematica.setText("Tematica:");
        add(lblTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        txtTematica.setColumns(20);
        txtTematica.setRows(5);
        add(txtTematica, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 140, 20));

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrecio.setText("Precio:");
        add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 140, -1));

        lblCitas.setText("Citas (X)");
        add(lblCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, -1, -1));

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
        add(btnAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 50, -1));

        lblAgregarCita.setText("Agregar cita");
        add(lblAgregarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 410, -1, -1));

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
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCitasLayout.setVerticalGroup(
            pnlCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCitasLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(scrollPaneCitas, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pnlCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 330, 370, 210));
        add(jCalendarCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 460, 220));

        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pencilIcon.png"))); // NOI18N
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });
        add(lblEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, -1));

        btnConfirmarEdicion.setBackground(new java.awt.Color(36, 160, 108));
        btnConfirmarEdicion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirmarEdicion.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmarEdicion.setText("Confirmar edicion");
        btnConfirmarEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnConfirmarEdicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarEdicionActionPerformed(evt);
            }
        });
        add(btnConfirmarEdicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 130, -1));

        lblTerminarContrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check_circle_green.png"))); // NOI18N
        lblTerminarContrato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTerminarContratoMouseClicked(evt);
            }
        });
        add(lblTerminarContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        lblCancelarContrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contract_delete_red.png"))); // NOI18N
        lblCancelarContrato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelarContratoMouseClicked(evt);
            }
        });
        add(lblCancelarContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        lblDescCancelarContrato.setText("Cancelar contrato");
        add(lblDescCancelarContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        lbldescTerminarContrato.setText("Teminar contrato");
        add(lbldescTerminarContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, -1, -1));

        btnCrearContrato.setBackground(new java.awt.Color(36, 160, 108));
        btnCrearContrato.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCrearContrato.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearContrato.setText("Crear contrato");
        btnCrearContrato.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCrearContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearContratoActionPerformed(evt);
            }
        });
        add(btnCrearContrato, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 130, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCitaMouseClicked
        añadirCita();
    }//GEN-LAST:event_btnAgregarCitaMouseClicked

    private void btnAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarCitaActionPerformed

    private void txtClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyReleased

    }//GEN-LAST:event_txtClienteKeyReleased

    private void txtClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyTyped
        String texto = txtCliente.getText().toLowerCase();
        List<String> correosFiltrados = new LinkedList<>();

        for (ClienteDTO cliente : clientesTotales) {
            if (cliente.getNombre().toLowerCase().contains(texto)
                    || cliente.getCorreo().toLowerCase().contains(texto)
                    || cliente.getTelefono().toLowerCase().contains(texto)) {
                correosFiltrados.add(cliente.getCorreo());
            }
        }

        if (!correosFiltrados.isEmpty() && !texto.isEmpty()) {
            listaCorreosClientes.setListData(correosFiltrados.toArray(new String[0]));
            popupMenuClientes.show(txtCliente, 0, txtCliente.getHeight());
            SwingUtilities.invokeLater(() -> {
                txtCliente.requestFocusInWindow();
            });
        } else {
            popupMenuClientes.setVisible(false);
        }
    }//GEN-LAST:event_txtClienteKeyTyped

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        editarContrato();
    }//GEN-LAST:event_lblEditMouseClicked

    private void btnConfirmarEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarEdicionActionPerformed
        guardarEdicion();
    }//GEN-LAST:event_btnConfirmarEdicionActionPerformed

    private void lblTerminarContratoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTerminarContratoMouseClicked
        terminarContrato();
    }//GEN-LAST:event_lblTerminarContratoMouseClicked

    private void lblCancelarContratoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelarContratoMouseClicked
        cancelarContrato();
    }//GEN-LAST:event_lblCancelarContratoMouseClicked

    private void btnCrearContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearContratoActionPerformed
       crearContrato();
    }//GEN-LAST:event_btnCrearContratoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnConfirmarEdicion;
    private javax.swing.JButton btnCrearContrato;
    private javax.swing.JComboBox<String> cmbPaquete;
    private com.toedter.calendar.JCalendar jCalendarCitas;
    private javax.swing.JLabel lblAgregarCita;
    private javax.swing.JLabel lblCancelarContrato;
    private javax.swing.JLabel lblCitas;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDescCancelarContrato;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblPaquete;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTematica;
    private javax.swing.JLabel lblTerminarContrato;
    private javax.swing.JLabel lbldescTerminarContrato;
    private javax.swing.JPanel pnlCitas;
    private javax.swing.JScrollPane scrollPaneCitas;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextArea txtTematica;
    // End of variables declaration//GEN-END:variables
}
