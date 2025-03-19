/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import org.itson.sof.persistencia.conexion.IConexion;
import static org.itson.sof.persistencia.daos.ContratosDAO.logger;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abel
 */
public class CitasDAO implements ICitasDAO {

    private IConexion conexion;
    static final Logger logger = Logger.getLogger(CitasDAO.class.getName());

    public CitasDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public Cita obtenerCitaCodigo(String codigo) throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigoCita";
            Cita citaDeCodigo = em.createQuery(jpql, Cita.class)
                    .setParameter("codigoCita", codigo).getSingleResult();

            transaction.commit();
            logger.info("Cita obtenida: ID(" + citaDeCodigo.getId() + ")");
            return citaDeCodigo;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener las citas del contrato", e);
            throw new PersistenciaSOFException("Error al obtener las citas de los contratos");
        } finally {
            em.close();
        }

    }

    @Override
    public List<Cita> obtenerCitasContratos(long contratoId) throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            String jpql = "SELECT c FROM Cita c WHERE c.contrato.id = :contratoId";
            List<Cita> citasDeContrato = em.createQuery(jpql, Cita.class)
                    .setParameter("contratoId", contratoId)
                    .getResultList();

            transaction.commit();

            return citasDeContrato;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener las citas del contrato", e);
            throw new PersistenciaSOFException("Error al obtener las citas de los contratos");
        } finally {
            em.close();
        }
    }

    @Override
    public Cita obtenerCita(Cita cita) {
        EntityManager em = conexion.crearConexion();
        try {
            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigo";
            return em.createQuery(jpql, Cita.class)
                    .setParameter("codigo", cita.getCodigo()) // Extrae el código del objeto
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No se encontró la cita con código: " + cita.getCodigo(), e);
            return null;  // Puedes lanzar una excepción si prefieres
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener la cita por código", e);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Cita agregarCita(Cita cita) {
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            em.persist(cita);

            transaction.commit();
            logger.info("Cita agregada correctamente: ID(" + cita.getId() + ")");

            return cita;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al agregar la cita", e);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Cita actualizarCita(Cita cita) {
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Cita citaExistente = obtenerCita(cita);

            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada");
            }

            // Solo actualiza los valores que no sean nulos
            if (cita.getFechaHoraInicio() != null) {
                citaExistente.setFechaHoraInicio(cita.getFechaHoraInicio());
            }
            if (cita.getFechaHoraFin() != null) {
                citaExistente.setFechaHoraFin(cita.getFechaHoraFin());
            }
            if (cita.getLugar() != null) {
                citaExistente.setLugar(cita.getLugar());
            }
            if (cita.getExtras() != null) {
                citaExistente.setExtras(cita.getExtras());
            }
            if (cita.getCodigo() != null) {
                citaExistente.setCodigo(cita.getCodigo());
            }
            if (cita.getFotografo() != null) {
                citaExistente.setFotografo(cita.getFotografo());
            }

            Cita resultado = em.merge(citaExistente);

            transaction.commit();
            logger.info("Cita actualizada correctamente: ID(" + cita.getId() + ")");
            return resultado;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar la cita", e);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Cita eliminarcita(Cita cita) {
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Buscar la cita por código
            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigo";
            Cita citaExistente = em.createQuery(jpql, Cita.class)
                    .setParameter("codigo", cita.getCodigo())
                    .getSingleResult();

            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada con código: " + cita.getCodigo());
            }

            // Se elimina la entidad administrada por el EntityManager
            em.remove(citaExistente);

            transaction.commit();
            logger.info("Cita eliminada correctamente: Código(" + cita.getCodigo() + ")");
            return citaExistente;

        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No se encontró una cita con código: " + cita.getCodigo(), e);
            return null;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar la cita", e);
            return null;
        } finally {
            em.close();
        }
    }

}
