package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Votos;

@Repository
public interface VotosRepository extends JpaRepository<Votos, Integer>{

}
