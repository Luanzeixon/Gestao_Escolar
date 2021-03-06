package br.edu.ifrn.projetotcc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.projetotcc.DTO.FrequenciaCreationDTO;
import br.edu.ifrn.projetotcc.DTO.NotaCreationDTO;
import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Diario;
import br.edu.ifrn.projetotcc.dominio.Frequencia;
import br.edu.ifrn.projetotcc.dominio.Nota;
import br.edu.ifrn.projetotcc.dominio.Usuario;
import br.edu.ifrn.projetotcc.repository.DiarioRepository;
import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;
import br.edu.ifrn.projetotcc.repository.NotaRepository;
import br.edu.ifrn.projetotcc.repository.UsuarioRepository;

@Controller
@RequestMapping("/diariosProfessor")
public class buscaDiarioProfessorController {

	@Autowired
	private DiarioRepository diarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	@Autowired
	private NotaRepository notaRepository;

	@GetMapping("/busca")
	public String entrarDiarioP(ModelMap model) {
		model.addAttribute("u", retornarUsuario());
		return "usuario/professor/paginaDiarioProfessor";
	}

	@GetMapping("/buscar")
	@Transactional(readOnly = false)
	public String buscar(@RequestParam(name = "nome", required = false) String nome, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		int id = retornarUsuario().getId();
		List<Diario> diariosEncontrados = diarioRepository.findByProfessorAndId(id, nome);

		model.addAttribute("diariosEncontrados", diariosEncontrados);

		return "usuario/professor/paginaDiarioProfessor";

	}

	@GetMapping("/frequencia/{id}")
	public String frequencia(@PathVariable("id") Integer idDiario, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		Diario d = diarioRepository.findById(idDiario).get();

		List<Usuario> alunosDiario = d.getEstudante();

		// Criando uma lista de frequ??ncias a ser enviada para a p??gina
		List<Frequencia> frequencias = new ArrayList<Frequencia>();

		// Para vincular lista no thymeleaf
		FrequenciaCreationDTO frequenciasForm = new FrequenciaCreationDTO();

		for (int i = 0; i < alunosDiario.size(); i++) {
			Usuario u = alunosDiario.get(i);

			Frequencia f = new Frequencia();
			f.setDiario(d);
			f.setEstudante(u);
			frequenciasForm.addFrequencia(f);
		}

		model.addAttribute("form", frequenciasForm);
		model.addAttribute("frequencias", frequencias);

		return "usuario/professor/paginaFrequencia";
	}

	@PostMapping("/salvarFrequencia")
	public String salvarFrequencia(FrequenciaCreationDTO frequencias, RedirectAttributes attr, ModelMap model,
			@RequestParam("data") String data) {

		model.addAttribute("u", retornarUsuario());

		// Falar qual formato e do input data...
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		Date dataFormatada;
		try {
			// ...passar de string para data...
			dataFormatada = formato.parse(data);

			// ...percorrer todas as frequencias e setar pra essa data
			for (int i = 0; i < frequencias.getFrequencias().size(); i++) {
				frequencias.getFrequencias().get(i).setData(dataFormatada);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		frequenciaRepository.saveAll(frequencias.getFrequencias());
		attr.addFlashAttribute("msgSucesso", "Frequencias salvas");

		return "redirect:/diariosProfessor/busca";
	}

	@GetMapping("/nota/{id}")
	public String nota(@PathVariable("id") Integer idDiario, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		Diario d = diarioRepository.findById(idDiario).get();

		List<Usuario> alunosDiario = d.getEstudante();

		List<Nota> notas = new ArrayList<Nota>();

		NotaCreationDTO notasForm = new NotaCreationDTO();

		for (int i = 0; i < alunosDiario.size(); i++) {
			Usuario u = alunosDiario.get(i);

			Nota n = new Nota();
			n.setDiario(d);
			n.setEstudante(u);
			notasForm.addNota(n);
		}

		model.addAttribute("form", notasForm);
		model.addAttribute("notas", notas);

		return "usuario/professor/paginaNota";
	}

	@PostMapping("/salvarNota")
	public String salvarNota(NotaCreationDTO notas, RedirectAttributes attr, ModelMap model,
			@RequestParam("select") String bimestre) {

		model.addAttribute("u", retornarUsuario());
		int valorB = Integer.parseInt(bimestre);
		List<Nota> n = new ArrayList<>();

		for (int i = 0; i < notas.getNotas().size(); i++) {
			notas.getNotas().get(i).setBimestre(valorB);
			List<String> validacao = validarDados(notas.getNotas().get(i));

			n = notaRepository.findByBimestreAndDiario(valorB, notas.getNotas().get(i).getDiario().getId());
			if (n.size() > 0) {
				n.get(i).setNota(notas.getNotas().get(i).getNota());

			}

			if (!validacao.isEmpty()) {
				model.addAttribute("u", retornarUsuario());
				model.addAttribute("msgErro", validacao);
				model.addAttribute("form", notas);
				return "usuario/professor/paginaNota";
			}
		}

		if (n.size() > 0) {
			notas.setNotas(n);
		}

		notaRepository.saveAll(notas.getNotas());
		attr.addFlashAttribute("msgSucesso", "Notas salvas");

		return "redirect:/diariosProfessor/busca";
	}

	@GetMapping("/boletim/{id}")
	public String boletim(@PathVariable("id") Integer idDiario, ModelMap model) {
		model.addAttribute("u", retornarUsuario());

		Diario d = diarioRepository.findById(idDiario).get();

		List<Nota> notas = notaRepository.findByDiario(d.getId());
		List<Usuario> alunosDiario = d.getEstudante();

		for (int i = 0; i < alunosDiario.size(); i++) {
			float soma = 0;
			float media = 0;
			float recu = 0;
			List<Nota> enotas = notaRepository.findByAlunoAndDiario(alunosDiario.get(i).getId(), d.getId());
			for (int j = 0; j < enotas.size(); j++) {
				if (enotas.get(j).getBimestre() == 5) {
					recu = recu + enotas.get(j).getNota();
				} else {
					soma = soma + enotas.get(j).getNota();
				}
			}
			media = soma / 4;
			alunosDiario.get(i).setMedia(media);

			if (media >= 6.0) {
				alunosDiario.get(i).setSituacao("Aprovado");
			}
			if (media >= 2.0 && media <= 6.0) {
				if (recu == 0) {
					alunosDiario.get(i).setSituacao("Recupera????o");
				} else {
					media = (recu + media) / 2;
					alunosDiario.get(i).setMedia(media);
					if (media >= 6.0) {
						alunosDiario.get(i).setSituacao("Aprovado");
					} else {
						alunosDiario.get(i).setSituacao("Reprovado");
					}
				}

			}
			if (media < 2.0) {
				alunosDiario.get(i).setSituacao("Reprovado");
			}

		}

		model.addAttribute("notas", notas);
		model.addAttribute("diarios", d);
		model.addAttribute("estudante", alunosDiario);
		return "usuario/professor/paginaBoletim";
	}
	
	

	public Usuario retornarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepository.findByEmail(email).get();
		return usuario;
	}

	private List<String> validarDados(Nota nota) {
		List<String> msgs = new ArrayList<>();
		if (nota.getBimestre() == 0) {
			msgs.add("Escolha o Bimestre!");
		}
		return msgs;
	}
}
