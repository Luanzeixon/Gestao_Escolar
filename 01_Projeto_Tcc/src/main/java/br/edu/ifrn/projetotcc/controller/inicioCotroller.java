package br.edu.ifrn.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.AvisoRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
public class inicioCotroller {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AvisoRepository avisoRepository;

	@GetMapping("/")
	public String inicio(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		List<Aviso> avisosEncontrados = avisoRepository.findAll();
		int qnt = avisosEncontrados.size();
		model.addAttribute("avisosEncontrados", avisosEncontrados);
		model.addAttribute("qnt", qnt);
		
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
		model.addAttribute("u", retornarUsuario());
		return "usuario/secretario/paginaCadastro";

	}

	@GetMapping("/buscas")
	public String buscas(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/secretario/paginaBuscaEditar";
	}

	@GetMapping("/calendario")
	public String calendario(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "calendario";
	}

	@GetMapping("/dados")
	public String dados(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/dadosUsuario";
	}
	@GetMapping("/documentos")
	public String docs(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "documentos";
	}
	@GetMapping("/diarios")
	public String diarios(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/professor/paginaProfessor";
	}
	@GetMapping("/avisos")
	public String avisos(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/secretario/paginaAviso";
	}
	public Usuario retornarUsuario() {
		// BUSCANDO DADOS DO USUARIO LOGADO
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
	
}
