package com.gft.desafio.mvc.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.entities.Participante;

public class RequisicaoFormGrupo {
	
	@NotBlank
	private String nome;
	private List<Participante> participantes;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Participante> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}
	
	public Grupo toGrupo() {
		Grupo grupo = new Grupo();
		
		grupo.setNome(this.nome);
		grupo.setParticipantes(this.participantes);
		return grupo;
	}
	
	public void toGrupo(Grupo grupo) {
		grupo.setNome(this.nome);
		grupo.setParticipantes(this.participantes);
	}
	
	public void fromGrupo(Grupo grupo) {
		this.nome = grupo.getNome();
		this.participantes = grupo.getParticipantes();
	}
}
