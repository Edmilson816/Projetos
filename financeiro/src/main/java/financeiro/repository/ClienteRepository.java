package financeiro.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import financeiro.model.Cliente;


@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("select p from Pessoa p where upper(trim(p.nome)) like %?1% ") 
	List<Cliente> findByNome(String nome);
	
	default Page<Cliente> findByNomePage(String nome, Pageable pageable) {
		
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Cliente> example = Example.of(cliente, ignoringExampleMatcher);    
		
		Page<Cliente> clientes = findAll(example, pageable);
		
		return clientes;
		
	}	
	
	
	default Page<Cliente> findByNomeInscricao(String nome, String inscricao,Pageable pageable) {
		
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("iscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Cliente> example = Example.of(cliente, ignoringExampleMatcher);    
		
		Page<Cliente> clientes = findAll(example, pageable);
		
		return clientes;
		
	}	
	
}
