package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Ciudad;
import com.qbe.cotizador.model.Provincia;

public class CiudadDAO extends EntityManagerFactoryDAO<Ciudad>{
	
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
	
	public CiudadDAO() {
        super(Ciudad.class);
    } 
	
	public List<Ciudad> buscarTodos(){
		return getEntityManager().createNamedQuery("Ciudad.buscarTodos").getResultList();		
	}
		
	public Ciudad buscarPorId(String id){
		Ciudad ciudad = new Ciudad();
		List<Ciudad> query = getEntityManager().createNamedQuery("Ciudad.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			ciudad =  query.get(0);
		return ciudad;		
	}
	
	public List<Ciudad> buscarPorProvincia(Provincia provincia){
		return getEntityManager().createNamedQuery("Ciudad.buscarPorProvincia").setParameter("provincia", provincia).getResultList();		
	}
}