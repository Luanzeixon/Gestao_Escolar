package br.edu.ifrn.projetotcc.dominio;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Frequencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date data;
	
	@ManyToOne
	private Diario diario;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Usuario estudante;
	
	@Column
	private String falta;

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
		Frequencia other = (Frequencia) obj;
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

	public Usuario getEstudante() {
		return estudante;
	}

	public void setEstudante(Usuario estudante) {
		this.estudante = estudante;
	}

	public Diario getDiario() {
		return diario;
	}

	public void setDiario(Diario diario) {
		this.diario = diario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getFalta() {
		return falta;
	}

	public void setFalta(String falta) {
		this.falta = falta;
	}
	
}
