package dam.backend.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dam.backend.domain.Lista;
import dam.backend.domain.Usuario;
import dam.backend.repository.ListaRepository;
import dam.backend.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/lista")
public class ListaController {

	@Autowired
	ListaRepository listaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Devuelve las listas de un usuario
	 * @param principal Informacion del usuario obtenido de la cookie
	 * @return
	 */
	@GetMapping("")
	public List <Lista> index(Principal principal) {
		/*System.out.println(principal.getName());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);*/
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());


		if(usuario.isPresent()) {
			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());
			if(listas.isPresent()) {
				return listas.get();
			}
		}
		return new ArrayList();
	}

	/**
	 * Crea una lista en la bd.
	 * @param nombre nombre de la lista
	 * @param principal Informacion del usuario obtenido de la cookie
	 * @return
	 */
	@PostMapping("")
	@ResponseStatus (HttpStatus.CREATED)
	public List<Lista> creaLista(@RequestBody String nombre, Principal principal){
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		
		if(usuario.isPresent()) {
			Lista listaRecibida = new Lista(usuario.get(), nombre);
			listaRepository.save(listaRecibida);
			
			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());
			if(listas.isPresent()) {
				return listas.get();
			}
		}
		return new ArrayList<>();
	}

	/**
	 * Dado un Id de una lista se elimina una lista de la bd
	 * @param id
	 * @param principal Informacion del usuario obtenido de la cookie
	 * @return
	 */
	@DeleteMapping("/{id}")
	public List<Lista> borraLista(@PathVariable("id") int id, Principal principal) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());

		if (usuario.isPresent() ){
			if(listaRepository.existsByIdAndUsuario(id, usuario.get()));
			listaRepository.deleteById(id);

			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());

			if(listas.isPresent()) {
				return listas.get();
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada o no pertenece al usuario");
			}
		}
		return new ArrayList<>();
	}
}