package dam.backend.controller;
import java.util.List;

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
import dam.backend.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8100, http://localhost:8101")
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping({"/",""}) 
	public List <Usuario> index() {
		return usuarioRepository.findAll();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("/{id}")
	public Usuario showUsuario(@PathVariable("id") int id) { 
		Usuario user = usuarioRepository.findById(id).orElse(null);
		//Pasamos el atributo password como null por seguridad
		user.setPassword(null);
		return user;
	}
	
	//Crear pelicula
	@PostMapping({""})
	@ResponseStatus (HttpStatus.CREATED)
	public Usuario creaUsuario(@RequestBody Usuario usuario) {  
		return usuarioRepository.save(usuario);
	}
	
	/***
	 * Borramos una lista
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraUsuario(@PathVariable("id") int id) {
		usuarioRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una lista dependiendo de su id
	 * **/
	@PutMapping("/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Usuario actualizaUsuario(@RequestBody Usuario usuario, @PathVariable("id") int id) {
		Usuario actuUsuario = usuarioRepository.findById(id).orElse(null);
		
		actuUsuario.setEmail(usuario.getEmail());
		actuUsuario.setPassword(usuario.getPassword());
		actuUsuario.setNombre(usuario.getNombre());
		//actuUsuario.setToken(usuario.getToken());
		
		return usuarioRepository.save(actuUsuario);
	}
}

