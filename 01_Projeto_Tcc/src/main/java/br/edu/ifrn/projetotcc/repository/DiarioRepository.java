package br.edu.ifrn.projetotcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Diario;

public interface DiarioRepository extends JpaRepository<Diario,Integer> {
	
	@Query("select d from Diario d where d.nome like %:nome%")	
	List<Diario> findByNome(@Param("nome") String nome);
	
	@Query("select d from Diario d where d.professor.id like :id"
			+ " and d.nome like %:nome% ")	
	List<Diario> findByProfessorAndId(@Param("id") int id, 
			@Param("nome") String nome);
	
	@Query("select d from Diario d where d.professor.nome like %:nome%")	
	List<Diario> findByProfessor(@Param("nome") String nome);
	
	@Query("select d from Diario d where d.estudante like :id")	
	List<Diario> findByEstudante(@Param("id") int id);
	
}
