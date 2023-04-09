package src;

public class Envejecimiento extends Thread{

    public static TablaTiempo tablaTiempo;
    public static MarcosPagina marcosPagina;
    
    public Envejecimiento(TablaTiempo tablaTiempo, MarcosPagina marcosPagina){
        Envejecimiento.marcosPagina = marcosPagina;
        Envejecimiento.tablaTiempo = tablaTiempo;
    }
        
    
    public void run (){

        MarcosPagina marcosCopia = marcosPagina;

        while (true){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int cambios = marcosPagina.compararCambios(marcosCopia);
            if (cambios == -1 ){
                marcosCopia = marcosPagina;
                tablaTiempo.actualizarTiempo(cambios);
            }
        }
    }

   


    
}
