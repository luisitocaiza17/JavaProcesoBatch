package com.qbe.cotizador.servlets.producto.agricola;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.EndosoBeneficiarioDAO;
import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.cotizacion.UplaDAO;
import com.qbe.cotizador.dao.entidad.AgenteDAO;
import com.qbe.cotizador.dao.entidad.ClienteDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriCicloDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriObjetoCotizacionDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriParametroXPuntoVentaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriReglaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoDAO;
import com.qbe.cotizador.dao.seguridad.TipoVariableDAO;
import com.qbe.cotizador.dao.seguridad.VariableSistemaDAO;
import com.qbe.cotizador.model.Agente;
import com.qbe.cotizador.model.AgriCiclo;
import com.qbe.cotizador.model.AgriObjetoCotizacion;
import com.qbe.cotizador.model.AgriParametroXPuntoVenta;
import com.qbe.cotizador.model.AgriRegla;
import com.qbe.cotizador.model.AgriTipoCultivo;
import com.qbe.cotizador.model.Cliente;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Direccion;
import com.qbe.cotizador.model.EndosoBeneficiario;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Pago;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.TipoVariable;
import com.qbe.cotizador.model.Upla;
import com.qbe.cotizador.model.VariableSistema;
import com.qbe.cotizador.transaction.cotizacion.CotizacionDetalleTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.producto.agricola.AgriObjetoCotizacionTransaction;
//Import del servicio
import com.tandi.servicios.DTOs.ClienteDTO;
import com.tandi.servicios.DTOs.ConfiguracionPagoDTO;
import com.tandi.servicios.DTOs.DireccionDTO;
import com.tandi.servicios.DTOs.PagoDTO;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ObjetoGanaderoController
 */
@WebServlet("/AgriObjetoCotizacionController")
public class AgriObjetoCotizacionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgriObjetoCotizacionController() {
		super();
		//TODO Auto-generated constructor stub
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

			CotizacionDAO cotizacionDAO=new CotizacionDAO();
			CotizacionDetalleDAO cotizacionDetalleDAO=new CotizacionDetalleDAO();
			ClienteDAO clienteDAO=new ClienteDAO();
			TipoObjetoDAO tipoObjetoDAO=new TipoObjetoDAO();
			AgriObjetoCotizacionDAO agriObjetoDAO=new AgriObjetoCotizacionDAO();
			AgriReglaDAO agriReglaDAO=new AgriReglaDAO();

			CotizacionTransaction cotizacionTransaction= new CotizacionTransaction();
			AgriObjetoCotizacionTransaction agriObjetoCotizacionTransaction = new AgriObjetoCotizacionTransaction();
			CotizacionDetalleTransaction cotizacionDetalleTransaction= new CotizacionDetalleTransaction();			

