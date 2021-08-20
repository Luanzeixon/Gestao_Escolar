package br.edu.ifrn.projetotcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
public class inicioCotroller {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/")
	public String inicio(ModelMap model){
		
		//BUSCANDO DADOS DO USUARIO LOGADO
		Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
		
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).get();
        model.addAttribute("usuario", usuario);
        
        if(usuario.getTipo().equals("ESTUDANTE")) {
        	return "usuario/paginaEstudante";
        }if(usuario.getTipo().equals("PROFESSOR")) {
        	return "usuario/paginaProfessor";
        }if(usuario.getTipo().equals("PARENTE")) {
        	return "usuario/paginaParente";
        }if(usuario.getTipo().equals("SECRETARIO") || usuario.getTipo().equals("ADM") ) {
        	return "usuario/paginaSecretario";
        }else{
        	return "inicio";
        }
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Login ou senha incorretos, tente novamente");
		return "login";
	}

}
