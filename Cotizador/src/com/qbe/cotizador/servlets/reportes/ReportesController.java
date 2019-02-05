package com.qbe.cotizador.servlets.reportes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import com.qbe.cotizador.util.Reportes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Color
 */
@WebServlet("/Jasper")
public class ReportesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet() 
     */
    public ReportesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * evaldez
	 * Servlet que recibe el pathReporte con la direccion del reporte que se desea generar (/static/reportes/prueba.jasper)
	 * y parametros es un objeto json ({"parametros":{"id":"1","prueba":"prueba"}})
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		String pathReporte = request.getParameter("pathReporte") == null ? "" : request.getParameter("pathReporte");
		String parametros=request.getParameter("parametros") == null ? "" : request.getParameter("parametros");
		
		JSONObject jsonObject = JSONObject.fromObject(parametros);
		
		String tipoConsulta=jsonObject.containsKey("tipoConsulta") ? jsonObject.getString("tipoConsulta") : "";
		
		
		File reportFile = new File(getServletConfig().getServletContext()
	        .getRealPath(pathReporte));
	    byte[] bytes = null;

	    try
	    {
	    	
	    	if(tipoConsulta.equals("reporteModificatoriosEnsurance")){
	    		
	    		String path="http://10.10.21.66:8080/ensurance/servlet/PostReportServlet?report="+URLEncoder.encode("file:C:/Tomcat 5.5.12/webapps/ensurance/WEB-INF/classes/com/tandi/emision/poliza/reportes/modificatorios/MODIFICATORIO_MASIVOS_VH_RAS.rpt", "UTF-8")+"&prompt0="+"7744157910008"+"&prompt1="+"7744157909932";
	    		
	    		InputStream is; 
	    		URL url = new URL(path);  
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setConnectTimeout(50000);
	    		conn.setReadTimeout(50000);
	    		conn.setInstanceFollowRedirects(true);
	    		conn.connect();
	    		
	    		is = conn.getInputStream();
				
				bytes = IOUtils.toByteArray(is);
				int tam=bytes.length;
	    		while(bytes.length<=1014){

	    		is = conn.getInputStream();
				
				bytes = IOUtils.toByteArray(is);
	    		}
	    	            
	    	}
	    	if(tipoConsulta.equals("")){
	    		Reportes reporte=new Reportes();
	    		bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), toMap(jsonObject), reporte.getConnection());
	    		
	    	}
	      response.setContentType("application/pdf");
	      response.setContentLength(bytes.length);

	      servletOutputStream.write(bytes, 0, bytes.length);
	      servletOutputStream.flush();
	      servletOutputStream.close();
	    }
	    catch (JRException e)
	    {
	    	JSONObject result = new JSONObject();
	    	result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			e.printStackTrace();
	    }
	}
	
	 public static Map<String, Object> toMap(JSONObject object) throws JSONException
	    {
	        Map<String, Object> map = new HashMap<String, Object>();

	        Iterator<String> keysItr = object.keys();
	        while(keysItr.hasNext())
	        {
	            String key = keysItr.next();
	            Object value = object.get(key);

	            if(value instanceof JSONArray)
	            {
	                value = toList((JSONArray) value);
	            }

	            else if(value instanceof JSONObject)
	            {
	                value = toMap((JSONObject) value);
	            }
	            map.put(key, value);
	        }
	       
	        map.put(JRParameter.REPORT_LOCALE,new Locale("ES"));//se agrega el idioma del reporte a los parametros
	        return map;
	    }

	    public static List toList(JSONArray array) throws JSONException
	    {
	        List<Object> list = new ArrayList<Object>();
	        for(int i = 0; i < array.size() ; i++)
	        {
	            list.add(array.get(i));
	        }
	        return list;
	    }

}
