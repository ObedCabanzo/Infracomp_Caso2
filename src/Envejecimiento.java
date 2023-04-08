package src;

public class Envejecimiento extends Thread{

    public static TablaTiempo tablaTiempo;
    
    public Envejecimiento(TablaTiempo tablaTiempo){
        Envejecimiento.tablaTiempo = tablaTiempo;
    }
        
    
    public void run (){

        while (true){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tablaTiempo.avanzarTiempo();
            //tablaTiempo.imprimirTablaTiempo();
        }
    }


    
}
