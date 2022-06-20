package it.prova.gestionepermessi.repository.messaggio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {

	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta);

	public Messaggio save(MessaggioDTO messaggioInstance);
	
	@Query("select m from Messaggio m where m.letto = false or m.letto = null")
	public List<Messaggio> findAllMessaggiNonLetti();

	public Page<Messaggio> findAll(Specification<RichiestaPermesso> specificationCriteria, Pageable paging);
 
}
