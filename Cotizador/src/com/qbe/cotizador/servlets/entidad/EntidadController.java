package com.qbe.cotizador.servlets.entidad;

import com.qbe.cotizador.dao.entidad.ActividadEconomicaDAO;
import com.qbe.cotizador.dao.entidad.AgenteDAO;
import com.qbe.cotizador.dao.entidad.ClienteDAO;
import com.qbe.cotizador.dao.entidad.DireccionDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.TipoEntidadDAO;
import com.qbe.cotizador.dao.entidad.TipoIdentificacionDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.dao.seguridad.CredencialDAO;
import com.qbe.cotizador.dao.seguridad.RolDAO;
import com.qbe.cotizador.dao.seguridad.UsuarioRolDAO;
import com.qbe.cotizador.model.*;
import com.qbe.cotizador.servicios.QBE.cliente.WebServiceCotizadorImplService;
import com.qbe.cotizador.transaction.entidad.AgenteTransaction;
import com.qbe.cotizador.transaction.entidad.ClienteTransaction;
import com.qbe.cotizador.transaction.entidad.EntidadTransaction;
import com.qbe.cotizador.transaction.entidad.UsuarioTransaction;
import com.qbe.cotizador.transaction.seguridad.CredencialTransaction;
import com.qbe.cotizador.transaction.seguridad.UsuarioRolTransaction;
import com.qbe.cotizador.util.Utilitarios;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

/**
 * Servlet implementation class AgenteController
 */
@WebServlet("/EntidadController")
public class EntidadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EntidadController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {
			String codigoEnsurance = request.getParameter("codigoEnsurance") == null ? "" : request.getParameter("codigoEnsurance");
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String nombreCompleto = request.getParameter("nombreCompleto") == null ? "" : request.getParameter("nombreCompleto").toUpperCase();
			String tipoIdentificacionId = request.getParameter("tipoIdentificacion") == null ? "" : request.getParameter("tipoIdentificacion");
			String identificacion = request.getParameter("identificacion") == null ? "" : request.getParameter("identificacion");
			String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre").toUpperCase();
			String apellido = request.getParameter("apellido") == null ? "" : request.getParameter("apellido").toUpperCase();
			String login = request.getParameter("login") == null ? "" : request.getParameter("login");
			String password = request.getParameter("password") == null ? "" : request.getParameter("password");
			String mail = request.getParameter("mail") == null ? "" : request.getParameter("mail");
			String telefono = request.getParameter("telefono") == null ? "" : request.getParameter("telefono");
			String celular = request.getParameter("celular") == null ? "" : request.getParameter("celular");
			String actividadEconomicaId = request.getParameter("actividadEconomica") == null ? "" : request.getParameter("actividadEconomica");
			String activo = request.getParameter("activo") == null ? "" : request.getParameter("activo");
			String tipoEntidad = request.getParameter("tipoEntidad") == null ? "" : request.getParameter("tipoEntidad");
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String rolId = request.getParameter("rol") == null ? "" : request.getParameter("rol");

			// para Agente
			String comisionVariable = request.getParameter("comisionVariable") == null ? "" : request.getParameter("comisionVariable");
			String comisionVH = request.getParameter("comisionVH") == null ? "" : request.getParameter("comisionVH");
			String pymes = request.getParameter("pymes") == null ? "" : request.getParameter("pymes");
			String agenteEnsurance = request.getParameter("agenteEnsurance") == null ? "" : request.getParameter("agenteEnsurance");
			String comision1 = request.getParameter("comision1") == null ? "" : request.getParameter("comision1");
			String comision2 = request.getParameter("comision2") == null ? "" : request.getParameter("comision2");
			String comision3 = request.getParameter("comision3") == null ? "" : request.getParameter("comision3");
			String ramo = request.getParameter("ramo") == null ? "" : request.getParameter("ramo");
			String credencial = request.getParameter("credencial") == null ? "" : request.getParameter("credencial");
			
			EntidadTransaction entidadTransaction = new EntidadTransaction();
			AgenteTransaction agenteTransaction = new AgenteTransaction();
			ClienteTransaction clienteTransaction = new ClienteTransaction();
			UsuarioTransaction usuarioTransaction = new UsuarioTransaction();
			CredencialTransaction credencialTransaction = new CredencialTransaction();
			UsuarioRolTransaction usuarioRolTransaction = new UsuarioRolTransaction();

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Entidad entidad = new Entidad();
			EntidadDAO entidadDAO = new EntidadDAO();

			Agente agente = new Agente();
			AgenteDAO agenteDAO = new AgenteDAO();

			Cliente cliente = new Cliente();
			ClienteDAO clienteDAO = new ClienteDAO();

			if (!codigoEnsurance.equals(""))
				entidad.setEntEnsurance(codigoEnsurance);

			if (!codigo.equals(""))
				entidad.setId(codigo);

			if (!nombreCompleto.equals("")){
				entidad.setNombreCompleto(nombreCompleto);
			}

			if(tipoEntidad.equals("usuarios"))
				entidad.setNombreCompleto(nombre + " " + apellido);
			
			if(tipoEntidad.equals("agentes"))
				entidad.setNombreCompleto(nombre);
			
