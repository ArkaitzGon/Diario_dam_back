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

import com.example.domain.PeliculaLista;
import com.example.domain.PeliculaListaId;
import com.example.repository.PeliculaListaRepository;

@RestController
@RequestMapping("/api/pelicula_lista")
public class PeliculaListaController {

	@Autowired
	PeliculaListaRepository peliculaListaRepository;
	
	@GetMapping({"/",""}) 
	public List <PeliculaLista> index() {
		return peliculaListaRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/id/{listaId}/{peliculaId}")
	public PeliculaLista show(@PathVariable("usuarioId") int listaId, @PathVariable("peliculaId") int peliculaId) { 
		PeliculaListaId id = new PeliculaListaId(listaId, peliculaId);
		return peliculaListaRepository.findById(id).orElse(null);
	}
	
	//Crear pelicula
	@PostMapping({"crea"})
	@ResponseStatus (HttpStatus.CREATED)
	public PeliculaLista creaLista(@RequestBody PeliculaLista peliculaLista) {  
		return peliculaListaRepository.save(peliculaLista);
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("borra/{listaId}/{peliculaId}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraLista(@PathVariable("usuarioId") int listaId, @PathVariable("peliculaId") int peliculaId) {
		PeliculaListaId id = new PeliculaListaId(listaId, peliculaId);
		peliculaListaRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("actualiza/{listaId}/{peliculaId}")
	@ResponseStatus (HttpStatus.CREATED)
	public PeliculaLista actualizaLista(@RequestBody PeliculaLista peliculaLista, @PathVariable("listaId") int listaId, @PathVariable("peliculaId") int peliculaId) {
		PeliculaListaId id = new PeliculaListaId(listaId, peliculaId);
		PeliculaLista actuPeliLista = peliculaListaRepository.findById(id).orElse(null);
		
		actuPeliLista.setPeliculaId(peliculaLista.getPeliculaId());
		actuPeliLista.setListaId(peliculaLista.getListaId());
		
		return peliculaListaRepository.save(actuPeliLista);
	}
}
