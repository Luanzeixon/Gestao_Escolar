package br.edu.ifrn.projetotcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioCotroller {

	@GetMapping("/")
	public String inicio() {

		return "inicio";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Login ou senha incorretos, tente novamente");
		return "login";
	}

}
