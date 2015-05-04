package com.gsgtech.sap;

import java.io.Serializable;

public class MasterCategoriaInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6633673349236328528L;
	/**
	 * 
	 */
	
	private String categ;
	private String server; //new
	
	private String mandt;	
	private String text;
	private String event;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getCateg() {
		return categ;
	}
	public void setCateg(String categ) {
		this.categ = categ;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	@Override
	public String toString() {
		return "MasterCategoriaInfo [categ=" + categ + ", server=" + server
				+ ", mandt=" + mandt + ", text=" + text + ", event=" + event
				+ "]";
	}
	
	

}
