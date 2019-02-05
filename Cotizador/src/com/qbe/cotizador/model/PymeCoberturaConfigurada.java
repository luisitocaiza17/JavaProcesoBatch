package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_COBERTURA_CONFIGURADAS database table.
 * 
 */
@Entity
@Table(name="PYME_COBERTURA_CONFIGURADAS")
@NamedQueries({
	@NamedQuery(name="PymeCoberturaConfigurada.buscarTodos", query="SELECT p FROM PymeCoberturaConfigurada p"),
	@NamedQuery(name="PymeCoberturaConfigurada.buscarPorGrupoPorProductoId", query="SELECT p FROM PymeCoberturaConfigurada p where p.grupoPorProductoId=:grupoPorProductoId")
})
public class PymeCoberturaConfigurada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COBERTURA_COPIA_ID")
	private BigInteger coberturaCopiaId;

	@Column(name="COBERTURA_PYMES_ID")
	private BigInteger coberturaPymesId;

	@Column(name="DEPENDE_VALOR")
	private int dependeValor;

	@Column(name="GRUPO_POR_PRODUCTO_ID")
	private BigInteger grupoPorProductoId;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;

	@Column(name="INCLUYE_EN_PRODUCTO")
	private int incluyeEnProducto;

	private String nombre;

	@Column(name="NOMBRE_COMERCIAL_PRODUCTO")
	private String nombreComercialProducto;

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

	@Column(name="TIPO_COBERTURA_ID")
	private BigInteger tipoCoberturaId;

	@Column(name="TIPO_DECLARACION")
	private int tipoDeclaracion;

	@Column(name="TIPO_TASA")
	private int tipoTasa;

	@Column(name="VALOR_MAXIMO_LIMITE_ASEGURADO")
	private double valorMaximoLimiteAsegurado;

	@Column(name="VALOR_MAXIMO_LIMITE_COBERTURA")
	private double valorMaximoLimiteCobertura;

	public PymeCoberturaConfigurada() {
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

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int getIncluyeEnProducto() {
		return this.incluyeEnProducto;
	}

	public void setIncluyeEnProducto(int incluyeEnProducto) {
		this.incluyeEnProducto = incluyeEnProducto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreComercialProducto() {
		return this.nombreComercialProducto;
	}

	public void setNombreComercialProducto(String nombreComercialProducto) {
		this.nombreComercialProducto = nombreComercialProducto;
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

	public BigInteger getTipoCoberturaId() {
		return this.tipoCoberturaId;
	}

	public void setTipoCoberturaId(BigInteger tipoCoberturaId) {
		this.tipoCoberturaId = tipoCoberturaId;
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