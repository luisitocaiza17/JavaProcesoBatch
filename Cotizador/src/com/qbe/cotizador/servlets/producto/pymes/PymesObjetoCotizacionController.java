package com.qbe.cotizador.servlets.producto.pymes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.DIRECCION;
import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.EMPRESA;
import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.ENTIDAD;
import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.ENTIDADDIRECCION;
import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.EnsuranceEntity;
import org.datacontract.schemas._2004._07.SmartWork_BPM_BPMLogicEnsurance.PERSONA;
import org.tempuri.EngineProxy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.qbe.cotizador.dao.cotizacion.CotizacionDAO;
import com.qbe.cotizador.dao.cotizacion.CotizacionDetalleDAO;
import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.cotizacion.UplaDAO;
import com.qbe.cotizador.dao.entidad.DireccionDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeAsistenciaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeCoberturaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeConfiguracionCoberturaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeDerechoEmisionDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeInspectorProvinciaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeObjetoCotizacionCoberturaDAO;
import com.qbe.cotizador.dao.producto.pymes.PymeObjetoCotizacionDAO;
import com.qbe.cotizador.dao.seguridad.TipoVariableDAO;
import com.qbe.cotizador.dao.seguridad.VariableSistemaDAO;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Direccion;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.PymeAsistencia;
import com.qbe.cotizador.model.PymeConfiguracionCobertura;
import com.qbe.cotizador.model.PymeDerechoEmision;
import com.qbe.cotizador.model.PymeInspectorProvincia;
import com.qbe.cotizador.model.PymeObjetoCotizacion;
import com.qbe.cotizador.model.PymeObjetoCotizacionCobertura;
import com.qbe.cotizador.model.TipoVariable;
import com.qbe.cotizador.model.Upla;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.VariableSistema;
import com.qbe.cotizador.transaction.cotizacion.CotizacionDetalleTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.producto.pymes.PymeObjetoCotizacionCoberturaTransaction;
import com.qbe.cotizador.transaction.producto.pymes.PymeObjetoCotizacionTransaction;

/**
 * Servlet implementation class ObjetoPymesController
 */

@WebServlet("/PymesObjetoCotizacionController")
public class PymesObjetoCotizacionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PymesObjetoCotizacionController() {
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
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
		String cotizacionId = request.getParameter("cotizacionId") == null ? "" : request.getParameter("cotizacionId");
		String cotizacionDetalleId = request.getParameter("cotizacionDetalleId") == null ? "" : request.getParameter("cotizacionDetalleId");
		String provinciaId = request.getParameter("provinciaId") == null ? "" : request.getParameter("provinciaId");
		String cantonId = request.getParameter("cantonId") == null ? "" : request.getParameter("cantonId");
		String callePrincipal = request.getParameter("callePrincipal") == null ? "" : request.getParameter("callePrincipal");
		String numeroDireccion = request.getParameter("numeroDireccion") == null ? "" : request.getParameter("numeroDireccion");
		String calleSecundaria = request.getParameter("calleSecundaria") == null ? "" : request.getParameter("calleSecundaria");
		String actividadEconomicaId = request.getParameter("actividadEconomicaId") == null ? "" : request.getParameter("actividadEconomicaId");
		String tieneMasDosAnios = request.getParameter("tieneMasDosAnios") == null ? "" : request.getParameter("tieneMasDosAnios");
		String contabilidadFormal = request.getParameter("contabilidadFormal") == null ? "" : request.getParameter("contabilidadFormal");
		String requiereInspeccion = request.getParameter("requiereInspeccion") == null ? "" : request.getParameter("requiereInspeccion");
		String sector = request.getParameter("sector") == null ? "" : request.getParameter("sector");
		String nombreEdificio = request.getParameter("nombreEdificio") == null ? "" : request.getParameter("nombreEdificio");
		String numeroOficina = request.getParameter("numeroOficina") == null ? "" : request.getParameter("numeroOficina");
		String valorEstructuras = request.getParameter("valorEstructuras") == null ? "" : request.getParameter("valorEstructuras");
		String valorMueblesEnseres = request.getParameter("valorMueblesEnseres") == null ? "" : request.getParameter("valorMueblesEnseres");
		String valorMaquinaria = request.getParameter("valorMaquinaria") == null ? "" : request.getParameter("valorMaquinaria");
		String valorMercaderia = request.getParameter("valorMercaderia") == null ? "" : request.getParameter("valorMercaderia");
		String valorInsumosNoelectronicos = request.getParameter("valorInsumosNoelectronicos") == null ? "" : request.getParameter("valorInsumosNoelectronicos");
		String valorEquipoHerramienta = request.getParameter("valorEquipoHerramienta") == null ? "" : request.getParameter("valorEquipoHerramienta");
		String primaNeta = request.getParameter("primaNeta") == null ? "" : request.getParameter("primaNeta");
		String coberturas = request.getParameter("coberturas") == null ? "" : request.getParameter("coberturas");
		//Parametros usados en la inspeccion
		String tipoConstruccionId = request.getParameter("tipoConstruccionId") == null ? "" : request.getParameter("tipoConstruccionId");
		String tipoOcupacionId = request.getParameter("tipoOcupacionId") == null ? "" : request.getParameter("tipoOcupacionId");
		String numeroTotalPisos = request.getParameter("numeroTotalPisos") == null ? "" : request.getParameter("numeroTotalPisos");
		String anioConstruccion = request.getParameter("anioConstruccion") == null ? "" : request.getParameter("anioConstruccion");
		String extintores = request.getParameter("extintores") == null ? "" : request.getParameter("extintores");
		String detectorHumo = request.getParameter("detectorHumo") == null ? "" : request.getParameter("detectorHumo");
		String sprinklers = request.getParameter("sprinklers") == null ? "" : request.getParameter("sprinklers");
		String alarmaMonitoreada = request.getParameter("alarmaMonitoreada") == null ? "" : request.getParameter("alarmaMonitoreada");
		String guardiania = request.getParameter("guardiania") == null ? "" : request.getParameter("guardiania");
		String otros = request.getParameter("otros") == null ? "" : request.getParameter("otros");
		String latitud = request.getParameter("latitud") == null ? "" : request.getParameter("latitud");
		String longuitud = request.getParameter("longuitud") == null ? "" : request.getParameter("longuitud");
		String registro = request.getParameter("registro") == null ? "" : request.getParameter("registro");
		String estadoInspeccion = request.getParameter("estadoInspeccion") == null ? "" : request.getParameter("estadoInspeccion");


