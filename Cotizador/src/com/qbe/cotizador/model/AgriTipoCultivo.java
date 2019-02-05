package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;



/**
 * The persistent class for the AGRI_TIPO_CULTIVO database table.
 * 
 */
@Entity
@Table(name="AGRI_TIPO_CULTIVO")
@NamedQueries({
		@NamedQuery(name="AgriTipoCultivo.findAll", query="SELECT a FROM AgriTipoCultivo a"),
		@NamedQuery(name="AgriTipoCultivo.buscarPorId", query="SELECT c FROM AgriTipoCultivo c where c.tipoCultivoId = :tipoCultivoId")		
		})

public class AgriTipoCultivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TIPO_CULTIVO_ID")
	private BigInteger tipoCultivoId;

	private String nombre;
	
	@Column(name="COD_ENSURANCE")
	private String codEnsurance;

	private int tipo;
	@Column(name="VIGENCIA_DIAS")
	private int vigenciaDias;

	public AgriTipoCultivo() {
	}

	public BigInteger getTipoCultivoId() {
		return this.tipoCultivoId;
	}

	public void setTipoCultivoId(BigInteger tipoCultivoId) {
		this.tipoCultivoId = tipoCultivoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getVigenciaDias() {
		return this.vigenciaDias;
	}

	public void setVigenciaDias(int vigenciaDias) {
		this.vigenciaDias = vigenciaDias;
	}

	public String getCodEnsurance() {
		return codEnsurance;
	}

	public void setCodEnsurance(String codEnsurance) {
		this.codEnsurance = codEnsurance;
	}

}