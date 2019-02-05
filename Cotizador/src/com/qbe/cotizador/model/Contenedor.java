package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;


/**
 * The persistent class for the CONTENEDOR database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Contenedor.buscarPorId", query="SELECT c FROM Contenedor c where c.id = :id"),
	@NamedQuery(name="Contenedor.buscarPorNumero", query="SELECT c FROM Contenedor c where c.numero = :numero"),
	@NamedQuery(name="Contenedor.buscarTodos", query="SELECT c FROM Contenedor c"),
	@NamedQuery(name="Contenedor.buscarPorEnsuranceId", query="SELECT c FROM Contenedor c where c.idEnsurance = :ensuranceId")
})
public class Contenedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private boolean activo;

	private String descripcion;

	@Column(name="id_ensurance")
	private BigInteger idEnsurance;

	private String numero;

	public Contenedor() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigInteger getIdEnsurance() {
		return this.idEnsurance;
	}

	public void setIdEnsurance(BigInteger idEnsurance) {
		this.idEnsurance = idEnsurance;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}