package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeCoberturaConfigurada;
import com.qbe.cotizador.model.PymeCoberturaTasa;
import com.qbe.cotizador.model.PymeConfiguracionCobertura;

public class PymeConfiguracionCoberturaDAO extends EntityManagerFactoryDAO<PymeConfiguracionCobertura>{
	
	/*public PymeConfiguracionCobertura crear(PymeConfiguracionCobertura objeto) {
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

	public PymeConfiguracionCobertura editar(PymeConfiguracionCobertura objeto) {
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
	
	
	public PymeConfiguracionCobertura eliminar(PymeConfiguracionCobertura objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			PymeConfiguracionCobertura configuracionCoberturaToBeRemoved = em.getReference(PymeConfiguracionCobertura.class, objeto.getConfiguracionCoberturaId());
			em.remove(configuracionCoberturaToBeRemoved);
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
	
	public PymeConfiguracionCoberturaDAO() {
		super(PymeConfiguracionCobertura.class);
	}
	
	public List<PymeConfiguracionCobertura> buscarTodos() {
		return getEntityManager().createNamedQuery("PymeConfiguracionCobertura.buscarTodos").getResultList();
	}
	
	
	public PymeConfiguracionCobertura buscarPorId(BigInteger configuracionCoberturaId){
		PymeConfiguracionCobertura configuracionCobertura = new PymeConfiguracionCobertura();
		List<PymeConfiguracionCobertura> query = getEntityManager().createNamedQuery("PymeConfiguracionCobertura.buscarPorId").setParameter("Id", configuracionCoberturaId).getResultList();
		if(!query.isEmpty())
			configuracionCobertura = query.get(0);
		return configuracionCobertura;
	}

}
