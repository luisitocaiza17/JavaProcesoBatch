package com.qbe.cotizador.dao.producto.agricola;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.AgriTipoCultivo;

public class AgriTipoCultivoDAO extends EntityManagerFactoryDAO<AgriTipoCultivo>  {
	
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
	
	public AgriTipoCultivoDAO()	{
		super(AgriTipoCultivo.class);
	}

	public AgriTipoCultivo BuscarPorId(BigInteger TipoCultivoId)
{
	AgriTipoCultivo agriTipoCultivo = new AgriTipoCultivo();
	List<AgriTipoCultivo> result = getEntityManager().createNamedQuery("AgriTipoCultivo.buscarPorId").setParameter("tipoCultivoId", TipoCultivoId).getResultList();
	if (result.size()>0)
		agriTipoCultivo=result.get(0);
	return agriTipoCultivo;
	}
	public  List<AgriTipoCultivo>BuscarTodos()
	{
		return getEntityManager().createNamedQuery("AgriTipoCultivo.findAll").getResultList();
	}
}
