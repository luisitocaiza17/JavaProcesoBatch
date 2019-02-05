package com.qbe.cotizador.servlets.producto.vehiculo;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.qbe.cotizador.dao.cotizacion.CoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.PaqueteDAO;
import com.qbe.cotizador.dao.cotizacion.SolicitudDescuentoDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.entidad.ClienteDAO;
import com.qbe.cotizador.dao.entidad.SucursalDAO;
import com.qbe.cotizador.dao.inspeccion.SolicitudInspeccionDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ColorDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ExtraDAO;
import com.qbe.cotizador.dao.producto.vehiculo.MarcaDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ModeloDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ObjetoVehiculoDAO;
import com.qbe.cotizador.dao.producto.vehiculo.TipoExtraDAO;
import com.qbe.cotizador.dao.producto.vehiculo.TipoUsoDAO;
import com.qbe.cotizador.dao.producto.vehiculo.TipoVehiculoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.TasaProductoDAO;
import com.qbe.cotizador.dao.seguridad.TipoVariableDAO;
import com.qbe.cotizador.dao.seguridad.VariableSistemaDAO;
import com.qbe.cotizador.model.Cliente;
import com.qbe.cotizador.model.Cobertura;
import com.qbe.cotizador.model.Color;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionCobertura;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Estado;
import com.qbe.cotizador.model.Extra;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.Marca;
import com.qbe.cotizador.model.Modelo;
import com.qbe.cotizador.model.ObjetoVehiculo;
import com.qbe.cotizador.model.Paquete;
import com.qbe.cotizador.model.SolicitudDescuento;
import com.qbe.cotizador.model.SolicitudInspeccion;
import com.qbe.cotizador.model.Sucursal;
import com.qbe.cotizador.model.TasaProducto;
import com.qbe.cotizador.model.TipoExtra;
import com.qbe.cotizador.model.TipoObjeto;
import com.qbe.cotizador.model.TipoUso;
import com.qbe.cotizador.model.TipoVariable;
import com.qbe.cotizador.model.TipoVehiculo;
import com.qbe.cotizador.model.VariableSistema;
import com.qbe.cotizador.model.VigenciaPoliza;
import com.qbe.cotizador.servicios.QBE.cliente.WebServiceCotizadorImplService;
import com.qbe.cotizador.servlets.cotizacion.CoberturaComparator;
import com.qbe.cotizador.transaction.cotizacion.CotizacionCoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionDetalleTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.ExtraTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.ObjetoVehiculoTransaction;
import com.qbe.cotizador.util.ManejoColas;
import com.qbe.cotizador.util.ManejoFTP;
import com.qbe.cotizador.util.MotorTarifador;
import com.qbe.cotizador.util.Utilitarios;


/**
 * Servlet implementation class ObjetoVehiculoController
 */
@WebServlet("/ObjetoVehiculoController")
public class ObjetoVehiculoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObjetoVehiculoController() {
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
			String producto = request.getParameter("producto") == null ? "" : request.getParameter("producto");
			String tipoObjeto = request.getParameter("tipoObjeto") == null ? "" : request.getParameter("tipoObjeto").trim();
			JSONObject vehiculoJSONObject = new JSONObject();
			JSONArray vehiculoJSONArray = new JSONArray();
			JSONArray coberturasTextosJSONArray = new JSONArray();
			JSONObject coberturaTextosJSONObject = new JSONObject();
			JSONObject impuestoJSONObject = new JSONObject();
			JSONArray impuestoJSONArray = new JSONArray();
			Cotizacion cotizacion = new Cotizacion();
			CotizacionDAO cotizacionDAO = new CotizacionDAO();
			
			ExtraTransaction extraTransaction = new ExtraTransaction();
			CotizacionTransaction cotizacionTransaction= new CotizacionTransaction();
			ObjetoVehiculoTransaction objetoVehiculoTransaction= new ObjetoVehiculoTransaction();
			CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
			CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
			
			// Metodo obtener todos vehiculos de una cotizacion ayanez
			if(tipoConsulta.equalsIgnoreCase("obtenerVehiculos") && producto.equalsIgnoreCase("productoVehiculo"))
			{			
 				cotizacion = cotizacionDAO.buscarPorId(request.getParameter("cotizacionId"));
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = new Cliente();
											
				if(cotizacion.getClienteId() != null)
					cliente = clienteDAO.buscarPorId(cotizacion.getClienteId().toString());
								
				CotizacionDetalleDAO cotizacionDetalleDAO= new CotizacionDetalleDAO();		
				List<CotizacionDetalle> cotizacionesDetalle = cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);
								
				ObjetoVehiculoDAO objetoVehiculoDAO = new ObjetoVehiculoDAO();
				List<ObjetoVehiculo> listadoVehiculos = objetoVehiculoDAO.buscarObjetoVehiculoPorCotizacionDetalle(cotizacionesDetalle);
				ObjetoVehiculo objetoVehiculo = new ObjetoVehiculo();
				SolicitudDescuentoDAO solicitudDescuentoDAO = new SolicitudDescuentoDAO();
				EstadoDAO estadoDAO = new EstadoDAO();
				Estado estado = estadoDAO.buscarPorId("7");
				SolicitudDescuento solicitudDescuento = solicitudDescuentoDAO.buscarPorCotizacion(cotizacion,estado);
				int i = 0;
				if(listadoVehiculos.size() > 0) {
					Double sumaAseguradaTotal = 0.0;
					Double sumaPrimaOrigen = 0.0;
					Double sumaPrimaNeta = 0.0;
					for(i=0; i<listadoVehiculos.size(); i++) {
						objetoVehiculo = (ObjetoVehiculo) listadoVehiculos.get(i);		
						vehiculoJSONObject.put("codigo_vehiculo", objetoVehiculo.getId());
						vehiculoJSONObject.put("marca_modelo", objetoVehiculo.getModelo().getMarca().getNombre() +" :: "+ objetoVehiculo.getModelo().getNombre());
						vehiculoJSONObject.put("ano_fabricacion", objetoVehiculo.getAnioFabricacion());
						
						SucursalDAO sucursalDAO = new SucursalDAO ();
						Sucursal sucursal =sucursalDAO.buscarPorId(objetoVehiculo.getSucursalId());
						vehiculoJSONObject.put("sucursal", sucursal.getNombre());
						
						CotizacionDetalle cotizacionDetalle = cotizacionDetalleDAO.buscarPorId(cotizacionesDetalle.get(i).getId());
						
						sumaAseguradaTotal += cotizacionDetalle.getSumaAseguradaItem();
						sumaPrimaOrigen += cotizacionDetalle.getPrimaNetaItemOrigen();
						sumaPrimaNeta += cotizacionDetalle.getPrimaNetaItem();
						
						vehiculoJSONObject.put("suma_asegurada", cotizacionDetalle.getSumaAseguradaItem());
						vehiculoJSONObject.put("prima_vehiculo", cotizacionDetalle.getPrimaNetaItem());
						vehiculoJSONObject.put("tasa_vehiculo", cotizacionDetalle.getTasa());
						String valorRastreo = "No";
						
						if (objetoVehiculo.getDispositivoRastreo())
							valorRastreo = "Si";							
						vehiculoJSONObject.put("dispositivo_rastreo", valorRastreo);
						
						int numeroExtras = objetoVehiculo.getExtras().size();
						
						vehiculoJSONArray.add(vehiculoJSONObject);
					}
					cotizacion.setSumaAseguradaTotal(sumaAseguradaTotal);
					cotizacion.setPrimaOrigen(sumaPrimaOrigen);
					cotizacion.setPrimaNetaTotal(sumaPrimaNeta.toString());
					SolicitudDescuentoDAO sdDAO=new SolicitudDescuentoDAO();
					SolicitudDescuento sd=sdDAO.buscarPorCotizacion(cotizacion);
					if(sd==null||sd.getId()==null)
						cotizacion.setSolicitudDescuentos(new ArrayList<SolicitudDescuento>());
					if(cotizacion.getNumeroFactura()==null)
						cotizacionTransaction.editar(cotizacion);
				}
				
				result.put("vehiculosCotizacion", vehiculoJSONArray);
				result.put("numeroVehiculos", i);
				CotizacionDetalle cotizacionDetalle = new CotizacionDetalle();
				VariableSistema variable = new VariableSistema();  
				VariableSistemaDAO variableDAO = new VariableSistemaDAO(); 
				double valorPrima = 0;
				double valorAsegurado = 0;
				double valorPrimaDescuento = 0;
				double valorFinalPrima = 0;
				if(cotizacionesDetalle.size() > 0) {
					for(i=0; i<cotizacionesDetalle.size(); i++) {
						cotizacionDetalle = (CotizacionDetalle) cotizacionesDetalle.get(i);					
						valorPrima = valorPrima + cotizacionDetalle.getPrimaNetaItem();
						valorAsegurado = valorAsegurado+ cotizacionDetalle.getSumaAseguradaItem();
						valorPrima = Math.rint(valorPrima*100)/100;
						valorAsegurado = Math.rint(valorAsegurado*100)/100;
					}
					valorFinalPrima = valorPrima;
					if(solicitudDescuento != null && solicitudDescuento.getId()!=null){
						if(solicitudDescuento.getEstado().getId().equals("7")){	
							valorPrimaDescuento = Math.rint(((Double.parseDouble(solicitudDescuento.getPorcentaje())/100)*valorFinalPrima));
						    valorPrimaDescuento = valorFinalPrima - valorPrimaDescuento;
							//valorFinalPrima = valorPrimaDescuento;
							cotizacion.setPrimaNetaTotal(valorFinalPrima+"");
							result.put("porcentajeDescuento", solicitudDescuento.getPorcentaje());
						}	
					}else{
						result.put("porcentajeDescuento", 0.0);  
					}
					TipoVariableDAO tipoVariableDao = new TipoVariableDAO();
					TipoVariable tipoVariable = tipoVariableDao.buscarPorId("3");
					List<VariableSistema> variablesistema = variableDAO.buscarTipoVariable(tipoVariable);
					double valorBase = 0;
					double valorDerechosEmision = 0;
					double valorSeguroCampesino = 0;
					double valorRecargoCampesino = 0;
					double valorSuperBancos = 0;
					double valorIva= 0;
					double valorSubTotal = 0;
					double valorTotal = 0;
					result.put("valorPrima", valorFinalPrima);
					result.put("valorAsegurado", valorAsegurado);
					if(variablesistema.size() > 0) {
						for(i=0; i<variablesistema.size(); i++) {
							variable = (VariableSistema) variablesistema.get(i);
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
							/*else if(variable.getNombre().equals("RECARGO_SEGURO_CAMPESINO")){
								InquiredServiceInterfaceService servicio = new InquiredServiceInterfaceService();
								valorRecargoCampesino = servicio.getInquiredServiceInterfacePort().consultarRecargoSeguroAgricola(cliente.getEntidad().getIdentificacion(), valorFinalPrima);
								cotizacion.setImpDerechoEmision(valorRecargoCampesino);
								result.put("valorRecargoCampesino", valorRecargoCampesino);
								valorBase = valorBase + valorRecargoCampesino;
								
							}*/
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
						cotizacion.setTotalFactura(valorTotal);
						result.put("valorTotal", valorTotal);
						if(cotizacion.getNumeroFactura()==null)
							cotizacionTransaction.editar(cotizacion);
						
					}
					
				}
				
				// Obtenemos los textos de las coberturas y presentar en orden 
				for(CotizacionDetalle cotizacionDetalleItem:cotizacionesDetalle){
					List<CotizacionCobertura> cotizacionCoberturas = cotizacionDetalleItem.getCotizacionCoberturas();
					List<Cobertura> coberturas = new ArrayList<Cobertura>();
					
					// Obtenemos las coberturas de las cotizaciones coberturas
					for(CotizacionCobertura cotizacionCobertura:cotizacionCoberturas){
						Cobertura cobertura = cotizacionCobertura.getCobertura();
						coberturas.add(cobertura);
					}
					// Ordenamiento de los las coberturas por medio de las secciones y el orden
					Collections.sort(coberturas, new CoberturaComparator());
					
					// Recorre las coberturas y se les agrega dentro de un objeto json y se graba en un jsonarray
					for(Cobertura cobertura:coberturas){						
						coberturaTextosJSONObject.put("tipo_cobertura_nombre", cobertura.getTipoCobertura().getNombre());						
						coberturaTextosJSONObject.put("texto", cobertura.getNombre());
						coberturasTextosJSONArray.add(coberturaTextosJSONObject);
					}
				}
				// Enviamos los textos ordenamos de las coberturas
				result.put("textosCoberturas", coberturasTextosJSONArray);
			}
			
			
			// Metodo para agregar extras al vehiculo
			if(tipoConsulta.equalsIgnoreCase("extrasCotizacion") && producto.equalsIgnoreCase("productoVehiculo"))
			{			
				ObjetoVehiculoDAO objetoVehiculoDAO = new ObjetoVehiculoDAO();
				ObjetoVehiculo objetoVehiculo = objetoVehiculoDAO.buscarPorId(request.getParameter("vehiculoId"));
				
				TipoExtraDAO tipoExtraDAO = new TipoExtraDAO();
				TipoExtra tipoExtra = tipoExtraDAO.buscarPorId(request.getParameter("extraId")); 
				
				Extra extra = new Extra(); 
				extra.setObjetoVehiculo(objetoVehiculo);
				extra.setTipoExtra(tipoExtra);
				extra.setDescripcion(request.getParameter("detalleExtra"));
				extra.setValorAsegurado(Double.parseDouble(request.getParameter("valorExtra")));
				
				ExtraDAO extraDAO = new ExtraDAO();
				extra = extraTransaction.crear(extra);
				result.put("Id", extra.getId());				
			}
			
