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
import org.itson.sof.persistencia.entidades.Fotografo;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class FotografoDAO implements IFotografoDAO {

    IConexion conexion;
    static final Logger logger = Logger.getLogger(FotografoDAO.class.getName());

    
    public FotografoDAO(IConexion conexion) {
        this.conexion = conexion;
    }
    
    @Override
    public List<Fotografo> obtenerTodosFotografos() throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            
            transaction.begin();

            String jpql = "SELECT f FROM Fotografo f";
            List<Fotografo> fotografos = em.createQuery(jpql, Fotografo.class)
                    .getResultList();

            transaction.commit();
            return fotografos;
        } catch (PersistenciaSOFException e) {
            logger.log(Level.SEVERE, "Error al obtener fotógrafos", e);
            throw new PersistenciaSOFException("Error al obtener fotógrafos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        
    }

    @Override
    public Fotografo obtenerFotografoNombreUsuario(String nombreUsuario) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
             em = conexion.crearConexion();
            String jpql = "SELECT f FROM Fotografo f WHERE f.nombreUsuario = :nombreUsuario";
            return em.createQuery(jpql, Fotografo.class)
                    .setParameter("nombreUsuario", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No se encontró el fotógrafo con usuario: " + nombreUsuario, e);
            throw new PersistenciaSOFException ("No se encontró el fotógrafo");
        } catch (PersistenciaSOFException e) {
            logger.log(Level.SEVERE, "Error al obtener fotógrafo por nombre de usuario", e);
            throw new PersistenciaSOFException("Error al obtener fotógrafo");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    
    
}
