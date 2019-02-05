package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the producto_x_punto_venta database table.
 * 
 */
@Entity
@Table(name="PRODUCTO_X_PUNTO_VENTA")
@NamedQueries({
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorId", query="SELECT c FROM ProductoXPuntoVenta c where c.id = :id"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorProductoPuntoVenta", query="SELECT c FROM ProductoXPuntoVenta c where c.grupoPorProducto=:grupoPorProducto"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorProductoPuntoVentaListado", query="SELECT c FROM ProductoXPuntoVenta c where c.grupoPorProducto=:grupoPorProducto order by c.puntoVenta.nombre"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorGrupoPunto", query="SELECT c FROM ProductoXPuntoVenta c where c.puntoVenta=:puntoVenta and c.grupoPorProducto.tipoGrupo=:tipoGrupo"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarTodos", query="SELECT c FROM ProductoXPuntoVenta c"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorGrupoPuntoVenta", query="SELECT c FROM ProductoXPuntoVenta c where c.grupoPorProducto=:grupoPorProducto and c.puntoVenta=:puntoVenta"),
	@NamedQuery(name="ProductoXPuntoVenta.buscarPorProductoPuntoVentaSession", query="SELECT c FROM ProductoXPuntoVenta c where c.grupoPorProducto.tipoGrupo.id IN :listadoTipoGrupo order by c.puntoVenta.nombre")	
})
public class ProductoXPuntoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Column(name="contenedor_id")
	private String contenedorId;

	//bi-directional many-to-one association to GrupoPorProducto
	@ManyToOne
	@JoinColumn(name="grupo_por_producto_id")
	private GrupoPorProducto grupoPorProducto;

	//bi-directional many-to-one association to PuntoVenta
	@ManyToOne
	@JoinColumn(name="punto_venta_id")
	private PuntoVenta puntoVenta;

	//bi-directional many-to-one association to UnidadProduccion
	@ManyToOne
	@JoinColumn(name="unidad_produccion_id")
	private UnidadProduccion unidadProduccion;

	//bi-directional many-to-one association to UnidadNegocio
	@ManyToOne
	@JoinColumn(name="unidad_negocio_id")
	private UnidadNegocio unidadNegocio;

	//bi-directional many-to-one association to Plan
	@ManyToOne
	private Plan plan;

	public ProductoXPuntoVenta() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContenedorId() {
		return this.contenedorId;
	}

	public void setContenedorId(String contenedorId) {
		this.contenedorId = contenedorId;
	}

	public GrupoPorProducto getGrupoPorProducto() {
		return this.grupoPorProducto;
	}

	public void setGrupoPorProducto(GrupoPorProducto grupoPorProducto) {
		this.grupoPorProducto = grupoPorProducto;
	}

	public PuntoVenta getPuntoVenta() {
		return this.puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public UnidadProduccion getUnidadProduccion() {
		return this.unidadProduccion;
	}

	public void setUnidadProduccion(UnidadProduccion unidadProduccion) {
		this.unidadProduccion = unidadProduccion;
	}

	public UnidadNegocio getUnidadNegocio() {
		return this.unidadNegocio;
	}

	public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}

	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}