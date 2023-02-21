package financeiro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {
	
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@NotBlank
	@Column(name = "nome", length = 120, nullable = false)
	private String nome;
	
	@Column(name = "descricao", length = 200)
	private String descricao;
	
	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL)	
	private List<Estoque> estoque;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Estoque> getEstoque() {
		return estoque;
	}

	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}
	
	

}
