package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the TIPO_OCUPACION database table.
 * 
 */
@Entity
@Table(name="TIPO_OCUPACION")
@NamedQueries({
	@NamedQuery(name="TipoOcupacion.buscarPorId", query="SELECT c FROM TipoOcupacion c where c.id = :id"),
	@NamedQuery(name="TipoOcupacion.buscarTodos", query="SELECT c FROM TipoOcupacion c"),
	@NamedQuery(name="TipoOcupacion.buscarPorNombre", query="SELECT c FROM TipoOcupacion c where c.nombre = :nombre")
})
public class TipoOcupacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String nombre;

	public TipoOcupacion() {
	}

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