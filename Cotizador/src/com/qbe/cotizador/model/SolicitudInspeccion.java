package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SOLICITUD_INSPECCION database table.
 * 
 */
@Entity
@Table(name="SOLICITUD_INSPECCION")
@NamedQueries({
	@NamedQuery(name="SolicitudInspeccion.buscarPorId", query="SELECT c FROM SolicitudInspeccion c where c.id = :id"),
	@NamedQuery(name="SolicitudInspeccion.buscarTodos", query="SELECT c FROM SolicitudInspeccion c"),
	@NamedQuery(name="SolicitudInspeccion.buscarPorCotizacion", query="SELECT c FROM SolicitudInspeccion c where c.cotizacion = :cotizacion"),
	@NamedQuery(name="SolicitudInspeccion.buscarPorEstado", query="SELECT c FROM SolicitudInspeccion c where c.estado = :estado"),
	@NamedQuery(name="SolicitudInspeccion.buscarEstadoInspector", query="SELECT c FROM SolicitudInspeccion c WHERE c.estado = :estado and c.inspector = :inspector")
})	
public class SolicitudInspeccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@ManyToOne
	@JoinColumn(name="cotizacion_id")
	private Cotizacion cotizacion;

	private String destino;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_solicitud")
	private Date fechaSolicitud;

	//bi-directional many-to-one association to Inspector
	@ManyToOne
	private Inspector inspector;

	private String origen;

	@Column(name="telf_contacto")
	private String telfContacto;

	@Column(name="valor_inspeccion")
	private double valorInspeccion;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	private Estado estado;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	private Usuario usuario;
	
	//bi-directional many-to-one association to Zona
	@ManyToOne
	private Zona zona;

	public SolicitudInspeccion() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cotizacion getCotizacion() {
		return this.cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Inspector getInspector() {
		return this.inspector;
	}

	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getTelfContacto() {
		return this.telfContacto;
	}

	public void setTelfContacto(String telfContacto) {
		this.telfContacto = telfContacto;
	}

	public double getValorInspeccion() {
		return this.valorInspeccion;
	}

	public void setValorInspeccion(double valorInspeccion) {
		this.valorInspeccion = valorInspeccion;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}
	
}