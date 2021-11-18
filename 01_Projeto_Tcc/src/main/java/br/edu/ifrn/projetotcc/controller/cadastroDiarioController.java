package br.edu.ifrn.projetotcc.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.DTO.AutocompleteDTO;
import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/diarios")
public class cadastroDiarioController {
	
	@Autowired
	private DiarioRepository diarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@GetMapping("/cadastroDiario")
	public String entrarCadastroDiario(ModelMap model) {
		model.addAttribute("diario", new Diario());
		model.addAttribute("usuario", retornarUsuario());
		return "diario/cadastroDiario";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvarCadastroEstudante(Diario diario, RedirectAttributes attr, ModelMap model) {
		
		List<String> validacao = validarDados(diario);
		
		if(!validacao.isEmpty()) {
			model.addAttribute("msgErro",validacao);
			return "/diario/cadastroDiario";
		}
		diarioRepository.save(diario);
		
		attr.addFlashAttribute("msgSucesso", "Diario inserido com sucesso");

		return "redirect:/diarios/cadastroDiario";
	}
	
	@GetMapping("/autocompleteEstudante")
	@Transactional(readOnly = true) 
	@ResponseBody 
	public List<AutocompleteDTO> autocompleteEstudante(@RequestParam("term") String termo) {

		List<Usuario> estudante = usuarioRepository.findByNomeAndEstudante(termo);

		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		estudante.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));
		return resultados;
	}
	
	@GetMapping("/autocompleteProfessor")
	@Transactional(readOnly = true) 
	@ResponseBody 
	public List<AutocompleteDTO> autocompleteProfessor(@RequestParam("term") String termo) {

		List<Usuario> professor = usuarioRepository.findByNomeAndProfessor(termo);

		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		professor.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));
		return resultados;
	}
	
	@GetMapping("/autocompleteDisciplina")
	@Transactional(readOnly = true) 
	@ResponseBody 
	public List<AutocompleteDTO> autocompleteDisciplina(@RequestParam("term") String termo) {

		List<Disciplina> disciplina = disciplinaRepository.findByNome(termo);

		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		disciplina.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));
		return resultados;
	}
		
	@PostMapping("/addEstudante")
	public String addEstudante(Diario diario, ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());	
		if(diario.getEstudante() == null) {
			diario.setEstudante(new ArrayList<>());
		}
		diario.getEstudante().add(diario.getTipoEstudante());
		
		return "diario/cadastroDiario"; 
	}
	
	@PostMapping("/removerEstudante/{id}")
	public String removerEstudante(@PathVariable("id") Integer idEstudante,
			Diario diario, 
			ModelMap model ) {
		
		Usuario estudante = new Usuario();
		
		estudante.setId(idEstudante);
		
		diario.getEstudante().remove(estudante);
		
		return "diario/cadastroDiario"; 
	}
	
	private List<String> validarDados(Diario diario){
		List<String> msgs = new ArrayList<>();
		if(diario.getNome() == null || diario.getNome().isEmpty()) {
			msgs.add("Campo nome é obrigatorio!");
		}if(diario.getDisciplina() == null) {
			msgs.add("Campo disciplina é obrigatorio!");
		}if(diario.getEstudante() == null) {
			msgs.add("Campo aluno é obrigatorio!");
		}if(diario.getProfessor() == null) {
			msgs.add("Campo professor é obrigatorio!");
		}
		return msgs;
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
