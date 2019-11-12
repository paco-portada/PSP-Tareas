/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lavadero;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jmfdiaz
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        List<Coche> listaCoches = new ArrayList<>();
    
        listaCoches.add(new Coche("Seat Ibiza", Coche.Size.PEQUEÑO, Coche.Wash.NORMAL));    //10
        listaCoches.add(new Coche("VW Touran", Coche.Size.GRANDE, Coche.Wash.SUPER));       //20
        listaCoches.add(new Coche("Opel Zafira", Coche.Size.GRANDE, Coche.Wash.EXTRA));     //22
        listaCoches.add(new Coche("Renault Megane", Coche.Size.MEDIANO, Coche.Wash.SUPER)); //16

        //listaCoches.forEach(c -> System.out.println(c.getNombre()));

        listaCoches.sort((c1, c2) -> c2.getTiempoLavado().compareTo(c1.getTiempoLavado()));
       
        //listaCoches.forEach(c -> System.out.println(c.getNombre()));
        
        long tiempoIni = System.currentTimeMillis(); //Almacenamos tiempo inicial
        System.out.println(">>> INICIO Lavadero <<<");

        //define un pool fijo de dos hilos
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < listaCoches.size(); i++) {
            executor.submit(new Operario("Operarion", listaCoches.get(i)));
        }

        //ordena la destrucción de los dos hilos del pool cuando hayan
        //completado todas las tareas
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);
                
        long tiempoTot = (System.currentTimeMillis()-tiempoIni)/1000; //Calculamos el tiempo total de ejecucion
        System.out.println("--- FIN Lavadero (Tiempo: "+tiempoTot+"s) ---");
    }
    
}
