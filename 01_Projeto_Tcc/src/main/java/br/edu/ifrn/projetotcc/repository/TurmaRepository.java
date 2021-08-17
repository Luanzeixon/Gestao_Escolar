package br.edu.ifrn.projetotcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Turma;

public interface TurmaRepository extends JpaRepository<Turma,Integer>{
	
	@Query("select d from Turma d where d.nome like %:nome%")	
	List<Turma> findByNome(@Param("nome") String nome);
}
