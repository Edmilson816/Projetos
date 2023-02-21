package financeiro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Empresa extends Pessoa {

	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Estoque> estoque;
	
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Pedido> pedido;
	
	@NotBlank(message = "Nome da Empresa: é obrigatorio!")
	@Column
	private String nome;
	
	@Column
	private String inscricao;
	
	@Column
	private String inscricaoEstadual;
	
	@Column(length = 80)
	private String endereco;
	
	@Column(length = 80)
	private String complemento;	
	
	@Column(length = 80)
	private String bairro;
	
	@Column(length = 10)
	private String cep;
	
	@Column(length = 80)
	private String municipio;	
	
	@Column(length = 2) 
	private String uf;	
	
	public List<Estoque> getEstoque() {
		return estoque;
	}

	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}	
    
	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public double limite() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
