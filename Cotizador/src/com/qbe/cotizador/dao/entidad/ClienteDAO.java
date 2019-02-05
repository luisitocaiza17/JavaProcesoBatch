package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Cliente;
import com.qbe.cotizador.model.Entidad;

public class ClienteDAO extends EntityManagerFactoryDAO<Cliente>{
	
	@PersistenceContext(name="CotizadorWebPC", unitName = "CotizadorWebPU" )	
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		if(em == null){
			Context initCtx = null;
			try {
				initCtx = new InitialContext();
				em = (javax.persistence.EntityManager) initCtx.lookup("java:comp/env/CotizadorWebPC");
			} catch (NamingException e) { 
				e.printStackTrace();
			}		
		}
		return em;
	}
	
	public ClienteDAO() {
        super(Cliente.class);
    }
	
	public List<Cliente> buscarTodos(){
		return getEntityManager().createNamedQuery("Cliente.buscarTodos").getResultList();		
	}
		
	public Cliente buscarPorId(String id){
		Cliente cliente = new Cliente();
		List<Cliente> query = getEntityManager().createNamedQuery("Cliente.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			cliente =  query.get(0);
		return cliente;		
	}
	public Cliente buscarPorEntidadId(Entidad entidad){
		Cliente cliente = new Cliente();
		List<Cliente> query = getEntityManager().createNamedQuery("Cliente.buscarPorEntidadId").setParameter("entidad", entidad).getResultList();
		if(!query.isEmpty())
			cliente =  query.get(0);
		return cliente;		
	}
	
	public List<Cliente> buscarActivos(){
		return getEntityManager().createNamedQuery("Cliente.buscarActivos").setParameter("valorActivo", true).getResultList();		
	}		
}
