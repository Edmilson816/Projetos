package financeiro.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class PedidoItem {
	
	@Id
	@Column(name = "id")
	private long id;
	
	@NotBlank
	@ManyToOne
	private Pedido pedido;
	
    @ManyToOne
	private Empresa empresa;
	
    @ManyToOne
	private Produto produto;
	
	@Column(name = "quantidade_item")
	private BigDecimal quantidade_item;
	
	@Column(name = "quantidade_entrega")
	private BigDecimal quantidade_entrega; 
	
	@Column(name = "valor_unitario")
	private BigDecimal valor_unitario;
	
	@Column(name = "valor_total")
	private BigDecimal valor_total;
	
	@Column(name = "data_EntregaItem")
	private Date dataEntregaItem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade_item() {
		return quantidade_item;
	}

	public void setQuantidade_item(BigDecimal quantidade_item) {
		this.quantidade_item = quantidade_item;
	}

	public BigDecimal getQuantidade_entrega() {
		return quantidade_entrega;
	}

	public void setQuantidade_entrega(BigDecimal quantidade_entrega) {
		this.quantidade_entrega = quantidade_entrega;
	}

	public BigDecimal getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(BigDecimal valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public BigDecimal getValor_total() {
		return valor_total;
	}

	public void setValor_total(BigDecimal valor_total) {
		this.valor_total = valor_total;
	}

	public Date getDataEntregaItem() {
		return dataEntregaItem;
	}

	public void setDataEntregaItem(Date dataEntregaItem) {
		this.dataEntregaItem = dataEntregaItem;
	}
	
	
}
