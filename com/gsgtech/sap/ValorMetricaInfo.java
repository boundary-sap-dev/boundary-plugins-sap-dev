package com.gsgtech.sap;

import java.io.Serializable;

public class ValorMetricaInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1130475974575223386L;
	/**
	 * 
	 */
	
	private String server;  //se llama source en el MF
	private String subSource;
	private String categ;
	private String metric;
	private String value;
	private String unit;
	
	//
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getSubSource() {
		return subSource;
	}
	public void setSubSource(String subSource) {
		this.subSource = subSource;
	}
	public String getCateg() {
		return categ;
	}
	public void setCateg(String categ) {
		this.categ = categ;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "ValorMetricaInfo [server=" + server + ", subSource="
				+ subSource + ", categ=" + categ + ", metric=" + metric
				+ ", value=" + value + ", unit=" + unit + "]";
	}

	

	
	

}
