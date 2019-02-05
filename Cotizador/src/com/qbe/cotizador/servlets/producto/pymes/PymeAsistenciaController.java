package com.qbe.cotizador.servlets.producto.pymes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.producto.pymes.PymeAsistenciaDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoProductoDAO;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.GrupoProducto;
import com.qbe.cotizador.model.PymeAsistencia;
import com.qbe.cotizador.transaction.producto.pymes.PymeAsistenciaTransaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PymeAsistenciaController
 */
@WebServlet("/PymeAsistenciaController")
public class PymeAsistenciaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymeAsistenciaController() {
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
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String asistenciaId = request.getParameter("asistenciaId") == null ? "" : request.getParameter("asistenciaId");
			String esPredeterminada = request.getParameter("esPredeterminada") == null ? "" : request.getParameter("esPredeterminada");
			String grupoPorProductoId = request.getParameter("grupoPorProductoId") == null ? "" : request.getParameter("grupoPorProductoId");
			String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
			String valor = request.getParameter("valor") == null ? "" : request.getParameter("valor");
			
			JSONObject asistenciaJSONObject = new JSONObject();
			JSONArray asistenciaJSONArray = new JSONArray();
			
			PymeAsistencia pymeAsistencia = new PymeAsistencia();
			PymeAsistenciaDAO pymeAsistenciaDAO = new PymeAsistenciaDAO();
			PymeAsistenciaTransaction pymeAsistenciaTransaction=new PymeAsistenciaTransaction();
			
			JSONObject grupoPPJSONObject = new JSONObject();
			JSONArray grupoPPJSONArray = new JSONArray();
			
			if(!asistenciaId.equals(""))
				pymeAsistencia.setAsistenciaId(new BigInteger(asistenciaId));
			if(!esPredeterminada.equals("") && esPredeterminada!=null)
				pymeAsistencia.setEsPredeterminada(Boolean.parseBoolean(esPredeterminada));
			if(!grupoPorProductoId.equals("") && grupoPorProductoId!=null)
				pymeAsistencia.setGrupoPorProductoId(new BigInteger( grupoPorProductoId));
			if(!nombre.equals("") && nombre!=null)
				pymeAsistencia.setNombre(nombre);
			if(!valor.equals("") && valor!=null)
				pymeAsistencia.setValor(Double.parseDouble(valor));
			
			if(tipoConsulta.equals("encontrarTodos")){
				List<PymeAsistencia> results = pymeAsistenciaDAO.buscarTodos();
				
				for(PymeAsistencia asistencia : results){
					asistenciaJSONObject.put("asistenciaId", asistencia.getAsistenciaId());
					if(asistencia.getEsPredeterminada())
						asistenciaJSONObject.put("esPredeterminada", "Si");
					else
						asistenciaJSONObject.put("esPredeterminada", "No");
					GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
					GrupoPorProducto grupoPorProducto = grupoPorProductoDAO.buscarPorId(asistencia.getGrupoPorProductoId().toString());
					asistenciaJSONObject.put("grupoPorProductoId", grupoPorProducto.getNombreComercialProducto());
					asistenciaJSONObject.put("nombre", asistencia.getNombre());
					asistenciaJSONObject.put("valor", asistencia.getValor());
					asistenciaJSONArray.add(asistenciaJSONObject);
				}
				result.put("asistenciaJSONArray", asistenciaJSONArray);
				
				GrupoProductoDAO grupoProductoDAO = new GrupoProductoDAO();
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorNombre("PYMES");
				
				GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
				List<GrupoPorProducto> grupoPorProducto = grupoPorProductoDAO.buscarTodosPorGrupo(grupoProducto);
				for(GrupoPorProducto grupoList : grupoPorProducto){
					grupoPPJSONObject.put("grupoPPId", grupoList.getId());
					grupoPPJSONObject.put("grupoPPNombre", grupoList.getNombreComercialProducto());
					grupoPPJSONArray.add(grupoPPJSONObject);
				}
				result.put("grupoPPJSONArray", grupoPPJSONArray);
			}
			
			if(tipoConsulta.equals("obtenerPorId")){
				PymeAsistencia results = pymeAsistenciaDAO.buscarPorId(new BigInteger(asistenciaId));
				
				result.put("asistenciaId", results.getAsistenciaId());
				result.put("esPredeterminada", results.getEsPredeterminada());
				result.put("grupoPorProductoId", results.getGrupoPorProductoId());
				result.put("nombre", results.getNombre());
				result.put("valor", results.getValor());
			}
			
			if(tipoConsulta.equals("buscarPorProductoId")){
				List<PymeAsistencia> results = pymeAsistenciaDAO.buscarGrupoPorProductoId(new BigInteger(grupoPorProductoId));
				
				for(PymeAsistencia asistencia:results){
					asistenciaJSONObject.put("asistenciaId", asistencia.getAsistenciaId());
					asistenciaJSONObject.put("esPredeterminada", asistencia.getEsPredeterminada());
					asistenciaJSONObject.put("grupoPorProductoId", asistencia.getGrupoPorProductoId());
					asistenciaJSONObject.put("nombre", asistencia.getNombre());
					asistenciaJSONObject.put("valor", asistencia.getValor());
					asistenciaJSONArray.add(asistenciaJSONObject);
				}
				result.put("asistenciaJSONArray", asistenciaJSONArray);
			}
			
			
			if(tipoConsulta.equals("crear"))
				pymeAsistenciaTransaction.crear(pymeAsistencia);
			
			if(tipoConsulta.equals("editar"))
				pymeAsistenciaTransaction.editar(pymeAsistencia);
			
			if(tipoConsulta.equals("eliminar"))
				pymeAsistenciaTransaction.eliminar(pymeAsistencia);
			
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
