package com.qbe.cotizador.servlets.producto.vehiculo;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.qbe.cotizador.dao.cotizacion.ContenedorDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.DeducibleDAO;
import com.qbe.cotizador.dao.cotizacion.EndosoBeneficiarioDAO;
import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.cotizacion.SolicitudEmisionDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.entidad.AgenteDAO;
import com.qbe.cotizador.dao.entidad.ClienteDAO;
import com.qbe.cotizador.dao.entidad.ConfiguracionProductoDAO;
import com.qbe.cotizador.dao.entidad.DetalleProductoDAO;
import com.qbe.cotizador.dao.entidad.DireccionDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.FirmasDigitalesDAO;
import com.qbe.cotizador.dao.entidad.RamoDAO;
import com.qbe.cotizador.dao.pagos.CuotaDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ObjetoVehiculoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.seguridad.VariableSistemaDAO;
import com.qbe.cotizador.model.Agente;
import com.qbe.cotizador.model.Cliente;
import com.qbe.cotizador.model.ConfiguracionProducto;
import com.qbe.cotizador.model.Contenedor;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionCobertura;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Cuota;
import com.qbe.cotizador.model.DetalleProducto;
import com.qbe.cotizador.model.Direccion;
import com.qbe.cotizador.model.EndosoBeneficiario;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.Extra;
import com.qbe.cotizador.model.FirmasDigitales;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.ObjetoVehiculo;
import com.qbe.cotizador.model.Pago;
import com.qbe.cotizador.model.Producto;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.Ramo;
import com.qbe.cotizador.model.SolicitudEmision;
import com.qbe.cotizador.model.TipoObjeto;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.VariableSistema;
import com.qbe.cotizador.model.VigenciaPoliza;
import com.qbe.cotizador.servicios.QBE.emisionWS.EmisionQbeLink_Service;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.cotizacion.SolicitudEmisionTransaction;
import com.qbe.cotizador.util.TipoEntidadEnum;
import com.qbe.cotizador.util.Utilitarios;

/**
 * Servlet implementation class EmisionVHController
 */@WebServlet("/EmisionVHController")
public class EmisionVHController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmisionVHController() {
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

		SolicitudEmisionTransaction solicitudEmisionTransaction= new SolicitudEmisionTransaction();
		CotizacionTransaction cotizacionTransaction= new CotizacionTransaction();
		
		String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
		String producto = request.getParameter("producto") == null ? "" : request.getParameter("producto");
		String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
		String validarEmision = request.getParameter("validarEmision") == null ? "" : request.getParameter("validarEmision");

		
		 /*String puerto="80"; 
		 String login ="cotizador"; 
		 String pass ="cotizador"; 
		 String usuarioActualiza = "8366750047036"; // Usuario Cotizador*/

		String puerto = "8084";
		String login = "cotizador";
		String pass = "pruebas";
		String usuarioActualiza = "8366750047036"; // Usuario Cotizador*/
		
		VariableSistemaDAO variablesDAO = new VariableSistemaDAO();
		VariableSistema variableSistema = variablesDAO.buscarPorNombre("PERMITE_EMISION");
		String permiteEmision = variableSistema.getValor();

