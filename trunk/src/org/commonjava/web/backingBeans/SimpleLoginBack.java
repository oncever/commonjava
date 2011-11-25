package org.commonjava.web.backingBeans;

import org.commonjava.cripto.CriptoUtil;

public class SimpleLoginBack {

	private boolean loggedIn = false;
	
	private String login;
	private String password;
	
	private String configuredLogin;
	private String configuredPassword;
	
	public void login(){
		if(login.equals(configuredLogin)&& CriptoUtil.md5(password).equals(configuredPassword)) loggedIn = true;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfiguredLogin() {
		return configuredLogin;
	}

	public void setConfiguredLogin(String configuredLogin) {
		this.configuredLogin = configuredLogin;
	}

	public String getConfiguredPassword() {
		return configuredPassword;
	}

	public void setConfiguredPassword(String configuredPassword) {
		this.configuredPassword = configuredPassword;
	}
}
