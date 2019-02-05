package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the ROL database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Rol.buscarTodosActivos", query="SELECT c FROM Rol c WHERE c.activo=:valorActivo ORDER BY c.nombre"),
	@NamedQuery(name="Rol.buscarPorId", query="SELECT c FROM Rol c where c.id = :id"),
	@NamedQuery(name="Rol.buscarTodos", query="SELECT c FROM Rol c ORDER BY c.nombre"),
	@NamedQuery(name="Rol.buscarRolPorDefecto", query="SELECT c FROM Rol c where c.nombre = :nombre ORDER BY c.nombre")
})
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private boolean activo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to OpcionMenuPantallaRol
	@OneToMany(mappedBy="rol")
	private List<OpcionMenuPantallaRol> opcionMenuPantallaRols;

	//bi-directional many-to-one association to TipoRolModulo
	@OneToMany(mappedBy="rol")
	private List<TipoRolModulo> tipoRolModulos;

	//bi-directional many-to-one association to UsuarioRol
	@OneToMany(mappedBy="rol")
	private List<UsuarioRol> usuarioRols;

	public Rol() {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<OpcionMenuPantallaRol> getOpcionMenuPantallaRols() {
		return this.opcionMenuPantallaRols;
	}

	public void setOpcionMenuPantallaRols(List<OpcionMenuPantallaRol> opcionMenuPantallaRols) {
		this.opcionMenuPantallaRols = opcionMenuPantallaRols;
	}

	public OpcionMenuPantallaRol addOpcionMenuPantallaRol(OpcionMenuPantallaRol opcionMenuPantallaRol) {
		getOpcionMenuPantallaRols().add(opcionMenuPantallaRol);
		opcionMenuPantallaRol.setRol(this);

		return opcionMenuPantallaRol;
	}

	public OpcionMenuPantallaRol removeOpcionMenuPantallaRol(OpcionMenuPantallaRol opcionMenuPantallaRol) {
		getOpcionMenuPantallaRols().remove(opcionMenuPantallaRol);
		opcionMenuPantallaRol.setRol(null);

		return opcionMenuPantallaRol;
	}

	public List<TipoRolModulo> getTipoRolModulos() {
		return this.tipoRolModulos;
	}

	public void setTipoRolModulos(List<TipoRolModulo> tipoRolModulos) {
		this.tipoRolModulos = tipoRolModulos;
	}

	public TipoRolModulo addTipoRolModulo(TipoRolModulo tipoRolModulo) {
		getTipoRolModulos().add(tipoRolModulo);
		tipoRolModulo.setRol(this);

		return tipoRolModulo;
	}

	public TipoRolModulo removeTipoRolModulo(TipoRolModulo tipoRolModulo) {
		getTipoRolModulos().remove(tipoRolModulo);
		tipoRolModulo.setRol(null);

		return tipoRolModulo;
	}

	public List<UsuarioRol> getUsuarioRols() {
		return this.usuarioRols;
	}

	public void setUsuarioRols(List<UsuarioRol> usuarioRols) {
		this.usuarioRols = usuarioRols;
	}

	public UsuarioRol addUsuarioRol(UsuarioRol usuarioRol) {
		getUsuarioRols().add(usuarioRol);
		usuarioRol.setRol(this);

		return usuarioRol;
	}

	public UsuarioRol removeUsuarioRol(UsuarioRol usuarioRol) {
		getUsuarioRols().remove(usuarioRol);
		usuarioRol.setRol(null);

		return usuarioRol;
	}

}