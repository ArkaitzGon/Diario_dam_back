package com.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="LISTA")
public class Lista {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private int id;
	@Column(name="PELICULAID")
	private int peliculaId;
	@Column(name="NOMBRE")
	private String nombre;
	
	// El JSONBAck... es la diferencia de SpringWeb
	@JsonBackReference // Decimos que la parte MANY es BACKREFERNECE (padre)
	@ManyToOne // un ciclista tiene un equipo, un equipo varios ciclistas. ESTA ES LA PARTE MANY
	@JoinColumn (name = "usuarioid") // Se une mediante la columna team_id de la parte ONE
	private Usuario usuarioid;
	
	//Añade propiedad teamName a JSON 
	@JsonProperty("usuarioID")
	public int getUsuarioID() {
	    return usuarioid != null ? usuarioid.getId() : null;
		}
}
