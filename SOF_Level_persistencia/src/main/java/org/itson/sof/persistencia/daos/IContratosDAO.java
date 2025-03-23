/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.sof.persistencia.daos;

import java.util.List;
import org.itson.sof.persistencia.entidades.Contrato;
import org.itson.sof.persistencia.exception.PersistenciaSOFException;

/**
 *
 * @author Abe
 */
public interface IContratosDAO {
    
    public List<Contrato> obtenerTotalContratos() throws PersistenciaSOFException;
    
    public Contrato obtenerContratoFolio (String folio)throws PersistenciaSOFException;
    
}
