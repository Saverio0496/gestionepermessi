package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		mv.setViewName("admin/listUtenti");
		return mv;
	}

}
