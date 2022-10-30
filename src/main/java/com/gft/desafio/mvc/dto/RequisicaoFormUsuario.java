package com.gft.desafio.mvc.dto;

import javax.validation.constraints.NotBlank;

import com.gft.desafio.mvc.entities.Usuario;

public class RequisicaoFormUsuario {

	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotBlank
	private String quatroLetras;
	@NotBlank
	private String senha;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome(this.nome);
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		usuario.setQuatroLetras(this.quatroLetras);

		return usuario;
	}

	public void toUsuario(Usuario usuario) {
		usuario.setNome(this.nome);
		usuario.setEmail(this.email);
		usuario.setSenha(this.senha);
		usuario.setQuatroLetras(this.quatroLetras);
	}

	public void fromUsuario(Usuario usuario) {
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.quatroLetras = usuario.getQuatroLetras();
	}
}
