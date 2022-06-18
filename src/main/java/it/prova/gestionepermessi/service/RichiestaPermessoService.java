package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	
	public List<RichiestaPermesso> listAllRichiestePermessi();
	
	public List<RichiestaPermesso> listAllRichiestePermessiPerIdDipendente(Long id);

	public RichiestaPermesso caricaSingolaRichiestaPermesso(Long id);

	public void aggiorna(RichiestaPermesso richiestaPermessoInstance);
	
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance);
	
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize, String sortBy);

}
