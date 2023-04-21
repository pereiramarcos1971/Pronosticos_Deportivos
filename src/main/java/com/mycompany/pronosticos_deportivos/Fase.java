
package com.mycompany.pronosticos_deportivos;

import java.util.ArrayList;


public class Fase {
    private String nro;
    private ArrayList<Ronda> Rondas; 

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public ArrayList<Ronda> getRondas() {
        return Rondas;
    }

    public void setRondas(ArrayList<Ronda> Rondas) {
        this.Rondas = Rondas;
    }

    public Fase(String nro, ArrayList<Ronda> Rondas_fase) {
        this.nro = nro;
        Rondas = new ArrayList<>();
        ArrayList<Partido> Partidos = new ArrayList<>();
        for(int i = 0; i < Rondas_fase.size(); i++){
            for(int j=0; j < Rondas_fase.get(i).getPartidos().size();j++){
                
                Partido partido = new Partido(Rondas_fase.get(i).getPartidos().get(j).getGolesEquipo1(),
                    Rondas_fase.get(i).getPartidos().get(j).getGolesEquipo2(),
                    Rondas_fase.get(i).getPartidos().get(j).getEquipo1(),
                    Rondas_fase.get(i).getPartidos().get(j).getEquipo2());
                Partidos.add(partido);
            }
                
            Ronda ronda = new Ronda(Rondas_fase.get(i).getNro(), Partidos);
            Rondas.add(ronda);
            Partidos.clear();
         }
        }   
    }


