package com.example.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PeliculasUsuarioId implements Serializable{

	private int usuarioId;
    private int peliculaId;
    
 // Constructor sin parámetros
    public PeliculasUsuarioId() {}
    
    // Constructor con parámetros
    public PeliculasUsuarioId(int usuarioId, int peliculaId) {
        this.usuarioId = usuarioId;
        this.peliculaId = peliculaId;
    }
    
 // Getters y Setters
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }
}
