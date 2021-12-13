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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.DTO.FrequenciaCreationDTO;
import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Frequencia;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.AvisoRepository;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/frequencias")
public class buscaFrequenciaController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FrequenciaRepository frequenciaRepository;
	
	@Autowired
	private DiarioRepository  diarioRepository;
	
	@GetMapping("/buscaFrequencias")
	public String entrarBuscaDisciplina(ModelMap model){
		model.addAttribute("u", retornarUsuario());
		return "usuario/professor/buscaFrequencia";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "estudante", required = false) String estudante,
			@RequestParam(name = "data", required = false) String data,
			ModelMap model) {
		
		model.addAttribute("u", retornarUsuario());
		
		List<Frequencia> todasFrequencias = frequenciaRepository.findByAluno(estudante);
		List<Frequencia> frequenciasEncontradas = new ArrayList<>();
		
		//Retornar lista de Diario pelo o nome do usuario(professor) cadastrado no site
		List<Diario> diariosProfessor = diarioRepository.findByProfessor(retornarUsuario().getNome());
		
		//Lista de frequencias que tem o id do diario do professor 
		for(int i = 0; i < todasFrequencias.size(); i++) {
			for(int j = 0; j < diariosProfessor.size(); j++) {
				if(todasFrequencias.get(i).getDiario().getId() == diariosProfessor.get(j).getId() ) {
					frequenciasEncontradas.add(todasFrequencias.get(i));
				}
			}
		}
		
		model.addAttribute("frequenciasEncontradas",frequenciasEncontradas);
		
				return "usuario/professor/buscaFrequencia";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idFrequencia, RedirectAttributes attr, ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		frequenciaRepository.deleteById(idFrequencia);
		
		attr.addFlashAttribute("msgSucesso", "Frequencia removida com sucesso");

		return "redirect:/frequencias/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idFrequencia, ModelMap model ) {
		model.addAttribute("u", retornarUsuario());
		
		Frequencia f = frequenciaRepository.findById(idFrequencia).get();
		FrequenciaCreationDTO frequenciasForm = new FrequenciaCreationDTO();
		frequenciasForm.addFrequencia(f);
		
		model.addAttribute("form", frequenciasForm);
		model.addAttribute("frequencias", f); 
		 
		return "usuario/professor/paginaFrequencia";
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
