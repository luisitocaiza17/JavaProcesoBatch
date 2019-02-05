package com.qbe.cotizador.servlets.producto.agricola;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;
import com.google.common.base.Charsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.persistence.internal.expressions.OuterJoinExpressionHolder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.qbe.cotizador.dao.cotizacion.ProductoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.CantonDAO;
import com.qbe.cotizador.dao.entidad.ParroquiaDAO;
import com.qbe.cotizador.dao.entidad.ProvinciaDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.TipoIdentificacionDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriCicloDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriObjetoCotizacionDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriParametroXPuntoVentaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriReglaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoXTipoCalculoDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriVariedadDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.model.AgriCiclo;
import com.qbe.cotizador.model.AgriObjetoCotizacion;
import com.qbe.cotizador.model.AgriParametroXPuntoVenta;
import com.qbe.cotizador.model.AgriRegla;
import com.qbe.cotizador.model.AgriTipoCultivo;
import com.qbe.cotizador.model.AgriTipoCultivoXTipoCalculo;
import com.qbe.cotizador.model.AgriVariedad;
import com.qbe.cotizador.model.Canton;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.Provincia;
import com.qbe.cotizador.model.Parroquia;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.TipoIdentificacion;
import com.qbe.cotizador.util.AES_Helper;
import com.sun.faces.taglib.html_basic.OutputTextTag;

/**
 * Servlet implementation class AgriExportarDatos
 */
@WebServlet("/AgriImportarDatosOfflineController")
public class AgriImportarDatosOfflineController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgriImportarDatosOfflineController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject result = new JSONObject();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "":request.getParameter("tipoConsulta");
			String nombreArchivo = request.getParameter("nombreArchivo") == null ? "":request.getParameter("nombreArchivo");

			if(tipoConsulta.equals("")){
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> items = upload.parseRequest(request);
					for (FileItem item : items) {
						if (!item.isFormField()) {
							// Process form file field (input type="file").
							System.out.println("Field name: " + item.getFieldName());
							System.out.println("File name: " + item.getName());
							System.out.println("File size: " + item.getSize());
							System.out.println("File type: " + item.getContentType());

							String fileName = item.getName();
							try {
								File saveFile = new File("../eclipseApps/Cotizador/static/CotizacionesOffline",fileName);
								saveFile.createNewFile();
								item.write(saveFile);
								System.out.println("Current folder: " + saveFile.getCanonicalPath());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} catch (FileUploadException e) {
					try {
						throw new ServletException("Cannot parse multipart request.", e);
					} catch (ServletException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}   

			}

			if(tipoConsulta.equals("importar")){
				//Leo el contenido del archivo que viene en la ruta seleccionada en pantalla
				String textoJson="";
				try{
					String rutaPlantilla = this.getServletContext().getRealPath("") + "/static/CotizacionesOffline/"+nombreArchivo;
					textoJson = Files.toString(new File(rutaPlantilla), Charsets.UTF_8);
				}
				catch(IOException ex){
				}


				// decripto el mensaje
				if(textoJson!=null)
				{
					String decrypted="";
					try
					{
						decrypted = AES_Helper.decrypt(textoJson);
					}
					catch(Exception ex){
						result.put("estado", Boolean.FALSE);
						response.setContentType("application/json; charset=ISO-8859-1"); 
						result.put("mensaje", "El archivo no tiene el formato adecuado para poder descomprimir.");
						result.write(response.getWriter());
					}

					Gson g = new Gson();
					TransporteCotizaciones cotizaciones = g.fromJson(decrypted, TransporteCotizaciones.class);
					AgriObjetoCotizacionDAO objetoCotizacionDAO=new AgriObjetoCotizacionDAO();
					List<AgriObjetoCotizacion> listaCotizaciones;
					JSONArray cotizacionesJSONArray = new JSONArray();
					JSONObject cotizaJSONObject = new JSONObject();
					for(CotizacionAgricola cotizaActual:cotizaciones.getCotizacionAgricola()){
						listaCotizaciones=objetoCotizacionDAO.buscarPorObjetoOfflineId(cotizaActual.getObjetoCotizacionId());
						if(listaCotizaciones.size()==0){
							String cotizacionId=AgriProcesarCotizacionExterna.procesarCotizacion(cotizaActual,"COTIZADOR_OFFLINE");
							if(!cotizacionId.equals("")){
								cotizaJSONObject.put("objetoCotizacionID", cotizaActual.getObjetoCotizacionId());
								cotizaJSONObject.put("cotizacionID", cotizacionId);
								cotizaJSONObject.put("facturaID", "");
								cotizaJSONObject.put("comentario", "Cotizacion procesada con exito.");
								cotizacionesJSONArray.add(cotizaJSONObject);
							}
						}
						else{
							cotizaJSONObject.put("objetoCotizacionID", cotizaActual.getObjetoCotizacionId());
							cotizaJSONObject.put("cotizacionID", 0);
							cotizaJSONObject.put("facturaID", "");
							cotizaJSONObject.put("comentario", "La cotizacion ya fue procesada anteriormente");
							cotizacionesJSONArray.add(cotizaJSONObject);
						}
					}
					result.put("listadoCotizaciones", cotizacionesJSONArray);
					result.put("mensaje", "El archivo a sido procesado con exito.");
				}
			}
			result.put("estado", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1"); 
			//result.put("mensaje", "El archivo a sido procesado con exito.");
			result.write(response.getWriter());


		}catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("mensaje", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1");
			result.write(response.getWriter());
			e.printStackTrace();
		}
	}
}

