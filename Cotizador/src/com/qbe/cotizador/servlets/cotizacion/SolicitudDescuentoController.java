package com.qbe.cotizador.servlets.cotizacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.entidad.EmpleadoDAO;
import com.qbe.cotizador.dao.entidad.UnidadNegocioDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.DescuentoDAO;
import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.GrupoUsuarioAutorizacionDAO;
import com.qbe.cotizador.dao.cotizacion.SolicitudDescuentoDAO;
import com.qbe.cotizador.dao.entidad.SucursalDAO;
import com.qbe.cotizador.dao.pagos.CuotaDAO;
import com.qbe.cotizador.dao.pagos.PagoDAO;
import com.qbe.cotizador.dao.seguridad.TipoVariableDAO;
import com.qbe.cotizador.dao.seguridad.VariableSistemaDAO;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionCobertura;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Cuota;
import com.qbe.cotizador.model.Empleado;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Estado;
import com.qbe.cotizador.model.GrupoAutorizacion;
import com.qbe.cotizador.model.GrupoUsuarioAutorizacion;
import com.qbe.cotizador.model.Pago;
import com.qbe.cotizador.model.SolicitudDescuento;
import com.qbe.cotizador.model.TipoVariable;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.VariableSistema;
import com.qbe.cotizador.transaction.cotizacion.CoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionCoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionDetalleTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.cotizacion.SolicitudDescuentoTransaction;
import com.qbe.cotizador.util.Utilitarios;

/**
 * Servlet implementation class SolicitudDescuentoController
 */
