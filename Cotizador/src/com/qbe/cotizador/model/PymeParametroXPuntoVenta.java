package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_PARAMETRO_X_PUNTO_VENTA database table.
 * 
 */
@Entity
@Table(name="PYME_PARAMETRO_X_PUNTO_VENTA")
@NamedQueries({
	@NamedQuery(name="PymeParametroXPuntoVenta.buscarTodos", query="SELECT p FROM PymeParametroXPuntoVenta p"),
	@NamedQuery(name="PymeParametroXPuntoVenta.buscarPorId", query="SELECT c FROM PymeParametroXPuntoVenta c WHERE c.parametroPuntoVentaId=:Id"),
	@NamedQuery(name="PymeParametroXPuntoVenta.buscarPorPuntoVentaId", query="SELECT c FROM PymeParametroXPuntoVenta c WHERE c.puntoVentaId=:puntoVentaId")
})
public class PymeParametroXPuntoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PARAMETRO_PUNTO_VENTA_ID")
	private BigInteger parametroPuntoVentaId;

	@Column(name="CONTENEDOR_ENSURANCE_ID")
	private BigInteger contenedorEnsuranceId;

	@Column(name="EMISION_DIRECTA")
	private Boolean emisionDirecta;

	@Column(name="PLANTILLA_ENSURANCE_ID")
	private BigInteger plantillaEnsuranceId;

	@Column(name="PUEDE_MODIFICAR_TASAS")
	private Boolean puedeModificarTasas;

	@Column(name="PUEDE_VISUALIZAR_TASAS")
	private Boolean puedeVisualizarTasas;

	@Column(name="PUNTO_VENTA_ID")
	private BigInteger puntoVentaId;

	public PymeParametroXPuntoVenta() {
	}

	public BigInteger getParametroPuntoVentaId() {
		return this.parametroPuntoVentaId;
	}

	public void setParametroPuntoVentaId(BigInteger parametroPuntoVentaId) {
		this.parametroPuntoVentaId = parametroPuntoVentaId;
	}

	public BigInteger getContenedorEnsuranceId() {
		return this.contenedorEnsuranceId;
	}

	public void setContenedorEnsuranceId(BigInteger contenedorEnsuranceId) {
		this.contenedorEnsuranceId = contenedorEnsuranceId;
	}

	public Boolean getEmisionDirecta() {
		return this.emisionDirecta;
	}

	public void setEmisionDirecta(Boolean emisionDirecta) {
		this.emisionDirecta = emisionDirecta;
	}

	public BigInteger getPlantillaEnsuranceId() {
		return this.plantillaEnsuranceId;
	}

	public void setPlantillaEnsuranceId(BigInteger plantillaEnsuranceId) {
		this.plantillaEnsuranceId = plantillaEnsuranceId;
	}

	public Boolean getPuedeModificarTasas() {
		return this.puedeModificarTasas;
	}

	public void setPuedeModificarTasas(Boolean puedeModificarTasas) {
		this.puedeModificarTasas = puedeModificarTasas;
	}

	public Boolean getPuedeVisualizarTasas() {
		return this.puedeVisualizarTasas;
	}

	public void setPuedeVisualizarTasas(Boolean puedeVisualizarTasas) {
		this.puedeVisualizarTasas = puedeVisualizarTasas;
	}

	public BigInteger getPuntoVentaId() {
		return this.puntoVentaId;
	}

	public void setPuntoVentaId(BigInteger puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

}