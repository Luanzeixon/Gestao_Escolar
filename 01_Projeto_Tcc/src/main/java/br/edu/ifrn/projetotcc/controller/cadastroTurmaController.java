package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.DTO.AutocompleteDTO;
import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Turma;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.TurmaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/turmas")
public class cadastroTurmaController {
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/cadastroTurma")
	public String entrarCadastroDisciplina(ModelMap model) {
		model.addAttribute("turma", new Turma());
		return "turma/cadastroTurma";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvarCadastroTurma(Turma turma, RedirectAttributes attr, ModelMap model) {
		List<String> validacao = validarDados(turma);
		if(!validacao.isEmpty()) {
			model.addAttribute("msgErro",validacao);
			return "/turma/cadastroTurma";
		}
		
		turmaRepository.save(turma);
		
		attr.addFlashAttribute("msgSucesso", "Turma inserido com sucesso");

		return "redirect:/turmas/cadastroTurma";
	}
	
	@GetMapping("/autocompleteEstudante")
	@Transactional(readOnly = true) 
	@ResponseBody 
	public List<AutocompleteDTO> autocompleteProfissoes(@RequestParam("term") String termo) {

		List<Usuario> estudante = usuarioRepository.findByNomeAndEstudante(termo);

		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		estudante.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));
		return resultados;
	}
	
	@PostMapping("/addEstudante")
	public String addEstudante(Turma turma, ModelMap model) {
		
		if(turma.getEstudante() == null) {
			turma.setEstudante(new ArrayList<>());
		}
		turma.getEstudante().add(turma.getTipoEstudante());
		
		return "turma/cadastroTurma"; 
	}
	
	@PostMapping("/removerEstudante/{id}")
	public String removerEstudante(@PathVariable("id") Integer idEstudante,
			Turma turma, 
			ModelMap model ) {
		
		Usuario estudante = new Usuario();
		
		estudante.setId(idEstudante);
		
		turma.getEstudante().remove(estudante);
		
		return "turma/cadastroTurma"; 
	}
	
	private List<String> validarDados(Turma turma){
		List<String> msgs = new ArrayList<>();
		if(turma.getNome() == null || turma.getNome().isEmpty()) {
			msgs.add("Campo nome é obrigatorio!");
		}if(turma.getData() == null || turma.getData().isEmpty()) {
			msgs.add("Campo data é obrigatorio!");
		}
		return msgs;
	}
	
}
