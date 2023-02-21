package financeiro.model;

public enum TipoPessoa {

	JURIDICA("Jurídica"),
	FISICA("Física");
	
	private String nome;
	private String valor;

	private TipoPessoa(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getValor() {
		return valor = this.name();
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}	
	

}
