package com.gft.desafio.mvc.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.gft.desafio.mvc.entities.Evento;

public class RequisicaoFormEvento {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInicio;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataFinal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public Evento toEvento() {
		Evento evento = new Evento();
		
		evento.setNome(this.nome);
		evento.setDataInicio(this.dataInicio);
		evento.setDataFinal(this.dataFinal);
		return evento;
	}
	
	public void toEvento(Evento evento) {
		evento.setNome(this.nome);
		evento.setDataInicio(this.dataInicio);
		evento.setDataFinal(this.dataFinal);
	}
	
	public void fromEvento(Evento evento) {
		this.nome = evento.getNome();
		this.dataInicio = evento.getDataInicio();
		this.dataFinal = evento.getDataFinal();
	}
}
