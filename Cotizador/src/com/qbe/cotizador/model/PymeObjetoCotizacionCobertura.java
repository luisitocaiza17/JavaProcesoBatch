package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_OBJETO_COTIZACION_COBERTURA database table.
 * 
 */
@Entity
@Table(name="PYME_OBJETO_COTIZACION_COBERTURA")
@NamedQueries({
	@NamedQuery(name="PymeObjetoCotizacionCobertura.buscarTodos", query="SELECT p FROM PymeObjetoCotizacionCobertura p"),
	@NamedQuery(name="PymeObjetoCotizacionCobertura.buscarPorId", query="SELECT c FROM PymeObjetoCotizacionCobertura c where c.objetoPymesCoberturaId=:id"),
	@NamedQuery(name="PymeObjetoCotizacionCobertura.buscarPorObjetoPymeId", query="SELECT c FROM PymeObjetoCotizacionCobertura c where c.objetoPymesId=:id")
})
public class PymeObjetoCotizacionCobertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OBJETO_PYMES_COBERTURA_ID")
	private BigInteger objetoPymesCoberturaId;

	@Column(name="OBJETO_ORIGEN_ID")
	private BigInteger objetoOrigenId;

	@Column(name="OBJETO_PYMES_ID")
	private BigInteger objetoPymesId;

	@Column(name="PRIMA_CALCULADA")
	private double primaCalculada;

	@Column(name="TASA_INGRESADA")
	private double tasaIngresada;

	@Column(name="TASA_SUGERIDA")
	private double tasaSugerida;

	@Column(name="TEXTO_DEDUCIBLE")
	private String textoDeducible;

	@Column(name="TIPO_ORIGEN")
	private String tipoOrigen;

	@Column(name="VALOR_INGRESADO")
	private double valorIngresado;

	public PymeObjetoCotizacionCobertura() {
	}

	public BigInteger getObjetoPymesCoberturaId() {
		return this.objetoPymesCoberturaId;
	}

	public void setObjetoPymesCoberturaId(BigInteger objetoPymesCoberturaId) {
		this.objetoPymesCoberturaId = objetoPymesCoberturaId;
	}

	public BigInteger getObjetoOrigenId() {
		return this.objetoOrigenId;
	}

	public void setObjetoOrigenId(BigInteger objetoOrigenId) {
		this.objetoOrigenId = objetoOrigenId;
	}

	public BigInteger getObjetoPymesId() {
		return this.objetoPymesId;
	}

	public void setObjetoPymesId(BigInteger objetoPymesId) {
		this.objetoPymesId = objetoPymesId;
	}

	public double getPrimaCalculada() {
		return this.primaCalculada;
	}

	public void setPrimaCalculada(double primaCalculada) {
		this.primaCalculada = primaCalculada;
	}

	public double getTasaIngresada() {
		return this.tasaIngresada;
	}

	public void setTasaIngresada(double tasaIngresada) {
		this.tasaIngresada = tasaIngresada;
	}

	public double getTasaSugerida() {
		return this.tasaSugerida;
	}

	public void setTasaSugerida(double tasaSugerida) {
		this.tasaSugerida = tasaSugerida;
	}

	public String getTextoDeducible() {
		return this.textoDeducible;
	}

	public void setTextoDeducible(String textoDeducible) {
		this.textoDeducible = textoDeducible;
	}

	public String getTipoOrigen() {
		return this.tipoOrigen;
	}

	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public double getValorIngresado() {
		return this.valorIngresado;
	}

	public void setValorIngresado(double valorIngresado) {
		this.valorIngresado = valorIngresado;
	}

}