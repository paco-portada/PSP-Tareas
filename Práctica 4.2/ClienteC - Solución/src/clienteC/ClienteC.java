/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteC;

/**
 *
 * @author ramon
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteC {
    
    Thread tCliente;
    private Socket skCliente;
    private Scanner in;
    private PrintWriter out;
    private Ventana ventana;

    public ClienteC(Ventana ventana){
        this.ventana = ventana;
    }
    
    public boolean conectar(String NickName, String HOST, int Puerto) {
        boolean ret = false;

        try {
            // Creo el socket del cliente
            skCliente = new Socket(HOST, Puerto);
            //skCliente.setSoTimeout(100);
            ret = true;

            // Creo los flujos de entrada y salida
            in = new Scanner(skCliente.getInputStream());
            out = new PrintWriter(skCliente.getOutputStream(), true);

            while (in.hasNextLine()) {
                var line = in.nextLine();
                if (line.startsWith("SUBMITNAME")) {
                    out.println(NickName);
                } else if (line.startsWith("NAMEACCEPTED")) {
                    this.ventana.setTitle("Chatter - " + line.substring(13));

                    // Lectura inicial de información del servidor
                    String datos = line.substring(13);
                    System.out.println("SERVIDOR: "+datos);
                    ventana.escribirTexto("SERVIDOR: "+datos);

                    // Atiendo al cliente mediante un thread
                    tCliente = new Thread(new RunCliente(ventana, skCliente));
                    tCliente.start();
                    // De manera implícita
                    //new Thread(new RunCliente(ventana, skCliente)).start();
                    
                    break;
                }
            }
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
        out.println(texto);
    }
}
