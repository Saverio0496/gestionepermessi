package it.prova.gestionepermessi.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;
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
	private MessaggioService messaggioService;

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
		
		String arr[] = richiestaPermesso.getDipendenteNominativo().split(" ", 2);
		String nome = arr[0];  
		String cognome = arr[1]; 
		
		richiestaPermesso.setDipendente(dipendenteService.findByNomeECognome(nome, cognome));
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.findByExamplePerBO(richiestaPermesso, pageNo, pageSize, sortBy).getContent();
		model.addAttribute("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestePermessiListDTOFromModelList(richiestePermessi));
		return "backoffice/listRichiestePermessi";
	}
	
	@PostMapping("/approvaRichiesta")
	public String approvaRichiesta(
			@RequestParam(name = "idRichiestaForApprovaRichiesta", required = true) Long idRichiestapermesso, RedirectAttributes redirectAttrs) {

		richiestaPermessoService.approvaRichiesta(idRichiestapermesso);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/backoffice/listRichiestePermesso";
	}
	
	@GetMapping("/showRichiestaPermesso/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingolaRichiestaPermessoEager(idRichiestaPermesso));
		return "backoffice/showRichiestaPermesso";
	}
	
	@GetMapping("/listMessaggio")
	public ModelAndView listAllMessaggi() {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> dipendenti = messaggioService.listAllMessaggi();
		mv.addObject("messaggio_list_attribute", MessaggioDTO.buildMessaggioDTOFromModelList(dipendenti));
		mv.setViewName("backoffice/listMessaggi");
		return mv;
	}
	
	@GetMapping("/showMessaggio/{idMessaggio}")
	public String showUtente(@PathVariable(required = true) Long idMessaggio, Model model) {
		MessaggioDTO messaggioDTO = MessaggioDTO.buildMessaggioDTOFromModel(messaggioService.caricaSingoloMessaggio(idMessaggio));
		messaggioDTO.setDataLettura(new Date());
		messaggioDTO.setLetto(true);
		model.addAttribute("show_messaggio_attr", messaggioDTO);
		return "backoffice/showMessaggio";
	}
	
	@GetMapping(value = "/searchDipendentiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchDipendenti(@RequestParam String term) {

		List<Dipendente> listaDipendentByTerm = dipendenteService.cercaByCognomeENomeILike(term);
		return buildJsonResponse(listaDipendentByTerm);
	}

	private String buildJsonResponse(List<Dipendente> listaDipendenti) {
		JsonArray ja = new JsonArray();

		for (Dipendente dipendenteItem : listaDipendenti) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", dipendenteItem.getId());
			jo.addProperty("label", dipendenteItem.getNome() + " " + dipendenteItem.getCognome());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}
	
	@GetMapping(value = "/presentiMessaggiNonLetti", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> checkPresenzaMessaggiNonLetti() {

		if (!messaggioService.findAllMessaggiNonLetti().isEmpty())
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/searchMessaggio")
	public String searchMessaggio(Model model) {
		model.addAttribute("messaggio_list_attribute", MessaggioDTO.buildMessaggioDTOFromModelList(messaggioService.listAllMessaggi()));
		return "backoffice/searchMessaggio";
	}

	@PostMapping("/listForSearchMessaggio")
	public String listMessaggi(MessaggioDTO messaggioExample , @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,ModelMap model) {

		List<Messaggio> messaggi = messaggioService.findByExample(messaggioExample, pageNo, pageSize, sortBy).getContent();
		model.addAttribute("messaggio_list_attribute", MessaggioDTO.buildMessaggioDTOFromModelList(messaggi));
		return "backoffice/listMessaggi";
	}
	
}
