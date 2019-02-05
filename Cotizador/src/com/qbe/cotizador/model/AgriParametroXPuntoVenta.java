package com.qbe.cotizador.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the AGRI_PARAMETRO_X_PUNTO_VENTA database table.
 * 
 */
@Entity
@Table(name="AGRI_PARAMETRO_X_PUNTO_VENTA")
@NamedQueries({
	@NamedQuery(name="AgriParametroXPuntoVenta.obtenerTodos", query="SELECT a FROM AgriParametroXPuntoVenta a"),
	@NamedQuery(name="AgriParametroXPuntoVenta.obtenerPorId", query="SELECT a FROM AgriParametroXPuntoVenta a where a.parametroPuntoVentaId=:parametroPuntoVentaId"),
	@NamedQuery(name="AgriParametroXPuntoVenta.obtenerPorPuntoVentaId", query="SELECT a FROM AgriParametroXPuntoVenta a where a.puntoVentaId=:puntoVentaId")
	})
public class AgriParametroXPuntoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PARAMETRO_PUNTO_VENTA_ID")
	private BigInteger parametroPuntoVentaId;

	@Column(name="CONTENEDOR_ENSURANCE_ID")
	private String contenedorEnsuranceId;

	@Column(name="EMISION_DIRECTA")
	private boolean emisionDirecta;

	@Column(name="PLANTILLA_ENSURANCE_ID")
	private String plantillaEnsuranceId;

	@Column(name="PUNTO_VENTA_ID")
	private BigInteger puntoVentaId;

	@Column(name="REQUIERE_INSPECCION")
	private boolean requiereInspeccion;

	@Column(name="TIPO_CALCULO_ID")
	private BigInteger tipoCalculoId;

	public AgriParametroXPuntoVenta() {
	}

	public BigInteger getParametroPuntoVentaId() {
		return this.parametroPuntoVentaId;
	}

	public void setParametroPuntoVentaId(BigInteger parametroPuntoVentaId) {
		this.parametroPuntoVentaId = parametroPuntoVentaId;
	}

	public String getContenedorEnsuranceId() {
		return this.contenedorEnsuranceId;
	}

	public void setContenedorEnsuranceId(String contenedorEnsuranceId) {
		this.contenedorEnsuranceId = contenedorEnsuranceId;
	}

	public boolean getEmisionDirecta() {
		return this.emisionDirecta;
	}

	public void setEmisionDirecta(boolean emisionDirecta) {
		this.emisionDirecta = emisionDirecta;
	}

	public String getPlantillaEnsuranceId() {
		return this.plantillaEnsuranceId;
	}

	public void setPlantillaEnsuranceId(String plantillaEnsuranceId) {
		this.plantillaEnsuranceId = plantillaEnsuranceId;
	}

	public BigInteger getPuntoVentaId() {
		return this.puntoVentaId;
	}

	public void setPuntoVentaId(BigInteger puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	public boolean getRequiereInspeccion() {
		return this.requiereInspeccion;
	}

	public void setRequiereInspeccion(boolean requiereInspeccion) {
		this.requiereInspeccion = requiereInspeccion;
	}

	public BigInteger getTipoCalculoId() {
		return this.tipoCalculoId;
	}

	public void setTipoCalculoId(BigInteger tipoCalculoId) {
		this.tipoCalculoId = tipoCalculoId;
	}

}