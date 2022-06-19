package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	
	public List<RichiestaPermesso> listAllRichiestePermessi();
	
	public List<RichiestaPermesso> listAllRichiestePermessiPerIdDipendente(Long id);

	public RichiestaPermesso caricaSingolaRichiestaPermesso(Long id);

	public void aggiorna(RichiestaPermesso richiestaPermessoInstance);
	
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoUnico,  MultipartFile file);
	
	public Page<RichiestaPermesso> findByExample(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize, String sortBy);

	public void rimuovi(Long idRichiesta);

}
