package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;
import it.prova.gestionepermessi.repository.ruolo.RuoloRepository;
import it.prova.gestionepermessi.repository.utente.UtenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private RuoloRepository ruoloRepository;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<Dipendente> listAllDipendenti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	@Transactional
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		Dipendente dipendenteReloaded = dipendenteRepository.findByIdConUtente(dipendenteInstance.getId()).orElse(null);
		if (dipendenteReloaded == null || dipendenteReloaded.getUtente() == null) {
			throw new RuntimeException("Elemento non trovato.");
		}
		
		dipendenteInstance.setUtente(dipendenteReloaded.getUtente());
		dipendenteInstance.getUtente().setUsername(dipendenteInstance.getNome().toLowerCase().charAt(0) + "." + dipendenteInstance.getCognome().toLowerCase());
		dipendenteInstance.setEmail(dipendenteInstance.getUtente().getUsername() + "@prova.it");
		
		utenteRepository.save(dipendenteInstance.getUtente());
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

	@Override
	@Transactional
	public void inserisciNuovoConUtente(Dipendente dipendenteInstance, Utente utenteInstance) {
		utenteInstance.getRuoli()
				.add(ruoloRepository.findByDescrizioneAndCodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setUsername(dipendenteInstance.getNome().toLowerCase().charAt(0) + "."
				+ dipendenteInstance.getCognome().toLowerCase());
		utenteInstance.setDateCreated(new Date());
		dipendenteInstance.setEmail(utenteInstance.getUsername() + "@prova.it");
		dipendenteInstance.setUtente(utenteInstance);
		utenteService.inserisciNuovoConDipendente(utenteInstance, dipendenteInstance);
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodFis()))
				predicates.add(cb.like(cb.upper(root.get("codFis")), "%" + example.getCodFis().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getEmail()))
				predicates.add(cb.like(cb.upper(root.get("email")), "%" + example.getEmail().toUpperCase() + "%"));

			if (example.getSesso() != null)
				predicates.add(cb.equal(root.get("sesso"), example.getSesso()));

			if (example.getDataNascita() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataNascita"), example.getDataNascita()));

			if (example.getDataAssunzione() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataAssunzione"), example.getDataAssunzione()));

			if (example.getDataDimissioni() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataDimissioni"), example.getDataDimissioni()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return dipendenteRepository.findAll(specificationCriteria, paging);
	}

	@Override
	@Transactional
	public Dipendente caricaSingoloDipendenteConUtente(Long id) {
		return dipendenteRepository.findByIdConUtente(id).orElse(null);
	}

}
