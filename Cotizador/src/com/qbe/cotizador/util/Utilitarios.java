package com.qbe.cotizador.util;

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.json.JSONObject;

//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.qbe.cotizador.dao.cotizacion.CotizacionCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.producto.vehiculo.MarcaDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ModeloDAO;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionCobertura;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Marca;
import com.qbe.cotizador.model.Modelo;
import com.qbe.cotizador.model.ObjetoVehiculo;
import com.qbe.cotizador.model.TipoObjeto;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Utilitarios {
	    
    // Variables envio de mail
    public static String usuarioMail    = "cotizador@qbe.com.ec";
    public static String miCorreo       = "cotizador@qbe.com.ec";
    public static String miContrasena   = "Passw0rd02%";
    public static String servidorSMTP   = "smtp.office365.com";
    public static String puertoEnvio    = "587";
	
	public static void subirXMLFTP(String cotizacionId,String xml) {
		
		// Variables FTP
		//FTPClient client = new FTPClient();
	
		// Variables XML
		DocumentBuilderFactory factory;
	    DocumentBuilder builder;
	    Document doc;
	    TransformerFactory tFactory;
	    Transformer transformer;
	    
	    String nombreArchivo = cotizacionId+".xml";
		try {
			factory 			= DocumentBuilderFactory.newInstance();
			builder 			= factory.newDocumentBuilder();
			doc 				= builder.parse(new InputSource(new StringReader(xml)));
			// Usamos un transformador para la salida del archivo xml generado
			tFactory 			= TransformerFactory.newInstance();
		    transformer 		= tFactory.newTransformer();
		    DOMSource source 	= new DOMSource(doc);		    
		    StreamResult result = new StreamResult(new File("/home/insis/"+nombreArchivo));
		    transformer.transform(source, result);
		    
		    
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {		
			e.printStackTrace();
		}
	    
}
	
	public static String generarEstructuraXMLVH(Cotizacion cotizacion, List<ObjetoVehiculo> vehiculos){

		StringBuilder facturaXML 			= new StringBuilder();
		facturaXML.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");

		facturaXML.append("<cotizaciones>");
		
//		// Listado de los vehiculos
//		for(ObjetoVehiculo vehiculo: vehiculos){
//			facturaXML.append("<cotizacion id=\"cotizacionid\">".replaceAll("cotizacionid", cotizacion.getId()));
//	  	    // Listado de parametros
//	  		facturaXML.append("<lista_parametro>");
//	  		
//	  		facturaXML.append("<parametros tipo=\"tipoCotizacion\" >".replaceAll("tipoCotizacion", "vehiculo"));
//	  		facturaXML.append("<parametro nombre=\"id\" valor=\"vehiculoid\"/>".replaceAll("vehiculoid", vehiculo.getId()));
//			
//	  		facturaXML.append("<parametro nombre=\"modelo\" valor=\"idmodelo\"/>".replaceAll("idmodelo", "13143"));
//			//facturaXML.append("<parametro nombre=\"modelo\" valor=\"idmodelo\"/>".replaceAll("idmodelo", vehiculo.getModelo().getId()));
//			
//			facturaXML.append("<parametro nombre=\"fabricacion\" valor=\"fabricacionid\"/>".replaceAll("fabricacionid", vehiculo.getAnioFabricacion()));
//			facturaXML.append("<parametro nombre=\"total\" valor=\"suma_asegurada\"/>".replaceAll("suma_asegurada", String.valueOf(vehiculo.getSumaAsegurada())));				
//			String valorRastreo ="0";
//			if (vehiculo.getDispositivoRastreo())
//				valorRastreo ="1";					
//			facturaXML.append("<parametro nombre=\"rastreo\" valor=\"rastreovalor\"/>".replaceAll("rastreovalor", valorRastreo));

			// Obtenemos las coberturas principales
//			CotizacionDetalleDAO cotizacionDetalleDAO= new CotizacionDetalleDAO();
//			TipoObjetoDAO tipoObjetoDAO = new TipoObjetoDAO();				
//			TipoObjeto tipoObjeto = tipoObjetoDAO.buscarPorNombre("Vehiculos Livianos");
//			String cotizacionDetalleId =cotizacionDetalleDAO.buscarCotizacionDetallePorObjetoId(vehiculo.getId());
//						
//			CotizacionDetalle cotizacionDetalle = cotizacionDetalleDAO.buscarPorId(cotizacionDetalleId);
//			CotizacionCoberturaDAO cotizacionCoberturaDAO = new CotizacionCoberturaDAO();
//			List<CotizacionCobertura> listado_cotizacion_cobertura =  cotizacionCoberturaDAO.buscarCotizacionCoberturaPorCotizacionDetalle(cotizacionDetalle, tipoObjeto.getId());
//			
//			String todoRiesgo = "0";
//			String responsabilidadCivil = "0";
//			String danoTotal = "0";
//			String montodeducibleTR ="";
//			String porcentajeSiniestroDeducibleTR ="";
//			String porcentajeAseguradoDeducibleTR ="";
//			
//			
//			for(int i = 0; i<listado_cotizacion_cobertura.size();i++){
//				CotizacionCobertura cotizacionCobertura = cotizacionCoberturaDAO.buscarPorId(listado_cotizacion_cobertura.get(i).getId());
//				
//				if(cotizacionCobertura.getCobertura().getNemotecnico().equalsIgnoreCase("TORI")){
//					todoRiesgo = "1";
//					montodeducibleTR =String.valueOf(cotizacionCobertura.getMontoFijo());
//					porcentajeSiniestroDeducibleTR = String.valueOf(cotizacionCobertura.getPorcentajeValorSiniestro());
//					porcentajeAseguradoDeducibleTR = String.valueOf(cotizacionCobertura.getPorcentajeSumaAsegurada());
//				}
//				
//				if(cotizacionCobertura.getCobertura().getNemotecnico().equalsIgnoreCase("RECI")){
//					responsabilidadCivil = "1";
//				}
//				
//				if(cotizacionCobertura.getCobertura().getNemotecnico().equalsIgnoreCase("DATO")){
//					danoTotal = "1";
//										
//				}
//								
//			}
//			
//			
//			if (todoRiesgo.equalsIgnoreCase("1")){				
//				facturaXML.append("<parametro nombre=\"montodeducible\" valor=\"suma_deducible\"/>".replaceAll("suma_deducible", montodeducibleTR));
//				facturaXML.append("<parametro nombre=\"siniestrodeducible\" valor=\"porcentaje_siniestro_deducible\"/>".replaceAll("porcentaje_siniestro_deducible", porcentajeSiniestroDeducibleTR));
//				facturaXML.append("<parametro nombre=\"aseguradadeducible\" valor=\"porcentaje_asegurado_deducible\"/>".replaceAll("porcentaje_asegurado_deducible", porcentajeAseguradoDeducibleTR));	
//			}
////			
////			if (danoTotal.equalsIgnoreCase("1") ){				
////				facturaXML.append("<parametro nombre=\"montodeducible\" valor=\"suma_deducible\"/>".replaceAll("suma_deducible", montodeducibleTR));
////				facturaXML.append("<parametro nombre=\"siniestrodeducible\" valor=\"porcentaje_siniestro_deducible\"/>".replaceAll("porcentaje_siniestro_deducible", porcentajeSiniestroDeducibleTR));
////				facturaXML.append("<parametro nombre=\"aseguradadeducible\" valor=\"porcentaje_asegurado_deducible\"/>".replaceAll("porcentaje_asegurado_deducible", porcentajeAseguradoDeducibleTR));	
////			}
//			
//						
//			
//			
//			facturaXML.append("<parametro nombre=\"comision\" valor=\"porcentaje_comision\"/>".replaceAll("porcentaje_comision", String.valueOf(vehiculo.getPorcentajeComision())));
//			facturaXML.append("<parametro nombre=\"sucursal\" valor=\"id_sucursal\"/>".replaceAll("id_sucursal", vehiculo.getSucursalId())); 
//			// nuevos campos
//			String ceroKilometros ="0";
//			if (vehiculo.getCeroKilometros())
//				ceroKilometros ="1";	
//			facturaXML.append("<parametro nombre=\"cerokilometros\" valor=\"kilometros_valor\"/>".replaceAll("kilometros_valor", ceroKilometros));
//			facturaXML.append("<parametro nombre=\"antiguedad\" valor=\"antiguedadvalor\"/>".replaceAll("antiguedadvalor", vehiculo.getAntiguedadVh()));
//			facturaXML.append("<parametro nombre=\"conductoredad\" valor=\"conductoredadvalor\"/>".replaceAll("conductoredadvalor", vehiculo.getConductorEdad()));
//			facturaXML.append("<parametro nombre=\"conductorgenero\" valor=\"conductorgenerovalor\"/>".replaceAll("conductorgenerovalor", vehiculo.getConductorGenero()));
//			facturaXML.append("<parametro nombre=\"conductorestadocivil\" valor=\"estadocivilvalor\"/>".replaceAll("estadocivilvalor", vehiculo.getConductorEstadoCivil()));
//			facturaXML.append("<parametro nombre=\"numerohijos\" valor=\"numero_hijos\"/>".replaceAll("numero_hijos", vehiculo.getNumeroHijos()));
//			facturaXML.append("<parametro nombre=\"aniossinsiniestros\" valor=\"aniossinsiniestrosvalor\"/>".replaceAll("aniossinsiniestrosvalor", vehiculo.getAnosSin_Siniestro()));
//			facturaXML.append("<parametro nombre=\"zona\" valor=\"zonavalor\"/>".replaceAll("zonavalor", vehiculo.getZona()));
//			String guardaGarage ="0";
//			if (vehiculo.getGuardaGarage())
//				guardaGarage ="1";	
//			facturaXML.append("<parametro nombre=\"guardagarage\" valor=\"garage_valor\"/>".replaceAll("garage_valor", guardaGarage));				
//			facturaXML.append("<parametro nombre=\"kmanio\" valor=\"kmxanio\"/>".replaceAll("kmxanio", vehiculo.getKilometrosRecorridos()));
//			facturaXML.append("<parametro nombre=\"gnc\" valor=\"gncvalue\"/>".replaceAll("gncvalue", vehiculo.getCombustible()));		
//			
//			// Validacion de los campos de coberturas
//			facturaXML.append("<parametro nombre=\"rc\" valor=\"rcValor\"/>".replaceAll("rcValor", responsabilidadCivil));			
//			facturaXML.append("<parametro nombre=\"todoriesgo\" valor=\"valortodoriesgo\"/>".replaceAll("valortodoriesgo", todoRiesgo));
//			facturaXML.append("<parametro nombre=\"robototal\" valor=\"valorrobototal\"/>".replaceAll("valorrobototal", String.valueOf("0")));
//			facturaXML.append("<parametro nombre=\"danototal\" valor=\"valordano\"/>".replaceAll("valordano", danoTotal));				
//				
//			facturaXML.append("</parametros>");		  	
//	  		facturaXML.append("</lista_parametro>");
//			facturaXML.append("</cotizacion>");			
//		}
		facturaXML.append("</cotizaciones>");
		
	
		return facturaXML.toString();
	}
	
	
	 public static void envioMail(String mailReceptor, String asunto,String cuerpo) {
	    
	        Properties props = new Properties();
	        props.put("mail.smtp.user", usuarioMail);
	        props.put("mail.smtp.host", servidorSMTP);
	        props.put("mail.smtp.port", puertoEnvio);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "false");
	        props.put("mail.store.protocol", "SMTP");
	        
	        javax.mail.Session session = javax.mail.Session.getInstance(props,
	      		  new javax.mail.Authenticator() {
	      			protected PasswordAuthentication getPasswordAuthentication() {
	      				return new PasswordAuthentication(usuarioMail, miContrasena);
	      			}
	      		  });

	        try {	        	 
	        	javax.mail.Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(miCorreo));
				message.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(mailReceptor));
				message.setSubject(asunto);
				message.setContent(cuerpo,"text/html");				
				javax.mail.Transport.send(message);
				System.out.println("Enviado a "+mailReceptor);
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}		
	    }


	 
	 public static String generarCodigoAleatorioPorLongitud(int longitud){
		 
		 String NUMEROS = "0123456789";
		 String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		 String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
		 String ESPECIALES = "!#$%^&*_=`~|/;:,.?�-�+@"; 
		 String clave = "";
		 String key = NUMEROS + MAYUSCULAS + MINUSCULAS; // si se desea agregar caracteres especiales poner en key ESPECIALES		 
		 for (int i = 0; i < longitud; i++) {
			 clave+=(key.charAt((int)(Math.random() * key.length())));
		 }
		 return clave; 
		 
	 }	 
	 
	 public static String encriptacionClave(String clave){		          
		try {
			
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md.reset();
	    md.update(clave.getBytes());
	    byte messageDigest[] = md.digest();
	    StringBuffer cadenaHexadecimal = new StringBuffer();
	    for (int i=0;i<messageDigest.length;i++) {
	    	cadenaHexadecimal.append(Integer.toHexString(0xFF & messageDigest[i]));
	    }              
	    clave = cadenaHexadecimal.toString();
	       
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}                             
         return clave;
	 }
	 
	 public static String numeroRandomico(int length){		          
			String retorno="";
			
			while(retorno.length()<length){
				
				 Random rand = new Random();
				 int randomNum = rand.nextInt(10);
				 retorno+=randomNum;
			}
	         return retorno;
		 }
	 
		
	 public static JSONObject cargarParametroWS(String xmltemp) {
			JSONObject vehiculoJSONObject = new JSONObject();
			String codigo = "";
			String anio = "";
			String chasis = "";
			String motor = "";
			String placas = "";
			String marcaId = "";
			String modeloId = "";
			String color = "";
			String dispositivo = "";
			String sucursal = "";
			String valor = "";
			String usoVehiculo = "";
			String tipoVehiculo = "";
			String agenteId = "";
			String entidadAgenteId = "";
			String vigencia = "";
			String tasa = "";
			String tipoEndoso = "";

			String textoSinSaltosDeLinea = xmltemp.replaceAll("[\t\n\r]", "");
			String xmlText = textoSinSaltosDeLinea.toString();
			xmlText = xmlText.replace("<![CDATA[", "");
			xmlText = xmlText.replace("]]>", "");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Use document builder factory
			DocumentBuilder builder;

			if (!xmlText.equals("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"))
				try {
					builder = factory.newDocumentBuilder();

					// Parse the document
					Reader reader = new CharArrayReader(xmlText.toCharArray());
					Document doc = builder
							.parse(new org.xml.sax.InputSource(reader));

					// Elementos del XML
					Node nodoUsuario = doc.getFirstChild();

					if (nodoUsuario.getNodeType() == Node.ELEMENT_NODE
							&& nodoUsuario.hasChildNodes()) {

						Element usuario = (Element) nodoUsuario;
						codigo = usuario.getElementsByTagName("codigo").item(0)
								.getChildNodes().item(0).getNodeValue();
						anio = usuario.getElementsByTagName("anio").item(0)
								.getChildNodes().item(0).getNodeValue();
						chasis = usuario.getElementsByTagName("chasis").item(0)
								.getChildNodes().item(0).getNodeValue();
						motor = usuario.getElementsByTagName("motor").item(0)
								.getChildNodes().item(0).getNodeValue();
						placas = usuario.getElementsByTagName("placas").item(0)
								.getChildNodes().item(0).getNodeValue();
						marcaId = usuario.getElementsByTagName("marca").item(0)
								.getChildNodes().item(0).getNodeValue();
						modeloId = usuario.getElementsByTagName("modelo").item(0)
								.getChildNodes().item(0).getNodeValue();
						color = usuario.getElementsByTagName("color").item(0)
								.getChildNodes().item(0).getNodeValue();
						dispositivo = usuario.getElementsByTagName("dispositivo")
								.item(0).getChildNodes().item(0).getNodeValue();
						sucursal = usuario.getElementsByTagName("sucursal").item(0)
								.getChildNodes().item(0).getNodeValue();
						valor = usuario.getElementsByTagName("valor").item(0)
								.getChildNodes().item(0).getNodeValue();
						usoVehiculo = usuario.getElementsByTagName("usoVehiculo")
								.item(0).getChildNodes().item(0).getNodeValue();
						tipoVehiculo = usuario.getElementsByTagName("tipoVehiculo")
								.item(0).getChildNodes().item(0).getNodeValue();
						agenteId = usuario.getElementsByTagName("agenteId").item(0)
								.getChildNodes().item(0).getNodeValue();
						entidadAgenteId = usuario
								.getElementsByTagName("entidadAgenteId").item(0)
								.getChildNodes().item(0).getNodeValue();
						vigencia = usuario.getElementsByTagName("vigencia").item(0)
								.getChildNodes().item(0).getNodeValue();
						tasa = usuario.getElementsByTagName("tasa").item(0)
								.getChildNodes().item(0).getNodeValue();
						tipoEndoso = usuario.getElementsByTagName("tipoEndoso").item(0)
								.getChildNodes().item(0).getNodeValue();

					}
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			ModeloDAO modeloDAO = new ModeloDAO();
			MarcaDAO marcaDAO = new MarcaDAO();
			Marca marca = marcaDAO.buscarPorCodigoEnsurance(marcaId);
			String nombreMarca = "";
			if (marca.getId() != null)
				nombreMarca = marca.getNombre();

			Modelo modelo = modeloDAO.buscarPorCodigoEnsurance(modeloId);
			String nombreModelo = "";
			if (modelo.getId() != null)
				nombreModelo = modelo.getNombre();

			vehiculoJSONObject.put("codigo", codigo);
			vehiculoJSONObject.put("anio", anio);
			vehiculoJSONObject.put("chasis", chasis);
			vehiculoJSONObject.put("motor", motor);
			vehiculoJSONObject.put("placas", placas);
			if(marca.getId()!=null)
				vehiculoJSONObject.put("marca", marca.getId());
			if(modelo.getId()!=null)
				vehiculoJSONObject.put("modelo", modelo.getId());
			vehiculoJSONObject.put("color", color);
			vehiculoJSONObject.put("dispositivo", dispositivo);
			vehiculoJSONObject.put("sucursal", sucursal);
			vehiculoJSONObject.put("valor", valor);
			vehiculoJSONObject.put("usoVehiculo", usoVehiculo);
			vehiculoJSONObject.put("tipoVehiculo", tipoVehiculo);
			vehiculoJSONObject.put("agenteId", agenteId);
			vehiculoJSONObject.put("entidadAgenteId", entidadAgenteId);
			vehiculoJSONObject.put("vigencia", vigencia);
			vehiculoJSONObject.put("tasa", tasa);
			vehiculoJSONObject.put("tipoEndoso", tipoEndoso);

			return vehiculoJSONObject;

	}
	 
	 // Metodo para agregar dias a una fecha timestamp
	 public static long agregarDiaFechaTimestamp(Timestamp date,int dias){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.DAY_OF_WEEK, dias); // agregamos los dias a la fecha timestamp ingresada		 
		 Timestamp ts = new Timestamp(cal.getTime().getTime());
		 return ts.getTime();
	 }

	 // Metodo para verificar si es ano bisiesto
	 public static boolean esAnoBisiesto(int year) {
		    assert year >= 1583; // not valid before this date.
		    return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}
	 
	 public static void envioMailPDFAdjunto(String mailReceptor, String asunto,String cuerpo,byte [] adjunto) {
		    
	        Properties props = new Properties();
	        props.put("mail.smtp.user", usuarioMail);
	        props.put("mail.smtp.host", servidorSMTP);
	        props.put("mail.smtp.port", puertoEnvio);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "false");
	        props.put("mail.store.protocol", "SMTP");
	        
	        javax.mail.Session session = javax.mail.Session.getInstance(props,
	      		  new javax.mail.Authenticator() {
	      			protected PasswordAuthentication getPasswordAuthentication() {
	      				return new PasswordAuthentication(usuarioMail, miContrasena);
	      			}
	      		  });

	        try {	        	 
	        	javax.mail.Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(miCorreo));
				message.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(mailReceptor));
				message.setSubject(asunto);
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				
				Multipart mp = new MimeMultipart();

		        MimeBodyPart htmlPart = new MimeBodyPart();
		        htmlPart.setContent(cuerpo, "text/html");
		        mp.addBodyPart(htmlPart);

		        MimeBodyPart attachment = new MimeBodyPart();
		        attachment.setFileName("CertificadoCotizacion.pdf");
		        attachment.setContent(adjunto, "application/pdf");
		        mp.addBodyPart(attachment);

		        message.setContent(mp);
		        
		        Transport.send(message);
		        System.out.println("enviado a "+mailReceptor);
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}		
	    }

	 public static boolean validarCedula(String cedula) {
	        int[] modulo9 = {2, 1, 2, 1, 2, 1, 2, 1, 2};
	        boolean valorRetorno = true;
	        BigDecimal verif = new BigDecimal(0);
	        if (cedula.length() != 10)
	            valorRetorno = false;
	        else {
	            for (int i = 0; i < 9; i++) {
	                BigDecimal temp = new BigDecimal(new BigDecimal(cedula.substring(i, (i + 1))).multiply(new BigDecimal(modulo9[i])).toString());
	                if (temp.doubleValue() > 9)
	                    temp = temp.subtract(new BigDecimal("9"));
	                verif = verif.add(temp);
	            }
	            if (verif.doubleValue() % 10 == 0)
	                if (Integer.parseInt(cedula.substring(9, 10)) == 0)
	                    valorRetorno = true;
	                else
	                    valorRetorno = false;
	            else if ((10 - (verif.doubleValue() % 10)) == Integer.parseInt(cedula.substring(9, 10)))
	                valorRetorno = true;
	            else
	                valorRetorno = false;
	        }
	        return valorRetorno;
	    }
	public static boolean validaRUC(String RUC) {
	        int[] modulo11 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	        boolean valorRetorno = true;
	        BigDecimal verif = new BigDecimal(0);
	        if (RUC.length() < 13)
	            valorRetorno = false;
	        else if (Integer.parseInt(RUC.substring(0, 2)) < 1 || Integer.parseInt(RUC.substring(0, 2)) > 22) {
	            valorRetorno = false;
	        } else {
	            if (Integer.parseInt(RUC.substring(2, 3)) < 0 ||
	                    (Integer.parseInt(RUC.substring(2, 3)) > 6 &&
	                            Integer.parseInt(RUC.substring(2, 3)) < 9)) {
	                valorRetorno = false;
	            } else {
	                if (Integer.parseInt(RUC.substring(2, 3)) == 9) { //sociedad privada o extranjeros
	                    if (!RUC.substring(10, 13).equals("001"))
	                        valorRetorno = false;
	                    else {
	                        modulo11[0] = 4;
	                        modulo11[1] = 3;
	                        modulo11[2] = 2;
	                        modulo11[3] = 7;
	                        modulo11[4] = 6;
	                        modulo11[5] = 5;
	                        modulo11[6] = 4;
	                        modulo11[7] = 3;
	                        modulo11[8] = 2;
	                        for (int i = 0; i < 9; i++) {
	                            verif = verif.add(new BigDecimal(RUC.substring(i, (i + 1))).multiply(new BigDecimal(modulo11[i])));
	                        }
	                        if (verif.doubleValue() % 11 == 0)
	                            if (Integer.parseInt(RUC.substring(9, 10)) == 0)
	                                valorRetorno = true;
	                            else
	                                valorRetorno = false;
	                        else if ((11 - (verif.doubleValue() % 11)) == Integer.parseInt(RUC.substring(9, 10)))
	                            valorRetorno = true;
	                        else
	                            valorRetorno = false;
	                    }
	                } else if (Integer.parseInt(RUC.substring(2, 3)) == 6) { //sociedad p�blicas
	                    if (!RUC.substring(10, 13).equals("001"))
	                        valorRetorno = false;
	                    else {
	                        modulo11[0] = 3;
	                        modulo11[1] = 2;
	                        modulo11[2] = 7;
	                        modulo11[3] = 6;
	                        modulo11[4] = 5;
	                        modulo11[5] = 4;
	                        modulo11[6] = 3;
	                        modulo11[7] = 2;
	                        for (int i = 0; i < 8; i++) {
	                            verif = verif.add(new BigDecimal(RUC.substring(i, (i + 1))).multiply(new BigDecimal(modulo11[i])));
	                        }
	                        if (verif.doubleValue() % 11 == 0)
	                            if (Integer.parseInt(RUC.substring(8, 9)) == 0)
	                                valorRetorno = true;
	                            else
	                                valorRetorno = false;
	                        else if ((11 - (verif.doubleValue() % 11)) == Integer.parseInt(RUC.substring(8, 9)))
	                            valorRetorno = true;
	                        else
	                            valorRetorno = false;
	                    }
	                } else if (Integer.parseInt(RUC.substring(2, 3)) < 6 && Integer.parseInt(RUC.substring(2, 3)) >= 0) { //personas naturales
	                    if (!RUC.substring(10, 13).equals("001"))
	                        valorRetorno = false;
	                    else {
	                        valorRetorno = validarCedula(RUC.substring(0, 10));
	                    }
	                }
	            }
	        }
	        return valorRetorno;
	   }
}