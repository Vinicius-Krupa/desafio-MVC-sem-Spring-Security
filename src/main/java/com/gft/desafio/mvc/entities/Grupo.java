package com.gft.desafio.mvc.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Grupo implements Comparable<Grupo> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "grupo")
	private List<Participante> participantes;
	
	@ManyToOne
	private Evento evento;
	
	private int pontuacaoAtividades;
	private int pontuacaoPresenca;
	private int pontuacaoTotal;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public int getPontuacaoAtividades() {
		return pontuacaoAtividades;
	}

	public void setPontuacaoAtividades(int pontuacaoAtividades) {
		this.pontuacaoAtividades = pontuacaoAtividades;
		this.setPontuacaoTotal();
	}

	public int getPontuacaoPresenca() {
		return pontuacaoPresenca;
	}

	public void setPontuacaoPresenca(int pontuacaoPresenca) {
		this.pontuacaoPresenca = pontuacaoPresenca;
		this.setPontuacaoTotal();
	}

	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}

	private void setPontuacaoTotal() {
		this.pontuacaoTotal = this.pontuacaoAtividades + this.pontuacaoPresenca;
	}

	@Override
	public int compareTo(Grupo grupo) {
		if (this.pontuacaoTotal < grupo.getPontuacaoTotal()) {
			return -1;
		} else if (this.pontuacaoTotal > grupo.getPontuacaoTotal()) {
			return 1;
		}
		return 0;
	}
	
}
