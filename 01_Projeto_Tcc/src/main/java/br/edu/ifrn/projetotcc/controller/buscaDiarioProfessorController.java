package br.edu.ifrn.projetotcc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public String entrarDiarioP(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/professor/paginaDiarioProfessor";
	}

	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		int id = retornarUsuario().getId();
		List<Diario> diariosEncontrados = diarioRepository.findByProfessorAndId(id, nome);

		model.addAttribute("diariosEncontrados", diariosEncontrados);

		return "usuario/professor/paginaDiarioProfessor";

	}

	@GetMapping("/frequencia/{id}")
	public String frequencia(@PathVariable("id") Integer idDiario, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		Diario d = diarioRepository.findById(idDiario).get();

		List<Usuario> alunosDiario = d.getEstudante();

		// Criando uma lista de frequências a ser enviada para a página
		List<Frequencia> frequencias = new ArrayList<Frequencia>();

		for (int i = 0; i < alunosDiario.size(); i++) {
			Usuario u = alunosDiario.get(i);
			
			Frequencia f = new Frequencia();
			f.setDiario(d);
			f.setEstudante(u);
		}

		model.addAttribute("frequencias", frequencias);

		return "usuario/professor/paginaFrequencia";
	}

	@PostMapping("/salvarFrequencia")
	public String salvarFrequencia(List<Frequencia> frequencias, RedirectAttributes attr, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		frequenciaRepository.saveAll(frequencias);
		attr.addFlashAttribute("msgSucesso", "Frequencias salvas");

		return "redirect:/diariosProfessor/busca";
	}

	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
