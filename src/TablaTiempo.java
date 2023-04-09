package src;

public class TablaTiempo {

    private String[] tiempoPaginas;
    private int numeroPaginas;

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

    public void avanzarTiempo (){
        synchronized(this){
            for (int i = 0; i < numeroPaginas; i++) {
                tiempoPaginas[i] = añadirCeroUno(tiempoPaginas[i], "0");
            }
        }
    }

    public void actualizarTiempo (int indice){
        synchronized(this){
            
            for (int i = 0; i < numeroPaginas; i++) {
                if (i != indice){
                    tiempoPaginas[i] = añadirCeroUno(tiempoPaginas[i], "0");
                }
                else{
                    tiempoPaginas[i] = añadirCeroUno(tiempoPaginas[i], "1");
                }
            }
        }
    }


    public int darPaginaMasVieja (){
        synchronized(this){
            int indice = 0;
            for (int i = 0; i < numeroPaginas; i++) {
                if (tiempoPaginas[i].compareTo(tiempoPaginas[indice]) < 0){
                    indice = i;
                }
            }

            return indice;
        }
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
