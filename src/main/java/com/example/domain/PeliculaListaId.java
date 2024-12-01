package com.example.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PeliculaListaId implements Serializable{

	private int listaId;
    private int peliculaId;
    
 // Constructor sin parámetros
    public PeliculaListaId() {}
    
    // Constructor con parámetros
    public PeliculaListaId(int listaId, int peliculaId) {
        this.listaId = listaId;
        this.peliculaId = peliculaId;
    }
    
 // Getters y Setters
    public int getListaId() {
        return listaId;
    }

    public void setListaId(int listaId) {
        this.listaId = listaId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }
}
