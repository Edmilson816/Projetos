package financeiro.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import financeiro.model.Cliente;
import financeiro.model.PedidoItem;

@Repository
@Transactional
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long>{

	@Query("select p from PedidoItem p where p.pedido.id = ?1 ") 
	List<PedidoItem> findByPedidoItem(Long id);
	
	
}