			if(tipoEntidad.equals("agentes"))
				entidad.setNombres(nombre);

			if (!tipoIdentificacionId.equals("")) {
				TipoIdentificacionDAO tipoIdentificacionDAO = new TipoIdentificacionDAO();
				entidad.setTipoIdentificacion(tipoIdentificacionDAO.buscarPorId(tipoIdentificacionId));
				TipoEntidadDAO tipoEntidadDAO = new TipoEntidadDAO();
				
				if(tipoIdentificacionId.equalsIgnoreCase("4"))
					entidad.setTipoEntidad(tipoEntidadDAO.buscarPorId("2"));
				else					
					entidad.setTipoEntidad(tipoEntidadDAO.buscarPorId("1"));
			}

			if (!identificacion.equals("")){
				if(entidadDAO.buscarPorIdentificacion(identificacion) == 0);				
					entidad.setIdentificacion(identificacion);
					
			}
			
			if (!mail.equals(""))
				entidad.setMail(mail);
			
			if (!telefono.equals(""))
				entidad.setTelefono(telefono);
			
			if (!celular.equals(""))
				entidad.setCelular(celular);

			if (!nombre.equals(""))
				entidad.setNombres(nombre);
			
			if (!apellido.equals(""))
			entidad.setApellidos(apellido);
			
			if(nombreCompleto.equals("")){
				nombreCompleto = nombre + " "+apellido;				
				entidad.setNombreCompleto(nombreCompleto);
			}

			if (!mail.equals(""))
				entidad.setMail(mail);
			
			//para cargar por identificacion EVALDEZ
				if(tipoConsulta.equals("cargarPorIdentificacion")&&!identificacion.equals("")){
							result.put("entidad", cargarPorIdentificacion(identificacion));
						}
						
			//para cargar por identificacion de Servicio web al ensurance EVALDEZ
						if(tipoConsulta.equals("cargarPorIdentificacionEnsurance")&&!identificacion.equals("")){
							
						// Obtener datos del usuario desde ensurance
							WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
							String resultado = webService.getWebServiceCotizadorImplPort().datosUsuario(identificacion);
							JSONObject entidaEnsurance=cargarPorIdentificacionWS(resultado);
							entidad=entidadDAO.buscarEntidadPorIdentificacion(identificacion);
							DireccionDAO dDAO=new DireccionDAO();
							JSONObject datosFactura=new JSONObject();
							if(entidad!=null&&entidad.getId()!=null){
								entidaEnsurance.put("codigo", entidad.getId());
								entidaEnsurance.put("mail", entidad.getMail());
								datosFactura.put("telefono", entidad.getTelefono());
								datosFactura.put("celular", entidad.getCelular());
								datosFactura.put("email", entidad.getMail());
								datosFactura.put("nombre", entidad.getNombres());
								datosFactura.put("nombreCompleto", entidad.getNombreCompleto());
								datosFactura.put("apellido", entidad.getApellidos());
								datosFactura.put("identificacion", entidad.getIdentificacion());
								datosFactura.put("tipoIdentificacion", entidad.getTipoIdentificacion().getId());
								List<Direccion> direcciones=dDAO.buscarPorEntidadId(entidad);
								
								if(direcciones.size()>0){
									if(direcciones.get(0)!=null){
										Direccion direccion=direcciones.get(0);
										
										if(direcciones.get(0).getZona()!=null)
											datosFactura.put("zona", direcciones.get(0).getZona().getId());
																				
										if(direccion.getZona().getId().equals("1")){//urbana
										
											if(direcciones.get(0).getCiudad()!=null)
												datosFactura.put("ciudad", direcciones.get(0).getCiudad().getId());
											
											if(direcciones.get(0).getCiudad().getProvincia()!=null)
												datosFactura.put("provincia", direcciones.get(0).getCiudad().getProvincia().getId());
																					
											if(direcciones.get(0).getCallePrincipal()!=null)
												datosFactura.put("callePrincipal", direcciones.get(0).getCallePrincipal());
											
											if(direcciones.get(0).getCalleSecundaria()!=null)
												datosFactura.put("calleSecundaria", direcciones.get(0).getCalleSecundaria());
											
											if(direcciones.get(0).getNumero()!=null)
												datosFactura.put("numero", direcciones.get(0).getNumero());
										}
										result.put("datosFactura", datosFactura);
										
										if(direccion.getZona().getId().equals("2")){//rural
											
											if(direcciones.get(0).getParroquia()!=null)
												datosFactura.put("parroquia", direcciones.get(0).getParroquia().getId());
											
											if(direcciones.get(0).getParroquia().getCanton()!=null)
												datosFactura.put("canton", direcciones.get(0).getParroquia().getCanton().getId());
											
											if(direcciones.get(0).getParroquia().getCanton().getProvincia()!=null)
												datosFactura.put("provincia", direcciones.get(0).getParroquia().getCanton().getProvincia().getId());
																					
											if(direcciones.get(0).getCallePrincipal()!=null)
												datosFactura.put("callePrincipal", direcciones.get(0).getCallePrincipal());
											
											if(direcciones.get(0).getCalleSecundaria()!=null)
												datosFactura.put("calleSecundaria", direcciones.get(0).getCalleSecundaria());
											
											if(direcciones.get(0).getNumero()!=null)
												datosFactura.put("numero", direcciones.get(0).getNumero());
										
										}
										result.put("datosFactura", datosFactura);
									}
								}
								
							}
							result.put("entidad",entidaEnsurance );						
						}

