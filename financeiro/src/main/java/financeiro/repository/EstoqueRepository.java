package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Empresa;
import financeiro.model.Estoque;

@Repository
@Transactional
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	
	default Page<Estoque> findByIdProdutoPage(Long id, Pageable pageable){    	
		Estoque estoque = new Estoque();
		estoque.getProduto().setId(id);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Estoque> example = Example.of(estoque, ignoringExampleMatcher);
		
		Page<Estoque> estoques = findAll(example, pageable);
		
		return estoques;
	}
	

}
