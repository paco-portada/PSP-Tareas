/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorg;

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
        
        // Por si queremos tener una lista de hilos 
        //List<Thread> hilos = new ArrayList<>();
        
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
                System.out.println("Cliente conectado: "+skCliente.getInetAddress()+":"+skCliente.getPort());

                // Atiendo al cliente mediante un thread
                tServidor = new Thread(new RunServidor(skCliente));
                tServidor.start();
                // De forma implícita
                //new Thread(new RunServidor(skCliente)).start();
                
                // Añadiéndolo a la lista de hilos por si nos hicera falta, por ejemplo para cerrarlos
                //hilos.add(tServidor);
                //hilos.stream().forEach((c) -> {c.interrupt();});
            }
        } catch (IOException e) {
            System.out.println("Error E/S en el servidor: " + e.getMessage());
        } finally {
            // Intentar cerrar el socket del servidor por si ocurre cualquier error
            try {
                if (skServidor != null) {
                    skServidor.close();
                }
            } catch (IOException ex) {
                System.out.println("Error E/S al finalizar el servidor: "+ ex.getMessage());
            }
        }
    }
    
}
