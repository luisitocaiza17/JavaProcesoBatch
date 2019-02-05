package com.qbe.cotizador.servlets.mantenimiento;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.qbe.cotizador.dao.cotizacion.AutorizacionSriDAO;
import com.qbe.cotizador.dao.cotizacion.CoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.CoberturasConjuntoDAO;
import com.qbe.cotizador.dao.cotizacion.ConjuntoCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.DeducibleDAO;
import com.qbe.cotizador.dao.cotizacion.GrupoCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.PlanDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoDAO;
import com.qbe.cotizador.dao.cotizacion.TipoCoberturaDAO;
import com.qbe.cotizador.dao.cotizacion.TipoTasaDAO;
import com.qbe.cotizador.dao.entidad.ConfiguracionProductoDAO;
import com.qbe.cotizador.dao.entidad.DetalleProductoDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.EntidadJrDAO;
import com.qbe.cotizador.dao.entidad.FirmasDigitalesDAO;
import com.qbe.cotizador.dao.entidad.RamoDAO;
import com.qbe.cotizador.dao.entidad.SucursalDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ClaseVehiculoDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ColorDAO;
import com.qbe.cotizador.dao.producto.vehiculo.MarcaDAO;
import com.qbe.cotizador.dao.producto.vehiculo.ModeloDAO;
import com.qbe.cotizador.dao.producto.vehiculo.TipoExtraDAO;
import com.qbe.cotizador.model.AutorizacionSri;
import com.qbe.cotizador.model.ClaseVehiculo;
import com.qbe.cotizador.model.Cobertura;
import com.qbe.cotizador.model.CoberturasConjunto;
import com.qbe.cotizador.model.Color;
import com.qbe.cotizador.model.ConfiguracionProducto;
import com.qbe.cotizador.model.ConjuntoCobertura;
import com.qbe.cotizador.model.Deducible;
import com.qbe.cotizador.model.DetalleProducto;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.EntidadJr;
import com.qbe.cotizador.model.FirmasDigitales;
import com.qbe.cotizador.model.GrupoCobertura;
import com.qbe.cotizador.model.Marca;
import com.qbe.cotizador.model.Modelo;
import com.qbe.cotizador.model.Plan;
import com.qbe.cotizador.model.Producto;
import com.qbe.cotizador.model.Ramo;
import com.qbe.cotizador.model.Sucursal;
import com.qbe.cotizador.model.TipoCobertura;
import com.qbe.cotizador.model.TipoExtra;
import com.qbe.cotizador.model.TipoTasa;
import com.qbe.cotizador.servicios.QBE.cliente.WebServiceCotizadorImplService;
import com.qbe.cotizador.transaction.cotizacion.AutorizacionSriTransaction;
import com.qbe.cotizador.transaction.cotizacion.CoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.CoberturasConjuntoTransaction;
import com.qbe.cotizador.transaction.cotizacion.ConfiguracionProductoTransaction;
import com.qbe.cotizador.transaction.cotizacion.ConjuntoCoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.DeducibleTransaction;
import com.qbe.cotizador.transaction.producto.DetalleProductoTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.ClaseVehiculoTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.ColorTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.MarcaTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.ModeloTransaction;
import com.qbe.cotizador.transaction.producto.vehiculo.TipoExtraTransaction;
import com.qbe.cotizador.transaction.cotizacion.GrupoCoberturaTransaction;
import com.qbe.cotizador.transaction.cotizacion.PlanTransaction;
import com.qbe.cotizador.transaction.cotizacion.ProductoTransaction;
import com.qbe.cotizador.transaction.entidad.EntidadJrTransaction;
import com.qbe.cotizador.transaction.entidad.FirmasDigitalesTransaction;

/**
 * Servlet implementation class MantenimientoController
 */
