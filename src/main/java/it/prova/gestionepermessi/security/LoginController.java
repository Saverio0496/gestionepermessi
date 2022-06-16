package it.prova.gestionepermessi.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
public class LoginController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model,
			HttpServletRequest request) {
		if (error != null) {
			model.addAttribute("errorMessage", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		return "login";
	}

	// questo mi serve per fare il display di un messaggio diverso in caso di
	// account bloccato
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = "Attenzione! Account disabilitato";
		} else if (exception instanceof DisabledException) {
			error = "Attenzione! Account non abilitato";
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	@RequestMapping(value = "/executeLogout", method = RequestMethod.GET)
	public String logoutPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// dovrebbe essere già impostato a null dalle impostazioni
		// invalidateHttpSession(true)
		// nel SecSecurityConfig ma il controllo si fa comunque
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("infoMessage", "You have been successfully logged out !!");
		return "login";
	}

	@RequestMapping(value = "/accessDenied", method = { RequestMethod.POST, RequestMethod.GET })
	public String accessDenied(Model model) {
		model.addAttribute("errorMessage",
				"Attenzione! Non si dispone delle autorizzazioni per accedere alla funzionalità richiesta.");
		return "index";
	}

	@RequestMapping(value = "/reset", method = { RequestMethod.GET })
	public String resetPassword() {
		return "reset";
	}

	@RequestMapping(value = "/reset", method = { RequestMethod.POST })
	public String executeResetPassword(@RequestParam(required = true) String oldPassword,
			@RequestParam(required = true) String newPassword, @RequestParam(required = true) String confermaPassword,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "redirect:/logout";
		}

		Utente utente = utenteService.findByUsername(auth.getName());
		if (utente == null || passwordEncoder.matches(utente.getPassword(), oldPassword)) {
			return "redirect:/logout";
		}

		if (newPassword.equals(confermaPassword)) {
			utente.setPassword(passwordEncoder.encode(newPassword));
			utenteService.aggiorna(utente);
		}

		return "redirect:/logout";
	}

}
