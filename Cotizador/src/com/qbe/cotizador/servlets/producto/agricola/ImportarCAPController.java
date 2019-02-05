package com.qbe.cotizador.servlets.producto.agricola;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.qbe.cotizador.dao.producto.agricola.AgriObjetoCotizacionDAO;
import com.qbe.cotizador.model.AgriObjetoCotizacion;
import com.qbe.cotizador.util.AES_Helper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class ImportarCAPController
 */
@WebServlet("/ImportarCAPController")
public class ImportarCAPController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportarCAPController() {
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
								File saveFile = new File("../eclipseApps/Cotizador/static/CotizacionesArchivoPlano",fileName);
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

				String rutaPlantilla = this.getServletContext().getRealPath("") + "/static/CotizacionesArchivoPlano/"+nombreArchivo;
				FileInputStream input = new FileInputStream(rutaPlantilla);
				List<CotizacionArchivoPlano> cotizacionList = new ArrayList<CotizacionArchivoPlano>();
				//String fileExt = GetFileExtension(rutaPlantilla);
				if(nombreArchivo.endsWith(".xlsx")){
					cotizacionList = ReadExelFile.readXLSXFile(rutaPlantilla);		
				}else if(nombreArchivo.endsWith(".xls")){
					cotizacionList = ReadExelFile.readXLSFile(rutaPlantilla);					 
				}
				for(CotizacionArchivoPlano cot: cotizacionList){ 
					System.out.println(cot.getCanal()+" - "+cot.getCliente()+" - "+cot.getClienteIdentificacion()+" - "+cot.getMontoAsegurado()+" - "+cot.getFechaSolicitud()+" - "+cot.getFechaSiembra()+" - "+cot.getTipoCultivoNombre()+" - "+cot.getNumeroHectareasAseguradas()+" - "+cot.getNumeroHectareasLote()+" - "+cot.getEsTecnificado()+" - "+cot.getProvinciaNombre()+" - "+cot.getCantonNombre()+" - "+cot.getParroquiaNombre()+" - "+cot.getUbicacionCultivo());
					System.out.println("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
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
