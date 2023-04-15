
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
 
   
    public static boolean ValidarCampos(String campos)
      
{
        boolean validado = false;
          
          //String linea = "1;Argentina;1;2;Arabia Saudita" ;
        String regex = ".[^;]*\\;.[^;]*\\;[0-9]*\\;[0-9]*\\;.[^;]*" ;
        final Pattern pattern = Pattern.compile(regex); 
          
        
        validado =   pattern.matcher(campos).matches(); 
       /*
          if (validado)
           System.out.println("OK");
          else System.out.println("NO OK");
         */ 
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
        
        Collection<Partido> Partidos= new ArrayList<>();
        Collection<Ronda> Rondas = new ArrayList<>();
        String nro_ronda = null;
        String nro_ronda_actual = null;
     
        /*
        List<String> lineas = null;
     
         FileReader fr = new FileReader(Archivo_Partidos_CSV);
         BufferedReader br = new BufferedReader(fr);
         */
         boolean primer_registro = true;
         int cantidadRegistrosResultados = registrosResultados.size();
         int nro_registro = 1; // teniendo en cuenta el encabezado
         for (String linea:registrosResultados) {
            
            // diseño de registro 1,Argentina,1,2,Arabia Saudita
            
            String[] campos = linea.split(";");

            if (!primer_registro){

                if (ValidarCampos(linea)){

                    nro_ronda=campos[0];
                    nro_registro = nro_registro + 1;
                    Equipo equipo1 = new Equipo(campos[1]);
                    int golesEquipo1 = Integer.parseInt(campos[2]);
                    int golesEquipo2 = Integer.parseInt(campos[3]);
                    Equipo equipo2 = new Equipo(campos[4]);
                    Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);
                    Partidos.add(partido);

                    if (!nro_ronda.equals(nro_ronda_actual)
                            && (nro_registro == cantidadRegistrosResultados)) {

                        nro_ronda_actual = nro_ronda;
                        Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
                        Rondas.add(ronda);
                        
                    }     
                }
                else {
                    System.out.println("La linea no fue validada");
                }
            }
            else {
                primer_registro=false;
            }
        }
            
                     
        

     
      // Leo los registros del archivo de Pronosticos
      
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
      
      // comienzo a recorrer todas las lineas del pronostico y a calcular los puntos
      
   // Collection<Pronostico> Pronosticos= new ArrayList<Pronostico>();
    int puntos_acumulados = 0;
      
    primer_registro = true;
    String participante_actual = "";
    Collection<Pronostico> Pronosticos = new ArrayList<Pronostico>();
            
            
            
    for(String registroPronostico:registrosPronosticos){
          
        if (!primer_registro){
        
            // diseño de registro Equipo 1;Gana 1;Empata;Gana 2;Equipo 2
            String[] campos = registroPronostico.split(";");
            
            Ronda ronda_pronostico = null;
            Partido partido_pronostico = null;
            ResultadoEnum resultado_pronostico = null;
            // diseño de registro pronostico Participante, Ronda, Equipo 1;Gana 1;Empata;Gana 2;Equipo 2;
            String participante = campos[0];
            nro_ronda = campos[1];
            Equipo equipo1 = new Equipo(campos[2]);
            Equipo equipo2  = new Equipo(campos[6]);
            
            
             
            for (Ronda ronda_buscada:Rondas) {
                
                if(ronda_buscada.getNro().equals(nro_ronda)){
                     
                    ronda_pronostico = ronda_buscada;
                    
                    for(Partido partido_buscado:ronda_buscada.getPartidos()){
                  
                        if(equipo1.getNombre().equals(partido_buscado.getEquipo1().getNombre()) && 
                          equipo2.getNombre().equals(partido_buscado.getEquipo2().getNombre())){
                      
                            partido_pronostico = partido_buscado;
                            
                        }
                    }  
                    
                }
            }
                    
                
            Equipo equipo_pronostico = null;
             // diseño de registro pronostico Participante, Ronda, Equipo 1;Gana 1;Empata;Gana 2;Equipo 2;
          
            if (campos[3].equals("X")){ // el primer equipo es el ganador

              equipo_pronostico = equipo1;
              resultado_pronostico = ResultadoEnum.GANADOR;               
            }

            if (campos[5].equals("X")){
                equipo_pronostico = equipo2;
                resultado_pronostico = ResultadoEnum.GANADOR;
            }

            if (campos[4].equals("X")){
                equipo_pronostico = equipo1;
                resultado_pronostico = ResultadoEnum.EMPATE;
            }       
              
           
                
             Pronostico pronostico = new Pronostico(participante, ronda_pronostico,
                partido_pronostico, equipo_pronostico, resultado_pronostico);
             
             Pronosticos.add(pronostico);
                
            }
              
        else
            primer_registro=false;
              
    } // del for de archivo de pronostico 
    
    participante_actual = "";
    // imprimo los puntajes de cada participante
    for(Pronostico pronostico:Pronosticos){
        
        
        if (!participante_actual.equals("") && 
            !participante_actual.equals(pronostico.getParticipante())){
            
            System.out.println("Participante: " + participante_actual + 
                                     " " + puntos_acumulados + " puntos");
            puntos_acumulados = 0;
        }
        else {
            puntos_acumulados = puntos_acumulados + pronostico.puntos();  
        }
        participante_actual = pronostico.getParticipante();
            
    }
    // imprime el ultimo participante
    System.out.println("Participante: " + participante_actual + 
                                     " " + puntos_acumulados + " puntos");
            
    
}
        
}  