package dam.backend.service;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dam.backend.domain.Cartelera;
import dam.backend.domain.Cine;
import dam.backend.domain.Pelicula;
import dam.backend.dto.CineCartelera;
import dam.backend.dto.PeliculaCartelera;
import dam.backend.repository.CarteleraRepository;
import dam.backend.repository.CineRepository;
import dam.backend.repository.PeliculaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarteleraService {
    @Autowired
    private CineRepository repository;

    @Autowired
    private PeliculaRepository peRepository;
    
    @Autowired
    private CarteleraRepository caRepository;

    
    public List<CineCartelera> getCartelera(){
        List<CineCartelera> values = new ArrayList<>();
        List<Cine> cines = repository.findAll();
        cines.forEach(cine ->{
                CineCartelera cineCartelera =  new CineCartelera(
                        cine.getId(),
                        cine.getLongitud(),
                        cine.getLatitud(),
                        cine.getNombre()
                );
                cineCartelera.setPeliculas(this.getPeliculas(cine.getId()));
                values.add(cineCartelera);
            System.out.println("Nombre::"+cineCartelera.getNombre());
//            if(this.getPeliculas(cine.getId()).size() > 0){
//                System.out.println("Nombre::"+cineCartelera.getNombre());
//            }
        });
        return values;
    }

    public List<PeliculaCartelera> getPeliculas(int cineId){
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formato);
        
        List<PeliculaCartelera> peliculas = new ArrayList<>();
        List<Cartelera> cartelera = caRepository.findByCineIdAndFecha(
                cineId, fechaFormateada
        );

        if(!cartelera.isEmpty()){
            cartelera.forEach(cart ->{
                Pelicula pelicula = peRepository.findById(cart.getPeliculaId()).orElse(null);
                if(pelicula != null){
                    PeliculaCartelera pc = new PeliculaCartelera(
                            pelicula.getId(),
                            pelicula.getTitulo(),
                            pelicula.getImagen(),
                            pelicula.getAnchoImagen(),
                            pelicula.getAltoImagen()
                            );
                    pc.setHorario(cart.getHorario().split(","));
                    peliculas.add(pc);
                }
            });
        }
        return peliculas;
    }

    /**
     * Elimina la cartelera del dia anterior al actual
     */
    @Transactional
    public void deleteCarterleraAyer(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        String ayer = new SimpleDateFormat("yyyy-MM-dd")
                .format(calendar.getTime());
        System.out.println("ayer: " + ayer);
        caRepository.deleteByFecha(ayer);
    }

    public void delete(){
        caRepository.deleteAll();
    }
    /**
     * Dada un ruta de un fichero json carga los cines en la bd
     * @param jsonPath
     */
    public void cargarCines(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Cine>> typeReference = new TypeReference<List<Cine>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/"+jsonPath);
        try {
            List<Cine> cines = mapper.readValue(inputStream,typeReference);
            for (Cine cine : cines) {
                Cine c = repository.save(cine);
            }
            System.out.println("Users Saved!");
        } catch (IOException e){
            System.out.println("Unable to save users: " + e.getMessage());
        }
    }
}