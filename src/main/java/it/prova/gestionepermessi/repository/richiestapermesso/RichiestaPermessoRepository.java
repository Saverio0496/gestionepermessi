package it.prova.gestionepermessi.repository.richiestapermesso;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository extends CrudRepository<RichiestaPermesso, Long> {

	List<RichiestaPermesso> findAllByDipendente_Id(Long id);

}
