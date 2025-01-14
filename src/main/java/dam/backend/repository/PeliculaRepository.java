package dam.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{
	    //Con este metodo podemos hacer consultas con uno o hasta 4 parametros
	    @Query("""
                   SELECT p FROM Pelicula p WHERE (:genero IS NULL OR LOWER(p.genero) LIKE LOWER(CONCAT('%', :genero, '%'))) \
                   AND (:reparto IS NULL OR LOWER(p.reparto) LIKE LOWER(CONCAT('%', :reparto, '%'))) \
                   AND (:fechaEstreno IS NULL OR p.fechaEstreno = :fechaEstreno) \
                   AND (:titulo IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))\
                   """)
		List<Pelicula> findByGeneroRepartoFechaEstrenoYTitulo(
			@Param("genero") String genero,
			@Param("reparto") String reparto,
			@Param("fechaEstreno") Integer fechaEstreno,
			@Param("titulo") String titulo
		);

		Optional<Pelicula> findByTituloAndFechaEstreno(
			String titulo,
			int fechaEstreno
		);
		
		Optional<List<Pelicula>> findByTituloOrderByFechaEstrenoDesc(String titulo);

}