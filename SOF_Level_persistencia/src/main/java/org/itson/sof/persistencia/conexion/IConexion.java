/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.conexion;

import javax.persistence.EntityManager;

/**
 *
 * @author Abe
 */
public interface IConexion {
    /**
     * Esté metodo crea la conexionde un EntityManager a la base de datos
     * @return entitymanager con conexión a la bd
     */
    public EntityManager crearConexion();
}
