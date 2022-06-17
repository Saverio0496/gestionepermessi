package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.service.DipendenteService;

@Controller
@RequestMapping(value = "/backoffice")
public class BackOfficeController {
	
	@Autowired
	private DipendenteService dipendenteService;

	@GetMapping("/searchDipendente")
	public String searchDipendente(Model model) {
		model.addAttribute("dipendente_list_attribute",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendenti()));
		return "backoffice/searchDipendente";
	}
	
	@PostMapping("/listForSearchDipendente")
	public String listDipendenti(DipendenteDTO dipendenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy, ModelMap model) {
		List<Dipendente> dipendenti = dipendenteService.findByExample(dipendenteExample.buildDipendenteModel(), pageNo, pageSize, sortBy).getContent();
		model.addAttribute("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "backoffice/listDipendenti";
	}
	
	@GetMapping("/listDipendente")
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllDipendenti();
		mv.addObject("dipendente_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		mv.setViewName("backoffice/listDipendenti");
		return mv;
	}
	
	@GetMapping("/showDipendente/{idDipendente}")
	public String showDipendente(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr", dipendenteService.caricaSingoloDipendente(idDipendente));
		return "backoffice/showDipendente";	
	}

}
