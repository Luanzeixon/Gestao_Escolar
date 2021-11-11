package br.edu.ifrn.projetotcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Diario;

public interface DiarioRepository extends JpaRepository<Diario,Integer> {
	
	@Query("select d from Diario d where d.nome like %:nome%")	
	List<Diario> findByNome(@Param("nome") String nome);
	
	@Query(value = "select d from Diario d where d.professor_id like %:id%" , nativeQuery = true  )	
	List<Diario> findByProfessor(@Param("id") String id);
}
