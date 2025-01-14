package dam.backend.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import dam.backend.service.CarteleraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import dam.backend.dto.CarteleraFecha;

import dam.backend.domain.Cartelera;
import dam.backend.domain.Cine;
import dam.backend.domain.Pelicula;
import dam.backend.dto.CineCartelera;
import dam.backend.dto.PeliculaCartelera;
import dam.backend.repository.CarteleraRepository;
import dam.backend.repository.CineRepository;
import dam.backend.repository.PeliculaRepository;

@RestController
@RequestMapping("/api/cartelera")
public class CarteleraController {

	@Autowired
	CarteleraRepository carteleraRepository;
	@Autowired
	PeliculaRepository peliculaRepository;
	@Autowired
	CarteleraService carteleraService;

	/**
	 * Devuelve la cartelera de la fecha actual
	 * @return
	 */
	@GetMapping("")
	public CarteleraFecha index() {
		LocalDate fechaActual = LocalDate.now(); // Guarda la fecha actual
		
		// Formatear a string con el formato YYYY-MM-DD
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formato);

	    return new CarteleraFecha(
			fechaFormateada,
			carteleraService.getCartelera()
		);
	}

	/**
	 * Devuelve la lista de peliculas que estan en cartelera
	 * @return
	 */
	@GetMapping("/pelisCartel")
	public List<Pelicula> showPeliculasCartelera(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		List<Cartelera> lista = carteleraRepository.findByFecha(
				new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())
		);
		ArrayList<Integer> listaId = new ArrayList<>();
		List<Pelicula> listaPeliculas = new ArrayList<>();

		for (Cartelera cartelera : lista) {
			//Comprobamos que el id no se encuentra dentro del array
			if (!listaId.contains(cartelera.getPeliculaId())) {
				listaId.add(cartelera.getPeliculaId());
				listaPeliculas.add(peliculaRepository
						.findById(cartelera.getPeliculaId())
						.orElse(new Pelicula())
				);
			}
		}
		return listaPeliculas;
	}
	
}