			// Para Agentes
			if (comisionVariable.equals("1"))
				agente.setComisionVariable(true);
			else if (comisionVariable.equals("0"))
				agente.setComisionVariable(false);

			if (!comisionVH.equals(""))
				agente.setComisionVh(Double.parseDouble(comisionVH));
			if (!pymes.equals(""))
				agente.setComisionPymes(Double.parseDouble(pymes));
			if (!agenteEnsurance.equals(""))
				agente.setAgeEnsurance(agenteEnsurance);				
			if (!comision1.equals(""))
				agente.setComision1(Double.parseDouble(comision1));
			if (!comision2.equals(""))
				agente.setComision2(Double.parseDouble(comision2));
			if (!comision3.equals(""))
				agente.setComision3(Double.parseDouble(comision3));
			if (ramo.equals("1"))
				agente.setRamoMultiriesgo(true);
			else if (ramo.equals("0"))
				agente.setRamoMultiriesgo(false);

			if (!credencial.equals(""))
				agente.setCredencial(credencial);

			if (nombre.equals("") && tipoEntidad.equals("agentes"))
				entidad.setNombres("");
			if (apellido.equals("") && tipoEntidad.equals("agentes"))
				entidad.setApellidos("");
			if (agenteEnsurance.equals("") && tipoEntidad.equals("agentes"))
				entidad.setEntEnsurance("");

		
			if (activo.equals("1")){
				agente.setActivo(true);
				cliente.setActivo(true);
			}else if(!tipoConsulta.equals("eliminar")){
				agente.setActivo(false);
				cliente.setActivo(false);
				}

			/*
			 * T R A N S A C C I O N E S D E A G E N T E S
			 */

			if (tipoEntidad.equals("agentes")) {
				if (tipoConsulta.equals("encontrarTodos")) {
					List<Agente> results = agenteDAO.buscarTodos();
					int i = 0;
					for (i = 0; i < results.size(); i++) {
						agente = results.get(i);
						entidadJSONObject.put("codigo", agente.getEntidad().getId());
						entidadJSONObject.put("codigoEnsurance", agente.getEntidad().getEntEnsurance());
						entidadJSONObject.put("identificacion", agente.getEntidad().getIdentificacion());
						entidadJSONObject.put("tipoIdentificacion", agente.getEntidad().getTipoIdentificacion().getNombre());
						entidadJSONObject.put("nombre", agente.getEntidad().getNombreCompleto());
						entidadJSONObject.put("apellido", agente.getEntidad().getApellidos());		
						entidadJSONObject.put("mail", agente.getEntidad().getMail());
						//agente
						entidadJSONObject.put("agenteEnsurance", agente.getAgeEnsurance());
						entidadJSONObject.put("comisionVariable", agente.getComisionVariable());
						entidadJSONObject.put("pymes", agente.getComisionPymes());
						entidadJSONObject.put("comisionVH", agente.getComisionVh());
						entidadJSONObject.put("comision1", agente.getComision1());
						entidadJSONObject.put("comision2", agente.getComision2());
						entidadJSONObject.put("comision3", agente.getComision3());
						entidadJSONObject.put("ramo", agente.getRamoMultiriesgo());
						entidadJSONObject.put("credencial", agente.getCredencial());
						 
						if(agente.getActivo())
							entidadJSONObject.put("activo", "Si");
						else
							entidadJSONObject.put("activo", "No");			
						
	
						entidadJSONArray.add(entidadJSONObject);
					}
					result.put("numRegistros", i);
					result.put("listadoAgente", entidadJSONArray);

					ActividadEconomicaDAO actividadEconomicaDAO = new ActividadEconomicaDAO();
					ActividadEconomica actividadEconomica = new ActividadEconomica();

					JSONObject actividadEconomicaJSONObject = new JSONObject();
					JSONArray actividadEconomicaJSONArray = new JSONArray();

					List<ActividadEconomica> listaActividadEconomica = actividadEconomicaDAO
							.buscarTodos();

					for (i = 0; i < listaActividadEconomica.size(); i++) {
						actividadEconomica = listaActividadEconomica.get(i);
						actividadEconomicaJSONObject.put("codigo",
								actividadEconomica.getId());
						actividadEconomicaJSONObject.put("nombre",
								actividadEconomica.getNombre());

						actividadEconomicaJSONArray
								.add(actividadEconomicaJSONObject);
					}

					result.put("listadoActividadEconomica",
							actividadEconomicaJSONArray);

					TipoIdentificacionDAO tipoIndentificacionDAO = new TipoIdentificacionDAO();
					TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();

					JSONObject tipoIdentificacionJSONObject = new JSONObject();
					JSONArray tipoIdentificacionJSONArray = new JSONArray();

					List<TipoIdentificacion> listadoTipoIdentificacion = tipoIndentificacionDAO
							.buscarTodos();

					for (i = 0; i < listadoTipoIdentificacion.size(); i++) {
						tipoIdentificacion = listadoTipoIdentificacion.get(i);
						tipoIdentificacionJSONObject.put("codigo", tipoIdentificacion.getId());
						tipoIdentificacionJSONObject.put("nombre", tipoIdentificacion.getNombre());

						tipoIdentificacionJSONArray.add(tipoIdentificacionJSONObject);
					}

					result.put("listadoTipoIdentificacion",
							tipoIdentificacionJSONArray);
				}

				if (tipoConsulta.equals("crear")) {
					Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
					entidad.setFechaCreacion(fechaActual);
					
					entidad = entidadTransaction.crear(entidad);

					if (!entidad.getId().equals("")
							&& !entidad.getId().equals(null))
						agente.setEntidad(entidad);

					agenteTransaction.crear(agente);
				}

				if(tipoConsulta.equals("cambioEstado")){
					agente = agenteDAO.buscarPorEntidadId(entidad);
					if (activo.equals("1")){
						agente.setActivo(false);
					}else if (activo.equals("0")){
						agente.setActivo(true);						
					}
					agenteTransaction.editar(agente);
					
					}
				
				if (tipoConsulta.equals("eliminar")) {
					entidad = entidadDAO.buscarPorId(codigo);
					entidadTransaction.eliminar(entidad);
					
				}

				if (tipoConsulta.equals("actualizar")) {
					
					entidad = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
					agente = agenteDAO.buscarPorEntidadId(entidad);

					agente.setComisionVh(Double.parseDouble(comisionVH));
					agente.setComisionPymes(Double.parseDouble(pymes));
					agente.setAgeEnsurance(agenteEnsurance);
					agente.setComision1(Double.parseDouble(comision1));
					agente.setComision2(Double.parseDouble(comision2));
					agente.setComision3(Double.parseDouble(comision3));
					agente.setCredencial(credencial);

					if (ramo.equals("1")) {
						agente.setRamoMultiriesgo(true);
					} else {
						agente.setRamoMultiriesgo(false);
					}
					if (comisionVariable.equals("1")) {
						agente.setComisionVariable(true);
					} else {
						agente.setComisionVariable(false);
					}

					if (activo.equals("1")) {
						agente.setActivo(true);
					}else if(activo.equals("0")) {
						agente.setActivo(false);
					}
					agenteTransaction.editar(agente);
				}
			}
			/*
			 * T R A N S A C C I O N E S D E C L I E N T E S
			 */

