
package com.mycompany.pronosticos_deportivos;

import java.util.ArrayList;


public class ParticipantePuntaje {
    
    private String participante;
    private ArrayList<Pronostico> Pronosticos;
    private int param_puntos_partido;
    private int param_puntos_extra_fase;
    private int param_puntos_extra_ronda;

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public ArrayList<Pronostico> getPronosticos() {
        return Pronosticos;
    }

    public void setPronosticos(ArrayList<Pronostico> Pronosticos) {
        this.Pronosticos = Pronosticos;
    }

    public ParticipantePuntaje(String participante, int param_puntos_partido,
            int param_puntos_extra_fase, int param_puntos_extra_ronda, 
            ArrayList<Pronostico> Pronosticos_participante)  {

        Pronosticos=new ArrayList<>() ;
        this.participante = participante;
        this.param_puntos_partido=param_puntos_partido;
        this.param_puntos_extra_fase= param_puntos_extra_fase;
        this.param_puntos_extra_ronda=param_puntos_extra_ronda;
        

        for(int i = 0; i < Pronosticos_participante.size(); i++){
            //if (Pronosticos_participante.get(i).getParticipante().equals(participante)){
                Pronostico pronostico = new Pronostico(Pronosticos_participante.get(i).getParticipante(),
                    Pronosticos_participante.get(i).getNro_ronda(),
                    Pronosticos_participante.get(i).getNro_fase(),
                    Pronosticos_participante.get(i).getPartido(),
                    Pronosticos_participante.get(i).getEquipo(),
                    Pronosticos_participante.get(i).getResultado());
                Pronosticos.add(pronostico);
                
            }
        }
    
    public int CantidadAciertos(){
        
        int aciertos=0;
        
        for(Pronostico pronostico_participante:Pronosticos){
            
            if(pronostico_participante.acertado())
                aciertos++;
            
        }
        
        
        return aciertos;
    }
    
    public int PuntajeExtraFase(){
        
        int puntaje = 0;
        int cantidad_aciertos_fase = 0;
        int cantidad_pronosticos_fase = 0;
        String fase_actual = null;
         
        for (Pronostico pronostico:Pronosticos){
            if (fase_actual!=null){
                if (fase_actual.equals(pronostico.getNro_fase())){
                    cantidad_pronosticos_fase++;
                    if(pronostico.acertado())
                        cantidad_aciertos_fase++;
                }
                else{ // cambio de fase entonces calculo puntaje de la fase anterior
                    if(cantidad_aciertos_fase==cantidad_pronosticos_fase)
                        puntaje = puntaje + this.param_puntos_extra_fase;
                    fase_actual = pronostico.getNro_fase();
                    cantidad_pronosticos_fase = 1;
                    cantidad_aciertos_fase = 0;
                    if(pronostico.acertado())
                        cantidad_aciertos_fase++;
                }
            }
            else{    // primer registro fase_actual = null
                cantidad_pronosticos_fase++;
                if(pronostico.acertado())
                        cantidad_aciertos_fase++;
                fase_actual = pronostico.getNro_fase();
                }
        }
        
        if(cantidad_aciertos_fase==cantidad_pronosticos_fase)
                        puntaje = puntaje + this.param_puntos_extra_fase;
        
        return puntaje;
    }
    
    public int PuntajeExtraRonda(){
        int puntaje = 0;
        
        int cantidad_aciertos_ronda = 0;
        int cantidad_pronosticos_ronda = 0;
        String fase_actual = null;
        String ronda_actual = null;
         
        for (Pronostico pronostico:Pronosticos){
            if (fase_actual!=null){
                if (fase_actual.equals(pronostico.getNro_fase())){
                    if (ronda_actual.equals(pronostico.getNro_ronda())){
                        cantidad_pronosticos_ronda++;
                        if(pronostico.acertado())
                            cantidad_aciertos_ronda++;
                    }
                    else{ // cambio de ronda dentro de la fase, calculo puntaje y reincio ronda
                        if(cantidad_aciertos_ronda==cantidad_pronosticos_ronda)
                            puntaje = puntaje + this.param_puntos_extra_ronda;
                        ronda_actual=pronostico.getNro_ronda();
                        cantidad_pronosticos_ronda = 1;
                        cantidad_aciertos_ronda = 0;
                        if(pronostico.acertado())
                            cantidad_aciertos_ronda++;
                    }
                }
                else{ // cambio de fase, calculo puntaje y reinicio ronda
                    if(cantidad_aciertos_ronda==cantidad_pronosticos_ronda)
                            puntaje = puntaje + this.param_puntos_extra_ronda;
                    fase_actual=pronostico.getNro_fase();
                    ronda_actual=pronostico.getNro_ronda();
                    cantidad_pronosticos_ronda = 1;
                    cantidad_aciertos_ronda = 0;
                    if(pronostico.acertado())
                        cantidad_aciertos_ronda++;    
               
                }
            }
            else{    // primer registro fase_actual = null
                cantidad_pronosticos_ronda++;
                if(pronostico.acertado())
                        cantidad_aciertos_ronda++;
                fase_actual=pronostico.getNro_fase();
                ronda_actual=pronostico.getNro_ronda();
                }
        }
        if(cantidad_aciertos_ronda==cantidad_pronosticos_ronda)
                        puntaje = puntaje + this.param_puntos_extra_ronda;
                
        return puntaje;
    }
    
    public int PuntajeTotal(){
        int puntaje;
        puntaje=(this.CantidadAciertos()* this.param_puntos_partido) + 
                this.PuntajeExtraFase() + this.PuntajeExtraRonda();
        return puntaje;
    }
    
}
    
    
    

