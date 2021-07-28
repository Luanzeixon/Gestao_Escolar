package br.edu.ifrn.projetotcc.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

@Id
private String tipo;

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

	
}
