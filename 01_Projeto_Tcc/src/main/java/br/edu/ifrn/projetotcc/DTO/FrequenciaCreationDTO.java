package br.edu.ifrn.projetotcc.DTO;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.projetotcc.dominio.Frequencia;

public class FrequenciaCreationDTO {
	
	private List<Frequencia> frequencias = new ArrayList<>();
	
	public void addFrequencia(Frequencia frequencias){
		this.frequencias.add(frequencias);
	}

	public List<Frequencia> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(List<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}
	
}
