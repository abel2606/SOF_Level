package org.itson.sof.objetosnegocios.gestorcitas.util;

import java.util.Random;

/**
 *
 * @author haesp
 */
public class GeneradorCodigos {

    private static final int INTENTOS_MAXIMOS = 10; // Número de intentos antes de rendirse

    public static String generarCodigo() {
        Random random = new Random();
        String codigo;
        int intentos = 0;

        do {
            codigo = String.valueOf(100000 + random.nextInt(900000)); // Asegura que siempre sea de 6 dígitos
            intentos++;
        } while (codigoExisteEnBaseDeDatos(codigo) && intentos < INTENTOS_MAXIMOS);

        if (intentos >= INTENTOS_MAXIMOS) {
            throw new RuntimeException("No se pudo generar un código único después de " + INTENTOS_MAXIMOS + " intentos.");
        }

        return codigo;
    }

    // Método simulado que verifica si el código existe en la base de datos.
    // Necesitarás implementarlo según tu base de datos.
    private static boolean codigoExisteEnBaseDeDatos(String codigo) {
        // Aquí iría el código para verificar en la base de datos, por ejemplo:
        // SELECT COUNT(*) FROM Codigos WHERE codigo = ?

        // Ejemplo simulado:
        return false; // Simula que nunca existe en la base de datos.
    }
}
