/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.Comparator;
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
            String jpql = "SELECT c FROM Cliente c WHERE c.estado = 'ACTIVO'";

            List<Cliente> clientes = em.createQuery(jpql, Cliente.class).getResultList();

            // Ordenar por estado y luego por nombre
            clientes.sort(Comparator
                    .comparingInt((Cliente c) -> {
                        switch (c.getEstado()) {
                            case "ACTIVO":
                                return 0;
                            case "INACTIVO":
                                return 1;
                            default:
                                return 2;
                        }
                    })
                    .thenComparing(c -> c.getNombre().toLowerCase()) // Orden alfabético sin importar mayúsculas
            );

            return clientes;
        } catch (Exception e) {
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

            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente AND c.estado = 'ACTIVO'";

            Cliente clienteObtenido = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo).getSingleResult();

            transaction.commit();
            logger.log(Level.INFO, "Cita obtenida: ID({0})", clienteObtenido.getId());
            return clienteObtenido;
        } catch (NoResultException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado");
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
                               AND c.estado = 'ACTIVO'
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
     * Cancela un cliente identificado por su correo electrónico.
     *
     * @param correo El correo del cliente a eliminar.
     * @return El cliente eliminado.
     * @throws PersistenciaSOFException Si no se encuentra el cliente o ocurre
     * un error al cancelarlo.
     */
    @Override
    public Cliente cancelarCliente(String correo) throws PersistenciaSOFException {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = conexion.crearConexion();
            transaction = em.getTransaction();
            transaction.begin();

            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente AND c.estado = 'ACTIVO'";
            Cliente clienteExistente = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo)
                    .getSingleResult();

            // Verificamos si se encontró el cliente
            if (clienteExistente == null) {
                throw new PersistenciaSOFException("No se encontró el cliente con correo: " + correo);
            }
            
            clienteExistente.setEstado("INACTIVO");
            
            transaction.commit();

            logger.log(Level.INFO, "Cliente desactivado correctamente: {0}", clienteExistente.getId());
            return clienteExistente;

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Error al desactivar el cliente", e);
            throw new PersistenciaSOFException("Error al desactivar el cliente");

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

            // Buscar cliente existente por el correo original
            String jpql = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente AND c.estado = 'ACTIVO'";
            Cliente clienteExistente = em.createQuery(jpql, Cliente.class)
                    .setParameter("correoCliente", correo)
                    .getSingleResult();

            if (clienteExistente == null) {
                throw new PersistenciaSOFException("No se encontró el cliente con correo: " + correo);
            }

            // Validar si el nuevo correo ya existe en otro cliente
            if (!clienteExistente.getCorreo().equals(clienteNuevo.getCorreo())) {
                String correoQuery = "SELECT COUNT(c) FROM Cliente c WHERE c.correo = :nuevoCorreo AND c.id <> :idActual";
                Long conteoCorreo = em.createQuery(correoQuery, Long.class)
                        .setParameter("nuevoCorreo", clienteNuevo.getCorreo())
                        .setParameter("idActual", clienteExistente.getId())
                        .getSingleResult();
                if (conteoCorreo > 0) {
                    throw new PersistenciaSOFException("Ya existe un cliente con el correo proporcionado");
                }
            }

            // Validar si el nuevo teléfono ya existe en otro cliente
            if (!clienteExistente.getTelefono().equals(clienteNuevo.getTelefono())) {
                String telefonoQuery = "SELECT COUNT(c) FROM Cliente c WHERE c.telefono = :nuevoTelefono AND c.id <> :idActual";
                Long conteoTelefono = em.createQuery(telefonoQuery, Long.class)
                        .setParameter("nuevoTelefono", clienteNuevo.getTelefono())
                        .setParameter("idActual", clienteExistente.getId())
                        .getSingleResult();
                if (conteoTelefono > 0) {
                    throw new PersistenciaSOFException("Ya existe un cliente con el teléfono proporcionado");
                }
            }

            // Actualizar datos
            clienteExistente.setNombre(clienteNuevo.getNombre());
            clienteExistente.setTelefono(clienteNuevo.getTelefono());
            clienteExistente.setCorreo(clienteNuevo.getCorreo());

            Cliente clienteActualizado = em.merge(clienteExistente);
            transaction.commit();

            logger.log(Level.INFO, "Cliente actualizado correctamente con ID: {0}", clienteActualizado.getId());
            return clienteActualizado;

        } catch (NoResultException e) {
            throw new PersistenciaSOFException("No se encontró el cliente con el correo proporcionado");

        } catch (PersistenciaSOFException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

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

            String jpqlCorreo = "SELECT c FROM Cliente c WHERE c.correo = :correoCliente";
            try {
                Cliente clienteConCorreo = em.createQuery(jpqlCorreo, Cliente.class)
                        .setParameter("correoCliente", cliente.getCorreo())
                        .getSingleResult();
                throw new PersistenciaSOFException("Ya existe un cliente con ese correo");
            } catch (NoResultException e) {
            }

            String jpqlTelefono = "SELECT c FROM Cliente c WHERE c.telefono = :telefonoCliente";
            try {
                Cliente clienteConTelefono = em.createQuery(jpqlTelefono, Cliente.class)
                        .setParameter("telefonoCliente", cliente.getTelefono())
                        .getSingleResult();
                throw new PersistenciaSOFException("Ya existe un cliente con ese número de teléfono");
            } catch (NoResultException e) {
            }
            
            em.persist(cliente);

            transaction.commit();
            logger.log(Level.INFO, "Cliente agregado correctamente con ID: {0}", cliente.getId());
            return cliente;

        } catch (PersistenciaSOFException e) {
            throw new PersistenciaSOFException(e.getMessage());

        } catch (Exception e) {
            throw new PersistenciaSOFException("Error en el servidor");

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
