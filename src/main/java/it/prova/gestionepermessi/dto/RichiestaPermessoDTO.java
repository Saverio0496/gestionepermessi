package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoDTO {

	private Long id;

	@NotNull(message = "{tipoPermesso.notnull}")
	private TipoPermesso tipoPermesso;

	@NotNull(message = "{dataInizio.notnull}")
	private Date dataInizio;

	@NotNull(message = "{dataFine.notnull}")
	private Date dataFine;

	private boolean approvato;

	private String codiceCertificato;

	private String note;

	private MultipartFile attachment;
	
	private DipendenteDTO dipendenteDTO;

	private Long dipendenteId;

	private boolean giornoSingolo;

	public RichiestaPermessoDTO() {
	}

	public RichiestaPermessoDTO(Long id, @NotBlank(message = "{tipoPermesso.notblank}") TipoPermesso tipoPermesso,
			@NotNull(message = "{dataInizio.notnull}") Date dataInizio,
			@NotNull(message = "{dataFine.notnull}") Date dataFine, boolean approvato, String codiceCertificato,
			String note) {
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
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

	public Long getDipendenteId() {
		return dipendenteId;
	}

	public void setDipendenteId(Long dipendenteId) {
		this.dipendenteId = dipendenteId;
	}

	public boolean getGiornoSingolo() {
		return giornoSingolo;
	}

	public void setGiornoSingolo(boolean giornoSingolo) {
		this.giornoSingolo = giornoSingolo;
	}
	
	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}


	public RichiestaPermesso buildRichiestaPermessoFromModel() {
		return new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine, false,
				this.codiceCertificato, this.note);
	}

	public static RichiestaPermessoDTO buildRichiestaPermessoDTOFromModel(RichiestaPermesso richiestaPermessoModel) {
		return new RichiestaPermessoDTO(richiestaPermessoModel.getId(), richiestaPermessoModel.getTipoPermesso(),
				richiestaPermessoModel.getDataInizio(), richiestaPermessoModel.getDataFine(),
				richiestaPermessoModel.isApprovato(), richiestaPermessoModel.getCodiceCertificato(),
				richiestaPermessoModel.getNote());
	}

	public static List<RichiestaPermessoDTO> createRichiestePermessiListDTOFromModelList(
			List<RichiestaPermesso> richieste) {
		return richieste.stream().map(richiesta -> RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiesta))
				.collect(Collectors.toList());
	}
	
	public RichiestaPermesso buildRichiestaPermessoModel(boolean includesDipendente) {
		RichiestaPermesso result = new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine, this.approvato,
				this.codiceCertificato, this.note);
		if (includesDipendente && dipendenteId != null) {
			result.setDipendente(new Dipendente(this.dipendenteId));
		}
		return result;
	}

	public DipendenteDTO getDipendenteDTO() {
		return dipendenteDTO;
	}

	public void setDipendenteDTO(DipendenteDTO dipendenteDTO) {
		this.dipendenteDTO = dipendenteDTO;
	}

}
