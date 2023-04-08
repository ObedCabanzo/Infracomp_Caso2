package src;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Actualizador extends Thread {
    
    public static TablaPaginas tablaPaginas;
    public static TablaTiempo tablaTiempo;
    public static int[] marcosPagina;

    
    private String referencias;
    private ArrayList<String> listaReferencias;
    private int numeroFallos = 0;
    
    public Actualizador (TablaPaginas tablaPaginas, TablaTiempo tablaTiempo, String referencias, int[] marcosPagina){
        Actualizador.tablaPaginas = tablaPaginas;
        Actualizador.tablaTiempo = tablaTiempo;
        Actualizador.marcosPagina = marcosPagina;
        this.referencias = referencias;
        extraerReferencias();
    }

    public void run(){

        for (String referencia : listaReferencias) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String[] partes = referencia.split(",");
            int pagina = Integer.parseInt(partes[0]);
            
            if (!tablaPaginas.estaEnMemoria(pagina)){ // Revisa que la pagina que se esta solicitando no esté en memoria 
                numeroFallos ++;  // Si no está, aumenta el numero de fallos  
                int paginaMasVieja = tablaTiempo.darPaginaMasVieja(); // Busca la pagina que lleva mas tiempo en memoria

                if (marcosPagina[paginaMasVieja] != -1){  // Si la pagina que lleva mas tiempo en memoria no es -1, quiere decir que esta en memoria

                    int paginaAMover = marcosPagina[paginaMasVieja]; // Se obtiene la pagina que tiene el marco de pagina que lleva mas tiempo en memoria
                    tablaPaginas.cambiarPagina(paginaAMover, -1); // Se actualiza la pagina de la tabla de paginas en -1, pues ya no esta en memoria
                }

                tablaTiempo.actualizarTiempo(paginaMasVieja); // Se avanza el tiempo de todos los marcos añadiendo un 0, excepto el que se acaba de actualizar que se le añade un 1
                tablaPaginas.cambiarPagina(pagina, paginaMasVieja); // Se actualiza la pagina de la tabla de paginas con el numero de marco de pagina en memeoria en el que esta 
                marcosPagina[paginaMasVieja] = pagina; // Se actualiza el marco de pagina con el numero de pagina que esta en memoria

                //System.out.println("");
                //System.out.println("Ref: " + referencia + " Fallos: " + numeroFallos );
                //imprimirMarcosPagina();
                tablaTiempo.imprimirTablaTiempo();
                System.out.println("");
            }


            
        }

        System.out.println("Numero de fallos: " + numeroFallos);
        
    }

    public void extraerReferencias (){

        ArrayList<String> valuesList = new ArrayList<String>();

        Pattern pattern = Pattern.compile("\\[.*?\\],(\\d+),(\\d+)");
        Matcher matcher = pattern.matcher(referencias);

        while (matcher.find()) {
            String values = matcher.group(1) + "," + matcher.group(2);
            valuesList.add(values);
        }

        listaReferencias = valuesList;
    }

    public void imprimirMarcosPagina(){
        for (int i = 0; i < marcosPagina.length; i++) {
            System.out.println("Marco " + i + ": " + marcosPagina[i]);
        }
    }

    
}
