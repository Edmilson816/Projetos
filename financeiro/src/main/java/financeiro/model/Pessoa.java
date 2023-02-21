package financeiro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public abstract class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
	//@Size(min = 2, max = 150)/*Tem de estar entre 2 e 50 o valor digitado.*/
	@NotNull(message = "Campo: Nome, deve ser preenchido")
	@NotBlank(message = "Campo: Nome, deve ser preenchido")
	@Column(name="nome", length = 150/*, nullable = false*/)
	protected String nome;

	//@Size(min = 11, max = 14)
	@NotNull(message = "Campo: Inscrição, deve ser preenchido")
	@NotBlank(message = "Campo: Inscrição, deve ser preenchido")
	@Column(name="inscricao", length = 22/*, nullable = false*/)
	protected String inscricao;//cpf ou cpnj	
	
	
	@Column(name="cep", length = 11, nullable = true)
	protected String cep;
	
	@Column(name="rua", length = 150, nullable = true)
	protected String rua;
	
	@Column(name="bairro", length = 80, nullable = true)
	protected String bairro;
	
	@Column(name="cidade", length = 80, nullable = true)
	protected String cidade;
	
	@Column(name="uf", length = 30, nullable = true)
	protected String uf;
	
	@Column(name="ibge", length = 12, nullable = true)
	protected String ibge;

	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa; //informa se é Fisica ou Juridica
	
	public abstract double limite(); //Este é um metodo que passa a ser obrigatorio nas classes filhas deste objeto
	/*forma que deve ser implementado na classe filha
	@Override
	public double limite(){
	  return 1000.00;
	}
	*/
	
	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	
    
}
