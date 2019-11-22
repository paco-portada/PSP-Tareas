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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    
    private Socket skCliente;
    private DataInputStream flujo_entrada;
    private DataOutputStream flujo_salida;
    private Ventana ventana;

    public Cliente(Ventana ventana){
        this.ventana = ventana;
    }
    
    public void conectar(String HOST, int Puerto) {
        try {
            // Creo el socket del cliente
            skCliente = new Socket(HOST, Puerto);

            // Creo los flujos de entrada y salida
            flujo_entrada = new DataInputStream(skCliente.getInputStream());
            flujo_salida = new DataOutputStream(skCliente.getOutputStream());

            // TAREAS QUE REALIZA EL CLIENTE
            String datos = flujo_entrada.readUTF();
            System.out.println(datos);
            
            ventana.escribirTexto(datos);
        } catch (UnknownHostException e) {
            System.out.println("Host Desconocido " + e.getMessage());

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar(){
        try {
            skCliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void enviar(String texto){
        try {
            flujo_salida.writeUTF(texto);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
