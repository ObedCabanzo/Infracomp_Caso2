package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class Main {

    private static int numeroFilas = 0;
    private static int numeroColumnas = 0;
    private static int tamanoPagina = 0;
    private static int numeroPaginas = 0;
    private static int tamanoEntero = 0;

    public static TablaPaginas tablaPaginas;
    public static TablaTiempo tablaTiempo;
    public static MarcosPagina marcosPagina;

    private static Scanner scanner;


    public static void main(String[] args) {

        System.out.println("------------------------------");
        System.out.println("SIMULADOR DE MEMORIA VIRTUAL ");
        System.out.println("------------------------------\n");

        scanner = new Scanner(System.in);
        int modo = -1;

        System.out.println("Ingresa la ruta del archivo input: \n(Ruta de archivo por defecto: src/input/input.txt)  \n");
        String ruta = scanner.nextLine();
        
        System.out.println("\n\nSeleccione el modo de ejecución: \n 1. Modo 1 \n 2. Modo 2 \n");
        modo = scanner.nextInt();
        scanner.close();
        
        if (modo == 1) {

            mode1(ruta );

        } else if (modo == 2) {

            String referencias = mode1(ruta);
            mode2(referencias);

        } else {
            System.out.println("Modo no válido");
        }


    }

    public static String  mode1(String ruta) {

        System.out.println("\nEjecutando modo 1... \n ");        
        
        String resultado = leerInput(ruta);
        
        String referencias = "";
        
        int numeroReferencias = 0;

        /* 
            Ejemplo: 
                Tamaño de pagina:  256 Bytes
                Tamaño de entero: 4 Bytes
                Filas = 8
                Columnas = 8
        */

        // 8*4 = 32 Bytes por fila
        int tamanoFila = numeroColumnas * tamanoEntero; 
        // 256 / 32 = 8 filas por pagina
        int filasEnUnaPagina = tamanoPagina / tamanoFila; 
         // 8 / 8 = 1 pagina por matriz
        int numeroPaginasNecesariasPorMatriz = numeroFilas / filasEnUnaPagina;


        int filaActual = 0;

        // Cuando todas las matrices caben en una pagina 
        if (filasEnUnaPagina >= numeroFilas*3) {    
            filasEnUnaPagina = numeroFilas;

            int desplazamiento = 0;

            for (int j = 0; j < numeroFilas; j++) {

                for (int k = 0; k < numeroColumnas; k++) {

    
                    String refA =  "0" + "," + desplazamiento;
                    String refB = "0" + "," + desplazamiento;
                    String refC = "0" + "," + desplazamiento;

                    String valor = "[A-" + j + "-" + k + "]"  
                                        + "," + refA + "\n" 
                                        + "[B-" + j + "-" + k + "]"
                                        + "," + refB + "\n" 
                                        + "[C-" + j + "-" + k + "]"
                                        + "," + refC + "\n";

                    referencias += valor;
                    desplazamiento += tamanoEntero;

                    numeroReferencias += 3;

                }
                filaActual++;   
            }

            tablaPaginas = new TablaPaginas(2);
            
        }
        
        // Cuanto dos matrices caben en una pagina 
        else if (filasEnUnaPagina >= numeroFilas*2) {      
            numeroPaginasNecesariasPorMatriz = 1;
            filasEnUnaPagina = numeroFilas;

            int desplazamiento = 0;
            int paginaActual = 0;

            for (int j = 0; j < numeroFilas; j++) {

                for (int k = 0; k < numeroColumnas; k++) {

    
                    String refA = paginaActual + "," + desplazamiento;
                    String refB = paginaActual + "," + desplazamiento;
                    String refC = paginaActual + 1 + "," + desplazamiento;

                    String valor = "[A-" + j + "-" + k + "]"  
                                        + "," + refA + "\n" 
                                        + "[B-" + j + "-" + k + "]"
                                        + "," + refB + "\n" 
                                        + "[C-" + j + "-" + k + "]"
                                        + "," + refC + "\n";

                    referencias += valor;
                    desplazamiento += tamanoEntero;

                    numeroReferencias += 3;

                }
                filaActual++;   
            }

            tablaPaginas = new TablaPaginas(2);

        }

        else {
             
                for (int i = 0; i < numeroPaginasNecesariasPorMatriz ; i++) {

                    int desplazamiento = 0;

                    for (int j = 0; j < filasEnUnaPagina; j++) {

                        for (int k = 0; k < numeroColumnas; k++) {

            
                            String refA = i + "," + desplazamiento;
                            String refB = (i + numeroPaginasNecesariasPorMatriz) + "," + desplazamiento;
                            String refC = (i + 2*numeroPaginasNecesariasPorMatriz) + "," + desplazamiento;

                            String valor = "[A-" + filaActual + "-" + k + "]"  
                                                + "," + refA + "\n" 
                                                + "[B-" + filaActual + "-" + k + "]"
                                                + "," + refB + "\n" 
                                                + "[C-" + filaActual + "-" + k + "]"
                                                + "," + refC + "\n";

                            referencias += valor;
                            desplazamiento += tamanoEntero;

                            numeroReferencias += 3;

                        }
                        filaActual++;
                        
                    }
                }
                tablaPaginas = new TablaPaginas(numeroPaginasNecesariasPorMatriz * 3);
        }

       

        resultado += "NR = " + numeroReferencias + "\n";

        String resultadoFinal = resultado + referencias;

        escribirOutput("src/output/output.txt", resultadoFinal);
        
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Referencias creadas en la siguiente ruta: src/output/output.txt ");
        System.out.println("---------------------------------------------------------------------\n");

        System.out.println("Parametros: \n");
        System.out.println("Numero de filas: " + numeroFilas);
        System.out.println("Numero de columnas: " + numeroColumnas);
        System.out.println("Tamano de pagina: " + tamanoPagina);
        System.out.println("Numero de paginas: " + numeroPaginas);
        System.out.println("Tamano de entero: " + tamanoEntero + "\n");
        System.out.println("Numero de referencias: " + numeroReferencias + "\n");
        


        return resultadoFinal;
        
    }

    public static void mode2(String referencias) {

        System.out.println("Ejecutando modo 2... ");

        tablaTiempo = new TablaTiempo(numeroPaginas);
        marcosPagina = new MarcosPagina(numeroPaginas);



        Envejecimiento envejecimiento = new Envejecimiento(tablaTiempo);
        envejecimiento.start();
        
        Actualizador actualizador = new Actualizador(tablaPaginas, tablaTiempo, referencias, marcosPagina);
        actualizador.start();

    }

    public static String leerInput(String rutaArchivo) {
        File file = new File(rutaArchivo);
        try {
            Scanner scanner = new Scanner(file);
            int NF = 0;
            int NC = 0;
            int TP = 0;
            int MP = 0;
            int TE = 0;
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split("=");
                if (partes.length == 2) {
                    String llave = partes[0].trim();
                    String valor = partes[1].trim();
                    switch (llave) {
                        case "NF":
                            NF = Integer.parseInt(valor);
                            break;
                        case "NC":
                            NC = Integer.parseInt(valor);
                            break;
                        case "TP":
                            TP = Integer.parseInt(valor);
                            break;
                        case "MP":
                            MP = Integer.parseInt(valor);
                            break;
                        case "TE":
                            TE = Integer.parseInt(valor);
                            break;
                    }
                }
            }
            numeroFilas = NF;
            numeroColumnas = NC;
            tamanoPagina = TP;
            numeroPaginas = MP;
            tamanoEntero = TE;

      

            String resultado = "TP = " + tamanoPagina + "\n" 
                             + "NF = " + numeroFilas + "\n"  
                             + "NC = " + numeroColumnas + "\n" ;
                             
            scanner.close();

            return resultado;

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
            System.exit(0);
            return "";
        }

    }

    public static void escribirOutput(String rutaArchivo, String contenido) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
            writer.write(contenido);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al escribir archivo: " + e.getMessage());
            System.exit(0);
        }
    }
}