package br.edu.ifrn.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Turma;
import br.edu.ifrn.projetotcc.repository.TurmaRepository;

@Controller
@RequestMapping("/turmas")
public class buscaTurmaController {
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@GetMapping("/buscaTurma")
	public String entrarBuscaTurma(){
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
}
