package org.itson.sof.persistencia.pruebas;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.CitasDAO;
import org.itson.sof.persistencia.daos.ContratosDAO;
import org.itson.sof.persistencia.daos.FotografoDAO;
import org.itson.sof.persistencia.daos.ICitasDAO;
import org.itson.sof.persistencia.daos.IContratosDAO;
import org.itson.sof.persistencia.daos.IFotografoDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

public class PruebaMapeo {

    static final Logger logger = Logger.getLogger(PruebaMapeo.class.getName());

    public static void main(String[] args) throws PersistenciaSOFException {
        boolean salir = false;
        Scanner sc = new Scanner(System.in);
        IConexion conexion;
        conexion = new Conexion();

        IContratosDAO contratosDAO = new ContratosDAO(conexion);
        ICitasDAO citasDAO = new CitasDAO(conexion);
        IFotografoDAO fotografosDAO = new FotografoDAO(conexion);

        System.out.println("*****************");
        System.out.println("Total de contratos:");

        List<Contrato> contratos = new LinkedList<>();
        try {
            contratos = contratosDAO.obtenerTotalContratos();
            for (Contrato contrato : contratos) {
                System.out.println(
                        "ID: "
                        + contrato.getId()
                        + " Folio: "
                        + contrato.getFolio()
                        + " Cliente: "
                        + contrato.getCliente().getNombre());
            }

        } catch (PersistenciaSOFException ex) {
            System.out.println("No hola");
            Logger.getLogger(PruebaMapeo.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Selecciona mediante el id alguno de los contratos");
        long idSeleccionadoContrato = sc.nextLong();
        sc.nextLine();
        Contrato contratoSeleccionado = null;

        for (Contrato contrato : contratos) {
            if (contrato.getId() == idSeleccionadoContrato) {
                contratoSeleccionado = contrato;
            } else {
                System.out.println("No es");
            }
        }

        //Aquí empezamos a realizar los procesos de las citas
        while (!salir) {
            System.out.println("*******************");
            System.out.println("Administrar citas de contrato:" + contratoSeleccionado.getFolio());
            System.out.println("Selecciona una opcion");
            System.out.println("1.-Agregar Cita");
            System.out.println("2.-Obtener todas las citas");
            System.out.println("3.-Obtener citas mediante codigo");
            System.out.println("4.-Eliminar Cita");
            System.out.println("5.-Actualizar cita");
            System.out.println("6.-Salir");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    Cita citaNueva = new Cita();
                    //Se implementara algo para que se generen codigos aleatorios
                    citaNueva.setCodigo("FOTO123");
                    citaNueva.setContrato(contratoSeleccionado);
                    citaNueva.setExtras("Que tengo flores");
                    citaNueva.setFechaHoraInicio(new GregorianCalendar(2025, 05, 12, 12, 0));
                    citaNueva.setFechaHoraFin(new GregorianCalendar(2025, 05, 12, 13, 0));
                    List<Fotografo> fotografos = fotografosDAO.obtenerTodosFotografos();
                    System.out.println("Fotógrafos disponibles:");
                    for (Fotografo fotografo : fotografos) {
                        System.out.println("ID: " + fotografo.getId() + " - Nombre: " + fotografo.getNombrePersona() + " | Usuario: " + fotografo.getNombreUsuario());
                    }

                    System.out.println("Ingresa el ID del fotógrafo para la cita:");
                    long idFotografo = sc.nextLong();
                    sc.nextLine();

                    Fotografo fotografoSeleccionado = fotografos.stream()
                            .filter(f -> f.getId().equals(idFotografo))
                            .findFirst()
                            .orElse(null);

                    if (fotografoSeleccionado != null) {
                        citaNueva.setFotografo(fotografoSeleccionado);
                    } else {
                        System.out.println("Fotógrafo no encontrado.");
                        break;
                    }

                    citaNueva.setLugar("Estudio");

                    citasDAO.agregarCita(citaNueva);
                    break;
                case 2:
                    System.out.println("Todas las citas del contrato " + contratoSeleccionado.getFolio() + ":");
                    List<Cita> todasLasCitas = citasDAO.obtenerCitasContratos(contratoSeleccionado.getId());
                    for (Cita cita : todasLasCitas) {
                        System.out.println("ID: " + cita.getId() + " | Código: " + cita.getCodigo());
                    }
                    break;
                case 3:
                    System.out.println("Ingresa el código de la cita:");
                    String codigoCita = sc.nextLine();
                    Cita citaPorCodigo = citasDAO.obtenerCitaCodigo(codigoCita);
                    if (citaPorCodigo != null) {
                        System.out.println("Cita encontrada: " + citaPorCodigo.getCodigo());
                    } else {
                        System.out.println("Cita no encontrada.");
                    }
                    break;

                case 4:
                    System.out.println("Selecciona la cita que deseas eliminar:");
                    for (Cita citaExistente : citasDAO.obtenerCitasContratos(contratoSeleccionado.getId())) {
                        System.out.println("ID: " + citaExistente.getId() + " | Código: " + citaExistente.getCodigo());
                    }

                    long idCitaEliminar = sc.nextLong();
                    sc.nextLine();

                    // Buscar la cita por ID
                    Cita citaEliminar = null;
                    for (Cita citaExistente : citasDAO.obtenerCitasContratos(contratoSeleccionado.getId())) {
                        if (citaExistente.getId() == idCitaEliminar) {
                            citaEliminar = citaExistente;
                        }
                    }

                    if (citaEliminar == null) {
                        System.out.println("Cita no encontrada.");
                        break;
                    }

                    // Eliminar la cita en la base de datos
                    Cita citaEliminada = citasDAO.eliminarcita(citaEliminar);

                    if (citaEliminada != null) {
                        logger.info("Cita eliminada correctamente: ID(" + citaEliminada.getId() + ")");
                        System.out.println("Cita eliminada correctamente.");
                    } else {
                        System.out.println("Error al eliminar la cita.");
                    }
                    break;
                case 5:
                    System.out.println("Selecciona la cita que deseas actualizar:");
                    for (Cita citaExistente : citasDAO.obtenerCitasContratos(contratoSeleccionado.getId())) {
                        System.out.println("ID: " + citaExistente.getId() + " | Código: " + citaExistente.getCodigo());
                    }

                    long idCitaActualizar = sc.nextLong();
                    sc.nextLine();

                    Cita citaActualizar = null;
                    for (Cita citaExistente : citasDAO.obtenerCitasContratos(contratoSeleccionado.getId())) {
                        if (citaExistente.getId() == idCitaActualizar) {
                            citaActualizar = citaExistente;
                        }
                    }

                    if (citaActualizar == null) {
                        System.out.println("Cita no encontrada.");
                        break;
                    }

                    System.out.println("Ingresa el nuevo código para la cita:");
                    String nuevoCodigo = sc.nextLine();
                    citaActualizar.setCodigo(nuevoCodigo);

                    Cita citaActualizada = citasDAO.actualizarCita(citaActualizar);

                    if (citaActualizada != null) {
                        logger.info("Cita actualizada correctamente: ID(" + citaActualizada.getId() + ")");
                        System.out.println("Cita actualizada correctamente.");
                    } else {
                        System.out.println("Error al actualizar la cita.");
                    }
                    break;

                case 6:
                    salir = true;
                    break;

                default:
                    throw new AssertionError();
            }
        }

//        // Crear un Cliente
//        Cliente cliente = new Cliente();
//        cliente.setNombre("Juan Pérez");
//        cliente.setTelefono("555-1234");
//        cliente.setCorreo("juan.perez@correo.com");
//
//        //Crear algunos contratos
//        Contrato contrato1 = new Contrato();
//        contrato1.setTematica("Contrato de Fotografía Básico");
//        contrato1.setCliente(cliente);
//
//        Contrato contrato2 = new Contrato();
//        contrato2.setTematica("Contrato de Fotografía Avanzado");
//        contrato2.setCliente(cliente);
//
//        //Añadir los contratos al cliente
//        List<Contrato> contratos = new ArrayList<>();
//        contratos.add(contrato1);
//        contratos.add(contrato2);
//        //cliente.setContratos((Set<Contrato>) contratos);
//
//        // Persistir el Cliente (esto también persistirá los Contratos debido a la relación CascadeType.ALL)
//        em.persist(cliente);
//
//        // Confirmar la transacción
//        em.getTransaction().commit();
//
//        // Verificar si el cliente fue guardado correctamente
//        Cliente clientePersistido = em.find(Cliente.class, cliente.getId());
//        System.out.println("Cliente guardado: " + clientePersistido.getNombre());
//
//        // Verificar si los contratos se guardaron
//        /*
//        for (Contrato contrato : clientePersistido.getContratos()) {
//            System.out.println("Contrato de cliente: " + contrato.getTematica());
//        }*/
//
//        // Cerrar el EntityManager
//        em.close();
    }
}
