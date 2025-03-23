
package org.itson.sof.objetosnegocios.sof_level_objetosnegocios.converterutil;

import java.util.Random;

/**
 *
 * @author JazmE
 */
public class DiferenciadorUtils {
    private static final Random random = new Random();

    public static String generarCodigo() {
        long timestamp = System.currentTimeMillis(); // Base del timestamp
        int randomNumber = random.nextInt(10000); // Número aleatorio para aumentar la aleatoriedad
        return timestamp + "" + randomNumber;
    }
}