			if (tipoEntidad.equals("clientes")) {
				if (tipoConsulta.equals("encontrarTodos")) {
					List<Cliente> results = clienteDAO.buscarTodos();
							int i=0;
							for(i=0; i<results.size(); i++){
								cliente = results.get(i);
								entidadJSONObject.put("codigo", cliente.getEntidad().getId());
								entidadJSONObject.put("codigoEnsurance", cliente.getEntidad().getEntEnsurance());
								entidadJSONObject.put("identificacion", cliente.getEntidad().getIdentificacion());
								entidadJSONObject.put("tipoIdentificacion", cliente.getEntidad().getTipoIdentificacion().getNombre());
								entidadJSONObject.put("nombre", cliente.getEntidad().getNombres());
								entidadJSONObject.put("apellido", cliente.getEntidad().getApellidos());
								entidadJSONObject.put("actividadEconomica", cliente.getActividadEconomica().getNombre());
								entidadJSONObject.put("mail", cliente.getEntidad().getMail());
								if(cliente.getActivo())
									entidadJSONObject.put("activo", "Si");
								else
									entidadJSONObject.put("activo", "No");			

								entidadJSONArray.add(entidadJSONObject);
							}
							result.put("numRegistros",i);
							result.put("listadoCliente", entidadJSONArray);
							
							ActividadEconomicaDAO actividadEconomicaDAO = new ActividadEconomicaDAO();
							ActividadEconomica actividadEconomica = new ActividadEconomica();
							
							JSONObject actividadEconomicaJSONObject = new JSONObject();
							JSONArray actividadEconomicaJSONArray = new JSONArray();
							
							List<ActividadEconomica> listaActividadEconomica = actividadEconomicaDAO.buscarTodos();
							
							for(i = 0; i < listaActividadEconomica.size(); i++ ){
								actividadEconomica = listaActividadEconomica.get(i);
								actividadEconomicaJSONObject.put("codigo", actividadEconomica.getId());
								actividadEconomicaJSONObject.put("nombre", actividadEconomica.getNombre());
								
								actividadEconomicaJSONArray.add(actividadEconomicaJSONObject);
							}
							
							result.put("listadoActividadEconomica", actividadEconomicaJSONArray);
							
							
							TipoIdentificacionDAO tipoIndentificacionDAO = new TipoIdentificacionDAO();
							TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
							
							JSONObject tipoIdentificacionJSONObject = new JSONObject();
							JSONArray tipoIdentificacionJSONArray = new JSONArray();
							
							List<TipoIdentificacion> listadoTipoIdentificacion = tipoIndentificacionDAO.buscarTodos();
							
							for(i = 0; i < listadoTipoIdentificacion.size(); i++ ){
								tipoIdentificacion = listadoTipoIdentificacion.get(i);
								tipoIdentificacionJSONObject.put("codigo", tipoIdentificacion.getId());
								tipoIdentificacionJSONObject.put("nombre", tipoIdentificacion.getNombre());
								tipoIdentificacionJSONArray.add(tipoIdentificacionJSONObject);
							}
							
							result.put("listadoTipoIdentificacion", tipoIdentificacionJSONArray);
						}
			
				
						if(tipoConsulta.equals("crear")){
							Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
							entidad.setFechaCreacion(fechaActual);
							entidad = entidadTransaction.crear(entidad);
							
							if(!entidad.getId().equals("") && !entidad.getId().equals(null))
								cliente.setEntidad(entidad);
							
							if (!actividadEconomicaId.equals("")){
								ActividadEconomicaDAO actividadEconomicaDAO = new ActividadEconomicaDAO();
								cliente.setActividadEconomica(actividadEconomicaDAO.buscarPorId(actividadEconomicaId));
							}
							clienteTransaction.crear(cliente);
							
						}
						
						if(tipoConsulta.equals("cambioEstado")){
							cliente = clienteDAO.buscarPorEntidadId(entidad);
							if (activo.equals("1")){
								cliente.setActivo(false);
							}else if (activo.equals("0")){
								cliente.setActivo(true);						
							}
							clienteTransaction.editar(cliente);
							
						}

				if (tipoConsulta.equals("eliminar")) {
					entidad = entidadDAO.buscarPorId(codigo);
					entidadTransaction.eliminar(entidad);
				}

				if (tipoConsulta.equals("actualizar")) {
					entidad = entidadTransaction.editar(entidad);
							cliente = clienteDAO.buscarPorEntidadId(entidad);
							
							if (!actividadEconomicaId.equals("")){
								ActividadEconomicaDAO actividadEconomicaDAO = new ActividadEconomicaDAO();
								cliente.setActividadEconomica(actividadEconomicaDAO.buscarPorId(actividadEconomicaId));
							}
							
							if (activo.equals("1")){
								cliente.setActivo(true);
							}else if(activo.equals("0")) {
								cliente.setActivo(false);
					}

					clienteTransaction.editar(cliente);
				}
			}

