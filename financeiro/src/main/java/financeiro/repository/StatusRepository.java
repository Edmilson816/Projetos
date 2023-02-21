package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.Status;

@Transactional
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
	
	default Page<Status> findByNomePage(String nome, Pageable pageable){    	
		Status status = new Status();
		status.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Status> example = Example.of(status, ignoringExampleMatcher);
		
		Page<Status> stat = findAll(example, pageable);
		
		return stat;
	}

  
}
