package com.qbe.cotizador.servicios.QBE.ganadero;

import java.math.BigDecimal;
import java.util.Date;

public class AseguradoOBJ {
	
	private int tipoIdentificacion;
	
	private String identificacion;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private Date fechaNacimiento;
	
	private String callePrincipal;
	
	private String numeroCasa;
	
	private String calleSecundaria;
	
	private String pais;
	
	private String provincia;
	
	private String ciudad;
	
	private String parroquia;
	
	private String telefono;
	
	private String celular;
	
	private String email;
	
	private Boolean personaExpuestaPoliticamente;
	
	private BigDecimal salarioMensual;
	
	private BigDecimal otrosIngresos;
	
	private BigDecimal totalEgresos;
	
	public int getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	
	public void setTipoIdentificacion(int tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	public String getIdentificacion() {
		return identificacion;
	}
	
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getCallePrincipal() {
		return callePrincipal;
	}
	
	public void setCallePrincipal(String callePrincipal) {
		this.callePrincipal = callePrincipal;
	}
	
	public String getNumeroCasa() {
		return numeroCasa;
	}
	
	public void setNumeroCasa(String numeroCasa) {
		this.numeroCasa = numeroCasa;
	}
	
	public String getCalleSecundaria() {
		return calleSecundaria;
	}
	
	public void setCalleSecundaria(String calleSecundaria) {
		this.calleSecundaria = calleSecundaria;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getParroquia() {
		return parroquia;
	}
	
	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getPersonaExpuestaPoliticamente() {
		return personaExpuestaPoliticamente;
	}
	
	public void setPersonaExpuestaPoliticamente(Boolean personaExpuestaPoliticamente) {
		this.personaExpuestaPoliticamente = personaExpuestaPoliticamente;
	}
	
	public BigDecimal getSalarioMensual() {
		return salarioMensual;
	}
	
	public void setSalarioMensual(BigDecimal salarioMensual) {
		this.salarioMensual = salarioMensual;
	}
	
	public BigDecimal getOtrosIngresos() {
		return otrosIngresos;
	}
	
	public void setOtrosIngresos(BigDecimal otrosIngresos) {
		this.otrosIngresos = otrosIngresos;
	}
	
	public BigDecimal getTotalEgresos() {
		return totalEgresos;
	}
	
	public void setTotalEgresos(BigDecimal totalEgresos) {
		this.totalEgresos = totalEgresos;
	}
	
	public BigDecimal getTotalActivos() {
		return totalActivos;
	}
	
	public void setTotalActivos(BigDecimal totalActivos) {
		this.totalActivos = totalActivos;
	}
	
	public BigDecimal getTotalPasivos() {
		return totalPasivos;
	}
	
	public void setTotalPasivos(BigDecimal totalPasivos) {
		this.totalPasivos = totalPasivos;
	}
	
	private BigDecimal totalActivos;
	
	private BigDecimal totalPasivos;
		
}
