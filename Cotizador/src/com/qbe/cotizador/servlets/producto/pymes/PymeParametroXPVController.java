package com.qbe.cotizador.servlets.producto.pymes;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.producto.pymes.PymeParametroXPuntoVentaDAO;
import com.qbe.cotizador.model.PymeParametroXPuntoVenta;
import com.qbe.cotizador.transaction.producto.pymes.PymeParametroXPuntoVentaTransaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class PymeParametroXPVController
 */
@WebServlet("/PymeParametroXPVController")
public class PymeParametroXPVController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymeParametroXPVController() {
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
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String parametroPPVId = request.getParameter("parametroPPVId")== null ? "" : request.getParameter("parametroPPVId");
			String contenedorEnsuranceId = request.getParameter("contenedorEnsuranceId")== null ? "" : request.getParameter("contenedorEnsuranceId");
			String emisionDirecta = request.getParameter("emisionDirecta")== null ? "" : request.getParameter("emisionDirecta");
			String plantillaEnsuranceId = request.getParameter("plantillaEnsuranceId")== null ? "" : request.getParameter("plantillaEnsuranceId");
			String modificarTasa = request.getParameter("modificarTasa")== null ? "" : request.getParameter("modificarTasa");
			String puntoVentaId = request.getParameter("puntoVentaId")== null ? "" : request.getParameter("puntoVentaId");
			String visualizarTasas = request.getParameter("visualizarTasas")== null ? "" : request.getParameter("visualizarTasas");
			
			PymeParametroXPuntoVenta parametroPymeXPV = new PymeParametroXPuntoVenta();
			PymeParametroXPuntoVentaDAO parametroPymeXPVDAO = new PymeParametroXPuntoVentaDAO();
			PymeParametroXPuntoVentaTransaction parametroPymeXPVTransaction=new PymeParametroXPuntoVentaTransaction();
 			
 			JSONObject parametroXPVJSONbject = new JSONObject();
			JSONArray parametroXPVJSONArray = new JSONArray();
			
			if(!parametroPPVId.equals(""))
				parametroPymeXPV.setParametroPuntoVentaId(new BigInteger(parametroPPVId));
			if(!contenedorEnsuranceId.equals(""))
				parametroPymeXPV.setContenedorEnsuranceId(new BigInteger(contenedorEnsuranceId));			
			if(!emisionDirecta.equals(""))
				parametroPymeXPV.setEmisionDirecta(Boolean.parseBoolean(emisionDirecta));
			if(!plantillaEnsuranceId.equals(""))
				parametroPymeXPV.setPlantillaEnsuranceId(new BigInteger(plantillaEnsuranceId));
			if(!modificarTasa.equals(""))
				parametroPymeXPV.setPuedeModificarTasas(Boolean.parseBoolean(modificarTasa));
			if(!puntoVentaId.equals(""))
				parametroPymeXPV.setPuntoVentaId(new BigInteger(puntoVentaId));
			if(!visualizarTasas.equals(""))
				parametroPymeXPV.setPuedeVisualizarTasas(Boolean.parseBoolean(visualizarTasas));
					
			if(tipoConsulta.equals("buscarPorId")){
				 parametroPymeXPV = parametroPymeXPVDAO.obtenerPorPuntoVentaId(Integer.parseInt(puntoVentaId));
				 
				 result.put("parametroPPVId", parametroPymeXPV.getParametroPuntoVentaId());
				 result.put("contenedorEnsuranceId", parametroPymeXPV.getContenedorEnsuranceId());
				 result.put("emisionDirecta", parametroPymeXPV.getEmisionDirecta());
				 result.put("plantillaEnsuranceId", parametroPymeXPV.getPlantillaEnsuranceId());
				 result.put("modificarTasa", parametroPymeXPV.getPuedeModificarTasas());
				 result.put("puntoVentaId", parametroPymeXPV.getPuntoVentaId());
				 result.put("visualizarTasas", parametroPymeXPV.getPuedeVisualizarTasas());
			}
			
			if(tipoConsulta.equals("crear")){
				parametroPymeXPVTransaction.crear(parametroPymeXPV);
			}
			
			if(tipoConsulta.equals("actualizar")){
				parametroPymeXPVTransaction.editar(parametroPymeXPV);
			}
			
			if(tipoConsulta.equals("eliminar")){
				parametroPymeXPVTransaction.eliminar(parametroPymeXPV);
			}
			
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			e.printStackTrace();
		}
	}

}
