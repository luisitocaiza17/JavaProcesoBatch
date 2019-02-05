package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the AGRI_COTIZACIONES database table.
 * 
 */
@Entity
@Table(name="AGRI_COTIZACIONES")
@NamedQueries({
	@NamedQuery(name="AgriCotizaciones.buscarTodos", query="SELECT p FROM AgriCotizaciones p"),
	@NamedQuery(name="AgriCotizaciones.buscarCotizacionId", query="SELECT p FROM AgriCotizaciones p where p.id=:id"),
	@NamedQuery(name="AgriCotizaciones.buscarPorIdentificacion", query="SELECT p FROM AgriCotizaciones p where p.identificacion=:identificacion")
})
public class AgriCotizaciones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;	

	@Column(name="identificacion")
	private String identificacion;
	
	@Column(name="nombre_completo")
	private String nombre_completo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_elaboracion")
	private java.util.Date fecha_elaboracion;
	
	@Column(name="suma_asegurada_total")
	private double suma_asegurada_total;
	
	@Column(name="prima_neta_total")
	private String prima_neta_total;
	
	@Column(name="tipo_cultivo_id")
	private BigInteger tipo_cultivo_id;
	
	@Column(name="tipo_cultivo_nombre")
	private String tipo_cultivo_nombre;
	
	@Column(name="provincia_id")
	private String provincia_id;
	
	@Column(name="provincia_nombre")
	private BigInteger provincia_nombre;
	
	@Column(name="canton_id")
	private BigInteger canton_id;
	
	@Column(name="canton_nombre")
	private String canton_nombre;
	
	@Column(name="parroquia_id")
	private BigInteger parroquia_id;
	
	@Column(name="parroquia_nombre")
	private String parroquia_nombre;
	
	@Column(name="direccion_siembra")
	private String direccion_siembra;
	
	@Column(name="agricultor_tecnificado")
	private boolean agricultor_tecnificado;
	
	@Column(name="hectareas_lote")
	private float hectareas_lote;
	
	@Column(name="hectareas_asegurables")
	private float hectareas_asegurables;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_siembra")
	private Date fecha_siembra;
	
	@Column(name="altitud_nivel_mar")
	private float altitud_nivel_mar;
	
	@Column(name="latitud")
	private float latitud;
	
	@Column(name="longitud")
	private float longitud;
	
	public AgriCotizaciones() {
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public Date getFecha_elaboracion() {
		return fecha_elaboracion;
	}

	public void setFecha_elaboracion(Date fecha_elaboracion) {
		this.fecha_elaboracion = fecha_elaboracion;
	}

	public double getSuma_asegurada_total() {
		return suma_asegurada_total;
	}

	public void setSuma_asegurada_total(double suma_asegurada_total) {
		this.suma_asegurada_total = suma_asegurada_total;
	}

	public String getPrima_neta_total() {
		return prima_neta_total;
	}

	public void setPrima_neta_total(String prima_neta_total) {
		this.prima_neta_total = prima_neta_total;
	}

	public BigInteger getTipo_cultivo_id() {
		return tipo_cultivo_id;
	}

	public void setTipo_cultivo_id(BigInteger tipo_cultivo_id) {
		this.tipo_cultivo_id = tipo_cultivo_id;
	}

	public String getTipo_cultivo_nombre() {
		return tipo_cultivo_nombre;
	}

	public void setTipo_cultivo_nombre(String tipo_cultivo_nombre) {
		this.tipo_cultivo_nombre = tipo_cultivo_nombre;
	}

	public String getProvincia_id() {
		return provincia_id;
	}

	public void setProvincia_id(String provincia_id) {
		this.provincia_id = provincia_id;
	}

	public BigInteger getProvincia_nombre() {
		return provincia_nombre;
	}

	public void setProvincia_nombre(BigInteger provincia_nombre) {
		this.provincia_nombre = provincia_nombre;
	}

	public BigInteger getCanton_id() {
		return canton_id;
	}

	public void setCanton_id(BigInteger canton_id) {
		this.canton_id = canton_id;
	}

	public String getCanton_nombre() {
		return canton_nombre;
	}

	public void setCanton_nombre(String canton_nombre) {
		this.canton_nombre = canton_nombre;
	}

	public BigInteger getParroquia_id() {
		return parroquia_id;
	}

	public void setParroquia_id(BigInteger parroquia_id) {
		this.parroquia_id = parroquia_id;
	}

	public String getParroquia_nombre() {
		return parroquia_nombre;
	}

	public void setParroquia_nombre(String parroquia_nombre) {
		this.parroquia_nombre = parroquia_nombre;
	}

	public String getDireccion_siembra() {
		return direccion_siembra;
	}

	public void setDireccion_siembra(String direccion_siembra) {
		this.direccion_siembra = direccion_siembra;
	}

	public boolean isAgricultor_tecnificado() {
		return agricultor_tecnificado;
	}

	public void setAgricultor_tecnificado(boolean agricultor_tecnificado) {
		this.agricultor_tecnificado = agricultor_tecnificado;
	}

	public float getHectareas_lote() {
		return hectareas_lote;
	}

	public void setHectareas_lote(float hectareas_lote) {
		this.hectareas_lote = hectareas_lote;
	}

	public float getHectareas_asegurables() {
		return hectareas_asegurables;
	}

	public void setHectareas_asegurables(float hectareas_asegurables) {
		this.hectareas_asegurables = hectareas_asegurables;
	}

	public Date getFecha_siembra() {
		return fecha_siembra;
	}

	public void setFecha_siembra(Date fecha_siembra) {
		this.fecha_siembra = fecha_siembra;
	}

	public float getAltitud_nivel_mar() {
		return altitud_nivel_mar;
	}

	public void setAltitud_nivel_mar(float altitud_nivel_mar) {
		this.altitud_nivel_mar = altitud_nivel_mar;
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

}