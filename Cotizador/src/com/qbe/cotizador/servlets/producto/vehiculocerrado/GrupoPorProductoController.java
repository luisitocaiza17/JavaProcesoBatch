package com.qbe.cotizador.servlets.producto.vehiculocerrado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qbe.cotizador.dao.cotizacion.ProductoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.producto.vehiculo.TipoUsoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.TasaProductoDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.TipoGrupoDAO;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.GrupoProducto;
import com.qbe.cotizador.model.Producto;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.Rol;
import com.qbe.cotizador.model.TasaProducto;
import com.qbe.cotizador.model.TipoGrupo;
import com.qbe.cotizador.model.TipoUso;


import com.qbe.cotizador.model.Usuario;


import com.qbe.cotizador.transaction.producto.GrupoPorProductoTransaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GrupoProductoController
 */
@WebServlet("/GrupoPorProductoController")
public class GrupoPorProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GrupoPorProductoController() {
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
			JSONObject grupoJSONObject = new JSONObject();
			JSONArray grupoJSONArray = new JSONArray();
			
			GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
			GrupoPorProducto grupoPorProducto = new GrupoPorProducto();
			GrupoProductoDAO grupoProductoDAO = new GrupoProductoDAO();
			
			GrupoPorProducto gp = new GrupoPorProducto();
			GrupoPorProductoDAO gpDAO = new GrupoPorProductoDAO();
			
			JSONObject gpJSONObject = new JSONObject();
			JSONArray gpJSONArray = new JSONArray();

			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			String grupoProductoId = request.getParameter("grupoProductoId") == null ? "" : request.getParameter("grupoProductoId");
			String tipoObjeto = request.getParameter("tipoObjeto") == null ? "" : request.getParameter("tipoObjeto");
			
			//Tabla de Mantenimiento
			String nombreComercialProducto = request.getParameter("nombreComercialProducto") == null ? "" : request.getParameter("nombreComercialProducto");
		    String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
		    String activo = request.getParameter("activo") == null ? "" : request.getParameter("activo");
			String productoCampo = request.getParameter("producto") == null ? "" : request.getParameter("producto");
			String grupoProductoCampo = request.getParameter("grupoProducto") == null ? "" : request.getParameter("grupoProducto");
			String tipoGrupoCampo = request.getParameter("tipoGrupo") == null ? "" : request.getParameter("tipoGrupo");
			String tasaFija = request.getParameter("tasaFija") == null ? "" : request.getParameter("tasaFija");
			String formulada = request.getParameter("formulada") == null ? "" : request.getParameter("formulada");
			String porcentajeTasaFija = request.getParameter("porcentajeTasaFija") == null ? "" : request.getParameter("porcentajeTasaFija");
			String porcentajeExtrasTasaFija = request.getParameter("porcentajeExtrasTasaFija") == null ? "" : request.getParameter("porcentajeExtrasTasaFija");
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
			String isInspeccionRequerida = request.getParameter("isInspeccionRequerida") == null ? "" : request.getParameter("isInspeccionRequerida");
			
			String grupoPorProductoId = request.getParameter("grupoPorProductoId") == null ? "" : request.getParameter("grupoPorProductoId");	
			
			GrupoPorProductoTransaction grupoPorProductoTransaction = new GrupoPorProductoTransaction();
			
			if (!codigo.equals(""))
				grupoPorProducto.setId(codigo);
			if (activo.equals("1")){
				grupoPorProducto.setActivo(true);
			}else if (activo.equals("0")){
				grupoPorProducto.setActivo(false);
			}
			if (!nombreComercialProducto.equals(""))
				grupoPorProducto.setNombreComercialProducto(nombreComercialProducto);
			if (!productoCampo.equals("")){
				ProductoDAO productoDAO = new ProductoDAO();
			    Producto producto= new Producto ();
			    producto = productoDAO.buscarPorNombre(productoCampo);
				grupoPorProducto.setProducto(producto);
			}
			if (!grupoProductoCampo.equals("")){
				GrupoProducto grupoProducto1 = new GrupoProducto();
				GrupoProductoDAO grupoProducto1DAO = new GrupoProductoDAO();				
				grupoProducto1 = grupoProducto1DAO.buscarPorNombre(grupoProductoCampo); 
				grupoPorProducto.setGrupoProducto(grupoProducto1);
			}
			if (!tipoGrupoCampo.equals("")){
				TipoGrupo tipoGrupo1 = new TipoGrupo();
				TipoGrupoDAO tipoGrupoDAO = new TipoGrupoDAO();
				tipoGrupo1 = tipoGrupoDAO.buscarPorNombre(tipoGrupoCampo); 
				grupoPorProducto.setTipoGrupo(tipoGrupo1);
			}
			if (tasaFija.equals("1")){
				grupoPorProducto.setTasaFija(true);
			}else if (tasaFija.equals("0")){
				grupoPorProducto.setTasaFija(false);
			}
			if (formulada.equals("1")){
				grupoPorProducto.setFormulada(true);
			}else if (formulada.equals("0")){
				grupoPorProducto.setFormulada(false);
			}
			
			if (!porcentajeTasaFija.equals(""))
				grupoPorProducto.setPorcentajeTasaFija(Double.parseDouble(porcentajeTasaFija));
			
			if (!porcentajeExtrasTasaFija.equals(""))
				grupoPorProducto.setPorcentajeExtrasTasaFija(Double.parseDouble(porcentajeExtrasTasaFija));	
			
			if (tieneSumaAsegurada.equals("1")){
				grupoPorProducto.setTieneSumaAsegurada(true);
			}else if (tieneSumaAsegurada.equals("0")){
				grupoPorProducto.setTieneSumaAsegurada(false);
			}
			
			if (!sumaAseguradaInicio.equals(""))
				grupoPorProducto.setSumaAseguradaInicio(Double.parseDouble(sumaAseguradaInicio));
			
			if (!sumaAseguradaFin.equals(""))
				grupoPorProducto.setSumaAseguradaFin(Double.parseDouble(sumaAseguradaFin));
			
			if (tieneAntiguedadVh.equals("1")){
				grupoPorProducto.setTieneAntiguedadVh(true);
			}else if (tieneAntiguedadVh.equals("0")){
				grupoPorProducto.setTieneAntiguedadVh(false);
			}
			if (!antiguedadInicio.equals(""))
				grupoPorProducto.setAntiguedadInicio(Integer.parseInt(antiguedadInicio));
			if (!antiguedadFin.equals(""))
				grupoPorProducto.setAntiguedadFin(Integer.parseInt(antiguedadFin));
			
			if (tieneDispositivoRastreo.equals("1")){
				grupoPorProducto.setTieneDispositivoRastreo(true);
			}else if (tieneDispositivoRastreo.equals("0")){
				grupoPorProducto.setTieneDispositivoRastreo(false);
			}
			
			if (tieneTipoVehiculo.equals("1")){
				grupoPorProducto.setTieneTipoVehiculo(true);
			}else if (tieneTipoVehiculo.equals("0")){
				grupoPorProducto.setTieneTipoVehiculo(false);
			}
			if (!tipoVehiculoNombre.equals(""))
				grupoPorProducto.setTipoVehiculoNombre(tipoVehiculoNombre);
			
			if (tieneTonelaje.equals("1")){
				grupoPorProducto.setTieneTonelaje(true);
			}else if (tieneTonelaje.equals("0")){
				grupoPorProducto.setTieneTonelaje(false);
			}
			if (!valorTonelajeInicio.equals(""))
				grupoPorProducto.setValorTonelajeInicio(Double.parseDouble(valorTonelajeInicio));
			if (!valorTonelajeFin.equals(""))
				grupoPorProducto.setValorTonelajeFin(Double.parseDouble(valorTonelajeFin));	

			if (tieneRegion.equals("1")){
				grupoPorProducto.setTieneRegion(true);
			}else if (tieneRegion.equals("0")){
				grupoPorProducto.setTieneRegion(false);
			}
			
			if (!valorRegion.equals(""))
				grupoPorProducto.setValorRegion(valorRegion);	
			
			if (tieneDeducible.equals("1")){
				grupoPorProducto.setTieneDeducible(true);
			}else if (tieneDeducible.equals("0")){
				grupoPorProducto.setTieneDeducible(false);
			}
			
