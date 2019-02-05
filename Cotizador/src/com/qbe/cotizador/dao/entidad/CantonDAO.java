package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Canton;
import com.qbe.cotizador.model.Provincia;

public class CantonDAO extends EntityManagerFactoryDAO<Canton>{
	
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
	
	public CantonDAO() {
        super(Canton.class);
    }
	
	public List<Canton> buscarTodos(){
		return getEntityManager().createNamedQuery("Canton.buscarTodos").getResultList();		
	}
		
	public Canton buscarPorId(String id){
		Canton canton = new Canton();
		List<Canton> query = getEntityManager().createNamedQuery("Canton.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			canton =  query.get(0);
		return canton;		
	}
	
	public List<Canton> buscarPorProvincia(Provincia provincia){
		return getEntityManager().createNamedQuery("Canton.buscarPorProvincia").setParameter("provincia", provincia).getResultList();		
	}
}