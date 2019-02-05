package com.qbe.cotizador.servlets.producto.pymes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.producto.pymes.PymeCoberturaConfiguradaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeCoberturaTasaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeConfiguracionCoberturaDAO;
import com.qbe.cotizador.model.PymeCoberturaConfigurada;
import com.qbe.cotizador.model.PymeCoberturaTasa;
import com.qbe.cotizador.model.PymeConfiguracionCobertura;
import com.qbe.cotizador.transaction.producto.pymes.PymeCoberturaTasaTransaction;
import com.qbe.cotizador.transaction.producto.pymes.PymeConfiguracionCoberturaTransaction;

/**
 * Servlet implementation class PymeConfiguracionCoberturaController
 */
@WebServlet("/PymeConfiguracionCoberturaController")
public class PymeConfiguracionCoberturaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymeConfiguracionCoberturaController() {
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
			String configuracionCoberturaId = request.getParameter("configuracionCoberturaId") == null ? "" : request.getParameter("configuracionCoberturaId");
			String coberturaPymesId = request.getParameter("coberturaPymesId") == null ? "" : request.getParameter("coberturaPymesId");
			String grupoPorProductoId = request.getParameter("grupoPorProductoId") == null ? "" : request.getParameter("grupoPorProductoId");
			String tipoDeclaracion = request.getParameter("tipoDeclaracion") == null ? "" : request.getParameter("tipoDeclaracion");
			String origenValorLimiteAsegurado = request.getParameter("origenValorLimiteAsegurado") == null ? "" : request.getParameter("origenValorLimiteAsegurado");
			String porcentajeLimiteAsegurado = request.getParameter("porcentajeLimiteAsegurado") == null ? "" : request.getParameter("porcentajeLimiteAsegurado");
			String valorMaximoLimiteAsegurado = request.getParameter("valorMaximoLimiteAsegurado") == null ? "" : request.getParameter("valorMaximoLimiteAsegurado");
			String origenValorLimiteCobertura = request.getParameter("origenValorLimiteCobertura") == null ? "" : request.getParameter("origenValorLimiteCobertura");
			String porcentajeLimiteCobertura = request.getParameter("porcentajeLimiteCobertura") == null ? "" : request.getParameter("porcentajeLimiteCobertura");
			String valorMaximoLimiteCobertura = request.getParameter("valorMaximoLimiteCobertura") == null ? "" : request.getParameter("valorMaximoLimiteCobertura");
			String textoDeducible = request.getParameter("textoDeducible") == null ? "" : request.getParameter("textoDeducible");
			String tipoTasa = request.getParameter("tipoTasa") == null ? "" : request.getParameter("tipoTasa");
			String tasa = request.getParameter("tasa") == null ? "" : request.getParameter("tasa");			
			String coberturaCopiaId = request.getParameter("coberturaCopiaId") == null ? "" : request.getParameter("coberturaCopiaId");
			String incluyeEnProducto = request.getParameter("incluyeEnProducto") == null ? "" : request.getParameter("incluyeEnProducto");
			String ordenPresentacion = request.getParameter("ordenPresentacion") == null ? "" : request.getParameter("ordenPresentacion");
			String dependeValor = request.getParameter("dependeValor") == null ? "" : request.getParameter("dependeValor");
			String listaValorMinimo = request.getParameter("listaValorMinimo") == null ? "" : request.getParameter("listaValorMinimo");
			String listaValorMaximo = request.getParameter("listaValorMaximo") == null ? "" : request.getParameter("listaValorMaximo");
			String listaTasa = request.getParameter("listaTasa") == null ? "" : request.getParameter("listaTasa");

			JSONObject configuracionJSONObject = new JSONObject();
			JSONArray configuracionJSONArray = new JSONArray();
			
			PymeConfiguracionCoberturaDAO configuracionCoberturaDAO = new PymeConfiguracionCoberturaDAO();
			PymeCoberturaConfiguradaDAO coberturaConfiguradaDAO = new PymeCoberturaConfiguradaDAO();
			PymeCoberturaTasaDAO coberturaTasaDAO=new PymeCoberturaTasaDAO();
			
			PymeConfiguracionCoberturaTransaction pymeConfiguracionCoberturaTransaction=new PymeConfiguracionCoberturaTransaction();
			PymeCoberturaTasaTransaction pymeCoberturaTasaTransaction=new PymeCoberturaTasaTransaction(); 
			

