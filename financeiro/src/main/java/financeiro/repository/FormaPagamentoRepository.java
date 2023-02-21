package financeiro.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiro.model.FormaPagamento;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{
	
	default Page<FormaPagamento> findByNomePage(String nome, Pageable pageable){    	
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setNome(nome);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<FormaPagamento> example = Example.of(formaPagamento, ignoringExampleMatcher);
		
		Page<FormaPagamento> formaPagamentos = findAll(example, pageable);
		
		return formaPagamentos;
	}
	
	
}
