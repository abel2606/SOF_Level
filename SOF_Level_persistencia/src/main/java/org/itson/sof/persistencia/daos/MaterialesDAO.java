/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author JazmE
 */
public class MaterialesDAO implements IMaterialesDAO{
    
     private final IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public MaterialesDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Material> obtenerTodosMateriales() throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
        try {
            String jpql = "SELECT m FROM Material m";
            List<Material> contratos = em.createQuery(jpql, Material.class).getResultList();
            return contratos;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener contratos", e);
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Material> obtenerMaterialesCita(Long id) throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
        try {
            // Crear la consulta JPQL para obtener los materiales asociados a la cita con el id especificado
            String jpql = "SELECT m FROM Material m JOIN m.citas c WHERE c.id = :id";

            // Ejecutar la consulta utilizando EntityManager
            List<Material> materiales = em.createQuery(jpql, Material.class)
                    .setParameter("id", id)
                    .getResultList();

            // Retornar los materiales obtenidos
            return materiales;

        } catch (Exception e) {
            // Manejar cualquier error relacionado con la persistencia
            throw new PersistenciaSOFException("Error al obtener los materiales de la cita", e);
        }
    }

}
