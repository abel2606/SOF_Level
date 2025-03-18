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
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            String jpql = "SELECT f FROM Fotografo f";
            List<Fotografo> fotografos = em.createQuery(jpql, Fotografo.class)
                    .getResultList();

            transaction.commit();
            return fotografos;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener fotógrafos", e);
            throw new PersistenciaSOFException("Error al obtener fotógrafos de persistencia");
        } finally {
            em.close();
        }
        
    }
    
}
