package com.gft.desafio.mvc.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.mvc.dto.RequisicaoFormUsuario;
import com.gft.desafio.mvc.entities.Usuario;
import com.gft.desafio.mvc.services.UsuarioService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("usuario/listar.html");
		mv.addObject("lista", usuarioService.listar());
		
		return mv;
	}
	
	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("usuario/login");
		return mv;
	}
	
	@GetMapping("novo")
	public ModelAndView salvar(RequisicaoFormUsuario requisicao) {
		ModelAndView mv = new ModelAndView("usuario/form.html");
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView criar(@Valid RequisicaoFormUsuario requisicao, BindingResult result,
								RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("usuario/form");
			return mv;
		} else {
			
			ModelAndView mv = new ModelAndView("redirect:/usuario");
			Usuario usuario = requisicao.toUsuario();
			usuarioService.salvar(usuario);
			
			redirectAttributes.addFlashAttribute("mensagem", "Usuário #" + usuario.getId()
													+ " criado com sucesso!");
			return mv;
		}
	}
	
	@GetMapping("editar")
	public ModelAndView editar(@RequestParam(required = false) Long id, RequisicaoFormUsuario requisicao,
									RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("usuario/edit");
		Usuario usuario;
		
		try {
			usuario = usuarioService.obterUsuario(id);
			requisicao.fromUsuario(usuario);
			mv.addObject("usuarioId", usuario.getId());
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "Usuário #" + id + " não encontrado!");
			mv.setViewName("redirect:/usuario/novo");
		}
		
		return mv;
	}
	
	@GetMapping("detalhes")
	public ModelAndView detalhes(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("usuario/show.html");
		Usuario usuario;
		
		try {
			usuario = usuarioService.obterUsuario(id);
		} catch (RuntimeException e) {
			usuario = new Usuario();
			mv.addObject("mensagem", "Usuário #" + id + " não encontrado!");
		}
		
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping("detalhes")
	public ModelAndView atualizar(@RequestParam(required = false) Long id, @Valid RequisicaoFormUsuario requisicao,
									BindingResult result, 
									RedirectAttributes redirectAttributes) {
		ModelAndView mv;
		Usuario usuario;
		
		if (result.hasErrors()) {
			mv = new ModelAndView("usuario.edit/html");
			mv.addObject("usuarioId", id);
			return mv;
		} else {
			try {
				usuario = usuarioService.obterUsuario(id);
				requisicao.toUsuario(usuario);
				usuarioService.salvar(usuario);
				
				redirectAttributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
				mv = new ModelAndView("redirect:/usuario/detalhes?id=" + id);
				return mv;
			} catch (RuntimeException e) {
				mv = new ModelAndView("usuario/edit.html");
				return mv;
			}
		}
	}
	
	@GetMapping("deletar")
	public ModelAndView deletar(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/usuario");
		
		try {
			usuarioService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário #" + id + " deletado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "ERRO: Usuário #" + id + " não encontrado!");
		}
		
		return mv;
	}
}
