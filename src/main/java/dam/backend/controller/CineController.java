package dam.backend.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import dam.backend.payload.RouteCoordinates;
import dam.backend.payload.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dam.backend.domain.Cine;
import dam.backend.repository.CineRepository;
import dam.backend.service.CarteleraService;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/cine")
public class CineController {

	@Autowired
	CineRepository cineRepository;
	@Autowired
	CarteleraService carteleraService; // Para cargar los cines en la bbdd

	/**
	 * Devuelve todos los cines
	 * @return
	 */
	@GetMapping({"/",""}) 
	public List <Cine> index() {
		return cineRepository.findAll();
	}

	/**
	 * Dadas unas coordenadas llama a una api externa para obtener una ruta
	 * @param coordinates
	 * @return
	 */
	@PostMapping({"/ubicacion"})
	public Object getRoute(@Valid @RequestBody RouteCoordinates coordinates){
		System.out.println("https://router.project-osrm.org/route/v1/driving/"+
				coordinates.getCine() + ";" + coordinates.getPosicion() +
				"?overview=full&geometries=polyline&steps=true&generate_hints=false");
		WebClient client = WebClient.create(
			"https://router.project-osrm.org/route/v1/driving/"+
			coordinates.getCine() + ";" + coordinates.getPosicion() +
			"?overview=full&geometries=polyline&steps=true&generate_hints=false"
		);
		return  client.get().retrieve().bodyToMono(String.class).block();
	}

	/**
	 * Carga los cines en la bd
	 */
	@GetMapping({"/cargaCines"})
	public void cargaCines() {
		carteleraService.cargarCines("cinesAlava.json");
		carteleraService.cargarCines("cinesBizkaia.json");
		carteleraService.cargarCines("cinesGipuzkoa.json");
		System.out.println("Cines cargados con exito");
	}
}
