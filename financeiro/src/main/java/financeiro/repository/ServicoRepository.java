package financeiro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import financeiro.model.Servicos;

@Repository
@Transactional
public interface ServicoRepository extends JpaRepository<Servicos, Long> {
	
	@Query("select s from Servicos s where upper(trim(nome)) like %?1%")
	List<Servicos> buscaServicoPorNome(String nome);	

}
