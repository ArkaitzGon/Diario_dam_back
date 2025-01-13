package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Cine;

@Repository
public interface CineRepository extends JpaRepository <Cine,Integer>{
    Cine save(Cine cine);

}
