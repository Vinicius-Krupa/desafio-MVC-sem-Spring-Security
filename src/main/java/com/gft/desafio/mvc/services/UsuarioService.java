package com.gft.desafio.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.desafio.mvc.entities.Usuario;
import com.gft.desafio.mvc.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}
	
	public void deletar(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Usuario obterUsuario(Long id) {
		return usuarioRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
	}
}
