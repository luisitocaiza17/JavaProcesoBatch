package com.qbe.cotizador.dao.producto.agricola;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.AgriParametroXPuntoVenta;

public class AgriParametroXPuntoVentaDAO extends EntityManagerFactoryDAO<AgriParametroXPuntoVenta> {
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
	public AgriParametroXPuntoVentaDAO (){
		super(AgriParametroXPuntoVenta.class);
	}
	
	public AgriParametroXPuntoVenta buscarPorId(BigInteger ParametroPuntoVentaId)
	{
		AgriParametroXPuntoVenta agriParametro = new AgriParametroXPuntoVenta();
		List<AgriParametroXPuntoVenta> result = getEntityManager().createNamedQuery("AgriParametroXPuntoVenta.obtenerPorId").setParameter("parametroPuntoVentaId", ParametroPuntoVentaId).getResultList();
		if (result.size()>0)
			agriParametro=result.get(0);
		return agriParametro;
	}
	
	public AgriParametroXPuntoVenta buscarPorPuntoVentaId(BigInteger puntoVentaId)
	{
		AgriParametroXPuntoVenta agriParametro = new AgriParametroXPuntoVenta();
		List<AgriParametroXPuntoVenta> result = getEntityManager().createNamedQuery("AgriParametroXPuntoVenta.obtenerPorPuntoVentaId").setParameter("puntoVentaId", puntoVentaId).getResultList();
		if (result.size()>0)
			agriParametro=result.get(0);
		return agriParametro;
	}
	
	public  List<AgriParametroXPuntoVenta> buscarTodos()
	{
		return getEntityManager().createNamedQuery("AgriParametroXPuntoVenta.obtenerTodos").getResultList();
	}
}
