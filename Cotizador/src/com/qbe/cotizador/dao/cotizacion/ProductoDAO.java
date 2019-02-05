package com.qbe.cotizador.dao.cotizacion;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Producto;

public class ProductoDAO extends EntityManagerFactoryDAO<Producto>{

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
	
	public ProductoDAO() {
		super(Producto.class);
	}
	
	public List<Producto> buscarTodos(){
		return getEntityManager().createNamedQuery("Producto.buscarTodos").getResultList();
	}
		
	public Producto buscarPorId(String id){
		Producto producto = new Producto();
		List<Producto> query = getEntityManager().createNamedQuery("Producto.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			producto =  query.get(0);
		return producto;		
	}
	
	public List<Producto> buscarActivos(){
		return getEntityManager().createNamedQuery("Producto.buscarActivos").setParameter("valorActivo", true).getResultList();		
	}

	public Producto buscarPorNemotecnico(String nemotecnico){
		Producto producto = new Producto();
		List<Producto> query = getEntityManager().createNamedQuery("Producto.buscarPorNemotecnico").setParameter("nemotecnico", nemotecnico).getResultList();
		if(!query.isEmpty())
			producto =  query.get(0);
		return producto;		
	}
	
	public Producto buscarPorNombre(String nombre){
		Producto producto = new Producto();
		List<Producto> query = getEntityManager().createNamedQuery("Producto.buscarPorNombre").setParameter("nombre", nombre).getResultList();
		if(!query.isEmpty())
			producto =  query.get(0);
		return producto;		
	}
	
	
	public List<Producto> buscarProductosPorNemotecnico(String nemotecnico){	
		return getEntityManager().createNamedQuery("Producto.buscarPorNemotecnico").setParameter("nemotecnico", nemotecnico).getResultList();	
	}	
}
