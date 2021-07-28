package br.edu.ifrn.projetotcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Usuario;

@Controller
@RequestMapping("/usuarios")
public class cadastroEstudanteController {
	
	
	@GetMapping("/cadastroEstudante")
	public String entrarCadastroEstudante(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroEstudante";
	}
	
	@PostMapping("/salvar")
	public String salvarCadastroEstudnate(Usuario usuario, RedirectAttributes attr) {
		usuario.setTipo("ESTUDANTE");
		return "redirect:/usuarios/cadastroEstudante";
	}
}
