package br.edu.ifrn.projetotcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina,Integer> {

	@Query("select d from Disciplina d where d.nome like %:nome%")	
	List<Disciplina> findByNome(@Param("nome") String nome);
}
