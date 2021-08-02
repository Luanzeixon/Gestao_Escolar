package br.edu.ifrn.projetotcc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class buscaUsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/busca")
	public String entrarBusca() {
		
		return "/usuario/busca";
	}
	
	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "tipo", required = false) String tipo,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados, 
			ModelMap model) {
		
		List<Usuario> usuariosEncontrados = usuarioRepository.findByEmailAndNome(email, nome);
		
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);
		
		if(mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}
		
				return "usuario/busca";
		
	}
	
	@GetMapping("/remover/{id}")
	public String iniciarRemocao(@PathVariable("id") Integer idUsuario, RedirectAttributes attr) {

		usuarioRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuario removido com sucesso");

		return "redirect:/usuarios/buscar";
	}
	
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model ) {
		
		Usuario u = usuarioRepository.findById(idUsuario).get();
		
		model.addAttribute("usuario", u); 
		 
		if(u.getTipo().equals("ESTUDANTE")) {
			return  "/usuario/cadastroEstudante";
		}
		if(u.getTipo().equals("PROFESSOR")) {
			return  "/usuario/cadastroProfessor";
		}
		if(u.getTipo().equals("SECRETARIO")) {
			return  "/usuario/cadastroSecretario";
		}
		if(u.getTipo().equals("PARENTE")) {
			return  "/usuario/cadastroParente";
		}else {
			return "Erro";
		}
	}
	
}
