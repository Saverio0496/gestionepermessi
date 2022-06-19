package it.prova.gestionepermessi.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoSearchDTO {

	private Long id;

	private TipoPermesso tipoPermesso;

	private Date dataInizio;

	private Date dataFine;

	private boolean approvato;

	private String codiceCertificato;

	private String note;

	private Dipendente dipendente;

	private MultipartFile attachment;

	private Long dipendenteId;

	public RichiestaPermessoSearchDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}

	public Long getDipendenteId() {
		return dipendenteId;
	}

	public void setDipendenteId(Long dipendenteId) {
		this.dipendenteId = dipendenteId;
	}

}
