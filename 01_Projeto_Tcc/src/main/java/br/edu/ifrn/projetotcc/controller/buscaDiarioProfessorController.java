package br.edu.ifrn.projetotcc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Frequencia;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/diariosProfessor")
public class buscaDiarioProfessorController {
	
	@Autowired
	private DiarioRepository diarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FrequenciaRepository frequenciaRepository;
	
	
	@GetMapping("/busca")
	public String entrarDiarioP(ModelMap model){
		model.addAttribute("usuario", retornarUsuario());
		return "usuario/professor/paginaDiarioProfessor";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		
		int id = retornarUsuario().getId();
		List<Diario> diariosEncontrados = diarioRepository.findByProfessorAndId(id, nome);
		
		model.addAttribute("diariosEncontrados", diariosEncontrados);
		
				return "usuario/professor/paginaDiarioProfessor";
		
	}
	
	@GetMapping("/frequencia/{id}")
	public String frequencia(@PathVariable("id") Integer idDiario, ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		
		model.addAttribute("frequencia", new Frequencia());
		Diario d = diarioRepository.findById(idDiario).get();
		
		List<Usuario> usuariosEncontrados = d.getEstudante();
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);
		
		model.addAttribute("diario", d); 
		
		return "usuario/professor/paginaFrequencia";
	}
	
	@PostMapping("/salvarFrequencia")
	public String salvarFrequencia(Frequencia frequencia,
			RedirectAttributes attr) {
		
			frequenciaRepository.save(frequencia);
			attr.addFlashAttribute("msgSucesso", "Frequencia salva");
		
		
		return null;
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
