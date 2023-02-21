package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Lote;

@Repository
@Transactional
public interface LoteRepository extends JpaRepository<Lote, Long> {

	default Page<Lote> findByNomePage(String nome, Pageable pageable){    	
		Lote lote = new Lote();
		lote.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Lote> example = Example.of(lote, ignoringExampleMatcher);
		
		Page<Lote> lotes = findAll(example, pageable);
		
		return lotes;
	}	
	
}
