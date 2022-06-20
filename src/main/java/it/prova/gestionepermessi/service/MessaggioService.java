package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {
	
	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);

	public Messaggio cercaPerIdRichiesta(Long idRichiestaPermesso);

	public void rimuovi(Long idMessaggio);

	public List<Messaggio> listAllMessaggi();

	public Messaggio caricaSingoloMessaggio(Long idMessaggio);

	public List<Messaggio> findAllMessaggiNonLetti ();

	Page<Messaggio> findByExample(MessaggioDTO example, Integer pageNo, Integer pageSize, String sortBy);
	
}
