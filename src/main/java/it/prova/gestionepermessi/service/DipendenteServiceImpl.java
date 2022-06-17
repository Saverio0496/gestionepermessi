package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;


@Service
public class DipendenteServiceImpl implements DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Override
	public List<Dipendente> listAllUtenti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Dipendente dipendenteInstance) {
		Dipendente dipendenteReloaded = dipendenteRepository.findById(dipendenteInstance.getId()).orElse(null);
		if (dipendenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		// da aggiornare
		dipendenteRepository.save(dipendenteReloaded);
	}


	@Override
	public void rimuovi(Dipendente dipendenteInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciNuovoConUtente(Dipendente dipendenteInstance, Utente utenteInstance) {
	}

}
