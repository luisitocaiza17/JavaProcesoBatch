package com.qbe.cotizador.servlets.cotizacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.entidad.CargoDAO;
import com.qbe.cotizador.dao.entidad.UnidadNegocioDAO;
import com.qbe.cotizador.dao.cotizacion.DescuentoDAO;
import com.qbe.cotizador.dao.cotizacion.GrupoAutorizacionDAO;
import com.qbe.cotizador.model.Cargo;
import com.qbe.cotizador.model.Descuento;
import com.qbe.cotizador.model.GrupoAutorizacion;
import com.qbe.cotizador.model.UnidadNegocio;
import com.qbe.cotizador.transaction.cotizacion.DescuentoTransaction;
import com.qbe.cotizador.transaction.cotizacion.SolicitudDescuentoTransaction;

/**
 * Servlet implementation class DescuentoController
 */
@WebServlet("/DescuentoController")
public class DescuentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DescuentoController() {
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
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion") == null ? "" : request.getParameter("descripcion");
			String rangoInicial = request.getParameter("rangoInicial") == null ? "" : request.getParameter("rangoInicial");
			String rangoFinal = request.getParameter("rangoFinal") == null ? "" : request.getParameter("rangoFinal");
			String activa = request.getParameter("activo") == null ? "" : request.getParameter("activo");
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String codigoGrupoAutorizacion = request.getParameter("grupoAutorizacion") == null ? "" : request.getParameter("grupoAutorizacion");
			String codigoCargo = request.getParameter("cargo") == null ? "" : request.getParameter("cargo");
			String unidadNegocioId = request.getParameter("unidadNegocioId") == null ? "" : request.getParameter("unidadNegocioId");
			JSONObject descuentoJSONObject = new JSONObject();
			JSONArray descuentoJSONArray = new JSONArray();

			Descuento descuento = new Descuento();
			DescuentoDAO descuentoDAO = new DescuentoDAO();
			
			GrupoAutorizacion grupoAutorizacion = new GrupoAutorizacion();
			GrupoAutorizacionDAO grupoAutorizacionDAO = new GrupoAutorizacionDAO();
			
			Cargo cargo = new Cargo();
			CargoDAO cargoDAO = new CargoDAO();
			DescuentoTransaction descuentoTransaction = new DescuentoTransaction();
			
			if (!codigo.equals("")){
				descuento = descuentoDAO.buscarPorId(codigo);
			}
				

			if (!codigoGrupoAutorizacion.equals("")){
				grupoAutorizacion = grupoAutorizacionDAO.buscarPorId(codigoGrupoAutorizacion);
				descuento.setGrupoAutorizacion(grupoAutorizacion);
			}

			if (!codigoCargo.equals("")){
				cargo = cargoDAO.buscarPorId(codigoCargo);
				descuento.setCargoAutoriza(cargo.getNombreGenerico());
			}
			
			if (!nombre.equals(""))
				descuento.setNombre(nombre);

//			if(!descripcion.equals(""))
				descuento.setDescripcion(descripcion);

			if(!rangoInicial.equals(""))
				descuento.setRangoInicial(rangoInicial);

			if(!rangoFinal.equals(""))
				descuento.setRangoFinal(rangoFinal);

			if (activa.equals("1"))
				descuento.setActivo(true);
			else if(!tipoConsulta.equals("eliminar"))
				descuento.setActivo(false);

			if(tipoConsulta.equals("encontrarPorUnidadNegocio") && unidadNegocioId != ""){
				UnidadNegocioDAO unidadNegocioDAO = new UnidadNegocioDAO();
				UnidadNegocio unidadNegocio = unidadNegocioDAO.buscarPorId(unidadNegocioId);
				List<GrupoAutorizacion> listaGrupoAutorizacion = grupoAutorizacionDAO.buscarPorUnidadNegocio(unidadNegocio);
				if(listaGrupoAutorizacion.size() > 0){
					for(int q=0; q< listaGrupoAutorizacion.size(); q++){
						List<Descuento> results = descuentoDAO.buscarPorGrupoAutorizacion(listaGrupoAutorizacion.get(q));
						int i=0;
						for(i=0; i<results.size(); i++){
							descuento = results.get(i);
							descuentoJSONObject.put("codigo", descuento.getId());
							descuentoJSONObject.put("unidadNegocioNombre", descuento.getGrupoAutorizacion().getUnidadNegocio().getNombre());
							descuentoJSONObject.put("unidadNegocioId", descuento.getGrupoAutorizacion().getUnidadNegocio().getId());
							descuentoJSONObject.put("nombre", descuento.getNombre());
							descuentoJSONObject.put("descripcion", descuento.getDescripcion());
							descuentoJSONObject.put("rangoInicial", descuento.getRangoInicial());
							descuentoJSONObject.put("rangoFinal", descuento.getRangoFinal());
							descuentoJSONObject.put("cargoAutoriza", descuento.getCargoAutoriza());
							
							if(descuento.getActivo())
								descuentoJSONObject.put("activo", "Si");
							else
								descuentoJSONObject.put("activo", "No");
		
							descuentoJSONArray.add(descuentoJSONObject);
						}
						result.put("numRegistros",i);
						result.put("listadoDescuento", descuentoJSONArray);
					}
				}
				
				List< Cargo> listaCargos = cargoDAO.buscarTodos();
				
				JSONObject cargoJSONObject = new JSONObject();
				JSONArray cargoJSONArray = new JSONArray();
				
				for (int i=0; i<listaCargos.size(); i++){
					cargo = listaCargos.get(i);
					cargoJSONObject.put("codigo", cargo.getId());
					cargoJSONObject.put("nombre", cargo.getNombre());
					cargoJSONObject.put("nombreGenerico", cargo.getNombreGenerico());
					cargoJSONObject.put("activo", cargo.getActivo());
					cargoJSONArray.add(cargoJSONObject);
				}
				result.put("listadoCargo", cargoJSONArray);
				
				List<GrupoAutorizacion> listGrupoAutorizacion =  grupoAutorizacionDAO.buscarActivos();
				JSONObject grupoAutorizacionJSONObject = new JSONObject();
				JSONArray grupoAutorizacionJSONArray = new JSONArray();
				for (int i=0; i<listGrupoAutorizacion.size(); i++){
					grupoAutorizacion = listGrupoAutorizacion.get(i);
					grupoAutorizacionJSONObject.put("codigo", grupoAutorizacion.getId());
					grupoAutorizacionJSONObject.put("unidadNegocioNombre", grupoAutorizacion.getUnidadNegocio().getNombre());
					grupoAutorizacionJSONObject.put("unidadNegocioId", grupoAutorizacion.getUnidadNegocio().getId());
					grupoAutorizacionJSONArray.add(grupoAutorizacionJSONObject);
				}
				result.put("listadoGrupoAutorizacion", grupoAutorizacionJSONArray);
				
			}
			
