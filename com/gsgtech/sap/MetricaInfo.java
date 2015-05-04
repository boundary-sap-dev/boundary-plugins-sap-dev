package com.gsgtech.sap;

import java.io.Serializable;

public class MetricaInfo implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7733014912896747759L;

	public MetricaInfo() {
		super();
		this.timestamp = "";
	}
	
	
	public MetricaInfo(String fuente, String etiqueta, String valor, String timestamp) {
		super();
		this.etiqueta = etiqueta;
		this.fuente = fuente;
		this.valor = valor;		
		this.timestamp = timestamp;
	}
	/**
	 * 
	 */

	private String etiqueta;
	private String valor;
	private String fuente;
	private String timestamp;
	
	@Override
	public String toString() {
		//return etiqueta.toUpperCase() + "_FD021 "+valor + " " + fuente + " " + timestamp;
		//return etiqueta + " " + valor  + " " + fuente;
		return consoleTime(false);
	}
	
	private String consoleTime(boolean withTime) {
		StringBuilder out = new StringBuilder();
		//return etiqueta.toUpperCase() + "_FD021 "+valor + " " + fuente + " " + timestamp;
		out.append("SAP_");
		out.append(etiqueta);
		out.append('\t');
		out.append(valor);
		out.append('\t');
		out.append(fuente);
		if (withTime) {
			out.append('\t');
			out.append(timestamp);
		}
		
		return out.toString();
	}
	
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	

}
