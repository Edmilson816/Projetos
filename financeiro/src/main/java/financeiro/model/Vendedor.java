package financeiro.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Vendedor extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="percentualComissao")
	public double percentualComissao;
	
	@Column(name="valorMeta")	
	public double valorMeta;

	public double getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(double percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public double getValorMeta() {
		return valorMeta;
	}

	public void setValorMeta(double valorMeta) {
		this.valorMeta = valorMeta;
	}

	@Override
	public double limite() {
		// TODO Auto-generated method stub
		return 0;
	}

}
