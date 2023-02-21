package financeiro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Fornecedor extends Pessoa {
	
	@OneToMany(mappedBy = "fornecedor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Pedido> pedido;
    
	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	@Override
	public double limite() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
