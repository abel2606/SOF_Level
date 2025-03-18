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
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            cita.setId(Long.parseLong("1"));
            Cita result = em.find(Cita.class, cita.getId());
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener la cita", e);
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

            Cita citaExistente = em.find(Cita.class, cita.getId());

            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada");
            }

            citaExistente.setFechaHoraInicio(cita.getFechaHoraInicio());
            citaExistente.setFechaHoraFin(cita.getFechaHoraFin());
            citaExistente.setLugar(cita.getLugar());
            citaExistente.setExtras(cita.getExtras());
            citaExistente.setCodigo(cita.getCodigo());
            citaExistente.setFotografo(cita.getFotografo());

            Cita resultado = em.merge(citaExistente);

            transaction.commit();
            logger.info("Cita actualizada correctamente: ID("+cita.getId()+")");
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

            Cita citaExistente = em.find(Cita.class, cita.getId());

            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada");
            }

            em.remove(citaExistente);

            transaction.commit();
            logger.info("Cita eliminada correctamente: ID("+cita.getId()+")");
            return citaExistente;

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
