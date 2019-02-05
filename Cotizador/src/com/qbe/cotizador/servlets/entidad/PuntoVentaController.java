package com.qbe.cotizador.servlets.entidad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.cotizacion.ProductoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.AgenteDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.SucursalDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.seguridad.RolDAO;
import com.qbe.cotizador.model.Agente;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.Producto;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.Rol;
import com.qbe.cotizador.model.Sucursal;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.UsuarioXPuntoVenta;
import com.qbe.cotizador.transaction.cotizacion.PuntoVentaTransaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PuntoVenta
 */
@WebServlet("/PuntoVentaController")
public class PuntoVentaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PuntoVentaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

           AgenteDAO agenteDAO = new AgenteDAO();
           SucursalDAO sucursalDAO = new SucursalDAO();
           
           PuntoVentaTransaction puntoVentaTransaction = new PuntoVentaTransaction();
           
		JSONObject result = new JSONObject();
		try{
			String codigoEnsurance = request.getParameter("codigoEnsurance") == null ? "" : request.getParameter("codigoEnsurance");
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion") == null ? "" : request.getParameter("descripcion");
			String activa = request.getParameter("activo") == null ? "" : request.getParameter("activo");
			String sucursalf = request.getParameter("sucursal") == null ? "" : request.getParameter("sucursal");
			String agente = request.getParameter("agente") == null ? "" : request.getParameter("agente");
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String tipoObjeto = request.getParameter("tipoObjeto") == null ? "" : request.getParameter("tipoObjeto");
			JSONObject PuntoVentaJSONObject = new JSONObject();
			JSONArray PuntoVentaJSONArray = new JSONArray(); 

			PuntoVenta PuntoVenta = new PuntoVenta();
			PuntoVentaDAO PuntoVentaDAO = new PuntoVentaDAO();

			 
			
			if (!codigo.equals(""))
				PuntoVenta.setId(codigo);

			if (!codigoEnsurance.equals(""))
				PuntoVenta.setPtoEnsurance(codigoEnsurance);        

			if (!nombre.equals(""))
				PuntoVenta.setNombre(nombre);
			
			if (!descripcion.equals(""))
				PuntoVenta.setDescripcion(descripcion);	

			if (!sucursalf.equals("")){				
				Sucursal sucursalaux = new Sucursal(); 
				sucursalaux = sucursalDAO.buscarPorId(sucursalf);
				PuntoVenta.setSucursal(sucursalaux);
				}
			
			if (!agente.equals("")){				
				PuntoVenta.setAgenteId(agente);
				}			

			if (activa.equals("1"))
				PuntoVenta.setActivo(true);
			else if(!tipoConsulta.equals("eliminar"))
				PuntoVenta.setActivo(false);

			if(tipoConsulta.equals("encontrarTodos")){
				Agente agenteAux = new Agente();
				AgenteDAO agentedao = new AgenteDAO();
				String agenteaux2 = " ";
				List<PuntoVenta> results = PuntoVentaDAO.buscarTodos();
				int i=0;
				for(i=0; i<results.size(); i++){
					PuntoVenta = results.get(i);					
					String idAgente = PuntoVenta.getAgenteId(); 
					if(idAgente.isEmpty()){
						agenteaux2 = " ";						
					}else{
						agenteAux = agentedao.buscarPorId(PuntoVenta.getAgenteId());
						agenteaux2 = agenteAux.getEntidad().getNombreCompleto();						
					}					
					PuntoVentaJSONObject.put("codigo", PuntoVenta.getId());
					
					if (PuntoVenta.getPtoEnsurance().equals("99952103658")){                		                		
                		PuntoVentaJSONObject.put("codigoEnsurance", "QUITO IN");
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551360")){
                		PuntoVentaJSONObject.put("codigoEnsurance","GUAYAQUIL");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551361")){
                		PuntoVentaJSONObject.put("codigoEnsurance","AMBATO");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551362")){
                		PuntoVentaJSONObject.put("codigoEnsurance","CUENCA");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551363")){
                		PuntoVentaJSONObject.put("codigoEnsurance", "IBARRA");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551364")){
                		PuntoVentaJSONObject.put("codigoEnsurance","MANTA");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551365")){
                		PuntoVentaJSONObject.put("codigoEnsurance","RIOBAMBA");                		
                	}
                	if (PuntoVenta.getPtoEnsurance().equals("100049551366")){
                		PuntoVentaJSONObject.put("codigoEnsurance","SANTO DOMINGO");                		
                	}

                	PuntoVentaJSONObject.put("nombre", PuntoVenta.getNombre());
					PuntoVentaJSONObject.put("descripcion", PuntoVenta.getDescripcion());
					PuntoVentaJSONObject.put("agente", agenteaux2);
					//PuntoVentaJSONObject.put("agente", PuntoVenta.getAgenteId());
					PuntoVentaJSONObject.put("sucursal", PuntoVenta.getSucursal().getNombre());

					if(PuntoVenta.getActivo())
						PuntoVentaJSONObject.put("activo", "Si");
					else
						PuntoVentaJSONObject.put("activo", "No");

					PuntoVentaJSONArray.add(PuntoVentaJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoPuntoVenta", PuntoVentaJSONArray);
				
				//Para Cargar combos
				JSONObject agenteJSONObject = new JSONObject();
                JSONArray agenteJSONArray = new JSONArray();

                List<Agente> listadoAgente= agenteDAO.buscarTodos();

                for (i = 0; i < listadoAgente.size(); i++) {
                	agenteJSONObject.put("codigo", listadoAgente.get(i).getId());
                	agenteJSONObject.put("nombre", listadoAgente.get(i).getEntidad().getNombreCompleto());

                	agenteJSONArray.add(agenteJSONObject);
                }

                result.put("listadoAgente", agenteJSONArray);
                
				JSONObject sucursalJSONObject = new JSONObject();
                JSONArray sucursalJSONArray = new JSONArray();

                List<Sucursal> listadoSucursal= sucursalDAO.buscarTodos();

                for (i = 0; i < listadoSucursal.size(); i++) {
                	sucursalJSONObject.put("codigo", listadoSucursal.get(i).getId());
                	sucursalJSONObject.put("nombre", listadoSucursal.get(i).getNombre());

                	sucursalJSONArray.add(sucursalJSONObject);
                }

                result.put("listadoSucursal", sucursalJSONArray);
                
                //Punto Venta Ensurance
                
                JSONObject puntoVentaEnsuranceJSONObject = new JSONObject();
                JSONArray puntoVentaEnsuranceJSONArray = new JSONArray();
                
                PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO (); 

                List<PuntoVenta> listadoPuntoVentaEnsurance = puntoVentaDAO.buscarPtosEnsurance();
                String Aux = null;
                String AuxListaPtoVenta = null;
                
                for (i = 0; i < listadoPuntoVentaEnsurance.size(); i++) {
                	puntoVentaEnsuranceJSONObject.put("codigo", listadoPuntoVentaEnsurance.get(i).getId());
                	
                	AuxListaPtoVenta = Aux.valueOf(listadoPuntoVentaEnsurance.get(i).getId());
                	
                	if (AuxListaPtoVenta.equals("99952103658")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "QUITO IN");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551360")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "GUAYAQUIL");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551361")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "AMBATO");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551362")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "CUENCA");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551363")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "IBARRA");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551364")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "MANTA");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551365")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "RIOBAMBA");                		
                	}
                	if (AuxListaPtoVenta.equals("100049551366")){
                		puntoVentaEnsuranceJSONObject.put("nombre", "SANTO DOMINGO");                		
                	}
                	puntoVentaEnsuranceJSONArray.add(puntoVentaEnsuranceJSONObject);
                }

                result.put("listadoPuntoVentaEnsurance", puntoVentaEnsuranceJSONArray);
				
			}
			
			// Puntos de Venta por Producto ayanez
			if(tipoConsulta.equals("puntosVentaXProducto") && tipoObjeto.equalsIgnoreCase("VHDinamico")){ 
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				List<UsuarioXPuntoVenta> usuarios = new ArrayList<UsuarioXPuntoVenta>();
				usuarios = usuario.getUsuarioXPuntoVentas();
				if(usuarios.size() > 0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}
				GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
				GrupoPorProducto grupoPorProductoVH = grupoPorProductoDAO.buscarPorNombre("Veh�culos Tarifa Din�mica");
				
				JSONArray sucursalesJSON=new JSONArray();
				JSONObject sucursalJSON=new JSONObject();
				
				ProductoXPuntoVentaDAO pppvDAO = new ProductoXPuntoVentaDAO();				
				ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
				
				List<Sucursal> sucursal = sucursalDAO.buscarActivos();
				
				List<ProductoXPuntoVenta> listado 		= pppvDAO.buscarPorProductoPuntoVentaListado(grupoPorProductoVH);
				if(listado.size() > 0) {
					JSONObject puntosVentaJSON = new JSONObject();
					for (int i = 0; i < listado.size(); i++) {

						productoXPuntoVenta = (ProductoXPuntoVenta) listado.get(i);
						if (rol.getId().equals("1") || rol.getId().equals("2") || rol.getId().equals("7")) {
							puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
							puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
							puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
							puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());
							puntosVentaJSON.put("productoPorPuntoDeVenta",productoXPuntoVenta.getId());
							PuntoVentaJSONArray.add(puntosVentaJSON);
						} else {
							if (puntoVenta != null&& puntoVenta.getId() != null) {
								if (productoXPuntoVenta.getPuntoVenta().getId().equals(puntoVenta.getId())) {
									puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
									puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
									puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
									puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());
									puntosVentaJSON.put("productoPorPuntoDeVenta",productoXPuntoVenta.getId());
									PuntoVentaJSONArray.add(puntosVentaJSON);
								}
							}
							
						}
						
					}
				}
				
				for(int j=0;j<sucursal.size();j++){
					sucursalJSON.put("id", sucursal.get(j).getId());
					sucursalJSON.put("nombre", sucursal.get(j).getNombre());
					sucursalesJSON.add(sucursalJSON);
				}
				
				result.put("puntosVenta", PuntoVentaJSONArray);	
				result.put("sucursales", sucursalesJSON);	
			}

			// Puntos de Venta por Producto productos cerrados VH
			if(tipoConsulta.equals("puntosVentaXProducto") && !tipoObjeto.equalsIgnoreCase("VHDinamico")){ 
							
				String grupoPorProductoId = request.getParameter("grupoPorProductoId") == null ? "" : request.getParameter("grupoPorProductoId");
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta();
				
				List<UsuarioXPuntoVenta> usuarios = new ArrayList<UsuarioXPuntoVenta>();
				usuarios = usuario.getUsuarioXPuntoVentas();
				if(usuarios.size() > 0){				
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
				GrupoPorProducto grupoPorProducto = grupoPorProductoDAO.buscarPorId(grupoPorProductoId);
				
				ProductoXPuntoVentaDAO pppvDAO = new ProductoXPuntoVentaDAO();
				ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
				
				JSONArray sucursalesJSON=new JSONArray();
				JSONObject sucursalJSON=new JSONObject();
							
				
				List<Sucursal> sucursal = sucursalDAO.buscarActivos();
							
				List<ProductoXPuntoVenta> listado 		= pppvDAO.buscarPorProductoPuntoVentaListado(grupoPorProducto);
				if(listado.size() > 0) {
					JSONObject puntosVentaJSON = new JSONObject();
					for(int i=0; i<listado.size(); i++) {
						
						productoXPuntoVenta = (ProductoXPuntoVenta) listado.get(i);
						if (rol.getId().equals("1") || rol.getId().equals("2") || rol.getId().equals("7") || rol.getId().equals("17") || rol.getId().equals("23")) {

							puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
							puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
							puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
							puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());
							puntosVentaJSON.put("productoPorPuntoDeVenta",productoXPuntoVenta.getId());
							PuntoVentaJSONArray.add(puntosVentaJSON);
						} else {
							if (puntoVenta != null&& puntoVenta.getId() != null) {
								if (productoXPuntoVenta.getPuntoVenta().getId().equals(puntoVenta.getId())) {
									puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
									puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
									puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
									puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());
									puntosVentaJSON.put("productoPorPuntoDeVenta",productoXPuntoVenta.getId());
									PuntoVentaJSONArray.add(puntosVentaJSON);
								}
							}
						}
						
						
					}
				}
							
				for(int j=0;j<sucursal.size();j++){
						sucursalJSON.put("id", sucursal.get(j).getId());
						sucursalJSON.put("nombre", sucursal.get(j).getNombre());
						sucursalesJSON.add(sucursalJSON);
				}
							
				result.put("puntosVenta", PuntoVentaJSONArray);	
				result.put("sucursales", sucursalesJSON);	
			}
			
			
			// Puntos de Venta por Sesion
			if(tipoConsulta.equals("puntosVentaXProductoSession")){ 
							
			Usuario usuario=new Usuario();
			if(request.getSession().getAttribute("usuario")!=null)
				usuario = (Usuario)request.getSession().getAttribute("usuario");
							
			if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

			Rol rol=new Rol();
							
			if(usuario.getUsuarioRols().size()>0)
				rol=usuario.getUsuarioRols().get(0).getRol();
							
			if(rol==null||rol.getId()==null)
				throw new Exception("Usuario sin Rol");

			PuntoVenta puntoVenta= new PuntoVenta(); 
			List<UsuarioXPuntoVenta> usuarios = new ArrayList<UsuarioXPuntoVenta>();
				usuarios = usuario.getUsuarioXPuntoVentas();
			if(usuarios.size() > 0){
				//si tiene puntos de venta
				puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
			}
										
			JSONArray sucursalesJSON=new JSONArray();
			JSONObject sucursalJSON=new JSONObject();
							
			ProductoXPuntoVentaDAO pppvDAO = new ProductoXPuntoVentaDAO();				
			ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
							
			List<Sucursal> sucursal = sucursalDAO.buscarActivos();
							
			List<ProductoXPuntoVenta> listado = pppvDAO.buscarPorProductoPuntoVentaSession();
			if(listado.size() > 0) {
				JSONObject puntosVentaJSON = new JSONObject();
				for (int i = 0; i < listado.size(); i++) {

				productoXPuntoVenta = (ProductoXPuntoVenta) listado.get(i);
				if (rol.getId().equals("1") || rol.getId().equals("2") || rol.getId().equals("7")) {
					puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
					puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
					puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
					puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());
					PuntoVentaJSONArray.add(puntosVentaJSON);
				} else {
					if (puntoVenta != null&& puntoVenta.getId() != null) {
						if (productoXPuntoVenta.getPuntoVenta().getId().equals(puntoVenta.getId())) {
							puntosVentaJSON.put("id",productoXPuntoVenta.getPuntoVenta().getId());
							puntosVentaJSON.put("nombre",productoXPuntoVenta.getPuntoVenta().getNombre());
							puntosVentaJSON.put("sucursal",productoXPuntoVenta.getPuntoVenta().getSucursal().getId());
							puntosVentaJSON.put("text",productoXPuntoVenta.getPuntoVenta().getNombre());							
							PuntoVentaJSONArray.add(puntosVentaJSON);
						}
					}										
				}									
				}
			}
							
			for(int j=0;j<sucursal.size();j++){
				sucursalJSON.put("id", sucursal.get(j).getId());
				sucursalJSON.put("nombre", sucursal.get(j).getNombre());
				sucursalesJSON.add(sucursalJSON);
			}
							
			result.put("puntosVenta", PuntoVentaJSONArray);	
			result.put("sucursales", sucursalesJSON);	
			}
			
			// Verificacion de los datos de un Punto de Venta
			if(tipoConsulta.equals("verificacionPuntoVenta")){ 
							
				String puntoVentaId = request.getParameter("puntoVentaId") == null ? "" : request.getParameter("puntoVentaId");
				
				PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO();
				PuntoVenta puntoVentaVerificar = puntoVentaDAO.buscarPorId(puntoVentaId);
								
				JSONObject puntoVentaJSON = new JSONObject();
				puntoVentaJSON.put("id", puntoVentaVerificar.getId().toString());
				puntoVentaJSON.put("agente_id", puntoVentaVerificar.getAgenteId());
				
				result.put("punto_venta", puntoVentaJSON);						
			}
			
			//Verificacion Nombre Punto de Venta -- PJacome
						if(tipoConsulta.equals("verificacionNombrePuntoVenta")){ 
										
							String nombrePuntoVenta = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
							
							PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO();
							PuntoVenta puntoVentaVerificar = puntoVentaDAO.buscarPorNombre(nombrePuntoVenta);
							if (puntoVentaVerificar.getId()!= null){
								JSONObject puntoVentaJSON = new JSONObject();								
								puntoVentaJSON.put("bandera", "verdad");
								result.put("PuntoVenta", puntoVentaJSON);							
							}else{
								JSONObject puntoVentaJSON = new JSONObject();
								puntoVentaJSON.put("bandera", "falso");								
								result.put("PuntoVenta", puntoVentaJSON);								
							}
						}
			
			if(tipoConsulta.equals("crear"))
				puntoVentaTransaction.crear(PuntoVenta);

			if(tipoConsulta.equals("actualizar"))
				puntoVentaTransaction.editar(PuntoVenta);

			if(tipoConsulta.equals("eliminar"))
				puntoVentaTransaction.eliminar(PuntoVenta);


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
