
package com.mycompany.pronosticos_deportivos;

public class Pronostico {
    
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

    public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }
    
    
    
   
    
    public int puntos(){
        
        /*// testing
        System.out.println(Resultado_Partido);
*/
        int p;
        
     /*   // testing
        System.out.println("Salida del metodo PUNTOS " );
        System.out.println("Resultado de this. " + this.resultado );
        System.out.println("Resultado de equipo.getnombre " + equipo.getNombre() );
        
        System.out.println("Resultado de this.partido.resultado(equipo.getnombre) " + resultado_partido );
    */    
        
        if (this.resultado == this.partido.resultado(equipo))   // resultado del pronostico = resultado del partido
            p=1;
        else
            p=0;
        
        return p;
        
    }
}
