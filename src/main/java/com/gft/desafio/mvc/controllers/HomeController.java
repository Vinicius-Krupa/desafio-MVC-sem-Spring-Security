package com.gft.desafio.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gft.desafio.mvc.entities.Evento;
import com.gft.desafio.mvc.entities.Grupo;
import com.gft.desafio.mvc.services.EventoService;

@Controller
public class HomeController {
	
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("/")
	public ModelAndView home() {
		
		return new ModelAndView("layout.html");
	}
	
	@GetMapping("ranking")
	public ModelAndView loginERaning() {
		ModelAndView mv = new ModelAndView("ranking.html");
		
		List<Evento> eventos = eventoService.listar();
		List<Grupo> grupos = new ArrayList<>();
		
		for (Evento evento : eventos) {
			eventoService.ordenarGruposPorRanking(evento.getGrupos());
			grupos.addAll(evento.getGrupos());
		}
		
		mv.addObject("listaEventos", eventos);
		
		return mv;
	}
}
