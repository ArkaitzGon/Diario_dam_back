package dam.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dam.backend.domain.Usuario;
import dam.backend.domain.Votos;
import dam.backend.dto.ValoracionDTO;
import dam.backend.repository.UsuarioRepository;
import dam.backend.repository.VotosRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8100, http://localhost:8101")
@RequestMapping("/api/votos")
public class VotosController {

	@Autowired
	VotosRepository votosRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping({"/",""}) 
	public List <Votos> index() {
		return votosRepository.findAll();
	}
	
	//Devuelve puntuacion media de una pelicula
	/*@GetMapping("/{id}")
	public ValoracionDTO show(@PathVariable("id") int id) { 
		
		Optional<List<Votos>>listaVotos = votosRepository.findByPeliculaId(id);
		
		//Comprobamos si ha sido votada
		if(listaVotos.get().size() > 0) {
			int sumatorio = 0;
			for (Votos votos : listaVotos.get()) {
				sumatorio += votos.getPuntuacion();
			}
			
			int puntuacionMedia = Math.round(sumatorio / listaVotos.get().size());
			ValoracionDTO valoracionMedia = new ValoracionDTO(true, puntuacionMedia, id);
			return valoracionMedia;
		}else {
			return new ValoracionDTO(false, -1, id);
		}
		
	}*/
	
	/*******
	 * Comprueba si la pelicula ha sido votada por el usuario
	 * Y calcula la valoracion media de la pelicula
	 * @return ValoracionDTO con la media y con true si ya ha sido votada por este usuario
	 * **/
	@GetMapping("/{id}")
	public ValoracionDTO show(@PathVariable("id") int id, Principal principal) { 
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		Optional<List<Votos>>listaVotos = votosRepository.findByPeliculaId(id);
		
		boolean votado = false;
		
		//Comprobamos si ha sido votada
		if(listaVotos.get().size() > 0) {
			int sumatorio = 0;
			for (Votos votos : listaVotos.get()) {
				sumatorio += votos.getPuntuacion();
				if(votos.getUsuarioId() == usuario.get().getId()) {
					votado = true;
				}
			}
			
			int puntuacionMedia = Math.round(sumatorio / listaVotos.get().size());
			ValoracionDTO valoracionMedia = new ValoracionDTO(votado, puntuacionMedia, id);
			return valoracionMedia;
		}else {
			return new ValoracionDTO(votado, -1, id);
		}
		
	}
	
	//Crear voto
	@PostMapping({""})
	@ResponseStatus (HttpStatus.CREATED)
	public ValoracionDTO creaVotos(@RequestBody ValoracionDTO valoracion, Principal principal) {  
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		
		if(usuario.isPresent()) {
			Votos voto = new Votos(
					valoracion.getPeliculaId(),
					usuario.get().getId(),
					valoracion.getPuntuacion()
					);
			
			votosRepository.save(voto);
			
			
			Optional<List<Votos>> listaVotos = votosRepository.findByPeliculaId(valoracion.getPeliculaId());
			
			int sumatorio = 0;
			
			if(listaVotos.isPresent()) {
				for (Votos valor : listaVotos.get()) {
					sumatorio += valor.getPuntuacion();
				}
				
				valoracion.setVotado(true);
				valoracion.setPuntuacion(Math.round(sumatorio / listaVotos.get().size()));
				System.out.println("Valoracion: " + valoracion);
				return valoracion;
			}
			
			
		}
		
		valoracion.setPuntuacion(-1);
		return valoracion;
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraVotos(@PathVariable("id") int id) {
		votosRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Votos actualizaLista(@RequestBody Votos voto, @PathVariable("id") int id) {
		Votos actuVoto = votosRepository.findById(id).orElse(null);
		
		actuVoto.setPeliculaId(voto.getPeliculaId());
		actuVoto.setUsuarioId(voto.getUsuarioId());
		actuVoto.setPuntuacion(voto.getPuntuacion());
		
		return votosRepository.save(actuVoto);
	}
}