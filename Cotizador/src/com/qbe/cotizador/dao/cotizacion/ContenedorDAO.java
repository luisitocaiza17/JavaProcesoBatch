package com.qbe.cotizador.dao.cotizacion;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Contenedor;

public class ContenedorDAO extends EntityManagerFactoryDAO <Contenedor>{
	
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
	
	public ContenedorDAO() {
        super(Contenedor.class);
    }
	
	public List<Contenedor> buscarTodos(){ 
		return getEntityManager().createNamedQuery("Contenedor.buscarTodos").getResultList();
	}
	
	public Contenedor buscarPorId(String id){   
		Contenedor contenedor = new Contenedor();
		List<Contenedor> query = getEntityManager().createNamedQuery("Contenedor.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			contenedor =  query.get(0);
		return contenedor;
	}
	
	public Contenedor buscarPorNumero(String numero){   
		Contenedor contenedor = new Contenedor();
		List<Contenedor> query = getEntityManager().createNamedQuery("Contenedor.buscarPorNumero").setParameter("numero", numero).getResultList();
		if(!query.isEmpty())
			contenedor =  query.get(0);
		return contenedor;
	}
	
	public Contenedor buscarPorEnsuranceId(String ensuranceId){   
		Contenedor contenedor = new Contenedor();
		List<Contenedor> query = getEntityManager().createNamedQuery("Contenedor.buscarPorEnsuranceId").setParameter("ensuranceId", ensuranceId).getResultList();
		if(!query.isEmpty())
			contenedor =  query.get(0);
		return contenedor;
	}
}
