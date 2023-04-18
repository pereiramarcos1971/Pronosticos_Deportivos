
package com.mycompany.pronosticos_deportivos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
 

public class Pronosticos_Deportivos  {
 
   
    public static boolean ValidarCampos(String campos){
        boolean validado = false;
          
          //String linea = "1;Argentina;1;2;Arabia Saudita" ;
        String regex = ".[^;]*\\;.[^;]*\\;[0-9]*\\;[0-9]*\\;.[^;]*" ;
        final Pattern pattern = Pattern.compile(regex); 
          
        
        validado =   pattern.matcher(campos).matches(); 
        return validado;
    }
    
      public static void main( String args[] ) //throws FileNotFoundException, IOException
      {  
      
      // Ingresamos por consola la ruta completa de los archivos
      
      Scanner miObjetoScanner = new Scanner( System.in );    
      System.out.println( "Ingrese ruta completa y archivo de Pronosticos (Ejemplo: C:\\temp\\pronosticos.csv" );
      String Archivo_Pronosticos_CSV = miObjetoScanner.nextLine(); 
      System.out.println("Archivo de Pronosticos a procesar: " + Archivo_Pronosticos_CSV);           
      System.out.println( "Ingrese ruta completa y archivo de Resultados (Ejemplo: C:\\temp\\resultados.csv" );
      String Archivo_Partidos_CSV = miObjetoScanner.nextLine();
      System.out.println("Archivo de Resultados a procesar: " + Archivo_Partidos_CSV);
      
      // Leo los registros del archivo de Resultados (Partido)
    
      Path rutaResultados = Paths.get(Archivo_Partidos_CSV);
      
      List<String> registrosResultados = null;
      
      try{
          registrosResultados = Files.readAllLines(rutaResultados); 
         }
      catch (IOException e) {
          System.out.println("No se pudo leer el registro de Resultados");
          System.out.println(e.getMessage());
          System.exit(1);
      }
      
      Path rutaPronosticos = Paths.get(Archivo_Pronosticos_CSV);
      
      List<String> registrosPronosticos = null;
      
      try{
          registrosPronosticos = Files.readAllLines(rutaPronosticos); 
         }
      catch (IOException e) {
          System.out.println("No se pudo leer el registro de Pronosticos");
          System.out.println(e.getMessage());
          System.exit(1);
      }     
        ArrayList<Partido> Partidos= new ArrayList<>();  
        ArrayList<Ronda> Rondas = new ArrayList<>();
        ArrayList<Pronostico> Pronosticos = new ArrayList<>(); 
        
        String nro_ronda = null;
        String nro_ronda_actual = null;
     
         boolean primer_registro_resultados = true;
         boolean primer_registro_pronosticos = true;
         
         //int cantidadRegistrosResultados = registrosResultados.size();
         for (String linea:registrosResultados) {
            
            // diseño de registro 1,Argentina,1,2,Arabia Saudita
            
            String[] campos_resultados = linea.split(";");
           
            if (!primer_registro_resultados){

                if (ValidarCampos(linea)){

                    if (nro_ronda_actual == null){
                    
                        nro_ronda_actual=campos_resultados[0];
                        Equipo equipo1 = new Equipo(campos_resultados[1]);
                        int golesEquipo1 = Integer.parseInt(campos_resultados[2]);
                        int golesEquipo2 = Integer.parseInt(campos_resultados[3]);
                        Equipo equipo2 = new Equipo(campos_resultados[4]);
                        Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                        Partidos.add(partido);
                    }
                    else if (nro_ronda_actual.equals(campos_resultados[0])) {
                            nro_ronda_actual=campos_resultados[0];
                            Equipo equipo1 = new Equipo(campos_resultados[1]);
                            int golesEquipo1 = Integer.parseInt(campos_resultados[2]);
                            int golesEquipo2 = Integer.parseInt(campos_resultados[3]);
                            Equipo equipo2 = new Equipo(campos_resultados[4]);
                            Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                            Partidos.add(partido);
                        }
                    else{
                        Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
                        Rondas.add(ronda);  
                        Partidos.clear();
                        nro_ronda_actual=campos_resultados[0];
                        Equipo equipo1 = new Equipo(campos_resultados[1]);
                        int golesEquipo1 = Integer.parseInt(campos_resultados[2]);
                        int golesEquipo2 = Integer.parseInt(campos_resultados[3]);
                        Equipo equipo2 = new Equipo(campos_resultados[4]);
                        Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                        Partidos.add(partido);
                        
                    }
                                                               
                }           
                           
                else {
                    System.out.println("La linea de resultados no fue validada");
                }
            }
            else {
                primer_registro_resultados=false;
            }
        }
         
         // ultima ronda
         Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
         Rondas.add(ronda);  
                        
         
         
        for(String registroPronostico:registrosPronosticos){
                
            String participante_actual = "";

            if (!primer_registro_pronosticos){

                // diseño de registro Equipo 1;Gana 1;Empata;Gana 2;Equipo 2
                String[] campos = registroPronostico.split(";");

                Ronda ronda_pronostico = null;
                Partido partido_pronostico = null;
                ResultadoEnum resultado_pronostico = null;
                // diseño de registro pronostico Participante, Ronda, Equipo 1;Gana 1;Empata;Gana 2;Equipo 2;
                String participante = campos[0];
                nro_ronda = campos[1];
                Equipo equipo1_pronostico = new Equipo(campos[2]);
                Equipo equipo2_pronostico  = new Equipo(campos[6]);

                for (Ronda ronda_buscada:Rondas) {

                    if(ronda_buscada.getNro().equals(nro_ronda)){

                        ronda_pronostico = ronda_buscada;

                        for(Partido partido_buscado:ronda_buscada.getPartidos()){

                            if((equipo1_pronostico.getNombre().equals(partido_buscado.getEquipo1().getNombre())) 
                                    && 
                                (equipo2_pronostico.getNombre().equals(partido_buscado.getEquipo2().getNombre()))){

                            partido_pronostico = partido_buscado;

                            }
                        }  

                    }
                }
                
                Equipo equipo_pronostico = null;
             // diseño de registro pronostico Participante, Ronda, Equipo 1;Gana 1;Empata;Gana 2;Equipo 2;
          
                if (campos[3].equals("X")){ // el primer equipo es el ganador

                equipo_pronostico = equipo1_pronostico;
                resultado_pronostico = ResultadoEnum.GANADOR;               
                }

                if (campos[5].equals("X")){
                equipo_pronostico = equipo2_pronostico;
                resultado_pronostico = ResultadoEnum.GANADOR;
            }

            if (campos[4].equals("X")){
                equipo_pronostico = equipo1_pronostico;
                resultado_pronostico = ResultadoEnum.EMPATE;
            }         
                
             Pronostico pronostico = new Pronostico(participante, ronda_pronostico,
                partido_pronostico, equipo_pronostico, resultado_pronostico);
             
             Pronosticos.add(pronostico);
            }
            else{
                primer_registro_pronosticos= false;
            }
        }
    
    // comienzo a recorrer todas las lineas del pronostico y a calcular los puntos     
    // Collection<Pronostico> Pronosticos= new ArrayList<Pronostico>();
    int puntos_acumulados = 0;
      
    String participante_actual = "";
          
    String ronda_actual = "";
    // imprimo los puntajes de cada participante
    
    puntos_acumulados = 0;
     
    for(Pronostico pronostico:Pronosticos){
        
        if (
                ((participante_actual.equals(pronostico.getParticipante()))
                && 
                (ronda_actual.equals(pronostico.getRonda().getNro())))
            ||
                ((participante_actual.equals(""))
                 &&
                 (ronda_actual.equals("")))
            ){
            
            participante_actual = pronostico.getParticipante();
            ronda_actual = pronostico.getRonda().getNro();
            puntos_acumulados = puntos_acumulados + pronostico.puntos();
            
        }
        else {
        System.out.println("Participante: " + participante_actual +
                                ", Ronda: " + ronda_actual +
                                     ", " + puntos_acumulados + " puntos");
            puntos_acumulados = 0;  
            participante_actual = pronostico.getParticipante();
            ronda_actual = pronostico.getRonda().getNro();
            puntos_acumulados = puntos_acumulados + pronostico.puntos();
            
            
        }
        

            
    }
    // imprime el ultimo participante
    System.out.println("Participante: " + participante_actual +
                                ", Ronda: " + ronda_actual +
                                     ", " + puntos_acumulados + " puntos");  
                
    
}
        
}  