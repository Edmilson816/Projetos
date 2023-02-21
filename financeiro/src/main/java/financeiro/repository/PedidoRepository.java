package financeiro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import financeiro.model.Cliente;
import financeiro.model.Pedido;

@Repository
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
		
	default Page<Pedido> findByNumeroPage(String numeroPedido, Pageable pageable) {
		
		Pedido pedido = new Pedido();
		pedido.setNumero_pedido(numeroPedido);
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("numero_pedido", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<Pedido> example = Example.of(pedido, ignoringExampleMatcher);    
		
		Page<Pedido> pedidos = findAll(example, pageable);
		
		return pedidos;
		
	}	
	
	
}
