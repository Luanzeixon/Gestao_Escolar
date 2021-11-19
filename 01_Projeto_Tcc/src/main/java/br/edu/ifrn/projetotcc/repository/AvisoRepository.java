package br.edu.ifrn.projetotcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Aviso;
import br.edu.ifrn.projetotcc.dominio.Turma;

public interface AvisoRepository extends JpaRepository<Aviso,Integer>{
	
	@Query("select d from Aviso d where d.remetente like %:nome%")	
	List<Aviso> findByNome(@Param("nome") String remetente);
}