@WebServlet("/SolicitudDescuentoController")
public class SolicitudDescuentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolicitudDescuentoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//para autorizar o rechazar solicitudes de descuento
		String direccion=request.getRequestURL().toString()+"?"+request.getQueryString();
		Usuario usuario=new Usuario();
		if(request.getSession().getAttribute("usuario")!=null)
		 usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		if(usuario.getId()==null)
		{
			//String link = request.getRequestURL().toString().replace("/SolicitudDescuentoController","/index.jsp?redir="+direccion.replace("&", "%1").replace("?", "%2").replace("=", "%3").replace("http://", "").replace("/", "%4"));
			
			String link = request.getRequestURL().toString().replace("/SolicitudDescuentoController","/index.jsp?redir="+URLEncoder.encode(direccion, "UTF-8"));
			
			response.sendRedirect(link);
			
		}
		else{
			
			String link = request.getRequestURL().toString().replace("/SolicitudDescuentoController","/dashboard/RespuestaDescuento.jsp?"+request.getQueryString());

			response.sendRedirect(link);

		/*String solicitudDescuentoId = request.getParameter("sdid") == null ? "" : request.getParameter("sdid");
		//####(id)#########
		solicitudDescuentoId=solicitudDescuentoId.substring(4);
		for(int i=0;i<9;i++){
			solicitudDescuentoId=solicitudDescuentoId.substring(0,solicitudDescuentoId.length()-1);
		}
		
		
		/*String empleadoId = request.getParameter("emid") == null ? "" : request.getParameter("emid");
		//####(id)#########
		empleadoId=empleadoId.substring(6);
		for(int i=0;i<4;i++){
			empleadoId=empleadoId.substring(0,empleadoId.length()-1);
		}
		
		SolicitudDescuentoDAO sdDAO = new SolicitudDescuentoDAO();
		SolicitudDescuento sd = sdDAO.buscarPorId(solicitudDescuentoId);
				
		EmpleadoDAO eDAO = new EmpleadoDAO();
		
		UsuarioDAO uDAO=new UsuarioDAO();
		//usuario = uDAO.buscarPorId("1");//
		//usuario=uDAO.buscarPorEntidadId(eDAO.buscarPorId(empleadoId).getEntidad());
		
		if(usuario.getId()!=null&&usuario.getId()!="")
		sd.setUsuarioId(new BigInteger( usuario.getId()));
		
		
		
		String autorizar = request.getParameter("autoriza") == null ? "" : request.getParameter("autoriza");
		//####(id)#########
		
		
			
			//falta el empleado autoriza
			EstadoDAO estDAO= new EstadoDAO();
			
		if (autorizar.equals("1")&&sd.getEstado().getId().equals(estDAO.buscarPorNombre("Pendiente", "Descuento").getId())) {
			sd.setEstado(estDAO.buscarPorNombre("Autorizado", "Descuento"));
			CotizacionDAO cotizacionDAO = new CotizacionDAO();
			Cotizacion cotizacion = sd.getCotizacion();
			cotizacion.setPrimaNetaTotal(""
					+ (cotizacion.getPrimaOrigen()
							* (100-new Double(sd.getPorcentaje())) / 100));
			cotizacion.setValorDescuento(new Double(sd.getPorcentaje()));
			
			/* 
			 * 
			 * 
			 * 
			 * calculo impuestos
			 * 
			 * 
			 * 
			 * *//*
			VariableSistema variable = new VariableSistema();
			VariableSistemaDAO variableDAO = new VariableSistemaDAO();
			double valorFinalPrima = new Double(cotizacion.getPrimaNetaTotal());

			TipoVariableDAO tipoVariableDao = new TipoVariableDAO();
			TipoVariable tipoVariable = tipoVariableDao.buscarPorId("3");
			List<VariableSistema> variablesistema = variableDAO
					.buscarTipoVariable(tipoVariable);
			double valorBase = 0;
			double valorDerechosEmision = 0;
			double valorSeguroCampesino = 0;
			double valorSuperBancos = 0;
			double valorIva = 0;
			double valorSubTotal = 0;
			double valorTotal = 0;

			for (int i = 0; i < variablesistema.size(); i++) {
				variable = (VariableSistema) variablesistema.get(i);
				if (variable.getNombre().equals("DERECHOS_EMISION")) {
					valorBase = Double.parseDouble(variable.getValor()) + valorFinalPrima;
					valorDerechosEmision = Double.parseDouble(variable.getValor());
					cotizacion.setImpDerechoEmision(valorDerechosEmision);

				} else if (variable.getNombre().equals("SEGURO_CAMPESINO")) {
					valorSeguroCampesino = Math.rint(Double
							.parseDouble(variable.getValor())
							* valorFinalPrima
							/ 100 * 100) / 100;
					valorBase = valorBase + valorSeguroCampesino;
					cotizacion.setImpSeguroCampesino(valorSeguroCampesino);
				}
				/*
				 * else
				 * if(variable.getNombre().equals("RECARGO_SEGURO_CAMPESINO")){
				 * InquiredServiceInterfaceService servicio = new
				 * InquiredServiceInterfaceService(); valorRecargoCampesino =
				 * servicio
				 * .getInquiredServiceInterfacePort().consultarRecargoSeguroAgricola
				 * (cliente.getEntidad().getIdentificacion(), valorFinalPrima);
				 * cotizacion.setImpDerechoEmision(valorRecargoCampesino);
				 * result.put("valorRecargoCampesino", valorRecargoCampesino);
				 * valorBase = valorBase + valorRecargoCampesino;
				 * 
				 * }
				 
				else if (variable.getNombre().equals("SUPER_DE_BANCOS")) {
					valorSuperBancos = Math.rint(Double.parseDouble(variable
							.getValor()) * valorFinalPrima / 100 * 100) / 100;
					cotizacion.setImpSuperBancos(valorSeguroCampesino);
					valorBase = valorBase + valorSuperBancos;

				}

				else if (variable.getNombre().equals("SUBTOTAL")) {
					valorSubTotal = Math.rint(valorBase * 100) / 100;

				} else if (variable.getNombre().equals("IVA")) {
					valorIva = Math.rint(Double.parseDouble(variable.getValor())* valorSubTotal / 100 * 100) / 100;
					cotizacion.setImpIva(valorIva);
				}

			}
			valorTotal = Math.rint((valorBase + valorIva) * 100) / 100;
			cotizacion.setTotalFactura(valorTotal);

			
			cotizacionDAO.editar(cotizacion);
		}
			else
				if (autorizar.equals("2")&&sd.getEstado().equals(estDAO.buscarPorNombre("Pendiente", "Descuento"))) 
				sd.setEstado(estDAO.buscarPorNombre("Denegado", "Descuento"));
			
			
			sdDAO.editar(sd);
			
			String link = request.getRequestURL().toString().replace("/SolicitudDescuentoController", "/respuesta.jsp?tipoRespuesta=SolicitudDescuento&a="+autorizar);
			
			
			
			response.sendRedirect(link);
		
		}*/
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String descripcion = request.getParameter("descripcion") == null ? "" : request.getParameter("descripcion");
			String porcentaje = request.getParameter("porcentaje") == null ? "" : request.getParameter("porcentaje");
			String estadoId = request.getParameter("estadoId") == null ? "" : request.getParameter("estadoId");
			String descuentoId = request.getParameter("descuentoId") == null ? "" : request.getParameter("descuentoId");
			String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
			String sucursalId = request.getParameter("sucursalId") == null ? "" : request.getParameter("sucursalId");
			String unidad_negocioId = request.getParameter("unidad_negocioId") == null ? "" : request.getParameter("unidad_negocioId");
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			JSONObject solicitudDescuentoJSONObject = new JSONObject();
			JSONArray solicitudDescuentoJSONArray = new JSONArray();

			SolicitudDescuento solicitudDescuento = new SolicitudDescuento();
			SolicitudDescuentoDAO solicitudDescuentoDAO = new SolicitudDescuentoDAO();
			CotizacionDAO cotizacionDAO = new CotizacionDAO();
			Cotizacion cotizacion = new Cotizacion();
			
			CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
			SolicitudDescuentoTransaction solicitudDescuentoTransaction = new SolicitudDescuentoTransaction();
			CotizacionTransaction cotizacionTransaction = new CotizacionTransaction();
			CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
			


			if (!codigo.equals(""))
				solicitudDescuento.setId(codigo);

			if(!descripcion.equals(""))
				solicitudDescuento.setDescripcion(descripcion);

			if(!porcentaje.equals(""))
				solicitudDescuento.setPorcentaje(porcentaje);

			if(!estadoId.equals("")){
				EstadoDAO estado = new EstadoDAO();
				solicitudDescuento.setEstado(estado.buscarPorId(estadoId));
				}
			
			if(!descuentoId.equals("")){
				DescuentoDAO descuento = new DescuentoDAO();
				solicitudDescuento.setDescuento(descuento.buscarPorId(descuentoId));
				}
			
			if(!cotizacionId.equals("")){
				
				solicitudDescuento.setCotizacion(cotizacionDAO.buscarPorId(cotizacionId));
				}
			
			if(!sucursalId.equals("")){
				SucursalDAO sucursal = new SucursalDAO();
				solicitudDescuento.setSucursal(sucursal.buscarPorId(sucursalId));
				}
			
			if(!unidad_negocioId.equals("")){
				UnidadNegocioDAO unidad = new UnidadNegocioDAO();
				solicitudDescuento.setUnidadNegocio(unidad.buscarPorId(unidad_negocioId));
				}

			if(tipoConsulta.equals("encontrarTodos")){ 
				
				
				List<SolicitudDescuento> results = solicitudDescuentoDAO.buscarTodos();
				int i=0;
				for(i=0; i<results.size(); i++){
					UsuarioDAO usuarioDAO=new UsuarioDAO();
					solicitudDescuento = results.get(i);
					solicitudDescuentoJSONObject.put("codigo", solicitudDescuento.getId());
					solicitudDescuentoJSONObject.put("descripcion", solicitudDescuento.getDescripcion());
					solicitudDescuentoJSONObject.put("porcentaje", solicitudDescuento.getPorcentaje());
					solicitudDescuentoJSONObject.put("estado", solicitudDescuento.getEstado().getNombre());
					solicitudDescuentoJSONObject.put("descuentoId", solicitudDescuento.getDescuento().getId());
					solicitudDescuentoJSONObject.put("cotizacionId", solicitudDescuento.getCotizacion().getId());
					solicitudDescuentoJSONObject.put("sucursalId", solicitudDescuento.getSucursal().getId());
					solicitudDescuentoJSONObject.put("unidad_negocioId", solicitudDescuento.getUnidadNegocio().getId());
					if(solicitudDescuento.getUsuarioId()!=null&&solicitudDescuento.getUsuarioId()!=new BigInteger("0"))
						solicitudDescuentoJSONObject.put("usuario", usuarioDAO.buscarPorId(solicitudDescuento.getUsuarioId().toString()).getEntidad().getNombreCompleto());
					else
						solicitudDescuentoJSONObject.put("usuario", "-");
					
					solicitudDescuentoJSONArray.add(solicitudDescuentoJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoSolicitudDescuento", solicitudDescuentoJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarDescuentosUsuario")){ 
				int i=0;
				List<SolicitudDescuento> results;
				
				Usuario usuario=(Usuario)request.getSession().getAttribute("usuario");
				if(usuario!=null&&usuario.getId()!=null){
					Entidad entidad=usuario.getEntidad();
					EmpleadoDAO empleadoDAO = new EmpleadoDAO();
					Empleado empleado=empleadoDAO.buscarPorEntidadId(entidad);
					
					
					if(empleado!=null&&empleado.getId()!=null){
						
						GrupoUsuarioAutorizacionDAO guaDAO= new GrupoUsuarioAutorizacionDAO();
						List<GrupoUsuarioAutorizacion> gua=guaDAO.buscarPorEmpleado(empleado);
						
						
						for (int j = 0; j < gua.size(); j++) {
							GrupoAutorizacion ga = gua.get(j).getGrupoAutorizacion();

							if (gua.get(j) != null && gua.get(j).getId() != null) {

								results = solicitudDescuentoDAO .buscarTodosPendientes();
								for (i = 0; i < results.size(); i++) {
									solicitudDescuento = results.get(i);
									if (solicitudDescuento.getDescuento().getGrupoAutorizacion().getId().equals(ga.getId())) {
										UsuarioDAO usuarioDAO = new UsuarioDAO();

										solicitudDescuentoJSONObject.put("codigo",solicitudDescuento.getId());
										solicitudDescuentoJSONObject.put("descripcion",solicitudDescuento.getDescripcion());
										solicitudDescuentoJSONObject.put("porcentaje",solicitudDescuento.getPorcentaje());
										solicitudDescuentoJSONObject.put("estado", solicitudDescuento.getEstado().getNombre());
										solicitudDescuentoJSONObject.put("descuentoId",solicitudDescuento.getDescuento().getId());
										solicitudDescuentoJSONObject.put("nombre", solicitudDescuento.getDescuento().getNombre());
										solicitudDescuentoJSONObject.put("cotizacionId",solicitudDescuento.getCotizacion().getId());
										solicitudDescuentoJSONObject.put("sucursalId",solicitudDescuento.getSucursal().getId());
										solicitudDescuentoJSONObject.put("unidad_negocioId",solicitudDescuento.getUnidadNegocio().getId());
										if (solicitudDescuento.getUsuarioId() != null && solicitudDescuento.getUsuarioId() != new BigInteger("0"))
											solicitudDescuentoJSONObject.put("usuario",usuarioDAO.buscarPorId(solicitudDescuento.getUsuarioId().toString()).getEntidad().getNombreCompleto());
										else
											solicitudDescuentoJSONObject.put("usuario", "-");
										
										String link = request.getRequestURL().toString();
										
										String urlAutorizar = link+ "?sdid="+ Utilitarios.numeroRandomico(4)+ solicitudDescuento.getId()+ Utilitarios.numeroRandomico(9);
										solicitudDescuentoJSONObject.put("urlAutorizar",urlAutorizar);
										

										solicitudDescuentoJSONArray.add(solicitudDescuentoJSONObject);
									}
								}

							}
							else {
								throw new Exception(
										"El empleado no puede autorizar descuentos");
							}
						}
						
					}
					else{
						throw new Exception("El usuario no puede ver solicitudes de descuento");
					}
					
					
				}
				else{
					throw new Exception("Inicie Sesi�n");
				}
				
				result.put("numRegistros",i);
				result.put("listadoSolicitudDescuento", solicitudDescuentoJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarPorId")){ 
				
				 solicitudDescuento = solicitudDescuentoDAO.buscarPorId(codigo);
				
					solicitudDescuentoJSONObject.put("codigo", solicitudDescuento.getId());
					solicitudDescuentoJSONObject.put("descripcion", solicitudDescuento.getDescripcion());
					solicitudDescuentoJSONObject.put("porcentaje", solicitudDescuento.getPorcentaje());
					solicitudDescuentoJSONObject.put("estado", solicitudDescuento.getEstado().getNombre());
					solicitudDescuentoJSONObject.put("descuentoId", solicitudDescuento.getDescuento().getId());
					solicitudDescuentoJSONObject.put("cotizacionId", solicitudDescuento.getCotizacion().getId());
					solicitudDescuentoJSONObject.put("sucursalId", solicitudDescuento.getSucursal().getId());
					solicitudDescuentoJSONObject.put("unidad_negocioId", solicitudDescuento.getUnidadNegocio().getId());
					solicitudDescuentoJSONArray.add(solicitudDescuentoJSONObject);
				
				result.put("numRegistros",1);
				result.put("listadoSolicitudDescuento", solicitudDescuentoJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarPorCotizacion")){ 
				
				 solicitudDescuento = solicitudDescuentoDAO.buscarPorCotizacion(cotizacionDAO.buscarPorId(cotizacionId)) ;
				
				 	UsuarioDAO usuarioDAO=new UsuarioDAO();
					solicitudDescuentoJSONObject.put("codigo", solicitudDescuento.getId());
					solicitudDescuentoJSONObject.put("descripcion", solicitudDescuento.getDescripcion());
					solicitudDescuentoJSONObject.put("porcentaje", solicitudDescuento.getPorcentaje());
					solicitudDescuentoJSONObject.put("estado", solicitudDescuento.getEstado().getNombre());
					solicitudDescuentoJSONObject.put("descuentoId", solicitudDescuento.getDescuento().getId());
					solicitudDescuentoJSONObject.put("cotizacionId", solicitudDescuento.getCotizacion().getId());
					solicitudDescuentoJSONObject.put("sucursalId", solicitudDescuento.getSucursal().getId());
					solicitudDescuentoJSONObject.put("unidad_negocioId", solicitudDescuento.getUnidadNegocio().getId());
					if(solicitudDescuento.getUsuarioId()!=null&&solicitudDescuento.getUsuarioId()!=new BigInteger("0"))
						solicitudDescuentoJSONObject.put("usuario", usuarioDAO.buscarPorId(solicitudDescuento.getUsuarioId().toString()).getEntidad().getNombreCompleto());
					
					solicitudDescuentoJSONArray.add(solicitudDescuentoJSONObject);
				
				result.put("numRegistros",1);
				result.put("listadoSolicitudDescuento", solicitudDescuentoJSONArray);
			}
			
			if(tipoConsulta.equals("eliminarPorCotizacion")){
				CotizacionDAO cDAO = new CotizacionDAO();
				cotizacion=cDAO.buscarPorId(cotizacionId);
				cotizacion.setValorDescuento(0);
				cotizacion.setSolicitudDescuentos(new ArrayList<SolicitudDescuento>());
				solicitudDescuentoDAO.eliminarPorCotizacion(cotizacion);
				cotizacionTransaction.editar(cotizacion);
			}
			
			
			if(tipoConsulta.equals("anularPorCotizacion")){
				CotizacionDAO cDAO = new CotizacionDAO();
				cotizacion=cDAO.buscarPorId(cotizacionId);
				cotizacion.setValorDescuento(0);
				cotizacion.setSolicitudDescuentos(new ArrayList<SolicitudDescuento>());
				HttpSession session = request.getSession(true);
				Usuario usuario = (Usuario) session.getAttribute("usuario");
				
				cotizacion=cotizacionTransaction.editar(cotizacion);
				solicitudDescuento = solicitudDescuentoDAO.buscarPorCotizacion(cotizacion);
				solicitudDescuento.setComentario("Eliminada por el usuario que solicito, "+usuario.getEntidad().getNombreCompleto());
				solicitudDescuento.setUsuarioId(new BigInteger(usuario.getId()));
				EstadoDAO eDAO=new EstadoDAO();
				Estado estado=eDAO.buscarPorNombreClase("Eliminada", "Descuento");
				solicitudDescuento.setEstado(estado);
				solicitudDescuento=solicitudDescuentoTransaction.editar(solicitudDescuento);
			}
			
			
			if(tipoConsulta.equals("crear")){
				solicitudDescuento=solicitudDescuentoTransaction.crear(solicitudDescuento);
			}

			if(tipoConsulta.equals("actualizar"))
				solicitudDescuentoTransaction.editar(solicitudDescuento);

			if(tipoConsulta.equals("eliminar"))
				solicitudDescuentoTransaction.eliminar(solicitudDescuento);
			
			if(tipoConsulta.equals("ActualizarEstado"))
				solicitudDescuentoDAO.actualizarEstado(codigo,estadoId) ;

			if(tipoConsulta.equals("cargarPorIdRes")){
				String solicitudDescuentoId = request.getParameter("id") == null ? "" : request.getParameter("id");
				//####(id)#########
				solicitudDescuentoId=solicitudDescuentoId.substring(4);
				for(int i=0;i<9;i++){
					solicitudDescuentoId=solicitudDescuentoId.substring(0,solicitudDescuentoId.length()-1);
				}
				
				solicitudDescuento=solicitudDescuentoDAO.buscarPorId(solicitudDescuentoId);
				
				JSONObject sdJSON = new JSONObject();
				sdJSON.put("cotizacionId", solicitudDescuento.getCotizacion().getId());
				sdJSON.put("estado", solicitudDescuento.getEstado().getNombre());
				sdJSON.put("descripcion", solicitudDescuento.getDescripcion());
				sdJSON.put("descuento", solicitudDescuento.getDescuento().getNombre());
				sdJSON.put("motivo", solicitudDescuento.getMotivoDescuento().getNombre());
				sdJSON.put("porcentaje", solicitudDescuento.getPorcentaje());
				sdJSON.put("comentario", solicitudDescuento.getComentario());
				
				result.put("solicitudDescuento", sdJSON);
			}
			
			if(tipoConsulta.equals("actualizarEstadoRes")){
				String solicitudDescuentoId = request.getParameter("id") == null ? "" : request.getParameter("id");
				String comentario = request.getParameter("comentario") == null ? "" : request.getParameter("comentario");
				String estado = request.getParameter("estado") == null ? "" : request.getParameter("estado");
				//####(id)#########
				if(solicitudDescuentoId.length()<14)
					throw new Exception("Identificador Invalido!");
				
				solicitudDescuentoId=solicitudDescuentoId.substring(4);
				for(int i=0;i<9;i++){
					solicitudDescuentoId=solicitudDescuentoId.substring(0,solicitudDescuentoId.length()-1);
				}
				EstadoDAO eDAO = new EstadoDAO();
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				
				if(usuario==null||usuario.getId()==null||usuario.getId().equals(""))
					throw new Exception("Inicie Sesion!");
				
				if(solicitudDescuentoId==null||solicitudDescuentoId.equals(""))
					throw new Exception("Identificador Invalido");
				
				solicitudDescuento=solicitudDescuentoDAO.buscarPorId(solicitudDescuentoId);
				
				if(solicitudDescuento==null||solicitudDescuento.getId()==null)
					throw new Exception("No existe la solicitud de descuento!");
				
				solicitudDescuento.setUsuarioId(new BigInteger(usuario.getId()));
				solicitudDescuento.setComentario(comentario);
				
				EmpleadoDAO empleadoDAO=new EmpleadoDAO();
				Empleado emp=empleadoDAO.buscarPorEntidadId(usuario.getEntidad());
				
				String cargoAutoriza=solicitudDescuento.getDescuento().getCargoAutoriza();
				
				if(emp.getId()==null||emp.getCargo()==null)
					throw new Exception("El usuario no tiene puede responder Solicitudes de Descuento!");
					
				if(!emp.getCargo().getNombreGenerico().equals(cargoAutoriza))
					throw new Exception("El usuario no esta autorizado para responder la solicitud de descuento");
				
				if(!solicitudDescuento.getEstado().getNombre().equals("Pendiente"))
					throw new Exception("La solicitud de descuento ya fue procesada, el estado es "+solicitudDescuento.getEstado().getNombre());
				if(estado.equals("1")){
					cotizacion=solicitudDescuento.getCotizacion();
					solicitudDescuento.setEstado(eDAO.buscarPorNombreClase("Autorizado", "Descuento"));
					
					cotizacion.setPrimaNetaTotal(""+ (cotizacion.getPrimaOrigen()* (100-new Double(solicitudDescuento.getPorcentaje())) / 100));
					cotizacion.setValorDescuento(new Double(solicitudDescuento.getPorcentaje()));
					
					/* 
					 * 
					 * 
					 * 
					 * calculo impuestos
					 * 
					 * 
					 * 
					 * */
					VariableSistema variable = new VariableSistema();
					VariableSistemaDAO variableDAO = new VariableSistemaDAO();
					double valorFinalPrima = new Double(cotizacion.getPrimaNetaTotal());

					TipoVariableDAO tipoVariableDao = new TipoVariableDAO();
					TipoVariable tipoVariable = tipoVariableDao.buscarPorId("3");
					List<VariableSistema> variablesistema = variableDAO
							.buscarTipoVariable(tipoVariable);
					double valorBase = 0;
					double valorDerechosEmision = 0;
					double valorSeguroCampesino = 0;
					double valorSuperBancos = 0;
					double valorIva = 0;
					double valorSubTotal = 0;
					double valorTotal = 0;

					for (int i = 0; i < variablesistema.size(); i++) {
						variable = (VariableSistema) variablesistema.get(i);
						if (variable.getNombre().equals("DERECHOS_EMISION")) {
							valorBase = Double.parseDouble(variable.getValor()) + valorFinalPrima;
							valorDerechosEmision = Double.parseDouble(variable.getValor());
							cotizacion.setImpDerechoEmision(valorDerechosEmision);

						} else if (variable.getNombre().equals("SEGURO_CAMPESINO")) {
							valorSeguroCampesino = Math.rint(Double.parseDouble(variable.getValor())* valorFinalPrima/ 100 * 100) / 100;
							valorBase = valorBase + valorSeguroCampesino;
							cotizacion.setImpSeguroCampesino(valorSeguroCampesino);
						}
						/*
						 * else
						 * if(variable.getNombre().equals("RECARGO_SEGURO_CAMPESINO")){
						 * InquiredServiceInterfaceService servicio = new
						 * InquiredServiceInterfaceService(); valorRecargoCampesino =
						 * servicio
						 * .getInquiredServiceInterfacePort().consultarRecargoSeguroAgricola
						 * (cliente.getEntidad().getIdentificacion(), valorFinalPrima);
						 * cotizacion.setImpDerechoEmision(valorRecargoCampesino);
						 * result.put("valorRecargoCampesino", valorRecargoCampesino);
						 * valorBase = valorBase + valorRecargoCampesino;
						 * 
						 * }
						 */
						else if (variable.getNombre().equals("SUPER_DE_BANCOS")) {
							valorSuperBancos = Math.rint(Double.parseDouble(variable
									.getValor()) * valorFinalPrima / 100 * 100) / 100;
							cotizacion.setImpSuperBancos(valorSeguroCampesino);
							valorBase = valorBase + valorSuperBancos;

						}

						else if (variable.getNombre().equals("SUBTOTAL")) {
							valorSubTotal = Math.rint(valorBase * 100) / 100;

						} else if (variable.getNombre().equals("IVA")) {
							valorIva = Math.rint(Double.parseDouble(variable.getValor())* valorSubTotal / 100 * 100) / 100;
							cotizacion.setImpIva(valorIva);
						}

					}
					valorTotal = Math.rint((valorBase + valorIva) * 100) / 100;
					cotizacion.setTotalFactura(valorTotal);

					/**/
					solicitudDescuento=solicitudDescuentoTransaction.editar(solicitudDescuento);
					cotizacionTransaction.editar(cotizacion);
					
					/**//**//**//**///detalles 
					
					Double valorDescuento=1-(new Double(solicitudDescuento.getPorcentaje())/100);
					
					List<CotizacionDetalle> detalles=cotizacion.getCotizacionDetalles();
					CotizacionDetalleDAO detalleDAO=new CotizacionDetalleDAO();
					CotizacionCoberturaDAO coberturaDAO=new CotizacionCoberturaDAO(); 
					for(CotizacionDetalle detalle:detalles){
						detalle.setPrimaNetaItem(detalle.getPrimaNetaItemOrigen()*valorDescuento);
						detalle=cotizacionDetalleTransaction.editar(detalle);
						List<CotizacionCobertura> coberturas = detalle.getCotizacionCoberturas();
						for(CotizacionCobertura cobertura: coberturas){
							cobertura.setValorPrima(cobertura.getValorPrimaOrigen()*valorDescuento);
							cobertura=cotizacionCoberturaTransaction.editar(cobertura);
						}
					}					
				}
				if(estado.equals("0")){
					solicitudDescuento.setEstado(eDAO.buscarPorNombreClase("Anulado", "Descuento"));
					solicitudDescuento.setComentario(comentario);
					solicitudDescuento=solicitudDescuentoTransaction.editar(solicitudDescuento);
					
				}
				
				result.put("estado",solicitudDescuento.getEstado().getNombre());
				
				String rutaPlantilla = this.getServletContext().getRealPath("") + "/static/plantillas/solicitudDescuentoAprobada.html";
				FileReader fr = null;
				BufferedReader br = null;

				String cuerpoMail = "";
				String link = request.getRequestURL().toString();

				try {
					File archivo = new File(rutaPlantilla);
					fr = new FileReader(archivo);
					br = new BufferedReader(fr);

					String linea;

					while ((linea = br.readLine()) != null) {
						cuerpoMail = cuerpoMail + linea;
					}
					
					//cuerpoMail = cuerpoMail.replace("#fechaSolicitud#",solicitudDescuento.get.toString());
					
					cuerpoMail = cuerpoMail.replace("#urlImagenes#",link.replace("/SolicitudDescuentoController", ""));
					
					cuerpoMail = cuerpoMail.replace("#numeroCotizacion#",solicitudDescuento.getCotizacion().getId());
					
					cuerpoMail = cuerpoMail.replace("#estado#",solicitudDescuento.getEstado().getNombre());
					
					cuerpoMail = cuerpoMail.replace("#comentario#",solicitudDescuento.getComentario());
										
					cuerpoMail = cuerpoMail.replace("#usuarioResponde#",usuario.getEntidad().getNombreCompleto());
					
					cuerpoMail = cuerpoMail.replace("#urlImagenes#",
							link.replace("/SolicitudDescuentoController", ""));
					
					cuerpoMail = cuerpoMail.replace("#urlCotizador#",
							link.replace("/SolicitudDescuentoController", ""));
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					br.close();
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UsuarioDAO uDAO = new UsuarioDAO();
				Usuario solicitante=uDAO.buscarPorId(solicitudDescuento.getUsuarioId().toString());
				Utilitarios.envioMail(solicitante.getEntidad().getMail(),
						"Respuesta de Solicitud de Descuento", cuerpoMail);
			
				
			}
			


			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
		}catch(Exception e){
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			e.printStackTrace();

		}		
	}

}
