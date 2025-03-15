/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Abe
 */
public class CitasDAOTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private IConexion conexion;
    private CitasDAO citasDAO;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("org.itson_SOF_Level_jar_1.0-VERSIONPU");
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        conexion = new Conexion();
        citasDAO = new CitasDAO(conexion);
    }

    @AfterEach
    public void tearDown() {
        em.close();
    }

    @AfterAll
    public static void close() {
        emf.close();
    }

    /**
     * Test of obtenerCita method, of class CitasDAO.
     */
    @org.junit.jupiter.api.Test
    public void testObtenerCita() {
        Contrato contratoExistente = em.find(Contrato.class, 1L);
        Fotografo fotografoExistente = em.find(Fotografo.class, 1L);

        if (contratoExistente == null) {
            throw new RuntimeException("Contrato no encontrado en la base de datos");
        }
        if (fotografoExistente == null) {
            throw new RuntimeException("Fotografo no encontrado en la base de datos");
        }

        Cita cita = new Cita();
        cita.setId(1L);
        cita.setFechaHoraInicio(new GregorianCalendar(2025, 2, 15, 10, 0));
        cita.setFechaHoraFin(new GregorianCalendar(2025, 2, 15, 12, 0));
        cita.setLugar("Estudio Fotográfico");
        cita.setExtras("Quiero mucahs cosas");
        cita.setCodigo("CITA321");

        // Asignar el contrato y fotógrafo existentes
        cita.setContrato(contratoExistente);
        cita.setFotografo(fotografoExistente);

        // Ahora que la cita está persistida, obtenla usando el método de la DAO
        Cita citaObtenida = citasDAO.obtenerCita(cita);

        // Verificar que la cita no sea null y que los datos sean correctos
        assertNotNull(citaObtenida);
        assertEquals(cita.getId(), citaObtenida.getId());
        assertEquals("Estudio Fotográfico", citaObtenida.getLugar());
        assertEquals("CITA123", citaObtenida.getCodigo());
        assertEquals(fotografoExistente.getNombrePersona(), citaObtenida.getFotografo().getNombrePersona());
        assertEquals(contratoExistente.getFolio(), citaObtenida.getContrato().getFolio());
    }

    /**
     * Test of agregarCita method, of class CitasDAO.
     */
    @org.junit.jupiter.api.Test
    public void testAgregarCita() {
        System.out.println("Prueba: agregarCita");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCorreo("juan@example.com");
        cliente.setNombre("Juan Pérez");
        cliente.setTelefono("555-1234");

        // Crear y persistir el contrato si no existe en la base de datos
        Contrato contrato = new Contrato();
        contrato.setFolio("FOLIO123");
        contrato.setTematica("Boda");
        contrato.setEstado("Activo");
        contrato.setCliente(cliente);
        EntityManager em = conexion.crearConexion();
        em.getTransaction().begin();
        em.persist(contrato);
        em.getTransaction().commit();

        // Crear y persistir el fotógrafo si no existe
        Fotografo fotografo = new Fotografo();
        fotografo.setNombrePersona("Carlos López");
        fotografo.setCorreo("carlos@example.com");
        fotografo.setTelefono("555-6789");
        em.getTransaction().begin();
        em.persist(fotografo);
        em.getTransaction().commit();

        Cita cita = new Cita();
        cita.setFechaHoraInicio(new GregorianCalendar(2025, 2, 15, 10, 0));
        cita.setFechaHoraFin(new GregorianCalendar(2025, 2, 15, 12, 0));
        cita.setLugar("Estudio Fotográfico");
        cita.setExtras("Sesión familiar");
        cita.setCodigo("CITA123");

        cita.setContrato(contrato);
        cita.setFotografo(fotografo);

        Cita resultado = citasDAO.agregarCita(cita);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Estudio Fotográfico", resultado.getLugar());
    }

    /**
     * Test of actualizarCita method, of class CitasDAO.
     */
    @org.junit.jupiter.api.Test
    public void testActualizarCita() {
        System.out.println("Prueba: actualizarCita");

        Cita citaExistente = em.createQuery("SELECT c FROM Cita c", Cita.class)
                .setMaxResults(1) // Traer la primera cita
                .getSingleResult();

        assertNotNull(citaExistente);

        citaExistente.setLugar("Nuevo Estudio Fotográfico");
        citaExistente.setExtras("Sesión de retratos familiares");

        Cita citaActualizada = citasDAO.actualizarCita(citaExistente);

        assertNotNull(citaActualizada);
        assertEquals(citaExistente.getId(), citaActualizada.getId());
        assertEquals("Nuevo Estudio Fotográfico", citaActualizada.getLugar());
        assertEquals("Sesión de retratos familiares", citaActualizada.getExtras());
    }

    /**
     * Test of eliminarcita method, of class CitasDAO.
     */
    @org.junit.jupiter.api.Test
    public void testEliminarcita() {
        System.out.println("eliminarcita");

        Cita citaExistente = em.createQuery("SELECT c FROM Cita c", Cita.class)
                .setMaxResults(1)
                .getSingleResult();

        assertNotNull(citaExistente);

        Cita citaEliminada = citasDAO.eliminarcita(citaExistente);

        Cita citaBuscada = em.find(Cita.class, citaExistente.getId());
        assertNull(citaBuscada, "La cita no fue eliminada correctamente.");
    }

}
