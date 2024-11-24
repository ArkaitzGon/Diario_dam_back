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

import com.example.domain.Lista;
import com.example.repository.ListaRepository;

@RestController
@RequestMapping("/api/lista")
public class ListaController {

	@Autowired
	ListaRepository listaRepository;
	
	@GetMapping({"/",""}) 
	public List <Lista> index() {
		return listaRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/id/{id}")
	public Lista show(@PathVariable("id") int id) { 
		return listaRepository.findById(id).orElse(null);
	}
	
	//Crear pelicula
	@PostMapping({"crea"})
	@ResponseStatus (HttpStatus.CREATED)
	public Lista creaLista(@RequestBody Lista lista) {  
		return listaRepository.save(lista);
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("borra/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraLista(@PathVariable("id") int id) {
		listaRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("actualiza/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Lista actualizaLista(@RequestBody Lista lista, @PathVariable("id") int id) {
		Lista actuLista = listaRepository.findById(id).orElse(null);
		
		actuLista.setPeliculaId(lista.getPeliculaId());
		actuLista.setNombre(lista.getNombre());
		
		return listaRepository.save(actuLista);
	}
}
