package com.example.domain;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // declaramos la clase como entidad
@Table(name="PELICULA")// name se usa en caso de que la columna se llame diferente a la clase
public class Pelicula {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private int id;
	@Column(name = "TITULO")
	private String titulo;
	@Column(name = "FECHAESTRENO")
    private int fechaEstreno;
	@Column(name = "GENERO")
    private String genero; // Lista de generos
	@Column(name = "REPARTO")
    private String reparto; // Lista de actores
	@Column(name = "RESUMEN")
    private String resumen;
	@Column(name = "VALORACION")
    private Integer valoracion; // Se declara Integer porque int no permite recoger valores null
	@Column(name = "IMAGEN")
    private String imagen;
	@Column(name = "ANCHOIMAGEN")
    private int anchoImagen;
	@Column(name = "ALTOIMAGEN")
    private int altoImagen;
}

