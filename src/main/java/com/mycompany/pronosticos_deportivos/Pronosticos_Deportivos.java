
package com.mycompany.pronosticos_deportivos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


public class Pronosticos_Deportivos  {
 
    
      public static void main( String args[] ) throws FileNotFoundException, IOException
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
      
      // comienzo a recorrer todas las lineas y a guardarlas
      
      Collection<Partido> Partidos= new ArrayList<Partido>();
      
      boolean primer_registro = true;
      
      for(String registroResultado:registrosResultados){
          
          if (!primer_registro)
          {
              // diseño de registro Argentina,1,2,Arabia Saudita
              String[] campos = registroResultado.split(";");
              
              int golesEquipo1 = Integer.parseInt(campos[1]);
              int golesEquipo2 = Integer.parseInt(campos[2]);
              Equipo equipo1 = new Equipo(campos[0]);
              Equipo equipo2 = new Equipo(campos[3]);
              Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);
              Partidos.add(partido);           
                  
              }
          else
              primer_registro=false;
              
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
      
      Collection<Pronostico> Pronosticos= new ArrayList<Pronostico>();
      int puntos_acumulados = 0;
      
      primer_registro = true;
      
      for(String registroPronostico:registrosPronosticos){
          
          if (!primer_registro)
          {
              // diseño de registro Equipo 1;Gana 1;Empata;Gana 2;Equipo 2
              String[] campos = registroPronostico.split(";");
              
              Partido partido_pronostico = null;
              ResultadoEnum resultado_pronostico = null;
              
              // busco el partido que le corresponde al pronostico
              //se necesitan los equipos del pronostico para buscar el partido
              Equipo equipo1 = new Equipo(campos[0]);
              Equipo equipo2  = new Equipo(campos[4]);
             
              for(Partido partido_buscado:Partidos){
                  
                  if(equipo1.getNombre().equals(partido_buscado.getEquipo1().getNombre()) && 
                          equipo2.getNombre().equals(partido_buscado.getEquipo2().getNombre()))
                      
                  partido_pronostico = partido_buscado;
                  
              }
              
              Equipo equipo_pronostico = null;
              // diseño de registro pronostico Equipo 1;Gana 1;Empata;Gana 2;Equipo 2        
              
              if (campos[1].equals("X")){ // el primer equipo es el ganador
                  
                  equipo_pronostico = equipo1;
                  resultado_pronostico = ResultadoEnum.GANADOR;               
              }
              
              if (campos[3].equals("X")){
                  equipo_pronostico = equipo2;
                  resultado_pronostico = ResultadoEnum.GANADOR;
              }
                  
              if (campos[2].equals("X")){
                  equipo_pronostico = equipo1;
                  resultado_pronostico = ResultadoEnum.EMPATE;
              }
              
              Pronostico pronostico = new Pronostico(partido_pronostico,
                      equipo_pronostico, resultado_pronostico);
                      
              // calculo los puntos
              
              puntos_acumulados = puntos_acumulados + pronostico.puntos();         
              
              }
          else
              primer_registro=false;
              
          } // del for de archivo de pronosticos
        
           System.out.println("Puntaje total de la persona: " + puntos_acumulados);
     
      }
} 
    
          
       
       