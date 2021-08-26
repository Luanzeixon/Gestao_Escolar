package br.edu.ifrn.projetotcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
public class inicioCotroller {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/")
	public String inicio(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
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

	@GetMapping("/cadastros")
	public String cadastros(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		return "usuario/secretario/paginaCadastro";

	}

	@GetMapping("/buscas")
	public String buscas(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		return "usuario/secretario/paginaBuscaEditar";
	}

	@GetMapping("/calendario")
	public String calendario(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		return "calendario";
	}

	@GetMapping("/dados")
	public String dados(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		return "usuario/dadosUsuario";
	}
	public Usuario retornarUsuario() {
		// BUSCANDO DADOS DO USUARIO LOGADO
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
