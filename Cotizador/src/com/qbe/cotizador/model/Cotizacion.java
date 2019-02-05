package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the COTIZACION database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Cotizacion.buscarPorId", query="SELECT c FROM Cotizacion c where c.id = :id"),
	@NamedQuery(name="Cotizacion.buscarPorTipoObjeto", query="SELECT c FROM Cotizacion c where c.tipoObjeto = :tipoObjeto"),
	@NamedQuery(name="Cotizacion.buscarPorEstado", query="SELECT c FROM Cotizacion c where c.estado = :estado"),
	@NamedQuery(name="Cotizacion.buscarPorEstadoPuntoVenta", query="SELECT c FROM Cotizacion c where c.estado = :estado and c.puntoVenta=:puntoVenta"),
	@NamedQuery(name="Cotizacion.buscarPorPago", query="SELECT c FROM Cotizacion c where c.pago = :pago"),
	@NamedQuery(name="Cotizacion.buscarTodos", query="SELECT c FROM Cotizacion c"),
	@NamedQuery(name="Cotizacion.buscarPorGrupoPorProducto", query="SELECT c FROM Cotizacion c where c.grupoPorProductoId=:grupoPorProductoId"),
	@NamedQuery(name="Cotizacion.buscarPorTipoObjetoPuntoVenta", query="SELECT c FROM Cotizacion c where c.tipoObjeto = :tipoObjeto and c.puntoVenta = :puntoVenta"),
})
public class Cotizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Column(name="agente_id")
	private BigInteger agenteId;

	@Column(name="cliente_id")
	private BigInteger clienteId;

	@Column(name="emitir_programa_seguros")
	private boolean emitirProgramaSeguros;

	@Column(name="etapa_wizard")
	private int etapaWizard;

	@Column(name="fecha_elaboracion")
	private Timestamp fechaElaboracion;

	@Column(name="fecha_emision")
	private Timestamp fechaEmision;

	@Column(name="grupo_por_producto_id")
	private BigInteger grupoPorProductoId;

	@Column(name="grupo_producto_id")
	private BigInteger grupoProductoId;

	@Column(name="imp_derecho_emision")
	private double impDerechoEmision;

	@Column(name="imp_iva")
	private double impIva;

	@Column(name="imp_recargo_seguro_campesino")
	private double impRecargoSeguroCampesino;

	@Column(name="imp_seguro_campesino")
	private double impSeguroCampesino;

	@Column(name="imp_super_bancos")
	private double impSuperBancos;

	private String numeroFactura;

	@Column(name="porcentaje_comision")
	private double porcentajeComision;

	@Column(name="prima_neta_total")
	private String primaNetaTotal;
	
	@Column(name="numero_tramite")
	private String numeroTramite;

	@Column(name="prima_origen")
	private double primaOrigen;

	@Column(name="producto_x_punto_venta_id")
	private BigInteger productoXPuntoVentaId;

	@Column(name="suma_asegurada_total")
	private double sumaAseguradaTotal;

	@Column(name="tasa_producto_id")
	private BigInteger tasaProductoId;

	@Column(name="tasa_producto_valor")
	private double tasaProductoValor;

	private double totalFactura;

	@Column(name="valor_descuento")
	private double valorDescuento;
	
	@Column(name="tasa_minima")
	private double tasaMinima;

	private String valoresDepresiacionVigencia;
	
	@Column(name="informacion_adicional")
	private String informacionAdicional;
	
	@Temporal(TemporalType.DATE)
	@Column(name="vigencia_desde")
	private Date vigenciaDesde;

	//bi-directional many-to-one association to PuntoVenta
	@ManyToOne
	@JoinColumn(name="punto_venta_id")
	private PuntoVenta puntoVenta;

	//bi-directional many-to-one association to PuntoVenta
	@ManyToOne
	@JoinColumn(name="asegurado_id")
	private Entidad asegurado;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	private Estado estado;

	//bi-directional many-to-one association to TipoObjeto
	@ManyToOne
	@JoinColumn(name="tipo_objeto_id")
	private TipoObjeto tipoObjeto;

	//bi-directional many-to-one association to VigenciaPoliza
	@ManyToOne
	@JoinColumn(name="vigencia_poliza_id")
	private VigenciaPoliza vigenciaPoliza;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	private Usuario usuario;

	//bi-directional many-to-one association to Pago
	@ManyToOne
	private Pago pago;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	private Producto producto;

	//bi-directional many-to-one association to CotizacionDetalle
	@OneToMany(mappedBy="cotizacion", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	private List<CotizacionDetalle> cotizacionDetalles;

	//bi-directional many-to-one association to SolicitudDescuento
	@OneToMany(mappedBy="cotizacion", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	private List<SolicitudDescuento> solicitudDescuentos;

	//bi-directional many-to-one association to SolicitudInspeccion
	@OneToMany(mappedBy="cotizacion", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	private List<SolicitudInspeccion> solicitudInspeccions;

	//bi-directional many-to-one association to SolicitudInspeccion
	@OneToMany(mappedBy="cotizacion", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	private List<DocumentoVisado> documentoVisados;

	public Cotizacion() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getAgenteId() {
		return this.agenteId;
	}

	public double getTasaMinima() {
		return tasaMinima;
	}

	public void setTasaMinima(double tasaMinima) {
		this.tasaMinima = tasaMinima;
	}

	public void setAgenteId(BigInteger agenteId) {
		this.agenteId = agenteId;
	}

	public BigInteger getClienteId() {
		return this.clienteId;
	}

	public void setClienteId(BigInteger clienteId) {
		this.clienteId = clienteId;
	}

	public boolean getEmitirProgramaSeguros() {
		return this.emitirProgramaSeguros;
	}

	public void setEmitirProgramaSeguros(boolean emitirProgramaSeguros) {
		this.emitirProgramaSeguros = emitirProgramaSeguros;
	}

	public int getEtapaWizard() {
		return this.etapaWizard;
	}

	public void setEtapaWizard(int etapaWizard) {
		this.etapaWizard = etapaWizard;
	}

	public Timestamp getFechaElaboracion() {
		return this.fechaElaboracion;
	}

	public void setFechaElaboracion(Timestamp fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

	public Timestamp getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public BigInteger getGrupoPorProductoId() {
		return this.grupoPorProductoId;
	}

	public void setGrupoPorProductoId(BigInteger grupoPorProductoId) {
		this.grupoPorProductoId = grupoPorProductoId;
	}

	public BigInteger getGrupoProductoId() {
		return this.grupoProductoId;
	}

	public void setGrupoProductoId(BigInteger grupoProductoId) {
		this.grupoProductoId = grupoProductoId;
	}

	public double getImpDerechoEmision() {
		return this.impDerechoEmision;
	}

	public void setImpDerechoEmision(double impDerechoEmision) {
		this.impDerechoEmision = impDerechoEmision;
	}

	public double getImpIva() {
		return this.impIva;
	}

	public void setImpIva(double impIva) {
		this.impIva = impIva;
	}

	public double getImpRecargoSeguroCampesino() {
		return this.impRecargoSeguroCampesino;
	}

	public void setImpRecargoSeguroCampesino(double impRecargoSeguroCampesino) {
		this.impRecargoSeguroCampesino = impRecargoSeguroCampesino;
	}

	public double getImpSeguroCampesino() {
		return this.impSeguroCampesino;
	}

	public void setImpSeguroCampesino(double impSeguroCampesino) {
		this.impSeguroCampesino = impSeguroCampesino;
	}

	public double getImpSuperBancos() {
		return this.impSuperBancos;
	}

	public void setImpSuperBancos(double impSuperBancos) {
		this.impSuperBancos = impSuperBancos;
	}

	public String getNumeroFactura() {
		return this.numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public double getPorcentajeComision() {
		return this.porcentajeComision;
	}

	public void setPorcentajeComision(double porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public String getPrimaNetaTotal() {
		return this.primaNetaTotal;
	}

	public void setPrimaNetaTotal(String primaNetaTotal) {
		this.primaNetaTotal = primaNetaTotal;
	}

	public String getNumeroTramite() {
		return this.numeroTramite;
	}

	public void setNumeroTramite(String numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	public double getPrimaOrigen() {
		return this.primaOrigen;
	}

	public void setPrimaOrigen(double primaOrigen) {
		this.primaOrigen = primaOrigen;
	}

	public BigInteger getProductoXPuntoVentaId() {
		return this.productoXPuntoVentaId;
	}

	public void setProductoXPuntoVentaId(BigInteger productoXPuntoVentaId) {
		this.productoXPuntoVentaId = productoXPuntoVentaId;
	}

	public double getSumaAseguradaTotal() {
		return this.sumaAseguradaTotal;
	}

	public void setSumaAseguradaTotal(double sumaAseguradaTotal) {
		this.sumaAseguradaTotal = sumaAseguradaTotal;
	}

	public BigInteger getTasaProductoId() {
		return this.tasaProductoId;
	}

	public void setTasaProductoId(BigInteger tasaProductoId) {
		this.tasaProductoId = tasaProductoId;
	}

	public double getTasaProductoValor() {
		return this.tasaProductoValor;
	}

	public void setTasaProductoValor(double tasaProductoValor) {
		this.tasaProductoValor = tasaProductoValor;
	}

	public double getTotalFactura() {
		return this.totalFactura;
	}

	public void setTotalFactura(double totalFactura) {
		this.totalFactura = totalFactura;
	}

	public double getValorDescuento() {
		return this.valorDescuento;
	}

	public void setValorDescuento(double valorDescuento) {
		this.valorDescuento = valorDescuento;
	}

	public String getValoresDepresiacionVigencia() {
		return this.valoresDepresiacionVigencia;
	}

	public void setValoresDepresiacionVigencia(String valoresDepresiacionVigencia) {
		this.valoresDepresiacionVigencia = valoresDepresiacionVigencia;
	}

	public Date getVigenciaDesde() {
		return this.vigenciaDesde;
	}

	public void setVigenciaDesde(Date vigenciaDesde) {
		this.vigenciaDesde = vigenciaDesde;
	}

	public PuntoVenta getPuntoVenta() {
		return this.puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoObjeto getTipoObjeto() {
		return this.tipoObjeto;
	}

	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public VigenciaPoliza getVigenciaPoliza() {
		return this.vigenciaPoliza;
	}

	public void setVigenciaPoliza(VigenciaPoliza vigenciaPoliza) {
		this.vigenciaPoliza = vigenciaPoliza;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Entidad getAsegurado() {
		return this.asegurado;
	}

	public void setAsegurado(Entidad asegurado) {
		this.asegurado = asegurado;
	}
	
	public Pago getPago() {
		return this.pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}
	
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public List<CotizacionDetalle> getCotizacionDetalles() {
		return this.cotizacionDetalles;
	}

	public void setCotizacionDetalles(List<CotizacionDetalle> cotizacionDetalles) {
		this.cotizacionDetalles = cotizacionDetalles;
	}

	public CotizacionDetalle addCotizacionDetalle(CotizacionDetalle cotizacionDetalle) {
		getCotizacionDetalles().add(cotizacionDetalle);
		cotizacionDetalle.setCotizacion(this);

		return cotizacionDetalle;
	}

	public CotizacionDetalle removeCotizacionDetalle(CotizacionDetalle cotizacionDetalle) {
		getCotizacionDetalles().remove(cotizacionDetalle);
		cotizacionDetalle.setCotizacion(null);

		return cotizacionDetalle;
	}

	public List<SolicitudDescuento> getSolicitudDescuentos() {
		return this.solicitudDescuentos;
	}

	public void setSolicitudDescuentos(List<SolicitudDescuento> solicitudDescuentos) {
		this.solicitudDescuentos = solicitudDescuentos;
	}

	public SolicitudDescuento addSolicitudDescuento(SolicitudDescuento solicitudDescuento) {
		getSolicitudDescuentos().add(solicitudDescuento);
		solicitudDescuento.setCotizacion(this);

		return solicitudDescuento;
	}

	public SolicitudDescuento removeSolicitudDescuento(SolicitudDescuento solicitudDescuento) {
		getSolicitudDescuentos().remove(solicitudDescuento);
		solicitudDescuento.setCotizacion(null);

		return solicitudDescuento;
	}

	public List<SolicitudInspeccion> getSolicitudInspeccions() {
		return this.solicitudInspeccions;
	}

	public void setSolicitudInspeccions(List<SolicitudInspeccion> solicitudInspeccions) {
		this.solicitudInspeccions = solicitudInspeccions;
	}

	public SolicitudInspeccion addSolicitudInspeccion(SolicitudInspeccion solicitudInspeccion) {
		getSolicitudInspeccions().add(solicitudInspeccion);
		solicitudInspeccion.setCotizacion(this);

		return solicitudInspeccion;
	}

	public SolicitudInspeccion removeSolicitudInspeccion(SolicitudInspeccion solicitudInspeccion) {
		getSolicitudInspeccions().remove(solicitudInspeccion);
		solicitudInspeccion.setCotizacion(null);

		return solicitudInspeccion;
	}
}