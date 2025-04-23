package org.itson.sof.persistencia.daos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author JazmE
 */
public class MaterialesDAO implements IMaterialesDAO {

    private final IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public MaterialesDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Material> obtenerTodosMateriales() throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT m FROM Material m";
            List<Material> contratos = em.createQuery(jpql, Material.class).getResultList();
            return contratos;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener contratos", e);
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia: " + e.getMessage());
        } finally {
           if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<CitaMaterial> obtenerMaterialesCita(String codigo) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            // Paso 1: Obtener el id de la cita usando el código
            String jpqlCita = "SELECT c.id FROM Cita c WHERE c.codigo = :codigo";
            Long citaId = em.createQuery(jpqlCita, Long.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();

            // Paso 2: Obtener los CitaMaterial que están relacionados con la cita
            String jpqlMateriales = "SELECT cm FROM CitaMaterial cm WHERE cm.cita.id = :id";
            List<CitaMaterial> citaMateriales = em.createQuery(jpqlMateriales, CitaMaterial.class)
                    .setParameter("id", citaId)
                    .getResultList();

            return citaMateriales;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener los materiales de la cita", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Material obtenerMaterialPorNombre(String nombre) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT m FROM Material m WHERE LOWER(m.nombre) = :nombre";
            return em.createQuery(jpql, Material.class)
                    .setParameter("nombre", nombre.toLowerCase())
                    .getSingleResult();
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al buscar material por nombre", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarMaterial(Material material) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            em.getTransaction().begin();
            em.merge(material);
            em.getTransaction().commit();
        } catch (PersistenciaSOFException e) {
            if(em!=null && em.getTransaction()!=null){
                em.getTransaction().rollback();
            }
            throw new PersistenciaSOFException("Error al actualizar el material", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