			if (!deduciblePorcentajeSiniestro.equals(""))
				grupoPorProducto.setDeduciblePorcentajeSiniestro(Double.parseDouble(deduciblePorcentajeSiniestro));
			if (!deduciblePorcentajeValorAsegurado.equals(""))
				grupoPorProducto.setDeduciblePorcentajeValorAsegurado(Double.parseDouble(deduciblePorcentajeValorAsegurado));

			if (!deducibleMinimo.equals(""))
				grupoPorProducto.setDeducibleMinimo(Double.parseDouble(deducibleMinimo));			
			
			if (tieneDeduciblePerdidaTotalSiniestro.equals("1")){
				grupoPorProducto.setTieneDeduciblePerdidaTotalSiniestro(true);
			}else if (tieneDeduciblePerdidaTotalSiniestro.equals("0")){
				grupoPorProducto.setTieneDeduciblePerdidaTotalSiniestro(false);
			}	
			if (!deduciblePerdidaTotalSiniestro.equals(""))
				grupoPorProducto.setDeduciblePerdidaTotalSiniestro(Double.parseDouble(deduciblePerdidaTotalSiniestro));			
			if (tieneAnoFabricacion.equals("1")){
				grupoPorProducto.setTieneAnoFabricacion(true);
			}else if (tieneAnoFabricacion.equals("0")){
				grupoPorProducto.setTieneAnoFabricacion(false);
			}
			if (!anoFabricacionInicio.equals(""))
				grupoPorProducto.setAnoFabricacionInicio(Integer.parseInt(anoFabricacionInicio));	
			if (!anoFabricacionFin.equals(""))
				grupoPorProducto.setAnoFabricacionFin(Integer.parseInt(anoFabricacionFin));			
			if (tieneEdadConductor.equals("1")){
				grupoPorProducto.setTieneEdadConductor(true);
			}else if (tieneEdadConductor.equals("0")){
				grupoPorProducto.setTieneEdadConductor(false);
			}			
			if (!edadConductorInicio.equals(""))
				grupoPorProducto.setEdadConductorInicio(Integer.parseInt(edadConductorInicio));	
			if (!edadConductorFin.equals(""))
				grupoPorProducto.setEdadConductorFin(Integer.parseInt(edadConductorFin));			
			if (tieneMarca.equals("1")){
				grupoPorProducto.setTieneMarca(true);
			}else if (tieneMarca.equals("0")){
				grupoPorProducto.setTieneMarca(false);
			}
			if (!marcaListado.equals(""))
				grupoPorProducto.setMarcaListado(marcaListado);
			if (tieneModelo.equals("1")){
				grupoPorProducto.setTieneModelo(true);
			}else if (tieneModelo.equals("0")){
				grupoPorProducto.setTieneModelo(false);
			}
			if (!modeloListado.equals(""))
				grupoPorProducto.setMarcaListado(modeloListado);
			if (ceroKilometros.equals("1")){
				grupoPorProducto.setCeroKilometros(true);
			}else if (ceroKilometros.equals("0")){
				grupoPorProducto.setCeroKilometros(false);
			}
			if (tieneConductorGenero.equals("1")){
				grupoPorProducto.setTieneConductorGenero(true);
			}else if (tieneConductorGenero.equals("0")){
				grupoPorProducto.setTieneConductorGenero(false);
			}
			if (!conductorGeneroValor.equals(""))
				grupoPorProducto.setConductorGeneroValor(conductorGeneroValor);
			if (tieneNumeroHijos.equals("1")){
				grupoPorProducto.setTieneNumeroHijos(true);
			}else if (tieneNumeroHijos.equals("0")){
				grupoPorProducto.setTieneNumeroHijos(false);
			}
			if (!numeroHijos.equals(""))
				grupoPorProducto.setNumeroHijos(Integer.parseInt(numeroHijos));
			if (tieneZona.equals("1")){
				grupoPorProducto.setTieneZona(true);
			}else if (tieneZona.equals("0")){
				grupoPorProducto.setTieneZona(false);
			}
			if (!valorZona.equals(""))
				grupoPorProducto.setValorZona(valorZona);
			if (tieneGuardaGarage.equals("1")){
				grupoPorProducto.setTieneGuardaGarage(true);
			}else if (tieneGuardaGarage.equals("0")){
				grupoPorProducto.setTieneGuardaGarage(false);
			}
			
