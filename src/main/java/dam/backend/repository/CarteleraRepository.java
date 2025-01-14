package dam.backend.repository;

import java.util.List;

import dam.backend.domain.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Cartelera;

@Repository
public interface CarteleraRepository extends JpaRepository <Cartelera,Integer>{

	List<Cartelera> findByCineIdAndFecha(int cineId, String fecha);

	void deleteByFecha(String fecha);


	List<Cartelera> findByFecha(String fecha);
}
