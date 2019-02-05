package com.qbe.cotizador.servlets.inspeccion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.entidad.ZonaDAO;
import com.qbe.cotizador.dao.inspeccion.DistanciaInspectorDAO;
import com.qbe.cotizador.dao.inspeccion.HonorariosInspectorDAO;
import com.qbe.cotizador.dao.inspeccion.InspectorDAO;
import com.qbe.cotizador.dao.seguridad.RolDAO;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.DistanciaInspector;
import com.qbe.cotizador.model.HonorariosInspector;
import com.qbe.cotizador.model.Inspector;
import com.qbe.cotizador.model.OpcionMenuPantallaRol;
import com.qbe.cotizador.model.OpcionPantalla;
import com.qbe.cotizador.model.Rol;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.Zona;
import com.qbe.cotizador.transaction.inspeccion.DistanciaInspectorTransaction;

/**
 * Servlet implementation class DistanciaInspectorController
 */
@WebServlet("/DistanciaInspectorController")
public class DistanciaInspectorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DistanciaInspectorController() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		DistanciaInspectorTransaction distanciaInspectorTransaction = new DistanciaInspectorTransaction(); 
		
		try{
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String origen = request.getParameter("origen") == null ? "" : request.getParameter("origen");
			String destino = request.getParameter("destino") == null ? "" : request.getParameter("destino");
			String kmIda = request.getParameter("kmIda") == null ? "" : request.getParameter("kmIda");
			String kmRoundtrip = request.getParameter("kmRoundtrip") == null ? "" : request.getParameter("kmRoundtrip");
			String inspector_id = request.getParameter("inspector") == null ? "" : request.getParameter("inspector");
			String zona_id = request.getParameter("zona") == null ? "" : request.getParameter("zona");
			String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
						
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			JSONObject distanciaInspectorJSONObject = new JSONObject();
			JSONArray distanciaInspectorJSONArray = new JSONArray();

			DistanciaInspector distanciaInspector = new DistanciaInspector();
			DistanciaInspectorDAO distanciaInspectorDAO = new DistanciaInspectorDAO();
			
			CotizacionDAO cotizacionDAO = new CotizacionDAO();
			Cotizacion cotizacion = new Cotizacion();
						
			if (!codigo.equals(""))
				distanciaInspector.setId(codigo);

			if (!origen.equals(""))
				distanciaInspector.setOrigen(origen);

			if (destino.equals("1"))
				distanciaInspector.setDestino(destino);

			if (destino.equals("1"))
				distanciaInspector.setKmIda(kmIda);
			
			if (destino.equals("1"))
				distanciaInspector.setKmRoundtrip(kmRoundtrip);
			
			if (destino.equals("1")){
				Inspector inspector = new Inspector();
				InspectorDAO inspectorDAO = new InspectorDAO();
				distanciaInspector.setInspector(inspector);
			}
			
			if(tipoConsulta.equals("encontrarTodos")){ 
				List<DistanciaInspector> results = distanciaInspectorDAO.buscarTodos();
				int i=0;
				for(i=0; i<results.size(); i++){
					distanciaInspector = results.get(i);
					distanciaInspectorJSONObject.put("origen", distanciaInspector.getOrigen());
					distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoDistanciaInspector", distanciaInspectorJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarOrigen")){ 
				List<DistanciaInspector> results = distanciaInspectorDAO.buscarCiudadOrigen();
				int i=0;
				for(i=0; i<results.size(); i++){
					//distanciaInspector = results.get(i);
					distanciaInspectorJSONObject.put("origen", results.get(i));
					distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoOrigenInspector", distanciaInspectorJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarDestinoByOrigen")){ 
				List<DistanciaInspector> results = distanciaInspectorDAO.buscarCiudadOrigenPorDestino(origen);
				int i=0;
				for(i=0; i<results.size(); i++){
					distanciaInspector = results.get(i);
					distanciaInspectorJSONObject.put("destino", distanciaInspector.getDestino());
					distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoDestinoInspector", distanciaInspectorJSONArray);
			}

			if(tipoConsulta.equals("buscarInspectoresExternosDisponibles")){
				InspectorDAO inspectorDAO = new InspectorDAO();
				Inspector inspector = new Inspector();
				JSONObject inspectorJSONObject = new JSONObject();
				JSONArray inspectorJSONArray = new JSONArray();
				
				List<Inspector> listaInpector = inspectorDAO.buscarTodos();
				for(int j = 0; j < listaInpector.size(); j++){
					inspector = listaInpector.get(j);
				if(inspector.getTipoInspector().getId().equals("2")){
					List<DistanciaInspector> results = distanciaInspectorDAO.buscarDistanciasPorInspector(origen, destino, inspector);
					int i=0;
					if(results.size() > 0){
						for(i=0; i<results.size(); i++){
							distanciaInspector = results.get(i);
							HonorariosInspectorDAO honorariosInspectorDAO = new HonorariosInspectorDAO();
							ZonaDAO zonaDAO= new ZonaDAO();
							Zona zona= zonaDAO.buscarPorId(zona_id);
							if(cotizacionId!=null&&!cotizacionId.equals(""))
								cotizacion=cotizacionDAO.buscarPorId(cotizacionId);
							HonorariosInspector honorariosInspector = honorariosInspectorDAO.buscarPorInspectorZonaTipoObjeto(distanciaInspector.getInspector(), zona, cotizacion.getTipoObjeto().getId());
							
							distanciaInspectorJSONObject.put("destino", distanciaInspector.getDestino());
							distanciaInspectorJSONObject.put("codigoInspector", distanciaInspector.getInspector().getId());
							distanciaInspectorJSONObject.put("nombreInspector", distanciaInspector.getInspector().getNombre());
							distanciaInspectorJSONObject.put("gastos", new Double(distanciaInspector.getKmRoundtrip()) * inspector.getValorKm());
							if(honorariosInspector!=null&&honorariosInspector.getId()!=null)
								distanciaInspectorJSONObject.put("honorarios", honorariosInspector.getValor());
							else
								distanciaInspectorJSONObject.put("honorarios", "-1");
							
							distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
						}
					}else{
						distanciaInspectorJSONObject.put("codigoInspector", inspector.getId());
						distanciaInspectorJSONObject.put("nombreInspector", inspector.getNombre());
						distanciaInspectorJSONObject.put("honorarios", "-1");
						distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
					}
				}
				}
				result.put("listaInspectoresRegistrados",inspectorJSONArray);
				result.put("listadoInspectoresDisponibles", distanciaInspectorJSONArray);
			}
			
			if(tipoConsulta.equals("buscarInspectoresInternosDisponibles")){
				InspectorDAO inspectorDAO = new InspectorDAO();
				Inspector inspector = new Inspector();
				JSONObject inspectorJSONObject = new JSONObject();
				JSONArray inspectorJSONArray = new JSONArray();
				
				List<Inspector> listaInpector = inspectorDAO.buscarTodos();
				for(int j = 0; j < listaInpector.size(); j++){
					inspector = listaInpector.get(j);
				if(inspector.getTipoInspector().getId().equals("1")){
					List<DistanciaInspector> results = distanciaInspectorDAO.buscarDistanciasPorInspector(origen, destino, inspector);
					int i=0;
					if(results.size() > 0){
						for(i=0; i<results.size(); i++){
							distanciaInspector = results.get(i);
							HonorariosInspectorDAO honorariosInspectorDAO = new HonorariosInspectorDAO();
							ZonaDAO zonaDAO= new ZonaDAO();
							Zona zona= zonaDAO.buscarPorId(zona_id);
							if(cotizacionId!=null&&!cotizacionId.equals(""))
								cotizacion=cotizacionDAO.buscarPorId(cotizacionId);
							HonorariosInspector honorariosInspector = honorariosInspectorDAO.buscarPorInspectorZonaTipoObjeto(distanciaInspector.getInspector(), zona, cotizacion.getTipoObjeto().getId());
							
							distanciaInspectorJSONObject.put("destino", distanciaInspector.getDestino());
							distanciaInspectorJSONObject.put("codigoInspector", distanciaInspector.getInspector().getId());
							distanciaInspectorJSONObject.put("nombreInspector", distanciaInspector.getInspector().getNombre());
							distanciaInspectorJSONObject.put("gastos", new Double(distanciaInspector.getKmRoundtrip()) * inspector.getValorKm());
							if(honorariosInspector!=null&&honorariosInspector.getId()!=null)
								distanciaInspectorJSONObject.put("honorarios", honorariosInspector.getValor());
							else
								distanciaInspectorJSONObject.put("honorarios", "-1");
							
							distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
						}
					}else{
						distanciaInspectorJSONObject.put("codigoInspector", inspector.getId());
						distanciaInspectorJSONObject.put("nombreInspector", inspector.getNombre());
						distanciaInspectorJSONObject.put("honorarios", "-1");
						distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
					}
				}
				}
				result.put("listaInspectoresRegistrados",inspectorJSONArray);
				result.put("listadoInspectoresDisponibles", distanciaInspectorJSONArray);
			}
			
			if(tipoConsulta.equals("buscarInspectoresDisponibles")){
				InspectorDAO inspectorDAO = new InspectorDAO();
				Inspector inspector = new Inspector();
				JSONObject inspectorJSONObject = new JSONObject();
				JSONArray inspectorJSONArray = new JSONArray();
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
					 usuario = (Usuario)request.getSession().getAttribute("usuario");
				Rol rol=new Rol();
				RolDAO rolDAO=new RolDAO();
				OpcionMenuPantallaRol ompr=new OpcionMenuPantallaRol(); 
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				List<OpcionMenuPantallaRol> listaOpcionMenuRol =rol.getOpcionMenuPantallaRols();
				
				boolean bandera=false;
				
				if(listaOpcionMenuRol!=null)
				for(int i=0;i<listaOpcionMenuRol.size();i++){
					if(listaOpcionMenuRol.get(i).getOpcionPantalla().getIdentificador().equals("costo_inspector"))
						bandera=true;
				}
				
				result.put("mostrarCostos", bandera);
				
				List<Inspector> listaInpector = inspectorDAO.buscarTodos();
				for(int j = 0; j < listaInpector.size(); j++){
					inspector = listaInpector.get(j);
					List<DistanciaInspector> results = distanciaInspectorDAO.buscarDistanciasPorInspector(origen, destino, inspector);
					int i=0;
					if(results.size() > 0){
						for(i=0; i<results.size(); i++){
							distanciaInspector = results.get(i);
							HonorariosInspectorDAO honorariosInspectorDAO = new HonorariosInspectorDAO();
							ZonaDAO zonaDAO= new ZonaDAO();
							Zona zona= zonaDAO.buscarPorId(zona_id);
							if(cotizacionId!=null&&!cotizacionId.equals(""))
								cotizacion=cotizacionDAO.buscarPorId(cotizacionId);
							HonorariosInspector honorariosInspector = honorariosInspectorDAO.buscarPorInspectorZonaTipoObjeto(distanciaInspector.getInspector(), zona, cotizacion.getTipoObjeto().getId());
							
							distanciaInspectorJSONObject.put("destino", distanciaInspector.getDestino());
							distanciaInspectorJSONObject.put("codigoInspector", distanciaInspector.getInspector().getId());
							distanciaInspectorJSONObject.put("nombreInspector", distanciaInspector.getInspector().getNombre());
							
							
							distanciaInspectorJSONObject.put("gastos", new Double(distanciaInspector.getKmRoundtrip()) * inspector.getValorKm());
							
							if(honorariosInspector!=null&&honorariosInspector.getId()!=null)
								distanciaInspectorJSONObject.put("honorarios", honorariosInspector.getValor());
							else
								distanciaInspectorJSONObject.put("honorarios", "-1");
							
							distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
						}
					}else{
						distanciaInspectorJSONObject.put("codigoInspector", inspector.getId());
						distanciaInspectorJSONObject.put("nombreInspector", inspector.getNombre());
						distanciaInspectorJSONObject.put("honorarios", "-1");
						distanciaInspectorJSONArray.add(distanciaInspectorJSONObject);
					}
				
				}
				result.put("listaInspectoresRegistrados",inspectorJSONArray);
				result.put("listadoInspectoresDisponibles", distanciaInspectorJSONArray);
			}

			if(tipoConsulta.equals("crear"))
				distanciaInspectorTransaction.crear(distanciaInspector);

			if(tipoConsulta.equals("actualizar"))
				distanciaInspectorTransaction.editar(distanciaInspector);

			if(tipoConsulta.equals("eliminar"))
				distanciaInspectorTransaction.eliminar(distanciaInspector);


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
