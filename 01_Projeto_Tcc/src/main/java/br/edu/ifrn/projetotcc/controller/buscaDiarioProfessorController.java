package br.edu.ifrn.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/diariosProfessor")
public class buscaDiarioProfessorController {
	
	@Autowired
	private DiarioRepository diarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/busca")
	public String entrarDiarioP(){
		return "usuario/professor/paginaDiarioProfessor";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {
		
		int id = retornarUsuario().getId();
		List<Diario> diariosEncontrados = diarioRepository.findByProfessorAndId(id, nome);
		
		model.addAttribute("diariosEncontrados", diariosEncontrados);
		
				return "usuario/professor/paginaDiarioProfessor";
		
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
