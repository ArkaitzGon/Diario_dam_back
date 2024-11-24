package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.PeliculasUsuario;
import com.example.domain.PeliculasUsuarioId;
import com.example.repository.PeliculasUsuarioRepository;

@RestController
@RequestMapping("/api/peliculas_usuario")
public class PeliculasUsuarioController {

	@Autowired
	PeliculasUsuarioRepository peliculasUsuarioRepository;
	
	@GetMapping({"/",""}) 
	public List <PeliculasUsuario> index() {
		return peliculasUsuarioRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/id/{usuarioId}/{peliculaId}")
	public PeliculasUsuario show(@PathVariable("usuarioId") int usuarioId, @PathVariable("peliculaId") int peliculaId) { 
		PeliculasUsuarioId id = new PeliculasUsuarioId(usuarioId, peliculaId);
		return peliculasUsuarioRepository.findById(id).orElse(null);
	}
	
	//Crear pelicula
	@PostMapping({"crea"})
	@ResponseStatus (HttpStatus.CREATED)
	public PeliculasUsuario creaLista(@RequestBody PeliculasUsuario peliculasUsuario) {  
		return peliculasUsuarioRepository.save(peliculasUsuario);
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("borra/{usuarioId}/{peliculaId}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraLista(@PathVariable("usuarioId") int usuarioId, @PathVariable("peliculaId") int peliculaId) {
		PeliculasUsuarioId id = new PeliculasUsuarioId(usuarioId, peliculaId);
		peliculasUsuarioRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("actualiza/{usuarioId}/{peliculaId}")
	@ResponseStatus (HttpStatus.CREATED)
	public PeliculasUsuario actualizaLista(@RequestBody PeliculasUsuario peliculasUsuario, @PathVariable("usuarioId") int usuarioId, @PathVariable("peliculaId") int peliculaId) {
		PeliculasUsuarioId id = new PeliculasUsuarioId(usuarioId, peliculaId);
		PeliculasUsuario actuPeliUsuario = peliculasUsuarioRepository.findById(id).orElse(null);
		
		actuPeliUsuario.setUsuarioId(peliculasUsuario.getUsuarioId());
		actuPeliUsuario.setPeliculaId(peliculasUsuario.getPeliculaId());
		actuPeliUsuario.setLista(peliculasUsuario.getLista());
		
		return peliculasUsuarioRepository.save(actuPeliUsuario);
	}
}
