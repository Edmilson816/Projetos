package financeiro.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Estoque {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne
	private Empresa empresa;
	
	@ManyToOne
	private Lote lote;
	
	@Column(name = "saldo_fisico")
	private BigDecimal saldo_fisico;
	
	@Column(name = "saldo_reservado")
	private BigDecimal saldo_reservado;

	@Column(name = "saldo_disponivel")
	private BigDecimal saldo_disponivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public BigDecimal getSaldo_fisico() {
		return saldo_fisico;
	}

	public void setSaldo_fisico(BigDecimal saldo_fisico) {
		this.saldo_fisico = saldo_fisico;
	}

	public BigDecimal getSaldo_reservado() {
		return saldo_reservado;
	}

	public void setSaldo_reservado(BigDecimal saldo_reservado) {
		this.saldo_reservado = saldo_reservado;
	}

	public BigDecimal getSaldo_disponivel() {
		return saldo_disponivel;
	}

	public void setSaldo_disponivel(BigDecimal saldo_disponivel) {
		this.saldo_disponivel = saldo_disponivel;
	}
	
		

}
