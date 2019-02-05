package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeCoberturaTasa;
import com.qbe.cotizador.model.PymeParametroXPuntoVenta;

public class PymeCoberturaTasaDAO extends EntityManagerFactoryDAO<PymeCoberturaTasa>{
	
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
	
	public PymeCoberturaTasaDAO() {
		super(PymeCoberturaTasa.class);
	}
	
	public List<PymeCoberturaTasa> buscarTodos(BigInteger id){
		return getEntityManager().createNamedQuery("PymeCoberturaTasa.buscarTodos").getResultList();
	}
	
	public PymeCoberturaTasa buscarPorId(BigInteger Id){
		PymeCoberturaTasa parametroPyme = new PymeCoberturaTasa();
		List<PymeCoberturaTasa> results = getEntityManager().createNamedQuery("PymeCoberturaTasa.buscarPorId").setParameter("id", Id).getResultList();	
		if(results.size()>0)
			parametroPyme = results.get(0);
		return  parametroPyme;
	}
	
	public List<PymeCoberturaTasa> buscarPorConfiguracionCoberturaId(BigInteger id){
		return getEntityManager().createNamedQuery("PymeCoberturaTasa.buscarPorConfiguracionCoberturaId").setParameter("id", id).getResultList();
	}
}
