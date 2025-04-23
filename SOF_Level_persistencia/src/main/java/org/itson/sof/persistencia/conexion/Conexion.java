
package org.itson.sof.persistencia.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author SofLevel
 */
public class Conexion implements IConexion{
    /**
     * Esté metodo crea la conexionde un EntityManager a la base de datos
     *
     * @return entitymanager con conexión a la bd
     */
    @Override
    public EntityManager crearConexion() throws PersistenciaSOFException {
        try {
            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("org.itson_SOF_Level_jar_1.0-VERSIONPU");
            return emFactory.createEntityManager();
        } catch (PersistenceException e) {
            throw new PersistenciaSOFException("No se pudo conectar a la base de datos", e);
        }
    }

}
