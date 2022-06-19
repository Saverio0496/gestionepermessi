package it.prova.gestionepermessi.web.controller;

import java.util.List;

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

import it.prova.gestionepermessi.dto.AttachmentDTO;
import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.AttachmentService;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;
import it.prova.gestionepermessi.validator.DipendenteValidator;

@Controller
@RequestMapping(value = "/backoffice")
public class BackOfficeController {

	@Autowired
	private DipendenteValidator dipendenteValidator;

	@Autowired
	private DipendenteService dipendenteService;
	
	@Autowired
	private RichiestaPermessoService richiestaPermessoService;
	
	@Autowired
	private AttachmentService attachmentService;

	@GetMapping("/searchDipendente")
	public String searchDipendente(Model model) {
		model.addAttribute("dipendente_list_attribute",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendenti()));
		return "backoffice/searchDipendente";
	}

	@PostMapping("/listForSearchDipendente")
	public String listDipendenti(DipendenteDTO dipendenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		List<Dipendente> dipendenti = dipendenteService
				.findByExample(dipendenteExample.buildDipendenteModel(), pageNo, pageSize, sortBy).getContent();
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

	@GetMapping("/insertDipendente")
	public String insertDipendente(Model model) {
		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());
		return "backoffice/insertDipendente";
	}

	@PostMapping("/saveDipendente")
	public String save(@Valid @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO,
			BindingResult result, RedirectAttributes redirectAttrs) {
		dipendenteValidator.validate(dipendenteDTO.buildDipendenteModel(), result);

		if (result.hasErrors()) {
			return "backoffice/insertDipendente";
		}

		Dipendente dipendente = dipendenteDTO.buildDipendenteModel();
		Utente utente = new Utente();
		utente.setDipendente(dipendente);

		dipendenteService.inserisciNuovoConUtente(dipendente, utente);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/backoffice/listDipendente";
	}

	@GetMapping("/editDipendente/{idDipendente}")
	public String edit(@PathVariable(required = true) Long idDipendente, Model model) {
		Dipendente dipendenteModel = dipendenteService.caricaSingoloDipendente(idDipendente);
		model.addAttribute("edit_dipendente_attr", DipendenteDTO.buildDipendenteDTOFromModel(dipendenteModel));
		return "backoffice/editDipendente";
	}

	@PostMapping("/updateDipendente")
	public String update(@Valid DipendenteDTO dipendenteDTO, BindingResult result, Model model, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return "backoffice/editDipendente";
		}
		dipendenteService.aggiorna(dipendenteDTO.buildDipendenteModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/backoffice/listDipendente";
	}
	
	@GetMapping("/listRichiestePermesso")
	public ModelAndView listRichiestePermesso() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> dipendenti = richiestaPermessoService.listAllRichiestePermessi();
		mv.addObject("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(dipendenti));
		mv.setViewName("backoffice/listRichiestePermessi");
		return mv;
	}
	
	@GetMapping("/searchRichiestaPermesso")
	public String searchRichieste(ModelMap model) {
		model.addAttribute("search_richiestapermesso_dipendente_attr",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllDipendenti()));
		model.addAttribute("search_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "backoffice/searchRichiestaPermesso";
	}
	
	@PostMapping("/listForSearchRichiestaPermesso")
	public String list(RichiestaPermessoSearchDTO richiestaPermesso, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {
		
		Authentication  utenteInPagina= SecurityContextHolder.getContext().getAuthentication();
		Dipendente dipendente= dipendenteService.cercaPerUsername(utenteInPagina.getName());
		richiestaPermesso.setDipendente(dipendente);
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.findByExamplePerBO(richiestaPermesso, pageNo, pageSize, sortBy).getContent();
		model.addAttribute("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		return "backoffice/listRichiestePermessi";
	}
	
	@GetMapping("/approva/{idRichiesta}")
	public String cambiaStato(@PathVariable(required = true) Long idRichiesta, Model model) {
		RichiestaPermesso richiestaModel = richiestaPermessoService.caricaSingolaRichiestaPermessoEager(idRichiesta);

		richiestaModel.setApprovato(!richiestaModel.isApprovato());
		richiestaPermessoService.aggiorna(richiestaModel);

		return "backoffice/listRichiestePermessi";
	}
	
	@GetMapping("/showRichiestaPermesso/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingolaRichiestaPermessoEager(idRichiestaPermesso));
		return "backoffice/showRichiestaPermesso";
	}
	
	
}
