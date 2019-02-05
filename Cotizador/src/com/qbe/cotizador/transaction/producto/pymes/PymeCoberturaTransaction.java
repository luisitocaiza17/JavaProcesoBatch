package com.qbe.cotizador.transaction.producto.pymes;

import javax.naming.InitialContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.qbe.cotizador.dao.producto.pymes.PymeCoberturaDAO;
import com.qbe.cotizador.model.PymeCobertura;

public class PymeCoberturaTransaction {

	public PymeCoberturaTransaction() {       
	}

	public PymeCobertura crear(PymeCobertura pymeCobertura){		
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeCoberturaDAO pymeObjetoDAO = new PymeCoberturaDAO();
			pymeCobertura = pymeObjetoDAO.crear(pymeCobertura);
			ut.commit();
		}catch(Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			    
		}					
		return pymeCobertura;	
	}

	public PymeCobertura editar(PymeCobertura pymeCobertura){
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeCoberturaDAO pymeObjetoDAO = new PymeCoberturaDAO();
			pymeCobertura = pymeObjetoDAO.editar(pymeCobertura);
			ut.commit();
		}catch(Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			    
		}					
		return pymeCobertura;
	}

	public void eliminar(PymeCobertura pymeCobertura){	
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeCoberturaDAO pymeObjetoDAO = new PymeCoberturaDAO();
			PymeCobertura ObjetoPymesBuscado = new PymeCobertura();
			ObjetoPymesBuscado = pymeObjetoDAO.buscarPorId(pymeCobertura.getCoberturaPymesId());
			if(ObjetoPymesBuscado !=null){
				pymeObjetoDAO.eliminar(pymeCobertura);
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
