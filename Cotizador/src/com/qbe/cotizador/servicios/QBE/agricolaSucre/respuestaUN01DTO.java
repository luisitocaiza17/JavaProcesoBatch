package com.qbe.cotizador.servicios.QBE.agricolaSucre;

public class respuestaUN01DTO {
	
	private int autorizacion;
	
	private String diferenciaMonto;
	
	private String estado;
	
	private float montoAprobadoQBE;
	
	private float montoRecomendadoQBE;
	
	private String observacion;
	
	private float prima;

	public int getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(int autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getDiferenciaMonto() {
		return diferenciaMonto;
	}

	public void setDiferenciaMonto(String diferenciaMonto) {
		this.diferenciaMonto = diferenciaMonto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public float getMontoAprobadoQBE() {
		return montoAprobadoQBE;
	}

	public void setMontoAprobadoQBE(float montoAprobadoQBE) {
		this.montoAprobadoQBE = montoAprobadoQBE;
	}

	public float getMontoRecomendadoQBE() {
		return montoRecomendadoQBE;
	}

	public void setMontoRecomendadoQBE(float montoRecomendadoQBE) {
		this.montoRecomendadoQBE = montoRecomendadoQBE;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public float getPrima() {
		return prima;
	}

	public void setPrima(float prima) {
		this.prima = prima;
	}
}
