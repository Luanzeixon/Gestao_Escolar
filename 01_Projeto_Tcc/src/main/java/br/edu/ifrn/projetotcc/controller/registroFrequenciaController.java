package br.edu.ifrn.projetotcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.ifrn.projetotcc.repository.FrequenciaRepository;

@Controller
public class registroFrequenciaController {

	@Autowired
	private FrequenciaRepository frequenciaRepository;
	
	
}
