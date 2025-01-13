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
@CrossOrigin(origins = "http://localhost:8100, http://localhost:8101")
@RequestMapping("/api/cine")
public class CineController {

	@Autowired
	CineRepository cineRepository;
	@Autowired
	CarteleraService carteleraService; // Para cargar los cines en la bbdd
	
	@GetMapping({"/",""}) 
	public List <Cine> index() {
		return cineRepository.findAll();
	}

	@PostMapping({"/ubicacion"})
	public Object getRoute(@Valid @RequestBody RouteCoordinates coordinates){
		System.out.println(coordinates.getCine().toString());
		WebClient client = WebClient.create(
			"https://router.project-osrm.org/route/v1/driving/"+
			coordinates.getCine() + ";" + coordinates.getPosicion() +
			"?overview=full&geometries=polyline&steps=true&generate_hints=false"
		);
		return  client.get().retrieve().bodyToMono(String.class).block();
	}

	@GetMapping({"/ubicacion"})
	public Object getRoute(){
		WebClient client = WebClient.create(
				"https://router.project-osrm.org/route/v1/driving/-2.3134208,42.8703744;-2.668882730514457,42.862404304714566?overview=full&geometries=polyline&steps=true&generate_hints=false");
		return  client.get().retrieve().bodyToMono(String.class).block();
	}
	
	//Devuelve pelicula por ID
	@GetMapping("id/{id}")
	public Cine showUsuario(@PathVariable int id) { 
		return cineRepository.findById(id).orElse(null);
	}
	
	/********
	 * Actualiza el fichera de cines con la fecha del dia y la lista de cines
	 * **/
	@GetMapping({"fichero"}) 
	public void actualizaFichero() {
		LocalDate fechaActual = LocalDate.now(); // Guarda la fecha actual
		
		// Formatear a string con el formato YYYY-MM-DD
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formato);
		System.out.println("La fecha actual es: " + fechaFormateada);
		
		List<Cine> listaCines = cineRepository.findAll();
		
		// Ruta del fichero a escribir
        String rutaFichero = "src/main/resources/cines.json";
        
        try (FileWriter escritor = new FileWriter(rutaFichero, false)) { // `false` sobreescribe el archivo
            // Crear contenido del fichero
            StringBuilder contenido = new StringBuilder();
            contenido.append("{\n");
            contenido.append("  \"fecha\": \"").append(fechaFormateada).append("\",\n");
            contenido.append("  \"cines\": [\n");

            for (int i = 0; i < listaCines.size(); i++) {
                Cine cine = listaCines.get(i);
                contenido.append("    {\n");
                contenido.append("      \"id\": ").append(cine.getId()).append(",\n");
                contenido.append("      \"nombre\": \"").append(cine.getNombre()).append("\",\n");
                contenido.append("      \"url\": \"").append(cine.getUrl()).append("\",\n");
                contenido.append("      \"latitud\": \"").append(cine.getLatitud()).append("\",\n");
                contenido.append("      \"longitud\": \"").append(cine.getLongitud()).append("\"\n");
                contenido.append("    }");
                if (i < listaCines.size() - 1) {
                    contenido.append(","); // Coma solo si no es el último elemento
                }
                contenido.append("\n");
            }

            contenido.append("  ]\n");
            contenido.append("}\n");

            // Escribir contenido al fichero
            escritor.write(contenido.toString());
            System.out.println("Fichero actualizado correctamente: " + rutaFichero);

        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
        }

	}
	
	/***
	 * Metodo que carga los cines en la base de datos
	 * Usa un metodo de CarteleraService
	 * **/
	@GetMapping({"cargaCines"}) 
	public void cargaCines() {
		carteleraService.cargarCines("cinesAlava.json");
		carteleraService.cargarCines("cinesBizkaia.json");
		carteleraService.cargarCines("cinesGipuzkoa.json");
		System.out.println("Cines cargados con exito");
	}
}
