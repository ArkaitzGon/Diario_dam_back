package dam.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dam.backend.domain.Cine;
import dam.backend.repository.CineRepository;

@RestController
@RequestMapping("/api/cine")
public class CineController {

	@Autowired
	CineRepository cineRepository;
	
	@GetMapping({"/",""}) 
	public List <Cine> index() {
		return cineRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("id/{id}")
	public Cine showUsuario(@PathVariable int id) { 
		return cineRepository.findById(id).orElse(null);
	}
}
