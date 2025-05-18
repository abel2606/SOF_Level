/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Paquete;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author haesp
 */
public class PaquetesDAO implements IPaquetesDAO{
    
    
    
    private final IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public PaquetesDAO(IConexion conexion) {
        this.conexion = conexion;
    }
    
    
    

    @Override
    public Paquete obtenerPaquete(Paquete paquete) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT p FROM Paquete p WHERE p.nombre = :nombre";
            Paquete paqueteEncontrado = em.createQuery(jpql, Paquete.class)
                    .setParameter("nombre", paquete.getNombre())
                    .getSingleResult();
            return paqueteEncontrado;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
}
