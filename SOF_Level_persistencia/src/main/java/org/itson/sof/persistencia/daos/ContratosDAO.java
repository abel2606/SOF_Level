package org.itson.sof.persistencia.daos;

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
    public Contrato crearContrato(Contrato contrato, Cliente cliente, Paquete paquete) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = conexion.crearConexion();
            tx = em.getTransaction();
            tx.begin();

            contrato.setCliente(cliente);
            contrato.setPaquete(paquete);

            em.persist(contrato); // Persistir el objeto contrato

            tx.commit(); // Confirmar la transacción
            logger.log(Level.INFO, "Contrato creado con folio: {0}", contrato.getFolio());
            return contrato; // Retornar el contrato persistido (ahora con ID si es autogenerado)
        } catch (PersistenciaSOFException | IllegalStateException | javax.persistence.PersistenceException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Revertir la transacción en caso de error
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

}