		JSONObject result = new JSONObject();
		try{
			JSONArray coberturasJSONArray = new JSONArray();
			JSONObject coberturaJSONObject = new JSONObject();

			CotizacionDAO cotizacionDAO = new CotizacionDAO();
			CotizacionDetalleDAO cotizacionDetalleDAO=new CotizacionDetalleDAO();
			PymeObjetoCotizacionDAO objetoCotizacionDAO=new PymeObjetoCotizacionDAO();
			PymeObjetoCotizacionCoberturaDAO objetoCCDAO=new PymeObjetoCotizacionCoberturaDAO();
			PymeAsistenciaDAO asistenciaDAO=new PymeAsistenciaDAO();
			PymeCoberturaDAO coberturaDAO=new PymeCoberturaDAO();
			EstadoDAO estadoDAO=new EstadoDAO();
			PymeConfiguracionCoberturaDAO configuracionCoberturaDAO=new PymeConfiguracionCoberturaDAO();

			PymeObjetoCotizacionTransaction pymeObjetoCotizacionTransaction=new PymeObjetoCotizacionTransaction();
			PymeObjetoCotizacionCoberturaTransaction pymeObjetoCCTransaction=new PymeObjetoCotizacionCoberturaTransaction();
			CotizacionTransaction cotizacionTransaction= new CotizacionTransaction();
			CotizacionDetalleTransaction cotizacionDetalleTransaction=new CotizacionDetalleTransaction();


			Cotizacion cotizacion = new Cotizacion();
			//Permite crear la cotización pyme en el paso 2
			if(tipoConsulta.equalsIgnoreCase("crear"))
			{
				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);

				//Valido si tiene almenos una dirección con requiere inspección
				int contador=0;
				List<CotizacionDetalle> listadoDetalles=cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);
				for(CotizacionDetalle detalleActual:listadoDetalles){
					PymeObjetoCotizacion objetoCotizacion=objetoCotizacionDAO.buscarPorId(new BigInteger(detalleActual.getObjetoId()));
					if(objetoCotizacion!=null){
						if(objetoCotizacion.getRequiereInspeccion()){
							contador++;
						}
					}
				}
				if(contador==0)
					throw new Exception("No es posible seguir con la cotización, porque no ha indicado al menos una dirección como dirección de inspección.");
				
				
				//Elimino las cooberturas generales y demás.
				List<PymeObjetoCotizacionCobertura> listado=objetoCCDAO.buscarPorObjetoPymeId(new BigInteger(cotizacionId));
				for(PymeObjetoCotizacionCobertura coberturaActual:listado){
					pymeObjetoCCTransaction.eliminar(coberturaActual);
				}

