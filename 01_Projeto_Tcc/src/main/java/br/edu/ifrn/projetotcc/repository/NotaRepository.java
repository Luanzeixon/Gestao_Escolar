package br.edu.ifrn.projetotcc.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Frequencia;
import br.edu.ifrn.projetotcc.dominio.Nota;

public interface NotaRepository extends JpaRepository<Nota,Integer> {
	
	@Query("select f from Frequencia f where f.estudante.nome like %:estudante%") 
	List<Frequencia> findByAluno(@Param("estudante") String estudante);
}
