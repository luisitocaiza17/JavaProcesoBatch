package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the TIPO_CONSTRUCCION database table.
 * 
 */
@Entity
@Table(name="TIPO_CONSTRUCCION")
@NamedQueries({
	@NamedQuery(name="TipoConstruccion.buscarPorId", query="SELECT c FROM TipoConstruccion c where c.id = :id"),
	@NamedQuery(name="TipoConstruccion.buscarTodos", query="SELECT c FROM TipoConstruccion c"),
	@NamedQuery(name="TipoConstruccion.buscarPorNombre", query="SELECT c FROM TipoConstruccion c where c.nombre = :nombre")
})
public class TipoConstruccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String nombre;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}