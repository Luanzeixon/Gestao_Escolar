package br.edu.ifrn.projetotcc.DTO;

import java.util.List;

import br.edu.ifrn.projetotcc.dominio.Frequencia;

public class FrequenciaCreationDTO {
	
	private List<Frequencia> frequencias;
	
	public void addFrequencia(Frequencia frequencias){
		this.frequencias.add(frequencias);
	}
}
