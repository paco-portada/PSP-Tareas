package servidorg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jmfdiaz
 */
public class Servidor implements Runnable{
    
    private Socket skCliente;
    
    Servidor(Socket skCliente){
        this.skCliente=skCliente;
    }

    @Override
    public void run() {
        try {
            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(skCliente.getInputStream());
            DataOutputStream flujo_salida = new DataOutputStream(skCliente.getOutputStream());

            // ATENDER PETICIÓN DEL CLIENTE
            flujo_salida.writeUTF("Se ha conectado el cliente de forma correcta");

            // Se cierra la conexión
            skCliente.close();
            System.out.println("Cliente desconectado");

        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
    }
    
}
