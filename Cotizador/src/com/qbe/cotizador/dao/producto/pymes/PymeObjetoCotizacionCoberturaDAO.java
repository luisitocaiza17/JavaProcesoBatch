package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeCobertura;
import com.qbe.cotizador.model.PymeObjetoCotizacionCobertura;

public class PymeObjetoCotizacionCoberturaDAO extends EntityManagerFactoryDAO<PymeObjetoCotizacionCobertura>{
	/*public PymeObjetoCotizacionCobertura crear(PymeObjetoCotizacionCobertura objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(objeto);
			em.flush();
			em.getTransaction();
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
            System.out.println(e.getMessage());
			return objeto;
        }finally{         
	        em.close();
        }
	}

	public PymeObjetoCotizacionCobertura editar(PymeObjetoCotizacionCobertura objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();         
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
            System.out.println(e.getMessage());
			return objeto;
        }finally{         
	        em.close();
        }
	}
	
	
	public PymeObjetoCotizacionCobertura eliminar(PymeObjetoCotizacionCobertura objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			PymeObjetoCotizacionCobertura PymeCoberturaToBeRemoved = em.getReference(PymeObjetoCotizacionCobertura.class, objeto.getObjetoPymesCoberturaId());
			em.remove(PymeCoberturaToBeRemoved);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
            System.out.println(e.getMessage());
			return objeto;
        }finally{         
	        em.close();
        }
	}*/
	
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
	
	public PymeObjetoCotizacionCoberturaDAO() {
		super(PymeObjetoCotizacionCobertura.class);
	}
	
	public List<PymeObjetoCotizacionCobertura> buscarTodos() {
		return getEntityManager().createNamedQuery("PymeObjetoCotizacionCobertura.buscarTodos").getResultList();
	}
	
	public PymeObjetoCotizacionCobertura buscarPorId(BigInteger id){
		PymeObjetoCotizacionCobertura pymeObjetoCotizacionCobertura = new PymeObjetoCotizacionCobertura();
		List <PymeObjetoCotizacionCobertura>results = getEntityManager().createNamedQuery("PymeObjetoCotizacionCobertura.buscarPorId").setParameter("id", id).getResultList();
		if(!results.isEmpty())
			pymeObjetoCotizacionCobertura = results.get(0);
		return pymeObjetoCotizacionCobertura;
	}
	
	public List<PymeObjetoCotizacionCobertura> buscarPorObjetoPymeId(BigInteger id){
		return getEntityManager().createNamedQuery("PymeObjetoCotizacionCobertura.buscarPorObjetoPymeId").setParameter("id", id).getResultList();
	}
	
}
