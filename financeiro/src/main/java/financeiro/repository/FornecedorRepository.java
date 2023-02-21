package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import financeiro.model.Fornecedor;

@Repository
@Transactional
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

	
	default Page<Fornecedor> findByNomePage(String nome, Pageable pageable){    	
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Fornecedor> example = Example.of(fornecedor, ignoringExampleMatcher);
		
		Page<Fornecedor> fornecedores = findAll(example, pageable);
		
		return fornecedores;
	}
	
	default Page<Fornecedor> findByNomeInscricaoPage(String nome, String inscricao, Pageable pageable){
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(nome);
		fornecedor.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("inscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Fornecedor> example = Example.of(fornecedor, ignoringExampleMatcher);
		
		Page<Fornecedor> fornecedores = findAll(example, pageable);
		
		return fornecedores;
		
		
		
	}

}
