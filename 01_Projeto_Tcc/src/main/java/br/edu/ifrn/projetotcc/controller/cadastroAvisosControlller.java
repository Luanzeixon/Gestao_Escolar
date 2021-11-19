package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.AvisoRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/avisos")
public class cadastroAvisosControlller {
	
	@Autowired
	private AvisoRepository avisoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/cadastro")
	public String entrarCadastroAviso(ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		model.addAttribute("aviso", new Aviso());
		return "usuario/secretario/cadastroAviso";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvarCadastroEstudante(Aviso aviso, RedirectAttributes attr, ModelMap model) {
		
		List<String> validacao = validarDados(aviso);
		
		if(!validacao.isEmpty()) {
			model.addAttribute("usuario", retornarUsuario());
			model.addAttribute("msgErro",validacao);
			return "usuario/secretario/cadastroAviso";
		}
		avisoRepository.save(aviso);
		
		attr.addFlashAttribute("msgSucesso", "Aviso inserido com sucesso");

		return "redirect:/avisos/cadastro";
	}
	
	private List<String> validarDados(Aviso aviso){
		List<String> msgs = new ArrayList<>();
		if(aviso.getRemetente() == null || aviso.getRemetente().isEmpty()) {
			msgs.add("É preciso dizer o Remetente!");
		}
		return msgs;
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
