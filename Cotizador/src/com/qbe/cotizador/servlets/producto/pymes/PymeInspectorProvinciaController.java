package com.qbe.cotizador.servlets.producto.pymes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.entidad.CiudadDAO;
import com.qbe.cotizador.dao.entidad.EmpleadoDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.ProvinciaDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeInspectorProvinciaDAO;
import com.qbe.cotizador.model.Ciudad;
import com.qbe.cotizador.model.Empleado;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Provincia;
import com.qbe.cotizador.model.PymeInspectorProvincia;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.transaction.entidad.EntidadTransaction;
import com.qbe.cotizador.transaction.producto.pymes.PymeInspectorProvinciaTransaction;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Servlet implementation class PymeInspectorProvinciaController
 */
@WebServlet("/PymeInspectorProvinciaController")
public class PymeInspectorProvinciaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymeInspectorProvinciaController() {
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
		JSONObject result =  new JSONObject();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "": request.getParameter("tipoConsulta");
			String usuarioId = request.getParameter("usuarioId") == null ? "": request.getParameter("usuarioId");
			String entidadId = request.getParameter("entidadId") == null ? "": request.getParameter("entidadId");
			String provinciaId = request.getParameter("provinciaId") == null ? "": request.getParameter("provinciaId");
			String ciudadId = request.getParameter("ciudadId") == null ? "": request.getParameter("ciudadId");
			String configuracionArray = request.getParameter("configuracionArray") == null ? "": request.getParameter("configuracionArray");
			/*Objetos bdd*/			
			Entidad entidad = new Entidad();
			EntidadDAO entidadDAO = new EntidadDAO();
			
			
			Usuario usuario = new Usuario();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			PymeInspectorProvincia pymeInspectorProvincia = new PymeInspectorProvincia();
			PymeInspectorProvinciaDAO pymeInspectorProvinciaDAO = new PymeInspectorProvinciaDAO();
			PymeInspectorProvinciaTransaction pymeTransaction = new PymeInspectorProvinciaTransaction();
			
			Provincia provincia = new Provincia();
			ProvinciaDAO provinciaDAO = new ProvinciaDAO();
			
			Ciudad ciudad = new Ciudad();
			CiudadDAO ciudadDAO = new CiudadDAO();
			
			Empleado empleado = new Empleado();
			EmpleadoDAO empleadoDAO = new EmpleadoDAO();
			
			/*Objetos bdd*/
			
			/*Objetos Json*/
			JSONObject datosEntidadJsonObject = new JSONObject();
			JSONArray datosEntidadJsonArray = new JSONArray();
			
			JSONObject entidadJsonObject = new JSONObject();
			JSONArray entidadJsonArray = new JSONArray();
			
			JSONObject pInspectorProvinciaJsonObject = new JSONObject();
			JSONArray pInspectorProvinciaJsonArray = new JSONArray();
			
			JSONObject provinciaJsonObject = new JSONObject();
			JSONArray provinciaJsonArray = new JSONArray();
			
			JSONObject ciudadJsonObject = new JSONObject();
			JSONArray ciudadJsonArray = new JSONArray();
			/*Objetos Json*/
			
			
			if(tipoConsulta.equals("buscarTodos")){
				
				List<Empleado> empleadoList = empleadoDAO.buscarActivos();
				for(Empleado empleadoList2 : empleadoList){
					entidadJsonObject.put("entidadId",empleadoList2.getEntidad().getId());
					entidadJsonObject.put("nombre",empleadoList2.getEntidad().getNombreCompleto());					
					entidadJsonArray.add(entidadJsonObject);
				}				
				result.put("listaEntidad", entidadJsonArray);
				
				List<Provincia> resultProvincia = provinciaDAO.buscarTodos();
				for(Provincia listProvincia: resultProvincia){
					provinciaJsonObject.put("provinciaId", listProvincia.getId());
					provinciaJsonObject.put("provinciaNombre", listProvincia.getNombre());
					provinciaJsonArray.add(provinciaJsonObject);
				}
				result.put("listaProvincia", provinciaJsonArray);
				
				
				List<Usuario> usuarioList = usuarioDAO.buscarActivos();
				for(Usuario list : usuarioList){											
					List<PymeInspectorProvincia> pymeInspectorProvinciaList = pymeInspectorProvinciaDAO.buscarUsuarioId(new BigInteger(list.getId()));		
					if(pymeInspectorProvinciaList.size() >= 1){
						entidad = entidadDAO.buscarPorId(list.getEntidad().getId());
						datosEntidadJsonObject.put("entidadId",entidad.getId());
						datosEntidadJsonObject.put("ensuranceId",entidad.getEntEnsurance());
						datosEntidadJsonObject.put("nombreCompleto",entidad.getNombreCompleto());
						datosEntidadJsonObject.put("login",list.getLogin());
						datosEntidadJsonObject.put("usuarioId",list.getId());
						
						if(list.getActivo()){
							datosEntidadJsonObject.put("estado","SI");
						}else{
							datosEntidadJsonObject.put("estado","NO");
						}
						if(list.getEmite()){
							datosEntidadJsonObject.put("emite", "SI");
						}else{
							datosEntidadJsonObject.put("emite", "NO");
						}										
						datosEntidadJsonArray.add(datosEntidadJsonObject);
					}					
				}
				result.put("listaInspector", datosEntidadJsonArray);	
				
			}
			
