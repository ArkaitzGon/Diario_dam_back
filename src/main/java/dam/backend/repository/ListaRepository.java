package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer>{

}
