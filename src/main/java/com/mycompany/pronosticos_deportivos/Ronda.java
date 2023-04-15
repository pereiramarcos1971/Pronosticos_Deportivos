/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pronosticos_deportivos;
import java.util.Collection;

/**
 *
 * @author perei_qior
 */
public class Ronda {
    private String nro;
    private Collection<Partido> Partidos;

    public Ronda(String nro, Collection<Partido> Partidos) {
        this.nro = nro;
        this.Partidos = Partidos;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Collection<Partido> getPartidos() {
        return Partidos;
    }

    public void setPartidos(Collection<Partido> Partidos) {
        this.Partidos = Partidos;
    }

    
    
}
