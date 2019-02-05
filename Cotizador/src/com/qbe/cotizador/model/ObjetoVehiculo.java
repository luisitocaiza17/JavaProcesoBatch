package com.qbe.cotizador.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the OBJETO_VEHICULO database table.
 * 
 */
@Entity
@Table(name="OBJETO_VEHICULO")
@NamedQueries({
	@NamedQuery(name="ObjetoVehiculo.buscarPorId", query="SELECT c FROM ObjetoVehiculo c where c.id=:id"),
	@NamedQuery(name="ObjetoVehiculo.buscarTodos", query="SELECT c FROM ObjetoVehiculo c"),
	@NamedQuery(name="ObjetoVehiculo.buscarPorPlaca", query="SELECT c FROM ObjetoVehiculo c where c.placa =:placa"),
	@NamedQuery(name="ObjetoVehiculo.buscarPorChasis", query="SELECT c FROM ObjetoVehiculo c where c.chasis =:chasis"),
	@NamedQuery(name="ObjetoVehiculo.buscarPorMotor", query="SELECT c FROM ObjetoVehiculo c where c.motor =:motor")
})
public class ObjetoVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Column(name="anio_fabricacion")
	private String anioFabricacion;

	@Column(name="`anos_sin _siniestro`")
	private String anosSin_Siniestro;

	@Column(name="antiguedad_vh")
	private String antiguedadVh;

	@Column(name="carga_pasajeros")
	private String cargaPasajeros;

	@Column(name="cero_kilometros")
	private boolean ceroKilometros;

	private String chasis;

	private String cilindraje;

	@Column(name="codigo_ensurance")
	private String codigoEnsurance;

	private String combustible;

	@Column(name="conductor_edad")
	private String conductorEdad;

	@Column(name="conductor_estado_civil")
	private String conductorEstadoCivil;

	@Column(name="conductor_genero")
	private String conductorGenero;

	@Column(name="dispositivo_rastreo")
	private boolean dispositivoRastreo;

	@Column(name="guarda_garage")
	private boolean guardaGarage;

	@Column(name="kilometros_recorridos")
	private String kilometrosRecorridos;

	@Column(name="modelo_ant")
	private String modeloAnt;

	private String motor;

	@Column(name="numero_hijos")
	private String numeroHijos;

	private int pasajeros;

	private String placa;

	@Column(name="porcentaje_comision")
	private double porcentajeComision;

	@Column(name="sucursal_id")
	private String sucursalId;

	@Column(name="suma_asegurada")
	private double sumaAsegurada;

	@Column(name="tipo_adquisicion")
	private String tipoAdquisicion;

	@Column(name="tonelaje_vehiculo")
	private double tonelajeVehiculo;

	private String zona;

	//bi-directional many-to-one association to Extra
	@OneToMany(mappedBy="objetoVehiculo")
	private List<Extra> extras;

	//bi-directional many-to-one association to Color
	@ManyToOne
	private Color color;

	//bi-directional many-to-one association to Modelo
	@ManyToOne
	private Modelo modelo;

	//bi-directional many-to-one association to TipoUso
	@ManyToOne
	@JoinColumn(name="tipo_uso_id")
	private TipoUso tipoUso;

	//bi-directional many-to-one association to TipoVehiculo
	@ManyToOne
	@JoinColumn(name="tipo_vehiculo_id")
	private TipoVehiculo tipoVehiculo;

	public ObjetoVehiculo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnioFabricacion() {
		return this.anioFabricacion;
	}

	public void setAnioFabricacion(String anioFabricacion) {
		this.anioFabricacion = anioFabricacion;
	}

	public String getAnosSin_Siniestro() {
		return this.anosSin_Siniestro;
	}

	public void setAnosSin_Siniestro(String anosSin_Siniestro) {
		this.anosSin_Siniestro = anosSin_Siniestro;
	}

	public String getAntiguedadVh() {
		return this.antiguedadVh;
	}

	public void setAntiguedadVh(String antiguedadVh) {
		this.antiguedadVh = antiguedadVh;
	}

	public String getCargaPasajeros() {
		return this.cargaPasajeros;
	}

	public void setCargaPasajeros(String cargaPasajeros) {
		this.cargaPasajeros = cargaPasajeros;
	}

	public boolean getCeroKilometros() {
		return this.ceroKilometros;
	}

	public void setCeroKilometros(boolean ceroKilometros) {
		this.ceroKilometros = ceroKilometros;
	}

	public String getChasis() {
		return this.chasis;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public String getCilindraje() {
		return this.cilindraje;
	}

	public void setCilindraje(String cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getCodigoEnsurance() {
		return this.codigoEnsurance;
	}

	public void setCodigoEnsurance(String codigoEnsurance) {
		this.codigoEnsurance = codigoEnsurance;
	}

	public String getCombustible() {
		return this.combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public String getConductorEdad() {
		return this.conductorEdad;
	}

	public void setConductorEdad(String conductorEdad) {
		this.conductorEdad = conductorEdad;
	}

	public String getConductorEstadoCivil() {
		return this.conductorEstadoCivil;
	}

	public void setConductorEstadoCivil(String conductorEstadoCivil) {
		this.conductorEstadoCivil = conductorEstadoCivil;
	}

	public String getConductorGenero() {
		return this.conductorGenero;
	}

	public void setConductorGenero(String conductorGenero) {
		this.conductorGenero = conductorGenero;
	}

	public boolean getDispositivoRastreo() {
		return this.dispositivoRastreo;
	}

	public void setDispositivoRastreo(boolean dispositivoRastreo) {
		this.dispositivoRastreo = dispositivoRastreo;
	}

	public boolean getGuardaGarage() {
		return this.guardaGarage;
	}

	public void setGuardaGarage(boolean guardaGarage) {
		this.guardaGarage = guardaGarage;
	}

	public String getKilometrosRecorridos() {
		return this.kilometrosRecorridos;
	}

	public void setKilometrosRecorridos(String kilometrosRecorridos) {
		this.kilometrosRecorridos = kilometrosRecorridos;
	}

	public String getModeloAnt() {
		return this.modeloAnt;
	}

	public void setModeloAnt(String modeloAnt) {
		this.modeloAnt = modeloAnt;
	}

	public String getMotor() {
		return this.motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getNumeroHijos() {
		return this.numeroHijos;
	}

	public void setNumeroHijos(String numeroHijos) {
		this.numeroHijos = numeroHijos;
	}

	public int getPasajeros() {
		return this.pasajeros;
	}

	public void setPasajeros(int pasajeros) {
		this.pasajeros = pasajeros;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getPorcentajeComision() {
		return this.porcentajeComision;
	}

	public void setPorcentajeComision(double porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public String getSucursalId() {
		return this.sucursalId;
	}

	public void setSucursalId(String sucursalId) {
		this.sucursalId = sucursalId;
	}

	public double getSumaAsegurada() {
		return this.sumaAsegurada;
	}

	public void setSumaAsegurada(double sumaAsegurada) {
		this.sumaAsegurada = sumaAsegurada;
	}

	public String getTipoAdquisicion() {
		return this.tipoAdquisicion;
	}

	public void setTipoAdquisicion(String tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}

	public double getTonelajeVehiculo() {
		return this.tonelajeVehiculo;
	}

	public void setTonelajeVehiculo(double tonelajeVehiculo) {
		this.tonelajeVehiculo = tonelajeVehiculo;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public List<Extra> getExtras() {
		return this.extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public Extra addExtra(Extra extra) {
		getExtras().add(extra);
		extra.setObjetoVehiculo(this);

		return extra;
	}

	public Extra removeExtra(Extra extra) {
		getExtras().remove(extra);
		extra.setObjetoVehiculo(null);

		return extra;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Modelo getModelo() {
		return this.modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public TipoUso getTipoUso() {
		return this.tipoUso;
	}

	public void setTipoUso(TipoUso tipoUso) {
		this.tipoUso = tipoUso;
	}

	public TipoVehiculo getTipoVehiculo() {
		return this.tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

}