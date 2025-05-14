package org.itson.sof.persistencia.daos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class ContratosDAO implements IContratosDAO {

    private final IConexion conexion;
    static final Logger logger = Logger.getLogger(ContratosDAO.class.getName());

    public ContratosDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Contrato> obtenerTotalContratos() throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c";
            List<Contrato> contratos = em.createQuery(jpql, Contrato.class).getResultList();
            return contratos;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Contrato> obtenerContratosPorCliente(Cliente cliente) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c WHERE c.cliente = :cliente";
            List<Contrato> contratos = em.createQuery(jpql, Contrato.class)
                    .setParameter("cliente", cliente)
                    .getResultList();
            return contratos;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contratos de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Contrato obtenerContratoFolio(String folio) throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Contrato c WHERE c.folio = :folio";
            return em.createQuery(jpql, Contrato.class)
                    .setParameter("folio", folio)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontro el folio del contrato");
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener contrato de persistencia");
        } catch (Exception e) {
        throw new PersistenciaSOFException("No se pudo conector a la base de datos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
