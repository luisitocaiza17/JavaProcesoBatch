package com.qbe.cotizador.servlets.producto.agricola;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.qbe.cotizador.dao.cotizacion.EstadoDAO;
import com.qbe.cotizador.dao.cotizacion.ProductoXPuntoVentaDAO;
import com.qbe.cotizador.dao.cotizacion.TipoObjetoDAO;
import com.qbe.cotizador.dao.cotizacion.VigenciaPolizaDAO;
import com.qbe.cotizador.dao.entidad.ActividadEconomicaDAO;
import com.qbe.cotizador.dao.entidad.ClienteDAO;
import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.TipoEntidadDAO;
import com.qbe.cotizador.dao.entidad.TipoIdentificacionDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.dao.producto.agricola.AgriObjetoCotizacionDAO;
import com.qbe.cotizador.dao.producto.vehiculocerrado.GrupoPorProductoDAO;
import com.qbe.cotizador.model.ActividadEconomica;
import com.qbe.cotizador.model.AgriObjetoCotizacion;
import com.qbe.cotizador.model.Cliente;
import com.qbe.cotizador.model.Cotizacion;
import com.qbe.cotizador.model.CotizacionDetalle;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.GrupoPorProducto;
import com.qbe.cotizador.model.ProductoXPuntoVenta;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.transaction.cotizacion.CotizacionDetalleTransaction;
import com.qbe.cotizador.transaction.cotizacion.CotizacionTransaction;
import com.qbe.cotizador.transaction.entidad.ClienteTransaction;
import com.qbe.cotizador.transaction.entidad.EntidadTransaction;
import com.qbe.cotizador.transaction.producto.agricola.AgriObjetoCotizacionTransaction;

