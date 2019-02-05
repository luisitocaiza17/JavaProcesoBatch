package com.qbe.cotizador.dao.entidad;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Cargo;

public class CargoDAO extends EntityManagerFactoryDAO<Cargo>{
	
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
	
	public CargoDAO() {
        super(Cargo.class);
    } 
	
	public List<Cargo> buscarTodos(){
		return getEntityManager().createNamedQuery("Cargo.buscarTodos").getResultList();		
	}
		
	public Cargo buscarPorId(String id){
		Cargo cargo = new Cargo();
		List<Cargo> query = getEntityManager().createNamedQuery("Cargo.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			cargo =  query.get(0);
		return cargo;		
	}
		
	public List<Cargo> buscarActivos(){
		return getEntityManager().createNamedQuery("Cargo.buscarActivos").setParameter("valorActivo", true).getResultList();			
	}

	public List<Cargo> buscarPorCargoGenerico(String cargoGenerico){
		return getEntityManager().createNamedQuery("Cargo.buscarPorCargoGenerico").setParameter("cargoGenerico", cargoGenerico).getResultList();		
	}
	
	public Cargo buscarPorCargo(String cargo){
		Cargo cargoObtenido = new Cargo();
		List<Cargo> query = getEntityManager().createNamedQuery("Cargo.buscarPorCargo").setParameter("cargo", cargo).getResultList();
		if(!query.isEmpty())
			cargoObtenido =  query.get(0);
		return cargoObtenido;		
	}		
}
