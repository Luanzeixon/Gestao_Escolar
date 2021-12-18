package br.edu.ifrn.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.NotaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("boletins")
public class boletimEstudanteController {
	
	@Autowired
	private DiarioRepository diarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	@Autowired
	private NotaRepository notaRepository;
	
	@GetMapping("busca")
	public String entrarBuscaBoletim(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		List<Diario> diarios =  diarioRepository.findByEstudante(retornarUsuario().getId());
		
		
		
		return "/usuario/estudante/paginaBoletimEstudante";
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
