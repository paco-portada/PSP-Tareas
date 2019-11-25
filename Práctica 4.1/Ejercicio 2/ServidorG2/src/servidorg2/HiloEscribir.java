package servidorg2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Angel Salas Calvo
 *  Hilo que permitira al servidor mandar mensajes
 */
public class HiloEscribir extends Thread {
    
    private Socket skCliente;

    HiloEscribir(Socket skCliente) {
        this.skCliente=skCliente;
    }
    
    @Override
    public void run(){
        String cadMensaje;
        boolean salir = false;
        
        try {
            // Se crea el stream de salida y el scanner para leer del teclado
            DataOutputStream flujo_salida = new DataOutputStream(skCliente.getOutputStream());
            Scanner teclado = new Scanner(System.in);
            
            do {
                cadMensaje = teclado.nextLine();
            
                System.out.println("CLIENTE: "+cadMensaje);
                // Se hace un eco
                flujo_salida.writeUTF(cadMensaje);

                // Si el servidor manda el mensaje de cierre, la ejecucion finaliza
                if(cadMensaje.equalsIgnoreCase("exit")){
                    salir = true;
                }
            } while(!salir);

            skCliente.close();
        } catch (IOException ex) {
            System.out.println("Error de E/S: " + ex.getMessage());
        } finally {
            // Se cierra la conexión
            System.out.println("Cliente desconectado");
        }
    }
}

