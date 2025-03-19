package org.itson.sof.objetosnegocios.gestorcitas.prueba;

import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.gestorcitas.GestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.IGestorCitas;
import org.itson.sof.objetosnegocios.gestorcitas.gestorexception.GestorException;
import org.itson.sof.objetosnegocios.gestorcitas.util.GeneradorCodigos;
import org.itson.sof.sof_dtos.CitaDTO;

/**
 *
 * @author haesp
 */
public class PruebaCitas {
    
    private static final GestorCitas gestorCitas = new GestorCitas();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== Menú de Gestión de Citas =====");
            System.out.println("1. Crear Cita");
            System.out.println("2. Consultar Cita");
            System.out.println("3. Actualizar Cita");
            System.out.println("4. Eliminar Cita");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1 -> crearCita();
                case 2 -> consultarCita();
                case 3 -> actualizarCita();
                case 4 -> eliminarCita();
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida, intente nuevamente.");
            }
        } while (opcion != 5);
    }

    private static void crearCita() {
        try {
            System.out.println("\n=== Crear Cita ===");

            System.out.print("Ingrese el folio del contrato: ");
            String folioContrato = scanner.nextLine();

            System.out.print("Ingrese el nombre de usuario del fotógrafo: ");
            String nombreUsuarioFotografo = scanner.nextLine();

            System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD HH:MM): ");
            GregorianCalendar fechaInicio = obtenerFecha();

            System.out.print("Ingrese la fecha de fin (YYYY-MM-DD HH:MM): ");
            GregorianCalendar fechaFin = obtenerFecha();

            System.out.print("Ingrese el lugar de la cita: ");
            String lugar = scanner.nextLine();

            System.out.print("Ingrese los extras: ");
            String extras = scanner.nextLine();

            CitaDTO cita = new CitaDTO();
            cita.setCodigo(GeneradorCodigos.generarCodigo());
            cita.setFechaHoraInicio(fechaInicio);
            cita.setFechaHoraFin(fechaFin);
            cita.setLugar(lugar);
            cita.setExtras(extras);

            CitaDTO citaCreada = gestorCitas.crearCita(cita, folioContrato, nombreUsuarioFotografo);
            System.out.println("✅ Cita creada exitosamente: " + citaCreada.getCodigo());

        } catch (GestorException e) {
            System.err.println("❌ Error al crear la cita: " + e.getMessage());
        }
    }

    private static void consultarCita() {
        System.out.println("\n=== Consultar Cita ===");
        System.out.print("Ingrese el código de la cita: ");
        String codigo = scanner.nextLine();

        CitaDTO cita = new CitaDTO();
        cita.setCodigo(codigo);

        CitaDTO resultado = gestorCitas.consultarCita(cita);
        if (resultado != null) {
            System.out.println("✅ Cita encontrada: " + resultado);
        } else {
            System.out.println("❌ No se encontró la cita con código " + codigo);
        }
    }

    private static void actualizarCita() {
        try {
            System.out.println("\n=== Actualizar Cita ===");
            System.out.print("Ingrese el código de la cita a actualizar: ");
            String codigo = scanner.nextLine();

            CitaDTO cita = new CitaDTO();
            cita.setCodigo(codigo);

            System.out.print("Ingrese la nueva fecha de inicio (YYYY-MM-DD HH:MM, ENTER para mantener): ");
            GregorianCalendar fechaInicio = obtenerFechaOpcional();
            if (fechaInicio != null) cita.setFechaHoraInicio(fechaInicio);

            System.out.print("Ingrese la nueva fecha de fin (YYYY-MM-DD HH:MM, ENTER para mantener): ");
            GregorianCalendar fechaFin = obtenerFechaOpcional();
            if (fechaFin != null) cita.setFechaHoraFin(fechaFin);

            System.out.print("Ingrese el nuevo lugar (ENTER para mantener): ");
            String lugar = scanner.nextLine();
            if (!lugar.isEmpty()) cita.setLugar(lugar);

            System.out.print("Ingrese los nuevos extras (ENTER para mantener): ");
            String extras = scanner.nextLine();
            if (!extras.isEmpty()) cita.setExtras(extras);

            CitaDTO citaActualizada = gestorCitas.actualizarCita(cita);
            System.out.println("✅ Cita actualizada exitosamente: " + citaActualizada.getCodigo());

        } catch (GestorException e) {
            System.err.println("❌ Error al actualizar la cita: " + e.getMessage());
        }
    }

    private static void eliminarCita() {
        try {
            System.out.println("\n=== Eliminar Cita ===");
            System.out.print("Ingrese el código de la cita a eliminar: ");
            String codigo = scanner.nextLine();

            CitaDTO cita = new CitaDTO();
            cita.setCodigo(codigo);

            CitaDTO citaEliminada = gestorCitas.eliminarCita(cita);
            if (citaEliminada != null) {
                System.out.println("✅ Cita eliminada exitosamente.");
            } else {
                System.out.println("❌ No se encontró la cita con código " + codigo);
            }
        } catch (GestorException e) {
            System.err.println("❌ Error al eliminar la cita: " + e.getMessage());
        }
    }

    private static GregorianCalendar obtenerFecha() {
        String fechaStr = scanner.nextLine();
        String[] partes = fechaStr.split("[- :]");
        int year = Integer.parseInt(partes[0]);
        int month = Integer.parseInt(partes[1]) - 1;
        int day = Integer.parseInt(partes[2]);
        int hour = Integer.parseInt(partes[3]);
        int minute = Integer.parseInt(partes[4]);
        return new GregorianCalendar(year, month, day, hour, minute);
    }

    private static GregorianCalendar obtenerFechaOpcional() {
        String fechaStr = scanner.nextLine();
        if (fechaStr.isEmpty()) {
            return null;
        }
        return obtenerFecha();
    }
}