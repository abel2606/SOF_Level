/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Fotografo;

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

//            Contrato contratoExistente = em.find(Contrato.class, cita.getContrato().getFolio());
//            Fotografo fotografoExistente = em.find(Fotografo.class, cita.getFotografo().getNombreUsuario());
//
//            if (contratoExistente != null) {
//                cita.setContrato(contratoExistente);
//            } else {
//                throw new RuntimeException("Contrato no encontrado");
//            }
//
//            if (fotografoExistente != null) {
//                cita.setFotografo(fotografoExistente);
//            } else {
//                throw new RuntimeException("Fotógrafo no encontrado");
//            }

            cita.getContrato().setId(Long.parseLong("1"));
            cita.getFotografo().setId(Long.parseLong("1"));
            em.persist(cita);

            transaction.commit();
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

            if (cita.getContrato() != null) {
                Contrato contratoExistente = em.find(Contrato.class, cita.getContrato().getId());
                if (contratoExistente != null) {
                    citaExistente.setContrato(contratoExistente);
                } else {
                    throw new RuntimeException("Contrato no encontrado");
                }
            }

            if (cita.getFotografo() != null) {
                Fotografo fotografoExistente = em.find(Fotografo.class, cita.getFotografo().getId());
                if (fotografoExistente != null) {
                    citaExistente.setFotografo(fotografoExistente);
                } else {
                    throw new RuntimeException("Fotógrafo no encontrado");
                }
            }

            Cita resultado = em.merge(citaExistente);

            transaction.commit();
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
