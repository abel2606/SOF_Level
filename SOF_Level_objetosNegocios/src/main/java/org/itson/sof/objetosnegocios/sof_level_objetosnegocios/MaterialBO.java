/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios;

import java.util.ArrayList;
import java.util.List;
import org.itson.sof.objetosnegocios.sof_level_objetosnegocios.exception.ObjetosNegocioException;
import org.itson.sof.sof_dtos.MaterialDTO;

/**
 *
 * @author haesp
 */
public class MaterialBO implements IMaterialBO{

    List<MaterialDTO> materiales = new ArrayList<>();
    
    @Override
    public List<MaterialDTO> obtenerCita(MaterialDTO materialDTO) throws ObjetosNegocioException {
        materiales.add(new MaterialDTO("Pipshas"));
        materiales.add(new MaterialDTO("Mazapan"));
        materiales.add(new MaterialDTO("Electrolit"));
        materiales.add(new MaterialDTO("Simi"));
        materiales.add(new MaterialDTO("Papois"));
        return materiales;
    }
    
}
