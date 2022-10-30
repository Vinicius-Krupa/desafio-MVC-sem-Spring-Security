package com.gft.desafio.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.mvc.dto.RequisicaoFormGrupo;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.services.EventoService;
import com.gft.desafio.mvc.services.GrupoService;
import com.gft.desafio.mvc.services.ParticipanteService;

@Controller
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("grupo")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("grupo/listar.html");
		mv.addObject("lista", grupoService.listar());
		
		return mv;
	}
	
	@GetMapping("grupo/participantes")
	public ModelAndView listarParticipantesGrupo(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("grupo/participantes.html");
		
		Grupo grupo = grupoService.obterGrupo(id);
		
		if (grupo.getParticipantes().isEmpty()) {
			mv.addObject("mensagem", "Não há participantes no grupo!");
		}
		mv.addObject("grupo", grupo);
		mv.addObject("listaParticipantes", grupo.getParticipantes());
		
		return mv;
	}
	
	@GetMapping("evento/grupo/novo")
	public ModelAndView novo(@RequestParam(required = false) Long id, RequisicaoFormGrupo requisicao) {
		ModelAndView mv = new ModelAndView("grupo/form.html");
		mv.addObject("eventoId", id);
		
		return mv;
	}

	@PostMapping("evento/grupo/novo")
	public ModelAndView criar(@RequestParam(required = false) Long id, @Valid RequisicaoFormGrupo requisicao,
										BindingResult result, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("grupo/form.html");
			mv.addObject("eventoId", id);
			return mv;
		} else {
			
			ModelAndView mv = new ModelAndView("redirect:/evento/detalhes?id=" + id);
			Grupo grupo = requisicao.toGrupo();
			grupo.setEvento(eventoService.obterEvento(id));
			grupoService.salvar(grupo);
			mv.addObject("eventoId", id);
			
			redirectAttributes.addFlashAttribute("mensagem", "Grupo #" + grupo.getId() + " criado com sucesso!");
			return mv;
		}
	}
	
	@GetMapping("grupo/editar")
	public ModelAndView editar(@RequestParam(required = false) Long id, RequisicaoFormGrupo requisicao,
								RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("grupo/edit.html");
		Grupo grupo;
		
		try {
			grupo = grupoService.obterGrupo(id);
			requisicao.fromGrupo(grupo);
			mv.addObject("grupoId", grupo.getId());
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "Grupo #" + id + " não encontrado!");
			mv.setViewName("redirect:/grupo/novo");
		}
		
		return mv;
	}
	
	@GetMapping("grupo/detalhes")
	public ModelAndView detalhes(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("grupo/show.html");
		Grupo grupo;
		
		try {
			grupo = grupoService.obterGrupo(id);
		} catch (RuntimeException e) {
			grupo = new Grupo();
			mv.addObject("mensagem", "Grupo #" + id + " não encontrado!");
		}
		
		mv.addObject("grupo", grupo);
		return mv;
	}
	
	@PostMapping("grupo/detalhes")
	public ModelAndView atualizar(@RequestParam(required = true) Long id, @Valid RequisicaoFormGrupo requisicao,
												BindingResult result, 
												RedirectAttributes redirectAttributes) {
		ModelAndView mv;
		Grupo grupo;
		
		if(result.hasErrors()) {
			mv = new ModelAndView("grupo/edit.html");
			mv.addObject("grupoId", id);
			return mv;
		} else {
			try {
				grupo = grupoService.obterGrupo(id);
				requisicao.toGrupo(grupo);
				grupoService.salvar(grupo);
				
				redirectAttributes.addFlashAttribute("mensagem", "Grupo atualizado com sucesso!");
				mv = new ModelAndView("redirect:/grupo/detalhes?id=" + id);
				return mv;
			} catch (RuntimeException e) {
				mv = new ModelAndView("grupo/edit.html");
				return mv;
			}
		}
	}
	
	@GetMapping("grupo/deletar")
	public ModelAndView deletar(@RequestParam(required = true) Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/grupo");
		
		try {
			grupoService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Grupo #" + id + " deletado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "ERRO: Grupo #" + id + " não encontrado!");
		}
		return mv;
	}
	
}