			//cargar vehiculos por web service en ensurance
			if(tipoConsulta.equals("cargarPorParametro")){
				
				// Obtener datos del usuario desde ensurance
				String parametro = request.getParameter("parametro") == null ? "" : request.getParameter("parametro");
				String nombreParametro = request.getParameter("nombreParametro") == null ? "" : request.getParameter("nombreParametro");
				
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				String resultado = webService.getWebServiceCotizadorImplPort().obtenerDatosVehiculo(parametro, nombreParametro);
				result.put("vehiculo", cargarParametroWS(resultado));
					
			}
			
			if(tipoConsulta.equals("guardarVehiculo")){
				String sumaAsegurada = request.getParameter("suma_asegurada_valor") == null ? "" : request.getParameter("suma_asegurada_valor");
				String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
				String coberturasAdicionalesStr = request.getParameter("coberturasAdicionales");
				String tasaVehiculosCerrados = request.getParameter("tasaVehiculosCerrados") == null ? "" : request.getParameter("tasaVehiculosCerrados");
				String valorExcesoRC = request.getParameter("valorExcesoRC") == "" ? "0" : request.getParameter("valorExcesoRC");
				String paquete1_check = request.getParameter("paquete1_check");
				String paquete2_check = request.getParameter("paquete2_check");
				String paquete3_check = request.getParameter("paquete3_check");
				String paquete4_check = request.getParameter("paquete4_check");
				String paquete5_check = request.getParameter("paquete5_check");
				String coberturaTR = request.getParameter("coberturaTR_check");
				String coberturaDT = request.getParameter("coberturaDT_check");
				String coberturaRC = request.getParameter("coberturaRC_check");
				String porcentajeSumaAsegurada = request.getParameter("porcentaje_suma_asegurada");
				String montoFijo = request.getParameter("monto_fijo");
				String valorSiniestro = request.getParameter("valor_siniestro");
				String listaExtrasIds = request.getParameter("listaExtrasIds") == null ? "" : request.getParameter("listaExtrasIds");
				String listaExtrasDetalles = request.getParameter("listaExtrasDetalles") == null ? "" : request.getParameter("listaExtrasDetalles");
				String listaExtrasValores = request.getParameter("listaExtrasValores") == null ? "" : request.getParameter("listaExtrasValores");
				
				ObjetoVehiculo ov=agregarVehiculoCotizacion(request);
				
				TipoObjetoDAO tipoObjetoDAO = new TipoObjetoDAO();	
				TipoObjeto tipoObjetoVehiculo = new TipoObjeto();
				
				CotizacionDAO cDAO=new CotizacionDAO();
				cotizacion=cDAO.buscarPorId(cotizacionId);
				
				if(tipoObjeto.equalsIgnoreCase("VHDinamico")){
					tipoObjetoVehiculo = tipoObjetoDAO.buscarPorNombre("Vehiculos Livianos");
				}				
				if(tipoObjeto.equalsIgnoreCase("Livianos")){
					tipoObjetoVehiculo = tipoObjetoDAO.buscarPorNombre("Vehiculos Cerrados Livianos");
				}
				if(tipoObjeto.equalsIgnoreCase("Motos")){
					tipoObjetoVehiculo = tipoObjetoDAO.buscarPorNombre("Vehiculos Cerrados Motos");
				}
				if(tipoObjeto.equalsIgnoreCase("Pesados")){
					tipoObjetoVehiculo = tipoObjetoDAO.buscarPorNombre("Vehiculos Cerrados Pesados");
				}
				if(tipoObjeto.equalsIgnoreCase("Publicos")){
					tipoObjetoVehiculo = tipoObjetoDAO.buscarPorNombre("Veh�culos Cerrados Publicos");
				}
				
				String[] arrListaExtrasIds = listaExtrasIds.split(","); 
				String[] arrListaExtrasDetalles = listaExtrasDetalles.split(","); 
				String[] arrListaExtrasValores = listaExtrasValores.split(",");
				
				VigenciaPoliza vp= cotizacion.getVigenciaPoliza();
				
				double valorTotalExtrasPrimerAnio=0.0;
				double valorTotalExtrasTodosAnios=0.0;
				double valorTotalCascoPrimerAnio=new Double(sumaAsegurada);
				double valorTotalCascoTodosAnios=depreciarValor(valorTotalCascoPrimerAnio, vp.getValor().intValue());

				if(arrListaExtrasDetalles.length>1){
					valorTotalExtrasPrimerAnio = crearExtrasPorVehiculo(ov, arrListaExtrasIds, arrListaExtrasDetalles, arrListaExtrasValores);
					valorTotalExtrasTodosAnios = depreciarValor(valorTotalExtrasPrimerAnio, vp.getValor().intValue());
				}
				
				VariableSistemaDAO variableSistemaDAO = new VariableSistemaDAO();
				List<String> variableSistemaList = new ArrayList<String>();
				variableSistemaList.add("TARIFICADOR_LOCAL");
				String tarificadorLocal = variableSistemaDAO.buscarPorNombres(variableSistemaList).get(0);
								
				double primaCoberturasPrincipales=0.0;
				double tasaCasco=0.0;
				double tasaExtras=0.0;
				double primaAfectaMonto=0.0;
				double primaNoAfectaMonto=0.0;
				double primaTotalPrincipales=0.0;
				double primaExtras=0.0;
				double primaAdicionales=0.0;
				double numeroDiasVigencia=Double.parseDouble(calcularDiasVigenciaCotizacion(cotizacion)+"");
				double totalResponsabilidadCivil=0.0;
				double totalDanioTotal=0.0;
				double totalTodoRisgo=0.0;
				double porcentajeConDescuento=1-(cotizacion.getValorDescuento()/100);
				
				CotizacionDetalle cd=agregarCotizacionDetalle(ov, cotizacion, tipoObjetoVehiculo, valorTotalExtrasPrimerAnio);
				CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
				ccDAO.eliminarPorCotizacionDetalle(cd);
				cd.setCotizacionCoberturas(new ArrayList<CotizacionCobertura>());
				

				if(cotizacion.getEtapaWizard()<2)
				{
					cotizacion.setEtapaWizard(2);
					cotizacion=cotizacionTransaction.editar(cotizacion);
				}
				
				double tasaRecalculo=cotizacion.getTasaMinima();
						
				if(tipoObjeto.equalsIgnoreCase("VHDinamico")){
					//se calcula la prima por el primer anio 365 dias
					JSONObject aux=calcularPrimaCoberturasPrincipalesVehiculosDinamicos(cotizacion, tarificadorLocal, coberturaTR, coberturaDT, coberturaRC, ov, montoFijo, valorSiniestro, porcentajeSumaAsegurada, valorTotalCascoPrimerAnio);
					primaAfectaMonto = aux.getDouble("primaAfectaMonto");
					primaNoAfectaMonto = aux.getDouble("primaNoAfectaMonto");
					totalResponsabilidadCivil = aux.getDouble("totalResponsabilidadCivil");
					totalDanioTotal = aux.getDouble("totalDanioTotal");
					totalTodoRisgo = aux.getDouble("totalTodoRisgo");
					primaTotalPrincipales=primaAfectaMonto+primaNoAfectaMonto;
					tasaCasco=primaTotalPrincipales/valorTotalCascoPrimerAnio;
					double tasaMinima=new Double(variableSistemaDAO.buscarPorNombre("TASA_MINIMA").getValor());				
					
					if(tasaCasco<tasaMinima && coberturaTR.equalsIgnoreCase("true") )
						tasaCasco=tasaMinima;
					if(tasaRecalculo>0)
						tasaCasco=tasaRecalculo;
					
					tasaExtras=tasaCasco;
					primaTotalPrincipales=tasaCasco*valorTotalCascoTodosAnios;
					
					int anios=vp.getValor().intValue();
					Date inicioVigencia=cotizacion.getVigenciaDesde();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cotizacion.getVigenciaDesde());
					
					
					double auxDepreciacion=valorTotalCascoPrimerAnio;
					double primaTotalSinImpuestos=0;
					for (int i = 1; i <= anios; i++) {
						Date fechaAnterior=calendar.getTime();
						calendar.add(Calendar.YEAR, 1);
						int numeroDias=Days.daysBetween(new DateTime(fechaAnterior), new DateTime(calendar.getTime())).getDays();
						
						double valorDepreciado=0;
						
						if (i == 1) {
							valorDepreciado = auxDepreciacion;
						} else if (i > 1) {
							if (i == 2)
								valorDepreciado = auxDepreciacion - auxDepreciacion * 0.15;
							else
								valorDepreciado = auxDepreciacion - auxDepreciacion * 0.10;

							auxDepreciacion = valorDepreciado;
						}
						primaTotalSinImpuestos+=(auxDepreciacion*tasaCasco*numeroDias)/365;
					}
					
					primaTotalPrincipales=primaTotalSinImpuestos;

					double primaAcumuladaPrincipales=guardarCotizacionCoberturaPrincipalesDinamicos(cd, new Double(porcentajeSumaAsegurada), new Double(montoFijo), new Double(valorSiniestro), totalTodoRisgo,totalDanioTotal,totalResponsabilidadCivil, porcentajeConDescuento, primaTotalPrincipales, valorTotalCascoPrimerAnio);
					double primaAcumuladaExtras=0.0;
					if(valorTotalExtrasPrimerAnio>0){
						primaAcumuladaExtras=guardarCotizacionCoberturaExtras(cd,tasaExtras,valorTotalExtrasTodosAnios, valorTotalExtrasPrimerAnio, porcentajeConDescuento, numeroDiasVigencia);
					}
					if(coberturasAdicionalesStr.length()>0){
						primaAdicionales=guardarCotizacionCoberturasAdicionales(cd,coberturasAdicionalesStr, valorTotalCascoPrimerAnio, valorTotalCascoTodosAnios, porcentajeConDescuento, new Double(valorExcesoRC));
					}
				}
				else{
					
					GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();	
					GrupoPorProducto grupoPorProducto = grupoPorProductoDAO.buscarPorId(cotizacion.getGrupoPorProductoId().toString());
					// Verificamos si la tasa es fija, formulada o tiene tasa variable
					if(grupoPorProducto.getTasaFija() && grupoPorProducto.getFormulada()== false ){ // Tasa productos tasa fija
						tasaCasco = grupoPorProducto.getPorcentajeTasaFija()/100;
						tasaExtras = grupoPorProducto.getPorcentajeExtrasTasaFija()/100;
						if(tasaExtras<=0)
							tasaExtras=tasaCasco;
					}
					if(grupoPorProducto.getTasaFija() == false && grupoPorProducto.getFormulada()== false ){ // Tasa productos tasa varios valores
						TasaProductoDAO tasaProductoDAO = new TasaProductoDAO();
						TasaProducto tasaProducto = new TasaProducto();
						tasaProducto = tasaProductoDAO.buscarPorId(cotizacion.getTasaProductoId().toString());	
						tasaCasco = tasaProducto.getPorcentajeCasco()/100;
						tasaExtras =tasaProducto.getPorcentajeExtras()/100;
						// porcentajeExtras = Double.parseDouble(resultado.get(key).toString());
					}
					if(grupoPorProducto.getTasaFija() == false && grupoPorProducto.getFormulada() ){ // Tasa productos formulados
						tasaCasco = Double.parseDouble(tasaVehiculosCerrados)/100;
						tasaExtras =Double.parseDouble(tasaVehiculosCerrados)/100;
					}
					
					primaCoberturasPrincipales=tasaCasco*valorTotalCascoTodosAnios;
					//primaTotalPrincipales=(primaCoberturasPrincipales/(vp.getValor().doubleValue()*365.0))*numeroDiasVigencia;
					
					int anios=vp.getValor().intValue();
					Date inicioVigencia=cotizacion.getVigenciaDesde();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cotizacion.getVigenciaDesde());
					
					
					double auxDepreciacion=valorTotalCascoPrimerAnio;
					double primaTotalSinImpuestos=0;
					// Adicionamos valor para ajustar valores - Vehiculos autoconsa - plan v
					if(!grupoPorProducto.getGrupoProducto().getId().equalsIgnoreCase("11")){
					for (int i = 1; i <= anios; i++) {
						Date fechaAnterior=calendar.getTime();
						calendar.add(Calendar.YEAR, 1);
						int numeroDias=Days.daysBetween(new DateTime(fechaAnterior), new DateTime(calendar.getTime())).getDays();
						
						double valorDepreciado=0;
						
						if (i == 1) {
							valorDepreciado = auxDepreciacion;
						} else if (i > 1) {
							if (i == 2)
								valorDepreciado = auxDepreciacion - auxDepreciacion * 0.15;
							else
								valorDepreciado = auxDepreciacion - auxDepreciacion * 0.10;

							auxDepreciacion = valorDepreciado;
						}
						primaTotalSinImpuestos+=(auxDepreciacion*tasaCasco*numeroDias)/365;
					}					
					}else{
						primaTotalSinImpuestos = auxDepreciacion*(tasaCasco*100)/100;
						primaTotalSinImpuestos = primaTotalSinImpuestos * vp.getValor().doubleValue();
					}
					
