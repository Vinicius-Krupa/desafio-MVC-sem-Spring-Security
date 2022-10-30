package com.gft.desafio.mvc.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.mvc.dto.RequisicaoFormParticipante;
import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.services.GrupoService;
import com.gft.desafio.mvc.services.ParticipanteService;

@Controller
public class ParticipanteController {
	
	@Autowired
	private ParticipanteService participanteService;
	
	@Autowired
	private GrupoService grupoService;
	
	@GetMapping("participante")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("participante/listar.html");
		mv.addObject("lista", participanteService.listarParticipantes());
		return mv;
	}
	
	@GetMapping("grupo/participante/novo")
	public ModelAndView novo(@RequestParam(required = false) Long id, RequisicaoFormParticipante requisicao) {
		ModelAndView mv = new ModelAndView("participante/form.html");
		mv.addObject("grupoId", id);
		return mv;
	}
	
	@PostMapping("grupo/participante/novo")
	public ModelAndView criar(@RequestParam(required = false) Long id, @Valid RequisicaoFormParticipante requisicao, BindingResult result,
											RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("participante/form");
			mv.addObject("grupoId", id);
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("redirect:/grupo/detalhes?id=" + id);
			Participante participante = requisicao.toParticipante();
			participante.setGrupo(grupoService.obterGrupo(id));
			participanteService.salvarParticipante(participante);
			mv.addObject("grupoId", id);
			
			redirectAttributes.addFlashAttribute("mensagem", "Participante #" + participante.getId()
												+ " criado com sucesso!");
			return mv;
		}
	}
	
	@GetMapping("participante/editar")
	public ModelAndView editar(@RequestParam(required = false) Long id, RequisicaoFormParticipante requisicao,
											RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("participante/edit.html");
		Participante participante;
		
		try {
			participante = participanteService.obterParticipante(id);
			requisicao.fromParticipante(participante);
			mv.addObject("participanteId", participante.getId());
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "Participante #" + id + " não encontrado!");
			mv.setViewName("redirect:/participante/novo");
		}
		
		return mv;
	}
	
	@PostMapping("participante/detalhes")
	public ModelAndView atualizar(@RequestParam(required = false) Long id,
									@Valid RequisicaoFormParticipante requisicao, BindingResult result,
									RedirectAttributes redirectAttributes) {
		ModelAndView mv;
		Participante participante;
		
		if (result.hasErrors()) {
			mv = new ModelAndView("participante/edit.html");
			mv.addObject("participanteId", id);
			return mv;
		} else {
			try {
				participante = participanteService.obterParticipante(id);
				requisicao.toParticipante(participante);
				participanteService.salvarParticipante(participante);
				
				redirectAttributes.addFlashAttribute("mensagem", "Atualizado com sucesso!");
				mv = new ModelAndView("redirect:/participante/detalhes?id=" + id);
				return mv;
			} catch (RuntimeException e) {
				mv = new ModelAndView("participante/edit.html");
				return mv;
			}
		}
	}
	
	@GetMapping("participante/detalhes")
	public ModelAndView detalhes(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("participante/show.html");
		Participante participante;
		
		try {
			participante = participanteService.obterParticipante(id);
		} catch (RuntimeException e) {
			participante = new Participante();
			mv.addObject("mensagem", "Participante #" + id + " não encontrado!");
		}
		
		mv.addObject("participante", participante);
		return mv;
	}
	
	
	@GetMapping("participante/deletar")
	public ModelAndView deletar(@RequestParam(required = false) Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/participante");
		
		try {
			participanteService.deleteParticipante(id);
			redirectAttributes.addFlashAttribute("mensagem", "Participante #"+id+" deletado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "ERRO: Participante #" + id + " não encontrado!");
		}
		return mv;
	}
	
}
