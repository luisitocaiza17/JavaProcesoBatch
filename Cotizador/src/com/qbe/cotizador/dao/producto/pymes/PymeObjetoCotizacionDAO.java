package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeObjetoCotizacion;

public class PymeObjetoCotizacionDAO extends EntityManagerFactoryDAO<PymeObjetoCotizacion>{

	/*public PymeObjetoCotizacion crear(PymeObjetoCotizacion objeto){
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try{
			em.getTransaction().begin();
			em.persist(objeto); 
			em.flush();
			em.getTransaction().commit();  

			return objeto;					
		}catch(Exception e) { 
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		}finally{         
			em.close();        
		}
	} 

	public PymeObjetoCotizacion editar(PymeObjetoCotizacion objeto){
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try{			  
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();         
			return objeto;					
		}catch(Exception e) { 
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		}finally{         
			em.close();
		}
	} 

	public PymeObjetoCotizacion eliminar(PymeObjetoCotizacion objeto){
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try{
			em.getTransaction().begin();
			PymeObjetoCotizacion ObjetoPymeToBeRemoved = em.getReference(PymeObjetoCotizacion.class, objeto.getObjetoPymesId());
			em.remove(ObjetoPymeToBeRemoved);
			em.getTransaction().commit();      
			return objeto;					
		}catch(Exception e) { 
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		}finally{         
			em.close();
		}
	} */

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
	
	public PymeObjetoCotizacionDAO() {
		super(PymeObjetoCotizacion.class);
	}
	
	public List<PymeObjetoCotizacion> buscarTodos(){   
		return getEntityManager().createNamedQuery("PymeObjetoCotizacion.buscarTodos", PymeObjetoCotizacion.class).getResultList();
	}


	public PymeObjetoCotizacion buscarPorId(BigInteger id){
		PymeObjetoCotizacion objetoCotizacion = new PymeObjetoCotizacion();
		List <PymeObjetoCotizacion>results = getEntityManager().createNamedQuery("PymeObjetoCotizacion.buscarPorId", PymeObjetoCotizacion.class).setParameter("id", id).getResultList();
		if(results.size()>0)
			objetoCotizacion = results.get(0);
		return objetoCotizacion;
	}
}
