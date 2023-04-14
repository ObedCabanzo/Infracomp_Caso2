package src;

public class MarcosPagina {
    

    public int[] marcos;    
    public MarcosPagina(int numeroMarcos){
        marcos = new int[numeroMarcos];
        for (int i = 0; i < numeroMarcos; i++) {
            marcos[i] = -1;
        }
    }



    public void cambiarMarco (int indice, int valor){
        synchronized (this) {
            marcos[indice] = valor;
        }
    }

    public int darValorMarco (int indice){
        synchronized (this) {
            return marcos[indice];
        }
    }
    public int[] darMarcos(){
        synchronized (this) {
            return marcos;
        }
    }

    public int compararCambios (MarcosPagina marcosPagina){
        synchronized (this) {
            int[] marcosCopia = marcosPagina.darMarcos();

            for (int i = 0; i < marcos.length; i++) {
                if (marcos[i] != marcosCopia[i]){
                    return i;
                }
            }
            return -1;
        }
    }



}
