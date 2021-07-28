package br.edu.ifrn.projetotcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class cadastroEstudanteController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/cadastroEstudante")
	public String entrarCadastroEstudante(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroEstudante";
	}

	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvarCadastroEstudante(Usuario usuario, RedirectAttributes attr) {
		usuario.setTipo("ESTUDANTE");
		
		usuarioRepository.save(usuario);
		
		attr.addFlashAttribute("msgSucesso", "Estudante inserido com sucesso");

		return "redirect:/usuarios/cadastroEstudante";
	}
}
