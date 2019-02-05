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

public class PymeCoberturaConfiguradaDAO extends EntityManagerFactoryDAO<PymeCoberturaTasa>{
	
	/*public PymeCoberturaTasa crear(PymeCoberturaTasa objeto) {
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
	
	public PymeCoberturaTasa eliminar(PymeCoberturaTasa objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			PymeCoberturaTasa PymeCoberturaTasaToBeRemoved = em.getReference(PymeCoberturaTasa.class, objeto.getCoberturaTasaId());
			em.remove(PymeCoberturaTasaToBeRemoved);
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
	
	public PymeCoberturaConfiguradaDAO() {
		super(PymeCoberturaTasa.class);
	}
	
	public List<PymeCoberturaConfigurada> buscarTodos() {
		return getEntityManager().createNamedQuery("PymeCoberturaConfigurada.buscarTodos").getResultList();
	}
	
	public List<PymeCoberturaConfigurada> buscarPorGrupoPorProductoId(BigInteger grupoPorProductoId) {
		return getEntityManager().createNamedQuery("PymeCoberturaConfigurada.buscarPorGrupoPorProductoId", PymeCoberturaConfigurada.class).setParameter("grupoPorProductoId", grupoPorProductoId).getResultList();
	}
}
