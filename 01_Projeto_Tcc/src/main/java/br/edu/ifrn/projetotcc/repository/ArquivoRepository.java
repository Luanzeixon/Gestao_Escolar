package br.edu.ifrn.projetotcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.projetotcc.dominio.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
