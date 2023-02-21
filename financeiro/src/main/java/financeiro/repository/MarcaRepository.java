package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Marca;


@Repository
@Transactional
public interface MarcaRepository extends JpaRepository<Marca, Long> {
	
	default Page<Marca> findByNomePage(String nome, Pageable pageable){    	
		Marca marca = new Marca();
		marca.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Marca> example = Example.of(marca, ignoringExampleMatcher);
		
		Page<Marca> marcas = findAll(example, pageable);
		
		return marcas;
	}
	

}
