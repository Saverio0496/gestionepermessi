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

	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoUnico, MultipartFile file);

	public Page<RichiestaPermesso> findByExample(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize,
			String sortBy);

	public void rimuovi(Long idRichiesta);

	public RichiestaPermesso caricaSingolaRichiestaPermessoEager(Long id);

	public RichiestaPermesso caricaSingolaRichiestaConAttachment(Long idRichiestaPermesso);

	public void aggiorna(Long idRichiestapermesso, MultipartFile file);

	Page<RichiestaPermesso> findByExamplePerBO(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize,
			String sortBy);

	public void aggiorna(RichiestaPermesso richiestaModel);

	void approvaRichiesta(Long idRichiestaPermesso);

}
