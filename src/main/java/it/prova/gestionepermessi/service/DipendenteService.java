package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendenti() ;

	public Dipendente caricaSingoloDipendente(Long id);

	public void aggiorna(Dipendente dipendenteInstance);

	public void inserisciNuovoConUtente(Dipendente dipendenteInstance, Utente utenteInstance);

	public void rimuovi(Dipendente dipendenteInstance);

}
