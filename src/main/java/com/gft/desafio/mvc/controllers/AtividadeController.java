package com.gft.desafio.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.desafio.mvc.dto.RequisicaoFormAtividade;
import com.gft.desafio.mvc.entities.Atividade;
import com.gft.desafio.mvc.entities.Evento;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.entities.Participante;
import com.gft.desafio.mvc.services.AtividadeService;
import com.gft.desafio.mvc.services.EventoService;
import com.gft.desafio.mvc.services.GrupoService;

@Controller
public class AtividadeController {
	
	@Autowired
	private AtividadeService atividadeService;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private GrupoService grupoService;
	
	@GetMapping("atividade")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("atividade/listar.html");
		mv.addObject("lista", atividadeService.listar());
		return mv;
	}
	
	@GetMapping("evento/atividade/novo")
	public ModelAndView novo(@RequestParam(required = false) Long id, RequisicaoFormAtividade requisicao) {
		ModelAndView mv = new ModelAndView("atividade/form.html");
		mv.addObject("eventoId", id);
		return mv;
	}
	
	@PostMapping("evento/atividade/novo")
	public ModelAndView criar(@RequestParam(required = false) Long id, @Valid RequisicaoFormAtividade requisicao, BindingResult result,
												RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("atividade/form.html");
			mv.addObject("eventoId", id);
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("redirect:/evento/detalhes?id=" + id);
			Atividade atividade = requisicao.toAtividade();
			atividade.setEvento(eventoService.obterEvento(id));
			atividadeService.salvar(atividade);
			
			mv.addObject("eventoId", id);
			
			redirectAttributes.addFlashAttribute("mensagem", "Atividade #" + atividade.getId() + " criado com sucesso!");
			return mv;
		}
	}
	
	@GetMapping("atividade/editar")
	public ModelAndView editar(@RequestParam(required = false) Long id, RequisicaoFormAtividade requisicao, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("atividade/edit.html");
		Atividade atividade;
		
		try {
			atividade = atividadeService.obterAtividade(id);
			requisicao.fromAtividade(atividade);
			mv.addObject("atividadeId", atividade.getId());
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "Atividade #" + id + " não encontrado!");
			mv.setViewName("redirect:/atividade/novo");
		}
		return mv;
	}
	
	@GetMapping("atividade/gerenciar")
	public ModelAndView gerenciar(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("atividade/gerenciar.html");
		Evento evento;
		
		try {
			mv.addObject("atividade", atividadeService.obterAtividade(id));
			evento = eventoService.obterEvento(atividadeService.obterAtividade(id).getEvento().getId());
		} catch (RuntimeException e) {
			evento = new Evento();
			mv.addObject("mensagem", "Atividade #" + id + " não encontrada!");
			return mv;
		}
		
		List<Grupo> listaGrupos = evento.getGrupos();
		List<Participante> listaParticipantes = new ArrayList<>();
		
		for (int i = 0; i < listaGrupos.size(); i++) {
			listaParticipantes.addAll(listaGrupos.get(i).getParticipantes());
		}
		
		mv.addObject("listaParticipantes", listaParticipantes);
		return mv;
	}
	
	@PostMapping("atividade/entregaram")
	public ModelAndView atualizarEntregaram(@RequestParam(required = false) Long id, Atividade atividade) {
		ModelAndView mv = new ModelAndView("redirect:/atividade/detalhes?id=" + id);
		
		Atividade atividadeSalvar = atividadeService.obterAtividade(id);
		atividadeSalvar.setEntregaram(atividade.getEntregaram());
		atividadeService.salvar(atividadeSalvar);
		
		for (Grupo g : atividadeSalvar.getEvento().getGrupos()) {
			grupoService.atualizarPontuacao(g);
			grupoService.salvar(g);
		}
		return mv;
	}
	
	@PostMapping("atividade/detalhes")
	public ModelAndView atualizar(@RequestParam(required = false) Long id, @Valid RequisicaoFormAtividade requisicao,
									BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv;
		Atividade atividade;
		
		if (result.hasErrors()) {
			mv = new ModelAndView("atividade/edit.html");
			mv.addObject("atividadeId", id);
			return mv;
		} else {
			try {
				atividade = atividadeService.obterAtividade(id);
				requisicao.toAtividade(atividade);
				atividadeService.salvar(atividade);
				
				redirectAttributes.addFlashAttribute("mensagem", "Atualizado com sucesso!");
				mv = new ModelAndView("redirect:/atividade/detalhes?id=" + id);
				return mv;
			} catch (RuntimeException e) {
				mv = new ModelAndView("atividade/edit.html");
				return mv;
			}
		}
	}
	
	@GetMapping("atividade/detalhes")
	public ModelAndView detalhes(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("atividade/show.html");
		Atividade atividade;
		Evento evento;
		
		try {
			atividade = atividadeService.obterAtividade(id);
			evento = eventoService.obterEvento(atividade.getEvento().getId());
		} catch (RuntimeException e) {
			atividade = new Atividade();
			evento = new Evento();
			mv.addObject("mensagem", "Atividade #" + id + " não encontrada!");
		}
		
		List<Grupo> listaGrupos = evento.getGrupos();
		List<Participante> listaParticipantes = new ArrayList<>();
		
		for (int i = 0; i < listaGrupos.size(); i++) {
			listaParticipantes.addAll(listaGrupos.get(i).getParticipantes());
		}
		
		mv.addObject("listaParticipantes", listaParticipantes);
		mv.addObject("atividade", atividade);
		
		return mv;
	}
	
	@GetMapping("atividade/deletar")
	public ModelAndView deletar(@RequestParam(required = false) Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/atividade");
		
		try {
			atividadeService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Atividade #" + id + " deletada com sucesso!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("mensagem", "ERRO: Atividade #" + id +" não encontrada!");
		}
		return mv;
	}
}
