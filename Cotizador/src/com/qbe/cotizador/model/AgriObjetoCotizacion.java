package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the AGRI_OBJETO_COTIZACION database table.
 * 
 */
@Entity
@Table(name="AGRI_OBJETO_COTIZACION")
@NamedQueries({
	@NamedQuery(name="AgriObjetoCotizacion.buscarTodos", query="SELECT p FROM AgriObjetoCotizacion p"),
	@NamedQuery(name="AgriObjetoCotizacion.buscarPorId", query="SELECT c FROM AgriObjetoCotizacion c where c.objetoCotizacionId=:id"),
	@NamedQuery(name="AgriObjetoCotizacion.buscarPorObjetoOfflineId", query="SELECT c FROM AgriObjetoCotizacion c where c.objetoOfflineId=:objetoOfflineId")
})
public class AgriObjetoCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OBJETO_COTIZACION_ID")
	private BigInteger objetoCotizacionId;

	@Column(name="AGRICULTOR_TECNIFICADO")
	private boolean agricultorTecnificado;
	
	@Column(name="DISPONE_RIEGO")
	private boolean disponeRiego;
	
	@Column(name="DISPONE_ASISTENCIA")
	private boolean disponeAsistencia;

	@Column(name="ALTITUD_NIVEL_MAR")
	private float altitudNivelMar;

	@Column(name="CANTON_ID")
	private BigInteger cantonId;
	
	@Column(name="SUCURSAL_CANAL_ID")
	private BigInteger sucursalCanalId;
	
	@Column(name="VARIEDAD")
	private String variedad;

	public boolean getDisponeRiego() {
		return disponeRiego;
	}

	public void setDisponeRiego(boolean disponeRiego) {
		this.disponeRiego = disponeRiego;
	}

	public boolean getDisponeAsistencia() {
		return disponeAsistencia;
	}

	public void setDisponeAsistencia(boolean disponeAsistencia) {
		this.disponeAsistencia = disponeAsistencia;
	}

	public String getVariedad() {
		return variedad;
	}

	public String getObjetoOfflineId() {
		return objetoOfflineId;
	}

	public void setObjetoOfflineId(String objetoOfflineId) {
		this.objetoOfflineId = objetoOfflineId;
	}

	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}

	public Date getFechaCredito() {
		return fechaCredito;
	}

	public void setFechaCredito(Date fechaCredito) {
		this.fechaCredito = fechaCredito;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	@Column(name="OBJETO_OFFLINE_ID")
	private String objetoOfflineId;
	
	@Column(name="DIRECCION_SIEMBRA")
	private String direccionSiembra;
	
	@Column(name="USUARIO_OFFLINE")
	private String usuarioOffline;
	
	@Column(name="TIPO_ORIGEN")
	private String tipoOrigen;

	@Column(name="ESTADO_INSPECCION")
	private int estadoInspeccion;
	
	@Column(name="TIPO_SEGURO")
	private int tipoSeguro;
	
	@Column(name="ANIOS_CULTIVO")
	private int aniosCultivo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_SIEMBRA")
	private Date fechaSiembra;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CREDITO")
	private Date fechaCredito;

	@Column(name="HECTAREAS_ASEGURABLES")
	private float hectareasAsegurables;

	@Column(name="HECTAREAS_LOTE")
	private float hectareasLote;

	@Column(name="MONTO_CREDITO")
	private float montoCredito;
	
	@Column(name="COSTO_PRODUCCION")
	private float costoProduccion;
	
	@Column(name="LATITUD")
	private float latitud;
	
	@Column(name="LONGITUD")
	private float longitud;

	@Column(name="PARROQUIA_ID")
	private BigInteger parroquiaId;

	@Column(name="PROVINCIA_ID")
	private BigInteger provinciaId;

	@Column(name="TIPO_CULTIVO_ID")
	private BigInteger tipoCultivoId;

	public AgriObjetoCotizacion() {
	}

	public BigInteger getObjetoCotizacionId() {
		return this.objetoCotizacionId;
	}

	public void setObjetoCotizacionId(BigInteger objetoCotizacionId) {
		this.objetoCotizacionId = objetoCotizacionId;
	}

	public boolean getAgricultorTecnificado() {
		return this.agricultorTecnificado;
	}

	public void setAgricultorTecnificado(boolean agricultorTecnificado) {
		this.agricultorTecnificado = agricultorTecnificado;
	}

	public float getAltitudNivelMar() {
		return this.altitudNivelMar;
	}

	public void setAltitudNivelMar(float altitudNivelMar) {
		this.altitudNivelMar = altitudNivelMar;
	}

	public BigInteger getCantonId() {
		return this.cantonId;
	}

	public void setCantonId(BigInteger cantonId) {
		this.cantonId = cantonId;
	}

	public BigInteger getSucursalCanalId() {
		return this.sucursalCanalId;
	}

	public void setSucursalCanalId(BigInteger sucursalCanalId) {
		this.sucursalCanalId = sucursalCanalId;
	}
	
	public String getDireccionSiembra() {
		return this.direccionSiembra;
	}

	public void setDireccionSiembra(String direccionSiembra) {
		this.direccionSiembra = direccionSiembra;
	}

	public int getEstadoInspeccion() {
		return this.estadoInspeccion;
	}

	public void setEstadoInspeccion(int estadoInspeccion) {
		this.estadoInspeccion = estadoInspeccion;
	}
	
	public int getTipoSeguro() {
		return this.tipoSeguro;
	}

	public void setTipoSeguro(int tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}

	public Date getFechaSiembra() {
		return this.fechaSiembra;
	}

	public void setFechaSiembra(Date fechaSiembra) {
		this.fechaSiembra = fechaSiembra;
	}

	public float getHectareasAsegurables() {
		return this.hectareasAsegurables;
	}

	public void setHectareasAsegurables(float hectareasAsegurables) {
		this.hectareasAsegurables = hectareasAsegurables;
	}

	public float getHectareasLote() {
		return this.hectareasLote;
	}

	public void setHectareasLote(float hectareasLote) {
		this.hectareasLote = hectareasLote;
	}

	public float getMontoCredito() {
		return this.montoCredito;
	}

	public void setMontoCredito(float montoCredito) {
		this.montoCredito = montoCredito;
	}

	public BigInteger getParroquiaId() {
		return this.parroquiaId;
	}

	public void setParroquiaId(BigInteger parroquiaId) {
		this.parroquiaId = parroquiaId;
	}

	public BigInteger getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(BigInteger provinciaId) {
		this.provinciaId = provinciaId;
	}

	public BigInteger getTipoCultivoId() {
		return this.tipoCultivoId;
	}

	public void setTipoCultivoId(BigInteger tipoCultivoId) {
		this.tipoCultivoId = tipoCultivoId;
	}

	public String getUsuarioOffline() {
		return usuarioOffline;
	}

	public void setUsuarioOffline(String usuarioOffline) {
		this.usuarioOffline = usuarioOffline;
	}

	public String getTipoOrigen() {
		return tipoOrigen;
	}

	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public int getAniosCultivo() {
		return aniosCultivo;
	}

	public void setAniosCultivo(int aniosCultivo) {
		this.aniosCultivo = aniosCultivo;
	}

	public float getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(float costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	
}