			if(tipoConsulta.equalsIgnoreCase("crear"))
			{
				String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId").trim();
				String provinciaId = request.getParameter("provinciaId") == null ? "" : request.getParameter("provinciaId");
				String cantonId = request.getParameter("cantonId") == null ? "" : request.getParameter("cantonId");
				String parroquiaId = request.getParameter("parroquiaId") == null ? "" : request.getParameter("parroquiaId");
				String direccionSiembra = request.getParameter("direccionSiembra") == null ? "" : request.getParameter("direccionSiembra").trim();
				String altitudNivelMar = request.getParameter("altitudNivelMar") == null ? "" : request.getParameter("altitudNivelMar").trim();
				String fechaCredito = request.getParameter("fechaCredito") == null ? "" : request.getParameter("fechaCredito").trim();
				String montoCredito = request.getParameter("montoCredito") == null ? "" : request.getParameter("montoCredito").trim();
				String tipoCultivoId = request.getParameter("tipoCultivoId") == null ? "" : request.getParameter("tipoCultivoId").trim();
				String variedad = request.getParameter("variedad") == null ? "" : request.getParameter("variedad").trim();
				String fechaSiembra = request.getParameter("fechaSiembra") == null ? "" : request.getParameter("fechaSiembra").trim();
				String hectareasLote = request.getParameter("hectareasLote") == null ? "" : request.getParameter("hectareasLote").trim();
				String hectareasAsegurables = request.getParameter("hectareasAsegurables") == null ? "" : request.getParameter("hectareasAsegurables").trim();
				String agricultorTecnificado = request.getParameter("agricultorTecnificado") == null ? "" : request.getParameter("agricultorTecnificado").trim();
				String latitud = request.getParameter("latitud") == null ? "" : request.getParameter("latitud").trim();
				String longitud = request.getParameter("longitud") == null ? "" : request.getParameter("longitud").trim();
				String disponeRiego = request.getParameter("disponeRiego") == null ? "" : request.getParameter("disponeRiego").trim();
				String disponeAsistencia = request.getParameter("disponeAsistencia") == null ? "" : request.getParameter("disponeAsistencia").trim();
				String tipoSeguro = request.getParameter("tipoSeguro") == null ? "" : request.getParameter("tipoSeguro").trim();
				String sucursalCanalId = request.getParameter("sucursalCanalId") == null ? "" : request.getParameter("sucursalCanalId").trim();
				String aniosCultivo = request.getParameter("aniosCultivo") == null ? "" : request.getParameter("aniosCultivo").trim();

				Date dateCredito=null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(fechaCredito!="")
				{
					dateCredito=formatter.parse(fechaCredito);
				}

				Date dateSiembra=null;
				if(fechaSiembra!=""){
					dateSiembra=formatter.parse(fechaSiembra);
				}

				if(Integer.parseInt(tipoSeguro)==0){
					Calendar c = Calendar.getInstance();    
					c.setTime(new Date());
					c.add(Calendar.DATE, -30);
					Date dateMenor=c.getTime();
					
					Calendar cM = Calendar.getInstance();    
					cM.setTime(new Date());
					cM.add(Calendar.DATE, 30);
					Date dateMayor=cM.getTime();
	
					if(!(dateSiembra.after(dateMenor) && dateSiembra.before(dateMayor)))
						throw new Exception("No se puede realizar la cotización. Porque la fecha de siembra debe estar entre 30 días más y 30 días menos de la fecha actual.");
				}

				//Valido si existe la cotizacion
				Cotizacion objetoCotizacion=cotizacionDAO.buscarPorId(cotizacionId);
				if(objetoCotizacion!=null)
				{
					//
					Double sumaAseguradaTotal=0.0;
					Double primaNetaTotal=0.0;

					//Obtengo el valor de la tabla de reglas
					List<AgriRegla> reglas= agriReglaDAO.BuscarPorParametros(new BigInteger(provinciaId), new BigInteger(cantonId), new BigInteger(tipoCultivoId));
					if(reglas.size()!=0)
					{
						Boolean valido=false;
						for(AgriRegla regla:reglas){
							if(regla.getClicloId()!=null){
								AgriCicloDAO cicloDAO=new AgriCicloDAO();
								AgriCiclo ciclo = cicloDAO.BuscarPorId(regla.getClicloId());
								Date fechafin=ciclo.getFechaFin();
								Date fechaInicio=ciclo.getFechaInicio();
								if(dateSiembra.after(fechaInicio) && dateSiembra.before(fechafin))
									valido=true;
							}
							else {
								if(dateSiembra.after(regla.getAceptabilidadDesde()) && dateSiembra.before(regla.getAceptabilidadHasta()))
									valido=true;
							}
						}
						if(valido){
							if(Integer.parseInt(tipoSeguro)==0){
								sumaAseguradaTotal=reglas.get(0).getCostoProduccion()*Double.parseDouble(hectareasAsegurables);
								primaNetaTotal=sumaAseguradaTotal*reglas.get(0).getTasa()/100;
							}
							else if(Integer.parseInt(tipoSeguro)==1){
								//long diffMSec = (new Date()).getTime()-dateSiembra.getTime();
								//long diff = (long)60 * (long)60 * (long)1000 * (long)24 * (long)365;
								//long diffYears = (long) (diffMSec / diff);
								Double costoMantenimiento = reglas.get(0).getCostoMantenimiento() * new Double(aniosCultivo) * Double.parseDouble(hectareasAsegurables);
								sumaAseguradaTotal=reglas.get(0).getCostoProduccion() * Double.parseDouble(hectareasAsegurables) + costoMantenimiento;
								primaNetaTotal=sumaAseguradaTotal*reglas.get(0).getTasa()/100;
							}
							else{
								Double costoMantenimiento = reglas.get(0).getCostoMantenimiento() * new Double(1) * Double.parseDouble(hectareasAsegurables);
								sumaAseguradaTotal=costoMantenimiento;
								primaNetaTotal=sumaAseguradaTotal*reglas.get(0).getTasa()/100;
							}

						}
						else{
							throw new Exception("No se puede realizar la cotización. Porque la fecha de siembra no esta permitida en ningun ciclo.");
						}

					}
					else{
						throw new Exception("No se puede realizar la cotización. Porque la provincia y ciudad seleccionadas no permite ese tipo de cultivo.");
					}


					//Cuento si ya hay al menos un detalle para la cotizacion
					List<CotizacionDetalle> listadoDetalles=cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(objetoCotizacion);
					if(listadoDetalles.size()==0)
					{
						//Inserta el detalle de la cotización
						AgriObjetoCotizacion agriObjetoCotizacion=new AgriObjetoCotizacion();
						agriObjetoCotizacion.setProvinciaId(new BigInteger(provinciaId));
						agriObjetoCotizacion.setCantonId(new BigInteger(cantonId));
						agriObjetoCotizacion.setParroquiaId(new BigInteger(parroquiaId));
						agriObjetoCotizacion.setTipoCultivoId(new BigInteger(tipoCultivoId));
						agriObjetoCotizacion.setVariedad(variedad);
						agriObjetoCotizacion.setAgricultorTecnificado(Boolean.parseBoolean(agricultorTecnificado));
						agriObjetoCotizacion.setDisponeRiego(Boolean.parseBoolean(disponeRiego));
						agriObjetoCotizacion.setDisponeAsistencia(Boolean.parseBoolean(disponeAsistencia));
						agriObjetoCotizacion.setAgricultorTecnificado(Boolean.parseBoolean(agricultorTecnificado));
						agriObjetoCotizacion.setDireccionSiembra(direccionSiembra);
						if(aniosCultivo!="")
							agriObjetoCotizacion.setAniosCultivo(Integer.parseInt(aniosCultivo));
						if(tipoSeguro!="")
							agriObjetoCotizacion.setTipoSeguro(Integer.parseInt(tipoSeguro));
						else
							agriObjetoCotizacion.setTipoSeguro(0);
						if(hectareasLote!="")
							agriObjetoCotizacion.setHectareasLote(Float.parseFloat(hectareasLote));
						if(latitud!="")
							agriObjetoCotizacion.setLatitud(Float.parseFloat(latitud));
						if(longitud!="")
							agriObjetoCotizacion.setLongitud(Float.parseFloat(longitud));
						if(hectareasAsegurables!="")
							agriObjetoCotizacion.setHectareasAsegurables(Float.parseFloat(hectareasAsegurables));
						if(montoCredito!="")
							agriObjetoCotizacion.setMontoCredito(Float.parseFloat(montoCredito));
						if(sucursalCanalId!="")
							agriObjetoCotizacion.setSucursalCanalId(new BigInteger(sucursalCanalId));

						if(dateCredito!=null)
							agriObjetoCotizacion.setFechaCredito(dateCredito);
						if(dateSiembra!=null)
							agriObjetoCotizacion.setFechaSiembra(dateSiembra);
						if(altitudNivelMar!="")
							agriObjetoCotizacion.setAltitudNivelMar(Float.parseFloat(altitudNivelMar));

						agriObjetoCotizacion.setTipoOrigen("COTIZADOR_ONLINE");
						
						agriObjetoCotizacion=agriObjetoCotizacionTransaction.crear(agriObjetoCotizacion);

						//Creo la cotización detalle
						CotizacionDetalle nuevoCotizacionDetalle=new CotizacionDetalle();
						nuevoCotizacionDetalle.setCotizacion(objetoCotizacion);
						nuevoCotizacionDetalle.setNecesitaInspeccion(false);
						nuevoCotizacionDetalle.setTipoObjetoId(tipoObjetoDAO.buscarPorNombre("Agricola").getId());
						nuevoCotizacionDetalle.setObjetoId(agriObjetoCotizacion.getObjetoCotizacionId().toString());
						nuevoCotizacionDetalle.setSumaAseguradaItem(sumaAseguradaTotal);
						nuevoCotizacionDetalle.setPrimaNetaItem(primaNetaTotal);
						cotizacionDetalleTransaction.crear(nuevoCotizacionDetalle);

						result.put("cotizacionId",objetoCotizacion.getId());
						result.put("objetoGanaderoId",agriObjetoCotizacion.getObjetoCotizacionId());
					}
					else
					{
						//Recupero el objeto detalle para actualizar
						CotizacionDetalle nuevoCotizacionDetalle=listadoDetalles.get(0);

						AgriObjetoCotizacion agriObjetoCotizacion;

						//Valido si existe el objeto agricol para ese datalle, si existe lo recupero 
						//caso contrario me toca crearlo

						if(nuevoCotizacionDetalle.getObjetoId().equals(""))
							agriObjetoCotizacion=new AgriObjetoCotizacion();
						else
							agriObjetoCotizacion=agriObjetoDAO.buscarPorId(new BigInteger(nuevoCotizacionDetalle.getObjetoId()));

						//Inserta el detalle de la cotización

						agriObjetoCotizacion.setProvinciaId(new BigInteger(provinciaId));
						agriObjetoCotizacion.setCantonId(new BigInteger(cantonId));
						agriObjetoCotizacion.setParroquiaId(new BigInteger(parroquiaId));
						agriObjetoCotizacion.setTipoCultivoId(new BigInteger(tipoCultivoId));
						agriObjetoCotizacion.setVariedad(variedad);
						agriObjetoCotizacion.setAgricultorTecnificado(Boolean.parseBoolean(agricultorTecnificado));
						agriObjetoCotizacion.setDisponeRiego(Boolean.parseBoolean(disponeRiego));
						agriObjetoCotizacion.setDisponeAsistencia(Boolean.parseBoolean(disponeAsistencia));
						agriObjetoCotizacion.setDireccionSiembra(direccionSiembra);
						if(aniosCultivo!="")
							agriObjetoCotizacion.setAniosCultivo(Integer.parseInt(aniosCultivo));
						if(tipoSeguro!="")
							agriObjetoCotizacion.setTipoSeguro(Integer.parseInt(tipoSeguro));
						else
							agriObjetoCotizacion.setTipoSeguro(0);
						if(hectareasLote!="")
							agriObjetoCotizacion.setHectareasLote(Float.parseFloat(hectareasLote));
						if(hectareasAsegurables!="")
							agriObjetoCotizacion.setHectareasAsegurables(Float.parseFloat(hectareasAsegurables));
						if(montoCredito!="")
							agriObjetoCotizacion.setMontoCredito(Float.parseFloat(montoCredito));
						if(latitud!="")
							agriObjetoCotizacion.setLatitud(Float.parseFloat(latitud));
						if(longitud!="")
							agriObjetoCotizacion.setLongitud(Float.parseFloat(longitud));
						if(!sucursalCanalId.equals(""))
							agriObjetoCotizacion.setSucursalCanalId(new BigInteger(sucursalCanalId));
						if(dateCredito!=null)
						{
							agriObjetoCotizacion.setFechaCredito(dateCredito);
						}
						if(fechaSiembra!=null)
						{
							agriObjetoCotizacion.setFechaSiembra(dateSiembra);
						}
						agriObjetoCotizacion.setAltitudNivelMar(Float.parseFloat(altitudNivelMar));
						
						agriObjetoCotizacion.setTipoOrigen("COTIZADOR_ONLINE");

						agriObjetoCotizacion=agriObjetoCotizacionTransaction.editar(agriObjetoCotizacion);

						//Cambio los valores de la poliza
						nuevoCotizacionDetalle.setSumaAseguradaItem(sumaAseguradaTotal);
						nuevoCotizacionDetalle.setPrimaNetaItem(primaNetaTotal);
						cotizacionDetalleTransaction.editar(nuevoCotizacionDetalle);


						result.put("cotizacionId",objetoCotizacion.getId());
						result.put("objetoGanaderoId",agriObjetoCotizacion.getObjetoCotizacionId());
					}
					objetoCotizacion.setSumaAseguradaTotal(sumaAseguradaTotal);
					objetoCotizacion.setPrimaNetaTotal(primaNetaTotal.toString());
					objetoCotizacion.setEtapaWizard(2);
					cotizacionTransaction.editar(objetoCotizacion);
				}
			}

