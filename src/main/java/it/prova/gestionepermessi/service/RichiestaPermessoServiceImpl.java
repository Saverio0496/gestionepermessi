package it.prova.gestionepermessi.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	public void aggiorna(RichiestaPermesso richiestaPermessoInstance) {
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
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		return null;
	}

}
