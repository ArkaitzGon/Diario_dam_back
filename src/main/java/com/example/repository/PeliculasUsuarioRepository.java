package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.PeliculasUsuario;
import com.example.domain.PeliculasUsuarioId;

public interface PeliculasUsuarioRepository extends JpaRepository<PeliculasUsuario, PeliculasUsuarioId>{

}
