/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jmfdiaz
 */
public class ServidorG {

    Socket skCliente;
    static final int PUERTO = 15000;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerSocket skServidor = null;
        Thread tServidor;
        
        try {
            // Inicio el servidor en el puerto
            skServidor = new ServerSocket(PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);

            // atender peticiones de forma ininterrumpida
            // no es lo recomendable tener un bucle infinito
            // en algún momento tendrá que finalizar el servidor
            while (true) {
                // Se conecta un cliente
                Socket skCliente = skServidor.accept();
                System.out.println("Cliente conectado"+skCliente.getPort());

                // Atiendo al cliente mediante un thread
                tServidor = new Thread(new Servidor(skCliente));
                tServidor.start();
            }
        } catch (IOException e) {
            System.out.println("Error E/S en el servidor: " + e);
        } finally {
            // Intentar cerrar el socket del servidor por si ocurre cualquier error
            try {
                if (skServidor != null) {
                    skServidor.close();
                }
            } catch (IOException e) {
                System.out.println("Error E/S al cerrar el servidor");
            }
        }
    }
    
}
