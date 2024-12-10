package dam.backend.controller;

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

import dam.backend.domain.Votos;
import dam.backend.repository.VotosRepository;

@RestController
@RequestMapping("/api/votos")
public class VotosController {

	@Autowired
	VotosRepository votosRepository;
	
	@GetMapping({"/",""}) 
	public List <Votos> index() {
		return votosRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/id/{id}")
	public Votos show(@PathVariable int id) { 
		return votosRepository.findById(id).orElse(null);
	}
	
	//Crear pelicula
	@PostMapping({"crea"})
	@ResponseStatus (HttpStatus.CREATED)
	public Votos creaLista(@RequestBody Votos voto) {  
		return votosRepository.save(voto);
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("borra/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraLista(@PathVariable int id) {
		votosRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("actualiza/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Votos actualizaLista(@RequestBody Votos voto, @PathVariable int id) {
		Votos actuVoto = votosRepository.findById(id).orElse(null);
		
		actuVoto.setPeliculaId(voto.getPeliculaId());
		actuVoto.setUsuarioID(voto.getUsuarioID());
		actuVoto.setPuntuacion(voto.getPuntuacion());
		
		return votosRepository.save(actuVoto);
	}
}
