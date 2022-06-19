package it.prova.gestionepermessi.dto;

import java.util.List;
import java.util.stream.Collectors;

import it.prova.gestionepermessi.model.Attachment;

public class AttachmentDTO {

	private Long id;
	private String nomeFile;
	private String contentType;
	private byte[] payload;

	public AttachmentDTO() {
	}

	public AttachmentDTO(Long id, String nomeFile, String contentType, byte[] payload) {
		this.id = id;
		this.nomeFile = nomeFile;
		this.contentType = contentType;
		this.payload = payload;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public Attachment buildAttachmentFromModel() {
		return new Attachment(this.id, this.nomeFile, this.contentType, this.payload);
	}

	public static AttachmentDTO buildAttachmentDTOFromModel(Attachment attachmentModel) {
		return new AttachmentDTO(attachmentModel.getId(), attachmentModel.getNomeFile(),
				attachmentModel.getContentType(), attachmentModel.getPayload());
	}

	public static List<AttachmentDTO> createAttachmentDTOListFromModelList(List<Attachment> modelListInput) {
		return modelListInput.stream().map(attachmentItem -> new AttachmentDTO(attachmentItem.getId(),
				attachmentItem.getNomeFile(), attachmentItem.getContentType(), attachmentItem.getPayload()))
				.collect(Collectors.toList());
	}

}
