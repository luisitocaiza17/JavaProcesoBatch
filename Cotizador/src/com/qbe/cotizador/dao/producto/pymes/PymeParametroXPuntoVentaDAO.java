package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeParametroXPuntoVenta;

public class PymeParametroXPuntoVentaDAO extends EntityManagerFactoryDAO<PymeParametroXPuntoVenta>{
	
	/*public PymeParametroXPuntoVenta crear(PymeParametroXPuntoVenta objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(objeto);
			em.flush();
			em.getTransaction().commit();
			
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		} finally {
			em.close();
		}		
	}
	
	public PymeParametroXPuntoVenta editar(PymeParametroXPuntoVenta objeto) {
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
		} finally{
			em.close();
		}
	}
	
	public PymeParametroXPuntoVenta eliminar(PymeParametroXPuntoVenta objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			PymeParametroXPuntoVenta parametroPymeXPuntoVentaToBeRemoved = em.getReference(PymeParametroXPuntoVenta.class, objeto.getParametroPuntoVentaId());
			em.remove(parametroPymeXPuntoVentaToBeRemoved);
			em.getTransaction().commit();
			
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;			
		} finally {
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
	
	public PymeParametroXPuntoVentaDAO() {
		super(PymeParametroXPuntoVenta.class);
	}
	
	public List<PymeParametroXPuntoVenta> buscarTodos(){
		return getEntityManager().createNamedQuery("PymeParametroXPuntoVenta.buscarTodos").getResultList();
	}
	
	public PymeParametroXPuntoVenta buscarPorId(BigInteger Id){
		PymeParametroXPuntoVenta parametroPyme = new PymeParametroXPuntoVenta();
		List<PymeParametroXPuntoVenta> results = getEntityManager().createNamedQuery("PymeParametroXPuntoVenta.buscarPorId").setParameter("Id", Id).getResultList();	
		if(results.size()>0)
			parametroPyme = results.get(0);
		return  parametroPyme;
	}
	
	public PymeParametroXPuntoVenta obtenerPorPuntoVentaId(int puntoVentaId){
		PymeParametroXPuntoVenta parametroPyme = new PymeParametroXPuntoVenta();
		List<PymeParametroXPuntoVenta> results = getEntityManager().createNamedQuery("PymeParametroXPuntoVenta.buscarPorPuntoVentaId").setParameter("puntoVentaId", puntoVentaId).getResultList();	
		if(results.size()>0)
			parametroPyme = results.get(0);
		return  parametroPyme;
	}		
}
