package src;

public class TablaPaginas {

    private int[] paginas;
    private int numeroPaginas;

    public TablaPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
        this.paginas = new int[numeroPaginas];
        for (int i = 0; i < numeroPaginas; i++) {
            this.paginas[i] = -1;
        }
    }

    public int[] getPaginas() {
        return paginas;
    }

    public void setPaginas(int[] paginas) {
        this.paginas = paginas;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void cambiarPagina( int indice , int numeroPagina){
        synchronized(this){
            paginas[indice] = numeroPagina;
        }
    }

    public int darPaginaEnMemoria( int indice ){
        synchronized(this){
            return paginas[indice];
        }
    }

    
}
