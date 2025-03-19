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
import javax.persistence.NoResultException;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class ContratosDAO implements IContratosDAO {

    private IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public ContratosDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Contrato> obtenerTotalContratos() throws PersistenciaSOFException{
        EntityManager em = conexion.crearConexion();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            
            String jpql = "SELECT c FROM Contrato c";
            List<Contrato> contratos = em.createQuery(jpql, Contrato.class)
                    .getResultList();

            transaction.commit();
            return contratos;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener contratos", e);
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            em.close();
        }
    }

    @Override
    public Contrato obtenerContratoFolio(String folio) throws PersistenciaSOFException {
        EntityManager em = conexion.crearConexion();
    try {
        String jpql = "SELECT c FROM Contrato c WHERE c.folio = :folio";
        return em.createQuery(jpql, Contrato.class)
                .setParameter("folio", folio)
                .getSingleResult();
    } catch (NoResultException e) {
        logger.log(Level.WARNING, "No se encontró el contrato con folio: " + folio, e);
        throw new PersistenciaSOFException("No se encontro el folio del contrato");
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error al obtener contrato por folio", e);
        throw new PersistenciaSOFException("Error al obtener contrato");
    } finally {
        em.close();
    }
    }
    
    
    

}
