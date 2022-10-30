package com.gft.desafio.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.repositories.ParticipanteRepository;

@Service
public class ParticipanteService {
	
	@Autowired
	private ParticipanteRepository participanteRepository;
	
	public Participante salvarParticipante(Participante participante) {
		return participanteRepository.save(participante);
	}
	
	public List<Participante> listarParticipantes(){
		return participanteRepository.findAll();
	}
	
	public void deleteParticipante(Long id) {
		participanteRepository.deleteById(id);
	}
	
	public Participante obterParticipante(Long id) {
				
		return participanteRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Participante não encontrado!"));
		
	}
}
