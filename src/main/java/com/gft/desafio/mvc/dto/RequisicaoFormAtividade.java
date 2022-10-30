package com.gft.desafio.mvc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.gft.desafio.mvc.entities.Atividade;

public class RequisicaoFormAtividade {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInicio;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEntrega;
	
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

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Atividade toAtividade() {
		Atividade atividade = new Atividade();
		
		atividade.setNome(this.nome);
		atividade.setDataInicio(this.dataInicio);
		atividade.setDataEntrega(this.dataEntrega);
		return atividade;
	}
	
	public void toAtividade(Atividade atividade) {
		atividade.setNome(this.nome);
		atividade.setDataInicio(this.dataInicio);
		atividade.setDataEntrega(this.dataEntrega);
	}
	
	public void fromAtividade(Atividade atividade) {
		this.nome = atividade.getNome();
		this.dataInicio = atividade.getDataInicio();
		this.dataEntrega = atividade.getDataEntrega();
	}
}
