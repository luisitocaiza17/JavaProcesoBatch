package com.qbe.cotizador.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the PYME_TEXTO_GRUPO_COBERTURA_COTIZACION database table.
 * 
 */
@Entity
@Table(name="PYME_TEXTO_GRUPO_COBERTURA_COTIZACION")
@NamedQuery(name="PymeTextoGrupoCoberturaCotizacion.findAll", query="SELECT p FROM PymeTextoGrupoCoberturaCotizacion p")
public class PymeTextoGrupoCoberturaCotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COTIZACION_ID")
	private BigInteger cotizacionId;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;

	private String nombre;

	@Lob
	private byte[] texto;

	public PymeTextoGrupoCoberturaCotizacion() {
	}

	public BigInteger getCotizacionId() {
		return this.cotizacionId;
	}

	public void setCotizacionId(BigInteger cotizacionId) {
		this.cotizacionId = cotizacionId;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getTexto() {
		return this.texto;
	}

	public void setTexto(byte[] texto) {
		this.texto = texto;
	}

}