package com.gft.desafio.mvc.dto;

import javax.validation.constraints.NotBlank;

import com.gft.desafio.mvc.entities.Participante;

public class RequisicaoFormParticipante {
	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotBlank
	private String quatroLetras;
	@NotBlank
	private String nivel;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQuatroLetras() {
		return quatroLetras;
	}
	public void setQuatroLetras(String quatroLetras) {
		this.quatroLetras = quatroLetras;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public Participante toParticipante() {
		Participante participante = new Participante();
		participante.setNome(this.nome);
		participante.setEmail(this.email);
		participante.setNivel(this.nivel);
		participante.setQuatroLetras(this.quatroLetras);
		
		return participante;
	}
	
	public void toParticipante(Participante participante) {
		participante.setNome(this.nome);
		participante.setEmail(this.email);
		participante.setNivel(this.nivel);
		participante.setQuatroLetras(this.quatroLetras);
	}
	
	public void fromParticipante(Participante participante) {
		this.nome = participante.getNome();
		this.email = participante.getEmail();
		this.nivel = participante.getNivel();
		this.quatroLetras = participante.getQuatroLetras();
	}
	
}
