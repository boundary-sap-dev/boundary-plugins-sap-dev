package com.gsgtech.sap;

import java.io.Serializable;

public class MasterMetricaInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 910223282921174226L;
	/**
	 * 
	 */
	
	private String mandt;
	private String server; //new
	private String categ; //new
	
	private String metric;
	private String text;
	private String unit;
	
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "MasterMetricaInfo [mandt=" + mandt + ", server=" + server
				+ ", categ=" + categ + ", metric=" + metric + ", text=" + text
				+ ", unit=" + unit + "]";
	}
	

}
