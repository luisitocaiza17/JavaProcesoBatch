package com.qbe.cotizador.dao.cotizacion;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.entitymanagerfactory.EntityManagerFactoryDAO;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Estado;
import com.qbe.cotizador.model.Pago;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.TipoObjeto;
import com.qbe.cotizador.model.Usuario;

public class CotizacionDAO extends EntityManagerFactoryDAO<Cotizacion>{
	
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
	
	public CotizacionDAO() {
        super(Cotizacion.class);
    }
	
	public List<Cotizacion> buscarTodos(){   
		return getEntityManager().createNamedQuery("Cotizacion.buscarTodos").getResultList();
	}
	
	public Cotizacion buscarPorId(String id){		
		Cotizacion cotizacion = new Cotizacion();
		List<Cotizacion> query = getEntityManager().createNamedQuery("Cotizacion.buscarPorId").setParameter("id", id).getResultList();
		if(!query.isEmpty())
			cotizacion =  query.get(0);
		return cotizacion;
	}

	public List<Cotizacion> buscarPorTipoObjeto(TipoObjeto tipoObjeto){   
		return getEntityManager().createNamedQuery("Cotizacion.buscarPorTipoObjeto").setParameter("tipoObjeto", tipoObjeto).getResultList();
	}

	public List<Cotizacion> buscarPorEstado(Estado estado){   
		return getEntityManager().createNamedQuery("Cotizacion.buscarPorEstado").setParameter("estado", estado).getResultList();
	}

