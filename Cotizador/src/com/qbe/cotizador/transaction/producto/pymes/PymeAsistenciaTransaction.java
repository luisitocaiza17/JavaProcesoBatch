package com.qbe.cotizador.transaction.producto.pymes;

import javax.naming.InitialContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.qbe.cotizador.dao.producto.pymes.PymeAsistenciaDAO;
import com.qbe.cotizador.model.PymeAsistencia;

public class PymeAsistenciaTransaction {

	public PymeAsistenciaTransaction() {       
	}

	public PymeAsistencia crear(PymeAsistencia pymeAsistencia){		
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeAsistenciaDAO pymeObjetoDAO = new PymeAsistenciaDAO();
			pymeAsistencia = pymeObjetoDAO.crear(pymeAsistencia);
			ut.commit();
		}catch(Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			    
		}					
		return pymeAsistencia;	
	}

	public PymeAsistencia editar(PymeAsistencia pymeAsistencia){
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeAsistenciaDAO pymeObjetoDAO = new PymeAsistenciaDAO();
			pymeAsistencia = pymeObjetoDAO.editar(pymeAsistencia);
			ut.commit();
		}catch(Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			    
		}					
		return pymeAsistencia;
	}

	public void eliminar(PymeAsistencia pymeAsistencia){	
		UserTransaction ut = null;
		try{
			ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			ut.begin();
			PymeAsistenciaDAO pymeObjetoDAO = new PymeAsistenciaDAO();
			PymeAsistencia ObjetoPymesBuscado = new PymeAsistencia();
			ObjetoPymesBuscado = pymeObjetoDAO.buscarPorId(pymeAsistencia.getAsistenciaId());
			if(ObjetoPymesBuscado !=null){
				pymeObjetoDAO.eliminar(pymeAsistencia);
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
