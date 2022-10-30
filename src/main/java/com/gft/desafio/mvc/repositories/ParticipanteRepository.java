package com.gft.desafio.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.desafio.mvc.entities.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
	
}