@WebServlet("/MantenimientoController")
public class MantenimientoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MantenimientoController() {
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
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3*60*60);//(3 horas por 60 minutos por 60 segundos)
		
		PlanTransaction planTransaction = new PlanTransaction();
		ProductoTransaction productoTransaction = new ProductoTransaction();
		GrupoCoberturaTransaction grupoCoberturaTransaction = new GrupoCoberturaTransaction();
		ConfiguracionProductoTransaction configuracionProductoTransaction= new ConfiguracionProductoTransaction();
		ConjuntoCoberturaTransaction conjuntoCoberturaTransaction = new ConjuntoCoberturaTransaction();
		CoberturasConjuntoTransaction coberturasConjuntoTransaction = new CoberturasConjuntoTransaction();
		CoberturaTransaction coberturaTransaction = new CoberturaTransaction();
		DetalleProductoTransaction detalleProductoTransaction = new DetalleProductoTransaction();
		DeducibleTransaction deducibleTransaction = new DeducibleTransaction();
		EntidadJrTransaction entidadJrTransaction = new EntidadJrTransaction();
		FirmasDigitalesTransaction firmasDigitalesTransaction = new FirmasDigitalesTransaction();
		AutorizacionSriTransaction autorizacionSriTransaction = new AutorizacionSriTransaction();
		MarcaTransaction marcaTransaction = new MarcaTransaction(); 
		ClaseVehiculoTransaction claseVehiculoTransaction = new ClaseVehiculoTransaction(); 
		ModeloTransaction modeloTransaction = new ModeloTransaction();
		ColorTransaction colorTransaction = new ColorTransaction();
		TipoExtraTransaction tipoExtraTransaction = new TipoExtraTransaction();
		
		try{
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
			
			Cobertura cobertura = new Cobertura();
			Plan plan = new Plan();
			Producto producto = new Producto();
			GrupoCobertura grupoCobertura = new GrupoCobertura();
			Ramo ramo = new Ramo();
			
			
			CoberturaDAO coberturaDAO = new CoberturaDAO();
			PlanDAO planDAO = new PlanDAO();
			ProductoDAO productoDAO = new ProductoDAO();
			GrupoCoberturaDAO grupoCoberturaDAO = new GrupoCoberturaDAO();
			RamoDAO ramoDAO = new RamoDAO();
			
			// Metodo para actualizar Tablas de vehiculos desde ensurance
			if(tipoConsulta.equals("actualizarCatalogosVH")){
				System.out.println("HORA INICIO: "+new Date());
				
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				// Actualizacion Plan VH								
				String listadoPlanes = webService.getWebServiceCotizadorImplPort().obtenerPlanes();
				String [] listadoPlanesArr=listadoPlanes.split("\\><");
				String resultadoActualizacion = "";
				int planesNuevos =0;
				int planesActualizados =0;
				for(String planFila:listadoPlanesArr){
					String [] planValores = planFila.split("\\|");
					String planEstado = "";
					plan = planDAO.buscarPorId(planValores[0]);
					
					if(plan == null || plan.getId()==null){
						plan = new Plan();
						planEstado = "NUEVO";
					}
					plan.setId(planValores[0].toString());
					plan.setNombre(planValores[1].toString());
					plan.setDescripcion(planValores[2].toString());
					plan.setSigla(planValores[3].toString());
					if(planEstado.equalsIgnoreCase("NUEVO")){												
						plan = planTransaction.crear(plan);									
						planesNuevos++;
					}	
					else{												
						plan = planTransaction.editar(plan);
						planesActualizados++;						
					}	
				}
				resultadoActualizacion = "HORA INICIO: "+new Date()+" ---- ";
				resultadoActualizacion +="Planes: "+planesNuevos+" Nuevos "+planesActualizados+" Actualizados";

				// Actualizacion GrupoCobertura VH							
				String listadoGrupoCoberturas = webService.getWebServiceCotizadorImplPort().obtenerGruposCoberturaVH();
				String [] listadoGrupoCoberturasArr=listadoGrupoCoberturas.split("\\><");
				int grupoCoberturaNuevos=0;
				int grupoCoberturaActualizados=0;
				for(String grupoCoberturaFila:listadoGrupoCoberturasArr){
					String [] grupoCoberturaValores = grupoCoberturaFila.split("\\|");
					String grupoCoberturaEstado = "";
					grupoCobertura = grupoCoberturaDAO.buscarPorId(grupoCoberturaValores[0]);
					
					if(grupoCobertura == null || grupoCobertura.getId()==null){
						grupoCobertura = new GrupoCobertura();
						grupoCoberturaEstado = "NUEVO";
					}
					grupoCobertura.setId(grupoCoberturaValores[0].toString());					
					grupoCobertura.setNombre(grupoCoberturaValores[1].toString());
					
					ramo = ramoDAO.buscarPorId(grupoCoberturaValores[2].toString());
					grupoCobertura.setRamo(ramo);
					
					grupoCobertura.setNombreNemotecnico(grupoCoberturaValores[3].toString());
					grupoCobertura.setCodcontable(grupoCoberturaValores[4].toString());
					grupoCobertura.setOrden(grupoCoberturaValores[5].toString());
					grupoCobertura.setSumaaltotal(Double.parseDouble(grupoCoberturaValores[6].toString()));
					grupoCobertura.setCuentapolizatotal(grupoCoberturaValores[7].toString());
														
					if(grupoCoberturaEstado.equalsIgnoreCase("NUEVO")){						
						grupoCobertura = grupoCoberturaTransaction.crear(grupoCobertura);
						grupoCoberturaNuevos++;
					}	
					else{					
						grupoCobertura = grupoCoberturaTransaction.editar(grupoCobertura);	
						grupoCoberturaActualizados++;
					}	
				}
				resultadoActualizacion +="GrupoCobertura: "+planesNuevos+" Nuevos "+planesActualizados+" Actualizados";				
						
				// Actualizacion Productos VH							
				String listadoProducto = webService.getWebServiceCotizadorImplPort().obteneProductosVH();
				String [] listadoProductoArr =listadoProducto.split("\\><");
				int productosNuevos=0;
				int productosActualizados=0;				
				for(String productoFila:listadoProductoArr){
					String [] productoValores = productoFila.split("\\|");
					String productoEstado = "";
					producto = productoDAO.buscarPorId(productoValores[0]);
					
					if(producto == null){
						producto = new Producto();
						productoEstado = "NUEVO";
					}
					producto.setId(productoValores[0].toString());
					producto.setNombre(productoValores[1].toString());
					producto.setRamoId(new BigInteger(productoValores[2].toString()));
					producto.setDefecto(productoValores[3].toString());
					producto.setVigencia(Integer.parseInt(productoValores[4].toString()));
					producto.setDinamico(productoValores[5].toString());
					
					if(productoEstado.equalsIgnoreCase("NUEVO")){											
						producto = productoTransaction.crear(producto);	
						productosNuevos++;
					}	
					else{						
						producto = productoTransaction.editar(producto);	
						productosActualizados++;	
					}	
				}				
				resultadoActualizacion +="Productos: "+productosNuevos+" Nuevos "+productosActualizados+" Actualizados";
				
				// Actualizacion ConfiguracionProducto							
				String listadoConfiguracionProducto = webService.getWebServiceCotizadorImplPort().obtenerConfiguracionProducto();
				String [] listadoConfiguracionProductoArr=listadoConfiguracionProducto.split("\\><");
				int configuracionProductosNuevos=0;
				int configuracionProductosActualizados=0;				
				ConfiguracionProducto configuracionProducto = new ConfiguracionProducto();
				ConfiguracionProductoDAO configuracionProductoDAO = new ConfiguracionProductoDAO();
				
				for(String configuracionProductoFila:listadoConfiguracionProductoArr){
					String [] configuracionProductoValores = configuracionProductoFila.split("\\|");
					String configuracionProductoEstado="";
					
					productoDAO = new ProductoDAO();
					producto=productoDAO.buscarPorId(configuracionProductoValores[1].toString());
					
					if (producto != null && producto.getId() != null) {
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 						
						configuracionProducto=configuracionProductoDAO.buscarPorId(configuracionProductoValores[0].toString());
						
						if (configuracionProducto == null || configuracionProducto.getId() == null) {
							configuracionProducto = new ConfiguracionProducto();
							configuracionProductoEstado = "NUEVO";
						}
												
						configuracionProducto.setId(configuracionProductoValores[0].toString());
						
						Date fecha=null;
						Date vigenciaDesde=null;
						Date vigenciaHasta=null;
						
						if(!configuracionProductoValores[2].toString().trim().equals(""))
							fecha=sdf.parse(configuracionProductoValores[2].toString());


						if(!configuracionProductoValores[4].toString().trim().equals(""))
							vigenciaDesde=sdf.parse(configuracionProductoValores[4].toString());
						
						if(!configuracionProductoValores[5].toString().trim().equals(""))
							vigenciaHasta=sdf.parse(configuracionProductoValores[5].toString());
						
						
						configuracionProducto.setProducto(producto);
						if(fecha!=null)
						configuracionProducto.setFecha(fecha);
						if(vigenciaDesde!=null)
						configuracionProducto.setVigenciadesde(vigenciaDesde);
						if(vigenciaHasta!=null)
						configuracionProducto.setVigenciahasta(vigenciaHasta);
						configuracionProducto.setVigente(configuracionProductoValores[3].toString());
						configuracionProducto.setModificable(configuracionProductoValores[6].toString());
						
						if(configuracionProductoEstado.equalsIgnoreCase("NUEVO")){												
							configuracionProducto = configuracionProductoTransaction.crear(configuracionProducto);
							configuracionProductosNuevos++;
						}	
						else{							
							configuracionProducto = configuracionProductoTransaction.editar(configuracionProducto);	
							configuracionProductosActualizados++;
						}							
					}
				}							
				resultadoActualizacion +="Configuracion Productos: "+configuracionProductosNuevos+" Nuevos "+configuracionProductosActualizados+" Actualizados";
				
				// Actualizacion Conjunto Coberturas					
				String listadoConjuntoCoberturas = webService.getWebServiceCotizadorImplPort().obteneConjuntoCoberturasVH();
				String [] listadoConjuntoCoberturasArr =listadoConjuntoCoberturas.split("\\><");				
				int conjuntoCoberturasNuevos=0;
				int conjuntoCoberturasActualizados=0;				
				for(String conjuntoCoberturasFila:listadoConjuntoCoberturasArr){
					String [] conjuntoCoberturasValores = conjuntoCoberturasFila.split("\\|");
					String conjuntoCoberturaEstado = "";
					ConjuntoCobertura conjuntoCobertura = new ConjuntoCobertura();
					ConjuntoCoberturaDAO conjuntoCoberturaDAO = new ConjuntoCoberturaDAO();
					conjuntoCobertura = conjuntoCoberturaDAO.buscarPorId(conjuntoCoberturasValores[0]);
					
					if(conjuntoCobertura == null||conjuntoCobertura.getId()==null){
						conjuntoCobertura = new ConjuntoCobertura();
						conjuntoCoberturaEstado = "NUEVO";
					}
					
					ConfiguracionProductoDAO cfDAO=new ConfiguracionProductoDAO();
					configuracionProducto=cfDAO.buscarPorId(conjuntoCoberturasValores[2]);
					
					if(configuracionProducto!=null&&configuracionProducto.getId()!=null){
										
					conjuntoCobertura.setConfiguracionProducto(configuracionProducto);
					conjuntoCobertura.setOrden(conjuntoCoberturasValores[3]);
					
					conjuntoCobertura.setId(conjuntoCoberturasValores[0]);
					conjuntoCobertura.setNombre(conjuntoCoberturasValores[1]);
										
					if(conjuntoCoberturaEstado.equalsIgnoreCase("NUEVO")){											
						conjuntoCobertura = conjuntoCoberturaTransaction.crear(conjuntoCobertura);	
						conjuntoCoberturasNuevos++;	
					}	
					else{						
						conjuntoCobertura = conjuntoCoberturaTransaction.editar(conjuntoCobertura);	
						conjuntoCoberturasActualizados++;	
					}	
					}					
				}
				
				resultadoActualizacion +="Conjunto Coberturas: "+conjuntoCoberturasNuevos+" Nuevos "+conjuntoCoberturasActualizados+" Actualizados";
				
				// Actualizacion CoberturasConjunto							
				String listadoCoberturaConjunto = webService.getWebServiceCotizadorImplPort().obtenerCoberturasConjunto();
				String [] listadoCoberturaConjuntoArr=listadoCoberturaConjunto.split("\\><");
				int coberturasConjuntoNuevos=0;
				int coberturasConjuntoActualizados=0;
				for(String coberturaConjuntoFila:listadoCoberturaConjuntoArr){
					String [] coberturaConjuntoValores = coberturaConjuntoFila.split("\\|");
					
					ConjuntoCoberturaDAO conjuntoCoberturaDAO = new ConjuntoCoberturaDAO();
					ConjuntoCobertura conjuntoCobertura=conjuntoCoberturaDAO.buscarPorId(coberturaConjuntoValores[1].toString());
					
					cobertura = coberturaDAO.buscarPorId(coberturaConjuntoValores[2].toString());
					
					CoberturasConjuntoDAO coberturasConjuntoDAO = new CoberturasConjuntoDAO();
					
					String coberturasConjuntoEstado="";
					
					if (conjuntoCobertura != null && conjuntoCobertura.getId() != null && cobertura!=null && cobertura.getId()!=null) {
						
						CoberturasConjunto coberturasConjunto=coberturasConjuntoDAO.buscarPorId(coberturaConjuntoValores[0].toString());
						
						if (coberturasConjunto == null || coberturasConjunto.getId() == null) {
							coberturasConjunto = new CoberturasConjunto();
							coberturasConjuntoEstado = "NUEVO";
						}
						
						coberturasConjunto.setConjuntoCobertura(conjuntoCobertura);
						coberturasConjunto.setCobertura(cobertura);
						coberturasConjunto.setId(coberturaConjuntoValores[0].toString());
						
						if(coberturasConjuntoEstado.equalsIgnoreCase("NUEVO")){												
							coberturasConjunto = coberturasConjuntoTransaction.crear(coberturasConjunto);
							coberturasConjuntoNuevos++;
						}	
						else{							
							coberturasConjunto = coberturasConjuntoTransaction.editar(coberturasConjunto);	
							coberturasConjuntoActualizados++;
						}							
					}
				} 
				
				resultadoActualizacion +="Coberturas Conjunto: "+coberturasConjuntoNuevos+" Nuevos "+coberturasConjuntoActualizados+" Actualizados";
				
				// Actualizacion Coberturas VH
				String listadoCoberturas = webService.getWebServiceCotizadorImplPort().obtenerCoberturaDetalleProducto();
				String [] listadoCoberturasDetalleArr=listadoCoberturas.split("\\><");
				DetalleProductoDAO detalleProductoDAO= new DetalleProductoDAO();				
				List<Cobertura> coberturasGrabar=new ArrayList<Cobertura>();
				List<DetalleProducto> detallesGrabar=new ArrayList<DetalleProducto>();
				
				for(String coberturaDetalleFila:listadoCoberturasDetalleArr){
					
					String [] coberturaValores = coberturaDetalleFila.split("\\|");
					cobertura = coberturaDAO.buscarPorId(coberturaValores[0]);
										
					if(cobertura == null||cobertura.getId()==null){
						cobertura = new Cobertura();
					}					
					cobertura.setId(coberturaValores[0].toString());
					cobertura.setNombre(coberturaValores[1].toString());						
					cobertura.setTexto(coberturaValores[2].getBytes("UTF-8"));
					
					TipoCoberturaDAO tipoCoberturaDAO = new TipoCoberturaDAO();	
					TipoCobertura tipoCobertura = tipoCoberturaDAO.buscarPorId(coberturaValores[3].toString());
					cobertura.setTipoCobertura(tipoCobertura);
					
					cobertura.setTipoCobertura(tipoCobertura);
					
					grupoCobertura = grupoCoberturaDAO.buscarPorId(coberturaValores[4].toString());
					cobertura.setGrupoCobertura(grupoCobertura);
					
					TipoTasaDAO ttDAO=new TipoTasaDAO();
					TipoTasa tipoTasa=ttDAO.buscarPorId(coberturaValores[11].toString());
					cobertura.setTipoTasa(tipoTasa);
					
					cobertura.setAfectaGrupo(coberturaValores[5].toString());
					cobertura.setAfectaValorAsegurado(coberturaValores[6].toString());
					cobertura.setSeccion(coberturaValores[7].toString());
					cobertura.setOrden(Integer.parseInt(coberturaValores[8].toString()));
					cobertura.setLimite(coberturaValores[9].toString());
					cobertura.setEsPredeterminada(coberturaValores[10].toString());						
					cobertura.setEsPrimaFija(coberturaValores[11].toString().equals("1")?"1":"0");
					cobertura.setEsTodoRiesgo(coberturaValores[12].toString());
					cobertura.setEsMasivo(coberturaValores[13].toString());
					cobertura.setEsPrincipal(coberturaValores[14].toString());
					cobertura.setRebajaValorAsegurado(coberturaValores[15].toString());
					cobertura.setGeneraEndosoRasa(coberturaValores[16].toString());
					cobertura.setEsIndemnizable(coberturaValores[17].toString());
					cobertura.setEsLimiteSuma(coberturaValores[18].toString());
					cobertura.setPrincipalCobertura(coberturaValores[19].toString());
					coberturasGrabar.add(cobertura);
					
					DetalleProducto detalleProducto=detalleProductoDAO.buscarPorId(coberturaValores[20].toString());
					
					detalleProducto.setAfectaPrima(coberturaValores[26].toString());
					detalleProducto.setAfectaValorAsegurado(coberturaValores[27].toString());
					
					CoberturasConjuntoDAO coberturasConjuntoDAO=new CoberturasConjuntoDAO(); 
					CoberturasConjunto coberturasConjunto=coberturasConjuntoDAO.buscarPorId(coberturaValores[22].toString());
					
					if(coberturasConjunto!=null&&coberturasConjunto.getId()!=null)
					detalleProducto.setCoberturasConjunto(coberturasConjunto);
					
					configuracionProductoDAO=new ConfiguracionProductoDAO(); 
					configuracionProducto=configuracionProductoDAO.buscarPorId(coberturaValores[36].toString());
					
					if(configuracionProducto!=null&&configuracionProducto.getId()!=null)
					detalleProducto.setConfiguracionProducto(configuracionProducto);
					
					detalleProducto.setDefecto(coberturaValores[31].toString());
					detalleProducto.setId(coberturaValores[20].toString());
					detalleProducto.setMonto(Double.parseDouble(coberturaValores[24].toString()));
					detalleProducto.setPeriodicidad(Integer.parseInt(coberturaValores[30].toString()));
					
					planDAO=new PlanDAO(); 
					plan=planDAO.buscarPorId(coberturaValores[21].toString());
					
					if(plan!=null&&plan.getId()!=null)
					detalleProducto.setPlan(plan);
					
					detalleProducto.setPorccomisionvendedor(Double.parseDouble(coberturaValores[34].toString()));
					detalleProducto.setPorcentajeComision(Double.parseDouble(coberturaValores[32].toString()));
					detalleProducto.setPorcotros(Double.parseDouble(coberturaValores[37].toString()));
					detalleProducto.setPorcutilidad(Double.parseDouble(coberturaValores[35].toString()));
					detalleProducto.setPrima(Double.parseDouble(coberturaValores[25].toString()));
					detalleProducto.setPrimaBasica(Double.parseDouble(coberturaValores[33].toString()));
					detalleProducto.setTasa(Double.parseDouble(coberturaValores[23].toString()));
					detalleProducto.setTexto(coberturaValores[28].toString().getBytes("UTF-8") );
					detalleProducto.setValorPeriodo(Double.parseDouble(coberturaValores[29].toString()));
					
					detallesGrabar.add(detalleProducto);

				}
				
				int detallesBorrados=detalleProductoDAO.eliminarTodos();
				int coberturasCreados=0;
				int coberturasActualizados=0;
				int detalleProductoCreados=0;
				int detalleProductoActualizados=0;
				int cont=0;
				for(Cobertura coberturaGrabar:coberturasGrabar){
					if(coberturaGrabar.getId()==null){						
						coberturaGrabar = coberturaTransaction.crear(coberturaGrabar);	
						coberturasCreados++;
					}	
					else{						
						coberturaGrabar = coberturaTransaction.editar(coberturaGrabar);		
						coberturasActualizados++;
					}
					
					DetalleProducto detalleGrabar=detallesGrabar.get(cont);
					
					if(detalleGrabar.getId()==null){						
						detalleGrabar = detalleProductoTransaction.crear(detalleGrabar);	
						detalleProductoCreados++;
					}	
					else{						
						detalleGrabar = detalleProductoTransaction.editar(detalleGrabar);		
						detalleProductoActualizados++;
					}
					cont++;
				}
				
				resultadoActualizacion +="Coberturas: "+coberturasCreados+" Nuevos "+coberturasActualizados+" Actualizados";
				resultadoActualizacion +="Detalle Productos: "+detalleProductoCreados+" Nuevos "+detalleProductoActualizados+" Actualizados";
				
				coberturasGrabar=null;
				detallesGrabar=null;
				
				// Actualizacion deducibles						
				String listadoDeducibles = webService.getWebServiceCotizadorImplPort().obtenerDeducibles();
				String [] listadoDeduciblesArr=listadoDeducibles.split("\\><");
				int deduciblesCreados=0;
				int deduciblesActualizados=0;
				DeducibleDAO deducibleDAO=new DeducibleDAO();
				List<Deducible> deduciblesGrabar=new ArrayList<Deducible>();
				
				for(String deducibleFila:listadoDeduciblesArr){
					String [] deducibleValores = deducibleFila.split("\\|");
					
					Deducible deducible=deducibleDAO.buscarPorProductoDeducible(deducibleValores[0],deducibleValores[1]);
					
					if(deducible==null||deducible.getId()==null){
						deducible=deducibleDAO.buscarPorCoberturaPlanDeducible(deducibleValores[0],deducibleValores[5],deducibleValores[6]);
					}
					
					if (deducible == null || deducible.getId() == null) {
						deducible = new Deducible();
					}
					
					if(!deducibleValores[6].equals("0"))
						deducible.setPlanId(deducibleValores[6]);
					if(!deducibleValores[5].equals("0"))
						deducible.setCoberturaId(deducibleValores[5]);
					deducible.setTipoDeducibleId(BigInteger.valueOf(Long.parseLong(deducibleValores[3])));
					deducible.setTexto(deducibleValores[2]);
					deducible.setProductoId(deducibleValores[1]);
					deducible.setValor(Double.parseDouble(deducibleValores[4]));
					deducible.setDeducibleId(deducibleValores[0]);
					deduciblesGrabar.add(deducible);	
					
					}												
				
				int deduciblesBorrados=deducibleDAO.eliminarTodos();
				
				
				for(Deducible deducibleGrabar:deduciblesGrabar){
					if(deducibleGrabar.getId()==null){
						deducibleGrabar = deducibleTransaction.crear(deducibleGrabar);						
						deduciblesCreados++;
					}	
					else{
						deducibleGrabar = deducibleTransaction.editar(deducibleGrabar);							
						deduciblesActualizados++;
					}
				}				
				resultadoActualizacion +="Deducibles: "+deduciblesCreados+" Nuevos "+deduciblesActualizados+" Actualizados";												
				resultadoActualizacion += " ---- HORA FIN: "+new Date();				
				result.put("success", Boolean.TRUE);
				result.put("resultadoActualizacion",resultadoActualizacion);				
			}
			
			if(tipoConsulta.equals("actualizarEntidadJr")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				// Actualizacion EntidadesJr							
				String listadoEntidadesJr = webService.getWebServiceCotizadorImplPort().obtenerEntidadesJr();
				String [] listadoEntidadesJrArr=listadoEntidadesJr.split("\\><");
				EntidadJrDAO entidadJrDAO = new EntidadJrDAO();
				System.out.println("ELIMINADOS "+entidadJrDAO.eliminarTodos()+" REGISTROS DE ENTIDADJR");
				
				for(String entidadesJrFila:listadoEntidadesJrArr){
					String [] entidadesJrValores = entidadesJrFila.split("\\|");
					
					EntidadJr entidadJr=new EntidadJr();
											
					entidadJr.setId(Integer.parseInt(entidadesJrValores[0]));
					entidadJr.setNombre(entidadesJrValores[1]);
					entidadJr.setApellido(entidadesJrValores[2]);
					entidadJr.setNombreCompleto(entidadesJrValores[3]);
					entidadJr.setIdentificacion(entidadesJrValores[4]);
						
					System.out.print("EntidadJr: "+entidadJr.getId());						
					entidadJr = entidadJrTransaction.crear(entidadJr);
					System.out.println(" CREADO");																	
				} 								
			}
			

			if(tipoConsulta.equals("actualizarFirmasDigitales")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				String listadoFirmasDigitales = webService.getWebServiceCotizadorImplPort().obtenerFirmasDigitales();
				String [] listadoFirmasDigitalesArr=listadoFirmasDigitales.split("\\><");
				FirmasDigitalesDAO firmasDigitalesDAO = new FirmasDigitalesDAO();
				
				for(String firmasDigitalesFila:listadoFirmasDigitalesArr){
					String [] firmasDigitalesValores = firmasDigitalesFila.split("\\|");
					
					SucursalDAO sucursalDAO=new SucursalDAO();
					ramoDAO=new RamoDAO(); 
					EntidadDAO entidadDAO=new EntidadDAO();
					Entidad entidad=entidadDAO.buscarEntidadPorIdEnsurance(firmasDigitalesValores[1]);
					Sucursal sucursal=sucursalDAO.buscarPorIdEnsurance(firmasDigitalesValores[2]);
					ramo=ramoDAO.buscarPorId(firmasDigitalesValores[3]);
					FirmasDigitales firmasDigitales=new FirmasDigitales();
					String idEnsurance=firmasDigitalesValores[0];
					String firmasDigitalesEstado = "";
					
					if(sucursal!=null&&ramo!=null&&entidad!=null&&sucursal.getId()!=null&&ramo.getId()!=null&&entidad.getId()!=null){
					firmasDigitales=firmasDigitalesDAO.buscarPorRamoSucursalEntidad(ramo, sucursal, entidad);
					
					if(firmasDigitales == null||firmasDigitales.getId()==null){
						firmasDigitales = new FirmasDigitales();
						firmasDigitalesEstado = "NUEVO";
					}
									
						byte[] bytes = webService.getWebServiceCotizadorImplPort().obtenerFirmasDigitalesParametros(sucursal.getSucEnsurance(), entidad.getEntEnsurance(), ramo.getId());
						if (bytes.length > 1) {
							firmasDigitales.setFirma(bytes);
							firmasDigitales.setIdEnsurance(idEnsurance);
							firmasDigitales.setRamo(ramo);
							firmasDigitales.setEntidad(entidad);
							firmasDigitales.setSucursal(sucursal);
							if (firmasDigitalesEstado.equals("NUEVO")) {
								firmasDigitales = firmasDigitalesTransaction.crear(firmasDigitales);
								System.out.print("FirmasDigitales: "+ firmasDigitales.getId());
								System.out.println(" CREADO");
							}
							else {
								System.out.print("FirmasDigitales: "+ firmasDigitales.getId());
								firmasDigitales = firmasDigitalesTransaction.editar(firmasDigitales);
								System.out.println(" ACTUALIZADO");
							}
						}
					}
				} 
			}
			
			if(tipoConsulta.equals("actualizarAutorizacionSri")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				String listadoAutorizacionSRI = webService.getWebServiceCotizadorImplPort().obtenerAurotizacionesSRI();
				String [] listadoAutorizacionSRIArr=listadoAutorizacionSRI.split("\\><");
				AutorizacionSriDAO autorizacionSRIDAO = new AutorizacionSriDAO();
				
				for(String autorizacionSRIFila:listadoAutorizacionSRIArr){
					String [] autorizacionSRIValores = autorizacionSRIFila.split("\\|");
					AutorizacionSri aSRI=new AutorizacionSri(); 
					String autorizacionSriEstado ="";
					aSRI = autorizacionSRIDAO.buscarPorIdEnsurance(autorizacionSRIValores[4]);
					
					if(aSRI == null || aSRI.getId()==null){
						aSRI = new AutorizacionSri();
						autorizacionSriEstado = "NUEVO";
					}
					aSRI.setIdEnsurance(autorizacionSRIValores[4].toString());
					aSRI.setNumero(autorizacionSRIValores[0].toString());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					
					Date vigenciaDesde=sdf.parse(autorizacionSRIValores[1].toString().split(" ")[0]);
					Date vigenciaHasta=sdf.parse(autorizacionSRIValores[2].toString().split(" ")[0]);
					
					aSRI.setVigenciaDesde(vigenciaDesde);
					aSRI.setVigenciaHasta(vigenciaHasta);

					if(autorizacionSRIValores[3].toString().equals("0"))
						aSRI.setActivo(false);
					else
						aSRI.setActivo(true);
					
					if(autorizacionSriEstado.equalsIgnoreCase("NUEVO")){
						aSRI = autorizacionSriTransaction.crear(aSRI);		
						System.out.print("AutorizacionSRI: "+aSRI.getId());						
						System.out.println(" CREADO");	
					}	
					else{
						aSRI = autorizacionSriTransaction.editar(aSRI);
						System.out.print("AutorizacionSRI: "+aSRI.getId());
						System.out.println(" ACTUALIZADO");
					}	
				}
		}
			
			if(tipoConsulta.equals("actualizarMarcasModelos")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				// Actualizacion Marcas, Modelos, Clase Vehiculo 							
				String listadoMarcasModelos = webService.getWebServiceCotizadorImplPort().obtenerMarcasVehiculo();
				JSONObject marcasJSONObject = new JSONObject();
	    		JSONArray marcasJSONArray = new JSONArray();
	    		
				if(listadoMarcasModelos.length() > 0){
					String [] listadoMarcasModelosArr=listadoMarcasModelos.split("\\><");				
					if(listadoMarcasModelosArr.length>1){						
						for(String fila:listadoMarcasModelosArr){
							String [] marcasModelosValores = fila.split("\\|");					
							
							Marca marca = new Marca();
							MarcaDAO mDao= new MarcaDAO();
							ModeloDAO moDao = new ModeloDAO();
							Modelo modelo = new Modelo();
							marca = mDao.buscarPorCodigoEnsurance(marcasModelosValores[0]);
							if(marca.getId()== null){
								marca.setMarEnsurance(marcasModelosValores[0]);
								marca.setNombre(marcasModelosValores[1]);
								marca.setActivo(true);
								marcaTransaction.crear(marca);
								
								ClaseVehiculo claseVehiculo = new ClaseVehiculo();
								ClaseVehiculoDAO clDao = new ClaseVehiculoDAO();
								claseVehiculo.setNombre(marcasModelosValores[5]);
								claseVehiculo.setActivo(true);
								claseVehiculoTransaction.crear(claseVehiculo);
								
								modelo = new Modelo();
								moDao = new ModeloDAO();
								modelo.setMarca(marca);	
								modelo.setClaseVehiculo(claseVehiculo);
								modelo.setModEnsurance(marcasModelosValores[2]);
								modelo.setNombre(marcasModelosValores[3]);
								modeloTransaction.crear(modelo);
								
								marcasJSONObject.put("marca", marcasModelosValores[1]);
	    	    				marcasJSONObject.put("modelo", marcasModelosValores[5]);
	    	    				marcasJSONObject.put("clase", marcasModelosValores[3]);
							}
							else{
								modelo = new Modelo();
								modelo = moDao.buscarPorCodigoEnsurance(marcasModelosValores[2]);
								if(modelo.getId()== null){
									modelo.setMarca(marca);	
									//modelo.setClaseVehiculo(claseVehiculo);
									modelo.setModEnsurance(marcasModelosValores[2]);
									modelo.setNombre(marcasModelosValores[3]);
									modeloTransaction.crear(modelo);
									
									marcasJSONObject.put("marca", marcasModelosValores[1]);
		    	    				marcasJSONObject.put("modelo", marcasModelosValores[5]);
		    	    				marcasJSONObject.put("clase", marcasModelosValores[3]);
								}
							}
							marcasJSONArray.add(marcasJSONObject);
						}						
					}
				}		
				result.put("success", Boolean.TRUE);
	    		result.put("listadoMarcas", marcasJSONArray);
			}
			
			if(tipoConsulta.equals("actualizarColores")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				// Actualizacion Colores							
				String listadoColores = webService.getWebServiceCotizadorImplPort().obtenerColoresVehiculo();
				JSONObject coloresJSONObject = new JSONObject();
	    		JSONArray coloresJSONArray = new JSONArray();
	    		
				if(listadoColores.length() > 0){
					String [] listadoColoresArr=listadoColores.split("\\><");					    	    		
					if(listadoColoresArr.length>0){						
						for(String fila:listadoColoresArr){
							String [] coloresValores = fila.split("\\|");					
							
							Color color = new Color();
							ColorDAO coDao= new ColorDAO();
							color = coDao.buscarPorCodigoEnsurance(coloresValores[0]);
							if(color.getId()== null){
								color.setColEnsurance(coloresValores[0]);
								color.setNombre(coloresValores[1]);
								color.setActivo(false);
								colorTransaction.crear(color);
								System.out.println("CREADO");
								coloresJSONObject.put("color", coloresValores[1]);
							}		
							coloresJSONArray.add(coloresJSONObject);
						}												
					}	
				}
				result.put("success", Boolean.TRUE);
	    		result.put("listadoColor", coloresJSONArray);
			}
			
			if(tipoConsulta.equals("actualizarTipoExtras")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
				
				// Actualizacion TipoExtras 							
				String listadoTipoExtras = webService.getWebServiceCotizadorImplPort().obtenerTipoExtra();
				JSONObject extrasJSONObject = new JSONObject();
	    		JSONArray extrasJSONArray = new JSONArray();
	    		
				if(listadoTipoExtras.length() > 0){
					String [] listadoTipoExtrasArr=listadoTipoExtras.split("\\><");					    	    		
					if(listadoTipoExtrasArr.length>0){
						for(String fila:listadoTipoExtrasArr){
							String [] marcasTipoExtrasValores = fila.split("\\|");					
							
							TipoExtra tipoExtra = new TipoExtra();
							TipoExtraDAO tipoExtraDAO = new TipoExtraDAO();
							tipoExtra = tipoExtraDAO.buscarPorIdEnsurance(marcasTipoExtrasValores[0]);
							if(tipoExtra.getId()== null){
								tipoExtra.setTipExtEnsurance(marcasTipoExtrasValores[0]);
								tipoExtra.setNombre(marcasTipoExtrasValores[1]);
								tipoExtra.setActivo(true);
								tipoExtraTransaction.crear(tipoExtra);
								extrasJSONObject.put("extra", marcasTipoExtrasValores[1]);
								System.out.println("CREADO");
								extrasJSONArray.add(extrasJSONObject);
							}											
						}						
					}
				}							
				result.put("success", Boolean.TRUE);
	    		result.put("listadoExtras", extrasJSONArray);
			}
			
			if(tipoConsulta.equals("actualizarProductosGanadero")){
				WebServiceCotizadorImplService webService = new WebServiceCotizadorImplService();
							
				// Actualizacion Productos ganadero							
				String listadoProducto = webService.getWebServiceCotizadorImplPort().obtenerProductosGanadero();
				String [] listadoProductoArr =listadoProducto.split("\\><");
				for(String productoFila:listadoProductoArr){
					String [] productoValores = productoFila.split("\\|");
					String productoEstado = "";
					producto = productoDAO.buscarPorId(productoValores[0]);
					
					if(producto == null){
						producto = new Producto();
						productoEstado = "NUEVO";
					}
					producto.setId(productoValores[0].toString());
					producto.setNombre(productoValores[1].toString());
					producto.setRamoId(new BigInteger(productoValores[2].toString()));
					producto.setDefecto(productoValores[3].toString());
					producto.setVigencia(Integer.parseInt(productoValores[4].toString()));
					producto.setDinamico(productoValores[5].toString());
					
					if(productoEstado.equalsIgnoreCase("NUEVO")){
						System.out.print("Producto: "+producto.getId());						
						producto = productoTransaction.crear(producto);	
						System.out.println(" CREADO");	
					}	
					else{
						System.out.print("Producto: "+producto.getId());
						producto = productoTransaction.editar(producto);	
						System.out.println(" ACTUALIZADO");	
					}	
				}	
				
				
				
				
				
					
			}

			
			session.setMaxInactiveInterval(1*60*60);
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
		}catch(Exception e){
			session.setMaxInactiveInterval(1*60*60);
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=ISO-8859-1"); 
			result.write(response.getWriter());
			e.printStackTrace();
		}	
	}

}
