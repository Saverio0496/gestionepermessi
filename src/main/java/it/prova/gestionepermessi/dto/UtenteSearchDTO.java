package it.prova.gestionepermessi.dto;

import java.util.Date;

import it.prova.gestionepermessi.model.StatoUtente;

public class UtenteSearchDTO {

	private Long id;

	private String username;

	private String nome;

	private String cognome;

	private String codFis;

	private Date dateCreated;

	private StatoUtente stato;

	private Long[] ruoliIds;

	public UtenteSearchDTO() {
	}

	public UtenteSearchDTO(String username, String nome, String congome, String codFis, Date dateCreated,
			StatoUtente stato, Long[] ruoliIds) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = congome;
		this.setCodFis(codFis);
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoliIds = ruoliIds;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

}
