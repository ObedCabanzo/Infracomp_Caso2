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


    public static void main(String[] args) {
        String referencias = mode1();

    }

    public static String  mode1() {

        String resultado = leerInput("src/input/input.txt");

        String referencias = "";

        int tamanoFila = numeroColumnas * tamanoEntero; // 2*4 = 8
        int filasEnUnaPagina = tamanoPagina / tamanoFila; // 10 / 8 = 1 
        int numeroPaginasNecesariasPorMatriz = numeroFilas / filasEnUnaPagina; // 2 / 1 = 2

        int filaActual = 0;
        for (int i = 0; i < numeroPaginasNecesariasPorMatriz; i++) {
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

                }
                filaActual++;
                
            }
        }
        
        tablaPaginas = new TablaPaginas(numeroPaginasNecesariasPorMatriz * 3);
        String resultadoFinal = resultado + referencias;
        escribirOutput("src/output/output.txt", resultadoFinal);
        return resultadoFinal;
        
    }

    public static void mode2() {
        System.out.println("Mode 2");
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

      

            String resultado = "NF = " + numeroFilas + "\n" 
                             + "NC = " + numeroColumnas + "\n" 
                             + "TP = " + tamanoPagina + "\n" 
                             + "MP = " + numeroPaginas + "\n" 
                             + "TE = " + tamanoEntero + "\n";
                             
            scanner.close();

            return resultado;

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
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
        }
    }
}