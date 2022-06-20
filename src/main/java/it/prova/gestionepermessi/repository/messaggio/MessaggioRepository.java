package it.prova.gestionepermessi.repository.messaggio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {

	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta);

	public Messaggio save(MessaggioDTO messaggioInstance);
	
	@Query("select m from Messaggio m where m.letto = false or m.letto = null")
	public List<Messaggio> findAllMessaggiNonLetti();
 
}
