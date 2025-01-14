package dam.backend.controller;

import java.security.Principal;
import java.util.ArrayList;
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

import dam.backend.domain.Lista;
import dam.backend.domain.PeliculaLista;
import dam.backend.domain.PeliculaListaId;
import dam.backend.domain.Usuario;
import dam.backend.repository.ListaRepository;
import dam.backend.repository.PeliculaListaRepository;
import dam.backend.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8100, http://localhost:8101")
@RequestMapping("/api/pelicula_lista")
public class PeliculaListaController {

	@Autowired
	PeliculaListaRepository peliculaListaRepository;
	@Autowired
	ListaRepository listaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Agrega una pelicula a una lista
	 * @param peliculaLista
	 * @param principal
	 * @return
	 */
	@PostMapping({""})
	@ResponseStatus (HttpStatus.CREATED)
	public List<Lista> creaPeliculaLista(@RequestBody PeliculaLista peliculaLista, Principal principal) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());

		if(usuario.isPresent()) {
			if(listaRepository.existsByIdAndUsuario(peliculaLista.getListaId(), usuario.get()))
				peliculaListaRepository.save(peliculaLista);

			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());
			if(listas.isPresent()) {
				return listas.get();
			}
		}
		return new ArrayList<>();
	}

	/**
	 * Elimina una pelicula de la Lista
	 * @param listaId
	 * @param peliculaId
	 * @param principal
	 * @return
	 */
	@DeleteMapping("/{listaId}/{peliculaId}")
	public List<Lista> borrarPeliculaLista(
			@PathVariable("listaId") int listaId,
			@PathVariable("peliculaId") int peliculaId,
			Principal principal)
	{
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		List<Lista> listaPrueba = new ArrayList<>();
		if(usuario.isPresent()) {
			if(listaRepository.existsByIdAndUsuario(peliculaId, usuario.get()))
				peliculaListaRepository.deleteByListaIdAndPeliculaId(listaId, peliculaId);

			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());

			if(listas.isPresent()) {
				return listas.get();
			}
		}
		return listaPrueba;
	}
}