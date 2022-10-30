package com.gft.desafio.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.mvc.entities.Atividade;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.repositories.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	public List<Grupo> listar(){
		return grupoRepository.findAll();
	}
	
	public void deletar(Long id) {
		grupoRepository.deleteById(id);
	}
	
	public Grupo obterGrupo(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo não encontrado!"));
	}
	
	public void atualizarPontuacao(Grupo grupo) {
		List<Atividade> atividades = grupo.getEvento().getAtividades();
		int pontuacaoAtividades = 0;
		
		for (Atividade atividade : atividades) {
			int participantesQueEntregaram = 0;
			for (int i = 0; i < atividade.getEntregaram().size(); i++) {
				if (atividade.getEntregaram().get(i).getGrupo().equals(grupo)) {
					pontuacaoAtividades += 5;
					participantesQueEntregaram++;
				}
			}
			if(participantesQueEntregaram == grupo.getParticipantes().size()) {
				pontuacaoAtividades += 3;
			}
		}
		
		
		grupo.setPontuacaoAtividades(pontuacaoAtividades);
		
	}
}
