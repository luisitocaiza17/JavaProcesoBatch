package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the PYME_ASISTENCIA database table.
 * 
 */
@Entity
@Table(name="PYME_ASISTENCIA")
@NamedQueries({
	@NamedQuery(name="PymeAsistencia.buscarTodos", query="SELECT p FROM PymeAsistencia p"),
	@NamedQuery(name="PymeAsistencia.buscarPorId", query="SELECT c FROM PymeAsistencia c where c.asistenciaId = :asistenciaId"),
	@NamedQuery(name="PymeAsistencia.buscarGrupoPorProductoId", query="SELECT c FROM PymeAsistencia c where c.grupoPorProductoId=:grupoPorProductoId")
})
public class PymeAsistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ASISTENCIA_ID")
	private BigInteger asistenciaId;

	@Column(name="ES_PREDETERMINADA")
	private Boolean esPredeterminada;

	@Column(name="GRUPO_POR_PRODUCTO_ID")
	private BigInteger grupoPorProductoId;

	private String nombre;

	private double valor;

	public PymeAsistencia() {
	}

	public BigInteger getAsistenciaId() {
		return this.asistenciaId;
	}

	public void setAsistenciaId(BigInteger asistenciaId) {
		this.asistenciaId = asistenciaId;
	}

	public Boolean getEsPredeterminada() {
		return this.esPredeterminada;
	}

	public void setEsPredeterminada(Boolean esPredeterminada) {
		this.esPredeterminada = esPredeterminada;
	}

	public BigInteger getGrupoPorProductoId() {
		return this.grupoPorProductoId;
	}

	public void setGrupoPorProductoId(BigInteger grupoPorProductoId) {
		this.grupoPorProductoId = grupoPorProductoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}