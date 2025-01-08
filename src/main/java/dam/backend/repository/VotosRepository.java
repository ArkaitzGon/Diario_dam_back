package dam.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Votos;

@Repository
public interface VotosRepository extends JpaRepository<Votos, Integer>{

	// Método para encontrar una película por su título
    Optional<List <Votos>> findByPeliculaId(int peliculaId);
}
