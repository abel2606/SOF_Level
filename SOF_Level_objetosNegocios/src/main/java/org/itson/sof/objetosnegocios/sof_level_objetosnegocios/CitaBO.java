/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.logging.Logger;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.persistencia.conexion.Conexion;
import org.itson.sof.persistencia.conexion.IConexion;
import org.itson.sof.persistencia.daos.CitasDAO;
import org.itson.sof.persistencia.daos.ICitasDAO;
import org.itson.sof.persistencia.entidades.Cita;
import org.itson.sof.sof_dtos.CitaDTO;
import org.itson.sof.sof_dtos.ContratoDTO;

/**
 *
 * @author haesp
 */
public class CitaBO implements ICitaBO{

    private ICitasDAO citasDAO;
    private static final Logger LOG = Logger.getLogger(CitaBO.class.getName());

    public CitaBO(ICitasDAO citasDAO) {
        IConexion conexion = new Conexion();
        this.citasDAO = new CitasDAO(conexion);
    }
    
    
    

    @Override
    public CitaDTO crearCita(ContratoDTO contrato, CitaDTO cita) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CitaDTO actualizarCita(CitaDTO cita) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarCita(CitaDTO cita) throws ObjetosNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
//    /**
//     * Método para convertir una CitaDTO a una entidad
//     * @param citaDTO cita que se desea convertir a entidad
//     * @return objeto tipo Cita
//     */
//    private Cita convertirCitaEntidad (CitaDTO citaDTO){
//        
//        Cita cita = new Cita();
//        cita.setContrato(citaDTO.getContrato());
//       
//    }
    
}