	public List<Cotizacion> buscarPorEstadoPuntoVenta(Estado estado, PuntoVenta puntoVenta){   
		return getEntityManager().createNamedQuery("Cotizacion.buscarPorEstadoPuntoVenta").setParameter("estado", estado).setParameter("puntoVenta", puntoVenta).getResultList();
	}
	//PJacome Cotizaciones No Emitidas x Fecha
	public List<Cotizacion> buscarPorTipoObjetoNoEmitidoxFecha(String fecha1, String fecha2,TipoObjeto tipoObjeto,String cotizacionId,String puntoVenta,String agenteId,String identificacion,String usuarioSession, String estadoCotizacion){   
		
		String f1 = fecha1 + " 00:05:00";
		String f2 = fecha2 + " 23:55:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		java.sql.Timestamp timestamp1 = null;
		java.sql.Timestamp timestamp2 = null;
		
		// Validacion para cuando se ingrese la fecha en la b�squeda
		if(fecha1.length()>0 && fecha1.length()>0){
			java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			try {
				parsedDate = dateFormat.parse(f2);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			timestamp2 = new java.sql.Timestamp(parsedDate.getTime());			
		}
		
		Estado estado = new Estado();	
		Estado estado2 = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();
		EstadoDAO estadoDAO2 = new EstadoDAO();
	
		if (estadoCotizacion.equals("")){
			estado = estadoDAO.buscarPorNombreClase("Borrador","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		}else{
			estado = estadoDAO.buscarPorNombreClase("Emitido","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Emitido","Cotizacion");			
		}
		if (estadoCotizacion.equals("Pendiente de Emitir")){
			estado = estadoDAO.buscarPorNombreClase("Pendiente de Emitir","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente de Emitir","Cotizacion");
		}
		List<Cotizacion> results = new ArrayList<Cotizacion>();
		TypedQuery<Cotizacion> query = null;
		
		List<String> listadoTiposObjeto = new ArrayList<String>();
		listadoTiposObjeto.add("1");
		listadoTiposObjeto.add("2");
		listadoTiposObjeto.add("4");
		listadoTiposObjeto.add("5");
		listadoTiposObjeto.add("6");
		
	
			String stringQuery= "SELECT c FROM Cotizacion c where (c.estado =:estado OR c.estado =:estado2)";					
			String valoresWhereQuery = "";
			
			// Agregamos la parte del where las opciones de busqueda
			if(cotizacionId.length()>0)
				valoresWhereQuery += " AND c.id=:cotizacionId";
			if(tipoObjeto == null)
				valoresWhereQuery += " AND c.tipoObjeto IN :listTipos";
			if(tipoObjeto != null)
				valoresWhereQuery += " AND c.tipoObjeto = :tipoObjeto";
			if(fecha1.length()>0 && fecha2.length()>0)
				valoresWhereQuery += " AND c.fechaElaboracion BETWEEN :startDate AND :endDate";
			if(puntoVenta.length()>0)
				valoresWhereQuery += " AND c.puntoVenta =:puntoVenta";
			if(agenteId.length()>0)
				valoresWhereQuery += " AND c.agenteId =:agenteId";
			if(identificacion.length()>0)
				valoresWhereQuery += " AND c.clienteId =:clienteId";
			if(usuarioSession.length()>0 && !estado.getNombre().equals("Emitido"))
				valoresWhereQuery += " AND c.usuario =:usuario";
			
			stringQuery = stringQuery+valoresWhereQuery+" ORDER BY c.fechaElaboracion ASC";
			
			query = getEntityManager().createQuery(stringQuery, Cotizacion.class);
			query.setParameter("estado", estado);
			query.setParameter("estado2", estado2);
			
			// Agregamos los parametros del buscador
			if(cotizacionId.length()>0)
				query.setParameter("cotizacionId", cotizacionId);
			if(tipoObjeto == null)
				query.setParameter("listTipos", listadoTiposObjeto);
			if(tipoObjeto != null)
				query.setParameter("tipoObjeto", tipoObjeto);
			if(fecha1.length()>0 && fecha2.length()>0){
				query.setParameter("startDate", timestamp1);
				query.setParameter("endDate", timestamp2);
			}
			if(puntoVenta.length()>0){
				PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO();	
				PuntoVenta puntoVentaObtenido = puntoVentaDAO.buscarPorId(puntoVenta);
				query.setParameter("puntoVenta", puntoVentaObtenido);
			}
			if(agenteId.length()>0)
				query.setParameter("agenteId", new BigInteger(agenteId));
			if(identificacion.length()>0){
				EntidadDAO entidadDAO = new EntidadDAO();
				Entidad entidadCliente = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
				if(entidadCliente.getId() != null)
					query.setParameter("clienteId", new BigInteger(entidadCliente.getClientes().get(0).getId()));
				else
					query.setParameter("clienteId", new BigInteger("0"));
			}
		
			if(usuarioSession.length()>0 && !estado.getNombre().equals("Emitido")){			
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = usuarioDAO.buscarPorId(usuarioSession);				
				query.setParameter("usuario", usuario);
			}
		
			results = query.getResultList();
			return results;
	}
	
	//PJacome Cotizaciones No Emitidas x Fecha
	public List<Cotizacion> buscarPorTipoObjetoNoEmitidoxFechaPuntoVenta(String fecha1, String fecha2,TipoObjeto tipoObjeto, PuntoVenta puntoVenta,String cotizacionId,String agenteId,String identificacion,String usuarioSession,String estadoCotizacion){   
		
		String f1 = fecha1 + " 00:05:00";
		String f2 = fecha2 + " 23:55:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		java.sql.Timestamp timestamp1 = null;
		java.sql.Timestamp timestamp2 = null;
		
		// Validacion para cuando se ingrese la fecha en la b�squeda
		if(fecha1.length()>0 && fecha1.length()>0){
			java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			try {
				parsedDate = dateFormat.parse(f2);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			timestamp2 = new java.sql.Timestamp(parsedDate.getTime());			
		}
		
		
		Estado estado = new Estado();	
		Estado estado2 = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();
		EstadoDAO estadoDAO2 = new EstadoDAO();
		
		if (estadoCotizacion.equals("")){
			estado = estadoDAO.buscarPorNombreClase("Borrador","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		}else{
			estado = estadoDAO.buscarPorNombreClase("Emitido","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Emitido","Cotizacion");			
		}
		if (estadoCotizacion.equals("Pendiente de Emitir")){
			estado = estadoDAO.buscarPorNombreClase("Pendiente de Emitir","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente de Emitir","Cotizacion");
		}		
		List<Cotizacion> results = new ArrayList<Cotizacion>();
		TypedQuery<Cotizacion> query = null;
		
		List<String> listadoTiposObjeto = new ArrayList<String>();
		listadoTiposObjeto.add("1");
		listadoTiposObjeto.add("2");
		listadoTiposObjeto.add("4");
		listadoTiposObjeto.add("5");
		listadoTiposObjeto.add("6");
		

			String stringQuery= "SELECT c FROM Cotizacion c where (c.estado =:estado OR c.estado =:estado2)";					
			String valoresWhereQuery = "";
			
			// Agregamos la parte del where las opciones de busqueda
			if(cotizacionId.length()>0)
				valoresWhereQuery += " AND c.id=:cotizacionId";
			if(tipoObjeto == null)
				valoresWhereQuery += " AND c.tipoObjeto IN :listTipos";
			if(tipoObjeto != null)
				valoresWhereQuery += " AND c.tipoObjeto = :tipoObjeto";
			if(fecha1.length()>0 && fecha2.length()>0)
				valoresWhereQuery += " AND c.fechaElaboracion BETWEEN :startDate AND :endDate";
			if(puntoVenta !=null)
				valoresWhereQuery += " AND c.puntoVenta =:puntoVenta";
			if(agenteId.length()>0)
				valoresWhereQuery += " AND c.agenteId =:agenteId";
			if(identificacion.length()>0)
				valoresWhereQuery += " AND c.clienteId =:clienteId";
			if(usuarioSession.length()>0 && !estado.getNombre().equals("Emitido"))
				valoresWhereQuery += " AND c.usuario =:usuario";
			
			stringQuery = stringQuery+valoresWhereQuery+" ORDER BY c.fechaElaboracion ASC";
			
			query = getEntityManager().createQuery(stringQuery, Cotizacion.class);
			query.setParameter("estado", estado);
			query.setParameter("estado2", estado2);
			
			// Agregamos los parametros del buscador
			if(cotizacionId.length()>0)
				query.setParameter("cotizacionId", cotizacionId);
			if(tipoObjeto == null)
				query.setParameter("listTipos", listadoTiposObjeto);
			if(tipoObjeto != null)
				query.setParameter("tipoObjeto", tipoObjeto);
			if(fecha1.length()>0 && fecha2.length()>0){
				query.setParameter("startDate", timestamp1);
				query.setParameter("endDate", timestamp2);
			}
			if(puntoVenta!=null)				
				query.setParameter("puntoVenta", puntoVenta);			
			if(agenteId.length()>0)
				query.setParameter("agenteId", new BigInteger(agenteId));
			if(identificacion.length()>0){
				EntidadDAO entidadDAO = new EntidadDAO();
				Entidad entidadCliente = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
				if(entidadCliente.getId() != null)
					query.setParameter("clienteId", new BigInteger(entidadCliente.getClientes().get(0).getId()));
				else
					query.setParameter("clienteId", new BigInteger("0"));
			}
			if(usuarioSession.length()>0 && !estado.getNombre().equals("Emitido")){			
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = usuarioDAO.buscarPorId(usuarioSession);				
				query.setParameter("usuario", usuario);
			}
			
			results = query.getResultList();
			return results;

	}
	
//	public List<Cotizacion> buscarActivos(){
//		return getEntityManager().createNamedQuery("Cotizacion.buscarTodos").getResultList();
//	}
	//Buscando cotizaciones emitidas - PJacome	
	public List<Cotizacion> buscarCotizacionxFecha(String fecha1, String fecha2, TipoObjeto tipoObjeto){
		
		String f1 = fecha1 + " 23:55:00";
		String f2 = fecha2 + " 23:55:00";
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		    java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    java.sql.Timestamp timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			 	try {
					parsedDate = dateFormat.parse(f2);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime());
		
		Estado estado = new Estado();	
		EstadoDAO estadoDAO = new EstadoDAO();		
		estado = estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"); 
		    
		List<Cotizacion> results = null;
	
			TypedQuery<Cotizacion> query = getEntityManager().createQuery("SELECT c FROM Cotizacion c WHERE c.fechaElaboracion BETWEEN :startDate AND :endDate AND c.estado = :estado and c.tipoObjeto = :tipoObjeto", Cotizacion.class).setParameter("startDate", timestamp1).setParameter("endDate", timestamp2).setParameter("estado", estado).setParameter("tipoObjeto", tipoObjeto);
			results = query.getResultList(); 

		return results;
	}
	//Buscando Cotizaciones Borrador y Pendientes - PJacome
	public List<Cotizacion> buscarCotizacionxFecha2(String fecha1 , String fecha2){
		
		String f1 = fecha1 + " 23:55:00";
		String f2 = fecha2 + " 23:55:00";
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		    java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    java.sql.Timestamp timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			 	try {
					parsedDate = dateFormat.parse(f2);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime());
		
		Estado estado = new Estado();	
		Estado estado2 = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();
		EstadoDAO estadoDAO2 = new EstadoDAO();
		estado = estadoDAO.buscarPorNombreClase("Borrador","Cotizacion");
		estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		    
		
		List<Cotizacion> results = null;

			TypedQuery<Cotizacion> query = getEntityManager().createQuery("SELECT c FROM Cotizacion c WHERE c.fechaElaboracion BETWEEN :startDate AND :endDate AND c.estado = :estado OR c.estado = :estado2", Cotizacion.class).setParameter("startDate", timestamp1).setParameter("endDate", timestamp2).setParameter("estado", estado).setParameter("estado2", estado2);
			results = query.getResultList(); 
		return results;
	}
	
	public List<Cotizacion> buscarPorTipoObjetoPuntoVenta(TipoObjeto tipoObjeto,PuntoVenta puntoVenta){   
		return getEntityManager().createNamedQuery("Cotizacion.buscarPorTipoObjetoPuntoVenta").setParameter("tipoObjeto", tipoObjeto).setParameter("puntoVenta", puntoVenta).getResultList();
	}
	
	public List<Cotizacion> buscarCotizacionxFechaPuntoVenta(String fecha1, String fecha2, TipoObjeto tipoObjeto, PuntoVenta puntoVenta){
		
		String f1 = fecha1 + " 23:55:00";
		String f2 = fecha2 + " 23:55:00";
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		    java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    java.sql.Timestamp timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			 	try {
					parsedDate = dateFormat.parse(f2);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime());
		
		Estado estado = new Estado();	
		EstadoDAO estadoDAO = new EstadoDAO();		
		estado = estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"); 
		    
		
		
		List<Cotizacion> results = null;
		try{	
			TypedQuery<Cotizacion> query = getEntityManager().createQuery("SELECT c FROM Cotizacion c WHERE c.fechaElaboracion BETWEEN :startDate AND :endDate AND c.estado = :estado and c.tipoObjeto = :tipoObjeto and c.puntoVenta = :puntoVenta", Cotizacion.class).setParameter("startDate", timestamp1).setParameter("endDate", timestamp2).setParameter("estado", estado).setParameter("tipoObjeto", tipoObjeto).setParameter("puntoVenta", puntoVenta);
			results = query.getResultList(); 
		}catch(Exception e) { 
			em.getTransaction().rollback();           
		}finally{         
			em.close();		
		}
		return results;
	}
	
	public Cotizacion buscarPorPago(Pago pago){
		Cotizacion cotizacion = new Cotizacion();
		List<Cotizacion> query = getEntityManager().createNamedQuery("Cotizacion.buscarPorPago").setParameter("pago", pago).getResultList();
		if(!query.isEmpty())
			cotizacion =  query.get(0);			
		return cotizacion;
	}
	
	//Cotizaciones pendientes de aprobar ganadero
	public List<Cotizacion> buscarPorTipoObjetoPorEstadoxFecha(String fecha1, String fecha2,TipoObjeto tipoObjeto, String Estado){   
		String f1 = fecha1 + " 23:55:00";
		String f2 = fecha2 + " 23:55:00";
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		    java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    java.sql.Timestamp timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			 	try {
					parsedDate = dateFormat.parse(f2);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate.getTime());
		
		Estado estado = new Estado();	
		EstadoDAO estadoDAO = new EstadoDAO();
		if(Estado.equals("SPA"))
			estado = estadoDAO.buscarPorNombreClase("Pendiente de Revisar","Cotizacion");
		else if(Estado.equals("SPE"))
			estado = estadoDAO.buscarPorNombreClase("Pendiente de Emitir","Cotizacion");
			
			TypedQuery<Cotizacion> query = getEntityManager().createQuery("SELECT c FROM Cotizacion c where c.fechaElaboracion BETWEEN :startDate AND :endDate AND c.tipoObjeto = :tipoObjeto AND c.estado = :estado", Cotizacion.class).setParameter("startDate", timestamp1).setParameter("endDate", timestamp2).setParameter("tipoObjeto", tipoObjeto).setParameter("estado", estado);
			List<Cotizacion> results = query.getResultList(); 	 
			return results;
	}
	
	//Cotizaciones No Emitidas x Fecha
	public List<Cotizacion> buscarPorTipoObjetoParaCanal(String fecha1, String fecha2,TipoObjeto tipoObjeto,String cotizacionId,String puntoVenta,String agenteId,String identificacion,String usuarioSession, String EstadoFiltro){   
		
		String f1 = fecha1 + " 00:05:00";
		String f2 = fecha2 + " 23:55:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		java.sql.Timestamp timestamp1 = null;
		java.sql.Timestamp timestamp2 = null;
		String clienteId = "";
		
		// Validacion para cuando se ingrese la fecha en la b�squeda
		if(fecha1.length()>0 && fecha1.length()>0){
			java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			try {
				parsedDate = dateFormat.parse(f2);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			timestamp2 = new java.sql.Timestamp(parsedDate.getTime());			
		}
		
		Estado estado = new Estado();	
		Estado estado2 = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();
		EstadoDAO estadoDAO2 = new EstadoDAO();
		if(EstadoFiltro.equals("SPB"))
		{
			estado = estadoDAO.buscarPorNombreClase("Borrador","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		}
		else if(EstadoFiltro.equals("SPINS"))
		{
			estado = estadoDAO.buscarPorNombreClase("Pendiente de Inspeccion","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente de Inspeccion","Cotizacion");
		}
		else if(EstadoFiltro.equals("SPE"))
		{
			estado = estadoDAO.buscarPorNombreClase("Pendiente","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		}
		else if(EstadoFiltro.equals("SRARV"))
		{
			estado = estadoDAO.buscarPorNombreClase("Revision Aprobada","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Revision Negada","Cotizacion");
		}
			
		List<Cotizacion> results = new ArrayList<Cotizacion>();
		TypedQuery<Cotizacion> query = null;
		
		List<String> listadoTiposObjeto = new ArrayList<String>();
		listadoTiposObjeto.add("2");
		listadoTiposObjeto.add("4");
		listadoTiposObjeto.add("5");
		listadoTiposObjeto.add("6");
		
	
			String stringQuery= "SELECT c FROM Cotizacion c where (c.estado =:estado OR c.estado =:estado2)";					
			String valoresWhereQuery = "";
			
			// Agregamos la parte del where las opciones de busqueda
			if(cotizacionId.length()>0)
				valoresWhereQuery += " AND c.id=:cotizacionId";
			if(tipoObjeto == null)
				valoresWhereQuery += " AND c.tipoObjeto IN :listTipos";
			if(tipoObjeto != null)
				valoresWhereQuery += " AND c.tipoObjeto = :tipoObjeto";
			if(fecha1.length()>0 && fecha2.length()>0)
				valoresWhereQuery += " AND c.fechaElaboracion BETWEEN :startDate AND :endDate";
			if(puntoVenta.length()>0)
				valoresWhereQuery += " AND c.puntoVenta =:puntoVenta";
			if(agenteId.length()>0)
				valoresWhereQuery += " AND c.agenteId =:agenteId";
			if(identificacion.length()>0)
				valoresWhereQuery += " AND c.clienteId =:clienteId";
			if(usuarioSession.length()>0)
				valoresWhereQuery += " AND c.usuario =:usuario";
			
			stringQuery = stringQuery+valoresWhereQuery+" ORDER BY c.fechaElaboracion ASC";
			
			query = getEntityManager().createQuery(stringQuery, Cotizacion.class);
			query.setParameter("estado", estado);
			query.setParameter("estado2", estado2);
			
			// Agregamos los parametros del buscador
			if(cotizacionId.length()>0)
				query.setParameter("cotizacionId", cotizacionId);
			if(tipoObjeto == null)
				query.setParameter("listTipos", listadoTiposObjeto);
			if(tipoObjeto != null)
				query.setParameter("tipoObjeto", tipoObjeto);
			if(fecha1.length()>0 && fecha2.length()>0){
				query.setParameter("startDate", timestamp1);
				query.setParameter("endDate", timestamp2);
			}
			if(puntoVenta.length()>0){
				PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO();	
				PuntoVenta puntoVentaObtenido = puntoVentaDAO.buscarPorId(puntoVenta);
				query.setParameter("puntoVenta", puntoVentaObtenido);
			}
			if(agenteId.length()>0)
				query.setParameter("agenteId", new BigInteger(agenteId));
			if(identificacion.length()>0){
				EntidadDAO entidadDAO = new EntidadDAO();
				Entidad entidadCliente = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
				if(entidadCliente.getId() != null)
					query.setParameter("clienteId", new BigInteger(entidadCliente.getClientes().get(0).getId()));
				else
					query.setParameter("clienteId", new BigInteger("0"));
			}
			if(usuarioSession.length()>0){			
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = usuarioDAO.buscarPorId(usuarioSession);				
				query.setParameter("usuario", usuario);
			}
			
			results = query.getResultList();
			return results;
	}
	
	//Cotizaciones No Emitidas x Fecha
	public List<Cotizacion> buscarPorTipoObjetoxFechaPuntoVentaYEstado(String fecha1, String fecha2,TipoObjeto tipoObjeto, PuntoVenta puntoVenta,String cotizacionId,String agenteId,String identificacion,String usuarioSession, String FiltroEstado){   
		
		String f1 = fecha1 + " 00:05:00";
		String f2 = fecha2 + " 23:55:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		java.sql.Timestamp timestamp1 = null;
		java.sql.Timestamp timestamp2 = null;
		String clienteId = "";
		
		// Validacion para cuando se ingrese la fecha en la b�squeda
		if(fecha1.length()>0 && fecha1.length()>0){
			java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(f1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    timestamp1 = new java.sql.Timestamp(parsedDate.getTime());
	
			try {
				parsedDate = dateFormat.parse(f2);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			timestamp2 = new java.sql.Timestamp(parsedDate.getTime());			
		}
		
		Estado estado = new Estado();	
		Estado estado2 = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();
		EstadoDAO estadoDAO2 = new EstadoDAO();
		if(FiltroEstado.equals("SPB"))
		{
			estado = estadoDAO.buscarPorNombreClase("Borrador","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Pendiente","Cotizacion");
		}
		else if(FiltroEstado.equals("SRARV"))
		{
			estado = estadoDAO.buscarPorNombreClase("Revision Aprobada","Cotizacion");
			estado2 = estadoDAO2.buscarPorNombreClase("Revision Negada","Cotizacion");
		}
		List<Cotizacion> results = new ArrayList<Cotizacion>();
		TypedQuery<Cotizacion> query = null;
		
		List<String> listadoTiposObjeto = new ArrayList<String>();
		listadoTiposObjeto.add("2");
		listadoTiposObjeto.add("4");
		listadoTiposObjeto.add("5");
		listadoTiposObjeto.add("6");
		
	
			String stringQuery= "SELECT c FROM Cotizacion c where (c.estado =:estado OR c.estado =:estado2)";					
			String valoresWhereQuery = "";
			
			// Agregamos la parte del where las opciones de busqueda
			if(cotizacionId.length()>0)
				valoresWhereQuery += " AND c.id=:cotizacionId";
			if(tipoObjeto == null)
				valoresWhereQuery += " AND c.tipoObjeto IN :listTipos";
			if(tipoObjeto != null)
				valoresWhereQuery += " AND c.tipoObjeto = :tipoObjeto";
			if(fecha1.length()>0 && fecha2.length()>0)
				valoresWhereQuery += " AND c.fechaElaboracion BETWEEN :startDate AND :endDate";
			if(puntoVenta !=null)
				valoresWhereQuery += " AND c.puntoVenta =:puntoVenta";
			if(agenteId.length()>0)
				valoresWhereQuery += " AND c.agenteId =:agenteId";
			if(identificacion.length()>0)
				valoresWhereQuery += " AND c.clienteId =:clienteId";
			if(usuarioSession.length()>0)
				valoresWhereQuery += " AND c.usuario =:usuario";
			
			stringQuery = stringQuery+valoresWhereQuery+" ORDER BY c.fechaElaboracion ASC";
			
			query = getEntityManager().createQuery(stringQuery, Cotizacion.class);
			query.setParameter("estado", estado);
			query.setParameter("estado2", estado2);
			
			// Agregamos los parametros del buscador
			if(cotizacionId.length()>0)
				query.setParameter("cotizacionId", cotizacionId);
			if(tipoObjeto == null)
				query.setParameter("listTipos", listadoTiposObjeto);
			if(tipoObjeto != null)
				query.setParameter("tipoObjeto", tipoObjeto);
			if(fecha1.length()>0 && fecha2.length()>0){
				query.setParameter("startDate", timestamp1);
				query.setParameter("endDate", timestamp2);
			}
			if(puntoVenta!=null)				
				query.setParameter("puntoVenta", puntoVenta);			
			if(agenteId.length()>0)
				query.setParameter("agenteId", new BigInteger(agenteId));
			if(identificacion.length()>0){
				EntidadDAO entidadDAO = new EntidadDAO();
				Entidad entidadCliente = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
				if(entidadCliente.getId() != null)
					query.setParameter("clienteId", new BigInteger(entidadCliente.getClientes().get(0).getId()));
				else
					query.setParameter("clienteId", new BigInteger("0"));
			}
			if(usuarioSession.length()>0){			
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = usuarioDAO.buscarPorId(usuarioSession);				
				query.setParameter("usuario", usuario);
			}
			
			results = query.getResultList();
			return results;
	}
}
