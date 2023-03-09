package financeiro.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pedido {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@Column(name = "numero_pedido", length = 20)	
	private String numero_pedido;
	
	
	@ManyToOne
	private Status status;
	
	
	@ManyToOne
	private Fornecedor fornecedor;
	
	
	@ManyToOne
	private Cliente cliente;
	
	
	@ManyToOne
	private Empresa empresa;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<PedidoItem> pedidoItem;
	
	
	@ManyToOne
	private FormaPagamento formaPagamento;
	
	@NotNull(message = "Campo: Data do Pedido, deve ser preenchio!")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "data_pedido")
	private Date data_pedido;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "data_previsaoEntrega")
	private Date data_previsaoEntrega;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "data_entregaRealizada")
	private Date data_entregaRealizada;
	
	@Column(name = "total_pedido")
	private BigDecimal total_pedido;
	
	@Column(name = "total_geral")
	private BigDecimal total_geral;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero_pedido() {
		return numero_pedido;
	}

	public void setNumero_pedido(String numero_pedido) {
		this.numero_pedido = numero_pedido;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(Date data_pedido) {
		this.data_pedido = data_pedido;
	}

	public Date getData_previsaoEntrega() {
		return data_previsaoEntrega;
	}

	public void setData_previsaoEntrega(Date data_previsaoEntrega) {
		this.data_previsaoEntrega = data_previsaoEntrega;
	}

	public Date getData_entregaRealizada() {
		return data_entregaRealizada;
	}

	public void setData_entregaRealizada(Date data_entregaRealizada) {
		this.data_entregaRealizada = data_entregaRealizada;
	}

	public BigDecimal getTotal_pedido() {
		return total_pedido;
	}

	public void setTotal_pedido(BigDecimal total_pedido) {
		this.total_pedido = total_pedido;
	}

	public BigDecimal getTotal_geral() {
		return total_geral;
	}

	public void setTotal_geral(BigDecimal total_geral) {
		this.total_geral = total_geral;
	}

	public List<PedidoItem> getPedidoItem() {
		return pedidoItem;
	}

	public void setPedidoItem(List<PedidoItem> pedidoItem) {
		this.pedidoItem = pedidoItem;
	}
	
	
	
	
	
}