			if (tieneKilometrosRecorridos.equals("1")){
				grupoPorProducto.setTieneKilometrosRecorridos(true);
			}else if (tieneKilometrosRecorridos.equals("0")){
				grupoPorProducto.setTieneKilometrosRecorridos(false);
			}
			if (!kilometrosRecorridosInicio.equals(""))
				grupoPorProducto.setKilometrosRecorridosInicio(Integer.parseInt(kilometrosRecorridosInicio));	
			if (!kilometrosRecorridosFin.equals(""))
				grupoPorProducto.setKilometrosRecorridosFin(Integer.parseInt(kilometrosRecorridosFin));
			if (tieneCombustibleUtilizado.equals("1")){
				grupoPorProducto.setTieneCombustibleUtilizado(true);
			}else if (tieneCombustibleUtilizado.equals("0")){
				grupoPorProducto.setTieneCombustibleUtilizado(false);
			}
			if (!combustibleUtilizadoValorId.equals(""))
				grupoPorProducto.setCombustibleUtilizadoValorId(combustibleUtilizadoValorId);
			if (tieneTipoUso.equals("1")){
				grupoPorProducto.setTieneTipoUso(true);
			}else if (tieneTipoUso.equals("0")){
				grupoPorProducto.setTieneTipoUso(false);
			}
			if (!tipoUsoListado.equals(""))
				grupoPorProducto.setTipoUsoListado(tipoUsoListado);
			if (tieneCargaPasajeros.equals("1")){
				grupoPorProducto.setTieneCargaPasajeros(true);
			}else if (tieneCargaPasajeros.equals("0")){
				grupoPorProducto.setTieneCargaPasajeros(false);
			}
			if (!cargaPasajerosValor.equals(""))
				grupoPorProducto.setCargaPasajerosValor(cargaPasajerosValor);
			if (tieneAdquisicion.equals("1")){
				grupoPorProducto.setTieneAdquisicion(true);
			}else if (tieneAdquisicion.equals("0")){
				grupoPorProducto.setTieneAdquisicion(false);
			}
			if (!nombreAdquisicion.equals(""))
				grupoPorProducto.setNombreAdquisicion(nombreAdquisicion);				
			if (esFlotaIndividual.equals("1")){
				grupoPorProducto.setEsFlotaIndividual(true);
			}else if (esFlotaIndividual.equals("0")){
				grupoPorProducto.setEsFlotaIndividual(false);
			}
			if (!valorFlotaIndividual.equals(""))
				grupoPorProducto.setValorFlotaIndividual(valorFlotaIndividual);
			if (tieneTipoObjetoVehiculo.equals("1")){
				grupoPorProducto.setTieneTipoObjetoVehiculo(true);
			}else if (tieneTipoObjetoVehiculo.equals("0")){
				grupoPorProducto.setTieneTipoObjetoVehiculo(false);
			}
			if (!valorTipoObjetoVehiculo.equals(""))
				grupoPorProducto.setValorTipoObjetoVehiculo(valorTipoObjetoVehiculo);
			if (isInspeccionRequerida.equals("1")){
				grupoPorProducto.setInspeccionRequerida(true);
			}else if (isInspeccionRequerida.equals("0")){
				grupoPorProducto.setInspeccionRequerida(false);
			}
		
			
			// Encontramos los diferentes grupos por producto - Livianos
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Livianos")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Livianos");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene punto de venta asignado, solo muestra los grupo por productos asignados
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
					List<ProductoXPuntoVenta> productosXPuntoVentas = new ArrayList<ProductoXPuntoVenta>();
					productosXPuntoVentas= puntoVenta.getProductoXPuntoVentas();
					ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
					if(productosXPuntoVentas.size()>0){
						
						for(int i=0; i<results.size(); i++){
							for(int j=0; j<productosXPuntoVentas.size(); j++){
							
							grupoPorProducto = results.get(i);
							productoXPuntoVenta = productosXPuntoVentas.get(j);
							
							if(grupoPorProducto.getId().equalsIgnoreCase(productoXPuntoVenta.getGrupoPorProducto().getId())){																														
								grupoJSONObject.put("id", grupoPorProducto.getId());
								grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
								grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);
							}
						   }	
						}
					}
					
				}else{
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}
				
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			// Encontramos los diferentes grupos por producto - Motos
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Motos")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");
				
				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Motos");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene punto de venta asignado, solo muestra los grupo por productos asignados
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
					List<ProductoXPuntoVenta> productosXPuntoVentas = new ArrayList<ProductoXPuntoVenta>();
					productosXPuntoVentas= puntoVenta.getProductoXPuntoVentas();
					ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
					if(productosXPuntoVentas.size()>0){
						
						for(int i=0; i<results.size(); i++){
							for(int j=0; j<productosXPuntoVentas.size(); j++){
							
							grupoPorProducto = results.get(i);
							productoXPuntoVenta = productosXPuntoVentas.get(j);
							
							if(grupoPorProducto.getId().equalsIgnoreCase(productoXPuntoVenta.getGrupoPorProducto().getId())){																														
								grupoJSONObject.put("id", grupoPorProducto.getId());
								grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
								grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);
							}
						   }	
						}
					}
					
				}else{
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}
								
				
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos por producto - Ganadero
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("PYMES")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas()!=null)
					if(usuario.getUsuarioXPuntoVentas().size()>0){
						//si tiene puntos de venta
						puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
					}
				
				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("PYMES");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
					grupoJSONObject.put("id", grupoPorProducto.getId());
					grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
					grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
					grupoJSONArray.add(grupoJSONObject);
				}				
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos por producto - Ganadero
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Ganadero")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}
				
				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Ganadero");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
					grupoJSONObject.put("id", grupoPorProducto.getId());
					grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
					grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
					grupoJSONArray.add(grupoJSONObject);
				}				
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos por producto - Ganadero
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Agricola")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}
				
				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Agricola");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
					grupoJSONObject.put("id", grupoPorProducto.getId());
					grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
					grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
					grupoJSONArray.add(grupoJSONObject);
				}				
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos por producto - Pesados
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Pesados")){
				
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");
				
				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Pesados");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene punto de venta asignado, solo muestra los grupo por productos asignados
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
					
					List<ProductoXPuntoVenta> productosXPuntoVentas = new ArrayList<ProductoXPuntoVenta>();
					productosXPuntoVentas= puntoVenta.getProductoXPuntoVentas();
					
					ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
					if(productosXPuntoVentas.size()>0){
						
						for(int i=0; i<results.size(); i++){
							for(int j=0; j<productosXPuntoVentas.size(); j++){
							
							grupoPorProducto = results.get(i);
							productoXPuntoVenta = productosXPuntoVentas.get(j);
							
							if(grupoPorProducto.getId().equalsIgnoreCase(productoXPuntoVenta.getGrupoPorProducto().getId())){																														
								grupoJSONObject.put("id", grupoPorProducto.getId());
								grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
								grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);
							}
						   }	
						}
					}
					
				}else{
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}
							
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			// Encontramos los diferentes grupos por producto - Publico
			if(tipoConsulta.equals("encontrarTodosPorGrupo") && tipoObjeto.equals("Publicos")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Publico");
				GrupoProducto grupoProducto = grupoProductoDAO.buscarPorId(grupoProductoId);								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorGrupoTipoGrupo(grupoProducto, tipoGrupo);
				
				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene punto de venta asignado, solo muestra los grupo por productos asignados
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();					
					List<ProductoXPuntoVenta> productosXPuntoVentas = new ArrayList<ProductoXPuntoVenta>();
					productosXPuntoVentas= puntoVenta.getProductoXPuntoVentas();
					ProductoXPuntoVenta productoXPuntoVenta = new ProductoXPuntoVenta();
					if(productosXPuntoVentas.size()>0){
						
						for(int i=0; i<results.size(); i++){
							for(int j=0; j<productosXPuntoVentas.size(); j++){
							
							grupoPorProducto = results.get(i);
							productoXPuntoVenta = productosXPuntoVentas.get(j);
							
							if(grupoPorProducto.getId().equalsIgnoreCase(productoXPuntoVenta.getGrupoPorProducto().getId())){																														
								grupoJSONObject.put("id", grupoPorProducto.getId());
								grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
								grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);
							}
						   }	
						}
					}
					
				}else{
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getNombreComercialProducto());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}		
								
				result.put("listadoGruposPorProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos producto - Pesados
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Pesados")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Pesados");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();
				if((rol.getId().equals("1")||rol.getId().equals("2")||rol.getId().equals("7"))&& puntoVenta.getId()==null){//usuarios de qbe
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}else{
					List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
					for(int j=0;j<pxpvs.size();j++){										
							grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
							grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
							grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
							grupoJSONArray.add(grupoJSONObject);					
					}										
				}
												
				result.put("listadoGruposProducto", grupoJSONArray);
			}
						
			// Encontramos los diferentes grupos producto - Dinamicos
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Dinamicos")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Dinamico");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();				
				
				if(rol.getId().equals("1")||rol.getId().equals("2")||rol.getId().equals("7")){//usuarios de qbe				
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
						}
					}else{
						List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
						for(int j=0;j<pxpvs.size();j++){						
								grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
								grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
								grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);					
						}					
				}
				
				
				result.put("listadoGruposProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos producto - Livianos
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Livianos")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Livianos");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();

				
				if((rol.getId().equals("1")||rol.getId().equals("2")||rol.getId().equals("7"))&& puntoVenta.getId()==null){//usuarios de qbe				
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
						}
					}else{
						List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
						for(int j=0;j<pxpvs.size();j++){						
								grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
								grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
								grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);					
						}					
				}
				
				
				result.put("listadoGruposProducto", grupoJSONArray);
			}
						
			// Encontramos los diferentes grupos producto - Motos
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Motos")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Motos");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();
				
				
				if((rol.getId().equals("1")||rol.getId().equals("2")||rol.getId().equals("7"))&& puntoVenta.getId()==null){//usuarios de qbe				
					for(int i=0; i<results.size(); i++){
						grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
						}
					}else{
						List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
						for(int j=0;j<pxpvs.size();j++){						
								grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
								grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
								grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
								grupoJSONArray.add(grupoJSONObject);					
						}					
				}					
				result.put("listadoGruposProducto", grupoJSONArray);
			}
						
			// Encontramos los diferentes grupos producto - Publicos
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Publicos")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Publico");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();
				
				if((rol.getId().equals("1")||rol.getId().equals("2")||rol.getId().equals("7"))&& puntoVenta.getId()==null){//usuarios de qbe				
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
					grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
					grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
					grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
					grupoJSONArray.add(grupoJSONObject);
					}
				}else{
					List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
					for(int j=0;j<pxpvs.size();j++){						
							grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
							grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
							grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
							grupoJSONArray.add(grupoJSONObject);					
					}					
				}
											
				result.put("listadoGruposProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos producto - Ganadero
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("Ganadero") || tipoObjeto.equals("Agricola")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("Ganadero");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();
				if(rol.getNombre().equals("ADMINISTRADOR_GANADERO")){//usuarios de qbe
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}else{
					List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
					for(int j=0;j<pxpvs.size();j++){										
							grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
							grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
							grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
							grupoJSONArray.add(grupoJSONObject);					
					}										
				}
												
				result.put("listadoGruposProducto", grupoJSONArray);
			}
			
			// Encontramos los diferentes grupos producto - PYMES
			if(tipoConsulta.equals("encontrarTodosGrupoProducto") && tipoObjeto.equals("PYMES")){
				Usuario usuario=new Usuario();
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				
				if(usuario==null||usuario.getId()==null)
				throw new Exception("Usuario sin sesi�n");

				Rol rol=new Rol();
				
				if(usuario.getUsuarioRols().size()>0)
					rol=usuario.getUsuarioRols().get(0).getRol();
				
				if(rol==null||rol.getId()==null)
					throw new Exception("Usuario sin Rol");

				PuntoVenta puntoVenta= new PuntoVenta(); 
				if(usuario.getUsuarioXPuntoVentas().size()>0){
					//si tiene puntos de venta
					puntoVenta=usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta();
				}

				TipoGrupoDAO tipoProductoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo = tipoProductoDAO.buscarPorNombre("PYMES");								
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodosPorTipoGrupo(tipoGrupo);
				ProductoXPuntoVentaDAO pxpvDAO=new ProductoXPuntoVentaDAO();
				if(rol.getNombre().equals("ADMINISTRADOR_PYMES")){//usuarios de qbe
				for(int i=0; i<results.size(); i++){
					grupoPorProducto = results.get(i);										
						grupoJSONObject.put("id", grupoPorProducto.getGrupoProducto().getId());
						grupoJSONObject.put("nombre", grupoPorProducto.getGrupoProducto().getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}
				}else{
					List<ProductoXPuntoVenta> pxpvs=(List<ProductoXPuntoVenta>) pxpvDAO.buscarPorGrupoPunto(puntoVenta,tipoGrupo);
					for(int j=0;j<pxpvs.size();j++){										
							grupoJSONObject.put("id", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getId());
							grupoJSONObject.put("nombre", pxpvs.get(j).getGrupoPorProducto().getGrupoProducto().getNombre());									
							grupoJSONObject.put("inspeccionRequerida",pxpvs.get(j).getGrupoPorProducto().getInspeccionRequerida());
							grupoJSONArray.add(grupoJSONObject);					
					}										
				}
												
				result.put("listadoGruposProducto", grupoJSONArray);
			}
						
			// Encontrar las o la tasa asignada al producto
			if(tipoConsulta.equals("encontrarTasaProducto")){
				grupoPorProducto = grupoPorProductoDAO.buscarPorId(grupoProductoId);
				
				if (grupoProductoId.length() >0){
				int tieneTasaFija = grupoPorProducto.getTasaFija() == true ? 1 : 0;
				int esFormulado = grupoPorProducto.getFormulada() == true ? 1 : 0;
				
				Double tasaFijaValor = 0.0;
					
				TasaProductoDAO tasaProductoDAO = new TasaProductoDAO();
				TasaProducto tasaProducto = new TasaProducto();
				
				if(tieneTasaFija == 1 && esFormulado == 0){					
					tasaFijaValor = grupoPorProducto.getPorcentajeTasaFija();
					result.put("tasa_fija_valor", tasaFijaValor);
				}
				if(tieneTasaFija == 0 && esFormulado == 0){					
					List<TasaProducto>tasasProducto = tasaProductoDAO.buscarTodosPorGrupoPorProducto(grupoPorProducto);
					for(int i=0; i<tasasProducto.size(); i++){
						tasaProducto = tasasProducto.get(i);
						grupoJSONObject.put("id", tasaProducto.getId());
						grupoJSONObject.put("nombre", tasaProducto.getNombre());									
						grupoJSONObject.put("inspeccionRequerida",grupoPorProducto.getInspeccionRequerida());
						grupoJSONArray.add(grupoJSONObject);
					}	
					result.put("tasas_listado", grupoJSONArray);
				}
			
				result.put("tiene_tasa_fija", tieneTasaFija);
				result.put("es_formulado", esFormulado);
				// Obtenemos el valor si el grupo por producto tiene inspeccion o no
				String tiene_inspeccion = "NO";
				if(grupoPorProducto.getInspeccionRequerida())
					tiene_inspeccion = "SI";
					
				result.put("tiene_inspeccion", tiene_inspeccion);
				}
			}
			//Para buscar si existe el grupoPorProducto utilizado en alguna poliza
			if(tipoConsulta.equals("buscarIdEnPoliza")){
				String resultado = grupoPorProductoDAO.buscarIdEnPoliza(codigo);
				if (resultado.equals("SI")){
					result.put("existeEnPoliza", "1");
				}else{
					result.put("existeEnPoliza", "0");
				}
			}
			
			//Tabla de Mantenimiento
			if (tipoConsulta.equals("encontrarTodos")) {
				JSONObject grupoPorProductoJSONObject = new JSONObject ();
				JSONArray grupoPorProductoJSONArray= new JSONArray ();
				List<GrupoPorProducto> results = grupoPorProductoDAO.buscarTodos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					grupoPorProducto= results.get(i);
					grupoPorProductoJSONObject.put("codigo", grupoPorProducto.getId());
					grupoPorProductoJSONObject.put("nombreComercialProducto", grupoPorProducto.getNombreComercialProducto());
					grupoPorProductoJSONObject.put("producto", grupoPorProducto.getProducto().getNombre());
					grupoPorProductoJSONObject.put("grupoProducto", grupoPorProducto.getGrupoProducto().getNombre());
					grupoPorProductoJSONObject.put("tipoGrupo", grupoPorProducto.getTipoGrupo().getNombre());
					
					if(grupoPorProducto.getTasaFija())
						grupoPorProductoJSONObject.put("tasaFija", "Si");
					else
						grupoPorProductoJSONObject.put("tasaFija", "No");
					
					if(grupoPorProducto.getFormulada())
						grupoPorProductoJSONObject.put("formulada", "Si");
					else
						grupoPorProductoJSONObject.put("formulada", "No");
					
					if(grupoPorProducto.getActivo())
						grupoPorProductoJSONObject.put("activo", "Si");
					else
						grupoPorProductoJSONObject.put("activo", "No");
					
					grupoPorProductoJSONObject.put("porcentajeTasaFija", grupoPorProducto.getPorcentajeTasaFija());
					grupoPorProductoJSONObject.put("porcentajeExtrasTasaFija", grupoPorProducto.getPorcentajeExtrasTasaFija());
					
					if(grupoPorProducto.getTieneSumaAsegurada())
						grupoPorProductoJSONObject.put("tieneSumaAsegurada", "Si");
					else
						grupoPorProductoJSONObject.put("tieneSumaAsegurada", "No");					
					grupoPorProductoJSONObject.put("sumaAseguradaInicio", grupoPorProducto.getSumaAseguradaInicio());
					grupoPorProductoJSONObject.put("sumaAseguradaFin", grupoPorProducto.getSumaAseguradaFin());
					
					if(grupoPorProducto.getTieneAntiguedadVh())
						grupoPorProductoJSONObject.put("tieneAntiguedadVh", "Si");
					else
						grupoPorProductoJSONObject.put("tieneAntiguedadVh", "No");					
					grupoPorProductoJSONObject.put("antiguedadInicio", grupoPorProducto.getAntiguedadInicio());
					grupoPorProductoJSONObject.put("antiguedadFin", grupoPorProducto.getAntiguedadFin());
					
					if(grupoPorProducto.getTieneDispositivoRastreo())
						grupoPorProductoJSONObject.put("tieneDispositivoRastreo", "Si");
					else
						grupoPorProductoJSONObject.put("tieneDispositivoRastreo", "No");
					
					if(grupoPorProducto.getTieneTipoVehiculo())
						grupoPorProductoJSONObject.put("tieneTipoVehiculo", "Si");
					else
						grupoPorProductoJSONObject.put("tieneTipoVehiculo", "No");
					grupoPorProductoJSONObject.put("tipoVehiculoNombre", grupoPorProducto.getTipoVehiculoNombre());
					if(grupoPorProducto.getTieneTonelaje())
						grupoPorProductoJSONObject.put("tieneTonelaje", "Si");
					else
						grupoPorProductoJSONObject.put("tieneTonelaje", "No");
					grupoPorProductoJSONObject.put("valorTonelajeInicio", grupoPorProducto.getValorTonelajeInicio());
					grupoPorProductoJSONObject.put("valorTonelajeFin", grupoPorProducto.getValorTonelajeFin());
					if(grupoPorProducto.getTieneRegion())
						grupoPorProductoJSONObject.put("tieneRegion", "Si");
					else
						grupoPorProductoJSONObject.put("tieneRegion", "No");
					grupoPorProductoJSONObject.put("valorRegion", grupoPorProducto.getValorRegion());
					if(grupoPorProducto.getTieneDeducible())
						grupoPorProductoJSONObject.put("tieneDeducible", "Si");
					else
						grupoPorProductoJSONObject.put("tieneDeducible", "No");					
					grupoPorProductoJSONObject.put("deduciblePorcentajeSiniestro", grupoPorProducto.getDeduciblePorcentajeSiniestro());
					grupoPorProductoJSONObject.put("deduciblePorcentajeValorAsegurado", grupoPorProducto.getDeduciblePorcentajeValorAsegurado());
					grupoPorProductoJSONObject.put("deducibleMinimo", grupoPorProducto.getDeducibleMinimo());
					if(grupoPorProducto.getTieneDeduciblePerdidaTotalSiniestro())
						grupoPorProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "Si");
					else
						grupoPorProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "No");
					grupoPorProductoJSONObject.put("deduciblePerdidaTotalSiniestro", grupoPorProducto.getDeduciblePerdidaTotalSiniestro());
					if(grupoPorProducto.getTieneAnoFabricacion())
						grupoPorProductoJSONObject.put("tieneAnoFabricacion", "Si");
					else
						grupoPorProductoJSONObject.put("tieneAnoFabricacion", "No");
					grupoPorProductoJSONObject.put("anoFabricacionInicio", grupoPorProducto.getAnoFabricacionInicio());
					grupoPorProductoJSONObject.put("anoFabricacionFin", grupoPorProducto.getAnoFabricacionFin());
					if(grupoPorProducto.getTieneEdadConductor())
						grupoPorProductoJSONObject.put("tieneEdadConductor", "Si");
					else
						grupoPorProductoJSONObject.put("tieneEdadConductor", "No");					
					grupoPorProductoJSONObject.put("edadConductorInicio", grupoPorProducto.getEdadConductorInicio());
					grupoPorProductoJSONObject.put("edadConductorFin", grupoPorProducto.getEdadConductorFin());
					if(grupoPorProducto.getTieneMarca())
						grupoPorProductoJSONObject.put("tieneMarca", "Si");
					else
						grupoPorProductoJSONObject.put("tieneMarca", "No");					
					grupoPorProductoJSONObject.put("marcaListado", grupoPorProducto.getMarcaListado());
					if(grupoPorProducto.getTieneModelo())
						grupoPorProductoJSONObject.put("tieneModelo", "Si");
					else
						grupoPorProductoJSONObject.put("tieneModelo", "No");					
					grupoPorProductoJSONObject.put("modeloListado", grupoPorProducto.getModeloListado());
					if(grupoPorProducto.getCeroKilometros())
						grupoPorProductoJSONObject.put("ceroKilometros", "Si");
					else
						grupoPorProductoJSONObject.put("ceroKilometros", "No");
					if(grupoPorProducto.getTieneConductorGenero())
						grupoPorProductoJSONObject.put("tieneConductorGenero", "Si");
					else
						grupoPorProductoJSONObject.put("tieneConductorGenero", "No");
					grupoPorProductoJSONObject.put("conductorGeneroValor", grupoPorProducto.getConductorGeneroValor());
					if(grupoPorProducto.getTieneNumeroHijos())
						grupoPorProductoJSONObject.put("tieneNumeroHijos", "Si");
					else
						grupoPorProductoJSONObject.put("tieneNumeroHijos", "No");
					grupoPorProductoJSONObject.put("numeroHijos", grupoPorProducto.getNumeroHijos());
					if(grupoPorProducto.getTieneZona())
						grupoPorProductoJSONObject.put("tieneZona", "Si");
					else
						grupoPorProductoJSONObject.put("tieneZona", "No");
					grupoPorProductoJSONObject.put("valorZona", grupoPorProducto.getValorZona());										
					if(grupoPorProducto.getTieneGuardaGarage())
						grupoPorProductoJSONObject.put("tieneGuardaGarage", "Si");
					else
						grupoPorProductoJSONObject.put("tieneGuardaGarage", "No");
					if(grupoPorProducto.getTieneKilometrosRecorridos())
						grupoPorProductoJSONObject.put("tieneKilometrosRecorridos", "Si");
					else
						grupoPorProductoJSONObject.put("tieneKilometrosRecorridos", "No");
					grupoPorProductoJSONObject.put("kilometrosRecorridosInicio", grupoPorProducto.getKilometrosRecorridosInicio());
					grupoPorProductoJSONObject.put("kilometrosRecorridosFin", grupoPorProducto.getKilometrosRecorridosFin());
					if(grupoPorProducto.getTieneCombustibleUtilizado())
						grupoPorProductoJSONObject.put("tieneCombustibleUtilizado", "Si");
					else
						grupoPorProductoJSONObject.put("tieneCombustibleUtilizado", "No");
					grupoPorProductoJSONObject.put("combustibleUtilizadoValorId", grupoPorProducto.getCombustibleUtilizadoValorId());
					if(grupoPorProducto.getTieneTipoUso())
						grupoPorProductoJSONObject.put("tieneTipoUso", "Si");
					else
						grupoPorProductoJSONObject.put("tieneTipoUso", "No");
					grupoPorProductoJSONObject.put("tipoUsoListado", grupoPorProducto.getTipoUsoListado());
					if(grupoPorProducto.getTieneCargaPasajeros())
						grupoPorProductoJSONObject.put("tieneCargaPasajeros", "Si");
					else
						grupoPorProductoJSONObject.put("tieneCargaPasajeros", "No");
					grupoPorProductoJSONObject.put("cargaPasajerosValor", grupoPorProducto.getCargaPasajerosValor());
					if(grupoPorProducto.getTieneAdquisicion())
						grupoPorProductoJSONObject.put("tieneAdquisicion", "Si");
					else
						grupoPorProductoJSONObject.put("tieneAdquisicion", "No");
					grupoPorProductoJSONObject.put("nombreAdquisicion", grupoPorProducto.getNombreAdquisicion());
					if(grupoPorProducto.getEsFlotaIndividual())
						grupoPorProductoJSONObject.put("esFlotaIndividual", "Si");
					else
						grupoPorProductoJSONObject.put("esFlotaIndividual", "No");
					grupoPorProductoJSONObject.put("valorFlotaIndividual", grupoPorProducto.getValorFlotaIndividual());
					if(grupoPorProducto.getTieneTipoObjetoVehiculo())
						grupoPorProductoJSONObject.put("tieneTipoObjetoVehiculo", "Si");
					else
						grupoPorProductoJSONObject.put("tieneTipoObjetoVehiculo", "No");
					grupoPorProductoJSONObject.put("valorTipoObjetoVehiculo", grupoPorProducto.getValorTipoObjetoVehiculo());
					if(grupoPorProducto.getTieneRenovacion())
						grupoPorProductoJSONObject.put("tieneRenovacion", "Si");
					else
						grupoPorProductoJSONObject.put("tieneRenovacion", "No");
					if(grupoPorProducto.getInspeccionRequerida())
						grupoPorProductoJSONObject.put("isInspeccionRequerida", "Si");
					else
						grupoPorProductoJSONObject.put("isInspeccionRequerida", "No");
					grupoPorProductoJSONArray.add(grupoPorProductoJSONObject);
				}
				result.put("numRegistros", i);
				result.put("listadoGrupoPorProducto", grupoPorProductoJSONArray);

				
				//Para cargar combos
				ProductoDAO productoDAO = new ProductoDAO();
				Producto producto= new Producto ();

				JSONObject productoJSONObject = new JSONObject();
				JSONArray productoJSONArray = new JSONArray();

				List<Producto> listaProducto= productoDAO
						.buscarTodos();

				for (i = 0; i < listaProducto.size(); i++) {
					producto = listaProducto.get(i);
					productoJSONObject.put("codigo",
							producto.getId());
					productoJSONObject.put("nombre",
							producto.getNombre());

					productoJSONArray
							.add(productoJSONObject);
				}

				result.put("listadoProducto",
						productoJSONArray);

				GrupoProductoDAO grupoProductoDAOCMB = new GrupoProductoDAO();
				GrupoProducto grupoProducto = new GrupoProducto();

				JSONObject grupoProductoJSONObject = new JSONObject();
				JSONArray grupoProductoJSONArray = new JSONArray();

				List<GrupoProducto> listadoGrupoProducto = grupoProductoDAOCMB
						.buscarTodos();

				for (i = 0; i < listadoGrupoProducto.size(); i++) {
					grupoProducto = listadoGrupoProducto.get(i);
					grupoProductoJSONObject.put("codigo", grupoProducto.getId());
					grupoProductoJSONObject.put("nombre", grupoProducto.getNombre());

					grupoProductoJSONArray.add(grupoProductoJSONObject);
				}

				result.put("listadoGrupoProducto",
						grupoProductoJSONArray);
				
				TipoGrupoDAO tipoGrupoDAO = new TipoGrupoDAO();
				TipoGrupo tipoGrupo= new TipoGrupo();

				JSONObject tipoGrupoJSONObject = new JSONObject();
				JSONArray  tipoGrupoJSONArray = new JSONArray();

				List<TipoGrupo> listadoTipoGrupo = tipoGrupoDAO.buscarTodos();

				for (i = 0; i < listadoTipoGrupo.size(); i++) {
					tipoGrupo = listadoTipoGrupo.get(i);
					tipoGrupoJSONObject.put("codigo", tipoGrupo.getId());
					tipoGrupoJSONObject.put("nombre", tipoGrupo.getNombre());
					tipoGrupoJSONArray.add(tipoGrupoJSONObject);
				}

				result.put("listadoTipoGrupo",
						tipoGrupoJSONArray);
				
				TipoUsoDAO tipoUsoDAO = new TipoUsoDAO();
				TipoUso tipoUso = new TipoUso();

				JSONObject tipoUsoJSONObject = new JSONObject();
				JSONArray  tipoUsoJSONArray = new JSONArray();

				List<TipoUso> listadoTipoUso = tipoUsoDAO.buscarTodos();

				for (i = 0; i < listadoTipoUso.size(); i++) {
					tipoUso = listadoTipoUso.get(i);
					tipoUsoJSONObject.put("codigo", tipoUso.getId());
					tipoUsoJSONObject.put("nombre", tipoUso.getNombre());
					tipoUsoJSONArray.add(tipoUsoJSONObject);
				}

				result.put("listadoTipoUso",
						tipoUsoJSONArray);				
				
			}
			
			//Metodo para obtener la informacion de una tasa de un producto
			if (tipoConsulta.equals("encontrarInformacionTasaProducto")) {
				String tasaProductoValor = request.getParameter("tasaProducto") == null ? "" : request.getParameter("tasaProducto");
				
				TasaProductoDAO tasaProductoDAO = new TasaProductoDAO();
				TasaProducto tasaProducto = tasaProductoDAO.buscarPorId(tasaProductoValor);								
				
				JSONObject tasaProductoJSONObject = new JSONObject ();
				tasaProductoJSONObject.put("porcentaje_casco", tasaProducto.getPorcentajeCasco());
				tasaProductoJSONObject.put("porcentaje_extras", tasaProducto.getPorcentajeExtras());				
				
				if(tasaProducto.getTieneSumaAsegurada())
					tasaProductoJSONObject.put("tieneSumaAsegurada", "Si");
				else
					tasaProductoJSONObject.put("tieneSumaAsegurada", "No");
				tasaProductoJSONObject.put("sumaAseguradaInicio", tasaProducto.getSumaAseguradaInicio());
				tasaProductoJSONObject.put("sumaAseguradaFin", tasaProducto.getSumaAseguradaFin());
				
				if(tasaProducto.getTieneAntiguedadVh())
					tasaProductoJSONObject.put("tieneAntiguedadVh", "Si");
				else
					tasaProductoJSONObject.put("tieneAntiguedadVh", "No");					
				tasaProductoJSONObject.put("antiguedadInicio", tasaProducto.getAntiguedadInicio());
				tasaProductoJSONObject.put("antiguedadFin", tasaProducto.getAntiguedadFin());
				
				if(tasaProducto.getTieneDispositivoRastreo())
					tasaProductoJSONObject.put("tieneDispositivoRastreo", "Si");
				else
					tasaProductoJSONObject.put("tieneDispositivoRastreo", "No");
				
				if(tasaProducto.getTieneTipoVehiculo())
					tasaProductoJSONObject.put("tieneTipoVehiculo", "Si");
				else
					tasaProductoJSONObject.put("tieneTipoVehiculo", "No");
				tasaProductoJSONObject.put("tipoVehiculoNombre", tasaProducto.getTipoVehiculoNombre());
				
				if(tasaProducto.getTieneTonelaje())
					tasaProductoJSONObject.put("tieneTonelaje", "Si");
				else
					tasaProductoJSONObject.put("tieneTonelaje", "No");
				tasaProductoJSONObject.put("valorTonelajeInicio", tasaProducto.getValorTonelajeInicio());
				tasaProductoJSONObject.put("valorTonelajeFin", tasaProducto.getValorTonelajeFin());
				
				if(tasaProducto.getTieneRegion())
					tasaProductoJSONObject.put("tieneRegion", "Si");
				else
					tasaProductoJSONObject.put("tieneRegion", "No");
				tasaProductoJSONObject.put("valorRegion", tasaProducto.getValorRegion());
				
				if(tasaProducto.getTieneDeducible())
					tasaProductoJSONObject.put("tieneDeducible", "Si");
				else
					tasaProductoJSONObject.put("tieneDeducible", "No");					
				tasaProductoJSONObject.put("deduciblePorcentajeSiniestro", tasaProducto.getDeduciblePorcentajeSiniestro());
				tasaProductoJSONObject.put("deduciblePorcentajeValorAsegurado", tasaProducto.getDeduciblePorcentajeValorAsegurado());
				tasaProductoJSONObject.put("deducibleMinimo", tasaProducto.getDeducibleMinimo());
				if(tasaProducto.getTieneDeduciblePerdidaTotalSiniestro())
					tasaProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "Si");
				else
					tasaProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "No");
				tasaProductoJSONObject.put("deduciblePerdidaTotalSiniestro", tasaProducto.getDeduciblePerdidaTotalSiniestro());
				
				if(tasaProducto.getTieneAnoFabricacion())
					tasaProductoJSONObject.put("tieneAnoFabricacion", "Si");
				else
					tasaProductoJSONObject.put("tieneAnoFabricacion", "No");
				tasaProductoJSONObject.put("anoFabricacionInicio", tasaProducto.getAnoFabricacionInicio());
				tasaProductoJSONObject.put("anoFabricacionFin", tasaProducto.getAnoFabricacionFin());
				
				if(tasaProducto.getTieneEdadConductor())
					tasaProductoJSONObject.put("tieneEdadConductor", "Si");
				else
					tasaProductoJSONObject.put("tieneEdadConductor", "No");					
				tasaProductoJSONObject.put("edadConductorInicio", tasaProducto.getEdadConductorInicio());
				tasaProductoJSONObject.put("edadConductorFin", tasaProducto.getEdadConductorFin());
								
				if(tasaProducto.getTieneMarca())
					tasaProductoJSONObject.put("tieneMarca", "Si");
				else
					tasaProductoJSONObject.put("tieneMarca", "No");					
				tasaProductoJSONObject.put("marcaListado", tasaProducto.getMarcaListadi());
				
				if(tasaProducto.getTieneModelo())
					tasaProductoJSONObject.put("tieneModelo", "Si");
				else
					tasaProductoJSONObject.put("tieneModelo", "No");					
				tasaProductoJSONObject.put("modeloListado", tasaProducto.getModeloListado());
								
				if(tasaProducto.getCeroKilometros())
					tasaProductoJSONObject.put("ceroKilometros", "Si");
				else
					tasaProductoJSONObject.put("ceroKilometros", "No");
				
				if(tasaProducto.getTieneConductorGenero())
					tasaProductoJSONObject.put("tieneConductorGenero", "Si");
				else
					tasaProductoJSONObject.put("tieneConductorGenero", "No");
				tasaProductoJSONObject.put("conductorGeneroValor", tasaProducto.getConductorGeneroValor());
				
				if(tasaProducto.getTieneNumeroHijos())
					tasaProductoJSONObject.put("tieneNumeroHijos", "Si");
				else
					tasaProductoJSONObject.put("tieneNumeroHijos", "No");
				tasaProductoJSONObject.put("numeroHijos", tasaProducto.getNumeroHijos());
				
				if(tasaProducto.getTieneZona())
					tasaProductoJSONObject.put("tieneZona", "Si");
				else
					tasaProductoJSONObject.put("tieneZona", "No");
				tasaProductoJSONObject.put("valorZona", tasaProducto.getValorZona());
				
				if(tasaProducto.getTieneGuardaGarage())
					tasaProductoJSONObject.put("tieneGuardaGarage", "Si");
				else
					tasaProductoJSONObject.put("tieneGuardaGarage", "No");
																						
				if(tasaProducto.getTieneKilometrosRecorridos())
					tasaProductoJSONObject.put("tieneKilometrosRecorridos", "Si");
				else
					tasaProductoJSONObject.put("tieneKilometrosRecorridos", "No");
				tasaProductoJSONObject.put("kilometrosRecorridosInicio", tasaProducto.getKilometrosRecorridosInicio());
				tasaProductoJSONObject.put("kilometrosRecorridosFin", tasaProducto.getKilometrosRecorridosFin());
				
				if(tasaProducto.getTieneCombustibleUtilizado())
					tasaProductoJSONObject.put("tieneCombustibleUtilizado", "Si");
				else
					tasaProductoJSONObject.put("tieneCombustibleUtilizado", "No");
				tasaProductoJSONObject.put("combustibleUtilizadoValorId", tasaProducto.getCombustibleUtilizadoValorId());
				
				if(tasaProducto.getTieneTipoUso())
					tasaProductoJSONObject.put("tieneTipoUso", "Si");
				else
					tasaProductoJSONObject.put("tieneTipoUso", "No");
				tasaProductoJSONObject.put("tipoUsoListado", tasaProducto.getTipoUsoListado());
				
				if(tasaProducto.getTieneCargaPasajeros())
					tasaProductoJSONObject.put("tieneCargaPasajeros", "Si");
				else
					tasaProductoJSONObject.put("tieneCargaPasajeros", "No");
				tasaProductoJSONObject.put("cargaPasajerosValor", tasaProducto.getCargaPasajerosValor());
				
				if(tasaProducto.getTieneAdquisicion())
					tasaProductoJSONObject.put("tieneAdquisicion", "Si");
				else
					tasaProductoJSONObject.put("tieneAdquisicion", "No");
				tasaProductoJSONObject.put("nombreAdquisicion", tasaProducto.getNombreAdquisicion());
				
				if(tasaProducto.getEsFlotaIndividual())
					tasaProductoJSONObject.put("esFlotaIndividual", "Si");
				else
					tasaProductoJSONObject.put("esFlotaIndividual", "No");
				tasaProductoJSONObject.put("valorFlotaIndividual", tasaProducto.getValorFlotaIndividual());
				
				if(tasaProducto.getTieneTipoObjetoVehiculo())
					tasaProductoJSONObject.put("tieneTipoObjetoVehiculo", "Si");
				else
					tasaProductoJSONObject.put("tieneTipoObjetoVehiculo", "No");
				tasaProductoJSONObject.put("valorTipoObjetoVehiculo", tasaProducto.getValorTipoObjetoVehiculo());
				
				if(tasaProducto.getTieneRenovacion())
					tasaProductoJSONObject.put("tieneRenovacion", "Si");
				else
					tasaProductoJSONObject.put("tieneRenovacion", "No");
												
				result.put("tasaProducto", tasaProductoJSONObject);
								
			}
			
			
			
			// Obtenemos los datos json del grupo por producto por medio del id
			if (tipoConsulta.equals("encontrarPorId")) {
				JSONObject grupoPorProductoJSONObject = new JSONObject ();
				JSONArray grupoPorProductoJSONArray= new JSONArray ();
								
				grupoPorProducto = grupoPorProductoDAO.buscarPorId(grupoPorProductoId);
								
				grupoPorProductoJSONObject.put("nombreComercialProducto", grupoPorProducto.getNombreComercialProducto());															
				grupoPorProductoJSONObject.put("porcentajeTasaFija", grupoPorProducto.getPorcentajeTasaFija());
				grupoPorProductoJSONObject.put("porcentajeExtrasTasaFija", grupoPorProducto.getPorcentajeExtrasTasaFija());
					
				if(grupoPorProducto.getTieneSumaAsegurada())
					grupoPorProductoJSONObject.put("tieneSumaAsegurada", "Si");
				else
				grupoPorProductoJSONObject.put("tieneSumaAsegurada", "No");					
				grupoPorProductoJSONObject.put("sumaAseguradaInicio", grupoPorProducto.getSumaAseguradaInicio());
				grupoPorProductoJSONObject.put("sumaAseguradaFin", grupoPorProducto.getSumaAseguradaFin());
					
				if(grupoPorProducto.getTieneAntiguedadVh())
					grupoPorProductoJSONObject.put("tieneAntiguedadVh", "Si");
				else
					grupoPorProductoJSONObject.put("tieneAntiguedadVh", "No");					
				grupoPorProductoJSONObject.put("antiguedadInicio", grupoPorProducto.getAntiguedadInicio());
				grupoPorProductoJSONObject.put("antiguedadFin", grupoPorProducto.getAntiguedadFin());
					
				if(grupoPorProducto.getTieneDispositivoRastreo())
					grupoPorProductoJSONObject.put("tieneDispositivoRastreo", "Si");
				else
					grupoPorProductoJSONObject.put("tieneDispositivoRastreo", "No");
					
				if(grupoPorProducto.getTieneTipoVehiculo())
					grupoPorProductoJSONObject.put("tieneTipoVehiculo", "Si");
				else
					grupoPorProductoJSONObject.put("tieneTipoVehiculo", "No");
				grupoPorProductoJSONObject.put("tipoVehiculoNombre", grupoPorProducto.getTipoVehiculoNombre());
				if(grupoPorProducto.getTieneTonelaje())
					grupoPorProductoJSONObject.put("tieneTonelaje", "Si");
				else
				grupoPorProductoJSONObject.put("tieneTonelaje", "No");
				
				grupoPorProductoJSONObject.put("valorTonelajeInicio", grupoPorProducto.getValorTonelajeInicio());
				grupoPorProductoJSONObject.put("valorTonelajeFin", grupoPorProducto.getValorTonelajeFin());
				if(grupoPorProducto.getTieneRegion())
					grupoPorProductoJSONObject.put("tieneRegion", "Si");
				else
					grupoPorProductoJSONObject.put("tieneRegion", "No");
				grupoPorProductoJSONObject.put("valorRegion", grupoPorProducto.getValorRegion());
				if(grupoPorProducto.getTieneDeducible())
					grupoPorProductoJSONObject.put("tieneDeducible", "Si");
				else
					grupoPorProductoJSONObject.put("tieneDeducible", "No");					
				grupoPorProductoJSONObject.put("deduciblePorcentajeSiniestro", grupoPorProducto.getDeduciblePorcentajeSiniestro());
				grupoPorProductoJSONObject.put("deduciblePorcentajeValorAsegurado", grupoPorProducto.getDeduciblePorcentajeValorAsegurado());
				grupoPorProductoJSONObject.put("deducibleMinimo", grupoPorProducto.getDeducibleMinimo());
				if(grupoPorProducto.getTieneDeduciblePerdidaTotalSiniestro())
					grupoPorProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "Si");
				else
					grupoPorProductoJSONObject.put("tieneDeduciblePerdidaTotalSiniestro", "No");
				grupoPorProductoJSONObject.put("deduciblePerdidaTotalSiniestro", grupoPorProducto.getDeduciblePerdidaTotalSiniestro());
				if(grupoPorProducto.getTieneAnoFabricacion())
					grupoPorProductoJSONObject.put("tieneAnoFabricacion", "Si");
				else
					grupoPorProductoJSONObject.put("tieneAnoFabricacion", "No");
				grupoPorProductoJSONObject.put("anoFabricacionInicio", grupoPorProducto.getAnoFabricacionInicio());
				grupoPorProductoJSONObject.put("anoFabricacionFin", grupoPorProducto.getAnoFabricacionFin());
				if(grupoPorProducto.getTieneEdadConductor())
					grupoPorProductoJSONObject.put("tieneEdadConductor", "Si");
				else
					grupoPorProductoJSONObject.put("tieneEdadConductor", "No");					
				grupoPorProductoJSONObject.put("edadConductorInicio", grupoPorProducto.getEdadConductorInicio());
				grupoPorProductoJSONObject.put("edadConductorFin", grupoPorProducto.getEdadConductorFin());
				if(grupoPorProducto.getTieneMarca())
					grupoPorProductoJSONObject.put("tieneMarca", "Si");
				else
					grupoPorProductoJSONObject.put("tieneMarca", "No");					
				grupoPorProductoJSONObject.put("marcaListado", grupoPorProducto.getMarcaListado());
				if(grupoPorProducto.getTieneModelo())
					grupoPorProductoJSONObject.put("tieneModelo", "Si");
				else
						grupoPorProductoJSONObject.put("tieneModelo", "No");					
				grupoPorProductoJSONObject.put("modeloListado", grupoPorProducto.getModeloListado());
				if(grupoPorProducto.getCeroKilometros())
					grupoPorProductoJSONObject.put("ceroKilometros", "Si");
				else
					grupoPorProductoJSONObject.put("ceroKilometros", "No");
				if(grupoPorProducto.getTieneConductorGenero())
					grupoPorProductoJSONObject.put("tieneConductorGenero", "Si");
				else
					grupoPorProductoJSONObject.put("tieneConductorGenero", "No");
				grupoPorProductoJSONObject.put("conductorGeneroValor", grupoPorProducto.getConductorGeneroValor());
				if(grupoPorProducto.getTieneNumeroHijos())
					grupoPorProductoJSONObject.put("tieneNumeroHijos", "Si");
				else
					grupoPorProductoJSONObject.put("tieneNumeroHijos", "No");
				grupoPorProductoJSONObject.put("numeroHijos", grupoPorProducto.getNumeroHijos());
				if(grupoPorProducto.getTieneZona())
					grupoPorProductoJSONObject.put("tieneZona", "Si");
				else
					grupoPorProductoJSONObject.put("tieneZona", "No");
				grupoPorProductoJSONObject.put("valorZona", grupoPorProducto.getValorZona());										
				if(grupoPorProducto.getTieneGuardaGarage())
					grupoPorProductoJSONObject.put("tieneGuardaGarage", "Si");
				else
					grupoPorProductoJSONObject.put("tieneGuardaGarage", "No");
				if(grupoPorProducto.getTieneKilometrosRecorridos())
					grupoPorProductoJSONObject.put("tieneKilometrosRecorridos", "Si");
				else
					grupoPorProductoJSONObject.put("tieneKilometrosRecorridos", "No");
				grupoPorProductoJSONObject.put("kilometrosRecorridosInicio", grupoPorProducto.getKilometrosRecorridosInicio());
				grupoPorProductoJSONObject.put("kilometrosRecorridosFin", grupoPorProducto.getKilometrosRecorridosFin());
				if(grupoPorProducto.getTieneCombustibleUtilizado())
					grupoPorProductoJSONObject.put("tieneCombustibleUtilizado", "Si");
				else
					grupoPorProductoJSONObject.put("tieneCombustibleUtilizado", "No");
				grupoPorProductoJSONObject.put("combustibleUtilizadoValorId", grupoPorProducto.getCombustibleUtilizadoValorId());
				if(grupoPorProducto.getTieneTipoUso())
					grupoPorProductoJSONObject.put("tieneTipoUso", "Si");
				else
					grupoPorProductoJSONObject.put("tieneTipoUso", "No");
				grupoPorProductoJSONObject.put("tipoUsoListado", grupoPorProducto.getTipoUsoListado());
				if(grupoPorProducto.getTieneCargaPasajeros())
					grupoPorProductoJSONObject.put("tieneCargaPasajeros", "Si");
				else
					grupoPorProductoJSONObject.put("tieneCargaPasajeros", "No");
				grupoPorProductoJSONObject.put("cargaPasajerosValor", grupoPorProducto.getCargaPasajerosValor());
				if(grupoPorProducto.getTieneAdquisicion())
					grupoPorProductoJSONObject.put("tieneAdquisicion", "Si");
				else
					grupoPorProductoJSONObject.put("tieneAdquisicion", "No");
				grupoPorProductoJSONObject.put("nombreAdquisicion", grupoPorProducto.getNombreAdquisicion());
				if(grupoPorProducto.getEsFlotaIndividual())
					grupoPorProductoJSONObject.put("esFlotaIndividual", "Si");
				else
					grupoPorProductoJSONObject.put("esFlotaIndividual", "No");
				grupoPorProductoJSONObject.put("valorFlotaIndividual", grupoPorProducto.getValorFlotaIndividual());
				if(grupoPorProducto.getTieneTipoObjetoVehiculo())
					grupoPorProductoJSONObject.put("tieneTipoObjetoVehiculo", "Si");
				else
					grupoPorProductoJSONObject.put("tieneTipoObjetoVehiculo", "No");
				grupoPorProductoJSONObject.put("valorTipoObjetoVehiculo", grupoPorProducto.getValorTipoObjetoVehiculo());
				if(grupoPorProducto.getTieneRenovacion())
					grupoPorProductoJSONObject.put("tieneRenovacion", "Si");
				else
					grupoPorProductoJSONObject.put("tieneRenovacion", "No");
				if(grupoPorProducto.getInspeccionRequerida())
					grupoPorProductoJSONObject.put("isInspeccionRequerida", "Si");
				else
					grupoPorProductoJSONObject.put("isInspeccionRequerida", "No");
				grupoPorProductoJSONArray.add(grupoPorProductoJSONObject);
				
				result.put("grupoPorProducto", grupoPorProductoJSONArray);
			}				
			
			if(tipoConsulta.equals("encontrarGrupoPorProductoPorGrupoProducto"))
			{ 
				String grupoProducto_Id = request.getParameter("grupoProductoId") == null ? "" : request.getParameter("grupoProductoId");
				GrupoProducto grupoxProducto = grupoProductoDAO.buscarPorId(grupoProducto_Id);	
				List<GrupoPorProducto> results = gpDAO.buscarTodosPorGrupo(grupoxProducto);

				int i=0;
				for(i=0; i<results.size(); i++){
					gp = results.get(i);
					gpJSONObject.put("codigo", gp.getId());
					gpJSONObject.put("nombre", gp.getNombreComercialProducto());															
					gpJSONArray.add(gpJSONObject);
				}				
				result.put("listadoGrupoPorProducto", gpJSONArray);															
			}
				
			
			if(tipoConsulta.equals("crear")){
				grupoPorProductoTransaction.crear(grupoPorProducto);
				String id = grupoPorProducto.getId();
				String tasaFijaFinal = String.valueOf(grupoPorProducto.getTasaFija());
				String formuladaFinal = String.valueOf(grupoPorProducto.getFormulada());
				result.put("id", id);
				result.put("tasaFijaFinal", tasaFijaFinal);
				result.put("formuladaFinal", formuladaFinal);
			}
			
			if(tipoConsulta.equals("actualizar")){
				grupoPorProducto = grupoPorProductoTransaction.editar(grupoPorProducto);
				String id = grupoPorProducto.getId();
				String tasaFijaFinal = String.valueOf(grupoPorProducto.getTasaFija());
				String formuladaFinal = String.valueOf(grupoPorProducto.getFormulada());
				result.put("id", id);
				result.put("tasaFijaFinal", tasaFijaFinal);
				result.put("formuladaFinal", formuladaFinal);
			}
			
			if(tipoConsulta.equals("eliminar")){
				grupoPorProductoTransaction.eliminar(grupoPorProducto);
				//Eliminar Tasas Correspondientes al Grupo Por Producto
				String id = grupoPorProducto.getId();
				TasaProductoDAO tasaProductoDAO = new TasaProductoDAO();
				tasaProductoDAO.eliminarCascada(id);
				result.put("id", id);
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

