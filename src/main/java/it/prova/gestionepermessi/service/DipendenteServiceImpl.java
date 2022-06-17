package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;


@Service
public class DipendenteServiceImpl implements DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Dipendente> listAllDipendenti() {
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
	
	@Transactional
	public List<Dipendente> findByExample(Dipendente example) {
	Map<String, Object> paramaterMap = new HashMap<String, Object>();
	List<String> whereClauses = new ArrayList<String>();

	StringBuilder queryBuilder = new StringBuilder("select d from Dipendente d where d.id = d.id ");

	if (StringUtils.isNotEmpty(example.getNome())) {
	whereClauses.add(" d.nome  like :nome ");
	paramaterMap.put("nome", "%" + example.getNome() + "%");
	}
	if (StringUtils.isNotEmpty(example.getCognome())) {
	whereClauses.add(" d.cognome like :cognome ");
	paramaterMap.put("cognome", "%" + example.getCognome() + "%");
	}
	if (StringUtils.isNotEmpty(example.getEmail())) {
	whereClauses.add(" d.email like :email ");
	paramaterMap.put("email", "%" + example.getEmail() + "%");
	}
	if (example.getDataNascita() != null) {
	whereClauses.add("d.dataNascita >= :dataNascita ");
	paramaterMap.put("dataNascita", example.getDataNascita());
	}
	if (example.getDataAssunzione() != null) {
	whereClauses.add("d.dataAssunzione >= :dataAssunzione ");
	paramaterMap.put("dataAssunzione", example.getDataAssunzione());
	}
	if (example.getDataDimissioni() != null) {
	whereClauses.add("d.dataDimissioni >= :dataDimissioni ");
	paramaterMap.put("dataDimissioni", example.getDataDimissioni());
	}
	if (example.getSesso() != null) {
	whereClauses.add(" d.sesso =:sesso ");
	paramaterMap.put("sesso", example.getSesso());
	}

	queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
	queryBuilder.append(StringUtils.join(whereClauses, " and "));
	TypedQuery<Dipendente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Dipendente.class);

	for (String key : paramaterMap.keySet()) {
	typedQuery.setParameter(key, paramaterMap.get(key));
	}

	return typedQuery.getResultList();
	}

}
