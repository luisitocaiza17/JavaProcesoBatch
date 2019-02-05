package com.qbe.cotizador.dao.producto.agricola;

import java.math.BigInteger;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.AgriCiclo;
import com.qbe.cotizador.model.AgriRegla;
import com.qbe.cotizador.model.AgriTipoCalculo;
import com.qbe.cotizador.model.AgriTipoCultivo;

public class AgriReglaDAO extends EntityManagerFactoryDAO<AgriRegla> {
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

	public AgriReglaDAO() {
		// TODO Auto-generated constructor stub
		super(AgriRegla.class);
	}
	public AgriRegla BuscarPorId (BigInteger ReglaId){
		AgriRegla agriRegla = new AgriRegla();
		List<AgriRegla> result = getEntityManager().createNamedQuery("AgriRegla.obtenerPorId").setParameter("reglaId", ReglaId).getResultList();
		if (result.size()>0)
			agriRegla = result.get(0);
		return agriRegla;
	}
	public List<AgriRegla>BuscarTodos(){
		return getEntityManager().createNamedQuery("AgriRegla.findAll").getResultList();
	}
	
	public List<AgriRegla> BuscarPorParametros(BigInteger provinciaId, BigInteger cantonId, BigInteger tipoCultivoId){
		return getEntityManager().createNamedQuery("AgriRegla.obtenerPorParametros")
				.setParameter("provinciaId", provinciaId)
				.setParameter("cantonId", cantonId)
				.setParameter("tipoCultivoId", tipoCultivoId).getResultList();
	}

}
