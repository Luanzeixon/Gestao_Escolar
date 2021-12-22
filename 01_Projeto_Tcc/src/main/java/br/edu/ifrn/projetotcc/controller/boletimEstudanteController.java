package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Nota;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.NotaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/boletins")
public class boletimEstudanteController {
	
	@Autowired
	private DiarioRepository diarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	@Autowired
	private NotaRepository notaRepository;
	
	@GetMapping("/busca")
	public String entrarBuscaBoletim(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		List<Diario> diarios =  diarioRepository.findByEstudante(retornarUsuario().getId());
		List<Nota> nota1 = new ArrayList<>();
		List<Nota> nota2 = new ArrayList<>();
		List<Nota> nota3 = new ArrayList<>();
		List<Nota> nota4 = new ArrayList<>();
		List<Nota> nota5 = new ArrayList<>();
		
		for(int i = 0; i < diarios.size(); i++) {
			
			Nota n1 = notaRepository.findByBimestreAndDiarioAndEstudante(1, 
					diarios.get(i).getId(), retornarUsuario().getId());
			nota1.add(n1);
			Nota n2 = notaRepository.findByBimestreAndDiarioAndEstudante(2, 
					diarios.get(i).getId(), retornarUsuario().getId());
			nota2.add(n2);
			Nota n3 = notaRepository.findByBimestreAndDiarioAndEstudante(3, 
					diarios.get(i).getId(), retornarUsuario().getId());
			nota3.add(n3);
			Nota n4 = notaRepository.findByBimestreAndDiarioAndEstudante(4, 
					diarios.get(i).getId(), retornarUsuario().getId());
			nota4.add(n4);
			
			Nota n5 = notaRepository.findByBimestreAndDiarioAndEstudante(5, 
					diarios.get(i).getId(), retornarUsuario().getId());
			nota5.add(n5);
			
			
		}
		
		
		
		model.addAttribute("nota1", nota1);
		model.addAttribute("nota2", nota2);
		model.addAttribute("nota3", nota3);
		model.addAttribute("nota4", nota4);
		if(nota5 != null) {
			model.addAttribute("nota5", nota5);
		}
		model.addAttribute("diarios", diarios);
		
		return "/usuario/estudante/paginaBoletimEstudante";
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
