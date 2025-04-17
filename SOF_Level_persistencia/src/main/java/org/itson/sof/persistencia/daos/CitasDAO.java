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

        if (cita.getLugar() == null || cita.getLugar() == "") {
            cita.setLugar("PENDIENTE");
        }

        try {
            transaction.begin();

            em.persist(cita);

            transaction.commit();
            logger.info("Cita agregada correctamente: ID(" + cita.getId() + ")");

            return cita;

        } catch (Exception e) {

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
            logger.info("Cita actualizada correctamente: ID(" + resultado.getId() + ")");
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

            // Recuperar los CitaMaterial relacionados a esa cita
            List<CitaMaterial> materialesAsociados = em.createQuery(
                    "SELECT cm FROM CitaMaterial cm WHERE cm.cita = :cita", CitaMaterial.class)
                    .setParameter("cita", citaExistente)
                    .getResultList();

            // Restablecer el stock de cada material
            for (CitaMaterial cm : materialesAsociados) {
                Material material = cm.getMaterial();
                float cantidadUsada = cm.getCantidad();

                // Sumar la cantidad al stock actual
                material.setCantidad(material.getCantidad() + cantidadUsada);

                // Persistir el cambio
                em.merge(material);

                // Eliminar la relación cita-material
                em.remove(cm);
            }

            // Eliminar la cita
            em.remove(citaExistente);

            transaction.commit();
            logger.info("Cita eliminada y materiales reabastecidos: Código(" + cita.getCodigo() + ")");
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

    @Override
    public List<Cita> obtenerCitasFecha(Cita cita) {

        EntityManager em = conexion.crearConexion();
        List<Cita> citas = new ArrayList<>();

        try {
            GregorianCalendar fechaInicio = cita.getFechaHoraInicio();
            GregorianCalendar fechaFin = cita.getFechaHoraFin();

            GregorianCalendar unaHoraAntes = (GregorianCalendar) fechaInicio.clone();
            unaHoraAntes.add(Calendar.MINUTE, -29);

            GregorianCalendar unaHoraDespues = (GregorianCalendar) fechaFin.clone();
            unaHoraDespues.add(Calendar.MINUTE, 30);

            TypedQuery<Cita> query = em.createQuery(
                    "SELECT c FROM Cita c WHERE "
                    + "c.codigo <> :codigo AND ("
                    + "(c.fechaHoraInicio BETWEEN :inicio AND :fin "
                    + "OR c.fechaHoraFin BETWEEN :inicio AND :fin "
                    + "OR (:inicio BETWEEN c.fechaHoraInicio AND c.fechaHoraFin) "
                    + "OR (:fin BETWEEN c.fechaHoraInicio AND c.fechaHoraFin)) "
                    + "OR (c.fechaHoraFin BETWEEN :antes AND :inicio) "
                    + "OR (c.fechaHoraInicio BETWEEN :fin AND :despues))", Cita.class
            );

            query.setParameter("codigo", cita.getCodigo());  // Excluir la cita con el mismo código
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setParameter("antes", unaHoraAntes);
            query.setParameter("despues", unaHoraDespues);

            citas = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return citas;

    }

    @Override
    public List<Cita> obtenerCitasPorFecha(String fecha) throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
        List<Cita> citas = new ArrayList<>();

        try {
            String jpql = "SELECT c FROM Cita c WHERE FUNCTION('DATE', c.fechaHoraInicio) = :fecha";
            TypedQuery<Cita> query = em.createQuery(jpql, Cita.class);
            query.setParameter("fecha", fecha);
            citas = query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener citas por fecha", e);
            throw new PersistenciaSOFException(e);
        } finally {
            em.close();
        }

        return citas;
    }

}
