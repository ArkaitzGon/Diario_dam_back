package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.domain.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{

	
		//Año y genero
		// Búsqueda de películas por género (como subcadena) y año de estreno
	    @Query("SELECT p FROM Pelicula p WHERE " +
	           "LOWER(p.genero) LIKE LOWER(CONCAT('%', :genero, '%')) " +
	           "AND p.fechaEstreno = :fechaEstreno")
		List<Pelicula> findByGeneroAndFechaEstreno(String genero, int fechaEstreno);
		
		// Método para encontrar una película por su título
	    List<Pelicula> findByTitulo(String titulo);
	    
	    @Query("SELECT p FROM Pelicula p WHERE " +
	    	       "LOWER(TRIM(p.reparto)) LIKE LOWER(CONCAT('%', :reparto, '%')) " +
	    	       "AND p.fechaEstreno = :fechaEstreno")
		List<Pelicula> findByFechaEstrenoAndReparto(String reparto, int fechaEstreno);

}