					primaTotalPrincipales=primaTotalSinImpuestos;
					
					
					if(coberturaDT.equalsIgnoreCase("true")&&!coberturaRC.equalsIgnoreCase("true")){
						totalDanioTotal=primaCoberturasPrincipales;
					}
					if(coberturaRC.equalsIgnoreCase("true")&&!coberturaDT.equalsIgnoreCase("true")){
						totalResponsabilidadCivil=primaCoberturasPrincipales;
					}
					if(coberturaRC.equalsIgnoreCase("true")&&coberturaDT.equalsIgnoreCase("true")){
						totalResponsabilidadCivil=primaCoberturasPrincipales/2;
						totalResponsabilidadCivil=primaCoberturasPrincipales/2;
					}
					
					if(coberturaTR.equalsIgnoreCase("true")){
						totalTodoRisgo=primaCoberturasPrincipales;
					} 
					
					guardarCotizacionCoberturaPrincipalesCerrados(cd, new Double(porcentajeSumaAsegurada), new Double(montoFijo), new Double(valorSiniestro), totalTodoRisgo, totalDanioTotal, totalResponsabilidadCivil, porcentajeConDescuento, primaTotalPrincipales, valorTotalCascoPrimerAnio);
					
					if(valorTotalExtrasPrimerAnio>0)
						guardarCotizacionCoberturaExtras(cd, tasaExtras, valorTotalExtrasTodosAnios, valorTotalExtrasPrimerAnio, porcentajeConDescuento, numeroDiasVigencia);
					
