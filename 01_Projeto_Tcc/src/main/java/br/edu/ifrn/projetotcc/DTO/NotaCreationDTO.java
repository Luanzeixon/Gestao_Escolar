package br.edu.ifrn.projetotcc.DTO;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.projetotcc.dominio.Nota;

public class NotaCreationDTO {
	
	private List<Nota> notas = new ArrayList<>();
	
	public void addNota(Nota notas){
		this.notas.add(notas);
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}
	
}
