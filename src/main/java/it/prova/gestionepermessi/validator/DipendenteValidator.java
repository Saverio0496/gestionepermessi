package it.prova.gestionepermessi.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.prova.gestionepermessi.model.Dipendente;

@Component
public class DipendenteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Dipendente dipendenteDaValidare = (Dipendente) target;

		if (dipendenteDaValidare.getDataAssunzione() != null && dipendenteDaValidare.getDataDimissioni() != null
				&& (dipendenteDaValidare.getDataAssunzione().before(dipendenteDaValidare.getDataNascita())
						|| dipendenteDaValidare.getDataDimissioni().before(dipendenteDaValidare.getDataNascita()))) {
			errors.rejectValue("dataNascita", "Errore, la data di nascita deve essere settata e non può essere dopo le altre date!");
		}
		if (dipendenteDaValidare.getDataAssunzione() == null && dipendenteDaValidare.getDataDimissioni() != null) {
			errors.rejectValue("dataAssunzione", "Errore, la data di assunzione deve essere settata se è presente la data di dimissione!");
		}
		if (dipendenteDaValidare.getDataAssunzione() != null
				&& dipendenteDaValidare.getDataAssunzione().after(dipendenteDaValidare.getDataDimissioni())) {
			errors.rejectValue("dataAssunzione", "Errore, la data di assunzione non può essere dopo la data di dimissione!");
		}
	}

}
