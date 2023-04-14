package src;

public class TablaTiempo {

    private String[] tiempoPaginas;
    private int numeroPaginas;
    private int actualizacionPendiente = 0;

    public TablaTiempo( int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
        this.tiempoPaginas = new String[numeroPaginas];
        for (int i = 0; i < numeroPaginas; i++) {
            this.tiempoPaginas[i] = "00000000";
        }

    }

    public String getTiempoPaginas() {
        String resultado = "";
        for (int i = 0; i < numeroPaginas; i++) {
            resultado += tiempoPaginas[i] + "\n";
        }

        return resultado;
    }

    public void setTiempoPaginas(String[] tiempoPaginas) {
        this.tiempoPaginas = tiempoPaginas;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public synchronized void avanzarTiempo (){

        
        while (actualizacionPendiente == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        

        for (int i = 0; i < numeroPaginas; i++) {
            tiempoPaginas[i] = añadirCeroUno(tiempoPaginas[i], "0");
        }

        actualizacionPendiente = 0;
        notifyAll();
    }
    

    

    public synchronized void actualizarTiempo(int indice){
        
        while (actualizacionPendiente == 1){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        tiempoPaginas[indice] = añadirCeroUno(tiempoPaginas[indice], "1");
        actualizacionPendiente = 1;
        notifyAll();
    
    }


    public synchronized int darPaginaMasVieja(){

        int indice = 0;
        int tiempoMasViejo = 0;

        for (int i = 0; i < numeroPaginas; i++) {
            if ( tiempoPaginas[i].compareTo(tiempoPaginas[indice]) < tiempoMasViejo){
                indice = i;
                tiempoMasViejo = tiempoPaginas[i].compareTo(tiempoPaginas[indice]);
            }
        }

        return indice ;

    }

  

    public String añadirCeroUno(String tiempo, String valor){
        return valor + tiempo.substring(0, tiempo.length()-1);
    }

    public void imprimirTablaTiempo(){
        for (int i = 0; i < numeroPaginas; i++) {
            System.out.println("Pagina " + i + " : " + tiempoPaginas[i]);
        }
    }
    
}   
