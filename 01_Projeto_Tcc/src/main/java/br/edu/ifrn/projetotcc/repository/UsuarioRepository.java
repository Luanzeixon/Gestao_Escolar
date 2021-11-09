package br.edu.ifrn.projetotcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	
	@Query("select u from Usuario u where u.email like %:email% "
			+ "and u.nome like %:nome% ") 
	List<Usuario> findByEmailAndNome(@Param("email") String email,
			@Param("nome") String nome);
	
	@Query("select u from Usuario u where u.email like %:email%") 
	Optional<Usuario> findByEmail(@Param("email") String email);
	
	@Query("select u from Usuario u where u.tipo like %:tipo%")	
	List<Usuario> findByTipo(@Param("tipo") String tipo);
	
	@Query("select u from Usuario u where u.nome like %:nome% "
			+ "and u.tipo like 'ESTUDANTE' ")	
	List<Usuario> findByNomeAndEstudante(@Param("nome") String nome);
	
	@Query("select u from Usuario u where u.nome like %:nome% "
			+ "and u.tipo like 'PROFESSOR' ")	
	List<Usuario> findByNomeAndProfessor(@Param("nome") String nome);
	
}
