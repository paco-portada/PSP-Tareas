/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidorg2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jmfdiaz
 */
public class ServidorG2 {

    Socket skCliente;
    static final int PUERTO = 15000;

    public static void main (String args []) {
        ServerSocket skServidor = null;
        HiloServidor hServidor;
        HiloEscribir hEscribir;

        try {
            // Inicio el servidor en el puerto
            skServidor = new ServerSocket(PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);

            // Se conecta un cliente
            Socket skCliente = skServidor.accept();
            System.out.println("Cliente conectado: "+skCliente.getInetAddress()+":"+skCliente.getPort());

            // crear un objeto de la clase servidor
            hServidor = new HiloServidor(skCliente);

            // iniciar al servidor para que esté a la escucha en el puerto
            hServidor.start();

            // De forma inplícita
            //new HiloServidor(skCliente).start();
                
            // crear el hilo que lee del teclado y escribe al cliente
            hEscribir = new HiloEscribir(skCliente);

            // iniciar al hilo que lee del teclado
            hEscribir.start();

            // De forma inplícita
            //new HiloEscribir(skCliente).start();
            
            // Espero a que termine el hilo que lee del teclado
            while (hEscribir.isAlive()){
            }
            
            // Habría que cerrar el hilo que lee del cliente, pero se cierra sólo
            hServidor.interrupt();
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
