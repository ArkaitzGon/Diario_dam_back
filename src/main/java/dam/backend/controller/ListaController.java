package dam.backend.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	/*
	@GetMapping({"/",""}) 
	public List <Lista> index() {
		return listaRepository.findAll();
	}*/
	
	@GetMapping("")
	public List <Lista> index(Principal principal) {
		System.out.println(principal.getName());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);
		Optional<Usuario> usuario = usuarioRepository.findByEmail(principal.getName());
		
		
		if(usuario.isPresent()) {
			Optional<List <Lista>> listas = listaRepository.findByUsuario(usuario.get());
			if(listas.isPresent()) {
				return listas.get();
			}
		}
		return new ArrayList();
	}
	//Devuelve pelicula por ID
	@GetMapping("/id/{id}")
	public Lista show(@PathVariable int id) { 
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
	public void borraLista(@PathVariable int id) {
		listaRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("actualiza/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Lista actualizaLista(@RequestBody Lista lista, @PathVariable int id) {
		Lista actuLista = listaRepository.findById(id).orElse(null);
		
		actuLista.setUsuario(lista.getUsuario());
		actuLista.setNombre(lista.getNombre());
		
		return listaRepository.save(actuLista);
	}
}
