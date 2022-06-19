package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
//import it.prova.gestionepermessi.service.AttachmentService;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {

	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private MessaggioService messaggioService;
	
//	@Autowired
//	private AttachmentService attachmentService;
	
	@GetMapping("/listRichiestaPermesso")
	public ModelAndView listAllRichiestePermessi() {
		ModelAndView mv = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendenteSpecifico = dipendenteService.cercaPerUsername(auth.getName());
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.listAllRichiestePermessiPerIdDipendente(dipendenteSpecifico.getId());
		mv.addObject("richiestapermesso_dipendente_list_attribute",
				RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		mv.setViewName("dipendente/listRichiestePermessi");
		return mv;
	}

	@GetMapping("/insertRichiestaPermesso")
	public String insertRichiestaPermesso(Model model) {
		model.addAttribute("insert_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "dipendente/insertRichiestaPermesso";
	}

	@PostMapping("/saveRichiestaPermesso")
	public String save(@Valid @ModelAttribute("insert_richiestapermesso_attr") RichiestaPermessoDTO richiestaDTO,
			BindingResult result, RedirectAttributes redirectAttrs) {

//		if (result.hasErrors()) {
//			return "dipendente/insertRichiestaPermesso";
//		}
		RichiestaPermesso richiestaDaInserire = richiestaDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendenteInSessione = dipendenteService.cercaPerUsername(auth.getName());
		if (dipendenteInSessione == null) {
			throw new RuntimeException("Errore!");
		}

		richiestaDaInserire.setDipendente(dipendenteInSessione);
		richiestaPermessoService.inserisciNuovo(richiestaDaInserire, richiestaDTO.getGiornoSingolo(),
				richiestaDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente/listRichiestaPermesso";
	}

	@GetMapping("/showRichiestaPermesso/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingolaRichiestaPermesso(idRichiestaPermesso));
		return "dipendente/showRichiestaPermesso";
	}
	
	@GetMapping("/searchRichiestaPermesso")
	public String search(Model model) {
		model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());
		
		return "dipendente/searchRichiestaPermesso";
	}
	
	@PostMapping("/listForSearchRichiestaPermesso")
	public String list(RichiestaPermessoSearchDTO richiestaPermesso, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		
		Authentication  utenteInPagina= SecurityContextHolder.getContext().getAuthentication();
		Dipendente dipendente= dipendenteService.cercaPerUsername(utenteInPagina.getName());
		Long id= dipendente.getId();
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.findByExample(richiestaPermesso, pageNo, pageSize, sortBy).getContent().stream().filter(richiesta -> richiesta.getDipendente().getId() == id)
				.collect(Collectors.toList());
		model.addAttribute("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		return "dipendente/listRichiestePermessi";
	}
	
	@GetMapping("/deleteRichiestaPermesso/{idRichiestapermesso}")
	public String deleteRichiestaPermesso(@PathVariable(required = true) Long idRichiestapermesso, Model model) {
		model.addAttribute("delete_richiestapermesso_attr",
				richiestaPermessoService.caricaSingolaRichiestaPermesso(idRichiestapermesso));

		return "dipendente/deleteRichiestaPermesso";
	}

	@PostMapping("/removeRichiestaPermesso")
	public String remove(@RequestParam(required = true) Long idRichiestapermesso, RedirectAttributes redirectAttrs) {

		
		
		Messaggio messaggioItem = messaggioService.cercaPerIdRichiesta(idRichiestapermesso);

		if (messaggioItem != null) {
			messaggioService.rimuovi(messaggioItem.getId());
		}
		
//		RichiestaPermesso richiestaDaEliminare = richiestaPermessoService.caricaSingolaRichiestaPermessoEager(idRichiestapermesso);
//		
//		if(richiestaDaEliminare.getAttachment() != null) {
//		
//			attachmentService.rimuovi(richiestaDaEliminare.getAttachment().getId());
//		}

		richiestaPermessoService.rimuovi(idRichiestapermesso);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:listRichiestaPermesso";
	}
	
	@GetMapping("/editRichiestaPermesso/{idRichiestaPermesso}")
	public String edit(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		RichiestaPermesso richiestaPermessoModel= richiestaPermessoService.caricaSingolaRichiestaConAttachment(idRichiestaPermesso);
		model.addAttribute("edit_richiestapermesso_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoModel));
		return "dipendente/editRichiestaPermesso";
	}

	@PostMapping("/updateRichiestaPermesso")
	public String update(@Valid @ModelAttribute("edit_richiestapermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "dipendente/editRichiestaPermesso";
		}
		RichiestaPermesso richiestaPermesso=richiestaPermessoDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendente = dipendenteService.cercaPerUsername(auth.getName());
		if (dipendente == null) {
			throw new RuntimeException("Errore!");
		}

		richiestaPermesso.setDipendente(dipendente);
		
		richiestaPermessoService.aggiorna(richiestaPermesso.getId(), richiestaPermessoDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente/listRichiestePermessi";
	}

}
