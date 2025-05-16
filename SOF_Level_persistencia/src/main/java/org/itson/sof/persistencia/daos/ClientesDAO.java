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
import javax.persistence.PersistenceException;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.entidades.CitaMaterial;
import org.itson.sof.persistencia.entidades.Cliente;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.entidades.Material;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public class ClientesDAO implements IClientesDAO {

    private IConexion conexion;
    static final Logger logger = Logger.getLogger(ClientesDAO.class.getName());

    public ClientesDAO(IConexion conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return Una lista con todos los clientes almacenados.
     * @throws PersistenciaSOFException Si ocurre un error al recuperar los
     * clientes.
     */
    @Override
    public List<Cliente> obtenerTodosClientes() throws PersistenciaSOFException {
        EntityManager em = null;
        try {
            em = conexion.crearConexion();
            String jpql = "SELECT c FROM Cliente c";
            List<Cliente> clientes = em.createQuery(jpql, Cliente.class).getResultList();
            return clientes;
        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("Error al obtener clientes de persistencia");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    
     /**
     * Obtiene un cliente a partir de su correo electrónico.
     *
     * @param correo El correo electrónico del cliente a buscar.
     * @return El cliente que coincide con el correo.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o hay un
     * error al acceder a los datos.
     */
    @Override
    public Cliente obtenerCliente(String correo) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
            Cliente clienteObtenido = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo).getSingleResult();

            transaction.commit();
            logger.log(Level.INFO.INFO, "Cita obtenida: ID({0})", clienteObtenido.getId());
            return clienteObtenido;
        } catch (PersistenciaSOFException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener las citas del contrato", e);
            throw new PersistenciaSOFException("Error al obtener las citas de los contratos");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Busca clientes cuyos nombres contengan una coincidencia parcial con el
     * nombre dado.
     *
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de clientes con nombres similares.
     * @throws PersistenciaSOFException Si ocurre un error al realizar la
     * búsqueda.
     */
    @Override
    public List<Cliente> obtenerClientesSimilares(String nombre) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            nombre = "%" + nombre + "%";
            String jpql = """
                               SELECT c FROM Cliente c 
                               WHERE c.nombre LIKE :nombre 
                               """;
            List<Cliente> clienteObtenido = em.createQuery(jpql, Cliente.class)
                    .setParameter("nombre", nombre).getResultList();

            transaction.commit();
            logger.log(Level.INFO.INFO, "Se han obtenido:{0}", clienteObtenido.size());
            return clienteObtenido;
        } catch (PersistenciaSOFException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al obtener los cliente similares ", e);
            throw new PersistenciaSOFException("Error al obtener los clientes similares");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Elimina un cliente identificado por su correo electrónico.
     *
     * @param correo El correo del cliente a eliminar.
     * @return El cliente eliminado.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o ocurre
     * un error al eliminarlo.
     */
    @Override
    public Cliente eliminarCliente(String correo) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
            Cliente clienteExistente = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo)
                    .getSingleResult();

            // Verificamos si se encontró el cliente
            if (clienteExistente == null) {
                throw new PersistenciaSOFException("No se encontró el cliente con correo: " + correo);
            }

            // Eliminar el cliente
            em.remove(clienteExistente);
            transaction.commit();

            logger.log(Level.INFO, "Cliente eliminado correctamente: {0}", clienteExistente.getId());
            return clienteExistente;

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar el cliente", e);
            throw new PersistenciaSOFException("Error al eliminar el cliente");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

     /**
     * Edita los datos de un cliente identificado por su correo electrónico.
     *
     * @param correo El correo del cliente a editar.
     * @param cliente El nuevo objeto Cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o ocurre
     * un error al actualizarlo.
     */
    @Override
    public Cliente editarCliente(String correo, Cliente clienteNuevo) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
            Cliente clienteExistente = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo)
                    .getSingleResult();

            if (clienteExistente == null) {
                throw new PersistenciaSOFException("No se encontró el cliente con correo: " + correo);
            }

            clienteExistente.setNombre(clienteNuevo.getNombre());
            clienteExistente.setTelefono(clienteNuevo.getTelefono());
            clienteExistente.setCorreo(clienteNuevo.getCorreo());

            // Si deseas también editar contratos o hijos relacionados, debes hacerlo explícitamente aquí.
            Cliente clienteActualizado = em.merge(clienteExistente);
            transaction.commit();

            logger.log(Level.INFO, "Cliente actualizado correctamente con ID: {0}", clienteActualizado.getId());
            return clienteActualizado;

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al editar el cliente", e);
            throw new PersistenciaSOFException("Error al editar el cliente");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Agrega un nuevo cliente al sistema. Si ya existe un cliente con el mismo
     * correo, lanza una excepción.
     *
     * @param cliente Objeto Cliente que se desea agregar.
     * @return El cliente que fue agregado exitosamente.
     * @throws PersistenciaSOFException Si ya existe un cliente con el mismo
     * correo o ocurre un error al persistir.
     */
    @Override
    public Cliente agregarCliente(Cliente cliente) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();
            
            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
            Cliente clienteObtenido = null;
            try{
                 clienteObtenido = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", cliente.getCorreo()).getSingleResult();
            }catch(Exception e){
                
            }
            
            if(clienteObtenido!=null){
                logger.log(Level.INFO, "El cliente ya existe", cliente.getCorreo());
                return null;
            }
            em.persist(cliente);
            
            transaction.commit();
            logger.log(Level.INFO, "Cliente agregado correctamente con ID: {0}", cliente.getId());
            return cliente;

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos");

        } catch (Exception e) {
            throw new PersistenciaSOFException("Error al agregar al cliente");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