					if(coberturasAdicionalesStr.length()>0)
						primaAdicionales=guardarCotizacionCoberturasAdicionales(cd,coberturasAdicionalesStr, valorTotalCascoPrimerAnio, valorTotalCascoTodosAnios, porcentajeConDescuento, new Double(valorExcesoRC));
				}
				
				Paquete paquete = new Paquete();
				PaqueteDAO paqueteDAO = new PaqueteDAO();
				if(paquete1_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("1");
				}
				if(paquete2_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("2");
				}
				if(paquete3_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("3");
				}
				if(paquete4_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("4");
				}
				if(paquete5_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("5");
				}
				if(paquete5_check.equalsIgnoreCase("true")){
					paquete = paqueteDAO.buscarPorId("5");
				}
				
				actualizarValoresCotizacionDetalle(cd, porcentajeConDescuento, paquete, tasaCasco);
				
				result.put("vehiculoId", ov.getId());
				result.put("tasaVehiculo", tasaCasco*100);
				
			}
			
			if(tipoConsulta.equals("actualizarVehiculo")){
				
				// Obtener datos del usuario desde ensurance
				String id = request.getParameter("id") == null ? "" : request.getParameter("id").trim();
				String placa = request.getParameter("placa") == null ? "" : request.getParameter("placa").trim();
				String motor = request.getParameter("motor") == null ? "" : request.getParameter("chasis").trim();
				String chasis = request.getParameter("chasis") == null ? "" : request.getParameter("motor").trim();
				
				ObjetoVehiculoDAO ovDAO=new ObjetoVehiculoDAO();
				ObjetoVehiculo ov=ovDAO.buscarPorId(id);
				
				ov.setPlaca(placa.toUpperCase());
				ov.setMotor(motor.toUpperCase());
				ov.setChasis(chasis.toUpperCase());
				
				if(ov.getId()!=null)
					objetoVehiculoTransaction.editar(ov);

					
			}
			
 			String valor = request.getParameter("valor") == null ? "" : request.getParameter("valor").trim();
			String parametro = request.getParameter("parametro") == null ? "" : request.getParameter("parametro").trim();
			// Metodo para consultar los datos del vehiculo en la corpaire
			if (tipoConsulta.equalsIgnoreCase("consultarCorpaire")) {
				try {
					result.put("datosVehiculo",consultarCorpaire(valor,parametro));
				}
				catch (Exception e) {
					result.put("datosVehiculo", new JSONObject());
				}
			}
			
			// Metodo para consultar los datos del vehiculo en el sri por la placa
			if (tipoConsulta.equalsIgnoreCase("consultarPlacaSRI")) {
				try {
					result.put("datosVehiculo",consultarPlacaSRI(valor));
				}
				catch (Exception e) {
					result.put("datosVehiculo", new JSONObject());
				}
			}
			
			// Metodo para consultar los datos del vehiculo en el sri por el chasis
			if (tipoConsulta.equalsIgnoreCase("consultarChasisSRI")) {
				try {
					result.put("datosVehiculo",consultarChasisSRI(valor));
					
				}
				catch (Exception e) {
					result.put("datosVehiculo", new JSONObject());
				}
			}
			
			
			
			if(tipoConsulta.equalsIgnoreCase("consultarANT"))
			{		
				
				
				try {
					result.put("datosVehiculo", consultarANT(valor));
				}
				catch (Exception e) {
					result.put("datosVehiculo", new JSONObject());
				}
				
			}
			
			if(tipoConsulta.equalsIgnoreCase("consultaGeneral"))
			{		
				JSONObject respuesta=new JSONObject();
				String formulario = request.getParameter("formulario") == null ? "" : request.getParameter("formulario").trim();
				String numero = request.getParameter("numero") == null ? "" : request.getParameter("numero").trim();
				result.put("formulario", formulario);
				result.put("numero", numero);
				
				try {
					respuesta=consultarANT(valor);
				}
				catch (Exception e) {
					respuesta = new JSONObject();
				}
				
				if(respuesta.isEmpty())
					respuesta=consultarPlacaSRI(valor);
				result.put("datosVehiculo", respuesta);
			}
			
			if(tipoConsulta.equalsIgnoreCase("validarDatosVehiculos"))
			{		
				String placas = request.getParameter("placas") == null ? "" : request.getParameter("placas").trim().toLowerCase();
				String chasiss = request.getParameter("chasiss") == null ? "" : request.getParameter("chasiss").trim().toLowerCase();
				String motors = request.getParameter("motors") == null ? "" : request.getParameter("motors").trim().toLowerCase();
				String marcas = request.getParameter("marcas") == null ? "" : request.getParameter("marcas").trim().toLowerCase();
				String modelos = request.getParameter("modelos") == null ? "" : request.getParameter("modelos").trim().toLowerCase();
				String ids = request.getParameter("ids") == null ? "" : request.getParameter("ids").trim().toLowerCase();
				String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId").trim().toLowerCase();
				
				String [] placasArr=placas.split(";");
				String [] motorsArr=motors.split(";");
				String [] chasissArr=chasiss.split(";");
				String [] marcasArr=marcas.split(";");
				String [] modelosArr=modelos.split(";");
				String [] idsArr=ids.split(";");
				String fechaInicioVigencia="";
				cotizacion=cotizacionDAO.buscarPorId(cotizacionId);
				SolicitudInspeccionDAO siDAO=new SolicitudInspeccionDAO();
				SolicitudInspeccion si=siDAO.buscarPorCotizacion(cotizacion);
				if(si!=null&&si.getId()!=null&&si.getEstado().getNombre().equals("Pendiente"))
					throw new Exception("Tiene una inspeccion pendiente no puede emitir!");
				
				Date aux=cotizacion.getVigenciaDesde();
				String mensaje="";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				for(int i=0;i<idsArr.length;i++){
					JSONObject respuesta=new JSONObject(); 
					JSONObject respuesta2=new JSONObject(); 
					
					
					ObjetoVehiculoDAO ovDAO=new ObjetoVehiculoDAO();
					ObjetoVehiculo ov=ovDAO.buscarPorId(idsArr[i]);
					ov.setPlaca(placasArr[i].toUpperCase());
					ov.setChasis(chasissArr[i].toUpperCase());
					ov.setMotor(motorsArr[i].toUpperCase());
					objetoVehiculoTransaction.editar(ov);

					
					if (placasArr[i] != null && !placasArr[i].equals("")) {
						try {
							respuesta = consultarANT(placasArr[i]);
						}
						catch (Exception e) {
							respuesta = new JSONObject();
						}
						try {
							respuesta2 = consultarPlacaSRI(placasArr[i]);
						}
						catch (Exception e) {
							respuesta2 = new JSONObject();
						}
						if (respuesta.isEmpty())
							respuesta = respuesta2;
					}

					if(respuesta.isEmpty()&&chasissArr[i]!=null&&!chasissArr[i].equals(""))
						respuesta=consultarChasisSRI(chasissArr[i]);
					
					if(respuesta.isEmpty()){
						if(placasArr[i]!=null&&!placasArr[i].equals(""))
							throw new Exception("No se encontraron Datos para el vehiculo de placa "+placasArr[i]);
						if(chasissArr[i]!=null&&!chasissArr[i].equals(""))
							throw new Exception("No se encontraron Datos para el vehiculo de chasis "+chasissArr[i]);
					}
					
					if(!respuesta.isEmpty()){
						if(respuesta.get("placa")!=null&&!respuesta.get("placa").equals(""))
							if(!placasArr[i].equals(respuesta.get("placa")))
								throw new Exception("La placa del vehiculo con chasis "+chasissArr[i]+" no coincide con los datos de la ANT, por favor verifiquelo");
						
						if(respuesta.get("cpn")!=null&&!respuesta.get("cpn").equals("")&&(respuesta.get("placa")==null||respuesta.get("placa").equals("")))
							if(!placasArr[i].equals(respuesta.get("cpn")))
								throw new Exception("La placa del vehiculo con chasis "+chasissArr[i]+" no coincide con los datos de la ANT, por favor verifiquelo");
						
						if(respuesta.get("motor")!=null&&!respuesta.get("motor").equals(""))
							if(!motorsArr[i].equals(respuesta.get("motor")))
								throw new Exception("El motor del vehiculo con placa "+placasArr[i]+" no coincide con los datos de la ANT, por favor verifiquelo");
						
						if(respuesta.get("chasis")!=null&&!respuesta.get("chasis").equals(""))
							if(!chasissArr[i].equals(respuesta.get("chasis")))
								throw new Exception("El chasis del vehiculo con placa "+placasArr[i]+" no coincide con los datos de la ANT, por favor verifiquelo");
						
/*						if ((respuesta.get("modelo") != null && !respuesta.get("modelo").equals("")) || (respuesta2.get("modelo") != null && !respuesta2.get("modelo").equals("")))
							if ((!respuesta.get("modelo").toString().replace("-", "").replace(" ", "").contains(modelosArr[i].toString().replace("-", "")))
									&& (!respuesta2.get("modelo").toString().contains(modelosArr[i].toString().replace("-", "").split(" ")[0]))
									||
							(!modelosArr[i].toString().contains(respuesta.get("modelo").toString().replace("-", "").split(" ")[0]))
									&& (!modelosArr[i].toString().contains(respuesta2.get("modelo").toString().replace("-", "").split(" ")[0])))
								throw new Exception("El modelo del vehiculo con placa "+ placasArr[i]+ " no coincide con los datos de la ANT, por favor verifiquelo");
*/
						if ((respuesta.get("marca") != null && !respuesta.get("marca").equals("")) || (respuesta2.get("marca") != null && !respuesta2.get("marca").equals("")))
							if ((!respuesta.get("marca").toString().replace("-","").replace(" ","").toUpperCase().contains(ov.getModelo().getMarca().getNombre().replace("-", "").split(" ")[0].toUpperCase()))
									&& (!respuesta2.get("marca").toString().replace("-","").replace(" ","").toUpperCase().contains(ov.getModelo().getMarca().getNombre().replace("-", "").split(" ")[0].toUpperCase()))
									|| 
								(!ov.getModelo().getMarca().getNombre().replace("-","").replace(" ","").toUpperCase().contains(respuesta.get("marca").toString().replace("-", "").split(" ")[0].toUpperCase()))
									&& (!ov.getModelo().getMarca().getNombre().replace("-","").replace(" ","").toUpperCase().contains(respuesta2.get("marca").toString().replace("-", "").split(" ")[0].toUpperCase())))
								throw new Exception("La marca del vehiculo con placa " + placasArr[i] + " no coincide con los datos de la ANT, por favor verifiquelo");
						
						if(respuesta.get("vigenciaEnsurance")!=null&&!respuesta.get("vigenciaEnsurance").equals("")){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
							Date fechaVigencia= sdf.parse(respuesta.get("vigenciaEnsurance").toString());
							Date fechaActual=new Date();
							int dias = Days.daysBetween(new DateTime(fechaVigencia), new DateTime(fechaActual)).getDays();
							if(dias>30&&fechaVigencia.after(fechaActual)){
								throw new Exception("El vehiculo de placa "+placasArr[i]+" se encuentra en una poliza vigente! No lo puede emitir");
							}
							if(dias<=30&&fechaVigencia.after(fechaActual)&&fechaVigencia.after(aux)){
								aux=fechaVigencia;
								fechaInicioVigencia=sdf2.format(aux);
								mensaje="El vehiculo de placa "+placasArr[i]+" se encuentra en una poliza vigente hasta el "+fechaInicioVigencia+"(yyyy-mm-dd), el inicio de la vigencia sera a partir de esta fecha";
								
							}
							if(dias>30&&fechaVigencia.after(fechaActual)&&(si==null||si.getId()==null)){
								throw new Exception("El vehiculo necesita tener una inspeccion!");
							}
						}

						
					}
					
				}
				result.put("fechaInicioVigencia", fechaInicioVigencia);
				result.put("mensaje", mensaje);
				
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
	
	public JSONObject cargarParametroWS(String xmltemp) {
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

		vehiculoJSONObject.put("codigo", codigo.trim());
		vehiculoJSONObject.put("anio", anio.trim());
		vehiculoJSONObject.put("chasis", chasis.trim());
		vehiculoJSONObject.put("motor", motor.trim());
		vehiculoJSONObject.put("placas", placas.trim());
		vehiculoJSONObject.put("marca", marca.getId());
		vehiculoJSONObject.put("modelo", modelo.getId());
		vehiculoJSONObject.put("color", color.trim());
		vehiculoJSONObject.put("dispositivo", dispositivo.trim());
		vehiculoJSONObject.put("sucursal", sucursal.trim());
		vehiculoJSONObject.put("valor", valor.trim());
		vehiculoJSONObject.put("usoVehiculo", usoVehiculo.trim());
		vehiculoJSONObject.put("tipoVehiculo", tipoVehiculo.trim());
		vehiculoJSONObject.put("agenteId", agenteId);
		vehiculoJSONObject.put("entidadAgenteId", entidadAgenteId);
		vehiculoJSONObject.put("vigencia", vigencia.trim());
		vehiculoJSONObject.put("tasa", tasa.trim());

		return vehiculoJSONObject;

	}

	public JSONObject consultarANT(String placa) throws Exception{
		JSONObject result=new JSONObject();
		
		
		  final WebClient webClient = new WebClient();

			  // Get the first page
		  final HtmlPage page1 = webClient.getPage("http://www.policiaecuador.gob.ec/aplicaciones/consultasws/informacionvehicular.php");

		  // Get the form that we are dealing with and within that form, 
		  // find the submit button and the field that we want to change.
		  final HtmlForm form = page1.getFormByName("form1");
		  final HtmlSubmitInput button = form.getInputByName("buscar");
		  final HtmlTextInput textField = form.getInputByName("placa");

		  // Change the value of the text field
		  textField.setValueAttribute(placa);

		  // Now submit the form by clicking the button and get back the second page.
		  final HtmlPage page2 = button.click();
		  String asText = page2.asText().toLowerCase();
		  webClient.closeAllWindows();	
		  //System.out.print(asText.length());
		  if(asText.toLowerCase().contains("servicio no disponible"))
			  return result;
		  String infAuto = asText.replaceAll("\n", ":");
		  String [] arrInfAuto = infAuto.split(":");

		  Color colorEnsurance = new Color();
		  ColorDAO colorDAO =  new ColorDAO();
		  
		  Marca marcaEnsurance = new Marca();
		  MarcaDAO marcaDAO = new MarcaDAO();
		  
		  Modelo modeloEnsurance = new Modelo();
		  ModeloDAO modeloDAO = new ModeloDAO();
		  String modelo="";
		  String marca="";
		  String placaValor="";
		  String chasis="";
		  String motor="";
		  String tipo="";
		  String clase="";
		  String anio="";
		  String color="";
		  int cont=0;
		  
		  for(String s:arrInfAuto){
			  if(s.trim().equals("chasis")&&arrInfAuto[cont+1]!=null)
				  chasis=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("modelo")&&arrInfAuto[cont+1]!=null)
				  modelo=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("marca")&&arrInfAuto[cont+1]!=null)
				  marca=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("placa")&&arrInfAuto[cont+1]!=null)
				  placaValor=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("motor")&&arrInfAuto[cont+1]!=null)
				  motor=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("tipo")&&arrInfAuto[cont+1]!=null)
				  tipo=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("clase")&&arrInfAuto[cont+1]!=null)
				  clase=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("a�o")&&arrInfAuto[cont+1]!=null)
				  anio=arrInfAuto[cont+1].trim();
			  if(s.trim().equals("color")&&arrInfAuto[cont+1]!=null)
				  color=arrInfAuto[cont+1].trim();
			  cont++;
		  }
		  			
		  if(placaValor.equals(""))
				  return result;
		 String valorColor=" ";
		  marcaEnsurance = marcaDAO.buscarPorNombre(marca);
		  if(color.length()>0){
			  colorEnsurance = colorDAO.buscarPorNombre(color);
			  valorColor = colorEnsurance.getId();
		  }
		  
		  modeloEnsurance = modeloDAO.buscarPorMarcaYNombre(marcaEnsurance, modelo);
		  result.put("placa", placa.trim().toLowerCase());
		  result.put("chasis", chasis.trim().toLowerCase());
		  result.put("motor", motor.trim().toLowerCase());
		  if(marcaEnsurance.getId()!=null){
			  result.put("marcaEnsurance", marcaEnsurance.getId().toLowerCase());
			  result.put("marca", marcaEnsurance.getNombre().toLowerCase());
		  }
		  else{
				result.put("marcaEnsurance", -1);
				result.put("marca", marca);
			}
		    
			  
		  result.put("tipo", tipo.trim().toLowerCase());
		  result.put("clase", clase.trim().toLowerCase());
		  if(valorColor!=null)
			  result.put("color", valorColor.trim().toLowerCase());
		  else
			  result.put("color", "");
		  result.put("anioFabricacion", anio.trim().toLowerCase());
			if (modeloEnsurance.getId() != null) {
				result.put("modeloEnsurance", modeloEnsurance.getId().toLowerCase());
				result.put("modelo", modeloEnsurance.getNombre().toLowerCase());
			}
			else{
				result.put("modeloEnsurance", -1);
				result.put("modelo", modelo);
			}
		    
		    String cotizacionId="";
		  
		  CotizacionDetalleDAO cdDAO = new CotizacionDetalleDAO(); 
		  ObjetoVehiculoDAO ovDAO= new ObjetoVehiculoDAO();
		  ObjetoVehiculo ov=ovDAO.buscarPorPlaca(placa.toUpperCase());
		  List<CotizacionDetalle>  cd=new ArrayList<CotizacionDetalle>();
		  
		  if(ov.getId()!=null)
			   cd=cdDAO.buscarCotizacionesDetallePorObjetoId(ov.getId());
		  
		  for(int i=0;i<cd.size();i++){
		  if(cd.get(i).getId()!=null)
			  if(cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Borrador")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente de Emitir"))
				  cotizacionId=cd.get(i).getCotizacion().getId();
		  }
		  
		  
		  result.put("cotizacionId", cotizacionId);


			WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
			String resultado = webService.getWebServiceCotizadorImplPort().obtenerDatosVehiculo( chasis.trim().toUpperCase(), "chasis");
			JSONArray jsonArray = new JSONArray();
			Utilitarios util = new Utilitarios();
			jsonArray.add(util.cargarParametroWS(resultado));
			result.put("codigoEnsurance", jsonArray.getJSONObject(0).get("codigo"));
			if(jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("POL")||jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("INC"))
				result.put("vigenciaEnsurance", jsonArray.getJSONObject(0).get("vigencia"));
			else
				result.put("vigenciaEnsurance", "");

		
		return result;
	}

	
	public JSONObject consultarPlacaSRI(String placa) throws Exception{
		JSONObject result=new JSONObject();
		
		int longitudMinima = 0;

			final WebClient webClient = new WebClient();

			// Get the first page
			final HtmlPage page1 = webClient.getPage("https://declaraciones.sri.gob.ec/mat-vehicular-internet/reportes/general/valoresAPagar.jsp");

			// Get the form that we are dealing with and within that
			// form,
			// find the submit button and the field that we want to
			// change.
			//final HtmlElement form = page1;
			final HtmlTextInput textField = (HtmlTextInput) page1.getElementByName("placaCamv");
			final HtmlSubmitInput button = (HtmlSubmitInput) page1.getElementByName("btnBuscar");

			// Change the value of the text field
			textField.setValueAttribute(placa);

			// Now submit the form by clicking the button and get back
			// the second page.
			final HtmlPage page2 = button.click();
			String asText = page2.asText().toLowerCase();

			if (!asText.contains("datos del veh�culo")||asText.contains("no existe el veh�culo con placa, camv o cpn")) {
				return result;
			}
			else {
				String informacionEncontrada = asText.substring(asText.indexOf("datos del veh�culo")).replaceAll("\r\n",":");
				String infAuto = informacionEncontrada.replaceAll("\n", ":").replaceAll("\t", "").replaceAll("  ", ":");
				String [] arrInfAuto = infAuto.split(":");
				  Color colorEnsurance = new Color();
				  ColorDAO colorDAO =  new ColorDAO();
				  
				  Marca marcaEnsurance = new Marca();
				  MarcaDAO marcaDAO = new MarcaDAO();
				  
				  Modelo modeloEnsurance = new Modelo();
				  ModeloDAO modeloDAO = new ModeloDAO();
				  String modelo="";
				  String marca="";
				  String placaValor="";
				  String chasis="";
				  String motor="";
				  String tipo="";
				  String clase="";
				  String anio="";
				  String color="";
				  String cpn="";
				  int cont=0;
				  
				  for(String s:arrInfAuto){
					  if(s.trim().equals("chasis")&&arrInfAuto[cont+1]!=null)
						  chasis=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("modelo")&&arrInfAuto[cont+1]!=null)
						  modelo=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("marca")&&arrInfAuto[cont+1]!=null)
						  marca=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("placa")&&arrInfAuto[cont+1]!=null)
						  placaValor=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("motor")&&arrInfAuto[cont+1]!=null)
						  motor=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("tipo")&&arrInfAuto[cont+1]!=null)
						  tipo=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("clase")&&arrInfAuto[cont+1]!=null)
						  clase=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("a�o")&&arrInfAuto[cont+1]!=null)
						  anio=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("color 1")&&arrInfAuto[cont+1]!=null)
						  color=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("camv o cpn")&&arrInfAuto[cont+1]!=null)
						  cpn=arrInfAuto[cont+1].trim();
					  cont++;
				  }
				
				webClient.closeAllWindows();

				marcaEnsurance = marcaDAO.buscarPorNombre(marca);

				modeloEnsurance = modeloDAO.buscarPorMarcaYNombre( marcaEnsurance, modelo);
				result.put("placa", placa.trim().toLowerCase());
				result.put("chasis", chasis.trim().toLowerCase());
				result.put("marcaEnsurance", marcaEnsurance.getId().toLowerCase());
				result.put("marca", marcaEnsurance.getNombre().toLowerCase());
				result.put("anioFabricacion", anio.trim().toLowerCase());
				result.put("color", color.trim().toLowerCase());
				if (modeloEnsurance.getId() != null) {
					result.put("modeloEnsurance", modeloEnsurance.getId().toLowerCase());
					result.put("modelo", modeloEnsurance.getNombre().toLowerCase());
				}
				else {
					result.put("modeloEnsurance", -1);
					result.put("modelo", modelo);
				}

				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				String resultado = webService.getWebServiceCotizadorImplPort().obtenerDatosVehiculo( placa, "placa");
				JSONArray jsonArray = new JSONArray();
				Utilitarios util = new Utilitarios();
				jsonArray.add(util.cargarParametroWS(resultado));
				result.put("codigoEnsurance", jsonArray.getJSONObject(0).get("codigo"));
				if(jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("POL")||jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("INC"))
					result.put("vigenciaEnsurance", jsonArray.getJSONObject(0).get("vigencia"));
				else
					result.put("vigenciaEnsurance", "");
			}
		  String cotizacionId="";
			  
		  CotizacionDetalleDAO cdDAO = new CotizacionDetalleDAO(); 
		  ObjetoVehiculoDAO ovDAO= new ObjetoVehiculoDAO();
		  ObjetoVehiculo ov=ovDAO.buscarPorPlaca(placa.toUpperCase());
		  List<CotizacionDetalle>  cd=new ArrayList<CotizacionDetalle>();
		  
		  if(ov.getId()!=null)
			   cd=cdDAO.buscarCotizacionesDetallePorObjetoId(ov.getId());
		  
		  for(int i=0;i<cd.size();i++){
		  if(cd.get(i).getId()!=null)
			  if(cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Borrador")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente de Emitir"))
				  cotizacionId=cd.get(i).getCotizacion().getId();
		  }
		  
		  
		  result.put("cotizacionId", cotizacionId);


		return result;
	}
	
	public JSONObject consultarCorpaire(String valor, String parametro) throws Exception{
		JSONObject result=new JSONObject();
		

		String valorSelect = "";
		int longitudMinima = 0;

		if (parametro.equals("placa")) {
			valorSelect = "0";
			longitudMinima = 5;
		}
		if (parametro.equals("chasis")) {
			valorSelect = "1";
			longitudMinima = 7;
		}
		if (parametro.equals("cpn")) {
			valorSelect = "2";
			longitudMinima = 5;
		}
		if (parametro.equals("placaAnterior")) {
			valorSelect = "3";
			longitudMinima = 5;
		}

		if (valor.length() > longitudMinima) {
			final WebClient webClient = new WebClient();

			// Get the first page
			final HtmlPage page1 = webClient.getPage("http://186.42.161.195/appSIMUtilesSite/rtv_onLine/new_Buscar.jsp");

			// Get the form that we are dealing with and within that
			// form,
			// find the submit button and the field that we want to
			// change.
			final HtmlForm form = page1.getFormByName("frm_Datos");
			final HtmlSelect select = form.getSelectByName("cbo_tipo");
			final HtmlTextInput textField = form.getInputByName("txt_dato");
			final HtmlSubmitInput button = form.getInputByName("btn_ingresar");

			// Change the value of the text field
			textField.setValueAttribute(valor);

			HtmlOption option1 = select.getOptionByValue(valorSelect);
			select.setSelectedAttribute(option1, true);

			// Now submit the form by clicking the button and get back
			// the second page.
			final HtmlPage page2 = button.click();
			String asText = page2.asText();

			if (asText.contains("� Inicio")) {
				return result;
			}
			else {
				String[] informacionEncontrada = asText.split("\r\n");

				String arregloPlaca[] = informacionEncontrada[3].split("\t");
				String arregloChasis[] = informacionEncontrada[4].split("\t");
				String arregloMarca[] = informacionEncontrada[5].split("\t");
				String arregloModelo[] = informacionEncontrada[6].split("\t");

				// Obtenemos los valores de placa, chasis, marca y
				// modelo

				String placaEncontrada = "";
				String chasisEncontrado = "";
				String marcaEncontrada = "";
				String motorEncontrado = "";
				String modeloEncontrado = "";
				String anoEncontrado = "";
				String cpnEncontrado = "";

				placaEncontrada = arregloPlaca[1].trim();
				chasisEncontrado = arregloChasis[1].trim();
				cpnEncontrado = arregloChasis[3].trim();
				marcaEncontrada = arregloMarca[1].trim();
				motorEncontrado = arregloMarca[3].trim();
				modeloEncontrado = arregloModelo[1].trim();
				anoEncontrado = arregloModelo[3].trim();

				webClient.closeAllWindows();

				Marca marcaEnsurance = new Marca();
				MarcaDAO marcaDAO = new MarcaDAO();

				Modelo modeloEnsurance = new Modelo();
				ModeloDAO modeloDAO = new ModeloDAO();

				marcaEnsurance = marcaDAO.buscarPorNombre(marcaEncontrada);

				modeloEnsurance = modeloDAO.buscarPorMarcaYNombre( marcaEnsurance, modeloEncontrado);
				result.put("placa", placaEncontrada);
				result.put("chasis", chasisEncontrado);
				result.put("motor", motorEncontrado);
				result.put("marcaEnsurance", marcaEnsurance.getId());
				result.put("marca", marcaEnsurance.getNombre());
				result.put("anioFabricacion", anoEncontrado);
				result.put("cpn", cpnEncontrado);
				if (modeloEnsurance.getId() != null) {
					result.put("modeloEnsurance", modeloEnsurance.getId());
					result.put("modelo", modeloEnsurance.getNombre());
				}
				else {
					result.put("modeloEnsurance", -1);
					result.put("modelo", modeloEncontrado);
				}
				if (chasisEncontrado != null && chasisEncontrado.equals("")) {
					valor = chasisEncontrado;
					parametro = "chasis";
				}
				
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				String resultado = webService.getWebServiceCotizadorImplPort().obtenerDatosVehiculo(valor, parametro);
				JSONArray jsonArray = new JSONArray();
				Utilitarios util = new Utilitarios();
				jsonArray.add(util.cargarParametroWS(resultado));
				result.put("codigoEnsurance", jsonArray.getJSONObject(0).get("codigo"));
				if(jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("POL")||jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("INC"))
					result.put("vigenciaEnsurance", jsonArray.getJSONObject(0).get("vigencia"));
				else
					result.put("vigenciaEnsurance", "");
			}
		}

		return result;
	}
	
	public JSONObject consultarChasisSRI(String chasis) throws Exception{
		JSONObject result=new JSONObject();
		
		int longitudMinima = 0;

			final WebClient webClient = new WebClient();

			// Get the first page
			final HtmlPage page1 = webClient.getPage("https://declaraciones.sri.gob.ec/mat-vehicular-internet/reportes/general/reporteVehiculosChasis.jsp");

			// Get the form that we are dealing with and within that
			// form,
			// find the submit button and the field that we want to
			// change.
			//final HtmlElement form = page1;
			final HtmlTextInput textField = (HtmlTextInput) page1.getElementByName("chasis");
			final HtmlSubmitInput button = (HtmlSubmitInput) page1.getElementByName("btnBuscar");

			// Change the value of the text field
			textField.setValueAttribute(chasis);

			// Now submit the form by clicking the button and get back
			// the second page.
			final HtmlPage page2 = button.click();
			String asText = "";
			if(page2!=null)
			asText = page2.asText().toLowerCase();

			if (!asText.contains("datos del veh�culo")||asText.contains("no existe el veh�culo con placa, camv o cpn")) {
				return result;
			}
			else {
				String informacionEncontrada = asText.substring(asText.indexOf("datos del veh�culo")).replaceAll("\r\n",":");
				String infAuto = informacionEncontrada.replaceAll("\n", ":").replaceAll("\t", "").replaceAll("  ", ":");
				String [] arrInfAuto = infAuto.split(":");
				  Color colorEnsurance = new Color();
				  ColorDAO colorDAO =  new ColorDAO();
				  
				  Marca marcaEnsurance = new Marca();
				  MarcaDAO marcaDAO = new MarcaDAO();
				  
				  Modelo modeloEnsurance = new Modelo();
				  ModeloDAO modeloDAO = new ModeloDAO();
				  String modelo="";
				  String marca="";
				  String placaValor="";
				  String chasisValor="";
				  String motor="";
				  String tipo="";
				  String clase="";
				  String anio="";
				  String color="";
				  String cpn="";
				  int cont=0;
				  
				  for(String s:arrInfAuto){
					  if(s.trim().equals("chasis")&&arrInfAuto[cont+1]!=null)
						  chasisValor=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("modelo")&&arrInfAuto[cont+1]!=null)
						  modelo=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("marca")&&arrInfAuto[cont+1]!=null)
						  marca=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("placa")&&arrInfAuto[cont+1]!=null)
						  placaValor=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("motor")&&arrInfAuto[cont+1]!=null)
						  motor=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("tipo")&&arrInfAuto[cont+1]!=null)
						  tipo=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("clase")&&arrInfAuto[cont+1]!=null)
						  clase=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("a�o")&&arrInfAuto[cont+1]!=null)
						  anio=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("color 1")&&arrInfAuto[cont+1]!=null)
						  color=arrInfAuto[cont+1].trim();
					  if(s.trim().equals("camv o cpn")&&arrInfAuto[cont+1]!=null)
						  cpn=arrInfAuto[cont+1].trim();
					  cont++;
				  }
				
				webClient.closeAllWindows();

				marcaEnsurance = marcaDAO.buscarPorNombre(marca);
				
				if(marcaEnsurance.getId()!=null){
				modeloEnsurance = modeloDAO.buscarPorMarcaYNombre( marcaEnsurance, modelo);
				result.put("placa", placaValor.trim().toLowerCase());
				result.put("chasis", chasis.trim().toLowerCase());
				result.put("marcaEnsurance", marcaEnsurance.getId().toLowerCase());
				result.put("marca", marcaEnsurance.getNombre().toLowerCase());
				result.put("anioFabricacion", anio.trim().toLowerCase());
				result.put("color", color.trim().toLowerCase());
				if (modeloEnsurance.getId() != null) {
					result.put("modeloEnsurance", modeloEnsurance.getId().toLowerCase());
					result.put("modelo", modeloEnsurance.getNombre().toLowerCase());
				}
				else {
					result.put("modeloEnsurance", -1);
					result.put("modelo", modelo);
				}
				}
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				String resultado = webService.getWebServiceCotizadorImplPort().obtenerDatosVehiculo( chasis, "chasis");
				JSONArray jsonArray = new JSONArray();
				Utilitarios util = new Utilitarios();
				jsonArray.add(util.cargarParametroWS(resultado));
				result.put("codigoEnsurance", jsonArray.getJSONObject(0).get("codigo"));
				if(jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("POL")||jsonArray.getJSONObject(0).get("tipoEndoso").toString().equals("INC"))
					result.put("vigenciaEnsurance", jsonArray.getJSONObject(0).get("vigencia"));
				else
					result.put("vigenciaEnsurance", "");
			}
		  String cotizacionId="";
			  
		  CotizacionDetalleDAO cdDAO = new CotizacionDetalleDAO(); 
		  ObjetoVehiculoDAO ovDAO= new ObjetoVehiculoDAO();
		  ObjetoVehiculo ov=ovDAO.buscarPorChasis(chasis.toUpperCase());
		  List<CotizacionDetalle>  cd=new ArrayList<CotizacionDetalle>();
		  
		  if(ov.getId()!=null)
			   cd=cdDAO.buscarCotizacionesDetallePorObjetoId(ov.getId());
		  
		  for(int i=0;i<cd.size();i++){
		  if(cd.get(i).getId()!=null)
			  if(cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Borrador")||cd.get(i).getCotizacion().getEstado().getNombre().equals("Pendiente de Emitir"))
				  cotizacionId=cd.get(i).getCotizacion().getId();
		  }
		  
		  
		  result.put("cotizacionId", cotizacionId);


		return result;
		}

		public ObjetoVehiculo agregarVehiculoCotizacion(HttpServletRequest request){
		String codigoVehiculoEnsurance = request.getParameter("codigoVehiculoEnsurance")  == null ? "" : request.getParameter("codigoVehiculoEnsurance");
		String placa = request.getParameter("placa")  == null ? "" : request.getParameter("placa").trim();
		String chasis = request.getParameter("chasis")  == null ? "" : request.getParameter("chasis").trim();
		String motor = request.getParameter("motor")  == null ? "" : request.getParameter("motor").trim();
		String vehiculoId = request.getParameter("vehiculoId")  == null ? "" : request.getParameter("vehiculoId");
		String modeloId = request.getParameter("modelo") == null ? "" : request.getParameter("modelo");
		String sucursalId = request.getParameter("sucursal_id") == null ? "" : request.getParameter("sucursal_id");
		String anioFabricacion = request.getParameter("anio_fabricacion") == null ? "" : request.getParameter("anio_fabricacion");
		String sumaAsegurada = request.getParameter("suma_asegurada_valor") == null ? "" : request.getParameter("suma_asegurada_valor");
		String sumaAseguradaArr = request.getParameter("suma_asegurada_valor_arr") == null ? "" : request.getParameter("suma_asegurada_valor_arr");
		String conDispositivoRastreo = request.getParameter("disposito_rastreo") == null ? "" : request.getParameter("disposito_rastreo");
		String esCeroKilometro = request.getParameter("cero_kilometros") == null ? "" : request.getParameter("cero_kilometros");
		String antiguedadVh = request.getParameter("antiguedad_vh") == null ? "" : request.getParameter("antiguedad_vh");
		String conductorEdad = request.getParameter("conductor_edad") == null ? "" : request.getParameter("conductor_edad");
		String conductorGenero = request.getParameter("conductor_genero") == null ? "" : request.getParameter("conductor_genero");
		String conductorEstadoCivil = request.getParameter("conductor_estado_civil") == null ? "" : request.getParameter("conductor_estado_civil");
		String numeroHijos = request.getParameter("numero_hijos") == null ? "" : request.getParameter("numero_hijos");
		String kmRecorridos = request.getParameter("km_recorridos") == null ? "" : request.getParameter("km_recorridos");
		String combustible = request.getParameter("combustible") == null ? "" : request.getParameter("combustible");
		String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
		String coberturasAdicionalesStr = request.getParameter("coberturasAdicionales");
		String colorId = request.getParameter("color") == null ? "" : request.getParameter("color");
		String tipoAdquisicionId = request.getParameter("codigoTipoAdquisicion") == null ? "" : request.getParameter("codigoTipoAdquisicion");
		String tonelaje = request.getParameter("tonelaje") == null ? "" : request.getParameter("tonelaje");
		String necesitaInspeccion = request.getParameter("necesitaInspeccion") == null ? "" : request.getParameter("necesitaInspeccion");
		String tasaVehiculosCerrados = request.getParameter("tasaVehiculosCerrados") == null ? "" : request.getParameter("tasaVehiculosCerrados");
		String valorExcesoRC = request.getParameter("valorExcesoRC") == null ? "0" : request.getParameter("valorExcesoRC");
		String valoresExtras = request.getParameter("valoresExtras") == null ? "" : request.getParameter("valoresExtras");
		String producto = request.getParameter("producto") == null ? "" : request.getParameter("producto");
		String listaExtrasIds = request.getParameter("listaExtrasIds") == null ? "" : request.getParameter("listaExtrasIds");
		String listaExtrasDetalles = request.getParameter("listaExtrasDetalles") == null ? "" : request.getParameter("listaExtrasDetalles");
		String listaExtrasValores = request.getParameter("listaExtrasValores") == null ? "" : request.getParameter("listaExtrasValores");
		String codigoTipoUso = request.getParameter("codigoTipoUso") == null ? "" : request.getParameter("codigoTipoUso").trim();
		String codigoTipoVehiculo = request.getParameter("codigoTipoVehiculo") == null ? "" : request.getParameter("codigoTipoVehiculo").trim();
		String tipoObjeto = request.getParameter("tipoObjeto") == null ? "" : request.getParameter("tipoObjeto").trim();
		
		ObjetoVehiculoTransaction objetoVehiculoTransaction = new ObjetoVehiculoTransaction();

		ModeloDAO modeloDAO = new ModeloDAO();				
		Modelo modelo = modeloDAO.buscarPorId(modeloId);
		
		ColorDAO colorDAO = new ColorDAO();				
		Color color = colorDAO.buscarPorId(colorId);
		
		TipoVehiculoDAO tipoVehiculoDAO = new TipoVehiculoDAO();
		TipoVehiculo tipoVehiculo = null;
		
		if(!codigoTipoVehiculo.equals(""))
			tipoVehiculo= tipoVehiculoDAO.buscarPorId(codigoTipoVehiculo);

		TipoUsoDAO tipoUsoDAO = new TipoUsoDAO();
		TipoUso tipoUso = tipoUsoDAO.buscarPorId(codigoTipoUso); 				
		
		SucursalDAO sucursalDAO = new SucursalDAO();
		Sucursal sucursal = sucursalDAO.buscarPorId(sucursalId);
		
		ObjetoVehiculoDAO objetoVehiculoDAO = new ObjetoVehiculoDAO();
		ObjetoVehiculo vehiculo = new ObjetoVehiculo();
		
		if(!placa.equals("") && vehiculoId.equals(""))
			vehiculo = objetoVehiculoDAO.buscarPorPlaca(placa);
		else if(!chasis.equals("") && vehiculoId.equals(""))
			vehiculo = objetoVehiculoDAO.buscarPorChasis(chasis);	

		if(vehiculoId!="")
			vehiculo=objetoVehiculoDAO.buscarPorId(vehiculoId);
		
		vehiculo.setCodigoEnsurance(codigoVehiculoEnsurance);
		vehiculo.setModelo(modelo);
		vehiculo.setColor(color);
		vehiculo.setTipoVehiculo(tipoVehiculo);
		vehiculo.setMotor(motor.toUpperCase());
		vehiculo.setChasis(chasis.toUpperCase());
		vehiculo.setPlaca(placa.toUpperCase());
		vehiculo.setAnioFabricacion(anioFabricacion);
		vehiculo.setAntiguedadVh(antiguedadVh);
		vehiculo.setConductorEdad(conductorEdad);
			vehiculo.setConductorGenero(conductorGenero);
		vehiculo.setConductorEstadoCivil(conductorEstadoCivil);
		vehiculo.setNumeroHijos(numeroHijos);
		vehiculo.setZona(request.getParameter("zona"));
		vehiculo.setKilometrosRecorridos(kmRecorridos);
		vehiculo.setCombustible(combustible);
		vehiculo.setTipoAdquisicion(tipoAdquisicionId);
		vehiculo.setSucursalId(sucursal.getId());
		vehiculo.setTipoUso(tipoUso);
		if(tonelaje!=""){
			Double tonelajeValor = Double.parseDouble(tonelaje);
			vehiculo.setTonelajeVehiculo(tonelajeValor);	
		}
		
		Boolean valorRastreo = false;
		if (conDispositivoRastreo.equalsIgnoreCase("1"))
			valorRastreo = true;						
		vehiculo.setDispositivoRastreo(valorRastreo);
		
		Boolean valorCeroKilometros = false;
		if (esCeroKilometro.equalsIgnoreCase("1"))
			valorCeroKilometros = true;
		vehiculo.setCeroKilometros(valorCeroKilometros);
						
		if(sumaAsegurada.isEmpty()){
			sumaAsegurada = "0";
		}
		Double sumaAseguradaValor = Double.parseDouble(sumaAsegurada);
		vehiculo.setSumaAsegurada(sumaAseguradaValor);
		
		// Obtener los numeros de reclamos pagados por medio de la placa
		//WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
		//int anoSinSiniestro = webService.getWebServiceCotizadorImplPort().obtenerNumeroReclamosPorPlaca(request.getParameter("placa"));
		vehiculo.setAnosSin_Siniestro(String.valueOf("0"));

		Boolean valorGuardaGarage = false;
		if (request.getParameter("guarda_garage").equalsIgnoreCase("1"))
			valorGuardaGarage = true;		
		vehiculo.setGuardaGarage(valorGuardaGarage);

		boolean vehiculoExistente = false;
		if(vehiculo.getId() != null && vehiculo.getId() != "")
			vehiculoExistente = true;
						
		if(vehiculoExistente)
			vehiculo=objetoVehiculoTransaction.editar(vehiculo);
		else
			vehiculo=objetoVehiculoTransaction.crear(vehiculo);

		
		return vehiculo;
		
	}
	
	public CotizacionDetalle agregarCotizacionDetalle(ObjetoVehiculo ov, Cotizacion cotizacion, TipoObjeto tipoObjeto, double sumaExtras){
		CotizacionDetalle cd= new CotizacionDetalle();
		CotizacionDetalleDAO cdDAO=new CotizacionDetalleDAO();
		
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		cd=cdDAO.buscarCotizacionDetalleIdYObjetoId(ov.getId(), cotizacion);
		cd.setObjetoId(ov.getId());
		cd.setValorExtras(sumaExtras);
		cd.setSumaAseguradaItem(ov.getSumaAsegurada()+sumaExtras);
		cd.setCotizacion(cotizacion);
		cd.setTipoObjetoId(tipoObjeto.getId());
		if(cd.getId()==null)
			cotizacionDetalleTransaction.crear(cd);
		else
			cotizacionDetalleTransaction.editar(cd);
		
		
		return cd;
	}

	public double crearExtrasPorVehiculo(ObjetoVehiculo ov, String[] arrListaExtrasIds, String[] arrListaExtrasDetalles, String[] arrListaExtrasValores){
		
		//double totalExtras=0.0;
		TipoExtraDAO tipoExtraDAO = new TipoExtraDAO();
		Extra extra = new Extra();
		ExtraDAO extraDAO = new ExtraDAO();
		
		ExtraTransaction extraTransaction= new ExtraTransaction();
		
		//eliminar extras antiguos
		List<Extra> extrasAntiguos=extraDAO.buscarPorObjetoVehiculo(ov);
		if(extrasAntiguos!=null&&extrasAntiguos.size()>0){
			for(int i=0;i<extrasAntiguos.size();i++){
				extraTransaction.eliminar(extrasAntiguos.get(i));
			}
		}
		
		double sumaExtras=0.0;
		
		//se agregan extras nuevos
		for(int i=1; i< arrListaExtrasIds.length; i++){

			Double valorExtra=Double.parseDouble(arrListaExtrasValores[i]);
			extra.setTipoExtra(tipoExtraDAO.buscarPorId(arrListaExtrasIds[i]));
			extra.setObjetoVehiculo(ov);
			extra.setDescripcion(arrListaExtrasDetalles[i]);
			extra.setValorAsegurado(valorExtra);
			sumaExtras+=valorExtra;

			extraTransaction.crear(extra);
		}
		return sumaExtras;
	}

	
	public JSONObject calcularPrimaCoberturasPrincipalesVehiculosDinamicos(Cotizacion cotizacion, String tarificadorLocal, String coberturaTR, String coberturaDT, String coberturaRC, ObjetoVehiculo ov, String montoFijo, String valorSiniestro, String porcentajeSumaAsegurada, double valorVehiculoTodosAnios){
		JSONObject retorno = new JSONObject();
		
		double comision=cotizacion.getPorcentajeComision();
		double valorPrimaPuraRT = 0.0;
		double valorPrimaPuraCHT = 0.0;
		double valorPrimaPuraRC = 0.0;
		double valorPrimaPuraDP = 0.0;
		double primaTasa = 0.0;
		double totalTodoRisgo=0.0;
		double totalDanioTotal=0.0;
		double totalResponsabilidadCivil=0.0;
		double sumaAseguradaValor=ov.getSumaAsegurada();
		double primaNoAfectaMonto=0.0;
		int dispositivoRastreo=ov.getDispositivoRastreo()?1:0;
		SucursalDAO sucursalDAO=new SucursalDAO();
		Sucursal sucursal=sucursalDAO.buscarPorId(ov.getSucursalId());
		Modelo modelo=ov.getModelo();
		int anioFabricacion = Integer.parseInt(ov.getAnioFabricacion());
			
		if(tarificadorLocal.equalsIgnoreCase("1")){
			if(coberturaTR.equalsIgnoreCase("true")){
				valorPrimaPuraRT = MotorTarifador.calcularPrimaRoboTotal(sumaAseguradaValor, sucursal, anioFabricacion, dispositivoRastreo, modelo);
				valorPrimaPuraCHT = MotorTarifador.calcularPrimaChoqueTotal(sumaAseguradaValor, sucursal, modelo);
				valorPrimaPuraRC = MotorTarifador.calcularPrimaResponsabilidadCivil(sumaAseguradaValor, sucursal,(anioFabricacion), modelo);
				valorPrimaPuraDP = MotorTarifador.calcularPrimaDanoParcial(sumaAseguradaValor, sucursal, (anioFabricacion), modelo,Double.parseDouble(montoFijo),Double.parseDouble(valorSiniestro),Double.parseDouble(porcentajeSumaAsegurada));
				totalTodoRisgo += MotorTarifador.calcularPrimaTasaTodoRiesgo(valorPrimaPuraRT, valorPrimaPuraCHT, valorPrimaPuraRC, valorPrimaPuraDP, comision, sumaAseguradaValor);
				primaTasa+=totalTodoRisgo;
			}
			
			if(coberturaDT.equalsIgnoreCase("true")&&coberturaRC.equalsIgnoreCase("false")){					
				valorPrimaPuraRT = MotorTarifador.calcularPrimaRoboTotal(sumaAseguradaValor, sucursal, (anioFabricacion), dispositivoRastreo, modelo);
				valorPrimaPuraCHT = MotorTarifador.calcularPrimaChoqueTotal(sumaAseguradaValor, sucursal, modelo);
				totalDanioTotal += MotorTarifador.calcularPrimaTasaDanoTotal(valorPrimaPuraRT, valorPrimaPuraCHT, comision, (sumaAseguradaValor));
				primaTasa+=totalDanioTotal;
				
			}
			
			if(coberturaRC.equalsIgnoreCase("true")&&coberturaDT.equalsIgnoreCase("false")){
				valorPrimaPuraRC = MotorTarifador.calcularPrimaResponsabilidadCivil(sumaAseguradaValor, sucursal, anioFabricacion, modelo);
				totalResponsabilidadCivil += MotorTarifador.calcularPrimaTasaResponsabilidadCivil(valorPrimaPuraRC, comision, sumaAseguradaValor);
				primaTasa+=totalResponsabilidadCivil;
			}
			
			if (coberturaDT.equalsIgnoreCase("true") && coberturaRC.equalsIgnoreCase("true")){
				valorPrimaPuraRT = MotorTarifador.calcularPrimaRoboTotal(sumaAseguradaValor, sucursal, (anioFabricacion), dispositivoRastreo, modelo);
				valorPrimaPuraCHT = MotorTarifador.calcularPrimaChoqueTotal(sumaAseguradaValor, sucursal, modelo);
				totalDanioTotal += MotorTarifador.calcularPrimaTasaDanoTotal(valorPrimaPuraRT, valorPrimaPuraCHT, comision, sumaAseguradaValor);
				
				valorPrimaPuraRC = MotorTarifador.calcularPrimaResponsabilidadCivil(sumaAseguradaValor, sucursal, anioFabricacion, modelo);
				totalResponsabilidadCivil += MotorTarifador.calcularPrimaTasaResponsabilidadCivil(valorPrimaPuraRC, comision, sumaAseguradaValor);
				primaNoAfectaMonto+=totalResponsabilidadCivil;
				primaTasa+=totalDanioTotal;
			}
		}else{
			
			List<ObjetoVehiculo> listadoVehiculos = new ArrayList<ObjetoVehiculo>();
			listadoVehiculos.add(ov);
			String xml = Utilitarios.generarEstructuraXMLVH(cotizacion, listadoVehiculos);
			ManejoColas.productorMensajes(xml);
			System.out.println(xml);
			ManejoColas.consumiMensajes();
			ManejoFTP.subirXMLFTP(xml, cotizacion.getId());
		}
		
		
		retorno.put("primaAfectaMonto", primaTasa);
		retorno.put("primaNoAfectaMonto", primaNoAfectaMonto);
		retorno.put("totalResponsabilidadCivil", totalResponsabilidadCivil);
		retorno.put("totalDanioTotal", totalDanioTotal);
		retorno.put("totalTodoRisgo", totalTodoRisgo);
		
		return retorno;
	}
	
	
	public double guardarCotizacionCoberturaPrincipales(CotizacionDetalle cd, double porcentajeSumaAsegurada, double montoFijo, double valorSiniestro, double totalResponsabilidadCivil, double totalDanioTotal, double totalTodoRisgo, double porcentajeConDescuento, double valorVehiculoTodosAnios){
		double totalPrimaDetalle=0.0;	
		CoberturaDAO cDAO=new CoberturaDAO();
		Cobertura todoRiesgo=new Cobertura();
		Cobertura perdidaTotal=new Cobertura();
		Cobertura responsabilidadCivil=new Cobertura();
		CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
		
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		double sumaAseguradaSinExtras=cd.getSumaAseguradaItem()-cd.getValorExtras();
		
		if(cd.getTipoObjetoId().equals("1")||cd.getTipoObjetoId().equals("5")||cd.getTipoObjetoId().equals("4")||cd.getTipoObjetoId().equals("6"))//dinamicos
		{
			todoRiesgo=cDAO.buscarPorNemotecnico("TORI");
			perdidaTotal=cDAO.buscarPorNemotecnico("DATO");
			responsabilidadCivil=cDAO.buscarPorNemotecnico("RECI");
		}
		else{
			todoRiesgo=cDAO.buscarPorNemotecnico("TRCE");
			perdidaTotal=cDAO.buscarPorNemotecnico("DATO");
			responsabilidadCivil=cDAO.buscarPorNemotecnico("RECI");	
		}
		if(totalTodoRisgo>0)
		{
			double tasa=totalTodoRisgo/(cd.getSumaAseguradaItem()-cd.getValorExtras());
			double valor=tasa*valorVehiculoTodosAnios;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccTR=new CotizacionCobertura();
			ccTR.setValorPrimaOrigen(valor);
			ccTR.setValorPrima(valor*porcentajeConDescuento);
			ccTR.setCobertura(todoRiesgo);
			ccTR.setCotizacionDetalle(cd);
			ccTR.setMontoFijo(montoFijo);
			ccTR.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccTR.setPorcentajeValorSiniestro(valorSiniestro);
			ccTR.setValorMonto(sumaAseguradaSinExtras);
			ccTR=cotizacionCoberturaTransaction.crear(ccTR);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil<=0)
		{
			double tasa=totalDanioTotal/(sumaAseguradaSinExtras);
			double valor=tasa*valorVehiculoTodosAnios;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(valor);
			ccDT.setValorPrima(valor*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(montoFijo);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(valorSiniestro);
			ccDT.setValorMonto(cd.getSumaAseguradaItem()-cd.getValorExtras());
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil>0)
		{
			double valorMontoDT=((totalDanioTotal)/(totalDanioTotal+totalResponsabilidadCivil))*(sumaAseguradaSinExtras);
			double valorMontoRC=((totalResponsabilidadCivil)/(totalDanioTotal+totalResponsabilidadCivil))*(sumaAseguradaSinExtras);
			double tasaDT=(totalDanioTotal)/sumaAseguradaSinExtras;
			double tasaRC=(totalResponsabilidadCivil)/sumaAseguradaSinExtras;
			double valorDT=tasaDT*valorVehiculoTodosAnios;
			double valorRC=tasaRC*valorVehiculoTodosAnios;
			
			totalPrimaDetalle+=valorDT+valorRC;
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(valorDT);
			ccDT.setValorPrima(valorDT*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(0);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(0);
			ccDT.setValorMonto(valorMontoDT);
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
			
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(valorRC);
			ccRC.setValorPrima(valorRC*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(valorMontoRC);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}
		
		if(totalDanioTotal<=0&&totalResponsabilidadCivil>0)
		{
		
			double tasa=totalTodoRisgo/(sumaAseguradaSinExtras);
			double valor=tasa*valorVehiculoTodosAnios;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(valor);
			ccRC.setValorPrima(valor*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(sumaAseguradaSinExtras);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}


		return totalPrimaDetalle;
	}
	
	
	public double guardarCotizacionCoberturaPrincipalesDinamicos(
			CotizacionDetalle cd, double porcentajeSumaAsegurada, double montoFijo, double valorSiniestro, double totalTodoRisgo,
			double totalDanioTotal, double totalResponsabilidadCivil, double porcentajeConDescuento, double primaTotalPrincipales,
			double valorTotalCascoPrimerAnio) {
		
	double totalPrimaDetalle=0.0;	
		CoberturaDAO cDAO=new CoberturaDAO();
		Cobertura todoRiesgo=new Cobertura();
		Cobertura perdidaTotal=new Cobertura();
		Cobertura responsabilidadCivil=new Cobertura();
		CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
		
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		todoRiesgo=cDAO.buscarPorNemotecnico("TORI");
		perdidaTotal=cDAO.buscarPorNemotecnico("DATO");
		responsabilidadCivil=cDAO.buscarPorNemotecnico("RECI");
		
		if(totalTodoRisgo>0)
		{
			double valor=primaTotalPrincipales;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccTR=new CotizacionCobertura();
			ccTR.setValorPrimaOrigen(valor);
			ccTR.setValorPrima(valor*porcentajeConDescuento);
			ccTR.setCobertura(todoRiesgo);
			ccTR.setCotizacionDetalle(cd);
			ccTR.setMontoFijo(montoFijo);
			ccTR.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccTR.setPorcentajeValorSiniestro(valorSiniestro);
			ccTR.setValorMonto(valorTotalCascoPrimerAnio);
			ccTR=cotizacionCoberturaTransaction.crear(ccTR);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil<=0)
		{
			double valor=primaTotalPrincipales;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(valor);
			ccDT.setValorPrima(valor*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(0);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(0);
			ccDT.setValorMonto(valorTotalCascoPrimerAnio);
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil>0)
		{
			double valorMontoDT=((totalDanioTotal)/(totalDanioTotal+totalResponsabilidadCivil))*(valorTotalCascoPrimerAnio);
			double valorMontoRC=((totalResponsabilidadCivil)/(totalDanioTotal+totalResponsabilidadCivil))*(valorTotalCascoPrimerAnio);
			double porcentajeDT=(totalDanioTotal)/(totalDanioTotal+totalResponsabilidadCivil);
			double porcentajeRC=(totalResponsabilidadCivil)/(totalDanioTotal+totalResponsabilidadCivil);
			double primaDT=porcentajeDT*primaTotalPrincipales;
			double primaRC=porcentajeRC*primaTotalPrincipales;
			
			totalPrimaDetalle+=primaTotalPrincipales;
			
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(primaDT);
			ccDT.setValorPrima(primaDT*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(0);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(0);
			ccDT.setValorMonto(valorMontoDT);
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
			
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(primaRC);
			ccRC.setValorPrima(primaRC*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(valorMontoRC);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}
		
		if(totalDanioTotal<=0&&totalResponsabilidadCivil>0)
		{
			double valor=primaTotalPrincipales;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(valor);
			ccRC.setValorPrima(valor*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(valorTotalCascoPrimerAnio);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}


		return totalPrimaDetalle;
	}

	
	public double guardarCotizacionCoberturaPrincipalesCerrados(
			CotizacionDetalle cd, double porcentajeSumaAsegurada, double montoFijo, double valorSiniestro, double totalTodoRisgo,
			double totalDanioTotal, double totalResponsabilidadCivil, double porcentajeConDescuento, double primaTotalPrincipales,
			double valorTotalCascoPrimerAnio) {
		
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		double totalPrimaDetalle=0.0;	
		CoberturaDAO cDAO=new CoberturaDAO();
		Cobertura todoRiesgo=new Cobertura();
		Cobertura perdidaTotal=new Cobertura();
		Cobertura responsabilidadCivil=new Cobertura();
		CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
		
		if(cd.getTipoObjetoId().equals("1")||cd.getTipoObjetoId().equals("5")||cd.getTipoObjetoId().equals("4")||cd.getTipoObjetoId().equals("6"))//dinamicos
			todoRiesgo = cDAO.buscarPorNemotecnico("TORI");
		else
			todoRiesgo = cDAO.buscarPorNemotecnico("TRCE");
		
		perdidaTotal=cDAO.buscarPorNemotecnico("DATO");
		responsabilidadCivil=cDAO.buscarPorNemotecnico("RECI");
		
		if(totalTodoRisgo>0)
		{
			double valor=primaTotalPrincipales;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccTR=new CotizacionCobertura();
			ccTR.setValorPrimaOrigen(valor);
			ccTR.setValorPrima(valor*porcentajeConDescuento);
			ccTR.setCobertura(todoRiesgo);
			ccTR.setCotizacionDetalle(cd);
			ccTR.setMontoFijo(montoFijo);
			ccTR.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccTR.setPorcentajeValorSiniestro(valorSiniestro);
			ccTR.setValorMonto(valorTotalCascoPrimerAnio);
			ccTR=cotizacionCoberturaTransaction.crear(ccTR);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil<=0)
		{
			double valor=valorTotalCascoPrimerAnio;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(valor);
			ccDT.setValorPrima(valor*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(0);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(0);
			ccDT.setValorMonto(valorTotalCascoPrimerAnio);
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
		}
		
		if(totalDanioTotal>0&&totalResponsabilidadCivil>0)
		{
			double valorMontoDT=((totalDanioTotal)/(totalDanioTotal+totalResponsabilidadCivil))*(valorTotalCascoPrimerAnio);
			double valorMontoRC=((totalResponsabilidadCivil)/(totalDanioTotal+totalResponsabilidadCivil))*(valorTotalCascoPrimerAnio);
			double porcentajeDT=(totalDanioTotal)/(totalDanioTotal+totalResponsabilidadCivil);
			double porcentajeRC=(totalResponsabilidadCivil)/(totalDanioTotal+totalResponsabilidadCivil);
			double primaDT=porcentajeDT*primaTotalPrincipales;
			double primaRC=porcentajeRC*primaTotalPrincipales;
			
			totalPrimaDetalle+=primaTotalPrincipales;
			
			CotizacionCobertura ccDT=new CotizacionCobertura();
			ccDT.setValorPrimaOrigen(primaDT);
			ccDT.setValorPrima(primaDT*porcentajeConDescuento);
			ccDT.setCobertura(perdidaTotal);
			ccDT.setCotizacionDetalle(cd);
			ccDT.setMontoFijo(0);
			ccDT.setPorcentajeSumaAsegurada(porcentajeSumaAsegurada);
			ccDT.setPorcentajeValorSiniestro(0);
			ccDT.setValorMonto(valorMontoDT);
			ccDT=cotizacionCoberturaTransaction.crear(ccDT);
			
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(primaRC);
			ccRC.setValorPrima(primaRC*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(valorMontoRC);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}
		
		if(totalDanioTotal<=0&&totalResponsabilidadCivil>0)
		{
			double valor=primaTotalPrincipales;
			totalPrimaDetalle+=valor;
			CotizacionCobertura ccRC=new CotizacionCobertura();
			ccRC.setValorPrimaOrigen(valor);
			ccRC.setValorPrima(valor*porcentajeConDescuento);
			ccRC.setCobertura(responsabilidadCivil);
			ccRC.setCotizacionDetalle(cd);
			ccRC.setMontoFijo(0);
			ccRC.setPorcentajeSumaAsegurada(0);
			ccRC.setPorcentajeValorSiniestro(0);
			ccRC.setValorMonto(valorTotalCascoPrimerAnio);
			ccRC=cotizacionCoberturaTransaction.crear(ccRC);
		}


		return totalPrimaDetalle;
	}
	
	public double guardarCotizacionCoberturaExtras(CotizacionDetalle cd, double tasa, double valorExtrasTodosAnios, double valorExtrasPrimerAnio, double porcentajeConDescuento, double numeroDiasVigencia){
		double totalPrimaExtras=0.0;	
		VigenciaPoliza vp=cd.getCotizacion().getVigenciaPoliza();
		CoberturaDAO cDAO=new CoberturaDAO();
		Cobertura coberturaExtras=new Cobertura();
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
		if(cd.getTipoObjetoId().equals("1")||cd.getTipoObjetoId().equals("5")||cd.getTipoObjetoId().equals("4")||cd.getTipoObjetoId().equals("6"))//dinamicos
		{
			coberturaExtras=cDAO.buscarPorNemotecnico("AMAC");
		}
		else{
			coberturaExtras=cDAO.buscarPorNemotecnico("AMCE");
		}
		if(valorExtrasTodosAnios>0)
		{	
			int anios=vp.getValor().intValue();
			Date inicioVigencia=cd.getCotizacion().getVigenciaDesde();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cd.getCotizacion().getVigenciaDesde());
			
			
			double auxDepreciacion=valorExtrasPrimerAnio;
			double primaTotalSinImpuestos=0;
			for (int i = 1; i <= anios; i++) {
				Date fechaAnterior=calendar.getTime();
				calendar.add(Calendar.YEAR, 1);
				int numeroDias=Days.daysBetween(new DateTime(fechaAnterior), new DateTime(calendar.getTime())).getDays();
				
				double valorDepreciado=0;
				
				if (i == 1) {
					valorDepreciado = auxDepreciacion;
				} else if (i > 1) {
					if (i == 2)
						valorDepreciado = auxDepreciacion - auxDepreciacion * 0.15;
					else
						valorDepreciado = auxDepreciacion - auxDepreciacion * 0.10;

					auxDepreciacion = valorDepreciado;
				}
				primaTotalSinImpuestos+=(auxDepreciacion*tasa*numeroDias)/365;
			}
			
			totalPrimaExtras=primaTotalSinImpuestos;
			
		//	totalPrimaExtras+=tasa*valorExtrasTodosAnios;
		//	totalPrimaExtras=(totalPrimaExtras/(vp.getValor().doubleValue()*365.0))*numeroDiasVigencia;
			CotizacionCobertura ccTR=new CotizacionCobertura();
			ccTR.setValorPrimaOrigen(totalPrimaExtras);
			ccTR.setValorPrima(totalPrimaExtras*porcentajeConDescuento);
			ccTR.setCobertura(coberturaExtras);
			ccTR.setCotizacionDetalle(cd);
			ccTR.setMontoFijo(0);
			ccTR.setPorcentajeSumaAsegurada(0);
			ccTR.setPorcentajeValorSiniestro(0);
			ccTR.setValorMonto(valorExtrasPrimerAnio);
			ccTR=cotizacionCoberturaTransaction.crear(ccTR);
		}

		return totalPrimaExtras;
	}
	
	public double depreciarValor(double valor, int anios){
		double valorExtraDepreciado = valor;
		double valorExtraTiempoVigencia = 0;
		
		for (int i = 1; i <= anios; i++) {
			if (i == 1) {
				valorExtraTiempoVigencia += valorExtraDepreciado;
			} else if (i > 1) {
				if (i == 2)
					valorExtraDepreciado = valorExtraDepreciado - valorExtraDepreciado * 0.15;
				else
					valorExtraDepreciado = valorExtraDepreciado - valorExtraDepreciado * 0.10;

				valorExtraTiempoVigencia += valorExtraDepreciado;
			}
		}
		return valorExtraTiempoVigencia;
	}
	
	public double guardarCotizacionCoberturasAdicionales(CotizacionDetalle cd, String coberturasAdicionalesStr, double valorTotalCascoPrimerAnio, double valorTotalCascoTodosAnios, double porcentajeConDescuento, double valorExcesoRC){
		double primaAcumuladaAdicionales=0.0;
		CoberturaDAO coberturaDAO=new CoberturaDAO();
		String [] coberturasAdicionales = coberturasAdicionalesStr.split(",");
		int vigenciaCotizacion=cd.getCotizacion().getVigenciaPoliza().getValor().intValue();
		List<CotizacionCobertura> listaCoberturas = new ArrayList<CotizacionCobertura>();
		
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		if(coberturasAdicionales.length >= 1&&!coberturasAdicionalesStr.equals(""))
			for(int i=0;i<coberturasAdicionales.length;i++){
				CotizacionCobertura ccAdicional=new CotizacionCobertura();
				Cobertura adicional = coberturaDAO.buscarPorId(coberturasAdicionales[i]);
				CotizacionCoberturaDAO ccDAO=new CotizacionCoberturaDAO();
				if(!adicional.getId().toString().equals("6348540415022")&&!adicional.getId().toString().equals("6349173767914")){
				if (adicional.getTipoTasa().getId().equals("1")) {
					ccAdicional.setCobertura(adicional);
					ccAdicional.setCotizacionDetalle(cd);
					ccAdicional.setMontoFijo(0);
					ccAdicional.setPorcentajeSumaAsegurada(0);
					ccAdicional.setPorcentajeValorSiniestro(0);
					ccAdicional.setValorPrimaOrigen(vigenciaCotizacion*adicional.getTasaValor());
					ccAdicional.setValorPrima(ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento);
					primaAcumuladaAdicionales+=	ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento;	
					ccAdicional=cotizacionCoberturaTransaction.crear(ccAdicional);
					//cotizacionCoberturaDAO.crear(ccAdicional);
					//primaTasa += adicional.getTasaValor();
				}
				if (adicional.getTipoTasa().getId().equals("2")) {
					ccAdicional.setCobertura(adicional);
					ccAdicional.setCotizacionDetalle(cd);
					ccAdicional.setMontoFijo(0);
					ccAdicional.setPorcentajeSumaAsegurada(0);
					ccAdicional.setPorcentajeValorSiniestro(0);
					ccAdicional.setValorPrimaOrigen(vigenciaCotizacion*adicional.getTasaValor()* (valorTotalCascoPrimerAnio)/100);
					ccAdicional.setValorPrima(ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento);
					primaAcumuladaAdicionales+=	ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento;	
					
					ccAdicional=cotizacionCoberturaTransaction.crear(ccAdicional);
					//cotizacionCoberturaDAO.crear(ccAdicional);
					//primaTasa += (adicional.getTasaValor() * vehiculo.getSumaAsegurada()/100);
				}
				}
				else{
					ccAdicional.setCobertura(adicional);
					ccAdicional.setCotizacionDetalle(cd);
					ccAdicional.setMontoFijo(0);
					ccAdicional.setPorcentajeSumaAsegurada(0);
					ccAdicional.setPorcentajeValorSiniestro(0);
					ccAdicional.setValorPrimaOrigen(vigenciaCotizacion*adicional.getTasaValor()*valorExcesoRC/100);
					ccAdicional.setValorPrima(ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento);
					ccAdicional.setValorMonto(valorExcesoRC);
					primaAcumuladaAdicionales+=	ccAdicional.getValorPrimaOrigen()*porcentajeConDescuento;	
										
					ccAdicional=cotizacionCoberturaTransaction.crear(ccAdicional);
					//cotizacionCoberturaDAO.crear(ccAdicional);
					//primaTasa += adicional.getTasaValor();
				}
			}
		
		return primaAcumuladaAdicionales;
	}
	
	
	public double actualizarValoresCotizacionDetalle(CotizacionDetalle cotizacionDetalle , double porcentajeConDescuento, Paquete paquete, double tasaCasco){
		double primaAcumuladaTotal=0.0;
		CoberturaDAO coberturaDAO=new CoberturaDAO();
		CotizacionCoberturaDAO ccDAO= new CotizacionCoberturaDAO();
		CotizacionDetalleDAO cdDAO=new CotizacionDetalleDAO();
		List<CotizacionCobertura> listaCoberturas = ccDAO.buscarPorCotizacionDetalle(cotizacionDetalle);
		
		CotizacionCoberturaTransaction cotizacionCoberturaTransaction = new CotizacionCoberturaTransaction();
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		
		for(int i=0;i<listaCoberturas.size();i++){
			CotizacionCobertura cc=listaCoberturas.get(i);
			primaAcumuladaTotal+=cc.getValorPrimaOrigen();
		}
		
		cotizacionDetalle.setPrimaNetaItemOrigen(primaAcumuladaTotal);
		cotizacionDetalle.setPrimaNetaItem(primaAcumuladaTotal*porcentajeConDescuento);
		cotizacionDetalle.setTasaOrigen(tasaCasco);
		cotizacionDetalle.setTasa(tasaCasco);
		cotizacionDetalle = cotizacionDetalleTransaction.editar(cotizacionDetalle);
		
		return primaAcumuladaTotal;
	}	
	
	
	public int calcularDiasVigenciaCotizacion(Cotizacion cotizacion){
		int numeroDias=0;
		
		VigenciaPoliza vp=cotizacion.getVigenciaPoliza();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cotizacion.getVigenciaDesde());
		calendar.add(Calendar.YEAR, vp.getValor().intValue());
		numeroDias=Days.daysBetween(new DateTime(cotizacion.getVigenciaDesde()), new DateTime(calendar.getTime())).getDays();
		
		return numeroDias;
	}
}


