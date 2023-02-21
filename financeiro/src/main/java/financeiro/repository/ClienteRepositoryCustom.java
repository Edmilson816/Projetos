package financeiro.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import financeiro.model.Cliente;

@Repository
public class ClienteRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
    public List<Cliente> customListaClienteRepository(String nome, String inscricao){
    	
    	String consulta_cliente = " select p from Pessoa p ";
    	String condicao = null;
    	String orderBy = " nome ";
    	
    	if (nome != null) {
    		condicao += " where ";
    		condicao += " nome = :nome ";
    	} 
    	
    	if (condicao != null) {
    		condicao += " and ";
    	}else {
    		condicao += " where ";
    	}
    	
    	if (inscricao != null) {
    		condicao += "inscricao = :inscricao";    		
    	}
    	
    	StringBuilder query = new StringBuilder(consulta_cliente);

    	condicao += orderBy;
		query.append(condicao);  	
    	
    	
    	TypedQuery<Cliente> typedQuery = em.createQuery(query.toString(), Cliente.class);
    	
    	if (nome != null) {
    		typedQuery.setParameter("nome", nome);
    	}
    	
    	if (inscricao != null) {
    		typedQuery.setParameter("inscricao", inscricao);
    	}
    	
    	return typedQuery.getResultList();
    	
    	
    }
    

}
