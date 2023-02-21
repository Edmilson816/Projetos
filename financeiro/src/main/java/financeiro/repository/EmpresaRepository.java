package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import financeiro.model.Empresa;

@Repository
@Transactional
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	default Page<Empresa> findByNomePage(String nome, Pageable pageable){    	
		Empresa empresa = new Empresa();
		empresa.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Empresa> example = Example.of(empresa, ignoringExampleMatcher);
		
		Page<Empresa> empresas = findAll(example, pageable);
		
		return empresas;
	}
	
	default Page<Empresa> findByInscricaoPage(String inscricao, Pageable pageable){    	
		Empresa empresa = new Empresa();
		empresa.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("inscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Empresa> example = Example.of(empresa, ignoringExampleMatcher);
		
		Page<Empresa> empresas = findAll(example, pageable);
		
		return empresas;
	}
	
	default Page<Empresa> findByNomeInscricaoPage(String nome, String inscricao, Pageable pageable){    	
		Empresa empresa = new Empresa();
		empresa.setNome(nome);
		empresa.setInscricao(inscricao);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("inscricao", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Empresa> example = Example.of(empresa, ignoringExampleMatcher);
		
		Page<Empresa> empresas = findAll(example, pageable);
		
		return empresas;
	}	
	

}