			PymeConfiguracionCobertura configuracionCobertura = new PymeConfiguracionCobertura();
 			if(!configuracionCoberturaId.equals(""))
 				configuracionCobertura.setConfiguracionCoberturaId(new BigInteger(configuracionCoberturaId)); 	
 			
 			if(coberturaPymesId != null && !coberturaPymesId.equals(""))
 				configuracionCobertura.setCoberturaPymesId(new BigInteger(coberturaPymesId)); 			
 			
 			if(grupoPorProductoId != null && !grupoPorProductoId.equals(""))
 				configuracionCobertura.setGrupoPorProductoId(new BigInteger(grupoPorProductoId));
 			
 			if(tipoDeclaracion != null && !tipoDeclaracion.equals(""))
 				configuracionCobertura.setTipoDeclaracion(Integer.parseInt(tipoDeclaracion));
 			
 			if(origenValorLimiteAsegurado != null && !origenValorLimiteAsegurado.equals(""))
 				configuracionCobertura.setOrigenValorLimiteAsegurado(Integer.parseInt(origenValorLimiteAsegurado));
 			
 			if(porcentajeLimiteAsegurado != null && !porcentajeLimiteAsegurado.equals(""))
 				configuracionCobertura.setPorcentajeLimiteAsegurado(Double.parseDouble(porcentajeLimiteAsegurado));
 			
 			if(valorMaximoLimiteAsegurado != null && !valorMaximoLimiteAsegurado.equals(""))
 				configuracionCobertura.setValorMaximoLimiteAsegurado(Double.parseDouble(valorMaximoLimiteAsegurado));
 			
 			if(origenValorLimiteCobertura != null && !origenValorLimiteCobertura.equals(""))
 				configuracionCobertura.setOrigenValorLimiteCobertura(Integer.parseInt(origenValorLimiteCobertura));
 			
 			if(porcentajeLimiteCobertura != null && !porcentajeLimiteCobertura.equals(""))
 				configuracionCobertura.setPorcentajeLimiteCobertura(Double.parseDouble(porcentajeLimiteCobertura));
 			
 			if(valorMaximoLimiteCobertura != null && !valorMaximoLimiteCobertura.equals(""))
 				configuracionCobertura.setValorMaximoLimiteCobertura(Double.parseDouble(valorMaximoLimiteCobertura));
 			
 			if(!textoDeducible.equals(""))
 				configuracionCobertura.setTextoDeducible(textoDeducible);
 			
 			if(tipoTasa != null && !tipoTasa.equals(""))
 				configuracionCobertura.setTipoTasa(Integer.parseInt(tipoTasa));
 		
 			if(coberturaCopiaId != null && !coberturaCopiaId.equals(""))
 				configuracionCobertura.setCoberturaCopiaId(new BigInteger(coberturaCopiaId));
 			
 			if(tasa != null && !tasa.equals(""))
 				configuracionCobertura.setTasa(Double.parseDouble(tasa));
 			
 			if(incluyeEnProducto != null && !incluyeEnProducto.equals(""))
 				configuracionCobertura.setIncluyeEnProducto(Integer.parseInt(incluyeEnProducto));
 			
 			if(ordenPresentacion != null && !ordenPresentacion.equals(""))
 				configuracionCobertura.setOrdenPresentacion(Integer.parseInt(ordenPresentacion));	
 			
 			if(dependeValor != null && !dependeValor.equals(""))
 				configuracionCobertura.setDependeValor(Integer.parseInt(dependeValor));	
 			
