/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pronosticos_deportivos_test;

import com.mycompany.pronosticos_deportivos.Equipo;
import com.mycompany.pronosticos_deportivos.Partido;
import com.mycompany.pronosticos_deportivos.Pronostico;
import com.mycompany.pronosticos_deportivos.ResultadoEnum;
import com.mycompany.pronosticos_deportivos.Ronda;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 *
 * @author perei_qior
 */
public class PronosticoTest {
    
    @Test
    public void CalcularCorrectamentePuntajeDeMarianaConUnaRondaDeUnPartido(){
        
        String participante = "Pedro";
        Equipo equipo1 = new Equipo("Argentina");
        Equipo equipo2 = new Equipo("Arabia Saudita");
        Partido partido = new Partido(3, 0, equipo1, equipo2);
        ArrayList<Partido> Partidos = new ArrayList<>();
        Partidos.add(partido);
        Ronda ronda = new Ronda("2", Partidos);
        Equipo equipo = new Equipo("Argentina");
        ResultadoEnum resultado = ResultadoEnum.GANADOR;

         
        Pronostico pronostico = new Pronostico(participante, ronda, partido, equipo, resultado );
                
        assertEquals(1, pronostico.puntos());

        
    }
    
}
