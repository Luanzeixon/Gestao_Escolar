package br.edu.ifrn.projetotcc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class cadastroSecretarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/cadastroSecretario")
	public String entrarCadastroParente(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroSecretario";
	}

	@PostMapping("/salvarSecretario")
	@Transactional(readOnly = false)
	public String salvarCadastroSecretario(Usuario usuario, RedirectAttributes attr) {

		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		usuario.setTipo("SECRETARIO");

		usuarioRepository.save(usuario);

		attr.addFlashAttribute("msgSucesso", "Secretario inserido com sucesso");

		return "redirect:/usuarios/cadastroSecretario";
	}
	
	
}