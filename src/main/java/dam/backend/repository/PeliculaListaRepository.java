package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.PeliculaLista;
import dam.backend.domain.PeliculaListaId;

@Repository
public interface PeliculaListaRepository extends JpaRepository<PeliculaLista, PeliculaListaId>{

}
