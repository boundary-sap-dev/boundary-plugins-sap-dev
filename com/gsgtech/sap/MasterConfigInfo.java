package com.gsgtech.sap;

import java.io.Serializable;

public class MasterConfigInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6048577292161347831L;
	/**
	 * 
	 */
	
	private String mandt;
	private String dbName;
	private String opSys;
	private String sapRelease;
	private String server;
	private String categ;
	private String metric;
	private String active;
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getOpSys() {
		return opSys;
	}
	public void setOpSys(String opSys) {
		this.opSys = opSys;
	}
	public String getSapRelease() {
		return sapRelease;
	}
	public void setSapRelease(String sapRelease) {
		this.sapRelease = sapRelease;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	

	

}
