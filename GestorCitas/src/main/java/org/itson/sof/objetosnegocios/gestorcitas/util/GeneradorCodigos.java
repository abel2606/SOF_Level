/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.sof.objetosnegocios.gestorcitas.util;

import java.util.Random;

/**
 *
 * @author haesp
 */
public class GeneradorCodigos {
    
    public static String generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Asegura que siempre sea de 6 dígitos
        return String.valueOf(codigo);
    }
    
    
}
