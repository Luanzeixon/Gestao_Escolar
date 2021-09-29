package br.edu.ifrn.projetotcc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.dominio.Arquivo;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.ArquivoRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class cadastroEstudanteController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;

	@GetMapping("/cadastroEstudante")
	public String entrarCadastroEstudante(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroEstudante";
	}

	@PostMapping("/salvarEstudante")
	@Transactional(readOnly = false)
	public String salvarCadastroEstudante(Usuario usuario, RedirectAttributes attr,
			@RequestParam("file") MultipartFile arquivo, ModelMap model) {
		try {
			List<String> validacao = validarDados(usuario);
			if(!validacao.isEmpty()) {
				model.addAttribute("msgsErro",validacao);
				return "/disciplina/cadastroEstudante";
			}
			if (arquivo != null && !arquivo.isEmpty()) {
				
				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());

				arquivoRepository.save(arquivoBD);
				
				if (usuario.getFoto() != null && usuario.getFoto().getId() != null && usuario.getFoto().getId() > 0) {

					arquivoRepository.delete(usuario.getFoto());
				}

				usuario.setFoto(arquivoBD);
			} else {
				
				usuario.setFoto(null);
			}
			
			String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);

			usuario.setTipo("ESTUDANTE");

			usuarioRepository.save(usuario);

			attr.addFlashAttribute("msgSucesso", "Estudante inserido com sucesso");
			

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return "redirect:/usuarios/cadastroEstudante";
	}
	private List<String> validarDados(Usuario usuario){
		
		List<String> msgs = new ArrayList<>();
		if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("Campo nome é obrigatorio!");
		}if(usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
			msgs.add("CPF inavalido!");
		}
		if(usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("Email é obrigatorio!");
		}
		if(usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("A senha não pode ser nula!");
		}
		return msgs;
	}
	
}
