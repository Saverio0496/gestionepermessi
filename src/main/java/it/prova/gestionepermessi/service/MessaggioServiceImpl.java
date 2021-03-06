package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {
	
	@Autowired
	private MessaggioRepository messaggioRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Messaggio> listAllMessaggi() {
		return (List<Messaggio>) messaggioRepository.findAll();
	}

	@Override
	@Transactional
	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance) {
		String note = richiestaInstance.getNote().isBlank() ? "" : " , le note del dipendente " + richiestaInstance.getNote();
		String codiceCertificato = richiestaInstance.getCodiceCertificato().isBlank() ? ""
				: " , il Codice del Certificato: " + richiestaInstance.getCodiceCertificato();
		String attachment = richiestaInstance.getAttachment() == null ? "" : " , il file allegato";
		String parteFinaleMessaggio = ".";

		if (!note.isBlank() || !codiceCertificato.isBlank() || !attachment.isBlank()) {
			parteFinaleMessaggio += " In allegato :";
			parteFinaleMessaggio += note.isBlank() ? "" : " " + note;
			parteFinaleMessaggio += codiceCertificato.isBlank() ? "" : " " + codiceCertificato;
			parteFinaleMessaggio += attachment.isBlank() ? "" : " " + attachment;
			parteFinaleMessaggio += ".";
		}

		messaggioInstance.setOggetto("Richiesta Permesso di " + richiestaInstance.getDipendente().getNome() + " "
				+ richiestaInstance.getDipendente().getCognome());

		messaggioInstance.setTesto(
				"Il dipendente " + richiestaInstance.getDipendente().getNome() + " " + richiestaInstance.getDipendente().getCognome()
						+ " ha richiesto un permesso per " + richiestaInstance.getTipoPermesso() + " a partire dal giorno "
						+ richiestaInstance.getDataInizio() + " al giorno " + richiestaInstance.getDataFine() + parteFinaleMessaggio);

		messaggioInstance.setDataInserimento(new Date());
		messaggioInstance.setLetto(false);
		messaggioInstance.setRichiestaPermesso(richiestaInstance);

		messaggioRepository.save(messaggioInstance);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Messaggio cercaPerIdRichiesta(Long idRichiesta) {
		return messaggioRepository.findByRichiestaPermesso_Id(idRichiesta);
	}
	
	@Override
	@Transactional
	public void rimuovi(Long idMessaggio) {
		messaggioRepository.deleteById(idMessaggio);
	}

	@Override
	public Messaggio caricaSingoloMessaggio(Long idMessaggio) {
		return messaggioRepository.findById(idMessaggio).orElse(null);
	}

	public List<Messaggio> findAllMessaggiNonLetti() {
		return messaggioRepository.findAllMessaggiNonLetti();
	}
	
	@Override
	@Transactional
	public Page<Messaggio> findByExample(MessaggioDTO example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(StringUtils.isNotEmpty(example.getTesto()))
				predicates.add(cb.like(cb.upper(root.get("testo")), "%"+ example.getTesto().toUpperCase()+"%" ));
			
			if (example.getDataInserimento() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInserimento"), example.getDataInserimento()));
			
			if (example.getDataLettura() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLettura"), example.getDataLettura()));
			
			if(StringUtils.isNotEmpty(example.getOggetto()))
				predicates.add(cb.like(cb.upper(root.get("oggetto")), "%"+ example.getOggetto().toUpperCase()+"%" ));
			
			
			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return messaggioRepository.findAll(specificationCriteria, paging);
	}

}
