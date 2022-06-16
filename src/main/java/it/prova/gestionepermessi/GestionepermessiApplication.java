package it.prova.gestionepermessi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;
import it.prova.gestionepermessi.model.Utente;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}

	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Back Office User", "ROLE_BO_USER"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente User", "ROLE_DIPENDENTE_USER"));
		}

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", new Date());
			Dipendente dipendenteAdmin = new Dipendente("Gianluca", "Fava", "FVAGNC89I67H501G", "g.fava@prova.it",
					new Date(), new Date(), Sesso.MASCHIO);
			admin.setDipendente(dipendenteAdmin);
			dipendenteAdmin.setUtente(admin);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovoConDipendente(admin, dipendenteAdmin);
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("bo") == null) {
			Utente backOffice = new Utente("bo", "bo", new Date());
			Dipendente dipendenteBackOffice = new Dipendente("Lorenzo", "Fiore", "FORLRN96T06A323C", "l.fiore@prova.it",
					new Date(), new Date(), Sesso.MASCHIO);
			backOffice.setDipendente(dipendenteBackOffice);
			dipendenteBackOffice.setUtente(backOffice);
			backOffice.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER"));
			utenteServiceInstance.inserisciNuovoConDipendente(backOffice, dipendenteBackOffice);
			utenteServiceInstance.changeUserAbilitation(backOffice.getId());
		}

		if (utenteServiceInstance.findByUsername("dipendente") == null) {
			Utente dipendente = new Utente("dipendente", "dipendente", new Date());
			Dipendente dip = new Dipendente("Matteo", "Foglio", "FGLMTO80U03B323Z", "m.foglio@prova.it", new Date(),
					new Date(), Sesso.MASCHIO);
			dipendente.setDipendente(dip);
			dip.setUtente(dipendente);
			dipendente.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			utenteServiceInstance.inserisciNuovoConDipendente(dipendente, dip);
			utenteServiceInstance.changeUserAbilitation(dipendente.getId());
		}

	}

}
