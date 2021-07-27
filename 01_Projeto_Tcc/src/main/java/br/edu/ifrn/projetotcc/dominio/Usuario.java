package br.edu.ifrn.projetotcc.dominio;

import javax.persistence.Entity;

@Entity
public class Usuario {
private String tipo;

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

	
}
