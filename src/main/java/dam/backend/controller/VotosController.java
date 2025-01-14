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
@RequestMapping("/api/votos")
public class VotosController {

	@Autowired
	VotosRepository votosRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Comprueba si la pelicula ha sido votada por el usuario y calcula la valoracion media de la pelicula
	 * @param id
	 * @param principal
	 * @return ValoracionDTO con la media y con true si ya ha sido votada por este usuario
	 */
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

	/**
	 * Inserta un voto en la bd
	 * @param valoracion
	 * @param principal
	 * @return
	 */
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
				return valoracion;
			}
		}
		valoracion.setPuntuacion(-1);
		return valoracion;
	}
}