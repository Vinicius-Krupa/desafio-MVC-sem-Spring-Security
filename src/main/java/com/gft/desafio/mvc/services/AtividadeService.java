package com.gft.desafio.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.mvc.entities.Atividade;
import com.gft.desafio.mvc.repositories.AtividadeRepository;

@Service
public class AtividadeService {
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	public Atividade salvar(Atividade atividade) {
		return atividadeRepository.save(atividade);
	}
	
	public List<Atividade> listar(){
		return atividadeRepository.findAll();
	}
	
	public void deletar(Long id) {
		atividadeRepository.deleteById(id);
	}
	
	public Atividade obterAtividade(Long id) {
		return atividadeRepository.findById(id).orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));
	}
}
