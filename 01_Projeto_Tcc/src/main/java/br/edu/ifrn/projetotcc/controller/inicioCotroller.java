package br.edu.ifrn.projetotcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioCotroller {

	@GetMapping("/")
	public String inicio() {

		return "inicio";
	}

}