			if(tipoConsulta.equals("encontrarTodosVista")){ 
				List<PymeCoberturaConfigurada> results = coberturaConfiguradaDAO.buscarTodos();
				
				for(PymeCoberturaConfigurada configuracion:results){
					configuracionJSONObject.put("configuracionCoberturaId", configuracion.getId());
					configuracionJSONObject.put("coberturaNombre", configuracion.getNombre());
					configuracionJSONObject.put("grupoNombre", configuracion.getNombreComercialProducto());
					if(configuracion.getTipoDeclaracion()==1)
						configuracionJSONObject.put("tipoDeclaracionNombre", "GENERAL");
					else
						configuracionJSONObject.put("tipoDeclaracionNombre", "POR DIRECCIÓN");
					configuracionJSONArray.add(configuracionJSONObject);
				}
				result.put("listadoConfiguraciones", configuracionJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarTasasPorProductoID")){ 
				List<PymeCoberturaTasa> results = coberturaTasaDAO.buscarPorConfiguracionCoberturaId(new BigInteger(configuracionCoberturaId));
				
				for(PymeCoberturaTasa configuracion:results){
					configuracionJSONObject.put("configuracionCoberturaId", configuracion.getConfiguracionCoberturaId());
					configuracionJSONObject.put("valorCoberturaInicial", configuracion.getValorCoberturaInicial());
					configuracionJSONObject.put("valorCoberturaFinal", configuracion.getValorCoberturaFinal());
					configuracionJSONObject.put("tasa", configuracion.getTasa());
					configuracionJSONArray.add(configuracionJSONObject);
				}
				result.put("listadoTasas", configuracionJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarConfiguracionesPorProductoId")){ 
				List<PymeCoberturaConfigurada> listadoCoberturasConfig = coberturaConfiguradaDAO.buscarPorGrupoPorProductoId(new BigInteger(grupoPorProductoId));				
				for(PymeCoberturaConfigurada configuracion:listadoCoberturasConfig){
					configuracionJSONObject.put("configuracionCoberturaId", configuracion.getId());
					configuracionJSONObject.put("coberturaNombre", configuracion.getNombre());
					configuracionJSONObject.put("coberturaId", configuracion.getCoberturaPymesId());
					configuracionJSONObject.put("grupoNombre", configuracion.getNombreComercialProducto());
					if(configuracion.getTipoDeclaracion()==1)
						configuracionJSONObject.put("tipoDeclaracionNombre", "GENERAL");
					else
						configuracionJSONObject.put("tipoDeclaracionNombre", "POR DIRECCIÓN");
					configuracionJSONObject.put("tipoDeclaracion", configuracion.getTipoDeclaracion());
					configuracionJSONObject.put("origenValorLimiteAsegurado", configuracion.getOrigenValorLimiteAsegurado());
					configuracionJSONObject.put("porcentajeLimiteAsegurado", configuracion.getPorcentajeLimiteAsegurado());
					configuracionJSONObject.put("valorMaximoLimiteAsegurado", configuracion.getValorMaximoLimiteAsegurado());
					configuracionJSONObject.put("origenValorLimiteCobertura", configuracion.getOrigenValorLimiteCobertura());
					configuracionJSONObject.put("porcentajeLimiteCobertura", configuracion.getPorcentajeLimiteCobertura());
					configuracionJSONObject.put("valorMaximoLimiteCobertura", configuracion.getValorMaximoLimiteCobertura());
					configuracionJSONObject.put("textoDeducible", configuracion.getTextoDeducible());					
					configuracionJSONObject.put("tipoTasa", configuracion.getTipoTasa());
					configuracionJSONObject.put("tasa", configuracion.getTasa());
					configuracionJSONObject.put("coberturaCopiaId", configuracion.getCoberturaCopiaId());
					configuracionJSONObject.put("incluyeEnProducto", configuracion.getIncluyeEnProducto());
					configuracionJSONObject.put("ordenPresentacion", configuracion.getOrdenPresentacion());					
					configuracionJSONObject.put("dependeValor", configuracion.getDependeValor());
					configuracionJSONObject.put("tipoCoberturaId", configuracion.getTipoCoberturaId());
					
					configuracionJSONArray.add(configuracionJSONObject);
				}
				result.put("listadoConfiguraciones", configuracionJSONArray);
			}
			
			if(tipoConsulta.equals("encontrarPorId")){ 
				PymeConfiguracionCobertura results = configuracionCoberturaDAO.buscarPorId(new BigInteger(configuracionCoberturaId));				
				
				result.put("configuracionCoberturaId", results.getConfiguracionCoberturaId());
				result.put("coberturaPymesId", results.getCoberturaPymesId());
				result.put("grupoPorProductoId", results.getGrupoPorProductoId());
				result.put("tipoDeclaracion", results.getTipoDeclaracion());
				result.put("origenValorLimiteAsegurado", results.getOrigenValorLimiteAsegurado());
				result.put("porcentajeLimiteAsegurado", results.getPorcentajeLimiteAsegurado());
				result.put("valorMaximoLimiteAsegurado", results.getValorMaximoLimiteAsegurado());
				result.put("origenValorLimiteCobertura", results.getOrigenValorLimiteCobertura());
				result.put("porcentajeLimiteCobertura", results.getPorcentajeLimiteCobertura());
				result.put("valorMaximoLimiteCobertura", results.getValorMaximoLimiteCobertura());
				result.put("textoDeducible", results.getTextoDeducible());					
				result.put("tipoTasa", results.getTipoTasa());
				result.put("tasa", results.getTasa());
				result.put("coberturaCopiaId", results.getCoberturaCopiaId());
				result.put("incluyeEnProducto", results.getIncluyeEnProducto());
				result.put("ordenPresentacion", results.getOrdenPresentacion());					
				result.put("dependeValor", results.getDependeValor());
				JSONArray tasasJSONArray= new JSONArray(); 
				
				List<PymeCoberturaTasa> listadoCoberturasTasas=coberturaTasaDAO.buscarPorConfiguracionCoberturaId(results.getConfiguracionCoberturaId());
				for(PymeCoberturaTasa detActual:listadoCoberturasTasas){
					JSONObject tasaJSON = new JSONObject();
					tasaJSON.put("coberturaTasaId", detActual.getConfiguracionCoberturaId());
					tasaJSON.put("configuracionCoberturaId", detActual.getConfiguracionCoberturaId());
					tasaJSON.put("valorCoberturaInicial", detActual.getValorCoberturaInicial());
					tasaJSON.put("valorCoberturaFinal", detActual.getValorCoberturaFinal());
					tasaJSON.put("tasa", detActual.getTasa());
					tasasJSONArray.add(tasaJSON);
				}
				result.put("tasas", tasasJSONArray);
			}
			
			if(tipoConsulta.equals("crear")){
				
				PymeConfiguracionCobertura configuracion = pymeConfiguracionCoberturaTransaction.crear(configuracionCobertura);
				String[] arrlistaValorMinimo = listaValorMinimo.split(",");
				String[] arrlistaValorMaximo = listaValorMaximo.split(",");
				String[] arrlistaTasa = listaTasa.split(",");
				PymeCoberturaTasa nuevaCoberturaTasa;
				for(int i=1; i< arrlistaValorMinimo.length; i++){
					nuevaCoberturaTasa=new PymeCoberturaTasa();
					nuevaCoberturaTasa.setConfiguracionCoberturaId(configuracion.getConfiguracionCoberturaId());
					nuevaCoberturaTasa.setValorCoberturaInicial(Float.valueOf(arrlistaValorMinimo[i]));
					nuevaCoberturaTasa.setValorCoberturaFinal(Float.valueOf(arrlistaValorMaximo[i]));
					nuevaCoberturaTasa.setTasa(Float.valueOf(arrlistaTasa[i]));
					pymeCoberturaTasaTransaction.crear(nuevaCoberturaTasa);
				}
			}
			
			if(tipoConsulta.equals("editar")){
				PymeConfiguracionCobertura configuracion = pymeConfiguracionCoberturaTransaction.editar(configuracionCobertura);
				List<PymeCoberturaTasa> listadoCoberturasTasas=coberturaTasaDAO.buscarPorConfiguracionCoberturaId(configuracion.getConfiguracionCoberturaId());
				for(PymeCoberturaTasa ct:listadoCoberturasTasas){
					pymeCoberturaTasaTransaction.eliminar(ct);
				}
				String[] arrlistaValorMinimo = listaValorMinimo.split(",");
				String[] arrlistaValorMaximo = listaValorMaximo.split(",");
				String[] arrlistaTasa = listaTasa.split(",");
				PymeCoberturaTasa nuevaCoberturaTasa;
				for(int i=1; i< arrlistaValorMinimo.length; i++){
					nuevaCoberturaTasa=new PymeCoberturaTasa();
					nuevaCoberturaTasa.setConfiguracionCoberturaId(configuracion.getConfiguracionCoberturaId());
					nuevaCoberturaTasa.setValorCoberturaInicial(Float.valueOf(arrlistaValorMinimo[i]));
					nuevaCoberturaTasa.setValorCoberturaFinal(Float.valueOf(arrlistaValorMaximo[i]));
					nuevaCoberturaTasa.setTasa(Float.valueOf(arrlistaTasa[i]));
					pymeCoberturaTasaTransaction.crear(nuevaCoberturaTasa);
				}
			}
			
			if(tipoConsulta.equals("eliminar")){
				pymeConfiguracionCoberturaTransaction.eliminar(configuracionCobertura);
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
