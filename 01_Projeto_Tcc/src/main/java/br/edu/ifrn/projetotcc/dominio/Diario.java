package br.edu.ifrn.projetotcc.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Diario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nome;
	
	@ManyToMany
	private List<Usuario> estudante;
	
	@Transient
	private Usuario tipoEstudante;
	
	@ManyToOne
	private Usuario professor;
	
	@Transient
	private Usuario tipoProfessor;
	
	@ManyToOne
	private Disciplina disciplina;
	
	@Transient
	private Disciplina tipoDisciplina;

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
		Diario other = (Diario) obj;
		if (id != other.id)
			return false;
		return true;
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

	public List<Usuario> getEstudante() {
		return estudante;
	}

	public void setEstudante(List<Usuario> estudante) {
		this.estudante = estudante;
	}
	
	public Usuario getProfessor() {
		return professor;
	}

	public void setProfessor(Usuario professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Usuario getTipoEstudante() {
		return tipoEstudante;
	}

	public void setTipoEstudante(Usuario tipoEstudante) {
		this.tipoEstudante = tipoEstudante;
	}

	public Usuario getTipoProfessor() {
		return tipoProfessor;
	}

	public void setTipoProfessor(Usuario tipoProfessor) {
		this.tipoProfessor = tipoProfessor;
	}

	public Disciplina getTipoDisciplina() {
		return tipoDisciplina;
	}

	public void setTipoDisciplina(Disciplina tipoDisciplina) {
		this.tipoDisciplina = tipoDisciplina;
	}
	
	

}
