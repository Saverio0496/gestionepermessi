package it.prova.gestionepermessi.repository.dipendente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long> {

	Page<Dipendente> findAll(Specification<Utente> specificationCriteria, Pageable paging);

	@Query("select d from Dipendente d join fetch d.utente where d.id = ?1")
	Optional<Dipendente> findByIdConUtente(Long id);

	@Query("select d from Dipendente d join fetch d.utente u where u.username = ?1")
	Optional<Dipendente> findByUsername(String username);

	List<Dipendente> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome,
			String nome);
	
	Optional<Dipendente> findByNomeAndCognome(String nome, String cognome);
}
