package dam.backend.domain;

import java.io.Serializable;

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
