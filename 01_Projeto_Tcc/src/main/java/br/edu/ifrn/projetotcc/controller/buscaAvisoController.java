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

import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Disciplina;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.AvisoRepository;
import br.edu.ifrn.projetotcc.repository.DisciplinaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/avisos")
public class buscaAvisoController {
	
	@Autowired
	private AvisoRepository avisoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/buscaAvisos")
	public String entrarBuscaDisciplina(ModelMap model){
		model.addAttribute("usuario", retornarUsuario());
		return "usuario/secretario/buscaAviso";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "remetente", required = false) String remetente,
			ModelMap model) {
		
		model.addAttribute("usuario", retornarUsuario());
		
		List<Aviso> avisosEncontrados = avisoRepository.findByNome(remetente);
		
		model.addAttribute("avisosEncontrados", avisosEncontrados);
		
				return "usuario/secretario/buscaAviso";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idAviso, RedirectAttributes attr, ModelMap model) {
		model.addAttribute("usuario", retornarUsuario());
		
		avisoRepository.deleteById(idAviso);
		attr.addFlashAttribute("msgSucesso", "Aviso removido com sucesso");

		return "redirect:/avisos/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idAviso, ModelMap model ) {
		model.addAttribute("usuario", retornarUsuario());
		
		Aviso a = avisoRepository.findById(idAviso).get();
		
		model.addAttribute("aviso", a); 
		 
		return "usuario/secretario/cadastroAviso";
	}
	
	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}
}
