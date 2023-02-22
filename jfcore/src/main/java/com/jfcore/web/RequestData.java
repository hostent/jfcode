package com.jfcore.web;

import java.util.HashMap;

public class RequestData {
	
	

	
	private String callId;
	
	private String token;
	
	private String ip;
	
	private String env;
	
	
	private HashMap<String, Object> pars = new HashMap<String, Object>();
	
	
	


	public String getCallId() {
		return callId;
	}


	public void setCallId(String callId) {
		this.callId = callId;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getEnv() {
		return env;
	}


	public void setEnv(String env) {
		this.env = env;
	}


	public HashMap<String, Object> getPars() {
		return pars;
	}


	public void setPars(HashMap<String, Object> pars) {
		this.pars = pars;
	}




}
