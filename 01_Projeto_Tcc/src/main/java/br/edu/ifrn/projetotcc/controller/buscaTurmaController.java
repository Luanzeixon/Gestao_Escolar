package br.edu.ifrn.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Turma;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.TurmaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/turmas")
public class buscaTurmaController {
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/buscaTurma")
	public String entrarBuscaTurma(ModelMap model){
		model.addAttribute("usuario", retornarUsuario());
		return "turma/buscaTurma";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {
	
		List<Turma> turmasEncontradas = turmaRepository.findByNome(nome);
		
		model.addAttribute("turmasEncontradas", turmasEncontradas);
		
				return "turma/buscaTurma";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idTurma, RedirectAttributes attr) {

		turmaRepository.deleteById(idTurma);
		attr.addFlashAttribute("msgSucesso", "Turma removido com sucesso");

		return "redirect:/turmas/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idTurma, ModelMap model ) {
		
		Turma t = turmaRepository.findById(idTurma).get();
		
		model.addAttribute("turma", t); 
		 
		return "/turma/cadastroTurma";
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