			if(tipoConsulta.equals("buscarPorId")){
				
				if(!entidadId.equals("")){
					entidad = entidadDAO.buscarPorId(entidadId);
					usuario = usuarioDAO.buscarPorEntidad(entidad);
				
					//usuario = usuarioDAO.buscarPorId(usuarioId);
						
				
					result.put("entidadId",usuario.getEntidad().getId());
					//List<PymeInspectorProvincia> pymeInspectorProvinciaList = pymeInspectorProvinciaDAO.buscarUsuarioId2(new BigInteger(usuarioId));
					List<PymeInspectorProvincia> pymeInspectorProvinciaList = pymeInspectorProvinciaDAO.buscarUsuarioId(new BigInteger(usuario.getId()));
					if(pymeInspectorProvinciaList.size() >=1 ){					
						for(PymeInspectorProvincia pIPList : pymeInspectorProvinciaList){
							provincia = provinciaDAO.buscarPorId(pIPList.getProvinciaId().toString());
							pInspectorProvinciaJsonObject.put("provinciaId", provincia.getId());
							pInspectorProvinciaJsonObject.put("proviniciaNombre", provincia.getNombre());
							ciudad = ciudadDAO.buscarPorId(pIPList.getCiudadId().toString());
							pInspectorProvinciaJsonObject.put("ciudadId", ciudad.getId());
							pInspectorProvinciaJsonObject.put("ciudadNombre", ciudad.getNombre());
							pInspectorProvinciaJsonArray.add(pInspectorProvinciaJsonObject);						
						}
					}
				}
				result.put("detalleInspector", pInspectorProvinciaJsonArray);
			}
			
			if(tipoConsulta.equals("buscarPorProvincia")){
				provincia = provinciaDAO.buscarPorId(provinciaId);
				List<Ciudad> ciudadList = ciudadDAO.buscarPorProvincia(provincia);
				if(ciudadList.size() >=1 ){					
					for(Ciudad ciudadArg : ciudadList){
						ciudadJsonObject.put("ciudadId", ciudadArg.getId());
						ciudadJsonObject.put("nombre", ciudadArg.getNombre());
						ciudadJsonArray.add(ciudadJsonObject);
					}
				}
				result.put("listaCiudad", ciudadJsonArray);
			}
			
			
			if(tipoConsulta.equals("crear")){
				entidad = entidadDAO.buscarPorId(entidadId);
				usuario = usuarioDAO.buscarPorEntidadId(entidad);
				List<PymeInspectorProvincia> pymeInspectorProvinciaList = pymeInspectorProvinciaDAO.buscarUsuarioId(new BigInteger(usuario.getId()));
				if(pymeInspectorProvinciaList.size() >=1 ){					
					for(PymeInspectorProvincia pIPList : pymeInspectorProvinciaList){
						pymeTransaction.eliminar(pIPList);
					}					
				}
				JSONArray array = (JSONArray)JSONSerializer.toJSON(configuracionArray);
                //por cada objeto JSon CREADO ..
                for (Object js:array){
                	JSONObject jsonStr=(JSONObject)JSONSerializer.toJSON(js);
                	pymeInspectorProvincia.setUsuarioId(new BigInteger(usuario.getId()));
    				pymeInspectorProvincia.setProvinciaId(new BigInteger(jsonStr.getString("provinciaId")));
    				pymeInspectorProvincia.setCiudadId(new BigInteger(jsonStr.getString("ciudadId")));
    				
    				pymeTransaction.crear(pymeInspectorProvincia);
                }	
			}
			

			if(tipoConsulta.equals("eliminar")){
				entidad = entidadDAO.buscarPorId(entidadId);
				usuario = usuarioDAO.buscarPorEntidadId(entidad);
				List<PymeInspectorProvincia> pymeInspectorProvinciaList = pymeInspectorProvinciaDAO.buscarUsuarioId(new BigInteger(usuario.getId()));
				if(pymeInspectorProvinciaList.size() >=1 ){					
					for(PymeInspectorProvincia pIPList : pymeInspectorProvinciaList){
						pymeTransaction.eliminar(pIPList);
					}					
				}
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