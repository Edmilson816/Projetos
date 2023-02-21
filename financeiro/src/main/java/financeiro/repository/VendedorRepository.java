package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Vendedor;

@Repository
@Transactional
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
	
	default Page<Vendedor> findByNomeVendedor(String nome, Pageable pageable){
		
		Vendedor vendedor = new Vendedor();
		vendedor.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Vendedor> example = Example.of(vendedor, ignoringExampleMatcher);
		
	    Page<Vendedor> vendedores = findAll(example, pageable);
		
		return vendedores;
		
	}
	
	default Page<Vendedor> findByNomeVendedorInscricao(String nome, String inscricao, Pageable pageable){
		
		Vendedor vendedor = new Vendedor();
		vendedor.setNome(nome);
		vendedor.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("inscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Vendedor> example = Example.of(vendedor, ignoringExampleMatcher);
		
	    Page<Vendedor> vendedores = findAll(example, pageable);
		
		return vendedores;
		
	}

}
