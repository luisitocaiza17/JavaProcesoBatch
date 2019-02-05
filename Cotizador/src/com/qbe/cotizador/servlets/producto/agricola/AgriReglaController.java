package com.qbe.cotizador.servlets.producto.agricola;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.entidad.CantonDAO;
import com.qbe.cotizador.dao.entidad.ProvinciaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriReglaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCalculoDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoDAO;
import com.qbe.cotizador.model.AgriRegla;
import com.qbe.cotizador.model.AgriTipoCalculo;
import com.qbe.cotizador.model.AgriTipoCultivo;
import com.qbe.cotizador.model.Canton;
import com.qbe.cotizador.model.Provincia;
import com.qbe.cotizador.transaction.producto.agricola.AgriReglaTransaction;

/**
 * Servlet implementation class AgriReglaController
 */
@WebServlet("/AgriReglaController")
public class AgriReglaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgriReglaController() {
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
			String ReglaId = request.getParameter("ReglaId")==null?"":request.getParameter("ReglaId");
			String TipoCultivoId = request.getParameter("TipoCultivoId")==null?"":request.getParameter("TipoCultivoId");
			String CicloId = request.getParameter("CicloId")==null?"":request.getParameter("CicloId");
			String TipoCalculoId = request.getParameter("TipoCalculoId")==null?"":request.getParameter("TipoCalculoId");
			String ProvinciaId = request.getParameter("ProvinciaId")==null?"":request.getParameter("ProvinciaId");
			String CantonId = request.getParameter("CantonId")==null?"":request.getParameter("CantonId");
			String CostoProduccion = request.getParameter("CostoProduccion")==null?"":request.getParameter("CostoProduccion");
			String Tasa = request.getParameter("Tasa")==null?"":request.getParameter("Tasa");
			String CostoMantenimiento = request.getParameter("CostoMantenimiento")==null?"":request.getParameter("CostoMantenimiento");
			String AceptabilidadDesde = request.getParameter("AceptabilidadDesde")==null?"":request.getParameter("AceptabilidadDesde");
			String AceptabilidadHasta = request.getParameter("AceptabilidadHasta")==null?"":request.getParameter("AceptabilidadHasta");
			String Observaciones = request.getParameter("Observaciones")==null?"":request.getParameter("Observaciones");
			
			JSONObject ReglaJSONObjetc = new JSONObject();
			JSONArray ReglaJSONArray = new JSONArray();
			
			AgriRegla agriRegla = new AgriRegla();
			AgriReglaDAO agriReglaDAO = new AgriReglaDAO();
			AgriReglaTransaction agriReglaTransaction = new AgriReglaTransaction();
			
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (!ReglaId.equals(""))
				agriRegla.setReglaId(new BigInteger(ReglaId) );
			if (!TipoCultivoId.equals(""))
				agriRegla.setTipoCultivoId(new BigInteger(TipoCultivoId));
			if (!CicloId.equals(""))
				agriRegla.setClicloId(new BigInteger(CicloId));
			if (!TipoCalculoId.equals(""))
				agriRegla.setTipoCalculoId(new BigInteger(TipoCalculoId));
			if (!ProvinciaId.equals(""))
					agriRegla.setProvinciaId(new BigInteger(ProvinciaId));
			if (!CantonId.equals(""))
				agriRegla.setCantonId(new BigInteger(CantonId));
			if (!CostoProduccion.equals(""))
				agriRegla.setCostoProduccion(new Float(CostoProduccion));
			if (!Tasa.equals(""))
				agriRegla.setTasa(new Float(Tasa));
			if (!CostoMantenimiento.equals(""))
				agriRegla.setCostoMantenimiento(new Float(CostoMantenimiento));
			if (!AceptabilidadDesde.equals("")){
				Date fechaD = df.parse(AceptabilidadDesde);
				agriRegla.setAceptabilidadDesde(fechaD);
			}
			if (!AceptabilidadHasta.equals("")){
				Date fechaH = df.parse(AceptabilidadHasta);
				agriRegla.setAceptabilidadHasta(fechaH);
			}
			if (!Observaciones.equals(""))
				agriRegla.setObservaciones(Observaciones);
			
			//if (!Estado.equals(""))
			//agriRegla.setEstado(new Integer(Estado));
			if (tipoConsulta.equals("encontrarTodos"))
			{
				List<AgriRegla> results = agriReglaDAO.BuscarTodos();
				for (AgriRegla Regla: results)
				{
					ReglaJSONObjetc.put("ReglaId", Regla.getReglaId());
					
					AgriTipoCultivoDAO agriTipoCultivoDAO = new AgriTipoCultivoDAO();
					AgriTipoCultivo agriTipoCultivo = agriTipoCultivoDAO.BuscarPorId(Regla.getTipoCultivoId());		
					ReglaJSONObjetc.put("TipoCultivoId", agriTipoCultivo.getNombre());	
					AgriTipoCalculoDAO agriTipoCalculoDAO = new AgriTipoCalculoDAO();
					AgriTipoCalculo agriTipoCalculo = agriTipoCalculoDAO.BuscarPorId(Regla.getTipoCalculoId());
					ReglaJSONObjetc.put("TipoCalculoId",agriTipoCalculo.getNombre());
					
					ProvinciaDAO provinciaDAO = new ProvinciaDAO();
					Provincia provincia = provinciaDAO.buscarPorId(Regla.getProvinciaId().toString());
					ReglaJSONObjetc.put("ProvinciaId", provincia.getNombre());
					
					CantonDAO ciudadDAO = new CantonDAO();
					Canton ciudad= ciudadDAO.buscarPorId(Regla.getCantonId().toString());
					ReglaJSONObjetc.put("CantonId", ciudad.getNombre());
					if (Regla.getEstado()==1)
						ReglaJSONObjetc.put("Estado", "Activo");
					else 
						ReglaJSONObjetc.put("Estado", "Inactivo");
					ReglaJSONArray.add(ReglaJSONObjetc);
				}
				result.put("ReglaJSONArray", ReglaJSONArray);
			}
			if (tipoConsulta.equals("obtenerPorId"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				AgriRegla results =agriReglaDAO.BuscarPorId(new BigInteger(ReglaId));
				result.put("ReglaId", results.getReglaId());
				result.put("TipoCultivoId", results.getTipoCultivoId());
				result.put("CicloId", results.getClicloId());
				result.put("TipoCalculoId", results.getTipoCalculoId());
				result.put("ProvinciaId", results.getProvinciaId());
				result.put("CantonId", results.getCantonId());
				
				result.put("CostoProduccion", results.getCostoProduccion());
				result.put("Tasa", results.getTasa());
				result.put("CostoMantenimiento", results.getCostoMantenimiento());
				if (results.getAceptabilidadDesde()!=null)
					result.put("AceptabilidadDesde", formatter.format(results.getAceptabilidadDesde()));
				else 
					result.put("AceptabilidadDesde", null);
				if (results.getAceptabilidadHasta()!=null)
					result.put("AceptabilidadHasta", formatter.format(results.getAceptabilidadHasta()));
				else 
					result.put("AceptabilidadHasta", null);
				result.put("Observaciones", results.getObservaciones());
				result.put("Estado", results.getEstado());
			}
			if (tipoConsulta.equals("crear")){
				agriRegla.setEstado(1);
				agriReglaTransaction.crear(agriRegla);
				}
			
			if (tipoConsulta.equals("editar")){
				agriRegla.setEstado(1);
				agriReglaTransaction.editar(agriRegla);
				}
			
			if (tipoConsulta.equals("eliminar"))
				agriReglaTransaction.eliminar(agriRegla);
			
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
