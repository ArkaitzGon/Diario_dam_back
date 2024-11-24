package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Votos;

public interface VotosRepository extends JpaRepository<Votos, Integer>{

}