		try {
			if (permiteEmision.equals("0")) {
				result.put("error", "LA EMISION NO ESTA DISPONIBLE POR PROCESOS DE CIERRE DE MES");
				result.put("success", Boolean.FALSE);
				response.setContentType("application/json; charset=ISO-8859-1");
				result.write(response.getWriter());
			} else {
				boolean validaParaEmision = false;
				if (validarEmision.equals("1")) {
					validarEmision(cotizacionId);
				}

				if (tipoConsulta.equalsIgnoreCase("emisionPoliza") && producto.equalsIgnoreCase("VHDinamico")) {

					EmisionQbeLink_Service emisionQBE = new EmisionQbeLink_Service();

					// Obtenemos los datos de la cotizacion
					CotizacionDAO cotizacionDAO = new CotizacionDAO();
					Cotizacion cotizacion = cotizacionDAO.buscarPorId(cotizacionId);

					// Datos del agente
					AgenteDAO agenteDAO = new AgenteDAO();
					Agente agente = agenteDAO.buscarPorId(String.valueOf(cotizacion.getAgenteId()));

					// Datos del Producto
					ProductoDAO productoDAO = new ProductoDAO();
					Producto productoVH = cotizacion.getProducto();

					// Obtenemos el plan del producto
					ConfiguracionProductoDAO configuracionProductoDAO = new ConfiguracionProductoDAO();
					ConfiguracionProducto configuracionProducto = configuracionProductoDAO.buscarPorProducto(productoVH);
					DetalleProductoDAO detalleProductoDAO = new DetalleProductoDAO();
					DetalleProducto detalleProducto = detalleProductoDAO.buscarPorConfiguracionProducto(configuracionProducto);

					/// Calculo de la vigencia adicionando el ano bisiesto
					int vigencia = (int) cotizacion.getVigenciaPoliza().getValor().doubleValue();
					int anoInicioVigencia = cotizacion.getVigenciaDesde().getYear()+ 1900;
					int anoTemporal = 0;
					int diasExtrasBisiesto = 0;
					for (int i = 1;i<=vigencia;i++){
						anoTemporal = anoInicioVigencia+i; // Agregamos el numero de anos para verificar si es bisiesto
						if(Utilitarios.esAnoBisiesto(anoTemporal)){ // Comprobamos el anio bisiesto
							diasExtrasBisiesto++;
						}						
					}					
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(new Date());
					int numeroDias = (calendario.getActualMaximum(Calendar.DAY_OF_YEAR) * vigencia)+diasExtrasBisiesto; // numeros de dias existentes en la vigencia

					long output = cotizacion.getVigenciaDesde().getTime() / 1000L;
					String str = Long.toString(output);
					long timestamp = Long.parseLong(str) * 1000;

					long vigenciaHasta = Utilitarios.agregarDiaFechaTimestamp(new Timestamp(timestamp), numeroDias);

					// Obtenemos datos de la unidad de produccion y unidad de
					// negocio
					ProductoXPuntoVentaDAO productoXPuntoVentaDAO = new ProductoXPuntoVentaDAO();
					String productoXPuntoVentaId = cotizacion.getProductoXPuntoVentaId().toString();
					ProductoXPuntoVenta productoXPuntoVenta = productoXPuntoVentaDAO.buscarPorId(productoXPuntoVentaId);

					// Obtenemos datos del ramo
					RamoDAO ramoDAO = new RamoDAO();
					Ramo ramo = ramoDAO.buscarPorId(cotizacion.getProducto().getRamoId().toString());

					// Obtenemos los Pagos de la Cotizacion
					Pago pago = cotizacion.getPago();
					String formaPago = pago.getFormaPago().getNombre();
					formaPago = formaPago.replaceAll("\\s+", "");

					int numeroPagos = 0;
					String numeroCuenta = "";
					String tipoCuenta = "";
					String convenioPago = "";
					String institucionADebitar = "";
					String fechaVencimientoTarjeta = "";
					String PagoDTO = "";
					String pagosRealizados = "";
					Date fechaDesde = cotizacion.getVigenciaDesde();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					CuotaDAO cuotaDAO = new CuotaDAO();
					List < Cuota > cuotas = cuotaDAO.buscarPorPago(pago);

					// Validacion pago dependiendo de la forma de pago
					if (formaPago.equalsIgnoreCase("CONTADO")) {
						numeroPagos = 1;
						Date fechaPago = cuotas.get(0).getFechaPago();
						pagosRealizados = "<PagoDTO><valorPago>" + cuotas.get(0).getValor() + "</valorPago><fechaPago>" + sdf.format(fechaPago) + "</fechaPago><esCuota>1</esCuota><orden>" + "1" + "</orden></PagoDTO>";
					}

					if (formaPago.equalsIgnoreCase("DEBITOBANCARIO")) {
						numeroPagos = cuotas.size();
						institucionADebitar = pago.getInstitucionFinanciera()
							.getCodigoEnsurance().toString();
						numeroCuenta = pago.getNumeroCuentaTarjeta();
						convenioPago = pago.getInstitucionFinanciera()
							.getConvenioId();
						tipoCuenta = pago.getTipoCuenta();
						fechaVencimientoTarjeta = sdf.format(new Date());
						int contador = 1;
						for (Cuota cuota: cuotas) {
							/*if(pago.getCuotaInicial()>0&&contador==1)
							{
								pagosRealizados += "<PagoDTO><valorPago>" + pago.getCuotaInicial() + "</valorPago><fechaPago>" + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							}*/
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					if (formaPago.equalsIgnoreCase("DEBITOTARJETA")) {
						numeroPagos = cuotas.size();
						institucionADebitar = pago.getInstitucionFinanciera()
							.getCodigoEnsurance().toString();
						numeroCuenta = pago.getNumeroCuentaTarjeta();
						convenioPago = pago.getInstitucionFinanciera()
							.getConvenioId();
						tipoCuenta = pago.getTipoCuenta();
						fechaVencimientoTarjeta = "01/" + pago.getMesExpiracionTarjeta() + "/" + pago.getAnioExpiracionTarjeta();
						int contador = 1;
						for (Cuota cuota: cuotas) {
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					if (formaPago.equalsIgnoreCase("CREDITOCUOTAS")) {
						numeroPagos = cuotas.size();
						numeroCuenta = ""; // cuotas.get(0).getNumeroCheque();
						convenioPago = ""; // pago.getInstitucionFinanciera().getConvenioId();
						fechaVencimientoTarjeta = ""; // sdf.format(new Date());
						int contador = 1;
						for (Cuota cuota: cuotas) {
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					// Obtenemos los datos del usuario que emite
					Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
					if(usuario==null||usuario.getId()==null)
						throw new Exception("Usuario sin sesi�n");
					
					Entidad entidadEmisor = usuario.getEntidad();

					// Obtenemos el listado de vehiculos
					CotizacionDetalleDAO cdDAO = new CotizacionDetalleDAO();
					List < CotizacionDetalle > cotizacionesDetalle = cdDAO.buscarCotizacionDetallePorCotizacion(cotizacion);

					// cliente
					ClienteDAO clienteDAO = new ClienteDAO();
					Cliente cliente = clienteDAO.buscarPorId(cotizacion.getClienteId().toString());

					String xmlVehiculos = xmlVehiculos(cotizacionesDetalle,
					cliente, false);

					StringBuilder emisionXML = new StringBuilder();
					emisionXML.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
					emisionXML.append("<superObjetoXML>");
					emisionXML.append("<identificador>" + cotizacion.getId() + "</identificador>");
					emisionXML.append("<puerto>" + puerto + "</puerto>");
					emisionXML.append("<login>" + login + "</login>");
					emisionXML.append("<pass>" + pass + "</pass>");

					// Emision de programas de seguros
					if (cotizacion.getEmitirProgramaSeguros()) emisionXML.append("<programas>true</programas>");
					else emisionXML.append("<programas>false</programas>");

					emisionXML.append("<emision>V</emision>");
					emisionXML.append("<agenteId>" + agente.getAgeEnsurance() + "</agenteId>");
					emisionXML.append("<planProducto>" + detalleProducto.getPlan().getId() + "</planProducto>");
					emisionXML.append("<productoId>" + productoVH.getId() + "</productoId>");
					emisionXML.append("<esPymes>0</esPymes>");

					ContenedorDAO contenedorDAO = new ContenedorDAO();
					Contenedor contenedor = contenedorDAO.buscarPorId(productoXPuntoVenta.getContenedorId());

					emisionXML.append("<valorComision>" + (cotizacion.getPorcentajeComision() * Double.parseDouble(cotizacion.getPrimaNetaTotal())) / 100 + "</valorComision>");
					emisionXML.append("<porcentajeComision>" + cotizacion.getPorcentajeComision() + "</porcentajeComision>");
					emisionXML.append("<valorAsegurado>" + cotizacion.getSumaAseguradaTotal() + "</valorAsegurado>");
					emisionXML.append("<valorPrima>" + cotizacion.getPrimaNetaTotal() + "</valorPrima>");
					emisionXML.append("<valorFactura>" + cotizacion.getTotalFactura() + "</valorFactura>"); // Revisar
					// agregar total factura y numero factura
					emisionXML.append("<fechaVencimientoFactura>" + vigenciaHasta + "</fechaVencimientoFactura>");
					emisionXML.append("<TipoDocumentoFac>1</TipoDocumentoFac>");
					emisionXML.append("<numeroFactura></numeroFactura>");
					emisionXML.append("<autorizacionSRI></autorizacionSRI>");
					emisionXML.append("<contenedorId>" + contenedor.getIdEnsurance() + "</contenedorId>");
					emisionXML.append("<numeroPoliza>" + cotizacion.getId() + "</numeroPoliza>");
					emisionXML.append("<puntoVentaId>" + cotizacion.getPuntoVenta().getPtoEnsurance() + "</puntoVentaId>");
					emisionXML.append("<tipoRiesgoId>" + ramo.getTipoRiesgos().get(0).getId() + "</tipoRiesgoId>");
					emisionXML.append("<claseRiesgoId>" + ramo.getClaseRiesgos().get(0).getId() + "</claseRiesgoId>");
					emisionXML.append("<cobradorId>1</cobradorId>");
					emisionXML.append("<tipoItemId>110</tipoItemId>");
					emisionXML.append("<monedaId>11141120</monedaId>");
					emisionXML.append("<plantillaId></plantillaId>");
					FirmasDigitalesDAO fdDAO = new FirmasDigitalesDAO();
					EntidadDAO entidadDAO = new EntidadDAO();
					Entidad entidadUP = entidadDAO.buscarPorId(productoXPuntoVenta.getUnidadProduccion().getEntidadId());
					FirmasDigitales fd = fdDAO.buscarPorRamoSucursalEntidad(ramo, cotizacion.getPuntoVenta().getSucursal(),entidadUP);
					emisionXML.append("<firmaDigitalId>" + fd.getIdEnsurance() + "</firmaDigitalId>");
					emisionXML.append("<sistemaEmisorId>" + entidadEmisor.getEntEnsurance() + "</sistemaEmisorId>");
					emisionXML.append("<sistemaEmisor>" + entidadEmisor.getNombreCompleto() + "</sistemaEmisor>");
					emisionXML.append("<usuarioActualiza>" + usuarioActualiza + "</usuarioActualiza>");
					emisionXML.append("<ramoid>" + ramo.getId() + "</ramoid>");
					emisionXML.append("<ramo>" + ramo.getNemotecnico() + "</ramo>");
					emisionXML.append("<unidadNegocioId>" + productoXPuntoVenta.getUnidadNegocio().getUnEnsurance() + "</unidadNegocioId>");
					emisionXML.append("<unidadProduccionId>" + productoXPuntoVenta.getUnidadProduccion().getUpEnsurance() + "</unidadProduccionId>");
					emisionXML.append("<sucursalId>" + cotizacion.getPuntoVenta().getSucursal().getSucEnsurance() + "</sucursalId>");
					emisionXML.append("<vigenciaDesde>" + cotizacion.getVigenciaDesde().getTime() / 1000 + "</vigenciaDesde>");
					emisionXML.append("<vigenciaHasta>" + vigenciaHasta / 1000 + "</vigenciaHasta>");
					emisionXML.append("<configPago>");
					emisionXML.append("<tipoPagoId>" + pago.getFormaPago().getCodigoEnsurance() + "</tipoPagoId>");
					emisionXML.append("<numeroPagos>" + pago.getPlazonEnMes() + "</numeroPagos>");
					emisionXML.append("<numeroCuenta>" + numeroCuenta + "</numeroCuenta>");
					emisionXML.append("<tipoCuenta>" + tipoCuenta + "</tipoCuenta>");
					emisionXML.append("<convenioPagoId>" + convenioPago + "</convenioPagoId>");
					emisionXML.append("<institucionADebitar>" + institucionADebitar + "</institucionADebitar>");
					emisionXML.append("<FechaVencimientoTarjeta>" + fechaVencimientoTarjeta + "</FechaVencimientoTarjeta>");
					emisionXML.append(pagosRealizados);
					emisionXML.append("</configPago>");
					emisionXML.append(xmlEntidad(cliente,TipoEntidadEnum.CLIENTE)); // Agregamos tag cliente

					if (cotizacion.getAsegurado() == null) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.ASEGURADO)); // No existe asegurado
					else emisionXML.append(xmlEntidadAsegurado(cotizacion.getAsegurado())); // Existe el asegurado

					EndosoBeneficiarioDAO endosoBeneficiarioDAO = new EndosoBeneficiarioDAO();
					EndosoBeneficiario endosoBeneficiario = endosoBeneficiarioDAO.buscarPorCotizacion(cotizacion);

					if (endosoBeneficiario == null) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.BENEFICIARIOS)); // No existe Beneficiario
					else {
						Double valorBeneficiarioPorcentaje = (endosoBeneficiario.getMonto() * 100)/cotizacion.getTotalFactura();
						emisionXML.append(xmlEntidadBeneficiario(endosoBeneficiario,valorBeneficiarioPorcentaje.intValue())); // Agregamos el tag beneficiarios
					}

					emisionXML.append(xmlEntidad(cliente,
					TipoEntidadEnum.PROPIETARIO)); // Agregamos el tag propietario

					if (formaPago.equalsIgnoreCase("CONTADO") || formaPago.equalsIgnoreCase("CREDITOCUOTAS")) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.PROPIETARIOCUENTA)); // No existe propietario cuenta
					else emisionXML.append(xmlEntidadPropietarioCuenta(cotizacion.getPago())); // Agregamos el tag propietario cuenta

					emisionXML.append("<itemV>");
					emisionXML.append(xmlVehiculos); // Datos del Vehiculo
					emisionXML.append("</itemV>");

					// Obtenemos las coberturas con los deducibles correspondientes
					CotizacionDetalleDAO cotizacionDetalleDAO = new CotizacionDetalleDAO();
					TipoObjetoDAO tipoObjetoDAO = new TipoObjetoDAO();
					TipoObjeto tipoObjetoVHDinamicos = tipoObjetoDAO.buscarPorNombre("Vehiculos Livianos");
					CotizacionCoberturaDAO cotizacionCoberturaDAO = new CotizacionCoberturaDAO();
					CotizacionDetalle cotizacionDetalle = cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion).get(0);
					List < CotizacionCobertura > cotizacionCoberturas = cotizacionCoberturaDAO.buscarCotizacionCoberturaPorCotizacionDetalle(cotizacionDetalle,tipoObjetoVHDinamicos.getId());

					DeducibleDAO deducibleDAO = new DeducibleDAO();
					GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
					GrupoPorProducto grupoPorProducto = grupoPorProductoDAO.buscarPorId(cotizacion.getGrupoPorProductoId().toString());

					DecimalFormat decimalFormat = new DecimalFormat("#.#");

					emisionXML.append("<deducibles>");
					for (CotizacionCobertura cotizacionCobertura: cotizacionCoberturas) {
						if (cotizacionCobertura.getCobertura().getId().equalsIgnoreCase("40010")) {
							emisionXML.append("<DeducibleDTO>");
							emisionXML.append("<id>2</id>");
							emisionXML.append("<texto> 'VAL' % del Valor Asegurado</texto>");
							emisionXML.append("<valor>" + cotizacionCobertura.getPorcentajeSumaAsegurada() + "</valor>");
							emisionXML.append("<findedu>1</findedu>");
							emisionXML.append("</DeducibleDTO>");
							emisionXML.append("<DeducibleDTO>");
							emisionXML.append("<id>1</id>");
							emisionXML.append("<texto> 'VAL' % del Valor del Siniestro</texto>");
							emisionXML.append("<valor>" + cotizacionCobertura.getPorcentajeValorSiniestro() + "</valor>");
							emisionXML.append("<findedu>1</findedu>");
							emisionXML.append("</DeducibleDTO>");
							emisionXML.append("<DeducibleDTO>");
							emisionXML.append("<id>3</id>");
							emisionXML.append("<texto> Minimo US$. 'VAL'</texto>");
							emisionXML.append("<valor>" + cotizacionCobertura.getMontoFijo() + "</valor>");
							emisionXML.append("<findedu>1</findedu>");
							emisionXML.append("</DeducibleDTO>");
						}
						if (cotizacionCobertura.getCobertura().getId().equalsIgnoreCase("40037")) {
							emisionXML.append("<DeducibleDTO>");
							emisionXML.append("<id>2</id>");
							emisionXML.append("<texto> 'VAL' % del Valor Asegurado</texto>");
							emisionXML.append("<valor>" + cotizacionCobertura.getPorcentajeSumaAsegurada() + "</valor>");
							emisionXML.append("<findedu>1</findedu>");
							emisionXML.append("</DeducibleDTO>");
						}
						if (cotizacionCobertura.getCobertura().getId().equalsIgnoreCase("4540881508289")) {
							emisionXML.append("<DeducibleDTO>");
							emisionXML.append("<id>3</id>");
							emisionXML.append("<texto> Minimo US$. 'VAL'</texto>");
							emisionXML.append("<valor>500</valor>");
							emisionXML.append("<findedu>1</findedu>");
							emisionXML.append("</DeducibleDTO>");
						}
					}
					emisionXML.append("</deducibles>");

					emisionXML.append("</superObjetoXML>");
					String emisionValor = emisionXML.toString();
					SolicitudEmisionDAO seDAO=new SolicitudEmisionDAO();
					SolicitudEmision se = new SolicitudEmision();
					se.setCotizacionId(cotizacionId);
					se.setXml(emisionValor);
					System.out.println(emisionValor);
					String respuesta = emisionQBE.getEmisionQbeLinkPort().emisionGeneral(emisionValor, "");
					se.setRespuesta(respuesta);
					solicitudEmisionTransaction.crear(se);
					System.out.println(respuesta);
					result.put("respuesta", respuesta);
					if (!cotizacion.getEmitirProgramaSeguros()&&respuesta.split("::")[0].length() == 17) {
						EstadoDAO estadoDAO = new EstadoDAO();
						cotizacion.setNumeroFactura(respuesta.split("::")[0]);
						result.put("numeroFactura", respuesta.split("::")[0]);
						result.put("programaSeguros", false);
						cotizacion.setFechaEmision(new Timestamp(new java.util.Date().getTime()));
						cotizacion.setEstado(estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"));
						cotizacion.setEtapaWizard(5);
						cotizacion = cotizacionTransaction.editar(cotizacion);
					}
					else{
						
						if(cotizacion.getEmitirProgramaSeguros()&&respuesta.contains("::")){
							EstadoDAO estadoDAO = new EstadoDAO();
							cotizacion.setNumeroFactura("Programa de Seguros");
							result.put("numeroFactura", "");
							result.put("programaSeguros", true);
							cotizacion.setFechaEmision(new Timestamp(new java.util.Date().getTime()));
							cotizacion.setEstado(estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"));
							cotizacion.setEtapaWizard(5);
							cotizacion = cotizacionTransaction.editar(cotizacion);
						}
						else
						result.put("error", respuesta);
						}
					
					result.put("success", Boolean.TRUE);
					response.setContentType("application/json; charset=ISO-8859-1");
					result.write(response.getWriter());

				}

				if (tipoConsulta.equalsIgnoreCase("emisionPoliza") && !producto.equalsIgnoreCase("VHDinamico")) {

					EmisionQbeLink_Service emisionQBE = new EmisionQbeLink_Service();

					// Obtenemos los datos de la cotizacion
					CotizacionDAO cotizacionDAO = new CotizacionDAO();
					Cotizacion cotizacion = cotizacionDAO.buscarPorId(cotizacionId);

					// Datos del agente
					AgenteDAO agenteDAO = new AgenteDAO();
					Agente agente = agenteDAO.buscarPorId(String.valueOf(cotizacion.getAgenteId()));

					// Datos del Producto
					ProductoDAO productoDAO = new ProductoDAO();
					Producto productoVH = cotizacion.getProducto();

					// Obtenemos el plan del producto
					ConfiguracionProductoDAO configuracionProductoDAO = new ConfiguracionProductoDAO();
					ConfiguracionProducto configuracionProducto = configuracionProductoDAO.buscarPorProducto(productoVH);
					DetalleProductoDAO detalleProductoDAO = new DetalleProductoDAO();
					DetalleProducto detalleProducto = detalleProductoDAO.buscarPorConfiguracionProducto(configuracionProducto);

					// Calculo de la vigencia adicionando el ano bisiesto
					int vigencia = (int) cotizacion.getVigenciaPoliza().getValor().doubleValue();
					int anoInicioVigencia = cotizacion.getVigenciaDesde().getYear()+ 1900;
					int anoTemporal = 0;
					int diasExtrasBisiesto = 0;
					for (int i = 1;i<=vigencia;i++){
						anoTemporal = anoInicioVigencia+i; // Agregamos el numero de anos para verificar si es bisiesto
						if(Utilitarios.esAnoBisiesto(anoTemporal)){ // Comprobamos el anio bisiesto
							diasExtrasBisiesto++;
						}						
					}					
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(new Date());
					int numeroDias = (calendario.getActualMaximum(Calendar.DAY_OF_YEAR) * vigencia)+diasExtrasBisiesto; // numeros de dias existentes en la vigencia

					long output = cotizacion.getVigenciaDesde().getTime() / 1000L;
					String str = Long.toString(output);
					long timestamp = Long.parseLong(str) * 1000;

					long vigenciaHasta = Utilitarios.agregarDiaFechaTimestamp(new Timestamp(timestamp), numeroDias);

					// Obtenemos datos de la unidad de produccion y unidad de negocio
					ProductoXPuntoVentaDAO productoXPuntoVentaDAO = new ProductoXPuntoVentaDAO();
					String productoXPuntoVentaId = cotizacion.getProductoXPuntoVentaId().toString();
					ProductoXPuntoVenta productoXPuntoVenta = productoXPuntoVentaDAO.buscarPorId(productoXPuntoVentaId);

					// Obtenemos datos del ramo
					RamoDAO ramoDAO = new RamoDAO();
					Ramo ramo = ramoDAO.buscarPorId(cotizacion.getProducto()
						.getRamoId().toString());

					// Obtenemos los Pagos de la Cotizacion
					Pago pago = cotizacion.getPago();
					String formaPago = pago.getFormaPago().getNombre();
					formaPago = formaPago.replaceAll("\\s+", "");

					int numeroPagos = 0;
					String numeroCuenta = "";
					String tipoCuenta = "";
					String convenioPago = "";
					String institucionADebitar = "";
					String fechaVencimientoTarjeta = "";
					String PagoDTO = "";
					String pagosRealizados = "";
					Date fechaDesde = cotizacion.getVigenciaDesde();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					CuotaDAO cuotaDAO = new CuotaDAO();
					List < Cuota > cuotas = cuotaDAO.buscarPorPago(pago);

					// Validacion pago dependiendo de la forma de pago
					if (formaPago.equalsIgnoreCase("CONTADO")) {
						numeroPagos = 1;
						Date fechaPago = cuotas.get(0).getFechaPago();
						pagosRealizados = "<PagoDTO><valorPago>" + cuotas.get(0).getValor() + "</valorPago><fechaPago>" + sdf.format(fechaPago) + "</fechaPago><esCuota>1</esCuota><orden>" + "1" + "</orden></PagoDTO>";
					}

					if (formaPago.equalsIgnoreCase("DEBITOBANCARIO")) {
						numeroPagos = cuotas.size();
						institucionADebitar = pago.getInstitucionFinanciera()
							.getCodigoEnsurance().toString();
						numeroCuenta = pago.getNumeroCuentaTarjeta();
						convenioPago = pago.getInstitucionFinanciera()
							.getConvenioId();
						tipoCuenta = pago.getTipoCuenta();
						fechaVencimientoTarjeta = sdf.format(new Date());
						int contador = 1;
						for (Cuota cuota: cuotas) {
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					if (formaPago.equalsIgnoreCase("DEBITOTARJETA")) {
						numeroPagos = cuotas.size();
						institucionADebitar = pago.getInstitucionFinanciera()
							.getCodigoEnsurance().toString();
						numeroCuenta = pago.getNumeroCuentaTarjeta();
						convenioPago = pago.getInstitucionFinanciera()
							.getConvenioId();
						tipoCuenta = pago.getTipoCuenta();
						fechaVencimientoTarjeta = "01/" + pago.getMesExpiracionTarjeta() + "/" + pago.getAnioExpiracionTarjeta();
						int contador = 1;
						for (Cuota cuota: cuotas) {
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					if (formaPago.equalsIgnoreCase("CREDITOCUOTAS")) {
						numeroPagos = cuotas.size();
						numeroCuenta = ""; // cuotas.get(0).getNumeroCheque();
						convenioPago = ""; // pago.getInstitucionFinanciera().getConvenioId();
						fechaVencimientoTarjeta = ""; // sdf.format(new Date());
						int contador = 1;
						for (Cuota cuota: cuotas) {
							if(cuota.getValor()!=0){
							pagosRealizados += "<PagoDTO><valorPago>" + cuota.getValor() + "</valorPago><fechaPago>" + sdf.format(cuota.getFechaPago()) + "</fechaPago><esCuota>1</esCuota><orden>" + contador + "</orden></PagoDTO>";
							contador++;}
						}
					}

					// Obtenemos los datos del usuario que emite
					Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
					Entidad entidadEmisor = usuario.getEntidad();

					// Obtenemos el listado de vehiculos
					CotizacionDetalleDAO cdDAO = new CotizacionDetalleDAO();
					List < CotizacionDetalle > cotizacionesDetalle = cdDAO.buscarCotizacionDetallePorCotizacion(cotizacion);

					// cliente
					ClienteDAO clienteDAO = new ClienteDAO();
					Cliente cliente = clienteDAO.buscarPorId(cotizacion.getClienteId().toString());

					String xmlVehiculos = xmlVehiculos(cotizacionesDetalle,
					cliente, false);

					StringBuilder emisionXML = new StringBuilder();
					emisionXML.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
					emisionXML.append("<superObjetoXML>");
					emisionXML.append("<identificador>" + cotizacion.getId() + "</identificador>");
					emisionXML.append("<puerto>" + puerto + "</puerto>");
					emisionXML.append("<login>" + login + "</login>");
					emisionXML.append("<pass>" + pass + "</pass>");

					// Emision de programas de seguros
					if (cotizacion.getEmitirProgramaSeguros()) emisionXML.append("<programas>true</programas>");
					else emisionXML.append("<programas>false</programas>");

					emisionXML.append("<emision>V</emision>");
					emisionXML.append("<agenteId>" + agente.getAgeEnsurance() + "</agenteId>");
					emisionXML.append("<planProducto>" + detalleProducto.getPlan().getId() + "</planProducto>");
					emisionXML.append("<productoId>" + productoVH.getId() + "</productoId>");
					emisionXML.append("<esPymes>0</esPymes>");

					ContenedorDAO contenedorDAO = new ContenedorDAO();
					Contenedor contenedor = contenedorDAO.buscarPorId(productoXPuntoVenta.getContenedorId());

					emisionXML.append("<valorComision>" + (cotizacion.getPorcentajeComision() * Double.parseDouble(cotizacion.getPrimaNetaTotal())) / 100 + "</valorComision>");
					emisionXML.append("<porcentajeComision>" + cotizacion.getPorcentajeComision() + "</porcentajeComision>");
					emisionXML.append("<valorAsegurado>" + cotizacion.getSumaAseguradaTotal() + "</valorAsegurado>");
					emisionXML.append("<valorPrima>" + cotizacion.getPrimaNetaTotal() + "</valorPrima>");
					emisionXML.append("<valorFactura>" + cotizacion.getTotalFactura() + "</valorFactura>"); // Revisar
					// agregar total factura y numero factura
					emisionXML.append("<fechaVencimientoFactura>" + vigenciaHasta + "</fechaVencimientoFactura>");
					emisionXML.append("<TipoDocumentoFac>1</TipoDocumentoFac>");
					emisionXML.append("<numeroFactura></numeroFactura>");
					emisionXML.append("<autorizacionSRI></autorizacionSRI>");
					emisionXML.append("<contenedorId>" + contenedor.getIdEnsurance() + "</contenedorId>");
					emisionXML.append("<numeroPoliza>" + cotizacion.getId() + "</numeroPoliza>");
					emisionXML.append("<puntoVentaId>" + cotizacion.getPuntoVenta().getPtoEnsurance() + "</puntoVentaId>");
					emisionXML.append("<tipoRiesgoId>" + ramo.getTipoRiesgos().get(0).getId() + "</tipoRiesgoId>");
					emisionXML.append("<claseRiesgoId>" + ramo.getClaseRiesgos().get(0).getId() + "</claseRiesgoId>");
					emisionXML.append("<cobradorId>1</cobradorId>");
					emisionXML.append("<tipoItemId>110</tipoItemId>");
					emisionXML.append("<monedaId>11141120</monedaId>");
					emisionXML.append("<plantillaId></plantillaId>");
					FirmasDigitalesDAO fdDAO = new FirmasDigitalesDAO();
					EntidadDAO entidadDAO = new EntidadDAO();
					Entidad entidadUP = entidadDAO.buscarPorId(productoXPuntoVenta.getUnidadProduccion().getEntidadId());
					FirmasDigitales fd = fdDAO.buscarPorRamoSucursalEntidad(ramo, cotizacion.getPuntoVenta().getSucursal(),	entidadUP);
					emisionXML.append("<firmaDigitalId>" + fd.getIdEnsurance() + "</firmaDigitalId>");
					emisionXML.append("<sistemaEmisorId>" + entidadEmisor.getEntEnsurance() + "</sistemaEmisorId>");
					emisionXML.append("<sistemaEmisor>" + entidadEmisor.getNombreCompleto() + "</sistemaEmisor>");
					emisionXML.append("<usuarioActualiza>" + usuarioActualiza + "</usuarioActualiza>");
					emisionXML.append("<ramoid>" + ramo.getId() + "</ramoid>");
					emisionXML.append("<ramo>" + ramo.getNemotecnico() + "</ramo>");
					emisionXML.append("<unidadNegocioId>" + productoXPuntoVenta.getUnidadNegocio().getUnEnsurance() + "</unidadNegocioId>");
					emisionXML.append("<unidadProduccionId>" + productoXPuntoVenta.getUnidadProduccion().getUpEnsurance() + "</unidadProduccionId>");
					emisionXML.append("<sucursalId>" + cotizacion.getPuntoVenta().getSucursal().getSucEnsurance() + "</sucursalId>");
					emisionXML.append("<vigenciaDesde>" + cotizacion.getVigenciaDesde().getTime() / 1000 + "</vigenciaDesde>");
					emisionXML.append("<vigenciaHasta>" + vigenciaHasta / 1000 + "</vigenciaHasta>");
					emisionXML.append("<configPago>");
					emisionXML.append("<tipoPagoId>" + pago.getFormaPago().getCodigoEnsurance() + "</tipoPagoId>");
					emisionXML.append("<numeroPagos>" + pago.getPlazonEnMes() + "</numeroPagos>");
					emisionXML.append("<numeroCuenta>" + numeroCuenta + "</numeroCuenta>");
					emisionXML.append("<tipoCuenta>" + tipoCuenta + "</tipoCuenta>");
					emisionXML.append("<convenioPagoId>" + convenioPago + "</convenioPagoId>");
					emisionXML.append("<institucionADebitar>" + institucionADebitar + "</institucionADebitar>");
					emisionXML.append("<FechaVencimientoTarjeta>" + fechaVencimientoTarjeta + "</FechaVencimientoTarjeta>");
					emisionXML.append(pagosRealizados);
					emisionXML.append("</configPago>");
					emisionXML.append(xmlEntidad(cliente,
					TipoEntidadEnum.CLIENTE)); // Agregamos tag cliente

					if (cotizacion.getAsegurado() == null) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.ASEGURADO)); // No existe asegurado
					else emisionXML.append(xmlEntidadAsegurado(cotizacion.getAsegurado())); // Existe el asegurado

					EndosoBeneficiarioDAO endosoBeneficiarioDAO = new EndosoBeneficiarioDAO();
					EndosoBeneficiario endosoBeneficiario = endosoBeneficiarioDAO.buscarPorCotizacion(cotizacion);

					if (endosoBeneficiario == null) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.BENEFICIARIOS)); // No existe Beneficiario
					else {
						Double valorBeneficiarioPorcentaje = (endosoBeneficiario.getMonto() * 100)/cotizacion.getTotalFactura();
						emisionXML.append(xmlEntidadBeneficiario(endosoBeneficiario,valorBeneficiarioPorcentaje.intValue())); // Agregamos el tag beneficiarios						
					}
					emisionXML.append(xmlEntidad(cliente,
					TipoEntidadEnum.PROPIETARIO)); // Agregamos el tag propietario

					if (formaPago.equalsIgnoreCase("CONTADO") || formaPago.equalsIgnoreCase("CREDITOCUOTAS")) emisionXML.append(xmlEntidadVacio(TipoEntidadEnum.PROPIETARIOCUENTA)); // No existe propietario cuenta
					else emisionXML.append(xmlEntidadPropietarioCuenta(cotizacion.getPago())); // Agregamos el tag propietario cuenta

					emisionXML.append("<itemV>");
					emisionXML.append(xmlVehiculos); // Datos del Vehiculo
					emisionXML.append("</itemV>");
					emisionXML.append("</superObjetoXML>");
					String emisionValor = emisionXML.toString();
					//System.out.println(emisionValor);
					SolicitudEmisionDAO seDAO=new SolicitudEmisionDAO();
					SolicitudEmision se = new SolicitudEmision();
					se.setCotizacionId(cotizacionId);
					se.setXml(emisionValor);
					System.out.println(emisionValor);
					String respuesta = emisionQBE.getEmisionQbeLinkPort().emisionGeneral(emisionValor, "");
					se.setRespuesta(respuesta);
					solicitudEmisionTransaction.crear(se);
					System.out.println(respuesta);
					result.put("respuesta", respuesta);
					if (!cotizacion.getEmitirProgramaSeguros()&&respuesta.split("::")[0].length() == 17) {
						EstadoDAO estadoDAO = new EstadoDAO();
						cotizacion.setNumeroFactura(respuesta.split("::")[0]);
						result.put("numeroFactura", respuesta.split("::")[0]);
						result.put("programaSeguros", false);
						cotizacion.setFechaEmision(new Timestamp(new java.util.Date().getTime()));
						cotizacion.setEstado(estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"));
						cotizacion.setEtapaWizard(5);
						cotizacion = cotizacionTransaction.editar(cotizacion);
					}
					else{
						
						if(cotizacion.getEmitirProgramaSeguros()&&respuesta.contains("::")){
							EstadoDAO estadoDAO = new EstadoDAO();
							cotizacion.setNumeroFactura("Programa de Seguros");
							result.put("numeroFactura", "");
							result.put("programaSeguros", true);
							cotizacion.setFechaEmision(new Timestamp(new java.util.Date().getTime()));
							cotizacion.setEstado(estadoDAO.buscarPorNombreClase("Emitido", "Cotizacion"));
							cotizacion.setEtapaWizard(5);
							cotizacion = cotizacionTransaction.editar(cotizacion);
						}
						else
						result.put("error", respuesta);
						}
					
					result.put("success", Boolean.TRUE);
					response.setContentType("application/json; charset=ISO-8859-1");
					result.write(response.getWriter());

				}
			}
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1");
			result.write(response.getWriter());
			e.printStackTrace();

		}

	}

	public String xmlVehiculos(List < CotizacionDetalle > cotizacionesDetalle, Cliente cliente, Boolean vehiculoCerrado) {

		List < ObjetoVehiculo > listadoVehiculo = new ArrayList < ObjetoVehiculo > ();
		ObjetoVehiculoDAO objetoVehiculoDAO = new ObjetoVehiculoDAO();
		String vehiculos = "";
		for (CotizacionDetalle cotizacionDetalle: cotizacionesDetalle) {
			ObjetoVehiculo objeto = objetoVehiculoDAO.buscarPorId(cotizacionDetalle.getObjetoId());
			vehiculos += "<VehiculoDTO>";
			vehiculos += "<vehiculoId>" + objeto.getCodigoEnsurance() + "</vehiculoId>";
			vehiculos += "<tipoVehiculoId>" + objeto.getTipoVehiculo().getTipVhEnsurance() + "</tipoVehiculoId>";
			vehiculos += "<marcaId>" + objeto.getModelo().getMarca().getMarEnsurance() + "</marcaId>";
			vehiculos += "<modeloId>" + objeto.getModelo().getModEnsurance() + "</modeloId>";
			vehiculos += "<motor>" + objeto.getMotor() + "</motor>";
			vehiculos += "<chasis>" + objeto.getChasis() + "</chasis>";
			vehiculos += "<tipousoId>" + objeto.getTipoUso().getTipoEnsurance() + "</tipousoId>";
			vehiculos += "<anoFabricacion>" + objeto.getAnioFabricacion() + "</anoFabricacion>";
			vehiculos += "<placas>" + objeto.getPlaca() + "</placas>";
			vehiculos += "<valorPrimaExtras>0</valorPrimaExtras>"; // verificar
			vehiculos += "<nombreMarca>" + objeto.getModelo().getMarca().getNombre() + "</nombreMarca>";
			vehiculos += "<nombreModelo>" + objeto.getModelo().getNombre() + "</nombreModelo>";
			vehiculos += "<colorId>" + objeto.getColor().getColEnsurance() + "</colorId>";
			vehiculos += "<nombreColor>" + objeto.getColor().getNombre() + "</nombreColor>";
			vehiculos += "<autoAsistencia>0</autoAsistencia>"; // verificar
			vehiculos += "<claseVehiculoId>2</claseVehiculoId>";
			vehiculos += "<propietarioVehiculo>" + cliente.getEntidad().getNombreCompleto() + "</propietarioVehiculo>";
			vehiculos += "<valorAseguradoVehiculo>" + (cotizacionDetalle.getSumaAseguradaItem()) + "</valorAseguradoVehiculo>";
			vehiculos += "<valorPrimaVehiculo>" + cotizacionDetalle.getPrimaNetaItem() + "</valorPrimaVehiculo>";
			vehiculos += "<ocupantes></ocupantes>";
			//xmlextras
			vehiculos += xmlExtras(objeto);

			// Obtenemos todas las coberturas de cada uno de los vehiculos
			if (vehiculoCerrado == false) {
				vehiculos += "<coberturasAdicionales>";
				CotizacionCoberturaDAO cotizacionCoberturaDAO = new CotizacionCoberturaDAO();
				List < CotizacionCobertura > coberturas = cotizacionCoberturaDAO.buscarPorCotizacionDetalle(cotizacionDetalle);
				for (CotizacionCobertura cotizacionCobertura: coberturas) {

					vehiculos += "<CoberturaDTO>";
					vehiculos += "<id>" + cotizacionCobertura.getCobertura().getId() + "</id>";
					vehiculos += "<valorPrima>" + cotizacionCobertura.getValorPrima() + "</valorPrima>";
					vehiculos += "<valorMonto>" + cotizacionCobertura.getValorMonto() + "</valorMonto>";
					vehiculos += "<servicio>false</servicio>";
					vehiculos += "<restaPrincipal>false</restaPrincipal>";
					vehiculos += "<afectaMonto>" + cotizacionCobertura.getCobertura().getAfectaValorAsegurado().equals("1") + "</afectaMonto>";
					vehiculos += "<fincober>1</fincober>";
					vehiculos += "</CoberturaDTO>";


				}
				vehiculos += "</coberturasAdicionales>";
			}

			vehiculos += xmlEntidad(cliente, TipoEntidadEnum.CONDUCTORES);

			vehiculos += "<finV>1</finV>";
			vehiculos += "</VehiculoDTO>";
		}
		return vehiculos;
	}

	public String xmlEntidad(Cliente cliente, TipoEntidadEnum tipoEntidad) {
		String entidad = "";
		String entidadInicio = "";
		String entidadFin = "";
		String finEntidadTag = "";

		if (tipoEntidad == TipoEntidadEnum.CLIENTE) {
			entidadInicio = "<cliente>";
			entidadFin = "</cliente>";
			finEntidadTag = "<finclie>1</finclie>";
		}

		if (tipoEntidad == TipoEntidadEnum.BENEFICIARIOS) {
			entidadInicio = "<beneficiarios>";
			entidadFin = "</beneficiarios>";
			finEntidadTag = "<finBene>1</finBene>";
		}
		if (tipoEntidad == TipoEntidadEnum.PROPIETARIO) {
			entidadInicio = "<propietario>";
			entidadFin = "</propietario>";
			finEntidadTag = "<finProp>1</finProp>";
		}
		if (tipoEntidad == TipoEntidadEnum.PROPIETARIOCUENTA) {
			entidadInicio = "<propietarioCuenta>";
			entidadFin = "</propietarioCuenta>";
			finEntidadTag = "<finprocue>1</finprocue>";
		}
		if (tipoEntidad == TipoEntidadEnum.CONDUCTORES) {
			entidadInicio = "<conductores>";
			entidadFin = "</conductores>";
			finEntidadTag = "<finC>1</finC>";
		}

		DireccionDAO direccionDAO = new DireccionDAO();
		Direccion direccion = new Direccion();
		List < Direccion > direcciones = direccionDAO.buscarCobroPorEntidadId(cliente.getEntidad());
		if (direcciones.size() > 0) {
			direccion = direcciones.get(0);
		} else {
			direcciones = direccionDAO.buscarPorEntidadId(cliente.getEntidad());
			if (direcciones.size() > 0) direccion = direcciones.get(0);
		}



		if (tipoEntidad == TipoEntidadEnum.CLIENTE || tipoEntidad == TipoEntidadEnum.PROPIETARIO || tipoEntidad == TipoEntidadEnum.PROPIETARIOCUENTA || tipoEntidad == TipoEntidadEnum.BENEFICIARIOS) {
			entidad += entidadInicio;
			// Validacion Entidad - Natural
			if (cliente.getEntidad().getTipoIdentificacion().getId().equals("1") || cliente.getEntidad().getTipoIdentificacion().getId().equals("2") || cliente.getEntidad().getTipoIdentificacion().getId().equals("3")) {
				entidad += "<id></id>";
				entidad += "<entidadId>" + (cliente.getEntidad().getEntEnsurance() == null ? "" : cliente.getEntidad().getEntEnsurance()) + "</entidadId>";
				entidad += "<nombres>" + (cliente.getEntidad().getNombres() == null ? "" : cliente.getEntidad().getNombres()) + "</nombres>";
				entidad += "<apellidos>" + (cliente.getEntidad().getApellidos() == null ? "" : cliente.getEntidad().getApellidos()) + "</apellidos>";
				entidad += "<identificacion>" + (cliente.getEntidad().getIdentificacion() == null ? "" : cliente.getEntidad().getIdentificacion()) + "</identificacion>";
				entidad += "<correoElectronico>" + (cliente.getEntidad().getMail() == null ? "" : cliente.getEntidad().getMail()) + "</correoElectronico>";
				entidad += "<tipoEmpresaId></tipoEmpresaId>";
				entidad += "<esPrimaExcenta></esPrimaExcenta>";
				entidad += "<tipoIdentificacion>" + cliente.getEntidad().getTipoIdentificacion().getNombre().substring(0, 1).toLowerCase() + "</tipoIdentificacion>";
				entidad += "<tipoEntidad>1</tipoEntidad>";
				entidad += "<genero>m</genero>";
				entidad += "<fechaNacimiento></fechaNacimiento>";
				entidad += "<esEmpresa>false</esEmpresa>";
				entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
				entidad += "<sectorEmpresaId></sectorEmpresaId>";
				entidad += "<telefonoCasa>" + (cliente.getEntidad().getTelefono() == null ? "" : cliente.getEntidad().getTelefono()) + "</telefonoCasa>";
				entidad += "<telefonoOficina></telefonoOficina>";
				entidad += "<telefonoCelular>" + (cliente.getEntidad().getCelular() == null ? "" : cliente.getEntidad().getCelular()) + "</telefonoCelular>";
				entidad += "<sectorEmpresa></sectorEmpresa>";
				entidad += "<tituloAcademico></tituloAcademico>";
				entidad += "<webSite></webSite>";
				entidad += "<nacionalidad></nacionalidad>";
				entidad += "<limiteCredito>0</limiteCredito>";
				entidad += "<tipoEmpleado></tipoEmpleado>";
				entidad += "<nombreEmpresa></nombreEmpresa>";
				entidad += "<nombreCorto></nombreCorto>";
				entidad += "<actividadEconomica>" + (cliente.getActividadEconomica().getActEnsurance()) + "</actividadEconomica>";
				entidad += "<employeeType></employeeType>";
				entidad += "<income></income>";
				entidad += "<totalEgresos></totalEgresos>";
				entidad += "<otrosIngresos></otrosIngresos>";
				entidad += "<fuente>Masivos</fuente>";
				entidad += "<birthPlace></birthPlace>";
				entidad += "<representanteLegal>";
				entidad += "<id></id>";
				entidad += "<entidadId></entidadId>";
				entidad += "<nombres></nombres>";
				entidad += "<apellidos></apellidos>";
				entidad += "<identificacion></identificacion>";
				entidad += "<correoElectronico></correoElectronico>";
				entidad += "<tipoEmpresaId></tipoEmpresaId>";
				entidad += "<esPrimaExcenta></esPrimaExcenta>";
				entidad += "<tipoIdentificacion></tipoIdentificacion>";
				entidad += "<tipoEntidad></tipoEntidad>";
				entidad += "<genero>m</genero>";
				entidad += "<fechaNacimiento></fechaNacimiento>";
				entidad += "<esEmpresa>true</esEmpresa>";
				entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
				entidad += "<sectorEmpresaId></sectorEmpresaId>";
				entidad += "<telefonoCasa></telefonoCasa>";
				entidad += "<telefonoOficina></telefonoOficina>";
				entidad += "<telefonoCelular></telefonoCelular>";
				entidad += "<sectorEmpresa></sectorEmpresa>";
				entidad += "<tituloAcademico></tituloAcademico>";
				entidad += "<webSite></webSite>";
				entidad += "<nacionalidad></nacionalidad>";
				entidad += "<limiteCredito></limiteCredito>";
				entidad += "<tipoEmpleado></tipoEmpleado>";
				entidad += "<nombreEmpresa></nombreEmpresa>";
				entidad += "<nombreCorto></nombreCorto>";
				entidad += "<actividadEconomica></actividadEconomica>";
				entidad += "<employeeType></employeeType>";
				entidad += "<income></income>";
				entidad += "<totalEgresos></totalEgresos>";
				entidad += "<otrosIngresos></otrosIngresos>";
				entidad += "<fuente></fuente>";
				entidad += "<birthPlace></birthPlace>";
				entidad += "<socialObject></socialObject>";
				entidad += "<telefonoEmpresa></telefonoEmpresa>";
				entidad += "<faxEmpresa></faxEmpresa>";
				entidad += "<nombresConyuge></nombresConyuge>";
				entidad += "<apellidosConyuge></apellidosConyuge>";
				entidad += "<actividadEconomicaId></actividadEconomicaId>";
				entidad += "<grupoEconomicoId></grupoEconomicoId>";
				entidad += "</representanteLegal>";
				entidad += "<socialObject></socialObject>";
				entidad += "<telefonoEmpresa></telefonoEmpresa>";
				entidad += "<faxEmpresa></faxEmpresa>";
				entidad += "<nombresConyuge></nombresConyuge>";
				entidad += "<apellidosConyuge></apellidosConyuge>";
				entidad += "<actividadEconomicaId></actividadEconomicaId>";
				entidad += "<grupoEconomicoId></grupoEconomicoId>";
				entidad += "<NumDirecciones>1</NumDirecciones>";
				entidad += "<DireccionDTO>";

				// Validaciones campos nulos
				String callePrincipal = "-1";
				String calleSecundaria = "-1";
				String ciudad = "-1";
				String provincia = "-1";
				String pais = "-1";
				String parroquia = "-1";
				String canton = "-1";
				String numero = "-1";

				if (direccion.getCallePrincipal() != null) {
					callePrincipal = direccion.getCallePrincipal();
					calleSecundaria = direccion.getCalleSecundaria();
				}

				if (direccion.getCiudad() != null) {
					ciudad = direccion.getCiudad().getId();
					provincia = direccion.getCiudad().getProvincia().getCodigoSbs();
					pais = direccion.getCiudad().getProvincia().getPais().getId();
				}
				if (direccion.getParroquia() != null) {
					parroquia = direccion.getParroquia().getId();
					canton = direccion.getParroquia().getCanton().getId();
					provincia = direccion.getParroquia().getCanton().getProvincia().getCodigoSbs();
					pais = direccion.getParroquia().getCanton().getProvincia().getPais().getId();
					ciudad=direccion.getParroquia().getCanton().getProvincia().getCapitalId();
				}
				if (direccion.getNumero() != null) numero = direccion.getNumero();


				entidad += "<direccion>" + callePrincipal + " " + calleSecundaria + "</direccion>";
				entidad += "<callePrincipal>" + callePrincipal + "</callePrincipal>";
				entidad += "<calleSecundaria>" + calleSecundaria + "</calleSecundaria>";
				entidad += "<ciudadId>" + ciudad + "</ciudadId>";
				entidad += "<provinciaId>" + provincia + "</provinciaId>";
				entidad += "<paisId>" + pais + "</paisId>";
				entidad += "<parroquiaId>" + parroquia + "</parroquiaId>";
				entidad += "<cantonId>" + canton + "</cantonId>";
				entidad += "<numero>" + numero + "</numero>";
				entidad += "<tipoDireccion>3</tipoDireccion>";
				entidad += "<findir>1</findir>";
				entidad += "</DireccionDTO>";
				//entidad +=finEntidadTag; 
				//entidad +=entidadFin;

			}

			// Validacion Entidad - Natural
			if (cliente.getEntidad().getTipoIdentificacion().getId().equals("4")) {
				entidad += "<id></id>";
				entidad += "<entidadId>" + (cliente.getEntidad().getEntEnsurance() == null ? "" : cliente.getEntidad().getEntEnsurance()) + "</entidadId>";
				entidad += "<nombres>" + (cliente.getEntidad().getNombreCompleto() == null ? "" : cliente.getEntidad().getNombreCompleto()) + "</nombres>";
				//entidad +="<apellidos>"+(cliente.getEntidad().getApellidos()==null?"":cliente.getEntidad().getApellidos())+"</apellidos>"; 
				entidad += "<identificacion>" + (cliente.getEntidad().getIdentificacion() == null ? "" : cliente.getEntidad().getIdentificacion()) + "</identificacion>";
				entidad += "<correoElectronico>" + (cliente.getEntidad().getMail() == null ? "" : cliente.getEntidad().getMail()) + "</correoElectronico>";
				entidad += "<tipoEmpresaId></tipoEmpresaId>";
				entidad += "<esPrimaExcenta></esPrimaExcenta>";
				entidad += "<tipoIdentificacion>" + cliente.getEntidad().getTipoIdentificacion().getNombre().substring(0, 1).toLowerCase() + "</tipoIdentificacion>";

				entidad += "<tipoEntidad>2</tipoEntidad>";
				entidad += "<genero>m</genero>";
				entidad += "<fechaNacimiento></fechaNacimiento>";
				entidad += "<esEmpresa>true</esEmpresa>";
				entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
				entidad += "<sectorEmpresaId></sectorEmpresaId>";
				entidad += "<telefonoCasa></telefonoCasa>";
				entidad += "<telefonoOficina></telefonoOficina>";
				entidad += "<telefonoCelular></telefonoCelular>";
				entidad += "<sectorEmpresa></sectorEmpresa>";
				entidad += "<tituloAcademico></tituloAcademico>";
				entidad += "<webSite></webSite>";
				entidad += "<nacionalidad></nacionalidad>";
				entidad += "<limiteCredito>0</limiteCredito>";
				entidad += "<tipoEmpleado></tipoEmpleado>";
				entidad += "<nombreEmpresa></nombreEmpresa>";
				entidad += "<nombreCorto></nombreCorto>";
				entidad += "<actividadEconomica></actividadEconomica>";
				entidad += "<employeeType></employeeType>";
				entidad += "<income></income>";
				entidad += "<totalEgresos></totalEgresos>";
				entidad += "<otrosIngresos>0</otrosIngresos>";
				entidad += "<fuente>Masivos</fuente>";
				entidad += "<birthPlace></birthPlace>";
				entidad += "<representanteLegal>";
				entidad += "<id></id>";
				entidad += "<entidadId></entidadId>";
				entidad += "<nombres></nombres>";
				entidad += "<apellidos></apellidos>";
				entidad += "<identificacion></identificacion>";
				entidad += "<correoElectronico></correoElectronico>";
				entidad += "<tipoEmpresaId></tipoEmpresaId>";
				entidad += "<esPrimaExcenta></esPrimaExcenta>";
				entidad += "<tipoIdentificacion></tipoIdentificacion>";
				entidad += "<tipoEntidad>1</tipoEntidad>";
				entidad += "<genero>m</genero>";
				entidad += "<fechaNacimiento></fechaNacimiento>";
				entidad += "<esEmpresa>false</esEmpresa>";
				entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
				entidad += "<sectorEmpresaId></sectorEmpresaId>";
				entidad += "<telefonoCasa></telefonoCasa>";
				entidad += "<telefonoOficina></telefonoOficina>";
				entidad += "<telefonoCelular></telefonoCelular>";
				entidad += "<sectorEmpresa></sectorEmpresa>";
				entidad += "<tituloAcademico></tituloAcademico>";
				entidad += "<webSite></webSite>";
				entidad += "<nacionalidad></nacionalidad>";
				entidad += "<limiteCredito></limiteCredito>";
				entidad += "<tipoEmpleado></tipoEmpleado>";
				entidad += "<nombreEmpresa></nombreEmpresa>";
				entidad += "<nombreCorto></nombreCorto>";
				entidad += "<actividadEconomica></actividadEconomica>";
				entidad += "<employeeType></employeeType>";
				entidad += "<income></income>";
				entidad += "<totalEgresos></totalEgresos>";
				entidad += "<otrosIngresos></otrosIngresos>";
				entidad += "<fuente>masivos</fuente>";
				entidad += "<birthPlace></birthPlace>";
				entidad += "<socialObject></socialObject>";
				entidad += "<telefonoEmpresa></telefonoEmpresa>";
				entidad += "<faxEmpresa></faxEmpresa>";
				entidad += "<nombresConyuge></nombresConyuge>";
				entidad += "<apellidosConyuge></apellidosConyuge>";
				entidad += "<actividadEconomicaId></actividadEconomicaId>";
				entidad += "<grupoEconomicoId></grupoEconomicoId>";
				entidad += "</representanteLegal>";
				entidad += "<socialObject></socialObject>";
				entidad += "<telefonoEmpresa></telefonoEmpresa>";
				entidad += "<faxEmpresa></faxEmpresa>";
				entidad += "<nombresConyuge></nombresConyuge>";
				entidad += "<apellidosConyuge></apellidosConyuge>";
				entidad += "<actividadEconomicaId></actividadEconomicaId>";
				entidad += "<grupoEconomicoId></grupoEconomicoId>";
				entidad += "<NumDirecciones>1</NumDirecciones>";
				entidad += "<DireccionDTO>";

				// Validaciones campos nulos
				String callePrincipal = "-1";
				String calleSecundaria = "-1";
				String ciudad = "-1";
				String provincia = "-1";
				String pais = "-1";
				String parroquia = "-1";
				String canton = "-1";
				String numero = "-1";

				if (direccion.getCallePrincipal() != null) {
					callePrincipal = direccion.getCallePrincipal();
					calleSecundaria = direccion.getCalleSecundaria();
				}

				if (direccion.getCiudad() != null) {
					ciudad = direccion.getCiudad().getId();
					provincia = direccion.getCiudad().getProvincia().getCodigoSbs();
					pais = direccion.getCiudad().getProvincia().getPais().getId();
				}
				if (direccion.getParroquia() != null) {
					parroquia = direccion.getParroquia().getId();
					canton = direccion.getParroquia().getCanton().getId();
					provincia = direccion.getParroquia().getCanton().getProvincia().getCodigoSbs();
					pais = direccion.getParroquia().getCanton().getProvincia().getPais().getId();
					ciudad=direccion.getParroquia().getCanton().getProvincia().getCapitalId();
				}
				if (direccion.getNumero() != null) numero = direccion.getNumero();


				entidad += "<direccion>" + callePrincipal + " " + calleSecundaria + "</direccion>";
				entidad += "<callePrincipal>" + callePrincipal + "</callePrincipal>";
				entidad += "<calleSecundaria>" + calleSecundaria + "</calleSecundaria>";
				entidad += "<ciudadId>" + ciudad + "</ciudadId>";
				entidad += "<provinciaId>" + provincia + "</provinciaId>";
				entidad += "<paisId>" + pais + "</paisId>";
				entidad += "<parroquiaId>" + parroquia + "</parroquiaId>";
				entidad += "<cantonId>" + canton + "</cantonId>";
				entidad += "<numero>" + numero + "</numero>";
				entidad += "<tipoDireccion>3</tipoDireccion>";
				entidad += "<findir>1</findir>";
				entidad += "</DireccionDTO>";
				//entidad +=finEntidadTag; 
				//entidad +=entidadFin;

			}
			entidad += finEntidadTag;
			entidad += entidadFin;
		}


		return entidad;
	}


	public String xmlEntidadAsegurado(Entidad entidadAsegurado) {

		String entidad = "";
		String entidadInicio = "<asegurado>";
		String entidadFin = "</asegurado>";
		String finEntidadTag = "<finasegu>1</finasegu>";

		DireccionDAO direccionDAO = new DireccionDAO();
		Direccion direccion = new Direccion();
		List < Direccion > direcciones = direccionDAO.buscarCobroPorEntidadId(entidadAsegurado);
		if (direcciones.size() > 0) {
			direccion = direcciones.get(0);
		} else {
			direcciones = direccionDAO.buscarPorEntidadId(entidadAsegurado);
			if (direcciones.size() > 0) direccion = direcciones.get(0);
		}

		entidad += entidadInicio;
		// Validacion Entidad - Natural
		if (entidadAsegurado.getTipoIdentificacion().getId().equals("1") || entidadAsegurado.getTipoIdentificacion().getId().equals("2") || entidadAsegurado.getTipoIdentificacion().getId().equals("3")) {
			entidad += "<id></id>";
			entidad += "<entidadId>" + (entidadAsegurado.getEntEnsurance() == null ? "" : entidadAsegurado.getEntEnsurance()) + "</entidadId>";
			entidad += "<nombres>" + (entidadAsegurado.getNombres() == null ? "" : entidadAsegurado.getNombres()) + "</nombres>";
			entidad += "<apellidos>" + (entidadAsegurado.getApellidos() == null ? "" : entidadAsegurado.getApellidos()) + "</apellidos>";
			entidad += "<identificacion>" + (entidadAsegurado.getIdentificacion() == null ? "" : entidadAsegurado.getIdentificacion()) + "</identificacion>";
			entidad += "<correoElectronico>" + (entidadAsegurado.getMail() == null ? "" : entidadAsegurado.getMail()) + "</correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion>" + entidadAsegurado.getTipoIdentificacion().getNombre().substring(0, 1).toLowerCase() + "</tipoIdentificacion>";
			entidad += "<tipoEntidad>1</tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>false</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa>" + (entidadAsegurado.getTelefono() == null ? "" : entidadAsegurado.getTelefono()) + "</telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular>" + (entidadAsegurado.getCelular() == null ? "" : entidadAsegurado.getCelular()) + "</telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito>0</limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos></otrosIngresos>";
			entidad += "<fuente>Masivos</fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<representanteLegal>";
			entidad += "<id></id>";
			entidad += "<entidadId></entidadId>";
			entidad += "<nombres></nombres>";
			entidad += "<apellidos></apellidos>";
			entidad += "<identificacion></identificacion>";
			entidad += "<correoElectronico></correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion></tipoIdentificacion>";
			entidad += "<tipoEntidad></tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>true</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa></telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular></telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito></limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos></otrosIngresos>";
			entidad += "<fuente></fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "</representanteLegal>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "<NumDirecciones>1</NumDirecciones>";
			entidad += "<DireccionDTO>";

			// Validaciones campos nulos
			String callePrincipal = "-1";
			String calleSecundaria = "-1";
			String ciudad = "-1";
			String provincia = "-1";
			String pais = "-1";
			String parroquia = "-1";
			String canton = "-1";
			String numero = "-1";

			if (direccion.getCallePrincipal() != null) {
				callePrincipal = direccion.getCallePrincipal();
				calleSecundaria = direccion.getCalleSecundaria();
			}

			if (direccion.getCiudad() != null) {
				ciudad = direccion.getCiudad().getId();
				provincia = direccion.getCiudad().getProvincia().getCodigoSbs();
				pais = direccion.getCiudad().getProvincia().getPais().getId();
			}
			if (direccion.getParroquia() != null) {
				parroquia = direccion.getParroquia().getId();
				canton = direccion.getParroquia().getCanton().getId();
				provincia = direccion.getParroquia().getCanton().getProvincia().getCodigoSbs();
				pais = direccion.getParroquia().getCanton().getProvincia().getPais().getId();
				ciudad=direccion.getParroquia().getCanton().getProvincia().getCapitalId();
			}
			if (direccion.getNumero() != null) numero = direccion.getNumero();


			entidad += "<direccion>" + callePrincipal + " " + calleSecundaria + "</direccion>";
			entidad += "<callePrincipal>" + callePrincipal + "</callePrincipal>";
			entidad += "<calleSecundaria>" + calleSecundaria + "</calleSecundaria>";
			entidad += "<ciudadId>" + ciudad + "</ciudadId>";
			entidad += "<provinciaId>" + provincia + "</provinciaId>";
			entidad += "<paisId>" + pais + "</paisId>";
			entidad += "<parroquiaId>" + parroquia + "</parroquiaId>";
			entidad += "<cantonId>" + canton + "</cantonId>";
			entidad += "<numero>" + numero + "</numero>";
			entidad += "<tipoDireccion>3</tipoDireccion>";
			entidad += "<findir>1</findir>";
			entidad += "</DireccionDTO>";
			//entidad +=finEntidadTag; 
			//entidad +=entidadFin;

		}

		// Validacion Entidad - Natural
		if (entidadAsegurado.getTipoIdentificacion().getId().equals("4")) {
			entidad += "<id></id>";
			entidad += "<entidadId>" + (entidadAsegurado.getEntEnsurance() == null ? "" : entidadAsegurado.getEntEnsurance()) + "</entidadId>";
			entidad += "<nombres>" + (entidadAsegurado.getNombreCompleto() == null ? "" : entidadAsegurado.getNombreCompleto()) + "</nombres>";
			//entidad +="<apellidos>"+(cliente.getEntidad().getApellidos()==null?"":cliente.getEntidad().getApellidos())+"</apellidos>"; 
			entidad += "<identificacion>" + (entidadAsegurado.getIdentificacion() == null ? "" : entidadAsegurado.getIdentificacion()) + "</identificacion>";
			entidad += "<correoElectronico>" + (entidadAsegurado.getMail() == null ? "" : entidadAsegurado.getMail()) + "</correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion>" + entidadAsegurado.getTipoIdentificacion().getNombre().substring(0, 1).toLowerCase() + "</tipoIdentificacion>";

			entidad += "<tipoEntidad>2</tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>true</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa></telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular></telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito>0</limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos>0</otrosIngresos>";
			entidad += "<fuente>Masivos</fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<representanteLegal>";
			entidad += "<id></id>";
			entidad += "<entidadId></entidadId>";
			entidad += "<nombres></nombres>";
			entidad += "<apellidos></apellidos>";
			entidad += "<identificacion></identificacion>";
			entidad += "<correoElectronico></correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion></tipoIdentificacion>";
			entidad += "<tipoEntidad>1</tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>false</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa></telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular></telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito></limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos></otrosIngresos>";
			entidad += "<fuente>masivos</fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "</representanteLegal>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "<NumDirecciones>1</NumDirecciones>";
			entidad += "<DireccionDTO>";

			// Validaciones campos nulos
			String callePrincipal = "-1";
			String calleSecundaria = "-1";
			String ciudad = "-1";
			String provincia = "-1";
			String pais = "-1";
			String parroquia = "-1";
			String canton = "-1";
			String numero = "-1";

			if (direccion.getCallePrincipal() != null) {
				callePrincipal = direccion.getCallePrincipal();
				calleSecundaria = direccion.getCalleSecundaria();
			}

			if (direccion.getCiudad() != null) {
				ciudad = direccion.getCiudad().getId();
				provincia = direccion.getCiudad().getProvincia().getCodigoSbs();
				pais = direccion.getCiudad().getProvincia().getPais().getId();
			}
			if (direccion.getParroquia() != null) {
				parroquia = direccion.getParroquia().getId();
				canton = direccion.getParroquia().getCanton().getId();
				provincia = direccion.getParroquia().getCanton().getProvincia().getCodigoSbs();
				pais = direccion.getParroquia().getCanton().getProvincia().getPais().getId();
				ciudad=direccion.getParroquia().getCanton().getProvincia().getCapitalId();
			}
			if (direccion.getNumero() != null) numero = direccion.getNumero();


			entidad += "<direccion>" + callePrincipal + " " + calleSecundaria + "</direccion>";
			entidad += "<callePrincipal>" + callePrincipal + "</callePrincipal>";
			entidad += "<calleSecundaria>" + calleSecundaria + "</calleSecundaria>";
			entidad += "<ciudadId>" + ciudad + "</ciudadId>";
			entidad += "<provinciaId>" + provincia + "</provinciaId>";
			entidad += "<paisId>" + pais + "</paisId>";
			entidad += "<parroquiaId>" + parroquia + "</parroquiaId>";
			entidad += "<cantonId>" + canton + "</cantonId>";
			entidad += "<numero>" + numero + "</numero>";
			entidad += "<tipoDireccion>3</tipoDireccion>";
			entidad += "<findir>1</findir>";
			entidad += "</DireccionDTO>";
			//entidad +=finEntidadTag; 
			//entidad +=entidadFin;

		}
		entidad += finEntidadTag;
		entidad += entidadFin;

		return entidad;
	}

	public String xmlEntidadPropietarioCuenta(Pago pago) {
		String entidad = "";
		String entidadInicio = "<propietarioCuenta>";
		String entidadFin = "</propietarioCuenta>";
		String finEntidadTag = "<finprocue>1</finprocue>";

		entidad += entidadInicio;

		entidad += "<id></id>";
		entidad += "<entidadId></entidadId>";
		entidad += "<nombres>" + (pago.getNombreTitular() == null ? "" : pago.getNombreTitular()) + "</nombres>";
		entidad += "<apellidos></apellidos>";
		entidad += "<identificacion>" + (pago.getIdentificacionTitular() == null ? "" : pago.getIdentificacionTitular()) + "</identificacion>";
		entidad += "<correoElectronico></correoElectronico>";
		entidad += "<tipoEmpresaId></tipoEmpresaId>";
		entidad += "<esPrimaExcenta></esPrimaExcenta>";
		entidad += "<tipoIdentificacion>" + pago.getTipoIdentificacionId().getNombre().substring(0, 1).toLowerCase() + "</tipoIdentificacion>";
		entidad += "<tipoEntidad>1</tipoEntidad>";
		entidad += "<genero>m</genero>";
		entidad += "<fechaNacimiento></fechaNacimiento>";
		entidad += "<esEmpresa>false</esEmpresa>";
		entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
		entidad += "<sectorEmpresaId></sectorEmpresaId>";
		entidad += "<telefonoCasa></telefonoCasa>";
		entidad += "<telefonoOficina></telefonoOficina>";
		entidad += "<telefonoCelular></telefonoCelular>";
		entidad += "<sectorEmpresa></sectorEmpresa>";
		entidad += "<tituloAcademico></tituloAcademico>";
		entidad += "<webSite></webSite>";
		entidad += "<nacionalidad></nacionalidad>";
		entidad += "<limiteCredito>0</limiteCredito>";
		entidad += "<tipoEmpleado></tipoEmpleado>";
		entidad += "<nombreEmpresa></nombreEmpresa>";
		entidad += "<nombreCorto></nombreCorto>";
		entidad += "<actividadEconomica></actividadEconomica>";
		entidad += "<employeeType></employeeType>";
		entidad += "<income></income>";
		entidad += "<totalEgresos></totalEgresos>";
		entidad += "<otrosIngresos></otrosIngresos>";
		entidad += "<fuente>Masivos</fuente>";
		entidad += "<birthPlace></birthPlace>";
		entidad += "<representanteLegal>";
		entidad += "<id></id>";
		entidad += "<entidadId></entidadId>";
		entidad += "<nombres></nombres>";
		entidad += "<apellidos></apellidos>";
		entidad += "<identificacion></identificacion>";
		entidad += "<correoElectronico></correoElectronico>";
		entidad += "<tipoEmpresaId></tipoEmpresaId>";
		entidad += "<esPrimaExcenta></esPrimaExcenta>";
		entidad += "<tipoIdentificacion></tipoIdentificacion>";
		entidad += "<tipoEntidad></tipoEntidad>";
		entidad += "<genero>m</genero>";
		entidad += "<fechaNacimiento></fechaNacimiento>";
		entidad += "<esEmpresa>true</esEmpresa>";
		entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
		entidad += "<sectorEmpresaId></sectorEmpresaId>";
		entidad += "<telefonoCasa></telefonoCasa>";
		entidad += "<telefonoOficina></telefonoOficina>";
		entidad += "<telefonoCelular></telefonoCelular>";
		entidad += "<sectorEmpresa></sectorEmpresa>";
		entidad += "<tituloAcademico></tituloAcademico>";
		entidad += "<webSite></webSite>";
		entidad += "<nacionalidad></nacionalidad>";
		entidad += "<limiteCredito></limiteCredito>";
		entidad += "<tipoEmpleado></tipoEmpleado>";
		entidad += "<nombreEmpresa></nombreEmpresa>";
		entidad += "<nombreCorto></nombreCorto>";
		entidad += "<actividadEconomica></actividadEconomica>";
		entidad += "<employeeType></employeeType>";
		entidad += "<income></income>";
		entidad += "<totalEgresos></totalEgresos>";
		entidad += "<otrosIngresos></otrosIngresos>";
		entidad += "<fuente></fuente>";
		entidad += "<birthPlace></birthPlace>";
		entidad += "<socialObject></socialObject>";
		entidad += "<telefonoEmpresa></telefonoEmpresa>";
		entidad += "<faxEmpresa></faxEmpresa>";
		entidad += "<nombresConyuge></nombresConyuge>";
		entidad += "<apellidosConyuge></apellidosConyuge>";
		entidad += "<actividadEconomicaId></actividadEconomicaId>";
		entidad += "<grupoEconomicoId></grupoEconomicoId>";
		entidad += "</representanteLegal>";
		entidad += "<socialObject></socialObject>";
		entidad += "<telefonoEmpresa></telefonoEmpresa>";
		entidad += "<faxEmpresa></faxEmpresa>";
		entidad += "<nombresConyuge></nombresConyuge>";
		entidad += "<apellidosConyuge></apellidosConyuge>";
		entidad += "<actividadEconomicaId></actividadEconomicaId>";
		entidad += "<grupoEconomicoId></grupoEconomicoId>";
		entidad += "<NumDirecciones>0</NumDirecciones>";

		entidad += finEntidadTag;
		entidad += entidadFin;
		return entidad;
	}

	public String xmlEntidadBeneficiario(EndosoBeneficiario endosoBeneficiario,int valorBeneficiarioPorcentaje) {
		String entidad = "";
		String entidadInicio = "<beneficiarios>";
		String entidadFin = "</beneficiarios>";
		String finEntidadTag = "<finBene>1</finBene>";

		entidad += entidadInicio;

		entidad += "<id></id>";
		entidad += "<entidadId>" + (endosoBeneficiario.getBeneficiario().getCodigoEnsurance() == null ? "" : endosoBeneficiario.getBeneficiario().getCodigoEnsurance()) + "</entidadId>";
		entidad += "<nombres>" + (endosoBeneficiario.getBeneficiario().getNombre() == null ? "" : endosoBeneficiario.getBeneficiario().getNombre()) + "</nombres>";
		entidad += "<apellidos></apellidos>";
		entidad += "<identificacion></identificacion>";
		entidad += "<correoElectronico></correoElectronico>";
		entidad += "<tipoEmpresaId></tipoEmpresaId>";
		entidad += "<esPrimaExcenta></esPrimaExcenta>";
		entidad += "<tipoIdentificacion>r</tipoIdentificacion>";
		entidad += "<tipoEntidad>1</tipoEntidad>";
		entidad += "<genero>m</genero>";
		entidad += "<fechaNacimiento></fechaNacimiento>";
		entidad += "<esEmpresa>false</esEmpresa>";
		entidad += "<porcentajeBeneficio>"+valorBeneficiarioPorcentaje+"</porcentajeBeneficio>";
		entidad += "<sectorEmpresaId></sectorEmpresaId>";
		entidad += "<telefonoCasa></telefonoCasa>";
		entidad += "<telefonoOficina></telefonoOficina>";
		entidad += "<telefonoCelular></telefonoCelular>";
		entidad += "<sectorEmpresa></sectorEmpresa>";
		entidad += "<tituloAcademico></tituloAcademico>";
		entidad += "<webSite></webSite>";
		entidad += "<nacionalidad></nacionalidad>";
		entidad += "<limiteCredito>0</limiteCredito>";
		entidad += "<tipoEmpleado></tipoEmpleado>";
		entidad += "<nombreEmpresa></nombreEmpresa>";
		entidad += "<nombreCorto></nombreCorto>";
		entidad += "<actividadEconomica></actividadEconomica>";
		entidad += "<employeeType></employeeType>";
		entidad += "<income></income>";
		entidad += "<totalEgresos></totalEgresos>";
		entidad += "<otrosIngresos></otrosIngresos>";
		entidad += "<fuente>Masivos</fuente>";
		entidad += "<birthPlace></birthPlace>";
		entidad += "<representanteLegal>";
		entidad += "<id></id>";
		entidad += "<entidadId></entidadId>";
		entidad += "<nombres></nombres>";
		entidad += "<apellidos></apellidos>";
		entidad += "<identificacion></identificacion>";
		entidad += "<correoElectronico></correoElectronico>";
		entidad += "<tipoEmpresaId></tipoEmpresaId>";
		entidad += "<esPrimaExcenta></esPrimaExcenta>";
		entidad += "<tipoIdentificacion></tipoIdentificacion>";
		entidad += "<tipoEntidad></tipoEntidad>";
		entidad += "<genero>m</genero>";
		entidad += "<fechaNacimiento></fechaNacimiento>";
		entidad += "<esEmpresa>true</esEmpresa>";
		entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
		entidad += "<sectorEmpresaId></sectorEmpresaId>";
		entidad += "<telefonoCasa></telefonoCasa>";
		entidad += "<telefonoOficina></telefonoOficina>";
		entidad += "<telefonoCelular></telefonoCelular>";
		entidad += "<sectorEmpresa></sectorEmpresa>";
		entidad += "<tituloAcademico></tituloAcademico>";
		entidad += "<webSite></webSite>";
		entidad += "<nacionalidad></nacionalidad>";
		entidad += "<limiteCredito></limiteCredito>";
		entidad += "<tipoEmpleado></tipoEmpleado>";
		entidad += "<nombreEmpresa></nombreEmpresa>";
		entidad += "<nombreCorto></nombreCorto>";
		entidad += "<actividadEconomica></actividadEconomica>";
		entidad += "<employeeType></employeeType>";
		entidad += "<income></income>";
		entidad += "<totalEgresos></totalEgresos>";
		entidad += "<otrosIngresos></otrosIngresos>";
		entidad += "<fuente></fuente>";
		entidad += "<birthPlace></birthPlace>";
		entidad += "<socialObject></socialObject>";
		entidad += "<telefonoEmpresa></telefonoEmpresa>";
		entidad += "<faxEmpresa></faxEmpresa>";
		entidad += "<nombresConyuge></nombresConyuge>";
		entidad += "<apellidosConyuge></apellidosConyuge>";
		entidad += "<actividadEconomicaId></actividadEconomicaId>";
		entidad += "<grupoEconomicoId></grupoEconomicoId>";
		entidad += "</representanteLegal>";
		entidad += "<socialObject></socialObject>";
		entidad += "<telefonoEmpresa></telefonoEmpresa>";
		entidad += "<faxEmpresa></faxEmpresa>";
		entidad += "<nombresConyuge></nombresConyuge>";
		entidad += "<apellidosConyuge></apellidosConyuge>";
		entidad += "<actividadEconomicaId></actividadEconomicaId>";
		entidad += "<grupoEconomicoId></grupoEconomicoId>";
		entidad += "<NumDirecciones>0</NumDirecciones>";

		entidad += finEntidadTag;
		entidad += entidadFin;
		return entidad;
	}

	public String xmlEntidadVacio(TipoEntidadEnum tipoEntidad) {
		String entidad = "";
		String entidadInicio = "";
		String entidadFin = "";
		String finEntidadTag = "";

		if (tipoEntidad == TipoEntidadEnum.CLIENTE) {
			entidadInicio = "<cliente>";
			entidadFin = "</cliente>";
			finEntidadTag = "<finclie>1</finclie>";
		}
		if (tipoEntidad == TipoEntidadEnum.ASEGURADO) {
			entidadInicio = "<asegurado>";
			entidadFin = "</asegurado>";
			finEntidadTag = "<finasegu>1</finasegu>";
		}
		if (tipoEntidad == TipoEntidadEnum.BENEFICIARIOS) {
			entidadInicio = "<beneficiarios>";
			entidadFin = "</beneficiarios>";
			finEntidadTag = "<finBene>1</finBene>";
		}
		if (tipoEntidad == TipoEntidadEnum.PROPIETARIO) {
			entidadInicio = "<propietario>";
			entidadFin = "</propietario>";
			finEntidadTag = "<finProp>1</finProp>";
		}
		if (tipoEntidad == TipoEntidadEnum.PROPIETARIOCUENTA) {
			entidadInicio = "<propietarioCuenta>";
			entidadFin = "</propietarioCuenta>";
			finEntidadTag = "<finprocue>1</finprocue>";
		}
		if (tipoEntidad == TipoEntidadEnum.CONDUCTORES) {
			entidadInicio = "<conductores>";
			entidadFin = "</conductores>";
			finEntidadTag = "<finC>1</finC>";
		}


		if (tipoEntidad == TipoEntidadEnum.CLIENTE || tipoEntidad == TipoEntidadEnum.PROPIETARIO || tipoEntidad == TipoEntidadEnum.PROPIETARIOCUENTA || tipoEntidad == TipoEntidadEnum.ASEGURADO || tipoEntidad == TipoEntidadEnum.BENEFICIARIOS) {
			entidad += entidadInicio;

			entidad += "<id></id>";
			entidad += "<entidadId></entidadId>";
			entidad += "<nombres></nombres>";
			entidad += "<apellidos></apellidos>";
			entidad += "<identificacion></identificacion>";
			entidad += "<correoElectronico></correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion></tipoIdentificacion>";

			entidad += "<tipoEntidad>1</tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>false</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa></telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular></telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito>0</limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos></otrosIngresos>";
			entidad += "<fuente>Masivos</fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<representanteLegal>";
			entidad += "<id></id>";
			entidad += "<entidadId></entidadId>";
			entidad += "<nombres></nombres>";
			entidad += "<apellidos></apellidos>";
			entidad += "<identificacion></identificacion>";
			entidad += "<correoElectronico></correoElectronico>";
			entidad += "<tipoEmpresaId></tipoEmpresaId>";
			entidad += "<esPrimaExcenta></esPrimaExcenta>";
			entidad += "<tipoIdentificacion></tipoIdentificacion>";
			entidad += "<tipoEntidad></tipoEntidad>";
			entidad += "<genero>m</genero>";
			entidad += "<fechaNacimiento></fechaNacimiento>";
			entidad += "<esEmpresa>true</esEmpresa>";
			entidad += "<porcentajeBeneficio></porcentajeBeneficio>";
			entidad += "<sectorEmpresaId></sectorEmpresaId>";
			entidad += "<telefonoCasa></telefonoCasa>";
			entidad += "<telefonoOficina></telefonoOficina>";
			entidad += "<telefonoCelular></telefonoCelular>";
			entidad += "<sectorEmpresa></sectorEmpresa>";
			entidad += "<tituloAcademico></tituloAcademico>";
			entidad += "<webSite></webSite>";
			entidad += "<nacionalidad></nacionalidad>";
			entidad += "<limiteCredito></limiteCredito>";
			entidad += "<tipoEmpleado></tipoEmpleado>";
			entidad += "<nombreEmpresa></nombreEmpresa>";
			entidad += "<nombreCorto></nombreCorto>";
			entidad += "<actividadEconomica></actividadEconomica>";
			entidad += "<employeeType></employeeType>";
			entidad += "<income></income>";
			entidad += "<totalEgresos></totalEgresos>";
			entidad += "<otrosIngresos></otrosIngresos>";
			entidad += "<fuente></fuente>";
			entidad += "<birthPlace></birthPlace>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "</representanteLegal>";
			entidad += "<socialObject></socialObject>";
			entidad += "<telefonoEmpresa></telefonoEmpresa>";
			entidad += "<faxEmpresa></faxEmpresa>";
			entidad += "<nombresConyuge></nombresConyuge>";
			entidad += "<apellidosConyuge></apellidosConyuge>";
			entidad += "<actividadEconomicaId></actividadEconomicaId>";
			entidad += "<grupoEconomicoId></grupoEconomicoId>";
			entidad += "<NumDirecciones></NumDirecciones>";
			//entidad +=finEntidadTag; 
			//entidad +=entidadFin;


			entidad += finEntidadTag;
			entidad += entidadFin;
		}

		return entidad;
	}

	public String xmlExtras(ObjetoVehiculo vehiculo) {
		String retorno = "";
		//String retorno="";
		List < Extra > extras = vehiculo.getExtras();

		if (extras.size() > 0) {
			for (Extra extra: extras) {
				retorno += "<extras>";
				retorno += "<extraId></extraId>";
				retorno += "<nombre>" + extra.getDescripcion() + "</nombre>";
				retorno += "<valorExtra>" + extra.getValorAsegurado() + "</valorExtra>";
				retorno += "<tipoExtraId>" + extra.getTipoExtra().getTipExtEnsurance() + "</tipoExtraId>";
				retorno += "<finE>1</finE>";
				retorno += "</extras>";

			}
		}

		return retorno;
	}

	public void validarEmision(String cotizacionId) throws Exception {

		CotizacionDAO cotizacionDAO = new CotizacionDAO();
		Cotizacion cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
		if (cotizacion == null || cotizacion.getId() == null) {
			throw new Exception("No se puede emitir!, la cotizaci�n no existe");
		}

		AgenteDAO agenteDAO = new AgenteDAO();
		Agente agente = agenteDAO.buscarPorId(String.valueOf(cotizacion.getAgenteId()));
		if (agente == null || agente.getId() == null) {
			throw new Exception("No se puede emitir!, el agente no es valido");
		}

		if (agente.getEntidad() == null || agente.getEntidad().getId() == null || agente.getEntidad().getIdentificacion() == null) {
			throw new Exception("No se puede emitir!, el agente no es valido");
		}

		Producto productoVH = cotizacion.getProducto();
		if (productoVH == null || productoVH.getId() == null) {
			throw new Exception("No se puede emitir!, el producto no es valido");
		}

		PuntoVenta puntoVenta = cotizacion.getPuntoVenta();
		if (puntoVenta == null || puntoVenta.getId() == null) {
			throw new Exception("No se puede emitir!, el punto de venta no es valido");
		}
		VigenciaPoliza vigenciaPoliza = cotizacion.getVigenciaPoliza();
		if (vigenciaPoliza == null || vigenciaPoliza.getId() == null) {
			throw new Exception("No se puede emitir!, la vigencia de la poliza no es valida");
		}
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.buscarPorId(cotizacion.getClienteId().toString());
		if (cliente == null || cliente.getId() == null) {
			throw new Exception("No se puede emitir!, el cliente no es valido ");
		}


		//validaciones datos cliente

		Entidad clienteEnt = cliente.getEntidad();
		if (clienteEnt == null || clienteEnt.getId() == null) {
			throw new Exception("No se puede emitir!, el cliente no es valido ");
		}

		if (clienteEnt.getIdentificacion() == null || clienteEnt.getIdentificacion() == "") {
			throw new Exception("No se puede emitir!, la identificaci�n del cliente no es valida");
		}

		//direcci�n cliente
		if(clienteEnt.getDireccions().size()==0){
			throw new Exception("El cliente no tiene direcci�n");
		}

		if (!clienteEnt.getId().equals(cotizacion.getAsegurado().getId())) {
			Entidad aseguradoEnt = cotizacion.getAsegurado();
			if (aseguradoEnt == null || aseguradoEnt.getId() == null) {
				throw new Exception("No se puede emitir!, el asegurado no es valido ");
			}

			if (aseguradoEnt.getIdentificacion() == null || aseguradoEnt.getIdentificacion() == "") {
				throw new Exception("No se puede emitir!, la identificaci�n del cliente no es valida");
			}

			//direcci�n cliente
			if (aseguradoEnt.getDireccions().size() == 0) {
				throw new Exception("El cliente no tiene direcci�n");
			}

		}

		TipoObjeto tipoObjeto = cotizacion.getTipoObjeto();
		if (tipoObjeto == null || tipoObjeto.getId() == null) {
			throw new Exception("No se puede emitir!, el tipo Objeto no es valido ");
		}

		if (cotizacion.getSumaAseguradaTotal() <= 0) {
			throw new Exception("No se puede emitir!, la suma asegurada es " + cotizacion.getSumaAseguradaTotal());
		}

		if (Double.parseDouble(cotizacion.getPrimaNetaTotal()) <= 0) {
			throw new Exception("No se puede emitir!, la suma asegurada es " + cotizacion.getSumaAseguradaTotal());
		}

		Usuario usuario = cotizacion.getUsuario();
		if (usuario == null || usuario.getId() == null) {
			throw new Exception("No se puede emitir!, el usuario no es valido ");
		}

		Pago pago = cotizacion.getPago();
		if (pago == null || pago.getId() == null) {
			throw new Exception("No se puede emitir!, no hay una forma de pago ");
		}

		if (cotizacion.getFechaEmision() != null && cotizacion.getNumeroFactura() == null) {
			throw new Exception("La cotizaci�n ya fue emitida el " + cotizacion.getFechaEmision() + " el numero de factura es " + cotizacion.getNumeroFactura());
		}

		if (cotizacion.getVigenciaDesde() == null) {
			throw new Exception("No se puede emitir!, la vigencia desde no es valida");
		}

		ProductoXPuntoVentaDAO pxpvDAO = new ProductoXPuntoVentaDAO();
		ProductoXPuntoVenta pxpv = pxpvDAO.buscarPorId(cotizacion.getProductoXPuntoVentaId().toString());
		if (pxpv == null || pxpv.getId() == null) {
			throw new Exception("No se puede emitir!, el producto por punto de venta no es valido");
		}

		if (pxpv == null || pxpv.getId() == null) {
			throw new Exception("No se puede emitir!, la vigencia desde no es valida");
		}

		if (pxpv.getUnidadNegocio() == null || pxpv.getUnidadNegocio().getId() == null) {
			throw new Exception("No se puede emitir!, la unidad de negocio no es valida");
		}

		if (pxpv.getUnidadProduccion() == null || pxpv.getUnidadProduccion().getId() == null) {
			throw new Exception("No se puede emitir!, la unidad de produccion no es valida");
		}

		FirmasDigitalesDAO fdDAO = new FirmasDigitalesDAO();
		EntidadDAO entidadDAO = new EntidadDAO();
		Entidad entidadUP = entidadDAO.buscarPorId(pxpv.getUnidadProduccion().getEntidadId());
		RamoDAO ramoDAO = new RamoDAO();
		Ramo ramo = ramoDAO.buscarPorId(productoVH.getRamoId().toString());
		FirmasDigitales fd = fdDAO.buscarPorRamoSucursalEntidad(ramo, puntoVenta.getSucursal(), entidadUP);

		if (fd == null || fd.getId() == null) {
			throw new Exception("No se puede emitir!, no existe firma digital para el ramo: " + ramo.getNombre() + " sucursal: " + puntoVenta.getSucursal().getNombre() + " unidad de produccion: " + pxpv.getUnidadProduccion().getNombre());
		}

		if (!pxpv.getPuntoVenta().getId().equals(puntoVenta.getId())) {
			throw new Exception("No se puede emitir!, el punto de venta de la cotizacion no coincide con el punto de venta del producto por punto de venta");
		}
		
		//validacion de detalles
		List<CotizacionDetalle> detalles = cotizacion.getCotizacionDetalles();
		if(detalles.size()==0)
			throw new Exception("La cotizaci�n no tiene detalles!");

	}

}