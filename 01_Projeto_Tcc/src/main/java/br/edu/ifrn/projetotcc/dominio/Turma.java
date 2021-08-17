package br.edu.ifrn.projetotcc.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private String data;
	
	@ManyToMany
	private List<Usuario> estudante;
	
	@Transient
	private Usuario tipoEstudante;

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
		Turma other = (Turma) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

	public Usuario getTipoEstudante() {
		return tipoEstudante;
	}

	public void setTipoEstudante(Usuario tipoEstudante) {
		this.tipoEstudante = tipoEstudante;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Usuario> getEstudante() {
		return estudante;
	}

	public void setEstudante(List<Usuario> estudante) {
		this.estudante = estudante;
	}



}
