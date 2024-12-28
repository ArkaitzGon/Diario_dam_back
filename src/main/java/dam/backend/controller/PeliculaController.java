package dam.backend.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dam.backend.domain.Pelicula;
import dam.backend.repository.PeliculaRepository;

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
	@GetMapping("id/{id}")
	public Pelicula show(@PathVariable int id) { 
		/*
		Pelicula peli = peliculaRepository.findById(id).orElse(null);
		return peli
		*/
		
		return peliculaRepository.findById(id).orElse(null);
	}
	
	
	//Devuelve todas las peliculas enCartel
	@GetMapping("encartel")
	public List <Pelicula> showEnCartel() { 
		List <Pelicula> peliculas = peliculaRepository.findAll();
		List <Pelicula> pelisEnCartel = new ArrayList<>();
		
		for (Pelicula peli: peliculas) {
			if(peli.getEnCartel() != null && peli.getEnCartel().equals("true")) {
				pelisEnCartel.add(peli);
			}
		}
		
		return pelisEnCartel;
	}
	
	
	@GetMapping("/buscar/genero_reparto_fecha_titulo")
	public List<Pelicula> buscarPorGeneroRepartoFechaEstrenoYNombre(
	        @RequestParam(required = false) String genero,
	        @RequestParam(required = false) String reparto,
	        @RequestParam(required = false) Integer fechaEstreno,
	        @RequestParam(required = false) String titulo) {
	    // Llama al repositorio con todos los parámetros
	    return peliculaRepository.findByGeneroRepartoFechaEstrenoYTitulo(genero, reparto, fechaEstreno, titulo);
	}
	
	//Crear pelicula
	@PostMapping({"crea"})
	@ResponseStatus (HttpStatus.CREATED)
	public Pelicula creaPelicula(@RequestBody Pelicula peli) {  
		return peliculaRepository.save(peli);
	}

	
	/***
	 * Borramos una pelicula
	 * Le pasamos por parametro el ID
	 * **/
	@DeleteMapping("borra/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void borraPelicula(@PathVariable int id) {
		peliculaRepository.deleteById(id);
	}
	
	/**
	 * Actualizamos una pelicula dependiendo de su id
	 * **/
	@PutMapping("actualiza/{id}")
	@ResponseStatus (HttpStatus.CREATED)
	public Pelicula actualizaPelicula(@RequestBody Pelicula pelicula, @PathVariable int id) {
		Pelicula actuPeli = peliculaRepository.findById(id).orElse(null);
		
		actuPeli.setTitulo(pelicula.getTitulo());
		actuPeli.setImagen(pelicula.getImagen());
		actuPeli.setFechaEstreno(pelicula.getFechaEstreno());
		actuPeli.setGenero(pelicula.getGenero());
		actuPeli.setReparto(pelicula.getReparto());
		actuPeli.setResumen(pelicula.getResumen());
		actuPeli.setValoracion(pelicula.getValoracion());
		actuPeli.setAltoImagen(pelicula.getAltoImagen());
		actuPeli.setAnchoImagen(pelicula.getAnchoImagen());

		
		return peliculaRepository.save(actuPeli);
	}
/*
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
	  
	
	  //Busca Lista de peliculas por genero y año
	@GetMapping("/buscar/reparto_fecha")
	public List<Pelicula> buscarPorRepartoYAnio(
	        @RequestParam("reparto") String reparto,
	        @RequestParam("fechaEstreno") int fechaEstreno) {
		System.out.println("Reparto recibido: " + reparto);
	    return peliculaRepository.findByFechaEstrenoAndReparto(reparto, fechaEstreno);
	}
	*/
	// Busca una película por género, reparto y año
	/*@GetMapping("/buscar/genero_reparto_fecha")
	public List<Pelicula> buscarPorGeneroRepartoYAnio(
	        @RequestParam("genero") String genero,
	        @RequestParam("reparto") String reparto,
	        @RequestParam("fechaEstreno") int fechaEstreno) {
	    return peliculaRepository.findByFechaEstrenoGeneroAndReparto(genero, reparto, fechaEstreno);
	}*/
	/*
	@GetMapping("/buscar/genero_reparto_fecha")
	public List<Pelicula> buscarPorGeneroRepartoYAnio(
	        @RequestParam(value = "genero", required = false) String genero,
	        @RequestParam(value = "reparto", required = false) String reparto,
	        @RequestParam(value = "fechaEstreno", required = false) Integer fechaEstreno) {
	    return peliculaRepository.findByGeneroRepartoAndFechaEstreno(genero, reparto, fechaEstreno);
	}*/


	//Devuelva una lista con todos los generos diferentes
	@GetMapping("/generos")
    public List<String> obtenerGeneros() {
        // Obtener todas las películas
        List<Pelicula> peliculas = peliculaRepository.findAll();
        
        // Lista para almacenar los géneros
        List<String> generos = new ArrayList<>();
        
        //Recorremos la lista de peliculas para conseguir los generos
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getGenero() != null && !pelicula.getGenero().isEmpty()) {
            	//Dividimos el string por cada coma para conseguir los generos de la pelicula
                String[] generosArray = pelicula.getGenero().split(",");
                for (String genero : generosArray) {
                	//Conseguimos los generos de cada pelicula
                    String generoTrimmed = genero.trim();
                    //Añade el genero si no esta ya en la lista
                    if (!generos.contains(generoTrimmed)) {
                        generos.add(generoTrimmed);
                    }
                }
            }
        }
        
        return generos;
    }


}
