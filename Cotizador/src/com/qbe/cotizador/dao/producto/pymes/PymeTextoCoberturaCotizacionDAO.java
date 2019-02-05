package com.qbe.cotizador.dao.producto.pymes;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.PymeTextoGrupoCoberturaCotizacion;

public class PymeTextoCoberturaCotizacionDAO extends EntityManagerFactoryDAO<PymeTextoGrupoCoberturaCotizacion>{
	
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
	
	public PymeTextoCoberturaCotizacionDAO(){
		super(PymeTextoGrupoCoberturaCotizacion.class);
	}
	
	public List<PymeTextoGrupoCoberturaCotizacion> buscarTextoCoberturaCotizacionPorCotizacionId(BigInteger cotizacionId) {
		return getEntityManager().createQuery("SELECT c FROM PymeTextoGrupoCoberturaCotizacion c where c.cotizacionId=:objetoId").setParameter("objetoId", cotizacionId).getResultList();
	}

}
