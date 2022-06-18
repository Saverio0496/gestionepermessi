package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {
	
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	@GetMapping("/listRichiestaPermesso")
	public ModelAndView listAllRichiestePermessi() {
		ModelAndView mv = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendenteSpecifico = dipendenteService.cercaPerUsername(auth.getName());
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.listAllRichiestePermessiPerIdDipendente(dipendenteSpecifico.getId());
		mv.addObject("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		mv.setViewName("dipendente/listRichiestePermessi");
		return mv;
	}

}
