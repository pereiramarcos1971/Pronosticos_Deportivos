
package com.mycompany.pronosticos_deportivos;

public class Pronostico {
    
    private String participante;
    private String nro_ronda;
    private String nro_fase;
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

    public String getNro_ronda() {
        return nro_ronda;
    }

    public void setNro_ronda(String nro_ronda) {
        this.nro_ronda = nro_ronda;
    }

    public String getNro_fase() {
        return nro_fase;
    }

    public void setNro_fase(String nro_fase) {
        this.nro_fase = nro_fase;
    }

    
    public Pronostico(String participante, String nro_ronda, String nro_fase, 
            Partido partido, Equipo equipo, ResultadoEnum resultado) {
        
        this.participante = participante;
        this.nro_ronda = nro_ronda;
        this.nro_fase = nro_fase;
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;       
    }  
    
    public boolean acertado(){
        
        boolean acierto=true;
        
        if (this.resultado == partido.resultado(equipo))   
            acierto=true;
        else
            acierto=false;
        
         return acierto;

        
    }
    
    
}

