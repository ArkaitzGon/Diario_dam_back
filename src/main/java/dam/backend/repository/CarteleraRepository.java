package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Cartelera;

@Repository
public interface CarteleraRepository extends JpaRepository <Cartelera,Integer>{

}
