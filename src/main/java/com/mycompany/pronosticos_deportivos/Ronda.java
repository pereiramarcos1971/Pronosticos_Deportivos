/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pronosticos_deportivos;
import java.util.ArrayList;

/**
 *
 * @author perei_qior
 */
public class Ronda {
    private String nro;
    private ArrayList<Partido> Partidos;

    public Ronda(String nro, ArrayList<Partido> Partidos_ronda) {
        this.nro = nro;
        Partidos = new ArrayList<>();
        for(int i = 0; i < Partidos_ronda.size(); i++){
            Partido partido = new Partido(Partidos_ronda.get(i).getGolesEquipo1(),
            Partidos_ronda.get(i).getGolesEquipo2(), 
                Partidos_ronda.get(i).getEquipo1(),
                Partidos_ronda.get(i).getEquipo2());
            Partidos.add(partido);
        }
        
    }
    
    public Ronda(String nro) {
        this.nro = nro;
    }
    

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public ArrayList<Partido> getPartidos() {
        return Partidos;
    }

    public void setPartidos(ArrayList<Partido> Partidos) {
        this.Partidos = Partidos;
    }

    
}
