/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.persistencia.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Abe
 */
public class Conexion implements IConexion{
    /**
     * Esté metodo crea la conexionde un EntityManager a la base de datos
     * @return entitymanager con conexión a la bd
     */
    @Override
    public EntityManager crearConexion() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("org.itson_SOF_Level_jar_1.0-VERSIONPU");
        
        //Solicitamos una entity manager(acceso a la BD)
        EntityManager entityManager = emFactory.createEntityManager();
        return entityManager;
    }
}
