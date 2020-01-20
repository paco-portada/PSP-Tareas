/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteC;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
        
        try {
            Scanner in = new Scanner(skCliente.getInputStream());

            while (in.hasNextLine()) {
                var line = in.nextLine();
                if (line.startsWith("MESSAGE")) {
                    cadMensaje = line.substring(8);
                    System.out.println("SERVIDOR: "+cadMensaje);
                    ventana.escribirTexto("SERVIDOR: "+cadMensaje);
                } else if (line.startsWith("exit")) {
                    break;
                }
            }
            
            skCliente.close();
        } catch (IOException ex) {
            System.out.println("Error de E/S: " + ex.getMessage());
        } finally {
            // Se cierra la conexión
            System.out.println("Desconexión desde el servidor");
        }
    }
}
