package com.qbe.cotizador.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the PYME_COBERTURA_COTIZACION_VALOR database table.
 * 
 */
@Entity
@Table(name="PYME_COBERTURA_COTIZACION_VALOR")
@NamedQuery(name="PymeCoberturaCotizacionValor.findAll", query="SELECT p FROM PymeCoberturaCotizacionValor p")
public class PymeCoberturaCotizacionValor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cotizacion_id")
	private BigInteger cotizacionId;

	private BigInteger id;

	private String nombre;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OBJETO_PYMES_COBERTURA_ID")
	private BigInteger objetoPymesCoberturaId;

	@Column(name="PRIMA_CALCULADA")
	private double primaCalculada;

	@Column(name="TASA_SUGERIDA")
	private double tasaSugerida;

	@Column(name="VALOR_INGRESADO")
	private double valorIngresado;

	public PymeCoberturaCotizacionValor() {
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

	public BigInteger getObjetoPymesCoberturaId() {
		return this.objetoPymesCoberturaId;
	}

	public void setObjetoPymesCoberturaId(BigInteger objetoPymesCoberturaId) {
		this.objetoPymesCoberturaId = objetoPymesCoberturaId;
	}

	public double getPrimaCalculada() {
		return this.primaCalculada;
	}

	public void setPrimaCalculada(double primaCalculada) {
		this.primaCalculada = primaCalculada;
	}

	public double getTasaSugerida() {
		return this.tasaSugerida;
	}

	public void setTasaSugerida(double tasaSugerida) {
		this.tasaSugerida = tasaSugerida;
	}

	public double getValorIngresado() {
		return this.valorIngresado;
	}

	public void setValorIngresado(double valorIngresado) {
		this.valorIngresado = valorIngresado;
	}

}