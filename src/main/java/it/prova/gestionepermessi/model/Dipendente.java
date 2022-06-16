package it.prova.gestionepermessi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dipendente", uniqueConstraints = @UniqueConstraint(columnNames = { "utente_id" }))
public class Dipendente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cognome")
	private String cognome;
	@Column(name = "codFis")
	private String codFis;
	@Column(name = "email")
	private String email;
	@Column(name = "dataNascita")
	private Date dataNascita;
	@Column(name = "dataAssunzione")
	private Date dataAssunzione;
	@Column(name = "dataDimissioni")
	private Date dataDimissioni;

	@Column(name = "sesso")
	@Enumerated(EnumType.STRING)
	private Sesso sesso;

	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "utente_id", referencedColumnName = "ID")
	private Utente utente;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dipendente")
	private Set<RichiestaPermesso> richiestePermessi = new HashSet<RichiestaPermesso>(0);

	public Dipendente() {
	}

	public Dipendente(Long id, String nome, String cognome, String codFis, String email, Date dataNascita,
			Date dataAssunzione, Date dataDimissioni, it.prova.gestionepermessi.model.Sesso sesso,
			@NotNull Utente utente, Set<RichiestaPermesso> richiestePermessi) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
		this.utente = utente;
		this.richiestePermessi = richiestePermessi;
	}

	public Dipendente(Long id, String nome, String cognome, String codFis, String email, Date dataNascita,
			Date dataAssunzione, Date dataDimissioni, it.prova.gestionepermessi.model.Sesso sesso) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
	}

	public Dipendente(Long id, String nome, String cognome, String codFis) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Date getDataDimissioni() {
		return dataDimissioni;
	}

	public void setDataDimissioni(Date dataDimissioni) {
		this.dataDimissioni = dataDimissioni;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Set<RichiestaPermesso> getRichiestePermessi() {
		return richiestePermessi;
	}

	public void setRichiestePermessi(Set<RichiestaPermesso> richiestePermessi) {
		this.richiestePermessi = richiestePermessi;
	}

}
