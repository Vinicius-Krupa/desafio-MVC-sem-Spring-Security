package com.gft.desafio.mvc.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.mvc.entities.Evento;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.repositories.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public Evento salvar(Evento evento) {
		return eventoRepository.save(evento);
	}
	
	public List<Evento> listar(){
		return eventoRepository.findAll();
	}
	
	public void deletar(Long id) {
		eventoRepository.deleteById(id);
	}
	
	public Evento obterEvento(Long id) {
		return eventoRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento não encontrado!"));
	}
	
	public List<Grupo> ordenarGruposPorRanking(List<Grupo> grupos){
		Collections.sort(grupos);
		Collections.sort(grupos, Collections.reverseOrder());
		return grupos;
	}
	
}
