package com.qbe.cotizador.dao.cotizacion;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Cobertura;
import com.qbe.cotizador.model.CoberturasConjunto;

public class CoberturasConjuntoDAO extends EntityManagerFactoryDAO<CoberturasConjunto>{
	
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
	
	public CoberturasConjuntoDAO() {
        super(CoberturasConjunto.class);
    }
	
	
	public List<CoberturasConjunto> buscarTodos(){
		return getEntityManager().createNamedQuery("CoberturasConjunto.buscarTodos").getResultList();
	}
	
	
	public CoberturasConjunto buscarPorId(String id){
		CoberturasConjunto cobertura = new CoberturasConjunto();
		List<CoberturasConjunto> query = getEntityManager().createNamedQuery("CoberturasConjunto.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			cobertura =  query.get(0);			
		return cobertura;
	}
	
	public CoberturasConjunto buscarPorCobertura(Cobertura cobertura){
		CoberturasConjunto coberturaConjunto = new CoberturasConjunto();
		List<CoberturasConjunto> query = getEntityManager().createNamedQuery("CoberturasConjunto.buscarPorCobertura").setParameter("cobertura", cobertura).getResultList();
		if(!query.isEmpty())
			coberturaConjunto =  query.get(0);
		return coberturaConjunto;
	}
	
//	public List<CoberturasConjunto> buscarActivos(){
//		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
//		List<CoberturasConjunto> results = null;
//		try{	
//			TypedQuery<CoberturasConjunto> query = em.createQuery("SELECT c FROM CoberturasConjunto c WHERE c.activo =:valorActivo", CoberturasConjunto.class).setParameter("valorActivo", true);
//			results = query.getResultList(); 
//		}catch(Exception e) { 
//			em.getTransaction().rollback();           
//		}finally{         
//			em.close();			
//		}
//		return results;
//	}
}
