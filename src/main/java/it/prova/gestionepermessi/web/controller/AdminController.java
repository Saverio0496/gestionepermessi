package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.dto.UtenteSearchDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		mv.setViewName("admin/listUtenti");
		return mv;
	}

	@GetMapping("/showUtente/{idUtente}")
	public String showUtente(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtenteEager(idUtente));
		return "admin/showUtente";
	}

	@GetMapping("/searchUtente")
	public String searchUtente(Model model) {
		model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
		return "admin/searchUtente";
	}

	@PostMapping("/listUtente")
	public String listUtenti(UtenteSearchDTO utenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Utente> utenti = utenteService.findByExample(utenteExample, pageNo, pageSize, sortBy).getContent();

		model.addAttribute("utente_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		return "admin/listUtenti";
	}

	@GetMapping("/editUtente/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloUtenteConRuoli(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
		return "admin/editUtente";
	}

	@PostMapping("/updateUtente")
	public String update(@ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO, BindingResult result, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
			return "admin/editUtente";
		}
		utenteService.aggiornaPerAdmin(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/admin";
	}
	
	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/admin";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam(name = "idUtenteForResetpassword", required = true) Long idUtente, RedirectAttributes redirectAttr) {
		utenteService.resetPasswordService(idUtente);
		
		redirectAttr.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/admin";
	}

}
