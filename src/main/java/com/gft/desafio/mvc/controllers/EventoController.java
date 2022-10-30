package com.gft.desafio.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.gft.desafio.mvc.dto.RequisicaoFormEntregaram;
import com.gft.desafio.mvc.dto.RequisicaoFormEvento;
import com.gft.desafio.mvc.entities.Atividade;
import com.gft.desafio.mvc.entities.Evento;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.services.AtividadeService;
import com.gft.desafio.mvc.services.EventoService;
import com.gft.desafio.mvc.services.GrupoService;

@Controller
@RequestMapping("evento")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private AtividadeService atividadeService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("evento/listar.html");
		mv.addObject("lista", eventoService.listar());
		return mv;
	}
	
	@GetMapping("grupos")
	public ModelAndView listarGrupos(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("evento/grupos.html");
		Evento evento;
		
		try {
			evento = eventoService.obterEvento(id);
			if (evento.getGrupos().isEmpty()) {
				mv.addObject("mensagem", "Não há grupos no evento!");
			}
			mv.addObject("evento", evento);
			mv.addObject("listaGrupos", evento.getGrupos());
			return mv;
		} catch (RuntimeException e) {
			evento = new Evento();
			mv.addObject("evento", evento);
			mv.addObject("mensagem", "Evento #" + id + " não encontrado!");
			return mv;
		}
		
	}
	
	@GetMapping("atividades")
	public ModelAndView listarAtividades(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("evento/atividades.html");
		Evento evento;
		
		try {
			evento = eventoService.obterEvento(id);
			if (evento.getAtividades().isEmpty()) {
				mv.addObject("mensagem", "Não há atividades no evento!");
			}
			mv.addObject("evento", evento);
			mv.addObject("listaAtividades", evento.getAtividades());
			return mv;
		} catch (RuntimeException e) {
			evento = new Evento();
			mv.addObject("evento", evento);
			mv.addObject("mensagem", "Evento #" + id + " não encontrado!");
			return mv;
		}
		
	}
	
	@GetMapping("novo")
	public ModelAndView novo(RequisicaoFormEvento requisicao) {
		ModelAndView mv = new ModelAndView("evento/form.html");
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView criar(@Valid RequisicaoFormEvento requisicao, BindingResult result,
									RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("evento/form.html");
			return mv;
		} else {
			
			ModelAndView mv = new ModelAndView("redirect:/evento");
			Evento evento = requisicao.toEvento();
			eventoService.salvar(evento);
			
			redirectAttributes.addFlashAttribute("mensagem", "Evento #" + evento.getId() + " criado com sucesso!");
			return mv;
		}
	}
	
	@GetMapping("editar")
	public ModelAndView editar(@RequestParam(required = false) Long id, RequisicaoFormEvento requisicao,
											RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("evento/edit.html");
		Evento evento;
		
		try {
			evento = eventoService.obterEvento(id);
			requisicao.fromEvento(evento);
			mv.addObject("eventoId", evento.getId());
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "Evento #" + id + " não encontrado!");
			mv.setViewName("redirect:/evento/novo");
		}
		
		return mv;
	}
	
	@GetMapping("detalhes")
	public ModelAndView detalhes(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("evento/show.html");
		Evento evento;
		
		try {
			evento = eventoService.obterEvento(id);
		} catch (RuntimeException e) {
			evento = new Evento();
			mv.addObject("mensagem", "Evento #" + id + " não encontrado!");
		}
		
		List<Grupo> listaGrupos = evento.getGrupos();
		List<Participante> listaParticipantes = new ArrayList<>();
		
		for (int i = 0; i < listaGrupos.size(); i++) {
			listaParticipantes.addAll(listaGrupos.get(i).getParticipantes());
		}
		
		eventoService.ordenarGruposPorRanking(listaGrupos);
		
		mv.addObject("grupoVencendo", listaGrupos.get(0));
		mv.addObject("listaParticipantes", listaParticipantes);
		mv.addObject("listaAtividades", evento.getAtividades());
		mv.addObject("evento", evento);
		return mv;
	}
	
	@PostMapping("detalhes")
	public ModelAndView atualizar(@RequestParam(required = false) Long id, @Valid RequisicaoFormEvento requisicao,
											BindingResult result, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv;
		Evento evento;
		
		if (result.hasErrors()) {
			mv = new ModelAndView("evento/edit.html");
			mv.addObject("eventoId", id);
			return mv;
		} else {
			try {
				evento = eventoService.obterEvento(id);
				requisicao.toEvento(evento);
				eventoService.salvar(evento);
				
				redirectAttributes.addFlashAttribute("mensagem", "Evento atualizado com sucesso!");
				mv = new ModelAndView("redirect:/evento/detalhes?id=" + id);
				return mv;
			} catch (RuntimeException e) {
				mv = new ModelAndView("evento/edit.html");
				return mv;
			}
		}
	}
	
	@GetMapping("deletar")
	public ModelAndView deletar(@RequestParam(required = false) Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/evento");
		
		try {
			eventoService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Evento #" + id + " deletado com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "ERRO: Evento #" + id + " não encontrado!");
		}
		return mv;
	}
}
