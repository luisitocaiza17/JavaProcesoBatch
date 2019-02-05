package com.qbe.cotizador.dao.entidad;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.transaction.entidad.UsuarioTransaction;

public class UsuarioDAO extends EntityManagerFactoryDAO<Usuario>{

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
	
	public UsuarioDAO() {
        super(Usuario.class);
    }
	
	public List<Usuario> buscarTodos(){   
		return getEntityManager().createNamedQuery("Usuario.buscarTodos").getResultList();
	}
	
	
	public Usuario buscarPorId(String id){
		Usuario usuario = new Usuario();
		List<Usuario> query = getEntityManager().createNamedQuery("Usuario.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			usuario =  query.get(0);
		return usuario;
	}
	
	public Usuario buscarPorEntidadId(Entidad entidad){
		Usuario usuario = new Usuario();
		List<Usuario> query = getEntityManager().createNamedQuery("Usuario.buscarPorEntidadId").setParameter("entidad", entidad).getResultList();
		if(!query.isEmpty())
			usuario =  query.get(0);
		return usuario;
	}

	public List<Usuario> buscarActivos(){
		return getEntityManager().createNamedQuery("Usuario.buscarActivos").setParameter("valorActivo", true).getResultList();
	}
	
	public Boolean buscarPorConfirmacionMail(String confirmacionMail){
		Usuario usuario = new Usuario();
		UsuarioTransaction usuarioTransaction = new UsuarioTransaction();
		Boolean retorno = false;
		List<Usuario> query = getEntityManager().createNamedQuery("Usuario.buscarPorConfirmacionMail").setParameter("confirmacion", confirmacionMail).getResultList();
		int existe = query.size();
			
		if(existe != 0){			
			UserTransaction ut = null;
			try{
				ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
				ut.begin();
			
				usuario = query.get(0);	
				usuario.setValidacionMail("");
				usuarioTransaction.editar(usuario);
				ut.commit();
			}catch(Exception e) {
				try {
					ut.rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();			    
			}		
			retorno = true;
			
		}
		return retorno;			
		}
	
	public Usuario buscarPorLogin(String login){
		Usuario usuario = new Usuario();
		List<Usuario> query = getEntityManager().createNamedQuery("Usuario.buscarPorLogin").setParameter("login", login).getResultList();
		if(!query.isEmpty())
			usuario =  query.get(0);
		return usuario;
	}
	
	public Usuario buscarPorEntidad(Entidad entidad){
		Usuario usuario = new Usuario();
		List<Usuario> query = getEntityManager().createNamedQuery("Usuario.buscarPorEntidad").setParameter("entidad", entidad).getResultList();
		if(!query.isEmpty())
			usuario =  query.get(0);	
		return usuario;
	}
	
}