public class AgriProcesarCotizacionExterna {
	public static String procesarCotizacion(CotizacionAgricola nuevaCotizacion, String tipoOrigen){

		EntidadDAO entidadDAO = new EntidadDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		TipoEntidadDAO tipoEntidadDAO = new TipoEntidadDAO();
		TipoIdentificacionDAO tipoDAO = new TipoIdentificacionDAO();
		PuntoVentaDAO pvDAO = new PuntoVentaDAO();
		VigenciaPolizaDAO vpDAO= new  VigenciaPolizaDAO();
		GrupoPorProductoDAO grupoPorProductoDAO = new GrupoPorProductoDAO();
		ProductoXPuntoVentaDAO productoPorPuntoVentaDAO=new ProductoXPuntoVentaDAO();
		TipoObjetoDAO tipoObjetoDAO = new TipoObjetoDAO();
		UsuarioDAO usuarioDAO=new UsuarioDAO();
		EstadoDAO estadoDAO=new EstadoDAO();

		Entidad entidad = new Entidad();
		Cliente cliente = new Cliente();
		Cotizacion cotizacion = new Cotizacion();

		CotizacionTransaction cotizacionTransaction = new CotizacionTransaction(); 
		CotizacionDetalleTransaction cotizacionDetalleTransaction = new CotizacionDetalleTransaction();
		EntidadTransaction entidadTransaction = new EntidadTransaction();
		ClienteTransaction clienteTransaction = new ClienteTransaction();
		AgriObjetoCotizacionTransaction agriObjetoCotizacionTransaction = new AgriObjetoCotizacionTransaction();

		entidad = entidadDAO.buscarEntidadPorIdentificacion(nuevaCotizacion.getNumeroIdentificacion());

		if(!nuevaCotizacion.getNumeroIdentificacion().equals(""))
			entidad.setIdentificacion(nuevaCotizacion.getNumeroIdentificacion());
		
		if(!nuevaCotizacion.getNombres().equals(""))
			entidad.setNombres(nuevaCotizacion.getNombres());
		
		if(!nuevaCotizacion.getApellidos().equals(""))
			entidad.setApellidos(nuevaCotizacion.getApellidos());
			
		entidad.setNombreCompleto(nuevaCotizacion.getApellidos()+ " " + nuevaCotizacion.getNombres());

		if(!nuevaCotizacion.getTelefono().equals(""))
			entidad.setTelefono(nuevaCotizacion.getTelefono());

		if(!nuevaCotizacion.getCelular().equals(""))
			entidad.setCelular(nuevaCotizacion.getCelular());

		if(!nuevaCotizacion.getEmail().equals(""))
			entidad.setMail(nuevaCotizacion.getEmail());

		if(!nuevaCotizacion.getTipoIdentificacion().equals(""))
			entidad.setTipoIdentificacion(tipoDAO.buscarPorId(nuevaCotizacion.getTipoIdentificacion().toString()));

		if(nuevaCotizacion.getTipoIdentificacion().toString().equalsIgnoreCase("1") || nuevaCotizacion.getTipoIdentificacion().toString().equalsIgnoreCase("1")){
			entidad.setTipoEntidad(tipoEntidadDAO.buscarPorId("1"));
		}else{
			entidad.setTipoEntidad(tipoEntidadDAO.buscarPorId("2"));
		}

		if(entidad.getId()==null)
			entidad=entidadTransaction.crear(entidad);
		else
			entidad=entidadTransaction.editar(entidad);

		cliente = clienteDAO.buscarPorEntidadId(entidad);

		if(cliente.getId() == null){
			ActividadEconomica actividad = new ActividadEconomica();
			ActividadEconomicaDAO actividadDAO = new ActividadEconomicaDAO();
			actividad = actividadDAO.buscarPorNombre("Ninguno");

			Cliente clienteNuevo = new Cliente();

			clienteNuevo.setEntidad(entidad);
			clienteNuevo.setActividadEconomica(actividad);
			clienteNuevo.setActivo(true);
			cliente=clienteTransaction.crear(clienteNuevo);
			//falta en codigo del ensurance
		}

		//if(!puntoVentaId.equals(""))
		PuntoVenta puntoVenta = pvDAO.buscarPorId(nuevaCotizacion.getPuntoVentaId()); //TODO: Quemado punto de venta

		cotizacion.setPuntoVenta(puntoVenta); 

		GrupoPorProducto grupoPorProducto = grupoPorProductoDAO.buscarPorId("159");

		ProductoXPuntoVenta pxpv=productoPorPuntoVentaDAO.buscarPorGrupoPuntoVenta(grupoPorProducto, puntoVenta);

		cotizacion.setProductoXPuntoVentaId(new BigInteger(pxpv.getId()));
		cotizacion.setVigenciaPoliza(vpDAO.buscarPorId("1"));										
		cotizacion.setAgenteId(BigInteger.valueOf(Long.valueOf(puntoVenta.getAgenteId())));				
		cotizacion.setClienteId(BigInteger.valueOf(Long.valueOf(cliente.getId())));
		cotizacion.setGrupoProductoId(BigInteger.valueOf(Long.valueOf(grupoPorProducto.getGrupoProducto().getId())));
		cotizacion.setGrupoPorProductoId(BigInteger.valueOf(Long.valueOf(grupoPorProducto.getId())));
		cotizacion.setProducto(grupoPorProducto.getProducto());
		// Agregamos el tipo de  objeto a la cotización
		cotizacion.setTipoObjeto(tipoObjetoDAO.buscarPorNombre("Agricola"));
		cotizacion.setEstado(estadoDAO.buscarPorNombre("Borrador","Cotizacion"));
		cotizacion.setUsuario(usuarioDAO.buscarPorLogin("dgarzon")); //TODO: Quemado usuario
		java.sql.Timestamp sq = new java.sql.Timestamp(nuevaCotizacion.getFechaCreacionCotizacion().getTime());
		cotizacion.setFechaElaboracion(sq);

		//Pongo los valores calculados por cotización siempre y cuando sean los que vienen calculados
		cotizacion.setImpDerechoEmision(nuevaCotizacion.getDerechoEmision());
		cotizacion.setImpSeguroCampesino(nuevaCotizacion.getSeguroCampesino());
		cotizacion.setImpSuperBancos(nuevaCotizacion.getSuperBancos());
		cotizacion.setImpIva(nuevaCotizacion.getIva());
		cotizacion.setValorDescuento(0);
		cotizacion.setTotalFactura(nuevaCotizacion.getTotalPrima());

		

		cotizacion.setEtapaWizard(3);

		if(cotizacion.getId()!=null)
			cotizacion = cotizacionTransaction.editar(cotizacion);	
		else
			cotizacion = cotizacionTransaction.crear(cotizacion);


		//Inicia la creación del objeto de la cotización agricola.
		//Inserta el detalle de la cotización
		AgriObjetoCotizacion agriObjetoCotizacion=new AgriObjetoCotizacion();
		agriObjetoCotizacion.setProvinciaId(nuevaCotizacion.getProvinciaId());
		agriObjetoCotizacion.setCantonId(nuevaCotizacion.getCantonId());
		agriObjetoCotizacion.setParroquiaId(nuevaCotizacion.getParroquiaId());
		agriObjetoCotizacion.setTipoCultivoId(nuevaCotizacion.getTipoCultivoId());
		if(nuevaCotizacion.getVariedad()!=null)
			agriObjetoCotizacion.setVariedad(nuevaCotizacion.getVariedad().toString());
		agriObjetoCotizacion.setAgricultorTecnificado(nuevaCotizacion.getAgricultorTecnificado());
		agriObjetoCotizacion.setDisponeRiego(nuevaCotizacion.getDisponeRiego());
		agriObjetoCotizacion.setDisponeAsistencia(nuevaCotizacion.getDisponeAsistencia());
		agriObjetoCotizacion.setAgricultorTecnificado(nuevaCotizacion.getAgricultorTecnificado());
		agriObjetoCotizacion.setDireccionSiembra(nuevaCotizacion.getDireccionSiembra());
		agriObjetoCotizacion.setTipoSeguro(nuevaCotizacion.getTipoSeguro());
		agriObjetoCotizacion.setAniosCultivo(nuevaCotizacion.getEdadCultivo());
		agriObjetoCotizacion.setHectareasLote(new Float(nuevaCotizacion.getHectareasLote()));
		//agriObjetoCotizacion.setLatitud(new Float(nuevaCotizacion.getLatitud()));
		//agriObjetoCotizacion.setLongitud(new Float(nuevaCotizacion.getLongitud));
		agriObjetoCotizacion.setHectareasAsegurables(new Float(nuevaCotizacion.getHectareasAsegurables()));
		agriObjetoCotizacion.setMontoCredito(new Float(nuevaCotizacion.getMontoCredito()));
		//agriObjetoCotizacion.setSucursalCanalId(new BigInteger(nuevaCotizacion.getSucursalCanalId));
		agriObjetoCotizacion.setFechaCredito(nuevaCotizacion.getFechaCredito());
		agriObjetoCotizacion.setFechaSiembra(nuevaCotizacion.getFechaSiembra());
		agriObjetoCotizacion.setObjetoOfflineId(nuevaCotizacion.getObjetoCotizacionId());
		//agriObjetoCotizacion.setAltitudNivelMar(new Float(nuevaCotizacion.getAltitudNivelMar()));
		agriObjetoCotizacion.setUsuarioOffline(nuevaCotizacion.getUsuarioNombre()+" "+nuevaCotizacion.getUsuarioApellido());
		agriObjetoCotizacion.setTipoOrigen(tipoOrigen);


		agriObjetoCotizacion=agriObjetoCotizacionTransaction.crear(agriObjetoCotizacion);


		//Creo la cotización detalle
		CotizacionDetalle nuevoCotizacionDetalle=new CotizacionDetalle();
		nuevoCotizacionDetalle.setCotizacion(cotizacion);
		nuevoCotizacionDetalle.setNecesitaInspeccion(false);
		nuevoCotizacionDetalle.setTipoObjetoId(tipoObjetoDAO.buscarPorNombre("Agricola").getId());
		nuevoCotizacionDetalle.setObjetoId(agriObjetoCotizacion.getObjetoCotizacionId().toString());
		nuevoCotizacionDetalle.setSumaAseguradaItem(nuevaCotizacion.getAnalisisMontoAsegurado());
		nuevoCotizacionDetalle.setPrimaNetaItem(nuevaCotizacion.getPrimaNeta());
		cotizacionDetalleTransaction.crear(nuevoCotizacionDetalle);

		return cotizacion.getId();
	}
}
