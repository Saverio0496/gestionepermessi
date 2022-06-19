package it.prova.gestionepermessi.service;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {
	
	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);

	public Messaggio cercaPerIdRichiesta(Long idRichiestaPermesso);

	public void rimuovi(Long idMessaggio);


}
