package com.qbe.cotizador.servlets.producto.agricola;

import java.util.List;

public class TransporteData {
	
	private List<Provincia> Provincias;
	
	private List<Canton> Cantones;
	
	private List<Parroquia> Parroquias;
	
	private List<TipoCultivo> TiposCultivos;
	
	private List<Ciclo> Ciclos;
	
	private List<Regla> Reglas;
	
	private List<TipoIdentificacion> TiposIdentificacion;
	
	private List<PuntoVenta> PuntosVentaAgricola;
	
	private List<ParametrosXPuntoVenta> ParametrosPuntoVenta;
	
	private List<Variedad> Variedades;
	
	private List<TipoCultivoXTipoCalculo> TiposCultivoXTiposCalculos;
	
	private ParametroGeneral ParametrosGenerales;

	public List<Provincia> getProvincias() {
		return Provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		Provincias = provincias;
	}

	public List<Canton> getCantones() {
		return Cantones;
	}

	public void setCantones(List<Canton> cantones) {
		Cantones = cantones;
	}

	public List<Parroquia> getParroquias() {
		return Parroquias;
	}

	public void setParroquias(List<Parroquia> parroquias) {
		Parroquias = parroquias;
	}

	public List<TipoCultivo> getTiposCultivos() {
		return TiposCultivos;
	}

	public void setTiposCultivos(List<TipoCultivo> tiposCultivos) {
		TiposCultivos = tiposCultivos;
	}

	public List<Ciclo> getCiclos() {
		return Ciclos;
	}

	public void setCiclos(List<Ciclo> ciclos) {
		Ciclos = ciclos;
	}

	public List<Regla> getReglas() {
		return Reglas;
	}

	public void setReglas(List<Regla> reglas) {
		Reglas = reglas;
	}

	public List<TipoIdentificacion> getTiposIdentificacion() {
		return TiposIdentificacion;
	}

	public void setTiposIdentificacion(List<TipoIdentificacion> tiposIdentificacion) {
		TiposIdentificacion = tiposIdentificacion;
	}

	public List<PuntoVenta> getPuntosVentaAgricola() {
		return PuntosVentaAgricola;
	}

	public void setPuntosVentaAgricola(List<PuntoVenta> puntosVentaAgricola) {
		PuntosVentaAgricola = puntosVentaAgricola;
	}

	public List<ParametrosXPuntoVenta> getParametrosPuntoVenta() {
		return ParametrosPuntoVenta;
	}

	public void setParametrosPuntoVenta(
			List<ParametrosXPuntoVenta> parametrosPuntoVenta) {
		ParametrosPuntoVenta = parametrosPuntoVenta;
	}

	public List<Variedad> getVariedades() {
		return Variedades;
	}

	public void setVariedades(List<Variedad> variedades) {
		Variedades = variedades;
	}

	public List<TipoCultivoXTipoCalculo> getTiposCultivoXTiposCalculos() {
		return TiposCultivoXTiposCalculos;
	}

	public void setTiposCultivoXTiposCalculos(
			List<TipoCultivoXTipoCalculo> tiposCultivoXTiposCalculos) {
		TiposCultivoXTiposCalculos = tiposCultivoXTiposCalculos;
	}

	public ParametroGeneral getParametrosGenerales() {
		return ParametrosGenerales;
	}

	public void setParametrosGenerales(ParametroGeneral parametrosGenerales) {
		ParametrosGenerales = parametrosGenerales;
	}
	
}
