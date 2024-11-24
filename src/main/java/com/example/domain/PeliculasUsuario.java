package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name="PELICULAUSUARIO")
@IdClass(PeliculasUsuarioId.class)
public class PeliculasUsuario {

	@Id
	@Column(name="USURIOID")
	private int usuarioId;
	
	@Id
	@Column(name="PELICULAID")
	private int peliculaId;
	
	@Column(name="LISTA")
	private String lista;
}
