package com.qbe.cotizador.servlets.producto.agricola;

import java.util.Date;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class CotizacionArchivoPlano {

	private String Canal;

	private String Cliente;

	private String ClienteIdentificacion;

	private Double MontoAsegurado;

	private java.sql.Timestamp FechaSolicitud;

	private java.sql.Timestamp FechaSiembra;

	private String TipoCultivoNombre;

	private Double NumeroHectareasAseguradas;

	private Double NumeroHectareasLote;

	private Boolean EsTecnificado;

	private String ProvinciaNombre;

	private String CantonNombre;

	private String ParroquiaNombre;

	private String UbicacionCultivo;

	public String getCanal() {
		return Canal;
	}

	public void setCanal(String canal) {
		Canal = canal;
	}

	public String getCliente() {
		return Cliente;
	}

	public void setCliente(String cliente) {
		Cliente = cliente;
	}

	public String getClienteIdentificacion() {
		return ClienteIdentificacion;
	}

	public void setClienteIdentificacion(String clienteIdentificacion) {
		ClienteIdentificacion = clienteIdentificacion;
	}

	public Double getMontoAsegurado() {
		return MontoAsegurado;
	}

	public void setMontoAsegurado(Double montoAsegurado) {
		MontoAsegurado = montoAsegurado;
	}

	public java.sql.Timestamp getFechaSolicitud() {
		return FechaSolicitud;
	}

	public void setFechaSolicitud(java.sql.Timestamp fechaSolicitud) {
		FechaSolicitud = fechaSolicitud;
	}

	public java.sql.Timestamp getFechaSiembra() {
		return FechaSiembra;
	}

	public void setFechaSiembra(java.sql.Timestamp fechaSiembra) {
		FechaSiembra = fechaSiembra;
	}

	public String getTipoCultivoNombre() {
		return TipoCultivoNombre;
	}

	public void setTipoCultivoNombre(String tipoCultivoNombre) {
		TipoCultivoNombre = tipoCultivoNombre;
	}

	public Double getNumeroHectareasAseguradas() {
		return NumeroHectareasAseguradas;
	}

	public void setNumeroHectareasAseguradas(Double numeroHectareasAseguradas) {
		NumeroHectareasAseguradas = numeroHectareasAseguradas;
	}

	public Double getNumeroHectareasLote() {
		return NumeroHectareasLote;
	}

	public void setNumeroHectareasLote(Double numeroHectareasLote) {
		NumeroHectareasLote = numeroHectareasLote;
	}

	public Boolean getEsTecnificado() {
		return EsTecnificado;
	}

	public void setEsTecnificado(Boolean esTecnificado) {
		EsTecnificado = esTecnificado;
	}

	public String getProvinciaNombre() {
		return ProvinciaNombre;
	}

	public void setProvinciaNombre(String provinciaNombre) {
		ProvinciaNombre = provinciaNombre;
	}

	public String getCantonNombre() {
		return CantonNombre;
	}

	public void setCantonNombre(String cantonNombre) {
		CantonNombre = cantonNombre;
	}

	public String getParroquiaNombre() {
		return ParroquiaNombre;
	}

	public void setParroquiaNombre(String parroquiaNombre) {
		ParroquiaNombre = parroquiaNombre;
	}

	public String getUbicacionCultivo() {
		return UbicacionCultivo;
	}

	public void setUbicacionCultivo(String ubicacionCultivo) {
		UbicacionCultivo = ubicacionCultivo;
	}

}