			/*
			 * T R A N S A C C I O N E S D E USUARIOS
			 */

			if (tipoEntidad.equals("usuarios")) {
				Usuario usuario = new Usuario();
				Credencial credenciales = new Credencial();
				CredencialDAO credencialDAO = new CredencialDAO();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				boolean elActivo;
				if (tipoConsulta.equals("encontrarTodos")) {
					List<Usuario> results = usuarioDAO.buscarTodos();
					int i = 0;
					for (i = 0; i < results.size(); i++) {
						usuario = results.get(i);
						entidadJSONObject.put("codigo", usuario.getEntidad().getId());
						entidadJSONObject.put("codigoEnsurance", usuario.getEntidad().getEntEnsurance());
						entidadJSONObject.put("identificacion", usuario.getEntidad().getIdentificacion());
						entidadJSONObject.put("tipoIdentificacion", usuario.getEntidad().getTipoIdentificacion().getNombre());
						entidadJSONObject.put("nombre", usuario.getEntidad().getNombres());
						entidadJSONObject.put("apellido", usuario.getEntidad().getApellidos());
						entidadJSONObject.put("login", usuario.getLogin());
						entidadJSONObject.put("password", usuario.getCredencials().get(0).getClave());
						entidadJSONObject.put("mail", usuario.getEntidad().getMail());
						if (usuario.getActivo())
							entidadJSONObject.put("activo", "Si");
						else
							entidadJSONObject.put("activo", "No");

						entidadJSONArray.add(entidadJSONObject);
					}
					result.put("numRegistros", i);
					result.put("listadoUsuario", entidadJSONArray);

					TipoIdentificacionDAO tipoIndentificacionDAO = new TipoIdentificacionDAO();
					TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();

					JSONObject tipoIdentificacionJSONObject = new JSONObject();
					JSONArray tipoIdentificacionJSONArray = new JSONArray();

					List<TipoIdentificacion> listadoTipoIdentificacion = tipoIndentificacionDAO
							.buscarTodos();

					for (i = 0; i < listadoTipoIdentificacion.size(); i++) {
						tipoIdentificacion = listadoTipoIdentificacion.get(i);
						tipoIdentificacionJSONObject.put("codigo",
								tipoIdentificacion.getId());
						tipoIdentificacionJSONObject.put("nombre",
								tipoIdentificacion.getNombre());

						tipoIdentificacionJSONArray
								.add(tipoIdentificacionJSONObject);
					}

					result.put("listadoTipoIdentificacion",
							tipoIdentificacionJSONArray);
				}

				if (tipoConsulta.equals("crear")) {
					
					Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
					entidad.setFechaCreacion(fechaActual);
					// Verificamos que la entidad y el usuario no se repitan
					Entidad entidadTemporal = entidadDAO.buscarEntidadPorIdentificacion(identificacion);
					Usuario usuarioTemporal = usuarioDAO.buscarPorLogin(login);
					Entidad usuarioMailTemporal = null;
					if (codigo.equals(""))
						usuarioMailTemporal = entidadDAO.buscarEntidadPorMail(mail);
					if(entidadTemporal.getId() == null && usuarioTemporal == null && usuarioMailTemporal == null){
						if (codigo.equals(""))
							entidad = entidadTransaction.crear(entidad);
						
						if (!entidad.getId().equals("") && !entidad.getId().equals(null)) {
							elActivo = activo.equals("1");
							usuario.setActivo(elActivo);
							usuario.setLogin(login);
							usuario.setEntidad(entidad);
							// Agregamos clave aleatoria para validacion de la cuenta 
							usuario.setValidacionMail(Utilitarios.generarCodigoAleatorioPorLongitud(8));
							usuarioTransaction.crear(usuario);
						}			
						if (!usuario.getId().equals("")
								&& !usuario.getId().equals(null)) {

							credenciales.setClave(Utilitarios.encriptacionClave(password)); // Encriptacion clave
							credenciales.setUsuario(usuario);
							credencialTransaction.crear(credenciales);
						}
						
						// Grabamos al usuario un rol por defecto
						RolDAO rolDAO = new RolDAO();
						
						
						Rol rolDefecto = rolDAO.buscarPorId(rolId);
						UsuarioRolDAO usuarioRolDAO = new UsuarioRolDAO();
						UsuarioRol usuarioRol = new UsuarioRol();
						usuarioRol.setRol(rolDefecto);
						usuarioRol.setUsuario(usuario);
						usuarioRolTransaction.crear(usuarioRol);
						
						// Obtenemos plantilla de confirmacion de Mail
						String rutaPlantilla = this.getServletContext().getRealPath("")+"/static/plantillas/confirmarMail.html";
						
						
						// Obtenemos el archivo y obtenemos los datos de la plantilla
						FileReader fr = null;
			            BufferedReader br = null;

			            String cuerpoMail = "";
			            try {
			            File archivo = new File (rutaPlantilla);
			            fr = new FileReader (archivo);
			            br = new BufferedReader(fr);

			            String linea;
			             
			            while((linea=br.readLine())!=null){
			                cuerpoMail = cuerpoMail + linea;
			            }
			            }catch (Exception e) {
							e.printStackTrace();
						}		           
			            br.close();
			            fr.close();
			            cuerpoMail = cuerpoMail.replace("password", password);
			            cuerpoMail = cuerpoMail.replace("usuario", usuario.getLogin());
			            String link =  request.getRequestURL().toString();
			            String urlFormato = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath();
			            
			            cuerpoMail = cuerpoMail.replace("imagenurl", urlFormato);
			            
			            String urlConfirmacion = "/ConfirmarMail?confirmar="+usuario.getValidacionMail();
			            link = link.replace("/EntidadController",urlConfirmacion);
			            cuerpoMail = cuerpoMail.replace("link",link);
			            
						
			            Utilitarios.envioMail(usuario.getEntidad().getMail(), "Mail de Activacion de cuenta", cuerpoMail);
			            result.put("mensajeActivacion", "El usuario se creo con exito. Por favor revise su mail para poder activar la cuenta.");
			            
					}	
					else
					{
						if(usuarioTemporal != null){
							result.put("mensajeActivacion", "El usuario que desea registrar ya existe");
						}
						
						if(usuarioMailTemporal != null){
							result.put("mensajeActivacion", "El email ingresado ya existe. Ingrese otro");
						}					
						
					}
					    
		            
				}
				
				if(tipoConsulta.equals("cambioEstado")){
					usuario = usuarioDAO.buscarPorEntidadId(entidad);
					if (activo.equals("1")){
						usuario.setActivo(false);
					}else if (activo.equals("0")){
						usuario.setActivo(true);						
					}
					usuarioTransaction.editar(usuario);
					
				}
				
				if (tipoConsulta.equals("cambioClave")) {
					
					//Envio de Correo
					// Obtenemos plantilla de cambioClave	
					String rutaPlantilla = this.getServletContext().getRealPath("")+"/static/plantillas/mailCambioClave.html";
					
					
					// Obtenemos el archivo y obtenemos los datos de la plantilla
					FileReader fr = null;
		            BufferedReader br = null;

		            String cuerpoMail = "";
		            try {
		            File archivo = new File (rutaPlantilla);
		            fr = new FileReader (archivo);
		            br = new BufferedReader(fr);

		            String linea;
		             
		            while((linea=br.readLine())!=null){
		                cuerpoMail = cuerpoMail + linea;
		            }
		            }catch (Exception e) {
						e.printStackTrace();
					}		           
		            br.close();
		            fr.close();
		            //String link = "http://localhost:8084/CotizadorWeb";
		            String link = request.getRequestURL().toString().replace("/EntidadController","");
					
		            String PasswordTemporal = Utilitarios.generarCodigoAleatorioPorLongitud(8);
		            cuerpoMail = cuerpoMail.replace("#link#",link);
					cuerpoMail = cuerpoMail.replace("#password#", PasswordTemporal);
		            Utilitarios.envioMail(mail, "Mail de Cambio de Clave", cuerpoMail);

				}

				if (tipoConsulta.equals("eliminar")) {
					entidad = entidadDAO.buscarPorId(codigo);
					usuarioTransaction.eliminar(entidad.getUsuarios().get(0));
					credencialTransaction.eliminar(entidad.getUsuarios().get(0)
							.getCredencials().get(0));

				}

				if (tipoConsulta.equals("actualizar")) {
					
					// Editamos Entidad
					entidad = entidadTransaction.editar(entidad);
					
					// Editamos Rol					
					RolDAO rolDAO = new RolDAO();				
					Rol rol = rolDAO.buscarPorId(rolId);
					usuario = usuarioDAO.buscarPorEntidadId(entidad);
					UsuarioRolDAO usuarioRolDAO = new UsuarioRolDAO();
					UsuarioRol usuarioRol = usuarioRolDAO.buscarPorUsuario(usuario);					
					usuarioRol.setRol(rol);
					usuarioRolTransaction.editar(usuarioRol);
					
					// Editamos Usuario
					usuario.setValidacionMail("");
					elActivo = activo.equals("1");
					usuario.setActivo(elActivo);
					usuario.setLogin(login);
					usuarioTransaction.editar(usuario);
				}
			}
			
			
			if(tipoEntidad.equals("")){
				
				if (tipoConsulta.equals("encontrarTodos")) {
					List<Entidad> results = entidadDAO.buscarTodos();
							int i=0;
							for(i=0; i<results.size(); i++){
								entidad = results.get(i);
								entidadJSONObject.put("codigo", entidad.getId());
								entidadJSONObject.put("codigoEnsurance", entidad.getEntEnsurance()==null?"":entidad.getEntEnsurance());
								entidadJSONObject.put("identificacion", entidad.getIdentificacion());
								entidadJSONObject.put("tipoIdentificacion", entidad.getTipoIdentificacion().getNombre());
								entidadJSONObject.put("nombre", entidad.getNombres());
								entidadJSONObject.put("apellido", entidad.getApellidos());
								entidadJSONObject.put("nombreCompleto", entidad.getNombreCompleto());
								entidadJSONObject.put("mail", entidad.getMail());
								entidadJSONObject.put("telefono", entidad.getTelefono());
								entidadJSONObject.put("celular", entidad.getCelular());
								entidadJSONArray.add(entidadJSONObject);
							}
							result.put("numRegistros",i);
							result.put("listadoEntidad", entidadJSONArray);
							
							TipoIdentificacionDAO tipoIndentificacionDAO = new TipoIdentificacionDAO();
							TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
							
							JSONObject tipoIdentificacionJSONObject = new JSONObject();
							JSONArray tipoIdentificacionJSONArray = new JSONArray();
							
							List<TipoIdentificacion> listadoTipoIdentificacion = tipoIndentificacionDAO.buscarTodos();
							
							for(i = 0; i < listadoTipoIdentificacion.size(); i++ ){
								tipoIdentificacion = listadoTipoIdentificacion.get(i);
								tipoIdentificacionJSONObject.put("codigo", tipoIdentificacion.getId());
								tipoIdentificacionJSONObject.put("nombre", tipoIdentificacion.getNombre());
								
								tipoIdentificacionJSONArray.add(tipoIdentificacionJSONObject);
							}
							
							result.put("listadoTipoIdentificacion", tipoIdentificacionJSONArray);
						}
			
				
						if(tipoConsulta.equals("crear")){
							Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
							entidad.setFechaCreacion(fechaActual);
							entidad = entidadTransaction.crear(entidad);
							
						}
						
						

				if (tipoConsulta.equals("eliminar")) {
					entidad = entidadDAO.buscarPorId(codigo);
					entidadTransaction.eliminar(entidad);
				}

				if (tipoConsulta.equals("actualizar")) {
					entidad = entidadTransaction.editar(entidad);
				}
			}
			
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1");
			result.write(response.getWriter());
		} catch (Exception e) {
			
            result.put("success", Boolean.TRUE);
            result.put("error", e.getLocalizedMessage());
            response.setContentType("application/json; charset=ISO-8859-1"); 
            result.write(response.getWriter());
            e.printStackTrace();
		}
	}
	
	public JSONObject cargarPorIdentificacion(String identificacion){
				JSONObject entidadJSONObject = new JSONObject();
				EntidadDAO eDAO=new EntidadDAO();
				Entidad ent= new Entidad();
				ent=eDAO.buscarEntidadPorIdentificacion(identificacion);
				
				if(ent.getId()!=null){
				entidadJSONObject.put("codigo", ent.getId());
				entidadJSONObject.put("codigoEnsurance", ent.getEntEnsurance());
				entidadJSONObject.put("identificacion", ent.getIdentificacion());
				entidadJSONObject.put("tipoIdentificacion", ent.getTipoIdentificacion().getNombre());
				entidadJSONObject.put("nombre", ent.getNombres());
				entidadJSONObject.put("nombreCompleto", ent.getNombreCompleto());
				entidadJSONObject.put("apellido", ent.getApellidos());
				entidadJSONObject.put("mail", ent.getMail());
				entidadJSONObject.put("telefono", ent.getTelefono());
				entidadJSONObject.put("celular", ent.getCelular());}
				
				
				return entidadJSONObject;
				
			}
	
	
	
	public JSONObject cargarPorIdentificacionWS(String xmltemp){
		JSONObject entidadJSONObject = new JSONObject();
		String nombre="";
        String apellido="";
        String nombreCompleto="";
        String tipoEntidad="";
        String clienteIdEnsurance="";
        String entidadIdEnsurance="";
        String tipoIdentificacion="";
        String tipoIdentificacionNombre="";
        String agenteIdEnsurance="";
        String numerocredencialEnsurance="";
        TipoIdentificacionDAO tipoIdentificacionDAO=new TipoIdentificacionDAO(); 
		
		String textoSinSaltosDeLinea = xmltemp.replaceAll("[\t\n\r]", "");
        String xmlText = textoSinSaltosDeLinea.toString();
        xmlText = xmlText.replace("<![CDATA[", "");
        xmlText = xmlText.replace("]]>", "");
        
		
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Use document builder factory
            DocumentBuilder builder;
            
            if(!xmlText.equals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"))
			try {
				builder = factory.newDocumentBuilder();
			
            //Parse the document
            Reader reader = new CharArrayReader(xmlText.toCharArray());
            Document doc = builder.parse(new org.xml.sax.InputSource(reader));

            // Elementos del XML
            Node nodoUsuario = doc.getFirstChild();
            
            	if (nodoUsuario.getNodeType() == Node.ELEMENT_NODE && nodoUsuario.hasChildNodes() ) {
            		
            		Element usuario = (Element) nodoUsuario;
            		nombre=usuario.getElementsByTagName("nombre").item(0).getChildNodes().item(0).getNodeValue();
                    apellido=usuario.getElementsByTagName("apellido").item(0).getChildNodes().item(0).getNodeValue();
                    nombreCompleto=usuario.getElementsByTagName("nombreCompleto").item(0).getChildNodes().item(0).getNodeValue();
                    tipoEntidad=usuario.getElementsByTagName("tipoEntidad").item(0).getChildNodes().item(0).getNodeValue();
                    tipoIdentificacion=usuario.getElementsByTagName("tipoIdentificacion").item(0).getChildNodes().item(0).getNodeValue();
                   // tipoIdentificacionNombre=tipoIdentificacionDAO.buscarPorId(usuario.getElementsByTagName("tipoIdentificacion").item(0).getChildNodes().item(0).getNodeValue()).getNombre();
                    clienteIdEnsurance=usuario.getElementsByTagName("clienteIdEnsurance").item(0).getChildNodes().item(0).getNodeValue();
                    entidadIdEnsurance=usuario.getElementsByTagName("entidadIdEnsurance").item(0).getChildNodes().item(0).getNodeValue();
                    
                    agenteIdEnsurance=usuario.getElementsByTagName("agenteIdEnsurance").item(0).getChildNodes().item(0).getNodeValue();
                    numerocredencialEnsurance=usuario.getElementsByTagName("numerocredencialEnsurance").item(0).getChildNodes().item(0).getNodeValue();
                    
                    
                    TipoIdentificacionDAO tpDAO= new TipoIdentificacionDAO();
                    if(tipoEntidad.equals("1")&&tipoIdentificacion.equals("r")){
                    	tipoIdentificacion="3";//
                    	tipoIdentificacionNombre=tpDAO.buscarPorId("3").getNombre();
                    }
                    if(tipoEntidad.equals("2")&&tipoIdentificacion.equals("r")){
                    	tipoIdentificacion="4";//
                    	tipoIdentificacionNombre=tpDAO.buscarPorId("4").getNombre();
                    }
                    if(tipoIdentificacion.equals("p")){
                    	tipoIdentificacion="2";//
                    	tipoIdentificacionNombre=tpDAO.buscarPorId("2").getNombre();
                    }
                    if(tipoIdentificacion.equals("c")){
                    	tipoIdentificacion="1";//
                    	tipoIdentificacionNombre=tpDAO.buscarPorId("1").getNombre();
                    }
                    
			
            	}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			entidadJSONObject.put("tipoEntidad", tipoEntidad);
			entidadJSONObject.put("nombre", nombre);
			entidadJSONObject.put("nombreCompleto", nombreCompleto);
			entidadJSONObject.put("apellido", apellido);
			entidadJSONObject.put("tipoIdentificacion", tipoIdentificacion);
			entidadJSONObject.put("clienteIdEnsurance", clienteIdEnsurance);
			entidadJSONObject.put("entidadIdEnsurance", entidadIdEnsurance);
			entidadJSONObject.put("tipoIdentificacionNombre", tipoIdentificacionNombre);
			entidadJSONObject.put("agenteIdEnsurance", agenteIdEnsurance);
			entidadJSONObject.put("numerocredencialEnsurance", numerocredencialEnsurance);
			
			
		return entidadJSONObject;
		
	}

}
