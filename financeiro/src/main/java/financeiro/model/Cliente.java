package financeiro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Lob
	private byte[] documento;

	@Column(name="nomeFiledocumento", length = 120)
	private String nomeFiledocumento;
	
	@Column(name="tipoFiledocumento", length = 50)
	private String tipoFiledocumento;	
	
	@OneToMany(mappedBy = "cliente", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Pedido> pedido;
	
	@Override
	public double limite() {
		// TODO Auto-generated method stub
		return 0;
	}	

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getNomeFiledocumento() {
		return nomeFiledocumento;
	}

	public void setNomeFiledocumento(String nomeFiledocumento) {
		this.nomeFiledocumento = nomeFiledocumento;
	}
	
	public String getTipoFiledocumento() {
		return tipoFiledocumento;
	}

	public void setTipoFiledocumento(String tipoFiledocumento) {
		this.tipoFiledocumento = tipoFiledocumento;
	}

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	
	
}
