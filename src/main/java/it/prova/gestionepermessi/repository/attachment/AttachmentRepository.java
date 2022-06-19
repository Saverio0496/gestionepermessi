package it.prova.gestionepermessi.repository.attachment;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

////	@Query("select a from Attachment a join a.richiestaPermesso rp where rp.id = ?1")
//	Optional<Attachment> findByIdRichiesta(Long idRichiesta);

}
