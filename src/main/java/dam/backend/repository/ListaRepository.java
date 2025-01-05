package dam.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dam.backend.domain.Lista;
import dam.backend.domain.Usuario;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer>{
	// Método para encontrar una película por su título
    Optional<List <Lista>> findByUsuario(Usuario usuario);
}
