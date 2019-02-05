package com.qbe.cotizador.servlets.producto.vehiculocerrado;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.TasaProductoDAO;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.TasaProducto;
import com.qbe.cotizador.transaction.producto.vehiculo.TasaProductoTransaction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TasaProductoController
 */
@WebServlet("/TasaProductoController")
public class TasaProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TasaProductoController() {
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
		TasaProductoTransaction tasaProductoTransaction = new TasaProductoTransaction(); 
		try{
		
			TasaProductoDAO tasaProductoDAO = new TasaProductoDAO();
			TasaProducto tasaProducto = new TasaProducto();
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
		
			
			//Tabla de Mantenimiento
			String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String grupoPorProducto = request.getParameter("grupoPorProducto") == null ? "" : request.getParameter("grupoPorProducto");
		    String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
			String porcentajeCasco = request.getParameter("porcentajeCasco") == null ? "" : request.getParameter("porcentajeCasco");
			String porcentajeExtras = request.getParameter("porcentajeExtras") == null ? "" : request.getParameter("porcentajeExtras");
			String tieneSumaAsegurada = request.getParameter("tieneSumaAsegurada") == null ? "" : request.getParameter("tieneSumaAsegurada");
			String sumaAseguradaInicio = request.getParameter("sumaAseguradaInicio") == null ? "" : request.getParameter("sumaAseguradaInicio");
			String sumaAseguradaFin = request.getParameter("sumaAseguradaFin") == null ? "" : request.getParameter("sumaAseguradaFin");
			String tieneAntiguedadVh = request.getParameter("tieneAntiguedadVh") == null ? "" : request.getParameter("tieneAntiguedadVh");
			String antiguedadInicio = request.getParameter("antiguedadInicio") == null ? "" : request.getParameter("antiguedadInicio");
			String antiguedadFin = request.getParameter("antiguedadFin") == null ? "" : request.getParameter("antiguedadFin");
			String tieneDispositivoRastreo = request.getParameter("tieneDispositivoRastreo") == null ? "" : request.getParameter("tieneDispositivoRastreo");
			String tieneTipoVehiculo = request.getParameter("tieneTipoVehiculo") == null ? "" : request.getParameter("tieneTipoVehiculo");
			String tipoVehiculoNombre = request.getParameter("tipoVehiculoNombre") == null ? "" : request.getParameter("tipoVehiculoNombre");
			String tieneTonelaje = request.getParameter("tieneTonelaje") == null ? "" : request.getParameter("tieneTonelaje");
			String valorTonelajeInicio = request.getParameter("valorTonelajeInicio") == null ? "" : request.getParameter("valorTonelajeInicio");
			String valorTonelajeFin = request.getParameter("valorTonelajeFin") == null ? "" : request.getParameter("valorTonelajeFin");
			String tieneRegion = request.getParameter("tieneRegion") == null ? "" : request.getParameter("tieneRegion");
			String valorRegion = request.getParameter("valorRegion") == null ? "" : request.getParameter("valorRegion");
			String tieneDeducible = request.getParameter("tieneDeducible") == null ? "" : request.getParameter("tieneDeducible");
			String deduciblePorcentajeSiniestro = request.getParameter("deduciblePorcentajeSiniestro") == null ? "" : request.getParameter("deduciblePorcentajeSiniestro");
			String deduciblePorcentajeValorAsegurado = request.getParameter("deduciblePorcentajeValorAsegurado") == null ? "" : request.getParameter("deduciblePorcentajeValorAsegurado");
			String deducibleMinimo = request.getParameter("deducibleMinimo") == null ? "" : request.getParameter("deducibleMinimo");
			String tieneDeduciblePerdidaTotalSiniestro = request.getParameter("tieneDeduciblePerdidaTotalSiniestro") == null ? "" : request.getParameter("tieneDeduciblePerdidaTotalSiniestro");
			String deduciblePerdidaTotalSiniestro = request.getParameter("deduciblePerdidaTotalSiniestro") == null ? "" : request.getParameter("deduciblePerdidaTotalSiniestro");
			String tieneAnoFabricacion = request.getParameter("tieneAnoFabricacion") == null ? "" : request.getParameter("tieneAnoFabricacion");
			String anoFabricacionInicio = request.getParameter("anoFabricacionInicio") == null ? "" : request.getParameter("anoFabricacionInicio");
			String anoFabricacionFin = request.getParameter("anoFabricacionFin") == null ? "" : request.getParameter("anoFabricacionFin");
			String tieneEdadConductor = request.getParameter("tieneEdadConductor") == null ? "" : request.getParameter("tieneEdadConductor");
			String edadConductorInicio = request.getParameter("edadConductorInicio") == null ? "" : request.getParameter("edadConductorInicio");
			String edadConductorFin = request.getParameter("edadConductorFin") == null ? "" : request.getParameter("edadConductorFin");
			String tieneMarca = request.getParameter("tieneMarca") == null ? "" : request.getParameter("tieneMarca");
			String marcaListado = request.getParameter("marcaListado") == null ? "" : request.getParameter("marcaListado");
			String tieneModelo = request.getParameter("tieneModelo") == null ? "" : request.getParameter("tieneModelo");
			String modeloListado = request.getParameter("modeloListado") == null ? "" : request.getParameter("modeloListado");
			String ceroKilometros = request.getParameter("ceroKilometros") == null ? "" : request.getParameter("ceroKilometros");
			String tieneConductorGenero = request.getParameter("tieneConductorGenero") == null ? "" : request.getParameter("tieneConductorGenero");
			String conductorGeneroValor = request.getParameter("conductorGeneroValor") == null ? "" : request.getParameter("conductorGeneroValor");
			String tieneNumeroHijos = request.getParameter("tieneNumeroHijos") == null ? "" : request.getParameter("tieneNumeroHijos");
			String numeroHijos = request.getParameter("numeroHijos") == null ? "" : request.getParameter("numeroHijos");
			String tieneZona = request.getParameter("tieneZona") == null ? "" : request.getParameter("tieneZona");
			String valorZona = request.getParameter("valorZona") == null ? "" : request.getParameter("valorZona");
			String tieneGuardaGarage = request.getParameter("tieneGuardaGarage") == null ? "" : request.getParameter("tieneGuardaGarage");
			String tieneKilometrosRecorridos = request.getParameter("tieneKilometrosRecorridos") == null ? "" : request.getParameter("tieneKilometrosRecorridos");
			String kilometrosRecorridosInicio = request.getParameter("kilometrosRecorridosInicio") == null ? "" : request.getParameter("kilometrosRecorridosInicio");
			String kilometrosRecorridosFin = request.getParameter("kilometrosRecorridosFin") == null ? "" : request.getParameter("kilometrosRecorridosFin");
			String tieneCombustibleUtilizado = request.getParameter("tieneCombustibleUtilizado") == null ? "" : request.getParameter("tieneCombustibleUtilizado");
			String combustibleUtilizadoValorId = request.getParameter("combustibleUtilizadoValorId") == null ? "" : request.getParameter("combustibleUtilizadoValorId");
			String tieneTipoUso = request.getParameter("tieneTipoUso") == null ? "" : request.getParameter("tieneTipoUso");
			String tipoUsoListado = request.getParameter("tipoUsoListado") == null ? "" : request.getParameter("tipoUsoListado");
			String tieneCargaPasajeros = request.getParameter("tieneCargaPasajeros") == null ? "" : request.getParameter("tieneCargaPasajeros");
			String cargaPasajerosValor = request.getParameter("cargaPasajerosValor") == null ? "" : request.getParameter("cargaPasajerosValor");
			String tieneAdquisicion = request.getParameter("tieneAdquisicion") == null ? "" : request.getParameter("tieneAdquisicion");
			String nombreAdquisicion = request.getParameter("nombreAdquisicion") == null ? "" : request.getParameter("nombreAdquisicion");
			String esFlotaIndividual = request.getParameter("esFlotaIndividual") == null ? "" : request.getParameter("esFlotaIndividual");
			String valorFlotaIndividual = request.getParameter("valorFlotaIndividual") == null ? "" : request.getParameter("valorFlotaIndividual");
			String tieneTipoObjetoVehiculo = request.getParameter("tieneTipoObjetoVehiculo") == null ? "" : request.getParameter("tieneTipoObjetoVehiculo");
			String valorTipoObjetoVehiculo = request.getParameter("valorTipoObjetoVehiculo") == null ? "" : request.getParameter("valorTipoObjetoVehiculo");
			String tieneRenovacion = request.getParameter("tieneRenovacion") == null ? "" : request.getParameter("tieneRenovacion");	
			
			
			if (!codigo.equals("")){
				tasaProducto.setId(codigo);
			}		
			
			if(!grupoPorProducto.equals("")){
				GrupoPorProducto gpp = new GrupoPorProducto();
				GrupoPorProductoDAO gppDAO = new GrupoPorProductoDAO();
				gpp=gppDAO.buscarPorId(grupoPorProducto);
				tasaProducto.setGrupoPorProducto(gpp);				
			}
			
			if(!nombre.equals("")){
				tasaProducto.setNombre(nombre);
			}		
			
			if(!porcentajeCasco.equals("")){
				tasaProducto.setPorcentajeCasco(Double.parseDouble(porcentajeCasco));
			}
			
			if(!porcentajeExtras.equals("")){
				tasaProducto.setPorcentajeExtras(Double.parseDouble(porcentajeExtras));
			}
			
			if (tieneSumaAsegurada.equals("1")){
				tasaProducto.setTieneSumaAsegurada(true);
			}else if (tieneSumaAsegurada.equals("0")){
				tasaProducto.setTieneSumaAsegurada(false);
			}			
			
			if (!sumaAseguradaInicio.equals("")){
				tasaProducto.setSumaAseguradaInicio(Double.parseDouble(sumaAseguradaInicio));
			}
			
			if (!sumaAseguradaFin.equals("")){
				tasaProducto.setSumaAseguradaFin(Double.parseDouble(sumaAseguradaFin));
			}
			
			if (tieneAntiguedadVh.equals("1")){
				tasaProducto.setTieneAntiguedadVh(true);
			}else if (tieneAntiguedadVh.equals("0")){
				tasaProducto.setTieneAntiguedadVh(false);
			}
			
			if (!antiguedadInicio.equals("")){
				tasaProducto.setAntiguedadInicio(Integer.parseInt(antiguedadInicio));
			}
			
			if (!antiguedadFin.equals("")){
				tasaProducto.setAntiguedadFin(Integer.parseInt(antiguedadFin));				
			}
			
			if (tieneDispositivoRastreo.equals("1")){
				tasaProducto.setTieneDispositivoRastreo(true);
			}else if (tieneDispositivoRastreo.equals("0")){
				tasaProducto.setTieneDispositivoRastreo(false);
			}
			
			if (tieneTipoVehiculo.equals("1")){
				tasaProducto.setTieneTipoVehiculo(true);
			}else if (tieneTipoVehiculo.equals("0")){
				tasaProducto.setTieneTipoVehiculo(false);
			}
			if (!tipoVehiculoNombre.equals("")){
				tasaProducto.setTipoVehiculoNombre(tipoVehiculoNombre);
			}
			if (tieneTonelaje.equals("1")){
				tasaProducto.setTieneTonelaje(true);
			}else if (tieneTonelaje.equals("0")){
				tasaProducto.setTieneTonelaje(false);
			}
			
			if (!valorTonelajeInicio.equals("")){
				tasaProducto.setValorTonelajeInicio(Double.parseDouble(valorTonelajeInicio));
			}
			
			if (!valorTonelajeFin.equals("")){
				tasaProducto.setValorTonelajeFin(Double.parseDouble(valorTonelajeFin));
			}

			if (tieneRegion.equals("1")){
				tasaProducto.setTieneRegion(true);
			}else if (tieneRegion.equals("0")){
				tasaProducto.setTieneRegion(false);
			}			
			
			if (!valorRegion.equals("")){
				tasaProducto.setValorRegion(valorRegion);	
			}
			
			if (tieneDeducible.equals("1")){
				tasaProducto.setTieneDeducible(true);
			}else if (tieneDeducible.equals("0")){
				tasaProducto.setTieneDeducible(false);
			}	
			
			if (!deduciblePorcentajeSiniestro.equals("")){
				tasaProducto.setDeduciblePorcentajeSiniestro(Double.parseDouble(deduciblePorcentajeSiniestro));
			}
			
			if (!deduciblePorcentajeValorAsegurado.equals("")){
				tasaProducto.setDeduciblePorcentajeValorAsegurado(Double.parseDouble(deduciblePorcentajeValorAsegurado));
			}
			
			if (!deducibleMinimo.equals("")){
				tasaProducto.setDeducibleMinimo(Double.parseDouble(deducibleMinimo));			
			}
			
			if (tieneDeduciblePerdidaTotalSiniestro.equals("1")){
				tasaProducto.setTieneDeduciblePerdidaTotalSiniestro(true);
			}else if (tieneDeduciblePerdidaTotalSiniestro.equals("0")){
				tasaProducto.setTieneDeduciblePerdidaTotalSiniestro(false);
			}	
			
			if (!deduciblePerdidaTotalSiniestro.equals("")){
				tasaProducto.setDeduciblePerdidaTotalSiniestro(Double.parseDouble(deduciblePerdidaTotalSiniestro));
			}
			
			if (tieneAnoFabricacion.equals("1")){
				tasaProducto.setTieneAnoFabricacion(true);
			}else if (tieneAnoFabricacion.equals("0")){
				tasaProducto.setTieneAnoFabricacion(false);
			}
			
			if (!anoFabricacionInicio.equals("")){
				tasaProducto.setAnoFabricacionInicio(Integer.parseInt(anoFabricacionInicio));
			}
			
			if (!anoFabricacionFin.equals("")){
				tasaProducto.setAnoFabricacionFin(Integer.parseInt(anoFabricacionFin));
			}
			
			if (tieneEdadConductor.equals("1")){
				tasaProducto.setTieneEdadConductor(true);
			}else if (tieneEdadConductor.equals("0")){
				tasaProducto.setTieneEdadConductor(false);
			}		
			
			if (!edadConductorInicio.equals("")){
				tasaProducto.setEdadConductorInicio(Integer.parseInt(edadConductorInicio));
			}
			
			if (!edadConductorFin.equals("")){
				tasaProducto.setEdadConductorFin(Integer.parseInt(edadConductorFin));
			}
			
			if (tieneMarca.equals("1")){
				tasaProducto.setTieneMarca(true);
			}else if (tieneMarca.equals("0")){
				tasaProducto.setTieneMarca(false);
			}
			
			if (!marcaListado.equals("")){
				tasaProducto.setMarcaListadi(marcaListado);
			}
			
			if (tieneModelo.equals("1")){
				tasaProducto.setTieneModelo(true);		
			}else if (tieneModelo.equals("0")){
				tasaProducto.setTieneModelo(false);
			}
			
			if (!modeloListado.equals("")){
				tasaProducto.setModeloListado(modeloListado);
				}
			
			if (ceroKilometros.equals("1")){
				tasaProducto.setCeroKilometros(true);
			}else if (ceroKilometros.equals("0")){
				tasaProducto.setCeroKilometros(false);
			}
			
			if (tieneConductorGenero.equals("1")){
				tasaProducto.setTieneConductorGenero(true);
			}else if (tieneConductorGenero.equals("0")){
				tasaProducto.setTieneConductorGenero(false);
			}
			
			if (!conductorGeneroValor.equals("")){
				tasaProducto.setConductorGeneroValor(conductorGeneroValor);
			}
			
			if (tieneNumeroHijos.equals("1")){
				tasaProducto.setTieneNumeroHijos(true);
			}else if (tieneNumeroHijos.equals("0")){
				tasaProducto.setTieneNumeroHijos(false);
			}
			
			if (!numeroHijos.equals("")){
				tasaProducto.setNumeroHijos(Integer.parseInt(numeroHijos));
			}		
			
			if (tieneZona.equals("1")){
				tasaProducto.setTieneZona(true);
			}else if (tieneZona.equals("0")){
				tasaProducto.setTieneZona(false);
			}
			
			if (!valorZona.equals("")){
				tasaProducto.setValorZona(valorZona);
				}
			
			if (tieneGuardaGarage.equals("1")){
				tasaProducto.setTieneGuardaGarage(true);
			}else if (tieneGuardaGarage.equals("0")){
				tasaProducto.setTieneGuardaGarage(false);
			}		
			
			if (tieneKilometrosRecorridos.equals("1")){
				tasaProducto.setTieneKilometrosRecorridos(true);
			}else if (tieneKilometrosRecorridos.equals("0")){
				tasaProducto.setTieneKilometrosRecorridos(false);
			}
			
			if (!kilometrosRecorridosInicio.equals("")){
				tasaProducto.setKilometrosRecorridosInicio(Integer.parseInt(kilometrosRecorridosInicio));
			}
			if (!kilometrosRecorridosFin.equals("")){
				tasaProducto.setKilometrosRecorridosFin(Integer.parseInt(kilometrosRecorridosFin));
				}
			
			if (tieneCombustibleUtilizado.equals("1")){
				tasaProducto.setTieneCombustibleUtilizado(true);
			}else if (tieneCombustibleUtilizado.equals("0")){
				tasaProducto.setTieneCombustibleUtilizado(false);
			}
			if (!combustibleUtilizadoValorId.equals("")){
				tasaProducto.setCombustibleUtilizadoValorId(combustibleUtilizadoValorId);
			}
			if (tieneTipoUso.equals("1")){
				tasaProducto.setTieneTipoUso(true);
			}else if (tieneTipoUso.equals("0")){
				tasaProducto.setTieneTipoUso(false);
			}
			if (!tipoUsoListado.equals("")){
				tasaProducto.setTipoUsoListado(tipoUsoListado);
				}
			if (tieneCargaPasajeros.equals("1")){
				tasaProducto.setTieneCargaPasajeros(true);
			}else if (tieneCargaPasajeros.equals("0")){
				tasaProducto.setTieneCargaPasajeros(false);
			}
			if (!cargaPasajerosValor.equals("")){
				tasaProducto.setCargaPasajerosValor(cargaPasajerosValor);
				}
			if (tieneAdquisicion.equals("1")){
				tasaProducto.setTieneAdquisicion(true);
			}else if (tieneAdquisicion.equals("0")){
				tasaProducto.setTieneAdquisicion(false);
			}
			if (!nombreAdquisicion.equals("")){
				tasaProducto.setNombreAdquisicion(nombreAdquisicion);	
				}			
			if (esFlotaIndividual.equals("1")){
				tasaProducto.setEsFlotaIndividual(true);
			}else if (esFlotaIndividual.equals("0")){
				tasaProducto.setEsFlotaIndividual(false);
			}
			if (!valorFlotaIndividual.equals("")){
				tasaProducto.setValorFlotaIndividual(valorFlotaIndividual);
			}
			if (tieneTipoObjetoVehiculo.equals("1")){
				tasaProducto.setTieneTipoObjetoVehiculo(true);
			}else if (tieneTipoObjetoVehiculo.equals("0")){
				tasaProducto.setTieneTipoObjetoVehiculo(false);
			}
			if (!valorTipoObjetoVehiculo.equals("")){
				tasaProducto.setValorTipoObjetoVehiculo(valorTipoObjetoVehiculo);
			}
			if (tieneRenovacion.equals("1")){
				tasaProducto.setTieneRenovacion(true);
			}else if (tieneRenovacion.equals("0")){
				tasaProducto.setTieneRenovacion(false);
			}		

			//Tabla de Mantenimiento
			if (tipoConsulta.equals("encontrarTodos")) {
				JSONObject tasaProductoJSONObject = new JSONObject ();
				JSONArray tasaProductoJSONArray= new JSONArray ();
				List<TasaProducto> results = tasaProductoDAO.buscarTodos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					tasaProducto= results.get(i);
					tasaProductoJSONObject.put("codigo", tasaProducto.getId());
					tasaProductoJSONObject.put("grupoPorProducto", tasaProducto.getGrupoPorProducto().getId()+ " - " +tasaProducto.getGrupoPorProducto().getNombreComercialProducto() + " - " + tasaProducto.getGrupoPorProducto().getGrupoProducto().getNombre());
					tasaProductoJSONObject.put("nombre", tasaProducto.getNombre());
					tasaProductoJSONObject.put("porcentajeCasco", tasaProducto.getPorcentajeCasco());
					tasaProductoJSONObject.put("porcentajeExtras", tasaProducto.getPorcentajeExtras());			
					if(tasaProducto.getTieneSumaAsegurada()){
						tasaProductoJSONObject.put("tieneSumaAsegurada", "Si");
					}else{
						tasaProductoJSONObject.put("tieneSumaAsegurada", "No");
					}
					tasaProductoJSONObject.put("sumaAseguradaInicio", tasaProducto.getSumaAseguradaInicio());
					tasaProductoJSONObject.put("sumaAseguradaFin", tasaProducto.getSumaAseguradaFin());
					if(tasaProducto.getTieneAntiguedadVh()){
						tasaProductoJSONObject.put("tieneAntiguedadVh", "Si");
					}else{
						tasaProductoJSONObject.put("tieneAntiguedadVh", "No");
					}
					tasaProductoJSONObject.put("antiguedadInicio", tasaProducto.getAntiguedadInicio());
					tasaProductoJSONObject.put("antiguedadFin", tasaProducto.getAntiguedadFin());
					
					if(tasaProducto.getTieneDispositivoRastreo()){
						tasaProductoJSONObject.put("tieneDispositivoRastreo", "Si");
					}else{
						tasaProductoJSONObject.put("tieneDispositivoRastreo", "No");
					}
					
					if(tasaProducto.getTieneTipoVehiculo()){
						tasaProductoJSONObject.put("tieneTipoVehiculo", "Si");
					}else{
						tasaProductoJSONObject.put("tieneTipoVehiculo", "No");
					}
					
					tasaProductoJSONObject.put("tipoVehiculoNombre", tasaProducto.getTipoVehiculoNombre());
					if(tasaProducto.getTieneTonelaje()){
						tasaProductoJSONObject.put("tieneTonelaje", "Si");
					}else{
						tasaProductoJSONObject.put("tieneTonelaje", "No");
					}
					tasaProductoJSONObject.put("valorTonelajeInicio", tasaProducto.getValorTonelajeInicio());
					tasaProductoJSONObject.put("valorTonelajeFin", tasaProducto.getValorTonelajeFin());
					if(tasaProducto.getTieneRegion()){
						tasaProductoJSONObject.put("tieneRegion", "Si");
					}else{
						tasaProductoJSONObject.put("tieneRegion", "No");
					}
					tasaProductoJSONObject.put("valorRegion", tasaProducto.getValorRegion());
					if(tasaProducto.getTieneDeducible()){
						tasaProductoJSONObject.put("tieneDeducible", "Si");}
					else{
						tasaProductoJSONObject.put("tieneDeducible", "No");
					}
					tasaProductoJSONObject.put("deduciblePorcentajeSiniestro", tasaProducto.getDeduciblePorcentajeSiniestro());
					tasaProductoJSONObject.put("deduciblePorcentajeValorAsegurado", tasaProducto.getDeduciblePorcentajeValorAsegurado());
					tasaProductoJSONObject.put("deducibleMinimo", tasaProducto.getDeducibleMinimo());
					if(tasaProducto.getTieneDeduciblePerdidaTotalSiniestro()){
						tasaProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "Si");
					}else{
						tasaProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "No");
					}
					tasaProductoJSONObject.put("deduciblePerdidaTotalSiniestro", tasaProducto.getDeduciblePerdidaTotalSiniestro());
					if(tasaProducto.getTieneAnoFabricacion()){
						tasaProductoJSONObject.put("tieneAnoFabricacion", "Si");
					}else{
						tasaProductoJSONObject.put("tieneAnoFabricacion", "No");
					}
					tasaProductoJSONObject.put("anoFabricacionInicio", tasaProducto.getAnoFabricacionInicio());
					tasaProductoJSONObject.put("anoFabricacionFin", tasaProducto.getAnoFabricacionFin());
					if(tasaProducto.getTieneEdadConductor()){
						tasaProductoJSONObject.put("tieneEdadConductor", "Si");
					}else{
						tasaProductoJSONObject.put("tieneEdadConductor", "No");
					}
					tasaProductoJSONObject.put("edadConductorInicio", tasaProducto.getEdadConductorInicio());
					tasaProductoJSONObject.put("edadConductorFin", tasaProducto.getEdadConductorFin());
					if(tasaProducto.getTieneMarca()){
						tasaProductoJSONObject.put("tieneMarca", "Si");
					}else{
						tasaProductoJSONObject.put("tieneMarca", "No");					
					}
					tasaProductoJSONObject.put("marcaListado", tasaProducto.getMarcaListadi());
					if(tasaProducto.getTieneModelo()){
						tasaProductoJSONObject.put("tieneModelo", "Si");
					}else{
						tasaProductoJSONObject.put("tieneModelo", "No");
					}
					tasaProductoJSONObject.put("modeloListado", tasaProducto.getModeloListado());
					if(tasaProducto.getCeroKilometros()){
						tasaProductoJSONObject.put("ceroKilometros", "Si");
					}else{
						tasaProductoJSONObject.put("ceroKilometros", "No");
					}
					if(tasaProducto.getTieneConductorGenero()){
						tasaProductoJSONObject.put("tieneConductorGenero", "Si");
					}else{
						tasaProductoJSONObject.put("tieneConductorGenero", "No");
					}
					tasaProductoJSONObject.put("conductorGeneroValor", tasaProducto.getConductorGeneroValor());
					if(tasaProducto.getTieneNumeroHijos()){
						tasaProductoJSONObject.put("tieneNumeroHijos", "Si");
					}else{
						tasaProductoJSONObject.put("tieneNumeroHijos", "No");
					}
					tasaProductoJSONObject.put("numeroHijos", tasaProducto.getNumeroHijos());
					if(tasaProducto.getTieneZona()){
						tasaProductoJSONObject.put("tieneZona", "Si");
					}else{
						tasaProductoJSONObject.put("tieneZona", "No");
					}
					tasaProductoJSONObject.put("valorZona", tasaProducto.getValorZona());										
					if(tasaProducto.getTieneGuardaGarage()){
						tasaProductoJSONObject.put("tieneGuardaGarage", "Si");
					}else{
						tasaProductoJSONObject.put("tieneGuardaGarage", "No");
					}
					if(tasaProducto.getTieneKilometrosRecorridos()){
						tasaProductoJSONObject.put("tieneKilometrosRecorridos", "Si");
					}else{
						tasaProductoJSONObject.put("tieneKilometrosRecorridos", "No");
					}
					tasaProductoJSONObject.put("kilometrosRecorridosInicio", tasaProducto.getKilometrosRecorridosInicio());
					tasaProductoJSONObject.put("kilometrosRecorridosFin", tasaProducto.getKilometrosRecorridosFin());
					if(tasaProducto.getTieneCombustibleUtilizado()){
						tasaProductoJSONObject.put("tieneCombustibleUtilizado", "Si");
					}else{
						tasaProductoJSONObject.put("tieneCombustibleUtilizado", "No");
					}
					tasaProductoJSONObject.put("combustibleUtilizadoValorId", tasaProducto.getCombustibleUtilizadoValorId());
					if(tasaProducto.getTieneTipoUso()){
						tasaProductoJSONObject.put("tieneTipoUso", "Si");
					}else{
						tasaProductoJSONObject.put("tieneTipoUso", "No");
					}
					tasaProductoJSONObject.put("tipoUsoListado", tasaProducto.getTipoUsoListado());
					if(tasaProducto.getTieneCargaPasajeros()){
						tasaProductoJSONObject.put("tieneCargaPasajeros", "Si");
					}else{
						tasaProductoJSONObject.put("tieneCargaPasajeros", "No");
					}
					tasaProductoJSONObject.put("cargaPasajerosValor", tasaProducto.getCargaPasajerosValor());
					if(tasaProducto.getTieneAdquisicion()){
						tasaProductoJSONObject.put("tieneAdquisicion", "Si");
					}else{
						tasaProductoJSONObject.put("tieneAdquisicion", "No");
					}
					tasaProductoJSONObject.put("nombreAdquisicion", tasaProducto.getNombreAdquisicion());
					if(tasaProducto.getEsFlotaIndividual()){
						tasaProductoJSONObject.put("esFlotaIndividual", "Si");
					}else{
						tasaProductoJSONObject.put("esFlotaIndividual", "No");
					}
					tasaProductoJSONObject.put("valorFlotaIndividual", tasaProducto.getValorFlotaIndividual());
					if(tasaProducto.getTieneTipoObjetoVehiculo()){
						tasaProductoJSONObject.put("tieneTipoObjetoVehiculo", "Si");
					}else{
						tasaProductoJSONObject.put("tieneTipoObjetoVehiculo", "No");
					}
					tasaProductoJSONObject.put("valorTipoObjetoVehiculo", tasaProducto.getValorTipoObjetoVehiculo());
					if(tasaProducto.getTieneRenovacion()){
						tasaProductoJSONObject.put("tieneRenovacion", "Si");
					}else{
						tasaProductoJSONObject.put("tieneRenovacion", "No");
					}
					tasaProductoJSONArray.add(tasaProductoJSONObject);
				}	
				result.put("numRegistros", i);
				result.put("listadoTasaProducto", tasaProductoJSONArray);
			
				
				//Para cargar combos			

				GrupoPorProductoDAO grupoPorProductoDAOCMB = new GrupoPorProductoDAO();
				GrupoPorProducto grupoPorProductoCMB = new GrupoPorProducto();

				JSONObject grupoPorProductoJSONObject = new JSONObject();
				JSONArray grupoPorProductoJSONArray = new JSONArray();

				List<GrupoPorProducto> listadoGrupoPorProducto = grupoPorProductoDAOCMB
						.buscarTodos();

				for (i = 0; i < listadoGrupoPorProducto.size(); i++) {
					grupoPorProductoCMB = listadoGrupoPorProducto.get(i);
					grupoPorProductoJSONObject.put("codigo", grupoPorProductoCMB.getId());
					grupoPorProductoJSONObject.put("nombre", grupoPorProductoCMB.getId() + " - " +grupoPorProductoCMB.getNombreComercialProducto() + " - " + grupoPorProductoCMB.getGrupoProducto().getNombre());

					grupoPorProductoJSONArray.add(grupoPorProductoJSONObject);
				}

				result.put("listadoGrupoPorProducto",
						grupoPorProductoJSONArray);
				
			}//fin encontrarTodos				
			
			if(tipoConsulta.equals("crear")){
				tasaProductoTransaction.crear(tasaProducto);
			}

			if(tipoConsulta.equals("actualizar")){
				tasaProductoTransaction.editar(tasaProducto);
				}

			if(tipoConsulta.equals("eliminar")){
				tasaProductoTransaction.eliminar(tasaProducto);
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
