package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendenti() ;

	public Dipendente caricaSingoloDipendente(Long id);

	public void aggiorna(Dipendente dipendenteInstance);
	
	public void inserisciNuovoConUtente(Dipendente dipendenteInstance, Utente utenteInstance);

	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy);

}
