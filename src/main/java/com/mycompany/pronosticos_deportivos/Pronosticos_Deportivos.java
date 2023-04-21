
package com.mycompany.pronosticos_deportivos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.*;


 

public class Pronosticos_Deportivos  {
 
   
    public static boolean ValidarCampos(String campos){
        boolean validado = false;
          
          //String linea = "1,1;Argentina;1;2;Arabia Saudita" ;
        String regex = ".[^;]*\\;.[^;]*\\;.[^;]*\\;[0-9]*\\;[0-9]*\\;.[^;]*" ;
        final Pattern pattern = Pattern.compile(regex); 
          
        
        validado =   pattern.matcher(campos).matches(); 
        return validado;
    }
   

    
      public static void main( String args[] ) throws SQLException //throws FileNotFoundException, IOException
      {  
      
          /*
      // Ingresamos por consola la ruta completa de los archivos
      
      Scanner miObjetoScanner = new Scanner( System.in );    
      System.out.println( "Ingrese ruta completa y archivo de Pronosticos (Ejemplo: C:\\temp\\pronosticos.csv" );
      String Archivo_Pronosticos_CSV = miObjetoScanner.nextLine(); 
      System.out.println("Archivo de Pronosticos a procesar: " + Archivo_Pronosticos_CSV);           
      System.out.println( "Ingrese ruta completa y archivo de Resultados (Ejemplo: C:\\temp\\resultados.csv" );
      String Archivo_Partidos_CSV = miObjetoScanner.nextLine();
      System.out.println("Archivo de Resultados a procesar: " + Archivo_Partidos_CSV);
      
      
    
*/
     
    // Leo los registros del archivo de Resultados (Partido)

    Path rutaResultados = Paths.get(args[0]);
    List<String> registrosResultados = null;

    try{
        registrosResultados = Files.readAllLines(rutaResultados); 
       }
    catch (IOException e) {
        System.out.println("No se pudo leer el registro de Resultados");
        System.out.println(e.getMessage());
        System.exit(1);
    }

   
    ArrayList<Fase> Fases = new ArrayList<>();
    ArrayList<Partido> Partidos= new ArrayList<>();  
    ArrayList<Ronda> Rondas = new ArrayList<>();
    ArrayList<Pronostico> Pronosticos = new ArrayList<>(); 

    String nro_ronda_actual = null;
    String nro_fase_actual = null;

    boolean primer_registro_resultados = true;
         
    for (String linea:registrosResultados) {

       // diseño de registro 1,1,Argentina,1,2,Arabia Saudita

       String[] campos_resultados = linea.split(";");

       if (!primer_registro_resultados){

           if (ValidarCampos(linea)){

               if (nro_fase_actual == null){ //primer registro

                   nro_fase_actual = campos_resultados[0];
                   nro_ronda_actual=campos_resultados[1];
                   Equipo equipo1 = new Equipo(campos_resultados[2]);
                   int golesEquipo1 = Integer.parseInt(campos_resultados[3]);
                   int golesEquipo2 = Integer.parseInt(campos_resultados[4]);
                   Equipo equipo2 = new Equipo(campos_resultados[5]);
                   Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                   Partidos.add(partido);
               }
               else  
                   if (nro_fase_actual.equals(campos_resultados[0])) { //segundo registro

                       if (nro_ronda_actual.equals(campos_resultados[1])){ //misma fase comparo rondas

                           nro_fase_actual = campos_resultados[0];
                           nro_ronda_actual=campos_resultados[1];
                           Equipo equipo1 = new Equipo(campos_resultados[2]);
                           int golesEquipo1 = Integer.parseInt(campos_resultados[3]);
                           int golesEquipo2 = Integer.parseInt(campos_resultados[4]);
                           Equipo equipo2 = new Equipo(campos_resultados[5]);
                           Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                           Partidos.add(partido);
                       }
                       else {// cambio la ronda
                           Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
                           Rondas.add(ronda);  
                           Partidos.clear();
                           nro_ronda_actual=campos_resultados[1];
                           Equipo equipo1 = new Equipo(campos_resultados[2]);
                           int golesEquipo1 = Integer.parseInt(campos_resultados[3]);
                           int golesEquipo2 = Integer.parseInt(campos_resultados[4]);
                           Equipo equipo2 = new Equipo(campos_resultados[5]);
                           Partido partido = new Partido(golesEquipo1, golesEquipo2, equipo1, equipo2);      
                           Partidos.add(partido);
                       }
               }
               else { // cambio de fase

                       Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
                       Rondas.add(ronda);
                       Fase fase = new Fase(nro_fase_actual, Rondas);
                       Fases.add(fase);
                       nro_fase_actual = campos_resultados[0];
                       Rondas.clear();
                       nro_ronda_actual = campos_resultados[1];
                       Partidos.clear();
                       Equipo equipo1 = new Equipo(campos_resultados[2]);
                       int golesEquipo1 = Integer.parseInt(campos_resultados[3]);
                       int golesEquipo2 = Integer.parseInt(campos_resultados[4]);
                       Equipo equipo2 = new Equipo(campos_resultados[5]);
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
         
         // ultima fase
    Ronda ronda= new Ronda(nro_ronda_actual, Partidos);
    Rondas.add(ronda);  
    Fase fase = new Fase(nro_fase_actual, Rondas);
    Fases.add(fase);
                        
   
  
    
   
   
      // Pronosticos
       
        Path rutaParametros = Paths.get(args[1]);
        List<String> registrosParametros = null;

    try{
        registrosParametros = Files.readAllLines(rutaParametros); 
       }
    catch (IOException e) {
        System.out.println("No se pudo leer el registro de Parametros");
        System.out.println(e.getMessage());
        System.exit(1);
    }
    
    
    boolean primer_registro_parametros = true;

    Connection conexion = null;
    Statement consulta = null;
    String JDBC_DRIVER = null;
    String DB_URL=null;
    String USER = null;
    String PASS = null;
    String param_puntos = null;
    String param_extra_ronda=null;
    String param_extra_fase=null;
    
    for (String linea_param:registrosParametros) {
        String[] campos_parametros = linea_param.split(";");
        
        if (!primer_registro_parametros){

            JDBC_DRIVER = campos_parametros[0];
            DB_URL=campos_parametros[1];
            USER = campos_parametros[2];
            PASS = campos_parametros[3];
            param_puntos = campos_parametros[4];
            param_extra_ronda = campos_parametros[5];
            param_extra_fase = campos_parametros[6];
        }
        else
            primer_registro_parametros=false;
    }
            
            
  /*
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/pronosticos_deportivos";

        String USER = "root";
        String PASS = "root";
    */ 
  
  try{
        conexion = DriverManager.getConnection(DB_URL, USER, PASS);

        consulta = conexion.createStatement();
        String sql;
        sql = "SELECT participante, fase, ronda, equipo1, gana_1, empata, " +
                 "gana_2, equipo2 " +
                " from pronosticos " +
                " ORDER BY participante, fase, ronda;";

        ResultSet registrosPronosticos = consulta.executeQuery(sql);

        Partido partido_pronostico = null;
        ResultadoEnum resultado_pronostico = null;
        Fase fase_pronostico = null;
        Ronda ronda_pronostico = null;
        String participante_actual = null;   
        Equipo equipo_pronostico = null;
        ArrayList<ParticipantePuntaje>ParticipantePuntajes = new ArrayList<>();
       
   
        int param_puntos_partido = Integer.parseInt(param_puntos);
        int param_puntos_extra_ronda = Integer.parseInt(param_extra_ronda);
        int param_puntos_extra_fase = Integer.parseInt(param_extra_fase);
                 
        // Obtener las distintas filas de la consulta
        while (registrosPronosticos.next()) {
            // Obtener el valor de cada columna del registro
            String participante = registrosPronosticos.getString("participante");
            String nro_fase = registrosPronosticos.getString("fase");
            String nro_ronda = registrosPronosticos.getString("ronda");
            String nombre_equipo1 = registrosPronosticos.getString("equipo1");
            String nombre_equipo2 = registrosPronosticos.getString("equipo2");
            String empata = registrosPronosticos.getString("empata");
            String gana_equipo1 = registrosPronosticos.getString("gana_1");
            String gana_equipo2 = registrosPronosticos.getString("gana_2");
            Equipo equipo1 = new Equipo(nombre_equipo1);
            Equipo equipo2 = new Equipo(nombre_equipo2);


            for (Fase fase_buscada:Fases){

                if (fase_buscada.getNro().equals(nro_fase)){
                   fase_pronostico = fase_buscada;  

                    for(Ronda ronda_buscada:fase_pronostico.getRondas()){

                        if (ronda_buscada.getNro().equals(nro_ronda)){

                            ronda_pronostico = ronda_buscada;

                            for(Partido partido_buscado:ronda_pronostico.getPartidos()){

                                if(partido_buscado.getEquipo1().getNombre().equals(nombre_equipo1)
                                    && partido_buscado.getEquipo2().getNombre().equals(nombre_equipo2))
                                    partido_pronostico = partido_buscado;

                            }

                        }      
                    }
                }
            }
        if (empata.equals("X")){
            equipo_pronostico = equipo1;
            resultado_pronostico = ResultadoEnum.EMPATE;
        }

        if (gana_equipo1.equals("X")){
            equipo_pronostico = equipo1;
            resultado_pronostico = ResultadoEnum.GANADOR;
        }

        if (gana_equipo2.equals("X")){
            equipo_pronostico = equipo2;
            resultado_pronostico = ResultadoEnum.GANADOR;
        }

// ParticipantePuntaje
        
        
        if (participante_actual==null){

            Pronostico pronostico = new Pronostico(participante, nro_ronda, 
        nro_fase, partido_pronostico, equipo_pronostico, resultado_pronostico);

            Pronosticos.add(pronostico);
            participante_actual = participante;   
        }
        else 
            if (participante_actual.equals(participante)){ // mismo participante
                Pronostico pronostico = new Pronostico(participante_actual, 
                nro_ronda, nro_fase, partido_pronostico,
            equipo_pronostico, resultado_pronostico);
                Pronosticos.add(pronostico);
            }
            else{ // cambio de participante
                ParticipantePuntaje participantepuntaje = 
                        new ParticipantePuntaje(participante_actual, param_puntos_partido,
                        param_puntos_extra_fase, param_puntos_extra_ronda,Pronosticos);
                ParticipantePuntajes.add(participantepuntaje);
                participante_actual = participante;
                Pronosticos.clear();
            }                        
        }
            
            ParticipantePuntaje participantepuntaje = new ParticipantePuntaje
                (participante_actual, param_puntos_partido,
                param_puntos_extra_fase, param_puntos_extra_ronda, Pronosticos);
            ParticipantePuntajes.add(participantepuntaje);
            
            // Esto se utiliza par cerrar la conexión con la base de datos
            registrosPronosticos.close();
            consulta.close();
            conexion.close();
            
            
            for(ParticipantePuntaje participante_impresion:ParticipantePuntajes){
            
                String nombre_participante = participante_impresion.getParticipante();
                int cantidad_aciertos = participante_impresion.CantidadAciertos();
                int ptos_extra_fase = participante_impresion.PuntajeExtraFase();
                int ptos_extra_ronda = participante_impresion.PuntajeExtraRonda();
                int ptos_total = participante_impresion.PuntajeTotal();

                System.out.println("Nombre: " + nombre_participante
                        + "    aciertos: " + cantidad_aciertos
                        + "   extras fase: " + ptos_extra_fase 
                        + "   extras ronda: " + ptos_extra_ronda
                        + "   ptos total: " + ptos_total);
                }           
            
            
        } catch (SQLException se) {
            // Execpción ante problemas de conexión
            se.printStackTrace();
        } finally {
            // Esta sentencia es para que ante un problema con la base igual se cierren las conexiones
            try {
                if (consulta != null)
                    consulta.close();
            } catch (SQLException se2) {
            }
            try {
                if (conexion != null)
                    conexion.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
        
      
}  