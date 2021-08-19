package br.edu.ifrn.projetotcc.controller;

import java.net.http.HttpHeaders;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.projetotcc.dominio.Arquivo;
import br.edu.ifrn.projetotcc.repository.ArquivoRepository;

@Controller
public class downloadArquivoController {

	@Autowired
	private ArquivoRepository arquivoRepository;

	// RESPONSEENTITY = ENTIDADE DE RESPOSTA, <?> = TIPO IDENFINIDO
	@GetMapping("/download/{idArquivo}")
	public ResponseEntity<?> downloadFile(@PathVariable Long idArquivo, @PathParam("salvar") String salvar) {

		// CARREGANDO ARQUIVO DO BANCOO DE DADOS

		Arquivo arquivoBD = arquivoRepository.findById(idArquivo).get();

		// PARA O NAVEGADOR SABER SE � PRA FAZER O DOWNLOAD OU SO PRA VISUALIZAR
		String texto = (salvar == null || salvar.equals("true"))
				? "attachment; filename=\"" + arquivoBD.getNomeArquivo() + "\""
				: "inline; filename=\"" + arquivoBD.getNomeArquivo() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo()))
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, texto)
				.body(new ByteArrayResource(arquivoBD.getDados()));

	}

}
