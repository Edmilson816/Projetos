package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	
	default Page<Produto> findByNomePage(String nome, Pageable pageable){    	
		Produto produto = new Produto();
		produto.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Produto> example = Example.of(produto, ignoringExampleMatcher);
		
		Page<Produto> produtos = findAll(example, pageable);
		
		return produtos;
	}
	

}
