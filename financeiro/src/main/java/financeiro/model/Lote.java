package financeiro.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Lote {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(name = "nome", length = 60)
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "data_fabricacao")
	private Date data_fabricacao;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "data_vencimento")
    private Date data_vencimento;
	
	@OneToMany(mappedBy = "lote", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Estoque> estoque;

	
	private Empresa empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome_lote) {
		this.nome = nome_lote;
	}

	public Date getData_fabricacao() {
		return data_fabricacao;
	}

	public void setData_fabricacao(Date data_fabricacao) {
		this.data_fabricacao = data_fabricacao;
	}

	public Date getData_vencimento() {
		return data_vencimento;
	}

	public void setData_vencimento(Date data_vencimento) {
		this.data_vencimento = data_vencimento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
	public List<Estoque> getEstoque() {
		return estoque;
	}

	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}
	

}
