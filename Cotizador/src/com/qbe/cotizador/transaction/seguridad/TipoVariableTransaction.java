package com.qbe.cotizador.transaction.seguridad;

import javax.naming.InitialContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import com.qbe.cotizador.dao.seguridad.TipoVariableDAO;
import com.qbe.cotizador.model.TipoVariable;

public class TipoVariableTransaction {
	
	public TipoVariableTransaction() {       
    }

	public TipoVariable crear(TipoVariable TipoVariable){		
	UserTransaction ut = null;
	try{
		ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
		ut.begin();
		TipoVariableDAO TipoVariableDAO = new TipoVariableDAO();
		TipoVariable = TipoVariableDAO.crear(TipoVariable);
        ut.commit();
	}catch(Exception e) {
		try {
			ut.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();			    
	}					
	return TipoVariable;	
	}
	
	public TipoVariable editar(TipoVariable TipoVariable){
		UserTransaction ut = null;
	try{
		ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
		ut.begin();
		TipoVariableDAO TipoVariableDAO = new TipoVariableDAO();
		TipoVariable TipoVariableBuscada = TipoVariableDAO.buscarPorId(TipoVariable.getId());
		if(TipoVariableBuscada!=null){
			TipoVariable = TipoVariableDAO.editar(TipoVariable);
			ut.commit();
		}
	}catch(Exception e) {
		try {
			ut.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();			    
	}					
	return TipoVariable;
	}
	
	public void eliminar(TipoVariable TipoVariable){	
	UserTransaction ut = null;
	try{
		ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
		ut.begin();
		TipoVariableDAO TipoVariableDAO = new TipoVariableDAO();
		TipoVariable TipoVariableBuscado = new TipoVariable();
		TipoVariableBuscado = TipoVariableDAO.buscarPorId(TipoVariable.getId());
		if(TipoVariableBuscado !=null){
			TipoVariableDAO.eliminar(TipoVariable);
            ut.commit();
		}
	}catch(Exception e) {
		try {
			ut.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();			    
		}
	}
}
