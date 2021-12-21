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
	
	@Query("select n from Nota n where n.estudante.nome like %:estudante%") 
	List<Nota> findByAluno(@Param("estudante") String estudante);
	
	@Query("select n from Nota n where n.bimestre like :bimestre") 
	List<Nota> findByBimestre(@Param("bimestre") int bimestre);
	
	@Query("select n from Nota n where n.estudante.nome like %:estudante%"
			+ " and n.diario.nome like %:diario%") 
	List<Nota> findByAlunoAndDiario(@Param("estudante") String estudante, @Param("diario") String diario );
	
	@Query("select n from Nota n where n.diario.id like :diario") 
	List<Nota> findByDiario(@Param("diario") int id);
	
	@Query("select n from Nota n where n.bimestre like :bimestre"
			+ " and n.diario.id like :id") 
	List<Nota> findByBimestreAndDiario(@Param("bimestre") int bimestre,
			@Param("id") int id);
	
	@Query("select n from Nota n where n.bimestre like :bimestre"
			+ " and n.estudante.nome like %:estudante%") 
	List<Nota> findByTodosBimestres(@Param("bimestre") int bimestre, @Param("estudante") String estudante);
	
	@Query("select n from Nota n where n.bimestre like :bimestre"
			+ " and n.diario.id like :id"
			+ " and n.estudante.id like :idd") 
	List<Nota> findByBimestreAndDiarioAndEstudante(@Param("bimestre") int bimestre,
			@Param("id") int id, @Param("idd") int idd );
}
