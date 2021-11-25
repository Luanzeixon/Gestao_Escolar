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

import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/diarios")
public class buscaDiarioController {
	
	@Autowired
	private DiarioRepository diarioRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/buscaDiario")
	public String entrarBuscaTurma(ModelMap model){
		model.addAttribute("u", retornarUsuario());
		return "diario/buscaDiario";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		
		List<Diario> diariosEncontrados = diarioRepository.findByNome(nome);
		
		model.addAttribute("diariosEncontrados", diariosEncontrados);
		
				return "diario/buscaDiario";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idDiario, RedirectAttributes attr) {

		diarioRepository.deleteById(idDiario);
		attr.addFlashAttribute("msgSucesso", "Diario removido com sucesso");

		return "redirect:/diarios/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idDiario, ModelMap model ) {
		model.addAttribute("u", retornarUsuario());
		
		Diario d = diarioRepository.findById(idDiario).get();
		
		model.addAttribute("diario", d); 
		 
		return "/diario/cadastroDiario";
	}
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
