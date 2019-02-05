package com.qbe.cotizador.dao.producto.vehiculocerrado;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.ProductoCobertura;

public class ProductoCoberturaDAO extends EntityManagerFactoryDAO<ProductoCobertura>{
	
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

	public ProductoCoberturaDAO() {
	    super(ProductoCobertura.class);
	}
	
	public List<ProductoCobertura> buscarTodos(){
		return getEntityManager().createNamedQuery("ProductoCobertura.buscarTodos").getResultList();
	}
	
	public ProductoCobertura buscarPorId(String id){
		ProductoCobertura producto = new ProductoCobertura();
		List<ProductoCobertura> query = getEntityManager().createNamedQuery("ProductoCobertura.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			producto =  query.get(0);
		return producto;
	}
	
}
