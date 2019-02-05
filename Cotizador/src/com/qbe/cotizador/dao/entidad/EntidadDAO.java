package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Entidad;

public class EntidadDAO extends EntityManagerFactoryDAO <Entidad>{

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
	
	public EntidadDAO() {
        super(Entidad.class);
    }
	
	public List<Entidad> buscarTodos(){ 
		return getEntityManager().createNamedQuery("Entidad.buscarTodos").getResultList();
	}
		
	public Entidad buscarPorId(String id){
		Entidad entidad = new Entidad();
		List<Entidad> query = getEntityManager().createNamedQuery("Entidad.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			entidad =  query.get(0);
		return entidad;
	}
	
//	public List<Entidad> buscarActivos(){				
//		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
//		List<Entidad> results = null;
//		try{	
//			TypedQuery<Entidad> query = em.createQuery("SELECT c FROM Entidad c WHERE c.activo =:valorActivo", Entidad.class).setParameter("valorActivo", true);
//			results = query.getResultList(); 
//		}catch(Exception e) { 
//			em.getTransaction().rollback();           
//		}finally{         
//			em.close();			
//		}
//		return results;
//	}		
	
	public int buscarPorIdentificacion(String identificacion){
		int contador = 0;
		List<Entidad> query = getEntityManager().createNamedQuery("Entidad.buscarPorIdentificacion").setParameter("identificacion", identificacion).getResultList();
		if(query.isEmpty())
			contador = 0;
		else
			contador = query.size();
		return contador;		
	}	
	
	public Entidad buscarEntidadPorIdentificacion(String identificacion){
		Entidad entidad = new Entidad();
		List<Entidad> query = getEntityManager().createNamedQuery("Entidad.buscarEntidadPorIdentificacion").setParameter("identificacion", identificacion).getResultList();
		if(!query.isEmpty())
			entidad =  query.get(0);
		return entidad;		
	}	
	
	public Entidad buscarEntidadPorMail(String mail){
		Entidad entidad = new Entidad();
		List<Entidad> query = getEntityManager().createNamedQuery("Entidad.buscarEntidadPorMail").setParameter("mail", mail).getResultList();
		if(!query.isEmpty())
			entidad =  query.get(0);			
		return entidad;		
	}
	
	public Entidad buscarEntidadPorIdEnsurance(String idEnsurance){
		Entidad entidad = new Entidad();
		List<Entidad> query = getEntityManager().createNamedQuery("Entidad.buscarEntidadPorIdEnsurance").setParameter("idEnsurance", idEnsurance).getResultList();
		if(!query.isEmpty())
			entidad =  query.get(0);
		return entidad;			
	}	
	
}
