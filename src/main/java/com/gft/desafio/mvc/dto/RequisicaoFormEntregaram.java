package com.gft.desafio.mvc.dto;

import java.util.ArrayList;
import java.util.List;

import com.gft.desafio.mvc.entities.Atividade;

public class RequisicaoFormEntregaram {

	private List<Atividade> atividades = new ArrayList<>();
	
	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public void adicionarAtividade(Atividade atividade) {
		this.atividades.add(atividade);
	}
	
}
