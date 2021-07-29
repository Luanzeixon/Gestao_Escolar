package br.edu.ifrn.projetotcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class cadastroParenteController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/cadastroParente")
	public String entrarCadastroEstudante(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroParente";
	}

	@PostMapping("/salvarParente")
	@Transactional(readOnly = false)
	public String salvarCadastroParente(Usuario usuario, RedirectAttributes attr) {
		
		String senhaCriptografada = 
				new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		usuario.setTipo("PARENTE");
		
		usuarioRepository.save(usuario);
		
		attr.addFlashAttribute("msgSucesso", "Parente inserido com sucesso");

		return "redirect:/usuarios/cadastroParente";
	}
	
	
}
