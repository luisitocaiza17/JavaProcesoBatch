package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Cargo;
import com.qbe.cotizador.model.Empleado;
import com.qbe.cotizador.model.Entidad;

public class EmpleadoDAO extends EntityManagerFactoryDAO<Empleado>{
	
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
	
	public EmpleadoDAO() {
        super(Empleado.class);
	}
	
	public List<Empleado> buscarTodos(){
		return getEntityManager().createNamedQuery("Empleado.buscarTodos").getResultList();		
	}
		
	public Empleado buscarPorId(String id){
		Empleado empleado = new Empleado();
		List<Empleado> query = getEntityManager().createNamedQuery("Empleado.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			empleado =  query.get(0);
		return empleado;
	}
	public Empleado buscarPorEntidadId(Entidad entidad){
		Empleado empleado = new Empleado();
		List<Empleado> query = getEntityManager().createNamedQuery("Empleado.buscarPorEntidadId").setParameter("entidad", entidad).getResultList();
		if(!query.isEmpty())
			empleado =  query.get(0);
		return empleado;
	}
	
	public List<Empleado> buscarActivos(){
		return getEntityManager().createNamedQuery("Empleado.buscarActivos").setParameter("valorActivo", true).getResultList();
	}
	
	public List<Empleado> buscarPorCargo(Cargo cargo){
		return getEntityManager().createNamedQuery("Empleado.buscarPorCargo").setParameter("cargo", cargo).getResultList();
	}
}
