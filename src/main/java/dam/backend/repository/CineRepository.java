package dam.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Cine;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CineRepository extends JpaRepository <Cine,Integer>{
    @Transactional
    Cine save(Cine cine);

}
