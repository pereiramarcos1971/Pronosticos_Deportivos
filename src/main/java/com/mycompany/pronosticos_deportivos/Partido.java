package com.mycompany.pronosticos_deportivos;

public class Partido {
   
    private int golesEquipo1;
    private int golesEquipo2;
    private Equipo equipo1;
    private Equipo equipo2;

    public int getGolesEquipo1() {
        return golesEquipo1;
    }

    public void setGolesEquipo1(int golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public int getGolesEquipo2() {
        return golesEquipo2;
    }

    public void setGolesEquipo2(int golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public Partido(int golesEquipo1, int golesEquipo2, Equipo equipo1, Equipo equipo2) {
        super();
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
        
    }

     
    public ResultadoEnum resultado(Equipo equipo) {
  
        ResultadoEnum Resultado = null;
        
        if (equipo.getNombre().equals(this.equipo1.getNombre())) 
            {
            if (this.getGolesEquipo1() > this.getGolesEquipo2())
                Resultado = ResultadoEnum.GANADOR;
            else if (this.getGolesEquipo2() > this.getGolesEquipo1())
                    Resultado = ResultadoEnum.PERDEDOR;
                else
                    Resultado = ResultadoEnum.EMPATE;   
            }
        else 
            {
            if (equipo.getNombre().equals(this.equipo2.getNombre())) 
           
                {
                if (this.getGolesEquipo2() > this.getGolesEquipo1())
                    Resultado =  ResultadoEnum.GANADOR;
                else if (this.getGolesEquipo1() > this.getGolesEquipo2())
                    Resultado = ResultadoEnum.PERDEDOR;
                else
                    Resultado = ResultadoEnum.EMPATE;
                }
            else
                Resultado = null;
            }
        return Resultado;
    }
}