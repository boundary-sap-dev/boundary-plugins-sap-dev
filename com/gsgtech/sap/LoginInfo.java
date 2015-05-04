package com.gsgtech.sap;

import java.io.Serializable;


public class LoginInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6622384224852245123L;
	public LoginInfo(String aHost, String sysnr, String client, String user,
			String passwd, String lang) {
		super();
		this.aHost = aHost;
		this.sysnr = sysnr;
		this.client = client;
		this.user = user;
		this.passwd = passwd;
		this.lang = lang;
	}
	private long poll = 1000;
	private String aHost="localhost";
	private String sysnr="00";
	private String client="000";
	private String user="user";
	private String passwd="pwd";
	private String lang="EN";
	
	public long getPoll() {
		return poll;
	}
	public void setPoll(long poll) {
		this.poll = poll;
	}
	public String getaHost() {
		return aHost;
	}
	public void setaHost(String aHost) {
		this.aHost = aHost;
	}
	public String getSysnr() {
		return sysnr;
	}
	public void setSysnr(String sysnr) {
		this.sysnr = sysnr;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	@Override
	public String toString() {
		return "LoginInfo [aHost=" + aHost + ", sysnr=" + sysnr + ", client="
				+ client + ", user=" + user + ", passwd=*******" +", lang="
				+ lang + "]";
	}
	
	

}
