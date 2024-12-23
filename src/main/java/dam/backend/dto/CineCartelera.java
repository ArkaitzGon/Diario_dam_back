package dam.backend.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CineCartelera {
    private int id;
    private String latitud;
    private String longitud;
    private String nombre;
    private List<PeliculaCartelera> peliculas;

    public CineCartelera(int id, String latitud, String longitud, String nombre) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }
}
