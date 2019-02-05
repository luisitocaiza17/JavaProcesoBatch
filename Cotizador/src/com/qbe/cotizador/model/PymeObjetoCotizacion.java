package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_OBJETO_COTIZACION database table.
 * 
 */
@Entity
@Table(name="PYME_OBJETO_COTIZACION")
@NamedQueries({
	@NamedQuery(name="PymeObjetoCotizacion.buscarTodos", query="SELECT p FROM PymeObjetoCotizacion p"),
	@NamedQuery(name="PymeObjetoCotizacion.buscarPorId", query="SELECT c FROM PymeObjetoCotizacion c where c.objetoPymesId=:id")
})
public class PymeObjetoCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OBJETO_PYMES_ID")
	private BigInteger objetoPymesId;

	@Column(name="ACTIVIDAD_ECONOMICA_ID")
	private BigInteger actividadEconomicaId;

	@Column(name="ALARMA_MONITOREADA")
	private Boolean alarmaMonitoreada;

	@Column(name="ANIO_CONSTRUCCION")
	private int anioConstruccion;

	@Column(name="CALLE_PRINCIPAL")
	private String callePrincipal;

	@Column(name="CALLE_SECUNDARIA")
	private String calleSecundaria;

	@Column(name="CIUDAD_ID")
	private int ciudadId;

	@Column(name="CONTABILIDAD_FORMAL")
	private Boolean contabilidadFormal;

	@Column(name="DETECTOR_HUMO")
	private Boolean detectorHumo;

	@Column(name="ESTADO_INSPECCION")
	private int estadoInspeccion;
	
	private Boolean extintores;
	
	@Column(name="REQUIERE_INSPECCION")
	private Boolean requiereInspeccion;

	private Boolean guardiania;
	
	@Lob
	private byte[] informe;

	private double latitud;

	private double longuitud;

	@Column(name="NOMBRE_EDIFICIO")
	private String nombreEdificio;

	@Column(name="NUMERO_DIRECCION")
	private String numeroDireccion;

	@Column(name="NUMERO_OFICINA")
	private String numeroOficina;

	@Column(name="NUMERO_PISO")
	private String numeroPiso;

	@Column(name="NUMERO_TOTAL_PISOS")
	private int numeroTotalPisos;

	private String otros;

	@Column(name="PROVINCIA_ID")
	private int provinciaId;

	private String sector;

	private Boolean sprinklers;

	@Column(name="TIENE_MAS_DOS_ANIO")
	private Boolean tieneMasDosAnio;

	@Column(name="TIPO_CONSTRUCCION_ID")
	private BigInteger tipoConstruccionId;

	@Column(name="TIPO_OCUPACION_ID")
	private BigInteger tipoOcupacionId;

	@Column(name="VALOR_EQUIPO_HERRAMIENTA")
	private double valorEquipoHerramienta;

	@Column(name="VALOR_ESTRUCTURAS")
	private double valorEstructuras;

	@Column(name="VALOR_INSUMOS_NOELECTRONICOS")
	private double valorInsumosNoelectronicos;

	@Column(name="VALOR_MAQUINARIA")
	private double valorMaquinaria;

	@Column(name="VALOR_MERCADERIA")
	private double valorMercaderia;

	@Column(name="VALOR_MUEBLES_ENSERES")
	private double valorMueblesEnseres;

	public PymeObjetoCotizacion() {
	}

	public BigInteger getObjetoPymesId() {
		return this.objetoPymesId;
	}

	public void setObjetoPymesId(BigInteger objetoPymesId) {
		this.objetoPymesId = objetoPymesId;
	}

	public BigInteger getActividadEconomicaId() {
		return this.actividadEconomicaId;
	}

	public void setActividadEconomicaId(BigInteger actividadEconomicaId) {
		this.actividadEconomicaId = actividadEconomicaId;
	}

	public Boolean getAlarmaMonitoreada() {
		return this.alarmaMonitoreada;
	}

	public void setAlarmaMonitoreada(Boolean alarmaMonitoreada) {
		this.alarmaMonitoreada = alarmaMonitoreada;
	}

	public int getAnioConstruccion() {
		return this.anioConstruccion;
	}

	public void setAnioConstruccion(int anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public String getCallePrincipal() {
		return this.callePrincipal;
	}

	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}

	public String getCalleSecundaria() {
		return this.calleSecundaria;
	}

	public void setCalleSecundaria(String calleSecundaria) {
		this.calleSecundaria = calleSecundaria;
	}

	public int getCiudadId() {
		return this.ciudadId;
	}

	public void setCiudadId(int ciudadId) {
		this.ciudadId = ciudadId;
	}

	public Boolean getContabilidadFormal() {
		return this.contabilidadFormal;
	}

	public void setContabilidadFormal(Boolean contabilidadFormal) {
		this.contabilidadFormal = contabilidadFormal;
	}

	public Boolean getDetectorHumo() {
		return this.detectorHumo;
	}
	
	public Boolean getRequiereInspeccion() {
		return this.requiereInspeccion;
	}

	public void setRequiereInspeccion(Boolean requiereInspeccion) {
		this.requiereInspeccion = requiereInspeccion;
	}
	
	public void setDetectorHumo(Boolean detectorHumo) {
		this.detectorHumo = detectorHumo;
	}

	public int getEstadoInspeccion() {
		return this.estadoInspeccion;
	}

	public void setEstadoInspeccion(int estadoInspeccion) {
		this.estadoInspeccion = estadoInspeccion;
	}
	
	public Boolean getExtintores() {
		return this.extintores;
	}

	public void setExtintores(Boolean extintores) {
		this.extintores = extintores;
	}

	public Boolean getGuardiania() {
		return this.guardiania;
	}

	public void setGuardiania(Boolean guardiania) {
		this.guardiania = guardiania;
	}

	public byte[] getInforme() {
		return this.informe;
	}

	public void setInforme(byte[] informe) {
		this.informe = informe;
	}

	public double getLatitud() {
		return this.latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLonguitud() {
		return this.longuitud;
	}

	public void setLonguitud(double longuitud) {
		this.longuitud = longuitud;
	}
	
	public String getNombreEdificio() {
		return this.nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public String getNumeroDireccion() {
		return this.numeroDireccion;
	}

	public void setNumeroDireccion(String numeroDireccion) {
		this.numeroDireccion = numeroDireccion;
	}

	public String getNumeroOficina() {
		return this.numeroOficina;
	}

	public void setNumeroOficina(String numeroOficina) {
		this.numeroOficina = numeroOficina;
	}

	public String getNumeroPiso() {
		return this.numeroPiso;
	}

	public void setNumeroPiso(String numeroPiso) {
		this.numeroPiso = numeroPiso;
	}

	public int getNumeroTotalPisos() {
		return this.numeroTotalPisos;
	}

	public void setNumeroTotalPisos(int numeroTotalPisos) {
		this.numeroTotalPisos = numeroTotalPisos;
	}

	public String getOtros() {
		return this.otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public int getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(int provinciaId) {
		this.provinciaId = provinciaId;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Boolean getSprinklers() {
		return this.sprinklers;
	}

	public void setSprinklers(Boolean sprinklers) {
		this.sprinklers = sprinklers;
	}

	public Boolean getTieneMasDosAnio() {
		return this.tieneMasDosAnio;
	}

	public void setTieneMasDosAnio(Boolean tieneMasDosAnio) {
		this.tieneMasDosAnio = tieneMasDosAnio;
	}

	public BigInteger getTipoConstruccionId() {
		return this.tipoConstruccionId;
	}

	public void setTipoConstruccionId(BigInteger tipoConstruccionId) {
		this.tipoConstruccionId = tipoConstruccionId;
	}

	public BigInteger getTipoOcupacionId() {
		return this.tipoOcupacionId;
	}

	public void setTipoOcupacionId(BigInteger tipoOcupacionId) {
		this.tipoOcupacionId = tipoOcupacionId;
	}

	public double getValorEquipoHerramienta() {
		return this.valorEquipoHerramienta;
	}

	public void setValorEquipoHerramienta(double valorEquipoHerramienta) {
		this.valorEquipoHerramienta = valorEquipoHerramienta;
	}

	public double getValorEstructuras() {
		return this.valorEstructuras;
	}

	public void setValorEstructuras(double valorEstructuras) {
		this.valorEstructuras = valorEstructuras;
	}

	public double getValorInsumosNoelectronicos() {
		return this.valorInsumosNoelectronicos;
	}

	public void setValorInsumosNoelectronicos(double valorInsumosNoelectronicos) {
		this.valorInsumosNoelectronicos = valorInsumosNoelectronicos;
	}

	public double getValorMaquinaria() {
		return this.valorMaquinaria;
	}

	public void setValorMaquinaria(double valorMaquinaria) {
		this.valorMaquinaria = valorMaquinaria;
	}

	public double getValorMercaderia() {
		return this.valorMercaderia;
	}

	public void setValorMercaderia(double valorMercaderia) {
		this.valorMercaderia = valorMercaderia;
	}

	public double getValorMueblesEnseres() {
		return this.valorMueblesEnseres;
	}

	public void setValorMueblesEnseres(double valorMueblesEnseres) {
		this.valorMueblesEnseres = valorMueblesEnseres;
	}

}