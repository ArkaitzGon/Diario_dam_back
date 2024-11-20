package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Pelicula;
import com.example.repository.PeliculaRepository;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

	@Autowired
	PeliculaRepository peliculaRepository;
	
	/**
	 * Devuelve una con todas las peliculas
	 * Es bastante lento
	 * **/
	@GetMapping({"/",""}) 
	public List <Pelicula> index() {
		return peliculaRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/id/{id}")
	public Pelicula show(@PathVariable("id") int id) { 
		return peliculaRepository.findById(id).orElse(null);
	}


	//Busca pelicula por titulo
	@GetMapping("/titulo/{titulo}")
    public List<Pelicula> showTitulo(@PathVariable("titulo") String titulo) {
        // Busca todas las películas con el título proporcionado
        return peliculaRepository.findByTitulo(titulo);
    }

	
	//Busca Lista de peliculas por genero y año
	@GetMapping("/buscar/genero_año")
	public List<Pelicula> buscarPorGeneroYAnio(
	        @RequestParam("genero") String genero,
	        @RequestParam("fechaEstreno") int fechaEstreno) {
	    return peliculaRepository.findByGeneroAndFechaEstreno(genero, fechaEstreno);
	}
	  
	/*
	  //Busca Lista de peliculas por genero y año
	@GetMapping("/buscar/reparto_fecha")
	public List<Pelicula> buscarPorRepartoYAnio(
	        @RequestParam("reparto") String reparto,
	        @RequestParam("fechaEstreno") int fechaEstreno) {
		System.out.println("Reparto recibido: " + reparto);
	    return peliculaRepository.findByFechaEstrenoAndReparto(reparto, fechaEstreno);
	}
	*/

}
