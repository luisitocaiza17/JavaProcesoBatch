package com.qbe.cotizador.dao.seguridad;

import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.TipoVariable;
import com.qbe.cotizador.model.VariableSistema;


public class VariableSistemaDAO extends EntityManagerFactoryDAO<VariableSistema>{
	
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
	
	public VariableSistemaDAO() {
        super(VariableSistema.class);
    }
	
	public List<VariableSistema> buscarTodos(){
		return getEntityManager().createNamedQuery("VariableSistema.buscarTodos").getResultList();
	}
	
	public VariableSistema buscarPorId(String id){
		VariableSistema variable = new VariableSistema();
		List<VariableSistema> query = getEntityManager().createNamedQuery("VariableSistema.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			variable =  query.get(0);
		return variable;
	}
	
	public VariableSistema buscarPorNombre(String nombre){
		VariableSistema variable = new VariableSistema();
		List<VariableSistema> query = getEntityManager().createNamedQuery("VariableSistema.buscarPorNombre").setParameter("nombre", nombre).getResultList();
		if(!query.isEmpty())
			variable =  query.get(0);
		return variable;
	}
	
	public List<VariableSistema> buscarActivos(){
		return getEntityManager().createNamedQuery("VariableSistema.buscarActivos").setParameter("valorActivo", true).getResultList();
	}	
	
    
    public List<String> buscarPorNombres(List<String> nombres){
		VariableSistema variable = new VariableSistema();
		List<String> listadoValores = new ArrayList<String>();  

		for(int i = 0;i<nombres.size();i++){
			Query query = getEntityManager().createNamedQuery("VariableSistema.buscarPorNombre").setParameter("nombre", nombres.get(i));
			List results = query.getResultList();
			variable = (VariableSistema) results.get(0);	
			listadoValores.add(variable.getValor());
		}
		return listadoValores;
	}
    
    public int variableSesionSistema(){
    	VariableSistema variable_Sistema = new VariableSistema();
    	int valorSesionSistema = 0;
    	
    	VariableSistema variable = null;
		List<VariableSistema> query = getEntityManager().createNamedQuery("VariableSistema.buscarPorNombre").setParameter("nombre", "EXPIRA_SESION").getResultList();
		if(query.isEmpty())
			valorSesionSistema = 0;
		else
			valorSesionSistema =  Integer.parseInt(query.get(0).getValor());
		return valorSesionSistema;
    }
    
    public List<VariableSistema> buscarTipoVariable(TipoVariable tipoVariable){
    	return getEntityManager().createNamedQuery("VariableSistema.buscarTipoVariable").setParameter("tipoVariable", tipoVariable).getResultList();
	}

}
