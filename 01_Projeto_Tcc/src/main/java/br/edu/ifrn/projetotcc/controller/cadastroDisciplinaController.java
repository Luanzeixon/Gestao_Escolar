package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;

@Controller
@RequestMapping("/disciplinas")
public class cadastroDisciplinaController {
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@GetMapping("/cadastroDisciplina")
	public String entrarCadastroDisciplina(ModelMap model) {
		model.addAttribute("disciplina", new Disciplina());
		return "disciplina/cadastroDisciplina";
	}
	
	@PostMapping("/salvarDisciplina")
	@Transactional(readOnly = false)
	public String salvarCadastroEstudante(Disciplina disciplina, RedirectAttributes attr, ModelMap model) {
		
		List<String> validacao = validarDados(disciplina);
		
		if(!validacao.isEmpty()) {
			model.addAttribute("msgsErro",validacao);
			return "/disciplina/cadastroDisciplina";
		}
		disciplinaRepository.save(disciplina);
		
		attr.addFlashAttribute("msgSucesso", "Disciplina inserido com sucesso");

		return "redirect:/disciplinas/cadastroDisciplina";
	}
	
	private List<String> validarDados(Disciplina disciplina){
		List<String> msgs = new ArrayList<>();
		if(disciplina.getNome() == null || disciplina.getNome().isEmpty()) {
			msgs.add("Campo nome é obrigatorio!");
		}if(disciplina.getCarga_horaria() == 0) {
			msgs.add("A carga horaria não pode ser 0!");
		}
		return msgs;
	}
	
	
}
