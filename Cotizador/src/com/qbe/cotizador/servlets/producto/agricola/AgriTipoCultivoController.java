package com.qbe.cotizador.servlets.producto.agricola;

import java.io.File;
import java.io.IOException;






import java.math.BigInteger;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoDAO;
import com.qbe.cotizador.model.AgriTipoCultivo;
import com.qbe.cotizador.transaction.producto.agricola.AgriTipoCultivoTransaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AgriTipoCultivoController
 */
@WebServlet("/AgriTipoCultivoController")
public class AgriTipoCultivoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgriTipoCultivoController() {
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
		JSONObject result = new JSONObject ();
		try 
		{
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String TipoCultivoId = request.getParameter("TipoCultivoId")==null?"":request.getParameter("TipoCultivoId");
			String Nombre = request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
			String Tipo = request.getParameter("Tipo")==null?"":request.getParameter("Tipo");
			String VigenciaDias = request.getParameter("VigenciaDias")==null?"":request.getParameter("VigenciaDias");
			JSONObject TipoCultivoJSONObjetc = new JSONObject();
			JSONArray TipoCultivoJSONArray = new JSONArray();
			
			AgriTipoCultivo agriTipoCultivo = new AgriTipoCultivo();
			AgriTipoCultivoDAO agriTipoCultivoDAO = new AgriTipoCultivoDAO();
			AgriTipoCultivoTransaction agriTipoCultivoTransaction = new AgriTipoCultivoTransaction();
			
			if (!TipoCultivoId.equals(""))
				agriTipoCultivo.setTipoCultivoId(new BigInteger(TipoCultivoId) );
			if (!Nombre.equals(""))
				agriTipoCultivo.setNombre(Nombre);
			if (!Tipo.equals(""))
				agriTipoCultivo.setTipo(new Integer(Tipo));
			if (!VigenciaDias.equals(""))
				agriTipoCultivo.setVigenciaDias(new Integer(VigenciaDias));
			if (tipoConsulta.equals("encontrarTodos"))
			{
				List<AgriTipoCultivo> results = agriTipoCultivoDAO.BuscarTodos();
				for (AgriTipoCultivo tipoCultivo: results)
				{
					TipoCultivoJSONObjetc.put("TipoCultivoId", tipoCultivo.getTipoCultivoId());
					TipoCultivoJSONObjetc.put("Nombre", tipoCultivo.getNombre());
					TipoCultivoJSONObjetc.put("Tipo_", tipoCultivo.getTipo());
					if (tipoCultivo.getTipo()==1)
						TipoCultivoJSONObjetc.put("Tipo", "PERENNE");
					else 
						TipoCultivoJSONObjetc.put("Tipo", "NO PERENNE");
					TipoCultivoJSONObjetc.put("VigenciaDias",tipoCultivo.getVigenciaDias());
					TipoCultivoJSONArray.add(TipoCultivoJSONObjetc);
				}
				result.put("TipoCultivoJSONArray", TipoCultivoJSONArray);
			}
			if (tipoConsulta.equals("obtenerPorId"))
			{
				AgriTipoCultivo results =agriTipoCultivoDAO.BuscarPorId(new BigInteger(TipoCultivoId));
				result.put("TipoCultivoId", results.getTipoCultivoId());
				result.put("Nombre", results.getNombre());
				result.put("Tipo", results.getTipo());
				result.put("VigenciaDias", results.getVigenciaDias());
			}
			if (tipoConsulta.equals("crear"))
				agriTipoCultivoTransaction.crear(agriTipoCultivo);
			if (tipoConsulta.equals("editar"))
				agriTipoCultivoTransaction.editar(agriTipoCultivo);
			if (tipoConsulta.equals("eliminar"))
				agriTipoCultivoTransaction.eliminar(agriTipoCultivo);
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
		}
		catch(Exception e)
		{
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			e.printStackTrace();
		}
	}

}
