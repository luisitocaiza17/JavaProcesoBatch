package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_CONFIGURACION_COBERTURA database table.
 * 
 */
@Entity
@Table(name="PYME_CONFIGURACION_COBERTURA")
@NamedQueries({
	@NamedQuery(name="PymeConfiguracionCobertura.buscarTodos", query="SELECT p FROM PymeConfiguracionCobertura p"),
	@NamedQuery(name="PymeConfiguracionCobertura.buscarPorId", query="SELECT p FROM PymeConfiguracionCobertura p where p.configuracionCoberturaId=:Id"),
	@NamedQuery(name="PymeConfiguracionCobertura.buscarTodos", query="SELECT p FROM PymeConfiguracionCobertura p")
})
public class PymeConfiguracionCobertura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONFIGURACION_COBERTURA_ID")
	private BigInteger configuracionCoberturaId;

	@Column(name="COBERTURA_COPIA_ID")
	private BigInteger coberturaCopiaId;

	@Column(name="COBERTURA_PYMES_ID")
	private BigInteger coberturaPymesId;

	@Column(name="DEPENDE_VALOR")
	private int dependeValor;

	@Column(name="GRUPO_POR_PRODUCTO_ID")
	private BigInteger grupoPorProductoId;

	@Column(name="INCLUYE_EN_PRODUCTO")
	private int incluyeEnProducto;

	@Column(name="ORDEN_PRESENTACION")
	private int ordenPresentacion;

	@Column(name="ORIGEN_VALOR_LIMITE_ASEGURADO")
	private int origenValorLimiteAsegurado;

	@Column(name="ORIGEN_VALOR_LIMITE_COBERTURA")
	private int origenValorLimiteCobertura;

	@Column(name="PORCENTAJE_LIMITE_ASEGURADO")
	private double porcentajeLimiteAsegurado;

	@Column(name="PORCENTAJE_LIMITE_COBERTURA")
	private double porcentajeLimiteCobertura;

	private double tasa;

	@Column(name="TEXTO_DEDUCIBLE")
	private String textoDeducible;

	@Column(name="TIPO_DECLARACION")
	private int tipoDeclaracion;

	@Column(name="TIPO_TASA")
	private int tipoTasa;

	@Column(name="VALOR_MAXIMO_LIMITE_ASEGURADO")
	private double valorMaximoLimiteAsegurado;

	@Column(name="VALOR_MAXIMO_LIMITE_COBERTURA")
	private double valorMaximoLimiteCobertura;

	public PymeConfiguracionCobertura() {
	}

	public BigInteger getConfiguracionCoberturaId() {
		return this.configuracionCoberturaId;
	}

	public void setConfiguracionCoberturaId(BigInteger configuracionCoberturaId) {
		this.configuracionCoberturaId = configuracionCoberturaId;
	}

	public BigInteger getCoberturaCopiaId() {
		return this.coberturaCopiaId;
	}

	public void setCoberturaCopiaId(BigInteger coberturaCopiaId) {
		this.coberturaCopiaId = coberturaCopiaId;
	}

	public BigInteger getCoberturaPymesId() {
		return this.coberturaPymesId;
	}

	public void setCoberturaPymesId(BigInteger coberturaPymesId) {
		this.coberturaPymesId = coberturaPymesId;
	}

	public int getDependeValor() {
		return this.dependeValor;
	}

	public void setDependeValor(int dependeValor) {
		this.dependeValor = dependeValor;
	}

	public BigInteger getGrupoPorProductoId() {
		return this.grupoPorProductoId;
	}

	public void setGrupoPorProductoId(BigInteger grupoPorProductoId) {
		this.grupoPorProductoId = grupoPorProductoId;
	}

	public int getIncluyeEnProducto() {
		return this.incluyeEnProducto;
	}

	public void setIncluyeEnProducto(int incluyeEnProducto) {
		this.incluyeEnProducto = incluyeEnProducto;
	}

	public int getOrdenPresentacion() {
		return this.ordenPresentacion;
	}

	public void setOrdenPresentacion(int ordenPresentacion) {
		this.ordenPresentacion = ordenPresentacion;
	}

	public int getOrigenValorLimiteAsegurado() {
		return this.origenValorLimiteAsegurado;
	}

	public void setOrigenValorLimiteAsegurado(int origenValorLimiteAsegurado) {
		this.origenValorLimiteAsegurado = origenValorLimiteAsegurado;
	}

	public int getOrigenValorLimiteCobertura() {
		return this.origenValorLimiteCobertura;
	}

	public void setOrigenValorLimiteCobertura(int origenValorLimiteCobertura) {
		this.origenValorLimiteCobertura = origenValorLimiteCobertura;
	}

	public double getPorcentajeLimiteAsegurado() {
		return this.porcentajeLimiteAsegurado;
	}

	public void setPorcentajeLimiteAsegurado(double porcentajeLimiteAsegurado) {
		this.porcentajeLimiteAsegurado = porcentajeLimiteAsegurado;
	}

	public double getPorcentajeLimiteCobertura() {
		return this.porcentajeLimiteCobertura;
	}

	public void setPorcentajeLimiteCobertura(double porcentajeLimiteCobertura) {
		this.porcentajeLimiteCobertura = porcentajeLimiteCobertura;
	}

	public double getTasa() {
		return this.tasa;
	}

	public void setTasa(double tasa) {
		this.tasa = tasa;
	}

	public String getTextoDeducible() {
		return this.textoDeducible;
	}

	public void setTextoDeducible(String textoDeducible) {
		this.textoDeducible = textoDeducible;
	}

	public int getTipoDeclaracion() {
		return this.tipoDeclaracion;
	}

	public void setTipoDeclaracion(int tipoDeclaracion) {
		this.tipoDeclaracion = tipoDeclaracion;
	}

	public int getTipoTasa() {
		return this.tipoTasa;
	}

	public void setTipoTasa(int tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public double getValorMaximoLimiteAsegurado() {
		return this.valorMaximoLimiteAsegurado;
	}

	public void setValorMaximoLimiteAsegurado(double valorMaximoLimiteAsegurado) {
		this.valorMaximoLimiteAsegurado = valorMaximoLimiteAsegurado;
	}

	public double getValorMaximoLimiteCobertura() {
		return this.valorMaximoLimiteCobertura;
	}

	public void setValorMaximoLimiteCobertura(double valorMaximoLimiteCobertura) {
		this.valorMaximoLimiteCobertura = valorMaximoLimiteCobertura;
	}

}