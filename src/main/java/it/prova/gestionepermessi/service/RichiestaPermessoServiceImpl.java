package it.prova.gestionepermessi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.JoinType;
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
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.attachment.AttachmentRepository;
import it.prova.gestionepermessi.repository.richiestapermesso.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {

	@Autowired
	private RichiestaPermessoRepository richiestaPermessoRepository;

	@Autowired
	private MessaggioService messaggioService;

	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllRichiestePermessi() {
		return (List<RichiestaPermesso>) richiestaPermessoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllRichiestePermessiPerIdDipendente(Long id) {
		return richiestaPermessoRepository.findAllByDipendente_Id(id);
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingolaRichiestaPermesso(Long id) {
		return richiestaPermessoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Long idRichiestapermesso, MultipartFile file) {
		RichiestaPermesso richiestaPermessoReloaded= richiestaPermessoRepository.findByAttachment_id(idRichiestapermesso);
		if(file!=null) {
			Attachment attachment= new Attachment();
			attachment.setNomeFile(file.getOriginalFilename());
			attachment.setContentType(file.getContentType());
			try {
				attachment.setPayload(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			attachmentRepository.save(attachment);
			richiestaPermessoReloaded.setAttachment( attachment);
			richiestaPermessoRepository.save(richiestaPermessoReloaded);
			return;
		}
		richiestaPermessoRepository.save(richiestaPermessoReloaded);
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoSingolo, MultipartFile file) {
		if (giornoSingolo) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(richiestaPermessoInstance.getDataInizio());
			calendar.add(Calendar.HOUR, 24);
			richiestaPermessoInstance.setDataFine(calendar.getTime());
		}
		if (file != null) {
			Attachment newfile = new Attachment();
			newfile.setNomeFile(file.getOriginalFilename());
			newfile.setContentType(file.getContentType());
			try {
				newfile.setPayload(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			richiestaPermessoInstance.setAttachment(newfile);
			attachmentRepository.save(newfile);
			richiestaPermessoRepository.save(richiestaPermessoInstance);
			messaggioService.inserisciNuovo(new Messaggio(), richiestaPermessoInstance);
			return;
		}
		richiestaPermessoRepository.save(richiestaPermessoInstance);
		messaggioService.inserisciNuovo(new Messaggio(), richiestaPermessoInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RichiestaPermesso> findByExample(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			
			root.fetch("dipendente", JoinType.INNER);
			
			if(StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")), "%"+ example.getCodiceCertificato().toUpperCase()+"%" ));
			
			if (example.getDataInizio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInizio"), example.getDataInizio()));
			
			if (example.getDataFine() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataFine"), example.getDataFine()));
			
			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(root.get("tipoPermesso"), example.getTipoPermesso()));
		
			if (example.getDipendente() != null && example.getDipendente().getId() != null) {
				predicates.add(
						cb.equal(root.join("dipendente").get("id"), example.getDipendente().getId()));
			}
			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return richiestaPermessoRepository.findAll(specificationCriteria, paging);
	}
	
	@Override
	@Transactional
	public void rimuovi(Long idRichiesta) {
		richiestaPermessoRepository.deleteById(idRichiesta);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingolaRichiestaPermessoEager(Long id) {
		return richiestaPermessoRepository.findByIdEager(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingolaRichiestaConAttachment(Long id) {
		return richiestaPermessoRepository.findByAttachment_id(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<RichiestaPermesso> findByExamplePerBO(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			
			root.fetch("dipendente", JoinType.INNER);
			root.fetch("attachment", JoinType.INNER);
			
			if(StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")), "%"+ example.getCodiceCertificato().toUpperCase()+"%" ));
			
			if (example.getDataInizio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInizio"), example.getDataInizio()));
			
			if (example.getDataFine() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataFine"), example.getDataFine()));
			
			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(root.get("tipoPermesso"), example.getTipoPermesso()));
		
			if (example.getDipendente() != null && example.getDipendente().getId() != null) {
				predicates.add(
						cb.equal(root.join("dipendente").get("id"), example.getDipendente().getId()));
			}
			
			if(example.getAttachment()!= null) {
				predicates.add(cb.equal(root.join("attachment"), example.getAttachment()));
			}
			
			query.distinct(true);
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return richiestaPermessoRepository.findAll(specificationCriteria, paging);
	}
	
	@Override
	@Transactional
	public void aggiorna(RichiestaPermesso richiestaModel) {
		richiestaPermessoRepository.save(richiestaModel);
	}
	
	@Override
	@Transactional
	public void approvaRichiesta(Long idRichiestaPermesso) {
		RichiestaPermesso richiestaInstance = caricaSingolaRichiestaPermesso(idRichiestaPermesso);

		if (richiestaInstance.isApprovato() == false) {
			richiestaInstance.setApprovato(true);
		}
		else if (richiestaInstance.isApprovato() == true) {
			richiestaInstance.setApprovato(false);
		}

	}
}
