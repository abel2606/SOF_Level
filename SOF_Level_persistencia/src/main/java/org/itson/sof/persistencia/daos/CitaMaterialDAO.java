/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author haesp
 */
public class CitaMaterialDAO implements ICitaMaterialDAO {

    private IConexion conexion;
    static final Logger logger = Logger.getLogger(CitaMaterialDAO.class.getName());

    public CitaMaterialDAO(IConexion conexion) {
        this.conexion = conexion;
    }
    
    

    @Override
    public void agregarCitaMaterial(Cita cita, Material material) throws PersistenciaSOFException {

        EntityManager em = conexion.crearConexion();

        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Buscar el material real en la base de datos
            String jpql = "SELECT m FROM Material m WHERE m.nombre = :nombre";
            Material materialBD = em.createQuery(jpql, Material.class)
                    .setParameter("nombre", material.getNombre())
                    .getSingleResult();

            // Crear la relación CitaMaterial
            CitaMaterial citaMaterial = new CitaMaterial();
            citaMaterial.setCita(cita);
            citaMaterial.setMaterial(materialBD);
            citaMaterial.setCantidad(material.getCantidad()); // cantidad a usar en la cita

            em.persist(citaMaterial);

            tx.commit();
        } catch (NoResultException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaSOFException("Material no encontrado: " + material.getNombre(), e);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaSOFException("Error al agregar cita-material", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarCitaMaterial(CitaMaterial citaMaterial) throws PersistenciaSOFException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void consultaCitaMaterial(Cita cita, Material material) throws PersistenciaSOFException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarCitaMaterial(CitaMaterial citaMaterial) throws PersistenciaSOFException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
