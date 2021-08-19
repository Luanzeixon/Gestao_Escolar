package br.edu.ifrn.projetotcc.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.edu.ifrn.projetotcc.dominio.Arquivo;

@Entity
public class Usuario {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column
private String matricula;

@Column
private String tipo;

@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
private Arquivo foto;

@Column
private String nome;

@Column
private String senha;

@Column
private String email;

@Column
private String cpf;

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Usuario other = (Usuario) obj;
	if (id != other.id)
		return false;
	return true;
}

public Arquivo getFoto() {
	return foto;
}

public void setFoto(Arquivo foto) {
	this.foto = foto;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public String getSenha() {
	return senha;
}

public void setSenha(String senha) {
	this.senha = senha;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getCpf() {
	return cpf;
}

public void setCpf(String cpf) {
	this.cpf = cpf;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

public String getMatricula() {
	return matricula;
}

public void setMatricula(String matricula) {
	this.matricula = matricula;
}

	
}
