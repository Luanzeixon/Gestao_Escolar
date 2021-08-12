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

import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;

@Controller
@RequestMapping("disciplinas")
public class buscaDisciplinaController {
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@GetMapping("/buscaDisciplina")
	public String entrarBuscaDisciplina(){
		return "disciplina/buscaDisciplina";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {
		
		List<Disciplina> disciplinasEncontradas = disciplinaRepository.findByNome(nome);
		
		model.addAttribute("disciplinasEncontradas", disciplinasEncontradas);
		
				return "disciplina/buscaDisciplina";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idDisciplina, RedirectAttributes attr) {

		disciplinaRepository.deleteById(idDisciplina);
		attr.addFlashAttribute("msgSucesso", "Disciplina removido com sucesso");

		return "redirect:/disciplinas/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idDisciplina, ModelMap model ) {
		
		Disciplina d = disciplinaRepository.findById(idDisciplina).get();
		
		model.addAttribute("disciplina", d); 
		 
		return "disciplina/cadastroDisciplina";
	}
}
