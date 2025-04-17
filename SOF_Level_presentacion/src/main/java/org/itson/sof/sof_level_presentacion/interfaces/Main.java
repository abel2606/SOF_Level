/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.sof.sof_level_presentacion.interfaces;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author haesp
 */
public class Main {
    public static void main(String[] args) {
        // Verificar si ya está en ejecución
        if (isAlreadyRunning()) {
            System.out.println("El programa ya está en ejecución.");
            return;  // Terminar ejecución si ya hay una instancia corriendo
        }

        // Llamamos a la pantalla principal para iniciar la aplicación
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaPrincipal.getInstance().setVisible(true);
            }
        });
    }

    private static boolean isAlreadyRunning() {
        // Intentamos crear un archivo temporal de bloqueo
        try {
            File lockFile = new File("app.lock");
            RandomAccessFile stream = new RandomAccessFile(lockFile, "rw");
            FileChannel channel = stream.getChannel();
            FileLock lock = channel.tryLock();

            if (lock == null) {
                // Si no conseguimos el lock, significa que ya hay una instancia corriendo
                return true;
            }

            // Si conseguimos el lock, podemos proceder
            // Asegúrate de liberar el lock cuando la aplicación termine
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    lock.release();
                    channel.close();
                    stream.close();
                    lockFile.delete();  // Eliminar archivo de bloqueo
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            return false;  // La aplicación puede ejecutarse normalmente
        } catch (IOException e) {
            e.printStackTrace();
            return true;  // Error al intentar obtener el lock, asumimos que está corriendo
        }
    }
}
