/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
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
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigoCita";
            Cita citaDeCodigo = em.createQuery(jpql, Cita.class)
                    .setParameter("codigoCita", codigo).getSingleResult();

            transaction.commit();
            logger.log(Level.INFO, "Cita obtenida: ID({0})", citaDeCodigo.getId());
            return citaDeCodigo;
        } catch (PersistenciaSOFException e) {
            if (transaction!= null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener las citas del contrato", e);
            throw new PersistenciaSOFException("Error al obtener las citas de los contratos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    @Override
    public List<Cita> obtenerCitasContratos(long contratoId) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();
            
            String jpql = "SELECT c FROM Cita c WHERE c.contrato.id = :contratoId";
            List<Cita> citasDeContrato = em.createQuery(jpql, Cita.class)
                    .setParameter("contratoId", contratoId)
                    .getResultList();

            transaction.commit();

            return citasDeContrato;
        } catch (PersistenciaSOFException e) {
            if (transaction!= null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener las citas del contrato", e);
            throw new PersistenciaSOFException("Error al obtener las citas de los contratos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cita obtenerCita(Cita cita) throws PersistenciaSOFException{
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigo";
            return em.createQuery(jpql, Cita.class)
                    .setParameter("codigo", cita.getCodigo())
                    .getSingleResult();

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró la cita que se buscaba");

        } catch (Exception e) {
            throw new PersistenciaSOFException("Error desconocido");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cita agregarCita(Cita cita) throws PersistenciaSOFException {
        
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();

            if (cita.getLugar() == null || cita.getLugar().isEmpty()) {
                cita.setLugar("PENDIENTE");
            }

            // Relacionar cada CitaMaterial con la cita
            for (CitaMaterial cm : cita.getCitaMateriales()) {
                cm.setCita(cita);
            }

            transaction.begin();
            em.persist(cita);
            transaction.commit();

            return cita;

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (Exception e) {
            throw new PersistenciaSOFException("Error al agregar la cita");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cita actualizarCita(Cita cita)throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion(); 
            transaction = em.getTransaction();

            transaction.begin();

            Cita citaExistente = obtenerCita(cita); 
            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada");
            }

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

            if (cita.getCitaMateriales() != null) {
                TypedQuery<CitaMaterial> queryCM = em.createQuery(
                        "SELECT cm FROM CitaMaterial cm WHERE cm.cita.id = :citaId", CitaMaterial.class);
                queryCM.setParameter("citaId", citaExistente.getId());
                List<CitaMaterial> anteriores = queryCM.getResultList();

                for (CitaMaterial cm : anteriores) {
                    Material matBD = em.find(Material.class, cm.getMaterial().getId());
                    if (matBD != null) {
                        matBD.setCantidad(matBD.getCantidad() + cm.getCantidad());
                        em.merge(matBD);
                    }
                    em.remove(cm);
                }

                citaExistente.getCitaMateriales().clear();

                for (CitaMaterial citaMaterial : cita.getCitaMateriales()) {
                    TypedQuery<Material> query = em.createQuery(
                            "SELECT m FROM Material m WHERE m.nombre = :nombre", Material.class);
                    query.setParameter("nombre", citaMaterial.getMaterial().getNombre());

                    List<Material> encontrados = query.getResultList();
                    if (encontrados.isEmpty()) {
                        throw new RuntimeException("Material no encontrado: " + citaMaterial.getMaterial().getNombre());
                    }

                    Material material = encontrados.get(0);

                    if (material.getCantidad() < citaMaterial.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para: " + material.getNombre());
                    }

                    material.setCantidad(material.getCantidad() - citaMaterial.getCantidad());
                    em.merge(material);

                    citaMaterial.setCita(citaExistente);
                    citaMaterial.setMaterial(material);

                    em.persist(citaMaterial);
                    citaExistente.getCitaMateriales().add(citaMaterial);
                }
            }

            Cita resultado = em.merge(citaExistente);
            transaction.commit();
            return resultado;

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Cita eliminarcita(Cita cita)throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion(); // puede lanzar ConexionBDException
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cita c WHERE c.codigo = :codigo";
            Cita citaExistente = em.createQuery(jpql, Cita.class)
                    .setParameter("codigo", cita.getCodigo())
                    .getSingleResult();

            if (citaExistente == null) {
                throw new RuntimeException("Cita no encontrada con código: " + cita.getCodigo());
            }

            List<CitaMaterial> materialesAsociados = em.createQuery(
                    "SELECT cm FROM CitaMaterial cm WHERE cm.cita = :cita", CitaMaterial.class)
                    .setParameter("cita", citaExistente)
                    .getResultList();

            for (CitaMaterial cm : materialesAsociados) {
                Material material = cm.getMaterial();
                float cantidadUsada = cm.getCantidad();

                material.setCantidad(material.getCantidad() + cantidadUsada);
                em.merge(material);
                em.remove(cm);
            }

            em.remove(citaExistente);
            transaction.commit();

            logger.log(Level.INFO, "Cita eliminada y materiales reabastecidos: C\u00f3digo({0})", cita.getCodigo());
            return citaExistente;

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró la cita que se buscaba");

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Cita> obtenerCitasFecha(Cita cita) throws PersistenciaSOFException {
        EntityManager em = null;
        List<Cita> citas = new ArrayList<>();

        try {
            em = conexion.crearConexion();

            GregorianCalendar fechaInicio = cita.getFechaHoraInicio();
            GregorianCalendar fechaFin = cita.getFechaHoraFin();

            TypedQuery<Cita> query = em.createQuery(
                    "SELECT c FROM Cita c WHERE "
                    + "c.codigo <> :codigo AND "
                    + "(:inicio < c.fechaHoraFin AND :fin > c.fechaHoraInicio)", Cita.class
            );

            query.setParameter("codigo", cita.getCodigo());
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);

            citas = query.getResultList();

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (Exception e) {
            throw new PersistenciaSOFException("Error desocnocido");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return citas;
    }

    @Override
    public List<Cita> obtenerCitasPorFecha(String fecha) throws PersistenciaSOFException {
        List<Cita> citas = new ArrayList<>();
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Cita c WHERE FUNCTION('DATE', c.fechaHoraInicio) = :fecha";
            TypedQuery<Cita> query = em.createQuery(jpql, Cita.class);
            query.setParameter("fecha", fecha);
            citas = query.getResultList();
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException(e);
        } finally {
           if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return citas;
    }

}
