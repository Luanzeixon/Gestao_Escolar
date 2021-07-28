package br.edu.ifrn.projetotcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.projetotcc.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
	
	@Query("select u from Usuario u where u.cpf like %:cpf%") 
	Optional<Usuario> findByCpf(@Param("cpf") String email);
		
	
}
