package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Transportadora;

@Repository
@Transactional
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long>{
	
	default Page<Transportadora> findByNomePage(String nome, Pageable pageable) {
		Transportadora transportadora = new Transportadora();
		
		transportadora.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Transportadora> example = Example.of(transportadora, ignoringExampleMatcher);
		
		Page<Transportadora> transportadoras = findAll(example, pageable); 
		
		return transportadoras;		
	}
	
	default Page<Transportadora> findByNomeInscricaoPage (String nome, String inscricao, Pageable pageable) {
		Transportadora transportadora = new Transportadora();
		
		transportadora.setNome(nome);
		transportadora.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("inscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Transportadora> example = Example.of(transportadora, ignoringExampleMatcher);
		
		Page<Transportadora> transportadoras = findAll(example, pageable);
		
		return transportadoras;
		
	} 

}