			if(tipoConsulta.equalsIgnoreCase("obtenerValoresCotizacion"))
			{
				VariableSistemaDAO variableDAO = new VariableSistemaDAO();

				Cotizacion cotizacion = cotizacionDAO.buscarPorId(request.getParameter("cotizacionId"));
				List<CotizacionDetalle> cotizacionesDetalle = cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);								

				double valorPrima = 0;
				double valorAsegurado = 0;
				double valorFinalPrima = 0;
				if(cotizacionesDetalle.size()!=0)
				{
					//Calculo los valores
					for(CotizacionDetalle cotizacionDetalle : cotizacionesDetalle)
					{			
						valorPrima = valorPrima + cotizacionDetalle.getPrimaNetaItem();
						valorAsegurado = valorAsegurado+ cotizacionDetalle.getSumaAseguradaItem();
						valorPrima = Math.rint(valorPrima*100)/100;
						valorAsegurado = Math.rint(valorAsegurado*100)/100;
					}
					valorFinalPrima = valorPrima;
					result.put("porcentajeDescuento", 0.0);  
					TipoVariableDAO tipoVariableDao = new TipoVariableDAO();
					TipoVariable tipoVariable = tipoVariableDao.buscarPorId("3");
					List<VariableSistema> variablesistema = variableDAO.buscarTipoVariable(tipoVariable);
					double valorBase = 0;
					double valorDerechosEmision = 0;
					double valorSeguroCampesino = 0;
					double valorSuperBancos = 0;
					double valorIva= 0;
					double valorSubTotal = 0;
					double valorTotal = 0;
					result.put("valorPrima", valorFinalPrima);
					result.put("valorAsegurado", valorAsegurado);
					if(variablesistema.size() > 0) {
						for(VariableSistema variable : variablesistema) {
							if(variable.getNombre().equals("DERECHOS_EMISION")){
								valorBase = Double.parseDouble(variable.getValor())+ valorFinalPrima;
								valorDerechosEmision = Double.parseDouble(variable.getValor());
								cotizacion.setImpDerechoEmision(valorDerechosEmision);
								result.put("valorDerechosEmision", valorDerechosEmision);

							}
							else if(variable.getNombre().equals("SEGURO_CAMPESINO")){
								valorSeguroCampesino = Math.rint(Double.parseDouble(variable.getValor())*valorFinalPrima/100*100)/100;
								valorBase = valorBase + valorSeguroCampesino;
								cotizacion.setImpSeguroCampesino(valorSeguroCampesino);
								result.put("valorSeguroCampesino", valorSeguroCampesino);
							}
							else if(variable.getNombre().equals("SUPER_DE_BANCOS")){
								valorSuperBancos = Math.rint(Double.parseDouble(variable.getValor())*valorFinalPrima/100*100)/100;
								result.put("valorSuperBancos", valorSuperBancos);
								cotizacion.setImpSuperBancos(valorSuperBancos);
								valorBase = valorBase + valorSuperBancos;

							}

							else if(variable.getNombre().equals("SUBTOTAL")){
								valorSubTotal = Math.rint(valorBase*100)/100;
								result.put("valorSubTotal", valorSubTotal);
							}
							else if(variable.getNombre().equals("IVA")){
								valorIva = Math.rint(Double.parseDouble(variable.getValor())*valorSubTotal/100*100)/100;
								cotizacion.setImpIva(valorIva);
								result.put("valorIva", valorIva);
							}

						}
						valorTotal = Math.rint((valorBase+valorIva)*100)/100;
						cotizacion.setValorDescuento(0);
						cotizacion.setTotalFactura(valorTotal);
						result.put("valorTotal", valorTotal);
						cotizacionTransaction.editar(cotizacion);
					}
				}
				//
			}
			
			if(tipoConsulta.equalsIgnoreCase("registrarInspeccion"))
			{
				EstadoDAO estadoDAO=new EstadoDAO();
				Cotizacion cotizacion = cotizacionDAO.buscarPorId(request.getParameter("cotizacionId"));
				//verifico si todas las direcciones estan aprobadas
				List<CotizacionDetalle> listadoDetalles=cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);
				cotizacion.setEstado(estadoDAO.buscarPorId(request.getParameter("estadoInspeccion")));
				cotizacion.setEtapaWizard(3);
				Date fechaActual=new Date();
				cotizacion.setVigenciaDesde(fechaActual);
				cotizacionTransaction.editar(cotizacion);
			}

			if(tipoConsulta.equalsIgnoreCase("emitirPoliza"))
			{
				String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId").trim();
				String fechaInicioVigencia = request.getParameter("fechaInicioVigencia") == null ? "" : request.getParameter("fechaInicioVigencia").trim();

				Cotizacion cotizacion=cotizacionDAO.buscarPorId(cotizacionId);
				String xmlCotizacion=generarXML(cotizacion, fechaInicioVigencia);
				com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaProxy emisionAgricola=new com.qbe.cotizador.servicios.QBE.emisionAgricolaWS.WSEmisionAgricolaProxy();
				
				String resultado=emisionAgricola.emisionPoliza(xmlCotizacion, "f2rtiUdv2kjOgaCx");
				result.put("result", resultado);
				
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

	private String generarXML(Cotizacion cotizacion, String fechaInicioVigencia){
		ClienteDAO clienteDAO=new ClienteDAO();
		AgenteDAO agenteDAO=new AgenteDAO();
		AgriParametroXPuntoVentaDAO parametroDAO=new AgriParametroXPuntoVentaDAO();
		PuntoVentaDAO puntoVentaDAO=new PuntoVentaDAO();
		ProductoXPuntoVentaDAO productoXPVDAO=new ProductoXPuntoVentaDAO();
		AgriObjetoCotizacionDAO objetoCotizacionDAO=new AgriObjetoCotizacionDAO();
		AgriTipoCultivoDAO tipoCultivoDAO=new AgriTipoCultivoDAO();
		EntidadDAO entidadDAO=new EntidadDAO();
		EndosoBeneficiarioDAO endosoBeneficiarioDAO=new EndosoBeneficiarioDAO();
		
		Agente agente=agenteDAO.buscarPorId(cotizacion.getAgenteId().toString());
		AgriParametroXPuntoVenta parametroXPV=parametroDAO.buscarPorPuntoVentaId(new BigInteger(cotizacion.getPuntoVenta().getId()));
		PuntoVenta puntoVenta=puntoVentaDAO.buscarPorId(cotizacion.getPuntoVenta().getId());
		ProductoXPuntoVenta productoXPV=productoXPVDAO.buscarPorId(cotizacion.getProductoXPuntoVentaId().toString());
		AgriObjetoCotizacion objetoCotizacion=objetoCotizacionDAO.buscarPorId(new BigInteger(cotizacion.getCotizacionDetalles().get(0).getObjetoId()));
		AgriTipoCultivo tipoCultivo=tipoCultivoDAO.BuscarPorId(objetoCotizacion.getTipoCultivoId());
		Cliente cliente=clienteDAO.buscarPorId(cotizacion.getClienteId().toString());
		EndosoBeneficiario endosoBeneficiario= endosoBeneficiarioDAO.buscarPorCotizacion(cotizacion);
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(objetoCotizacion.getFechaSiembra()); 
		c.add(Calendar.DATE, tipoCultivo.getVigenciaDias());
		Date vigenciaHasta = c.getTime();
		/*Date fechaAprobacion=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fechaAprobacion=dateFormat.parse(fechaInicioVigencia);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		StringBuilder xml=new StringBuilder("<superObjetoXML><detallesPoliza><identificador>100885</identificador>")
				.append("<puerto>8084</puerto>")
				.append("<login>dchaguaro</login>")
				.append("<pass>pruebas</pass>")
				.append("<agenteId>").append(agente.getAgeEnsurance()).append("</agenteId>")
				.append("<porcentajeComision>").append(cotizacion.getPorcentajeComision()).append("</porcentajeComision>")
				.append("<valorprima>").append(cotizacion.getPrimaNetaTotal()).append("</valorprima>")
				.append("<canalId>26346</canalId>")
				.append("<contenedorId>").append(parametroXPV.getContenedorEnsuranceId()).append("</contenedorId>")
				.append("<loteImpresion>LOTE Credifé_2015-07-02</loteImpresion>")
				.append("<puntoVentaId>")
				.append(puntoVenta.getPtoEnsurance())
				.append("</puntoVentaId>")
				.append("<tipoRiesgoId>8126480</tipoRiesgoId>")
				.append("<claseRiesgoId>8060943</claseRiesgoId>")
				.append("<tipoItemId>102</tipoItemId>")
				.append("<monedaId>11141120</monedaId>")
				.append("<plantillaId>").append(parametroXPV.getPlantillaEnsuranceId()).append("</plantillaId>")
				.append("<firmaDigitalId>1</firmaDigitalId>")
				.append("<usuarioActualiza>5829986617978</usuarioActualiza>")
				.append("<ramoId>7274513</ramoId>")
				.append("<mnemotecnicoRamo>SA</mnemotecnicoRamo>")
				.append("<unidadNegocioId>").append(productoXPV.getUnidadNegocio().getUnEnsurance()).append("</unidadNegocioId>")
				.append("<unidadProduccionId>").append(productoXPV.getUnidadProduccion().getUpEnsurance()).append("</unidadProduccionId>")
				.append("<numeroPoliza>100885</numeroPoliza>")
				.append("<codigoEntidadFinanciera>26346</codigoEntidadFinanciera>")
				.append("<fechaAprobacion>").append("25/06/2015").append("</fechaAprobacion>")
				//.append("<fechaAprobacion>").append(dateFormat.format(fechaAprobacion)).append("</fechaAprobacion>")
				.append("<codigoIntegracion>998000008</codigoIntegracion>")
				.append("<vigenciaDesde>1435208400</vigenciaDesde>")
				.append("<valorAsegurado>").append(cotizacion.getSumaAseguradaTotal()).append("</valorAsegurado>")
				.append("<vigenciaHasta>1445576400</vigenciaHasta>")
				.append("<sucursalId>87</sucursalId>")
				.append("</detallesPoliza>")
				.append("<loteCultivo>")
				.append("<loteCultivoId>-1</loteCultivoId>")
				.append("<nombre>LOTE CREDIFE_2015-07-02</nombre>")
				.append("<valorAsegurado>").append(cotizacion.getSumaAseguradaTotal()).append("</valorAsegurado>")
				.append("<numeroHectareas>").append(objetoCotizacion.getHectareasLote()).append("</numeroHectareas>")
				.append("<valorPorHectarea>").append(objetoCotizacion.getCostoProduccion()).append("</valorPorHectarea>")
				.append("<gpsLoteX>0</gpsLoteX>")
				.append("<gpsLoteY>0</gpsLoteY>")
				.append("<tasa>").append(cotizacion.getTasaProductoValor()).append("</tasa>")
				.append("<tipoCultivoId>").append(tipoCultivo.getCodEnsurance()).append("</tipoCultivoId>")
				.append("<diasVigencia>").append(tipoCultivo.getVigenciaDias()).append("</diasVigencia>")
				//.append("<inicioVigenciaCultivo>").append(objetoCotizacion.getFechaSiembra()).append("</inicioVigenciaCultivo>")
				//.append("<finVigenciaCultivo>").append(vigenciaHasta).append("</finVigenciaCultivo>")
				.append("<inicioVigenciaCultivo>").append("25/06/2015").append("</inicioVigenciaCultivo>")
				.append("<finVigenciaCultivo>").append("23/10/2015").append("</finVigenciaCultivo>")
				.append("<variedad>DE VERANO</variedad>")
				.append("<numeroHectareasAsegurables>").append(objetoCotizacion.getHectareasAsegurables()).append("</numeroHectareasAsegurables>")
				.append("<esTecnificado>Si</esTecnificado>")
				.append("<fechaTentativaSiembra>").append("25/06/2015").append("</fechaTentativaSiembra>")
				//.append("<fechaTentativaSiembra>").append(objetoCotizacion.getFechaSiembra()).append("</fechaTentativaSiembra>")
				.append("<propiedadId>-1</propiedadId>")
				.append("</loteCultivo>")
				
				.append("<cliente>")
				.append("<id>no</id>")
				.append("<entidadId>").append(cliente.getEntidad().getEntEnsurance()).append("</entidadId>")
				.append("<nombres>").append(cliente.getEntidad().getNombres()).append("</nombres>")
				.append("<apellidos>").append(cliente.getEntidad().getApellidos()).append("</apellidos>")
				.append("<tipoIdentificacion>")
				.append(cliente.getEntidad().getTipoIdentificacion().getId()=="1" ? "c":cliente.getEntidad().getTipoIdentificacion().getId()=="2" ? "p":"r")
				.append("</tipoIdentificacion>")
				.append("<tipoEntidadId>").append(cliente.getEntidad().getTipoEntidad().getId()).append("</tipoEntidadId>")
				.append("<identificacion>").append(cliente.getEntidad().getIdentificacion()).append("</identificacion>")
				.append("<genero>f</genero>")
				.append("<esEmpresa>")
				.append(cliente.getEntidad().getTipoIdentificacion().getId()=="1" ? "false":cliente.getEntidad().getTipoIdentificacion().getId()=="2" ? "false":"true")
				.append("</esEmpresa>")
				.append("<DireccionDTO><direccion>")
				.append("<paisId>6815744</paisId>")
				.append("<provinciaId>").append(cliente.getEntidad().getDireccions().get(0).getCiudad().getProvincia().getCodigoSbs()).append("</provinciaId>")
				.append("<ciudadId>").append(cliente.getEntidad().getDireccions().get(0).getCiudad().getId()).append("</ciudadId>");
				if(cliente.getEntidad().getDireccions().get(0).getParroquia()!=null){
					xml.append("<cantonId>").append(cliente.getEntidad().getDireccions().get(0).getParroquia().getCanton().getId()).append("</cantonId>")
					.append("<parroquiaId>").append(cliente.getEntidad().getDireccions().get(0).getParroquia().getId()).append("</parroquiaId>");
				}
				else{
					xml.append("<cantonId>").append("1201").append("</cantonId>")
					.append("<parroquiaId>").append("120153").append("</parroquiaId>");
				}
				xml.append("<direccion>")
				.append(cliente.getEntidad().getDireccions().get(0).getCallePrincipal())
				.append(" ")
				.append(cliente.getEntidad().getDireccions().get(0).getNumero())
				.append(" ")
				.append(cliente.getEntidad().getDireccions().get(0).getCalleSecundaria())
				.append("</direccion>")
				.append("<telefono>").append(cliente.getEntidad().getTelefono()).append("</telefono>")
				.append("</direccion></DireccionDTO>")
				.append("</cliente>")
				
				.append("<asegurado>")
				.append("<id>no</id>")
				.append("<entidadId>").append(cotizacion.getAsegurado().getEntEnsurance()).append("</entidadId>")
				.append("<nombres>").append(cotizacion.getAsegurado().getNombres()).append("</nombres>")
				.append("<apellidos>").append(cotizacion.getAsegurado().getApellidos()).append("</apellidos>")
				.append("<tipoIdentificacion>")
				.append(cotizacion.getAsegurado().getTipoIdentificacion().getId()=="1" ? "c":cliente.getEntidad().getTipoIdentificacion().getId()=="2" ? "p":"r")
				.append("</tipoIdentificacion>")
				.append("<tipoEntidadId>").append(cotizacion.getAsegurado().getTipoEntidad().getId()).append("</tipoEntidadId>")
				.append("<identificacion>").append(cotizacion.getAsegurado().getIdentificacion()).append("</identificacion>")
				.append("<genero>f</genero>")
				.append("<esEmpresa>")
				.append(cotizacion.getAsegurado().getTipoIdentificacion().getId()=="1" ? "false":cotizacion.getAsegurado().getTipoIdentificacion().getId()=="2" ? "false":"true")
				.append("</esEmpresa>")
				.append("<DireccionDTO><direccion>")
				.append("<paisId>6815744</paisId>")
				.append("<provinciaId>").append(cotizacion.getAsegurado().getDireccions().get(0).getCiudad().getProvincia().getCodigoSbs()).append("</provinciaId>")
				.append("<ciudadId>").append(cotizacion.getAsegurado().getDireccions().get(0).getCiudad().getId()).append("</ciudadId>");
				if(cotizacion.getAsegurado().getDireccions().get(0).getParroquia()!=null){
					xml.append("<cantonId>").append(cotizacion.getAsegurado().getDireccions().get(0).getParroquia().getCanton().getId()).append("</cantonId>")
					.append("<parroquiaId>").append(cotizacion.getAsegurado().getDireccions().get(0).getParroquia().getId()).append("</parroquiaId>");
				}
				else{
					xml.append("<cantonId>").append("1201").append("</cantonId>")
					.append("<parroquiaId>").append("120153").append("</parroquiaId>");
				}
				
				xml.append("<direccion>")
				.append(cotizacion.getAsegurado().getDireccions().get(0).getCallePrincipal())
				.append(" ")
				.append(cotizacion.getAsegurado().getDireccions().get(0).getNumero())
				.append(" ")
				.append(cotizacion.getAsegurado().getDireccions().get(0).getCalleSecundaria())
				.append("</direccion>")
				.append("<telefono>").append(cotizacion.getAsegurado().getTelefono()).append("</telefono>")
				.append("</direccion></DireccionDTO>")
				.append("</asegurado>");
				if(endosoBeneficiario!=null){
					Entidad entidadBeneficiario=entidadDAO.buscarPorId(endosoBeneficiario.getBeneficiario().getCodigoEnsurance().toString());
					xml.append("<beneficiarios>")
				    .append("<id>no</id>")
				    .append("<entidadId>").append(endosoBeneficiario.getBeneficiario().getCodigoEnsurance()).append("</entidadId>")
				    .append("<nombres>").append(endosoBeneficiario.getBeneficiario().getNombre()).append("</nombres>")
				    .append("<apellidos>").append(endosoBeneficiario.getBeneficiario().getNombre()).append("</apellidos>")
				    .append("<tipoIdentificacion>")
				    .append(entidadBeneficiario.getTipoIdentificacion().getId()=="1" ? "c":cliente.getEntidad().getTipoIdentificacion().getId()=="2" ? "p":"r")
					.append("</tipoIdentificacion>")
				    .append("<tipoEntidadId>").append(entidadBeneficiario.getTipoEntidad().getId()).append("</tipoEntidadId>")
				    .append("<identificacion>").append(entidadBeneficiario.getIdentificacion()).append("</identificacion>")
				    .append("<esEmpresa>")
				    .append(cotizacion.getAsegurado().getTipoIdentificacion().getId()=="1" ? "false":cotizacion.getAsegurado().getTipoIdentificacion().getId()=="2" ? "false":"true")
					.append("</esEmpresa>")
				    .append("<DireccionDTO>")
				    .append("<direccion>")
				    .append("<paisId>-1</paisId>")
				    .append("<provinciaId>-1</provinciaId>")
				    .append("<cantonId>-1</cantonId>")
				    .append("<parroquiaId>-1</parroquiaId>")
				    .append("<ciudadId>-1</ciudadId>")
				    .append("<direccion>-1</direccion>")
				    .append("<telefono>-1</telefono>")
				    .append("</direccion>")
				    .append("</DireccionDTO>")
				    .append("</beneficiarios>");
				}
				else{
					xml.append("<beneficiarios>")
				    .append("<id>no</id>")
				    .append("<entidadId>-1</entidadId>")
				    .append("<nombres>-1</nombres>")
				    .append("<apellidos>-1</apellidos>")
				    .append("<tipoIdentificacion>-1</tipoIdentificacion>")
				    .append("<tipoEntidadId>-1</tipoEntidadId>")
				    .append("<identificacion>-1</identificacion>")
				    .append("<esEmpresa>-1</esEmpresa>")
				    .append("<DireccionDTO>")
				    .append("<direccion>")
				    .append("<paisId>-1</paisId>")
				    .append("<provinciaId>-1</provinciaId>")
				    .append("<cantonId>-1</cantonId>")
				    .append("<parroquiaId>-1</parroquiaId>")
				    .append("<ciudadId>-1</ciudadId>")
				    .append("<direccion>-1</direccion>")
				    .append("<telefono>-1</telefono>")
				    .append("</direccion>")
				    .append("</DireccionDTO>")
				    .append("</beneficiarios>");
				}
				xml.append("</superObjetoXML>");
		return xml.toString();
	}
}

