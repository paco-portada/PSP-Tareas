/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteG;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author jmfdiaz
 */
public class RunCliente implements Runnable{

    private Ventana ventana;
    private Socket skCliente;
    
    RunCliente(Ventana ventana, Socket skCliente) {
        this.ventana = ventana;
        this.skCliente = skCliente;
    }
    
    @Override
    public void run(){
        String cadMensaje;
        boolean salir = false;
        
        try {
            DataInputStream flujo_entrada = new DataInputStream(skCliente.getInputStream());

            do{
                // Se recibe el mensaje del servidor
                cadMensaje = flujo_entrada.readUTF();
                
                System.out.println("SERVIDOR: "+cadMensaje);
                ventana.escribirTexto("SERVIDOR: "+cadMensaje);
                
                // Si el mensaje recibido del servidor es exit finalizamos este hilo
                if(cadMensaje.equalsIgnoreCase("exit")){
                    salir = true;
                }
            } while (!salir);
            
            skCliente.close();
        } catch (IOException ex) {
            System.out.println("Error de E/S: " + ex.getMessage());
        } finally {
            // Se cierra la conexión
            System.out.println("Desconexión desde el servidor");
        }
    }
}
