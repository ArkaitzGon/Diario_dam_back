package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario,Integer>{
	Optional<Usuario> findByEmail(String email);
}