				//Barro el objeto de coberturas, asistencias y deducibles general.
				PymeObjetoCotizacionCobertura nuevoObjetoCC;
				if(coberturas != null && !coberturas.equals(""))
				{
					JSONArray array=(JSONArray)JSONSerializer.toJSON(coberturas);
					for(Object js:array){
						JSONObject jsonStr = (JSONObject)JSONSerializer.toJSON(js);
						nuevoObjetoCC=new PymeObjetoCotizacionCobertura();
						nuevoObjetoCC.setObjetoPymesId(new BigInteger(cotizacion.getId()));
						nuevoObjetoCC.setObjetoOrigenId(new BigInteger(jsonStr.getString("configuracionCoberturaId")));
						nuevoObjetoCC.setTasaSugerida(Double.parseDouble(jsonStr.getString("tasaSugerida")));
						nuevoObjetoCC.setTasaIngresada(Double.parseDouble(jsonStr.getString("tasaIngresada")));
						nuevoObjetoCC.setValorIngresado(Double.parseDouble(jsonStr.getString("valorIngresado")));
						nuevoObjetoCC.setPrimaCalculada(Double.parseDouble(jsonStr.getString("primaCalculada")));
						nuevoObjetoCC.setTipoOrigen(jsonStr.getString("tipoOrigen"));
						nuevoObjetoCC.setTextoDeducible(jsonStr.getString("textoDeducible"));
						pymeObjetoCCTransaction.crear(nuevoObjetoCC);
					}
				}
				cotizacion.setEtapaWizard(2);
				cotizacionTransaction.editar(cotizacion);
			}


			if(tipoConsulta.equalsIgnoreCase("obtenerResumenValores"))
			{
				VariableSistemaDAO variableDAO = new VariableSistemaDAO();
				cotizacion = cotizacionDAO.buscarPorId(request.getParameter("cotizacionId"));
				List<CotizacionDetalle> cotizacionesDetalle = cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);								

				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);

				double valorPrima = 0;
				double valorAsistencia = 0;
				double valorAsegurado = 0;
				double valorFinalPrima = 0;

				List<PymeObjetoCotizacionCobertura> cotizacionCoberturasGenerales =  objetoCCDAO.buscarPorObjetoPymeId(new BigInteger(cotizacion.getId()));
				for(PymeObjetoCotizacionCobertura coberturaActual: cotizacionCoberturasGenerales){
					if(coberturaActual.getTipoOrigen().equals("ADICIONALES") || coberturaActual.getTipoOrigen().equals("GENERAL")){
						valorPrima = valorPrima + coberturaActual.getPrimaCalculada();
						valorPrima = Math.rint(valorPrima*100)/100;
					}
					else if(coberturaActual.getTipoOrigen().equals("ASISTENCIA")){
						PymeAsistencia asistencia=asistenciaDAO.buscarPorId(coberturaActual.getObjetoOrigenId());
						if(asistencia.getEsPredeterminada()){
							valorAsistencia = valorAsistencia + coberturaActual.getPrimaCalculada();
							valorAsistencia = Math.rint(valorAsistencia*100)/100;
						}
						else{
							valorPrima = valorPrima + coberturaActual.getPrimaCalculada();
							valorPrima = Math.rint(valorPrima*100)/100;
						}
					}
				}


				Boolean primeraVez=false;  
				//Calculo los valores solo del detalle
				for(CotizacionDetalle cotizacionDetalle : cotizacionesDetalle)
				{			
					double primaActual=cotizacionDetalle.getPrimaNetaItem();

					//Sumo el valor de las asistencias a la cobertura de incendio
					if(!primeraVez){
						List<PymeObjetoCotizacionCobertura> cotizacionCoberturas =  objetoCCDAO.buscarPorObjetoPymeId(new BigInteger(cotizacionDetalle.getObjetoId()));

						for(PymeObjetoCotizacionCobertura coberturaActual : cotizacionCoberturas)
						{
							PymeConfiguracionCobertura configuracionCobertura=configuracionCoberturaDAO.buscarPorId(coberturaActual.getObjetoOrigenId());
							if(configuracionCobertura.getCoberturaPymesId().equals(new BigInteger("21"))){
								//Si deseo cambiar el valor de la cobertura
								double primaCalculada= coberturaActual.getPrimaCalculada();
								coberturaActual.setPrimaCalculada(primaCalculada+valorAsistencia);
								primaActual=primaActual+valorAsistencia;
								primeraVez=true;
							}
						}
					}

					valorPrima = valorPrima + primaActual;
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

				cotizacion.setPrimaNetaTotal(String.valueOf(valorFinalPrima));
				cotizacion.setSumaAseguradaTotal(valorAsegurado);

				for(VariableSistema variable : variablesistema) {
					if(variable.getNombre().equals("DERECHOS_EMISION")){
						//Obtengo el valor de la variable en base a la tabla.
						PymeDerechoEmisionDAO derechoEmisionDAO=new PymeDerechoEmisionDAO();
						List<PymeDerechoEmision> listadoDerechos=derechoEmisionDAO.buscarIntervalo(valorFinalPrima);
						double valorDerechoCalculado=0;
						if(listadoDerechos.size()!=0){
							valorDerechoCalculado = listadoDerechos.get(0).getValorDerechoEmision();
						}
						else{
							valorDerechoCalculado = Double.parseDouble(variable.getValor());
						}
						valorBase =valorDerechoCalculado + valorFinalPrima;
						valorDerechosEmision = valorDerechoCalculado;
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


			if(tipoConsulta.equalsIgnoreCase("crearDireccion"))
			{			
				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
				TipoObjetoDAO tipoObjetoDAO = new TipoObjetoDAO();				


				CotizacionDetalle nuevoCotizacionDetalle=new CotizacionDetalle(); 
				PymeObjetoCotizacion nuevoPymeObjetoCotizacion=new PymeObjetoCotizacion();

				//Si existe una cotización detalle id elimino las coberturas configurada.
				if(cotizacionDetalleId != null && !cotizacionDetalleId.equals(""))
				{
					nuevoCotizacionDetalle = cotizacionDetalleDAO.buscarPorId(cotizacionDetalleId);
					nuevoPymeObjetoCotizacion=objetoCotizacionDAO.buscarPorId(new BigInteger(nuevoCotizacionDetalle.getObjetoId()));


					List<PymeObjetoCotizacionCobertura> listado=objetoCCDAO.buscarPorObjetoPymeId(nuevoPymeObjetoCotizacion.getObjetoPymesId());

					pymeObjetoCotizacionTransaction.eliminar(nuevoPymeObjetoCotizacion);
					for(PymeObjetoCotizacionCobertura coberturaActual:listado){
						pymeObjetoCCTransaction.eliminar(coberturaActual);
					}
				}

				//Creo el objeto pyme
				if(callePrincipal != null && !callePrincipal.equals(""))
					nuevoPymeObjetoCotizacion.setCallePrincipal(callePrincipal);

				if(numeroDireccion != null && !numeroDireccion.equals(""))
					nuevoPymeObjetoCotizacion.setNumeroDireccion(numeroDireccion);

				if(calleSecundaria != null && !calleSecundaria.equals(""))
					nuevoPymeObjetoCotizacion.setCalleSecundaria(calleSecundaria);

				if(nombreEdificio != null && !nombreEdificio.equals(""))
					nuevoPymeObjetoCotizacion.setNombreEdificio(nombreEdificio);

				if(numeroOficina != null && !numeroOficina.equals(""))
					nuevoPymeObjetoCotizacion.setNumeroOficina(numeroOficina);

				if(sector != null && !sector.equals(""))
					nuevoPymeObjetoCotizacion.setSector(sector);

				if(provinciaId != null && !provinciaId.equals(""))
					nuevoPymeObjetoCotizacion.setProvinciaId(Integer.parseInt(provinciaId));

				if(cantonId != null && !cantonId.equals(""))
					nuevoPymeObjetoCotizacion.setCiudadId(Integer.parseInt(cantonId));

				if(actividadEconomicaId != null && !actividadEconomicaId.equals(""))
					nuevoPymeObjetoCotizacion.setActividadEconomicaId(new BigInteger(actividadEconomicaId));

				if(contabilidadFormal != null && !contabilidadFormal.equals(""))
					nuevoPymeObjetoCotizacion.setContabilidadFormal(Boolean.parseBoolean(contabilidadFormal));

				if(requiereInspeccion != null && !requiereInspeccion.equals(""))
					nuevoPymeObjetoCotizacion.setRequiereInspeccion(Boolean.parseBoolean(requiereInspeccion));

				if(tieneMasDosAnios != null && !tieneMasDosAnios.equals(""))
					nuevoPymeObjetoCotizacion.setTieneMasDosAnio(Boolean.parseBoolean(tieneMasDosAnios));

				if(valorEstructuras != null && !valorEstructuras.equals(""))
					nuevoPymeObjetoCotizacion.setValorEstructuras(Double.parseDouble(valorEstructuras));

				if(valorMueblesEnseres != null && !valorMueblesEnseres.equals(""))
					nuevoPymeObjetoCotizacion.setValorMueblesEnseres(Double.parseDouble(valorMueblesEnseres));

				if(valorMaquinaria != null && !valorMaquinaria.equals(""))
					nuevoPymeObjetoCotizacion.setValorMaquinaria(Double.parseDouble(valorMaquinaria));

				if(valorMercaderia != null && !valorMercaderia.equals(""))
					nuevoPymeObjetoCotizacion.setValorMercaderia(Double.parseDouble(valorMercaderia));

				if(valorInsumosNoelectronicos != null && !valorInsumosNoelectronicos.equals(""))
					nuevoPymeObjetoCotizacion.setValorInsumosNoelectronicos(Double.parseDouble(valorInsumosNoelectronicos));

				if(valorEquipoHerramienta != null && !valorEquipoHerramienta.equals(""))
					nuevoPymeObjetoCotizacion.setValorEquipoHerramienta(Double.parseDouble(valorEquipoHerramienta));

				double sumaAsegurada=nuevoPymeObjetoCotizacion.getValorEstructuras()+nuevoPymeObjetoCotizacion.getValorMueblesEnseres()+nuevoPymeObjetoCotizacion.getValorMaquinaria()+nuevoPymeObjetoCotizacion.getValorMercaderia()+nuevoPymeObjetoCotizacion.getValorInsumosNoelectronicos()+nuevoPymeObjetoCotizacion.getValorEquipoHerramienta();
				nuevoPymeObjetoCotizacion=pymeObjetoCotizacionTransaction.crear(nuevoPymeObjetoCotizacion);

				//Creo las coberturas
				PymeObjetoCotizacionCobertura nuevoObjetoCC;
				if(coberturas != null && !coberturas.equals(""))
				{
					JSONArray array=(JSONArray)JSONSerializer.toJSON(coberturas);
					for(Object js:array){
						JSONObject jsonStr = (JSONObject)JSONSerializer.toJSON(js);
						nuevoObjetoCC=new PymeObjetoCotizacionCobertura();
						nuevoObjetoCC.setObjetoPymesId(nuevoPymeObjetoCotizacion.getObjetoPymesId());
						nuevoObjetoCC.setObjetoOrigenId(new BigInteger(jsonStr.getString("configuracionCoberturaId")));
						nuevoObjetoCC.setTasaSugerida(Double.parseDouble(jsonStr.getString("tasaSugerida")));
						nuevoObjetoCC.setTasaIngresada(Double.parseDouble(jsonStr.getString("tasaIngresada")));
						nuevoObjetoCC.setValorIngresado(Double.parseDouble(jsonStr.getString("valorIngresado")));
						nuevoObjetoCC.setPrimaCalculada(Double.parseDouble(jsonStr.getString("primaCalculada")));
						nuevoObjetoCC.setTipoOrigen(jsonStr.getString("tipoOrigen"));
						nuevoObjetoCC.setTextoDeducible(jsonStr.getString("textoDeducible"));
						pymeObjetoCCTransaction.crear(nuevoObjetoCC);
					}
				}

				//Creo la cotización detalle
				if(cotizacionDetalleId == null || cotizacionDetalleId.equals(""))
				{
					nuevoCotizacionDetalle.setCotizacion(cotizacion);
					nuevoCotizacionDetalle.setNecesitaInspeccion(false);
					nuevoCotizacionDetalle.setTipoObjetoId(tipoObjetoDAO.buscarPorNombre("PYMES").getId());
					nuevoCotizacionDetalle.setObjetoId(String.valueOf(nuevoPymeObjetoCotizacion.getObjetoPymesId()));
					nuevoCotizacionDetalle.setSumaAseguradaItem(sumaAsegurada);
					nuevoCotizacionDetalle.setPrimaNetaItem(Double.parseDouble(primaNeta.replace("$", "").replace(",", "")));
					nuevoCotizacionDetalle=cotizacionDetalleTransaction.crear(nuevoCotizacionDetalle);
				}
				else
				{
					nuevoCotizacionDetalle.setObjetoId(String.valueOf(nuevoPymeObjetoCotizacion.getObjetoPymesId()));
					cotizacionDetalleTransaction.editar(nuevoCotizacionDetalle);
				}

				result.put("cotizacionDetalleId", nuevoCotizacionDetalle.getId());
			}

			// 
			if(tipoConsulta.equalsIgnoreCase("registrarInspeccion"))
			{			
				//Si existe una cotización detalle id elimino las coberturas configurada.
				if(cotizacionDetalleId != null && !cotizacionDetalleId.equals(""))
				{
					CotizacionDetalle nuevoCotizacionDetalle = cotizacionDetalleDAO.buscarPorId(cotizacionDetalleId);
					PymeObjetoCotizacion nuevoPymeObjetoCotizacion=objetoCotizacionDAO.buscarPorId(new BigInteger(nuevoCotizacionDetalle.getObjetoId()));

					if(tipoConstruccionId  != null && !tipoConstruccionId .equals(""))
						nuevoPymeObjetoCotizacion.setTipoConstruccionId(new BigInteger(tipoConstruccionId));

					if(tipoOcupacionId  != null && !tipoOcupacionId .equals(""))
						nuevoPymeObjetoCotizacion.setTipoOcupacionId(new BigInteger(tipoOcupacionId));

					if(numeroTotalPisos  != null && !numeroTotalPisos .equals(""))
						nuevoPymeObjetoCotizacion.setNumeroTotalPisos(Integer.parseInt(numeroTotalPisos));

					if(anioConstruccion  != null && !anioConstruccion .equals(""))
						nuevoPymeObjetoCotizacion.setAnioConstruccion(Integer.parseInt(anioConstruccion));

					if(extintores  != null && !extintores.equals(""))
						nuevoPymeObjetoCotizacion.setExtintores(extintores.equals("1")?true:false);

					if(detectorHumo  != null && !detectorHumo .equals(""))
						nuevoPymeObjetoCotizacion.setDetectorHumo(detectorHumo.equals("1")?true:false);

					if(sprinklers  != null && !sprinklers .equals(""))
						nuevoPymeObjetoCotizacion.setSprinklers(sprinklers.equals("1")?true:false);

					if(alarmaMonitoreada  != null && !alarmaMonitoreada .equals(""))
						nuevoPymeObjetoCotizacion.setAlarmaMonitoreada(alarmaMonitoreada.equals("1")?true:false);

					if(guardiania  != null && !guardiania .equals(""))
						nuevoPymeObjetoCotizacion.setGuardiania(guardiania.equals("1")?true:false);

					if(otros != null && !otros.equals(""))
						nuevoPymeObjetoCotizacion.setOtros(otros);

					if(latitud != null && !latitud.equals(""))
						nuevoPymeObjetoCotizacion.setLatitud(Double.parseDouble(latitud));

					if(longuitud != null && !longuitud.equals(""))
						nuevoPymeObjetoCotizacion.setLonguitud(Double.parseDouble(longuitud));

					if(registro != null && !registro.equals(""))
					{
						Path path = Paths.get("path/to/file");
						byte[] data = Files.readAllBytes(path);
						nuevoPymeObjetoCotizacion.setInforme(data);
					}

					if(estadoInspeccion != null && !estadoInspeccion.equals(""))
						nuevoPymeObjetoCotizacion.setEstadoInspeccion(Integer.parseInt(estadoInspeccion));

					pymeObjetoCotizacionTransaction.editar(nuevoPymeObjetoCotizacion);

					//verifico si todas las direcciones estan aprobadas
					cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
					List<CotizacionDetalle> listadoDetalles=cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);
					int contadorAprobados=0;
					int contadorRequiereInspeccion=0;
					for(CotizacionDetalle detalle:listadoDetalles){
						PymeObjetoCotizacion pymeObjetoCotizacion=objetoCotizacionDAO.buscarPorId(new BigInteger(detalle.getObjetoId()));
						if(pymeObjetoCotizacion.getEstadoInspeccion()==1)
							contadorAprobados++;
						if(pymeObjetoCotizacion.getRequiereInspeccion())
							contadorRequiereInspeccion++;
					}
					if(contadorRequiereInspeccion == contadorAprobados){
						cotizacion.setEstado(estadoDAO.buscarPorNombre("Pendiente","Cotizacion"));
						cotizacion.setEtapaWizard(3);
						Date fechaActual=new Date();
						cotizacion.setVigenciaDesde(fechaActual);
						cotizacionTransaction.editar(cotizacion);
					}

					result.put("cotizacionDetalleId", nuevoCotizacionDetalle.getId());
				}
			}

			if(tipoConsulta.equalsIgnoreCase("obtenerDireccionesInspeccion"))
			{
				HttpSession session = request.getSession(true);
				Usuario usuario = (Usuario) session.getAttribute("usuario");

				JSONObject objectDetalle= new JSONObject();
				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
				List<CotizacionDetalle> detalles= cotizacionDetalleDAO.buscarCotizacionDetallePorCotizacion(cotizacion);		
				JSONArray jsonDetallesDirecciones= new JSONArray(); 
				PymeInspectorProvinciaDAO inspeccionProvinciaDAO=new PymeInspectorProvinciaDAO();
				for(CotizacionDetalle detalleActual: detalles){
					PymeObjetoCotizacion objetoCotizacion= objetoCotizacionDAO.buscarPorId(new BigInteger(detalleActual.getObjetoId()));
					if(objetoCotizacion.getRequiereInspeccion()){
						List<PymeInspectorProvincia> listadoProvincias=inspeccionProvinciaDAO.buscarUsuarioId(new BigInteger(usuario.getId()), BigInteger.valueOf(objetoCotizacion.getProvinciaId()), BigInteger.valueOf(objetoCotizacion.getCiudadId()));
						if(listadoProvincias.size()!=0){
							objectDetalle.put("cotizacionDetalleId", detalleActual.getId());
							objectDetalle.put("sumaAsegurada", detalleActual.getSumaAseguradaItem());
							objectDetalle.put("primaNeta", detalleActual.getPrimaNetaItem());
	
							objectDetalle.put("provinciaId", objetoCotizacion.getProvinciaId());
							objectDetalle.put("cantonId", objetoCotizacion.getCiudadId());
							objectDetalle.put("callePrincipal", objetoCotizacion.getCallePrincipal());
							objectDetalle.put("numeroDireccion", objetoCotizacion.getNumeroDireccion());
							objectDetalle.put("calleSecundaria", objetoCotizacion.getCalleSecundaria());
							jsonDetallesDirecciones.add(objectDetalle);
						}
					}
				}
				result.put("direcciones", jsonDetallesDirecciones);
			}

			if(tipoConsulta.equalsIgnoreCase("obtenerConfiguracionPorDetalleId"))
			{			
				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
				CotizacionDetalle nuevoCotizacionDetalle=new CotizacionDetalle(); 

				PymeObjetoCotizacion nuevoObjetoPyme=new PymeObjetoCotizacion();

				if(cotizacionDetalleId != null && !cotizacionDetalleId.equals(""))
				{
					nuevoCotizacionDetalle = cotizacionDetalleDAO.buscarPorId(cotizacionDetalleId);
					if(nuevoCotizacionDetalle.getObjetoId()!=null)
					{
						nuevoObjetoPyme=objetoCotizacionDAO.buscarPorId(new BigInteger(nuevoCotizacionDetalle.getObjetoId()));
						if(nuevoObjetoPyme.getObjetoPymesId()!=null)
						{
							result.put("provinciaId", nuevoObjetoPyme.getProvinciaId());
							result.put("cantonId", nuevoObjetoPyme.getCiudadId());
							result.put("callePrincipal", nuevoObjetoPyme.getCallePrincipal());
							result.put("numeroDireccion", nuevoObjetoPyme.getNumeroDireccion());
							result.put("calleSecundaria", nuevoObjetoPyme.getCalleSecundaria());
							result.put("actividadEconomicaId", nuevoObjetoPyme.getActividadEconomicaId());
							result.put("tieneMasDosAnios", nuevoObjetoPyme.getTieneMasDosAnio());
							result.put("anioConstruccion", nuevoObjetoPyme.getAnioConstruccion());
							result.put("contabilidadFormal", nuevoObjetoPyme.getContabilidadFormal());
							result.put("requiereInspeccion", nuevoObjetoPyme.getRequiereInspeccion());
							result.put("sector", nuevoObjetoPyme.getSector());
							result.put("nombreEdificio", nuevoObjetoPyme.getNombreEdificio());
							result.put("numeroOficina", nuevoObjetoPyme.getNumeroOficina());
							result.put("valorEstructuras", nuevoObjetoPyme.getValorEstructuras());
							result.put("valorMueblesEnseres", nuevoObjetoPyme.getValorMueblesEnseres());
							result.put("valorMaquinaria", nuevoObjetoPyme.getValorMaquinaria());
							result.put("valorMercaderia", nuevoObjetoPyme.getValorMercaderia());
							result.put("valorInsumosNoelectronicos", nuevoObjetoPyme.getValorInsumosNoelectronicos());
							result.put("valorEquipoHerramienta", nuevoObjetoPyme.getValorEquipoHerramienta());
							//Datos de la inspección
							result.put("tipoConstruccionId", nuevoObjetoPyme.getTipoConstruccionId());
							result.put("tipoOcupacionId", nuevoObjetoPyme.getTipoOcupacionId());
							result.put("numeroTotalPisos", nuevoObjetoPyme.getNumeroTotalPisos());
							result.put("extintores", nuevoObjetoPyme.getExtintores());
							result.put("detectorHumo", nuevoObjetoPyme.getDetectorHumo());
							result.put("sprinklers", nuevoObjetoPyme.getSprinklers());
							result.put("alarmaMonitoreada", nuevoObjetoPyme.getAlarmaMonitoreada());
							result.put("guardiania", nuevoObjetoPyme.getGuardiania());
							result.put("otros", nuevoObjetoPyme.getOtros());
							result.put("latitud", nuevoObjetoPyme.getLatitud());
							result.put("longuitud", nuevoObjetoPyme.getLonguitud());
							result.put("registro", nuevoObjetoPyme.getInforme());
							result.put("EstadoInspeccion", nuevoObjetoPyme.getEstadoInspeccion());

							//Obtengo las coberturas del objeto pymes
							List<PymeObjetoCotizacionCobertura> listado=objetoCCDAO.buscarPorObjetoPymeId(nuevoObjetoPyme.getObjetoPymesId());
							for(PymeObjetoCotizacionCobertura coberturaActual:listado){
								coberturaJSONObject.put("configuracionCoberturaId", coberturaActual.getObjetoOrigenId());
								coberturaJSONObject.put("tasaSugerida",coberturaActual.getTasaSugerida());
								coberturaJSONObject.put("tasaIngresada",coberturaActual.getTasaIngresada());
								coberturaJSONObject.put("valorIngresado",coberturaActual.getValorIngresado());
								coberturaJSONObject.put("primaCalculada",coberturaActual.getPrimaCalculada());
								coberturaJSONObject.put("tipoOrigen",coberturaActual.getTipoOrigen());
								coberturaJSONObject.put("textoDeducible",coberturaActual.getTextoDeducible());
								coberturasJSONArray.add(coberturaJSONObject);
							}
							result.put("coberturas", coberturasJSONArray);
						}
					}
				}
			}
			
			if(tipoConsulta.equalsIgnoreCase("emitirPoliza")){
				cotizacion = cotizacionDAO.buscarPorId(cotizacionId);
				Date fechaActual=new Date();
				Calendar cal = Calendar.getInstance();
			    cal.setTime(fechaActual);
			    int day = cal.get(Calendar.DAY_OF_MONTH);
				if(day>27){
					cotizacion.setEstado(estadoDAO.buscarPorNombre("Pendiente de Emitir","Cotizacion"));
					cotizacionTransaction.editar(cotizacion);
				}
				else{
					//Antes de emitir, primero actualizo los datos de la entidad
					EntidadDAO entidadDAO=new EntidadDAO();
					DireccionDAO direccionDAO=new DireccionDAO();
					UplaDAO uplaDAO=new UplaDAO();
					Entidad aseguradoCotizacion=entidadDAO.buscarPorId(cotizacion.getAsegurado().getId());
					List<Direccion> aseguradoDirecciones=direccionDAO.buscarCobroPorEntidadId(aseguradoCotizacion);
					Direccion aseguradoDireccion=null;
					if(aseguradoDirecciones.size()!=0){
						aseguradoDireccion=aseguradoDirecciones.get(0);
					}
						
					
					EnsuranceEntity entity=new EnsuranceEntity();
					
					ENTIDAD entidadActualizada=new ENTIDAD();
					PERSONA personaActualizada=new PERSONA();
					ENTIDADDIRECCION entidadDireccionActualizada = new ENTIDADDIRECCION();
					DIRECCION direccionActualizada=new DIRECCION();
					EMPRESA empresaActualizada = new EMPRESA();
					
					if(aseguradoCotizacion.getEntEnsurance().equals("")){
						entity.setEsActualizacion(false);
						entity.setEsNuevo(true);
					}
					else{
						entity.setEsActualizacion(true);
						entity.setEsNuevo(false);
					}
					entidadActualizada.setID(aseguradoCotizacion.getEntEnsurance());
					entidadActualizada.setTELEFONOCELULAR1(aseguradoCotizacion.getTelefono());
					entidadActualizada.setTELEFONOCELULAR2("");
					entidadActualizada.setTELEFONOCELULAR3(aseguradoCotizacion.getCelular());
					entidadActualizada.setEMAILPRINCIPAL(aseguradoCotizacion.getMail());
					entidadActualizada.setTIPOENTIDADID(aseguradoCotizacion.getTipoEntidad().getId());
					entidadActualizada.setTIPOID(aseguradoCotizacion.getTipoIdentificacion().getId());
					entidadActualizada.setTIPOOBJETO("Cliente");
					entidadActualizada.setNACIONALIDADID(null);
					entidadActualizada.setFECHAACTUALIZA(Calendar.getInstance());
					entidadActualizada.setPEPS("0");
					entidadActualizada.setOFAC("0");
					entidadActualizada.setCONSUEP("0");
					entidadActualizada.setESTADO("1");
					entidadActualizada.setNUMEROINTENTOS(Short.valueOf("0"));
					entidadActualizada.setBLOQUEADA("0");
					entidadActualizada.setUSUARIOACTUALIZA("005338");
					entidadActualizada.setTIPOEMPLEADOID("3");
					entidadActualizada.setTIPOEMPRESAID("-1");
					entidadActualizada.setPARENTESCOID("11");
					entidadActualizada.setGRUPOECONOMICOID("-1");

					entidadActualizada.setGRUPOECONOMICOID("-1");
					if(Integer.parseInt(aseguradoCotizacion.getTipoEntidad().getId())==1){
						entidadActualizada.setAPELLIDO(aseguradoCotizacion.getApellidos());
						entidadActualizada.setNOMBRE(aseguradoCotizacion.getNombres());
						entidadActualizada.setNOMBRECOMPLETO(aseguradoCotizacion.getNombreCompleto());
						if(aseguradoCotizacion.getClientes().size()!=0){
							Upla uplaCotizacion=uplaDAO.buscarPorCliente(aseguradoCotizacion.getClientes().get(0));
							Calendar cal2 = Calendar.getInstance();
						    cal2.setTime(uplaCotizacion.getFechaNacimientoNatural());
							personaActualizada.setFECHANACIMIENTO(cal2);
							personaActualizada.setNACIONALIDAD(null);
							personaActualizada.setGENERO(uplaCotizacion.getGeneroNatural());
							personaActualizada.setID("");
							personaActualizada.setUSUARIOACTUALIZA("005338");
							entidadActualizada.setPERSONA(personaActualizada);
						}
					}
					else{
						entidadActualizada.setNOMBRE(aseguradoCotizacion.getNombres());
						entidadActualizada.setNOMBRECOMPLETO(aseguradoCotizacion.getNombreCompleto());
						if(aseguradoCotizacion.getClientes().size()!=0){
							Upla uplaCotizacion=uplaDAO.buscarPorCliente(aseguradoCotizacion.getClientes().get(0));
							Calendar cal2 = Calendar.getInstance();
						    cal2.setTime(uplaCotizacion.getFechaNacimientoNatural());
						    empresaActualizada.setID("");
						    empresaActualizada.setSECTORID("8847360");
						    empresaActualizada.setUSUARIOACTUALIZA("");
						    empresaActualizada.setAPELLIDOSREPRESENTANTE(uplaCotizacion.getApellidosRepresentanteLegal());
						    empresaActualizada.setNOMBRESREPRESENTANTE(uplaCotizacion.getNombresRepresentanteLegal());
						    empresaActualizada.setFECHACONSTITUCION(null);
						    entidadActualizada.setEMPRESA(empresaActualizada);	
						}
					}
					entity.setE(entidadActualizada);
					
					//Lleno el objeto Direccion
					direccionActualizada.setNOMBRE(null);
					direccionActualizada.setTELEFONO1(aseguradoCotizacion.getTelefono());
					direccionActualizada.setTELEFONO2("");
					direccionActualizada.setTELEFONO3(aseguradoCotizacion.getCelular());
					direccionActualizada.setPAISID("");
					direccionActualizada.setPROVINCIAID("");
					direccionActualizada.setCIUDADID(aseguradoDireccion.getCiudad().getId());
					direccionActualizada.setCANTONID("");
					direccionActualizada.setNOMBREPRINCIPAL(aseguradoDireccion.getCallePrincipal());
					direccionActualizada.setNOMBRESECUNDARIA(aseguradoDireccion.getCalleSecundaria());
					direccionActualizada.setNUMERO(aseguradoDireccion.getNumero());
					direccionActualizada.setZONA(aseguradoDireccion.getZona().getId());
					direccionActualizada.setNOMBRE(aseguradoDireccion.getCallePrincipal()+" "+aseguradoDireccion.getNumero()+" "+aseguradoDireccion.getCalleSecundaria());
					direccionActualizada.setNOMBREOPCIONAL(aseguradoDireccion.getDatosDeReferencia());
					direccionActualizada.setESTADOINFORMACION("A");
					direccionActualizada.setUSUARIOACTUALIZA("005338");
					entity.setD(direccionActualizada);
					
					//Lleno el objeto Direccion por Entidad
					entidadDireccionActualizada.setTIPODIRECCIONID(aseguradoDireccion.getTipoDireccion().getId());
					
					org.tempuri.EngineProxy proxy=new EngineProxy();
					proxy.saveData(java.util.UUID.randomUUID().toString(),null, entity);
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
