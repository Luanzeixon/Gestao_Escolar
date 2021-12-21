package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.edu.ifrn.projetotcc.DTO.FrequenciaCreationDTO;
import br.edu.ifrn.projetotcc.DTO.NotaCreationDTO;
import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Frequencia;
import br.edu.ifrn.projetotcc.dominio.Nota;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.AvisoRepository;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.NotaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/notas")
public class buscaNotaController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private NotaRepository notaRepository;
	
	@Autowired
	private DiarioRepository  diarioRepository;
	
	@GetMapping("/buscaNotas")
	public String entrarBuscaDisciplina(ModelMap model){
		model.addAttribute("u", retornarUsuario());
		return "usuario/professor/buscaNota";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "estudante", required = false) String estudante,
			ModelMap model) {
		
		model.addAttribute("u", retornarUsuario());
		
		List<Nota> todasNotas = notaRepository.findByAluno(estudante);
		List<Nota> notasEncontradas = new ArrayList<>();
		
		List<Diario> diariosProfessor = diarioRepository.findByProfessor(retornarUsuario().getNome());
		
		for(int i = 0; i < todasNotas.size(); i++) {
			for(int j = 0; j < diariosProfessor.size(); j++) {
				if(todasNotas.get(i).getDiario().getId() == diariosProfessor.get(j).getId() ) {
					notasEncontradas.add(todasNotas.get(i));
				}
			}
		}
		
		model.addAttribute("notasEncontradas",notasEncontradas);
		
				return "usuario/professor/buscaNota";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idNota, RedirectAttributes attr, ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		notaRepository.deleteById(idNota);
		
		attr.addFlashAttribute("msgSucesso", "Nota removida com sucesso");

		return "redirect:/notas/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idNota, ModelMap model ) {
		model.addAttribute("u", retornarUsuario());
		
		Nota n = notaRepository.findById(idNota).get();
		NotaCreationDTO notasForm = new NotaCreationDTO();
		notasForm.addNota(n);
		
		model.addAttribute("form", notasForm);
		model.addAttribute("notas", n); 
		 
		return "usuario/professor/paginaNota";
	}
	
	@PostMapping("/filtrarNota")
	@Transactional(readOnly = false)
	public String filtarNota(@RequestParam(name = "bimestre", required = false) String bimestre, ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		int b = Integer.parseInt(bimestre);
		
		if(b != 0) {
			List<Nota> notasEncontradas = notaRepository.findByBimestre(b);
			model.addAttribute("notasEncontradas", notasEncontradas);
		}
		
		

		return "usuario/professor/buscaNota";

	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
