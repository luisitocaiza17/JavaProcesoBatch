package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeCoberturaCotizacionValor;;

public class PymeCoberturaCotizacionValorDAO extends EntityManagerFactoryDAO<PymeCoberturaCotizacionValor>{
	
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
	
	public PymeCoberturaCotizacionValorDAO(){
		super(PymeCoberturaCotizacionValor.class);
	}
	
	public List<PymeCoberturaCotizacionValor> buscarCoberturaCotizacionValorPorGrupoCoberturaId(BigInteger grupoCoberturaId, BigInteger cotizacionId) {
		return getEntityManager().createQuery("SELECT c FROM PymeCoberturaCotizacionValor c where c.id=:grupoCoberturaId and c.cotizacionId=:cotizacionId").setParameter("grupoCoberturaId", grupoCoberturaId).setParameter("cotizacionId", cotizacionId).getResultList();
	}

}
