package com.qbe.cotizador.servlets.entidad;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.entidad.ActividadEconomicaDAO;
import com.qbe.cotizador.model.ActividadEconomica;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class actividadEconomica
 */
@WebServlet("/ActividadEconomicaController")
public class ActividadEconomicaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActividadEconomicaController() {
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
		try{
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			JSONObject actividadEconomicaJSONObject = new JSONObject();
			JSONArray actividadesEconomicasJSONArray = new JSONArray();
			ActividadEconomicaDAO actividadEconomicaDAO=new ActividadEconomicaDAO();
			ActividadEconomica actividadEconomica=new ActividadEconomica();

			if(tipoConsulta.equals("encontrarTodos")){ 
				List<ActividadEconomica> results = actividadEconomicaDAO.buscarTodos();
				int i=0;
				for(i=0; i<results.size(); i++){
					actividadEconomica = results.get(i);
					actividadEconomicaJSONObject.put("codigo", actividadEconomica.getId());
					actividadEconomicaJSONObject.put("nombre", actividadEconomica.getNombre());
					actividadesEconomicasJSONArray.add(actividadEconomicaJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoActividadesEconomicas", actividadesEconomicasJSONArray);
			}
			
			if(tipoConsulta.equals("cargarSelect2")){ 
				List<ActividadEconomica> results = actividadEconomicaDAO.buscarTodos();
				int i=0;
				for(i=0; i<results.size(); i++){
					actividadEconomica = results.get(i);
					actividadEconomicaJSONObject.put("id", actividadEconomica.getId());
					actividadEconomicaJSONObject.put("text", actividadEconomica.getNombre());
					actividadesEconomicasJSONArray.add(actividadEconomicaJSONObject);
				}
				result.put("numRegistros",i);
				result.put("listadoActividadesEconomicas", actividadesEconomicasJSONArray);
			}
			/*if(tipoConsulta.equals("crear"))
				actividadEconomicaDAO.crear(actividadEconomica);

			if(tipoConsulta.equals("actualizar"))
				actividadEconomicaDAO.editar(actividadEconomica);

			if(tipoConsulta.equals("eliminar"))
				actividadEconomicaDAO.eliminar(actividadEconomica);
*/

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