			if(tipoConsulta.equals("encontrarTodos")){ 
				List<Descuento> results = descuentoDAO.buscarTodos();
				int i = 0;
				for(i=0; i<results.size(); i++){
					descuento = results.get(i);
					descuentoJSONObject.put("codigo", descuento.getId());
					descuentoJSONObject.put("unidadNegocioNombre", descuento.getGrupoAutorizacion().getUnidadNegocio().getNombre());
					descuentoJSONObject.put("unidadNegocioId", descuento.getGrupoAutorizacion().getUnidadNegocio().getId());
					descuentoJSONObject.put("nombre", descuento.getNombre());
					descuentoJSONObject.put("descripcion", descuento.getDescripcion());
					descuentoJSONObject.put("rangoInicial", descuento.getRangoInicial());
					descuentoJSONObject.put("rangoFinal", descuento.getRangoFinal());
					descuentoJSONObject.put("cargoAutoriza", descuento.getCargoAutoriza());
					
					if(descuento.getActivo())
						descuentoJSONObject.put("activo", "Si");
					else
						descuentoJSONObject.put("activo", "No");

					descuentoJSONArray.add(descuentoJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoDescuento", descuentoJSONArray);
				
				List< Cargo> listaCargos = cargoDAO.buscarTodos();
				
				JSONObject cargoJSONObject = new JSONObject();
				JSONArray cargoJSONArray = new JSONArray();
				
				for (i=0; i<listaCargos.size(); i++){
					cargo = listaCargos.get(i);
					cargoJSONObject.put("codigo", cargo.getId());
					cargoJSONObject.put("nombre", cargo.getNombre());
					cargoJSONObject.put("nombreGenerico", cargo.getNombreGenerico());
					cargoJSONObject.put("activo", cargo.getActivo());
					cargoJSONArray.add(cargoJSONObject);
				}
				result.put("listadoCargo", cargoJSONArray);
				
				List<GrupoAutorizacion> listGrupoAutorizacion =  grupoAutorizacionDAO.buscarActivos();
				JSONObject grupoAutorizacionJSONObject = new JSONObject();
				JSONArray grupoAutorizacionJSONArray = new JSONArray();
				for (i=0; i<listGrupoAutorizacion.size(); i++){
					grupoAutorizacion = listGrupoAutorizacion.get(i);
					grupoAutorizacionJSONObject.put("codigo", grupoAutorizacion.getId());
					grupoAutorizacionJSONObject.put("unidadNegocioNombre", grupoAutorizacion.getUnidadNegocio().getNombre());
					grupoAutorizacionJSONObject.put("unidadNegocioId", grupoAutorizacion.getUnidadNegocio().getId());
					grupoAutorizacionJSONArray.add(grupoAutorizacionJSONObject);
				}
				result.put("listadoGrupoAutorizacion", grupoAutorizacionJSONArray);
				
			}			
			// Encontramos las descuentoes activas ayanez
			if(tipoConsulta.equals("encontrarDescuentoesActivas")){
				List<Descuento> listado = descuentoDAO.buscarActivos();
				if(listado.size() > 0) {
					JSONObject descuentoesJSON = new JSONObject();
					for(int i=0; i<listado.size(); i++) {
						descuento = (Descuento) listado.get(i);					
						descuentoesJSON.put("id", descuento.getId());
						descuentoesJSON.put("nombre", descuento.getNombre());
						descuentoJSONArray.add(descuentoesJSON);
					}
				}
				result.put("descuentoes", descuentoJSONArray);
			}

			if(tipoConsulta.equals("crear"))
				descuentoTransaction.crear(descuento);

			if(tipoConsulta.equals("actualizar")){
				descuento = descuentoTransaction.editar(descuento);
			}

			if(tipoConsulta.equals("eliminar"))
				descuentoTransaction.eliminar(descuento);


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
