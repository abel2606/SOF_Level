package org.itson.sof.persistencia.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class ContratosDAO implements IContratosDAO {

    private final IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public ContratosDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Contrato> obtenerTotalContratos() throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c";
            List<Contrato> contratos = em.createQuery(jpql, Contrato.class).getResultList();

            contratos.sort(Comparator
                    .comparingInt((Contrato c) -> {
                        switch (c.getEstado()) {
                            case "ACTIVO":
                                return 0;
                            case "TERMINADO":
                                return 1;
                            case "CANCELADO":
                                return 2;
                            default:
                                return 3;
                        }
                    })
                    .thenComparing(c -> c.getCliente().getId()) // cliente_id
                    .thenComparing(c -> c.getTematica().toLowerCase()) // temática alfabética
            );

            return contratos;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Contrato> obtenerContratosPorCliente(Cliente cliente) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c WHERE c.cliente = :cliente";
            List<Contrato> contratos = em.createQuery(jpql, Contrato.class)
                    .setParameter("cliente", cliente)
                    .getResultList();
            return contratos;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Contrato obtenerContratoFolio(String folio) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c WHERE c.folio = :folio";
            return em.createQuery(jpql, Contrato.class)
                    .setParameter("folio", folio)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontro el folio del contrato");
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contrato de persistencia");
        } catch (Exception e) {
            throw new PersistenciaSOFException("No se pudo conector a la base de datos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Contrato crearContrato(Contrato contrato, Cliente clienteInput, Paquete paqueteInput) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = conexion.crearConexion();
            tx = em.getTransaction();
            tx.begin();

            Cliente managedCliente = null;
            if (clienteInput != null && clienteInput.getId() != null) {
                managedCliente = em.find(Cliente.class, clienteInput.getId());
                if (managedCliente == null) {
                    if (tx != null && tx.isActive()) {
                        tx.rollback();
                    }
                    throw new PersistenciaSOFException("Cliente no encontrado.");
                }
            } else {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                throw new PersistenciaSOFException("Datos de Cliente insuficientes.");
            }

            Paquete managedPaquete = null;
            if (paqueteInput != null && paqueteInput.getId() != null) {
                managedPaquete = em.find(Paquete.class, paqueteInput.getId());
                if (managedPaquete == null) {
                    if (tx != null && tx.isActive()) {
                        tx.rollback();
                    }
                    throw new PersistenciaSOFException("Paquete encontrado.");
                }
            } else {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                throw new PersistenciaSOFException("Datos de Paquete insuficientes para crear contrato.");
            }

            contrato.setCliente(managedCliente);
            contrato.setPaquete(managedPaquete);

            em.persist(contrato);

            tx.commit(); // Confirmar la transacción
            logger.log(Level.INFO, "Contrato creado con folio: {0}", contrato.getFolio());
            return contrato; // Retornar el contrato persistido
        } catch (PersistenciaSOFException e) { // Capturar primero la excepción específica que lanzamos
            // No es necesario hacer rollback aquí si ya se hizo antes de lanzar PersistenciaSOFException
            logger.log(Level.WARNING, "Error de negocio al crear contrato: " + e.getMessage());
            throw e; // Re-lanzar la excepción
        } catch (IllegalStateException | javax.persistence.PersistenceException e) { // Otras excepciones de persistencia
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            String mensajeError = "Error al crear el contrato en persistencia: " + e.getMessage();
            logger.log(Level.SEVERE, mensajeError, e);
            throw new PersistenciaSOFException(mensajeError, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Contrato actualizarContrato(Contrato contrato) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = conexion.crearConexion();
            tx = em.getTransaction();
            tx.begin();

            // Buscar el contrato existente por folio
            String jpqlFind = "SELECT c FROM Contrato c WHERE c.folio = :folio";
            TypedQuery<Contrato> query = em.createQuery(jpqlFind, Contrato.class);
            query.setParameter("folio", contrato.getFolio());
            Contrato contratoEncontrado = query.getSingleResult();

            // Actualizar los campos necesarios
            if (contrato.getTematica() != null) {
                contratoEncontrado.setTematica(contrato.getTematica());
            }

            if (contrato.getEstado() != null) {
                contratoEncontrado.setEstado(contrato.getEstado());
            }

            // El objeto ya está en el contexto de persistencia, no es necesario usar merge
            tx.commit();
            logger.log(Level.INFO, "Contrato con folio {0} actualizado correctamente.", contrato.getFolio());
            return contratoEncontrado;

        } catch (NoResultException e) {
            logger.log(Level.WARNING, "Intento de editar un contrato no existente con folio: " + contrato.getFolio());
            throw new PersistenciaSOFException("No se encontró el contrato con folio '" + contrato.getFolio() + "' para editar.");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Contrato cancelarContrato(Contrato contrato) throws PersistenciaSOFException {
        return actualizarEstadoContrato(contrato, "CANCELADO");
    }

    @Override
    public Contrato terminarContrato(Contrato contrato) throws PersistenciaSOFException {

        return actualizarEstadoContrato(contrato, "TERMINADO");

    }

    @Override
    public Contrato actualizarEstadoContrato(Contrato contrato, String nuevoEstado) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = conexion.crearConexion();
            tx = em.getTransaction();
            tx.begin();

            // Buscar el contrato por folio
            String jpql = "SELECT c FROM Contrato c WHERE c.folio = :folio";
            TypedQuery<Contrato> query = em.createQuery(jpql, Contrato.class);
            query.setParameter("folio", contrato.getFolio());
            Contrato contratoEncontrado = query.getSingleResult();

            // Cambiar el estado
            contratoEncontrado.setEstado(nuevoEstado);
            //Indica la fecha de cambio de estado
            contratoEncontrado.setFechaTermino(contrato.getFechaTermino());

            // Guardar cambios (ya es entidad gestionada, no requiere merge)
            tx.commit();

            logger.log(Level.INFO, "Contrato con folio {0} actualizado al estado {1}.", new Object[]{contrato.getFolio(), nuevoEstado});
            return contratoEncontrado;

        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No se encontró el contrato con folio: " + contrato.getFolio());
            throw new PersistenciaSOFException("Contrato no encontrado para folio: " + contrato.getFolio());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar estado del contrato: " + e.getMessage(), e);
            throw new PersistenciaSOFException("Error al actualizar estado del contrato.");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Contrato> cancelarContratosCliente(String correo) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        List<Contrato> contratosModificados = new ArrayList<>();

        try {
            em = conexion.crearConexion(); // Reemplaza 'conexion' con tu forma de obtener el EntityManagerFactory/EntityManager
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Buscar al cliente por su correo electrónico
            Cliente clienteExistente;
            try {
                String jpqlCliente = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
                clienteExistente = em.createQuery(jpqlCliente, Cliente.class)
                        .setParameter("correoCliente", correo)
                        .getSingleResult();
            } catch (NoResultException e) {
                logger.log(Level.WARNING, "No se encontró cliente con correo para cancelar contratos: " + correo);
                throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado: " + correo);
            }

            // 2. Si el cliente existe, buscar sus contratos activos
            String jpqlContratos = "SELECT co FROM Contrato co WHERE co.cliente = :clienteEntidad AND co.estado = :estadoActual";
            List<Contrato> contratosActivosDelCliente = em.createQuery(jpqlContratos, Contrato.class)
                    .setParameter("clienteEntidad", clienteExistente)
                    .setParameter("estadoActual", "ACTIVO")
                    .getResultList();

            if (contratosActivosDelCliente.isEmpty()) {
                return null;
            } else {
                for (Contrato contrato : contratosActivosDelCliente) {
                    contrato.setEstado("CANCELADO"); // Cambiar estado
                    
                    GregorianCalendar fechaTerminoContrato = new GregorianCalendar();
                    fechaTerminoContrato.set(Calendar.HOUR_OF_DAY, 0);
                    fechaTerminoContrato.set(Calendar.MINUTE, 0);
                    fechaTerminoContrato.set(Calendar.SECOND, 0);
                    fechaTerminoContrato.set(Calendar.MILLISECOND, 0);
                    
                    contrato.setFechaTermino(fechaTerminoContrato);
                    contratosModificados.add(contrato);
                }
                logger.log(Level.INFO, "{0} contratos del cliente con correo {1} han sido cambiados a CANCELADO.", new Object[]{contratosModificados.size(), correo});
            }

            transaction.commit();
            return contratosModificados;

        } catch (PersistenciaSOFException e) { // Relanzar excepciones específicas de la capa
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Ya fue logueada o es específica
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error inesperado al cancelar contratos para el cliente con correo: " + correo, e);
            throw new PersistenciaSOFException("Error inesperado al procesar la cancelación de contratos: " + e.getMessage(), e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
