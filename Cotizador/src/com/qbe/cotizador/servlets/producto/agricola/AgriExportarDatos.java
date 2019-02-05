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

import org.eclipse.persistence.internal.expressions.OuterJoinExpressionHolder;

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
import com.qbe.cotizador.dao.producto.agricola.AgriParametroXPuntoVentaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriReglaDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriTipoCultivoXTipoCalculoDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriVariedadDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.model.AgriCiclo;
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
import com.sun.faces.taglib.html_basic.OutputTextTag;

/**
 * Servlet implementation class AgriExportarDatos
 */
@WebServlet("/AgriExportarDatos")
public class AgriExportarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgriExportarDatos() {
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
		//JSONObject retorno=new JSONObject();
		String result=generarData();

		response.setContentType("text/plain; charset=ISO-8859-1"); 
		response.getWriter().write(result);
	}

	private String generarData(){
		TransporteData nuevaData=new TransporteData();


		//Exportar los datos de los tipos de cultivo
		AgriTipoCultivoDAO agriTipoCultivoDAO = new AgriTipoCultivoDAO();
		List<AgriTipoCultivo> results = agriTipoCultivoDAO.BuscarTodos();
		List<TipoCultivo> listadoTipoCultivo=new ArrayList<TipoCultivo>(); 
		for (AgriTipoCultivo tipoCultivo: results)
		{
			TipoCultivo nuevoTipo=new TipoCultivo();
			nuevoTipo.setTipoCultivoId(tipoCultivo.getTipoCultivoId());
			nuevoTipo.setNombre(tipoCultivo.getNombre());
			nuevoTipo.setTipo(tipoCultivo.getTipo());
			nuevoTipo.setVigenciaDias(tipoCultivo.getVigenciaDias());
			listadoTipoCultivo.add(nuevoTipo);
		}


		//Exportar los datos de las reglas
		AgriReglaDAO agriReglaDAO = new AgriReglaDAO();
		List<AgriRegla> listadoAgriReglas = agriReglaDAO.BuscarTodos();
		List<Regla> listadoReglas=new ArrayList<Regla>();
		for (AgriRegla reglaActual: listadoAgriReglas)
		{
			Regla nuevaRegla=new Regla();
			nuevaRegla.setReglaId(reglaActual.getReglaId());
			nuevaRegla.setTipoCultivoId(reglaActual.getTipoCultivoId());	
			nuevaRegla.setTipoCalculoId(reglaActual.getTipoCalculoId());
			nuevaRegla.setCicloId(reglaActual.getClicloId());
			nuevaRegla.setProvinciaId(reglaActual.getProvinciaId());
			nuevaRegla.setCantonId(reglaActual.getCantonId());
			nuevaRegla.setCostoProduccion(reglaActual.getCostoProduccion());
			nuevaRegla.setTasa(reglaActual.getTasa());
			nuevaRegla.setCostoMantenimiento(reglaActual.getCostoMantenimiento());
			nuevaRegla.setAceptabilidadDesde(reglaActual.getAceptabilidadDesde());
			nuevaRegla.setAceptabilidadHasta(reglaActual.getAceptabilidadHasta());
			nuevaRegla.setObservaciones(reglaActual.getObservaciones());
			listadoReglas.add(nuevaRegla);
		}

		//Exportar los datos de los ciclos
		AgriCicloDAO agriCicloDAO = new AgriCicloDAO();
		List<AgriCiclo> listadoCiclos = agriCicloDAO.BuscarTodos();
		List<Ciclo> listaCiclos=new ArrayList<Ciclo>();
		for (AgriCiclo Ciclo: listadoCiclos)
		{
			Ciclo nuevoCiclo=new Ciclo();
			nuevoCiclo.setCicloId(Ciclo.getClicloId());
			nuevoCiclo.setNombre(Ciclo.getNombre());
			nuevoCiclo.setFechaInicio(Ciclo.getFechaInicio());
			nuevoCiclo.setFechaFin(Ciclo.getFechaFin());
			listaCiclos.add(nuevoCiclo);
		}

		//Exportar datos de las provincias
		ProvinciaDAO provinciaDAO=new ProvinciaDAO();
		List<Provincia> listadoProvincias = provinciaDAO.buscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.Provincia> listaProvincias=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.Provincia>();
		for(Provincia provinciaActual:listadoProvincias){
			com.qbe.cotizador.servlets.producto.agricola.Provincia nuevaProvincia=new com.qbe.cotizador.servlets.producto.agricola.Provincia();
			nuevaProvincia.setProvinciaId(provinciaActual.getId());
			nuevaProvincia.setNombre(provinciaActual.getNombre());
			listaProvincias.add(nuevaProvincia);
		}

		//Exportar datos de los cantones
		CantonDAO cantonDAO=new CantonDAO();
		List<Canton> listadoCantones = cantonDAO.buscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.Canton> listaCantones=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.Canton>();
		for(Canton cantonActual:listadoCantones){
			com.qbe.cotizador.servlets.producto.agricola.Canton nuevoCanton=new com.qbe.cotizador.servlets.producto.agricola.Canton();
			nuevoCanton.setCantonId(cantonActual.getId());
			nuevoCanton.setProvinciaId(cantonActual.getProvincia().getId());
			nuevoCanton.setNombre(cantonActual.getNombre());
			listaCantones.add(nuevoCanton);
		}
		
		//Exportar datos de las variedades
		AgriVariedadDAO variedadDAO=new AgriVariedadDAO();
		List<AgriVariedad> listadoVariedades = variedadDAO.BuscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.Variedad> listaVariedades=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.Variedad>();
		for(AgriVariedad variedadActual:listadoVariedades){
			com.qbe.cotizador.servlets.producto.agricola.Variedad nuevaVariedad = new com.qbe.cotizador.servlets.producto.agricola.Variedad();
			nuevaVariedad.setVariedadId(variedadActual.getVariedadId());
			nuevaVariedad.setTipoCultivoId(variedadActual.getTipoCultivoId());
			nuevaVariedad.setNombre(variedadActual.getNombre());
			listaVariedades.add(nuevaVariedad);
		}

		//Exportar datos de los parametros
		AgriParametroXPuntoVentaDAO parametroPuntoVentaDAO=new AgriParametroXPuntoVentaDAO();
		List<AgriParametroXPuntoVenta> listadoParametros = parametroPuntoVentaDAO.buscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.ParametrosXPuntoVenta> listaParametros=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.ParametrosXPuntoVenta>();
		for(AgriParametroXPuntoVenta parametroActual:listadoParametros){
			com.qbe.cotizador.servlets.producto.agricola.ParametrosXPuntoVenta nuevoParametro=new com.qbe.cotizador.servlets.producto.agricola.ParametrosXPuntoVenta();
			nuevoParametro.setParametroPuntoVentaID(parametroActual.getParametroPuntoVentaId());
			nuevoParametro.setTipoCalculoId(parametroActual.getTipoCalculoId());
			nuevoParametro.setPuntoVentaID(parametroActual.getPuntoVentaId());
			listaParametros.add(nuevoParametro);
		}

		//Exportar datos de los cantones
		ParroquiaDAO parroquiaDAO=new ParroquiaDAO();
		List<Parroquia> listadoParroquias = parroquiaDAO.buscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.Parroquia> listaParroquias=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.Parroquia>();
		for(Parroquia parroquiaActual:listadoParroquias){
			com.qbe.cotizador.servlets.producto.agricola.Parroquia nuevoParroquia=new com.qbe.cotizador.servlets.producto.agricola.Parroquia();
			nuevoParroquia.setCantonId(parroquiaActual.getCanton().getId());
			nuevoParroquia.setParroquiaId(parroquiaActual.getId());
			nuevoParroquia.setNombre(parroquiaActual.getNombre());
			listaParroquias.add(nuevoParroquia);
		}

		//Exporto los datos de los puntos de venta
		GrupoPorProductoDAO grupoPorProducto=new GrupoPorProductoDAO();
		ProductoXPuntoVentaDAO productoXPuntoVentaDAO=new ProductoXPuntoVentaDAO();
		PuntoVentaDAO puntoVentaDAO=new PuntoVentaDAO();
		GrupoPorProducto grupoProducto = grupoPorProducto.buscarPorNombre("Agricola");
		List<com.qbe.cotizador.servlets.producto.agricola.PuntoVenta> listaPuntosVenta=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.PuntoVenta>();
		if(grupoProducto!=null){
			List<ProductoXPuntoVenta> listadoProductoXPuntoVenta= productoXPuntoVentaDAO.buscarPorProductoPuntoVentaListado(grupoProducto);
			for(ProductoXPuntoVenta productoPuntoVenta:listadoProductoXPuntoVenta){
				PuntoVenta puntoVenta=puntoVentaDAO.buscarPorId(productoPuntoVenta.getPuntoVenta().getId());
				com.qbe.cotizador.servlets.producto.agricola.PuntoVenta nuevoPuntoVenta=new com.qbe.cotizador.servlets.producto.agricola.PuntoVenta();
				nuevoPuntoVenta.setNombre(puntoVenta.getNombre());
				nuevoPuntoVenta.setPuntoVentaID(puntoVenta.getId());
				listaPuntosVenta.add(nuevoPuntoVenta);
			}
		}


		//Exportar datos de los tipos de identificacion
		TipoIdentificacionDAO tipoIdentificacionDAO=new TipoIdentificacionDAO();
		List<TipoIdentificacion> listadoTipos=tipoIdentificacionDAO.buscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.TipoIdentificacion> listaTipos=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.TipoIdentificacion>();
		for(TipoIdentificacion tipoIdentificacionActual:listadoTipos){
			com.qbe.cotizador.servlets.producto.agricola.TipoIdentificacion nuevoTipoIdentificacion=new com.qbe.cotizador.servlets.producto.agricola.TipoIdentificacion();
			nuevoTipoIdentificacion.setId(tipoIdentificacionActual.getId());
			nuevoTipoIdentificacion.setNombre(tipoIdentificacionActual.getNombre());
			listaTipos.add(nuevoTipoIdentificacion);
		}

		//Exportar datos de tipos de cultivo por tipo de calcul
		AgriTipoCultivoXTipoCalculoDAO tipoCultivoTipoCalculoDAO=new AgriTipoCultivoXTipoCalculoDAO();
		List<AgriTipoCultivoXTipoCalculo> listadoTCXTC = tipoCultivoTipoCalculoDAO.BuscarTodos();
		List<com.qbe.cotizador.servlets.producto.agricola.TipoCultivoXTipoCalculo> listaTiposConfigurados=new ArrayList<com.qbe.cotizador.servlets.producto.agricola.TipoCultivoXTipoCalculo>();
		for(AgriTipoCultivoXTipoCalculo tipoActual:listadoTCXTC){
			com.qbe.cotizador.servlets.producto.agricola.TipoCultivoXTipoCalculo nuevoTipo=new com.qbe.cotizador.servlets.producto.agricola.TipoCultivoXTipoCalculo();
			nuevoTipo.setTipoCalculoId(tipoActual.getTipoCalculoId());
			nuevoTipo.setTipoCultivoId(tipoActual.getTipoCalculoId());
			nuevoTipo.setCoberturaTexto(tipoActual.getCoberturaText());
			nuevoTipo.setDeducibleTexto(tipoActual.getDeducibleTexto());
			nuevoTipo.setMetodoIndemnizacionTexto(tipoActual.getMetodoIndemnizacionTexto());
			listaTiposConfigurados.add(nuevoTipo);
		}
		
		ParametroGeneral nuevoParametroGeneral=new ParametroGeneral();
		nuevoParametroGeneral.setDiasValidacionCultivo(15);
		String html="";
		try{
			String rutaPlantilla = this.getServletContext().getRealPath("") + "/static/plantillas/solicitudCotizacionAgricola.htm";
			html = Files.toString(new File(rutaPlantilla), Charsets.UTF_8);
		}
		catch(IOException ex){
		}
		nuevoParametroGeneral.setTextoPlantilla(html);
		
		nuevaData.setParametrosGenerales(nuevoParametroGeneral);
		nuevaData.setCantones(listaCantones);
		nuevaData.setCiclos(listaCiclos);
		nuevaData.setParroquias(listaParroquias);
		nuevaData.setProvincias(listaProvincias);
		nuevaData.setReglas(listadoReglas);
		nuevaData.setTiposCultivos(listadoTipoCultivo);
		nuevaData.setTiposIdentificacion(listaTipos);
		nuevaData.setPuntosVentaAgricola(listaPuntosVenta);
		nuevaData.setParametrosPuntoVenta(listaParametros);
		nuevaData.setVariedades(listaVariedades);
		nuevaData.setTiposCultivoXTiposCalculos(listaTiposConfigurados);

		try{
			Gson g=new Gson();
			String data=g.toJson(nuevaData);
			String dataPreparada=com.qbe.cotizador.util.AES_Helper.padString(data);
			String dataSerializada= com.qbe.cotizador.util.AES_Helper.encrypt(dataPreparada);
			return dataSerializada;
		}
		catch(Exception ex){
			return "";	
		}

	}

}
