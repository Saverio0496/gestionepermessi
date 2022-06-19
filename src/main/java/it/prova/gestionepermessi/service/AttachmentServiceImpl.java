package it.prova.gestionepermessi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.repository.attachment.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Override
	@Transactional
	public void rimuovi(Long idAttachment) {
		attachmentRepository.deleteById(idAttachment);
	}

}