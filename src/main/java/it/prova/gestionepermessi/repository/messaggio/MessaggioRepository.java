package it.prova.gestionepermessi.repository.messaggio;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {

	public Messaggio findByRichiestaPermesso_Id(Long idRichiesta);

	public Messaggio save(MessaggioDTO messaggioInstance);

}
