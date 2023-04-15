
package com.mycompany.pronosticos_deportivos;

public class Pronostico {
    
    private String participante;
    private Ronda ronda;
    private Partido partido;
    private Equipo equipo;
    private ResultadoEnum resultado; 
    

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoEnum resultado) {
        this.resultado = resultado;
    }

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

  
    public Ronda getRonda() {
        return ronda;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    
    
    
    public Pronostico(String participante,  Ronda ronda, Partido partido, 
            Equipo equipo, ResultadoEnum resultado) {
        
        this.participante = participante;
        this.ronda = ronda;
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
        
    }
    
    
    
    public int puntos(){
        
        int p = 0;
         
        for(Partido partido_buscado:ronda.getPartidos()){ // busco el partido en la ronda

            if (partido_buscado.getEquipo1().getNombre().equals(partido.getEquipo1().getNombre())&&
                partido_buscado.getEquipo2().getNombre().equals(partido.getEquipo2().getNombre())) {

                if (this.resultado == partido_buscado.resultado(equipo))   // resultado del pronostico = resultado del partido de esa ronda
                    p=1;
                else
                    p=0;
            }
        }    
        return p;
    }

}

