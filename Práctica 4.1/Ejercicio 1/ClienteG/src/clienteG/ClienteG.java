/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteG;

/**
 *
 * @author ramon
 */
import java.io.*;
import java.net.*;

public class ClienteG {
    
    Thread tCliente;
    private Socket skCliente;
    private DataInputStream flujo_entrada;
    private DataOutputStream flujo_salida;
    private Ventana ventana;

    public ClienteG(Ventana ventana){
        this.ventana = ventana;
    }
    
    public boolean conectar(String HOST, int Puerto) {
        boolean ret = false;
        
        try {
            // Creo el socket del cliente
            skCliente = new Socket(HOST, Puerto);
            //skCliente.setSoTimeout(100);
            ret = true;

            // Creo los flujos de entrada y salida
            flujo_entrada = new DataInputStream(skCliente.getInputStream());
            flujo_salida = new DataOutputStream(skCliente.getOutputStream());

            // Lectura inicial de información del servidor
            String datos = flujo_entrada.readUTF();
            System.out.println("SERVIDOR: "+datos);
            ventana.escribirTexto("SERVIDOR: "+datos);

            // Atiendo al cliente mediante un thread
            tCliente = new Thread(new RunCliente(ventana, skCliente));
            tCliente.start();
            // De manera implícita
            //new Thread(new RunCliente(ventana, skCliente)).start();
        } catch (UnknownHostException e) {
            System.out.println("Host Desconocido " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de E/S al conectar: " + ex.getMessage());
        } 
        
        return ret;
    }
    
    public boolean desconectar(){
        boolean ret = false;
        
        try {
            // Mando mensaje de salida
            DataOutputStream flujo_salida = new DataOutputStream(skCliente.getOutputStream());
            flujo_salida.writeUTF("exit");

            skCliente.close();
            ret = true;
        } catch (IOException ex) {
            System.out.println("Error de E/S al desconectar: " + ex.getMessage());
        }    
        
        return ret;
    }
    
    public void enviar(String texto){
        try {
            flujo_salida.writeUTF(texto);
        } catch (IOException ex) {
            System.out.println("Error de E/S al enviar: " + ex.getMessage());
        }
    }
}
