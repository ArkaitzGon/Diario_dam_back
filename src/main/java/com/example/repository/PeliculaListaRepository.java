package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.PeliculaLista;
import com.example.domain.PeliculaListaId;

public interface PeliculaListaRepository extends JpaRepository<PeliculaLista, PeliculaListaId>{

}
