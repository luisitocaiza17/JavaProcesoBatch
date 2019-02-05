package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Agente;
import com.qbe.cotizador.model.Entidad;

public class AgenteDAO extends EntityManagerFactoryDAO<Agente>{
	
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
	
	public AgenteDAO() {
        super(Agente.class);
    }
	
	public List<Agente> buscarTodos(){
		return getEntityManager().createNamedQuery("Agente.buscarTodos").getResultList();		
	}
		
	public Agente buscarPorId(String id){
		Agente agente = new Agente();
		List<Agente> query = getEntityManager().createNamedQuery("Agente.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			agente =  query.get(0);
		return agente;		
	}
	
	public Agente buscarPorEntidadId(Entidad entidad){
		Agente agente = new Agente();
		List<Agente> query = getEntityManager().createNamedQuery("Agente.buscarPorEntidadId").setParameter("entidad", entidad).getResultList();
		if(!query.isEmpty())
			agente =  query.get(0);
		return agente;		
	}
	
	public List<Agente> buscarActivos(){
		return getEntityManager().createNamedQuery("Agente.buscarActivos").setParameter("valorActivo", true).getResultList();
	}

}
